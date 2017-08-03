//var listaRacionCache = new Object();
//var listaProductoCache = new Object();
var frm_dat_generales = $('#frm_dat_generales');
//var tbl_mnt_racion_ope = $('#tbl_mnt_racion_ope'); 
// 
var frm_productos = $('#frm_productos');
var tbl_mnt_productos = $('#tbl_mnt_productos'); 

var frm_documento = $('#frm_documento');


$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();

	$('#btn_grabar_dat_gen').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_ped_compra').val();
			var params = {
//				tipoRacion : $('#txt_num_pedido').val(),
					codPedido :pedido.codPedido,
					fecPedido : $('#txt_fecha_pedido').val(),
					codEstado : $('#sel_estado').val(),  
					codPedidoPor : $('#sel_pedidoPor').val(),
					descripcion : $('#txt_descripcion').val(),
					dee : $('#sel_dee').val()
				
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/pedido/grabarPedido', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else { 
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
//						racion.idRacionOpe=respuesta.idRacionOpe;
//						$('#div_tabla_prod').show();
//
//						$('#btn_grabar_racion').attr("disabled", true); 

						
					}
					
				}
				loadding(false);
			});			
		}
		
	});


	
	$('#btn_gra_prod').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_productos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_producto').val();
			var params = {
					idDetaRacion : codigo,
					fkIdProducto : $('#sel_producto').val(),
					pesoUnitarioPres : formatMonto($('#txt_uni_pres').val()),
					cantRacionKg : formatMonto($('#txt_gr_aprox').val()),
					idRacion : racion.idRacionOpe
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/racion/grabarProducto', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						$('#div_det_productos').modal('hide');
						llenarProductos(racion.idRacionOpe);
				}
				loadding(false);
			});			
		}
	});
	

	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/racion/inicio/1';
		$(location).attr('href', url);
		
	});
	

	$('#href_nuevo_prod').click(function(e) {
		e.preventDefault();

		$('#h4_tit_productos').html('Productos');
		frm_productos.trigger('reset');
	
		$('#hid_cod_producto').val('');
		$('#div_det_productos').modal('show');
		
	});
	
	
	$('#href_editar_prod').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaProductoCache[indices[0]];
			$('#hid_cod_producto').val(obj.idDetaRacion);
			$('#sel_producto').val(obj.fkIdProducto);
			$('#txt_uni_pres').val(obj.pesoUnitarioPres);
			$('#txt_gr_aprox').val(obj.cantRacionKg); 
			
			$('#div_det_productos').modal('show');
		}
		
	});


	
	$('#href_exp_excel_prod').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_productos > tbody > tr').length;
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
		
		var idRacionOpe = racion.idRacionOpe;
		var url = VAR_CONTEXT + '/programacion-bath/racion/exportarExcelProducto/';
		url += verificaParametro(idRacionOpe);
		
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

	
	$('#href_eliminar_prod').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idProducto = listaProductoCache[index].idDetaRacion;
				codigo = codigo + idProducto + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					
					var params = { 
							arrIdProducto : codigo
					};
			
					consultarAjax('POST', '/programacion-bath/racion/eliminarProductoRacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							llenarProductos(racion.idRacionOpe);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});

//	
//	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
//		e.preventDefault();
//		
//		var id = $(this).attr('id');
//		var name = $(this).attr('name');
//		if (!esnulo(id) && !esnulo(name)) {
//			descargarDocumento(id, name);
//		} else {
//			addInfoMessage(null, 'No dispone de documento adjunto asociado.');
//		}
//		
//	});
	
});

function inicializarDatos() {
	
	$('#div_tabla_prod').hide();//ocultamos la tabla

	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		//inicializar los valores
		$('#txt_num_pedido').val(pedido.codPedidoConcate);
		$('#txt_fecha_pedido').val(pedido.fecPedido);
		
		$('#txt_desc_pedido').val(pedido.codPedidoConcate);
		$('#txt_desc_pedido_doc').val(pedido.codPedidoConcate);
		
		if (!esnulo(pedido.codPedido)) {
			

			
		} else {
			

		}
	}
	
}

//
//function llenarProductos(codigo) {
//		
//			var params = { 
//					idRacion : codigo
//			};
//			
//			loadding(true);
//			
//			consultarAjax('GET', '/programacion-bath/racion/listarProductos', params, function(respuesta) {
//				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//					addErrorMessage(null, respuesta.mensajeRespuesta);
//				} else {
//					addSuccessMessage(null, respuesta.mensajeRespuesta);
//					 listarProductos(respuesta);
//				}
//				loadding(false);
//			});
//	
//}
//
//
//function listarProductos(respuesta) {
//
//	tbl_mnt_productos.dataTable().fnDestroy();
//	tbl_mnt_productos.dataTable({
//			data : respuesta,
//			columns : [ {
//					data : 'fkIdProducto',
//					sClass : 'opc-center',
//					render: function(data, type, row) {
//						if (data != null) {
//							return '<label class="checkbox">'+
//										'<input type="checkbox"><i></i>'+
//									'</label>';	
//						} else {
//							return '';	
//						}											
//					}	
//				}, {	
//					data : 'fkIdProducto',
//					render : function(data, type, full, meta) {
//						var row = meta.row + 1;
//						return row;											
//					}
//				}, {data : 'nombProducto'}, 
//				{data : 'pesoUnitarioPres'}, 
//				{data : 'cantRacionKg'}
//			],
//			language : {
//				'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
//			},
//			bFilter : false,
//			paging : false,
//			ordering : false,
//			info : false,
//			iDisplayLength : 15,
//			aLengthMenu : [
//				[15, 50, 100],
//				[15, 50, 100]
//			],
//			columnDefs : [
//				{ width : '55%', targets : 2 },
//				{ width : '15%', targets : 3 },
//				{ width : '15%', targets : 4 }
//			]
//		});
//		
//	listaProductoCache = respuesta;
//
//	}
//
//
//function listarDetalleRequerimiento(respuesta) {
//
//	tbl_mnt_racion_ope.dataTable().fnDestroy();
//	tbl_mnt_racion_ope.dataTable({
//			data : respuesta,
//			columns : [ {
//					data : 'idEmergencia',
//					sClass : 'opc-center',
//					render: function(data, type, row) {
//						if (data != null) {
//							return '<label class="checkbox">'+
//										'<input type="checkbox"><i></i>'+
//									'</label>';	
//						} else {
//							return '';	
//						}											
//					}	
//				}, {	
//					data : 'idEmergencia',
//					render : function(data, type, full, meta) {
//						var row = meta.row + 1;
//						return row;											
//					}
//				}, {data : 'desDepartamento'}, 
//				{data : 'desProvincia'}, 
//				{data : 'desDistrito'}, 
//				{data : 'idEmergencia'},
//				{data : 'poblacionINEI'},
//				{data : 'famAfectado'},
//				{data : 'famDamnificado'},
//				{data : 'totalFam'},
//				{data : 'persoAfectado'},
//				{data : 'persoDamnificado'},
//				{data : 'totalPerso'},
//				{data : 'famAfectadoReal'},
//				{data : 'famDamnificadoReal'},
//				{data : 'totalFamReal'},
//				{data : 'persoAfectadoReal'}, 
//				{data : 'persoDamnificadoReal'}, 
//				{data : 'totalPersoReal'}
//			],
//			language : {
//				'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
//			},
//			bFilter : false,
//			paging : false,
//			ordering : false,
//			info : false,
//			iDisplayLength : 15,
//			aLengthMenu : [
//				[15, 50, 100],
//				[15, 50, 100]
//			],
//			columnDefs : [
//				{ width : '15%', targets : 3 },
//				{ width : '15%', targets : 4 },
//				{ width : '15%', targets : 5 }
//			]
//		});
//		
//	listaRacionCache = respuesta;
//
//	}
