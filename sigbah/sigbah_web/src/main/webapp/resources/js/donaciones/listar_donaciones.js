var listaDonacionesCache = new Object();
var listaRegionesCache = new Object();

var tbl_mnt_con_donaciones = $('#tbl_mnt_con_donaciones');
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
	
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();
		
		loadding(true);					
		var url = VAR_CONTEXT + '/donaciones/registro-donaciones/mantenimientoDonaciones/0';
		
	
		$(location).attr('href', url);
		
	});
	
	$('#btn_buscar').click(function(e) {
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
					listarControlCalidad(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
			var estado = ''
			tbl_mnt_con_donaciones.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_con_donaciones.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				 estado = listaDonacionesCache[index].idEstado;
				var idDonacion = listaDonacionesCache[index].idDonacion;
				codigo = codigo + idDonacion + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			console.log("ESTADO: "+estado);
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);
			var url = VAR_CONTEXT + '/donaciones/registro-donaciones/mantenimientoDonaciones/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	
	$('#sel_estados_donacion').change(function() {
		var codigo = $(this).val();		
			console.log("ENTRdO: "+codigo);
			if (codigo ==='2') {
				$('#divRegiones').show();
			} else {
				$('#divRegiones').hide();
			}			

	});
	
	$('#sel_region_donacion').change(function() {
		var codigo = $(this).val();		
		var idDonacion = $('#hid_est_documento').val();
		if (!esnulo(codigo)) {						
			var params = { 
				idRegion : codigo,
				idDonacion : idDonacion
			};			
			loadding(true);
			consultarAjax('POST', '/donaciones/registro-donaciones/insertarRegionXDonacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarRegionDonacion(idDonacion, true);
				}
				loadding(false);
				//frm_nue_estado.bootstrapValidator('revalidateField', 'sel_region_donacion');
			});
		} else {
			//$('#sel_personal_oficina').html('');
			//frm_nue_estado.bootstrapValidator('revalidateField', 'sel_region_donacion');
		}
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
			addWarnMessage(null, 'No se encuentran registros para generar el reporte.');
			return;
		}

		loadding(true);
		
		var codigoAnio = $('#sel_anio').val();
		var codigoDdi = $('#txt_cod_ddi').val();
		var codigoMes = $('#sel_mes').val();
		var codigoEstado = $('#sel_estado').val();
		var url = VAR_CONTEXT + '/donaciones/registro-donaciones/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametro(codigoDdi) + '/';
		url += verificaParametro(codigoMes) + '/';
		url += verificaParametro(codigoEstado);
		
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
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);
			var url = VAR_CONTEXT + '/donaciones/registro-donaciones/exportarPdf/'+codigo;
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
		}
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
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
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
		info : true
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
	
  	$('#li_donaciones').addClass('active');
	$('#ul_donaciones').css('display', 'block');
	$('#li_reg_donaciones').attr('class', 'active');
	$('#li_reg_donaciones').closest('li').children('a').attr('href', '#');
	$('#divRegiones').hide();
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
	listarTablaDonaciones();
	
		
	}
}

function listarTablaDonaciones() {

	console.log($('#sel_anio').val());
	console.log($('#txt_cod_ddi').val());
	console.log($('#sel_mes').val());
	console.log($('#sel_estado').val());
	
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
			listarControlCalidad(respuesta);
		}
		loadding(false);
	});
		
	
}

function listarControlCalidad(respuesta) {

	tbl_mnt_con_donaciones.dataTable().fnDestroy();
	
	tbl_mnt_con_donaciones.dataTable({
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
			data : 'codigoMes'
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
		paging : true,
		ordering : false,
		info : true,
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
		paging : true,
		ordering : false,
		info : true,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		]
	});
	
	listaDonacionesCache = respuesta;

}

