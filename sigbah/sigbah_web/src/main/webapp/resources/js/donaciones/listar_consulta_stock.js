var listaDonacionesCache = new Object();
var listaRegionesCache = new Object();

var tbl_stock_todos = $('#tbl_stock_todos');
var tbl_stock_ddi = $('#tbl_stock_ddi');
var tbl_stock_almacen = $('#tbl_stock_almacen');

var tbl_det_estados = $('#tbl_det_estados');
var tbl_regiones = $('#tbl_regiones');
var frm_con_donaciones = $('#frm_con_donaciones');
var frm_nue_estado = $('#frm_nue_estado');


$(document).ready(function() {
	
	inicializarDatos();
	 
	frm_con_donaciones.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_anio : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Año.'
					}
				}
			},
			sel_ddi : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar DDI.'
					}
				}
			}
		}
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
		
		listarTablaStock()
		
	});
	
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_con_donaciones > tbody > tr').length;
		var empty = null;
		$('tr.odd').each(function() {		
			empty = $(this).find('.dataTables_empty').text();
			return false;
		});					
		if (!esnulo(empty) || row < 1) {
			addWarnMessage(null, mensajeReporteRegistroValidacion);
			return;
		}

		loadding(true);
		
		var codigoAnio = $('#sel_anio').val();
		var codigoDdi = $('#txt_cod_ddi').val();
		var codigoMes = $('#sel_mes').val();
		var codigoMov = $('#sel_movimiento').val();
		var url = VAR_CONTEXT + '/donacionesIngreso/registro-donacionesIngreso/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametro(codigoDdi) + '/';
		url += verificaParametro(codigoMes) + '/';
		url += verificaParametro(codigoMov);
		
		$.fileDownload(url).done(function(respuesta) {
			loadding(false);	
			if (respuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, mensajeReporteError);
			} else {
				addInfoMessage(null, mensajeReporteExito);
			}
		}).fail(function (respuesta) {
			loadding(false);
			addErrorMessage(null, mensajeReporteError);
		});

	});
	
	$('#href_imprimir').click(function(e) {
		e.preventDefault();
		var idDdi = +$('#sel_ddi').val();
		var idAlmacen = $('#sel_almacen').val();
		var nomDdi = $("#sel_ddi option:selected").text();
		var nomAlmacen = $("#sel_almacen option:selected").text();
		loadding(true);
		var url = VAR_CONTEXT + '/donaciones/consulta/exportarPdf/'+idDdi+'/'+idAlmacen+'/'+nomDdi+'/'+nomAlmacen;
		$.fileDownload(url).done(function(respuesta) {
			loadding(false);	
			if (respuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, mensajeReporteError);
			} else {
				addInfoMessage(null, mensajeReporteExito);
			}
		}).fail(function (respuesta) {
			loadding(false);
			if (respuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, mensajeReporteError);
			} else if (respuesta == NOTIFICACION_VALIDACION) {
				addWarnMessage(null, mensajeReporteValidacion);
			}
		});
		
	});
	
	function cargarEstados() {
		e.preventDefault();
	
		var bootstrapValidator = frm_con_donaciones.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoDdi : $('#txt_cod_ddi').val(),
				codigoMes : $('#sel_mes').val(),
				codigoEstado : $('#sel_estado').val()
			};
			
			loadding(true);
		
			consultarAjax('GET', '/donaciones/registro-donaciones/listarDonaciones', params, function(respuesta) {
			
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarEstados(respuesta);
				}
				loadding(false);
			});
			
		}
		
	}
	
	$('#href_estado').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
			tbl_mnt_con_donaciones.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_con_donaciones.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idDonacion = listaDonacionesCache[index].idDonacion;
				codigo = codigo + idDonacion + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else if (indices.length > 1) {
			addWarnMessage(null, mensajeValidacionSeleccionarSoloUnRegistro);
		} else {
//			loadding(true);
			$('#h4_tit_no_alimentarios').html('Nuevo Documento');
			$('#frm_det_documentos').trigger('reset');
			
			$('#sel_estados_donacion').val(0);
			$('#divRegiones').hide();
			$('#hid_cod_documento').val('');
			$('#hid_cod_act_alfresco').val('');
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_sub_archivo').val(null);
			listarRegionDonacion(codigo, true);
			$('#hid_est_documento').val(codigo);
			$('#div_estado').modal('show');
			
		}
		
	});
	
	$('#btn_act_estado').click(function(e) {
		e.preventDefault();
	
		var msg = '';

		msg = 'Está seguro de actualizar el estado de la donación ?';

		
		$.SmartMessageBox({
			title : msg,
			content : '',
			buttons : '[Cancelar][Aceptar]'
		}, function(ButtonPressed) {
			if (ButtonPressed === 'Aceptar') {

				loadding(true);
				
				var params = { 
					idDonacion : $('#hid_est_documento').val(),
					idEstado : $('#sel_estados_donacion').val()
				};
		
				consultarAjax('POST', '/donaciones/registro-donaciones/actualizarEstadoDonacion', params, function(respuesta) {
					if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
						loadding(false);
						addErrorMessage(null, respuesta.mensajeRespuesta);
					} else {
						loadding(false);
						//listarDocumentoDonacion(true);
						addSuccessMessage(null, respuesta.mensajeRespuesta);							
					}
				});
				
			}	
		});
			
		
		
	});
	
	
	$('#sel_ddi').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			cargarAlmacen(codigo, null);
		} else {
			$('#sel_almacen').html('');
		}
	});
	
});

function listarRegionDonacion(codigo, indicador) {
	var params = { 
		idDonacion : codigo
	};			
	consultarAjaxSincrono('GET', '/donaciones/registro-donaciones/listarRegionDonacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleRegion(respuesta);
			if (indicador) {
				loadding(false);
			}
			if (respuesta != null && respuesta.length > 0) {
				$('#sel_tip_movimiento').prop('disabled', true);
			} else {
				$('#sel_tip_movimiento').prop('disabled', false);
			}
		}
	});
}

function listarDetalleRegion(respuesta) {

	tbl_regiones.dataTable().fnDestroy();
	
	tbl_regiones.dataTable({
		data : respuesta,
		columns : [  {	
			data : 'idDonacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreRegion'
		},{
			data : 'idRegion',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<button type="button" class="btn btn-danger" onclick="eliminarRegion('+data+')">'+
								'<i class="fa fa-trash-o"></i>'+
						   '</button>';	
				} else {
					return '';	
				}											
			}
		}],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaRegionesCache = respuesta;

}

function eliminarRegion(codigo) {
	var msg = '';

		msg = 'Está seguro de eliminar la región ?';

	var idDonacion = $('#hid_est_documento').val();
	$.SmartMessageBox({
		title : msg,
		content : '',
		buttons : '[Cancelar][Aceptar]'
	}, function(ButtonPressed) {
		if (ButtonPressed === 'Aceptar') {

			loadding(true);
			
			var params = { 
				idRegion : codigo,
				idDonacion : idDonacion
			};
	
			consultarAjax('POST', '/donaciones/registro-donaciones/eliminarRegionDonacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarRegionDonacion(idDonacion,true);
					addSuccessMessage(null, respuesta.mensajeRespuesta);							
				}
			});
			
		}	
	});
		
	
	
}

function inicializarDatos() {
	
  	$('#li_consultas_stock').addClass('active');
	$('#ul_donaciones').css('display', 'block');
	$('#ul_don_consultas').css('display', 'block');
	$('#li_consultas_stock').attr('class', 'active');
	$('#li_consultas_stock').closest('li').children('a').attr('href', '#');

	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		listarTablaStock();	
		
	}
}


function listarStockTodos(respuesta) {

	tbl_stock_todos.dataTable().fnDestroy();
	
	tbl_stock_todos.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'nombreCategoria',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreCategoria'
		}, {
			data : 'nombreProducto'
		}, {
			data : 'cantidad'
		}, {
			data : 'importeTotal'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		]
	});
	
	listaDonacionesCache = respuesta;

}

function listarStockDdi(respuesta) {

	tbl_stock_ddi.dataTable().fnDestroy();
	
	tbl_stock_ddi.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'nombreCategoria',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDdi'
		}, {
			data : 'nombreCategoria'
		}, {
			data : 'nombreProducto'
		}, {
			data : 'cantidad'
		}, {
			data : 'importeTotal'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		]
	});
	
	listaDonacionesCache = respuesta;

}

function listarStockAlmacen(respuesta) {

	tbl_stock_almacen.dataTable().fnDestroy();
	
	tbl_stock_almacen.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'nombreCategoria',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDdi'
		}, {
			data : 'nombreAlmacen'
		}, {
			data : 'nombreCategoria'
		}, {
			data : 'nombreProducto'
		}, {
			data : 'cantidad'
		}, {
			data : 'importeTotal'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		]
	});
	
	listaDonacionesCache = respuesta;

}

function listarEstados(respuesta) {

	tbl_det_estados.dataTable().fnDestroy();
	
	tbl_det_estados.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDonacion',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox"><i></i>'+
							'</label>';	
				} else {
					return '';	
				}											
			}
		}, {
			data : 'codigoAnio'
		}, {
			data : 'codigoDdi'
		}, {
			data : 'codigoDonacion'
		}, {
			data : 'fechaEmision'
		}, {
			data : 'nombrePais'
		}, {
			data : 'nombreDonante'
		}, {
			data : 'nombreEstado'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		]
	});
	
	listaDonacionesCache = respuesta;

}


function cargarAlmacen(codigo, codigoProvincia) {
	var params = { 
		icodigo : codigo
	};			
	loadding(true);
	consultarAjax('GET', '/donaciones/consulta/listarAlmacen', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="0">Todos</option>';
			$.each(respuesta, function(i, item) {
				options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
			});
			$('#sel_almacen').html(options);
			if (codigoProvincia != null) {
				$('#sel_almacen').val(codigoProvincia);       	
			}
		}
		loadding(false);
	});
}

function listarTablaStock(){

	$('#div_todos').hide();
	$('#div_ddi_todos').hide();
	$('#div_ddi_almacen').hide();
	var idDdi = $('#sel_ddi').val();
	var idAlmacen = $('#sel_almacen').val();
	cargarTitulo( idDdi, idAlmacen);
	
		var params = { 
			idDdi : $('#sel_ddi').val(),
			idAlmacen : $('#sel_almacen').val()
		};
		
		loadding(true);
	
		consultarAjax('GET', '/donaciones/consulta/listarStock', params, function(respuesta) {
		
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				if(idDdi=='0' && idAlmacen=='0'){
					listarStockTodos(respuesta);
					$('#div_todos').show();
				}else if(idDdi!='0' && idAlmacen=='0'){
					listarStockDdi(respuesta);
					$('#div_ddi_todos').show();
				}else if(idDdi!='0' && idAlmacen!='0'){
					listarStockAlmacen(respuesta);
					$('#div_ddi_almacen').show();
				}
				
			}
			
			loadding(false);
		});
		
//	}
}

function cargarTitulo(idDdi, idAlmacen){
	var d = new Date();

	var month = d.getMonth()+1;
	var day = d.getDate();

	var fecha = (day<10 ? '0' : '')+day + '/' +
	    (month<10 ? '0' : '') + month + '/' +
	     + d.getFullYear();
	if(idDdi=='0' && idAlmacen=='0'){
		$('#txt_titulo').html("Stock al "+fecha+" A nivel Nacional");
		$('#hid_titulo').val("Stock al "+fecha+" A nivel Nacional");
	}else if(idDdi!='0' && idAlmacen=='0'){
		$('#txt_titulo').html("Stock al "+fecha+" de la DDI: "+$("#sel_ddi option:selected").text());
		$('#hid_titulo').val("Stock al "+fecha+" de la DDI: "+$("#sel_ddi option:selected").text())
	}else if(idDdi!='0' && idAlmacen!='0'){
		$('#txt_titulo').html("Stock al "+fecha+" de la DDI: "+$("#sel_ddi option:selected").text()+" y Almacén: "+$("#sel_almacen option:selected").text());
		$('#hid_titulo').val("Stock al "+fecha+" de la DDI: "+$("#sel_ddi option:selected").text()+" y Almacén: "+$("#sel_almacen option:selected").text())
	}
		
	
}
