var listaAlimentariosCache = new Object();
var listaNoAlimentariosCache = new Object();
var listaDocumentosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_alimentarios = $('#tbl_det_alimentarios');
var frm_det_alimentarios = $('#frm_det_alimentarios');

var tbl_det_no_alimentarios = $('#tbl_det_no_alimentarios');
var frm_det_no_alimentarios = $('#frm_det_no_alimentarios');

var tbl_det_documentos = $('#tbl_det_documentos');
var frm_det_documentos = $('#frm_det_documentos');

$(document).ready(function() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#li_con_calidad').addClass('active');
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#txt_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		if (!esnulo($(this).val())) {
			frm_dat_generales.bootstrapValidator('revalidateField', 'txt_fecha');
		}		
	});
	
	$('#sel_tip_control').change(function() {
		var val_tip_control = $(this).val();		
		if (!esnulo(val_tip_control)) {
//			frm_dat_generales.data('bootstrapValidator').resetForm();
			if (val_tip_control == '3' || // Ingreso por Transferencias de Almacén
					val_tip_control == '6') { // Salidas por Transferencias a Almacén
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_ori_almacen');
			} else if (val_tip_control == '1' || // Ingreso por Compra de productos
						val_tip_control == '5') { // Ingreso por Donación
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_proveedor');
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_representante');
				
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_emp_transporte');
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
				frm_dat_generales.bootstrapValidator('revalidateField', 'txt_nro_placa');
			}
		}
	});
	
	$('#sel_emp_transporte').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			var params = { 
				icodigo : codigo
			};			
			loadding(true);
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarChofer', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
			        });
			        $('#sel_chofer').html(options);
				}
				loadding(false);
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
			});
		} else {
			$('#sel_chofer').html('');
		}
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
//		frm_dat_generales.data('bootstrapValidator').resetForm();
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			
			
			/*
			var params = { 
				cod_anio : $('#sel_anio').val(),
				cod_ddi : $('#sel_ddi').val(),
				cod_almacen : $('#sel_almacen').val()
			};
			
			loadding(true);
			
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					addSuccessMessage(null, respuesta.mensajeRespuesta);
					
				}
				loadding(false);
			});
			*/
			
			addSuccessMessage(null, 'ok');
			
		}
		
	});
	
	$('#btn_salir').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/control-calidad/inicio';
		$(location).attr('href', url);
		
	});
	
	$('#href_ali_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_alimentarios').html('Nuevo Producto');
		frm_det_alimentarios.trigger('reset');
		$('#hid_cod_producto').val('');
		$('#div_det_alimentarios').modal('show');
		
	});
	
	$('#href_ali_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_alimentarios.dataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_alimentarios.dataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaAlimentariosCache[indices[0]];
			
			$('#h4_tit_alimentarios').html('Actualizar Producto');
			frm_det_alimentarios.trigger('reset');
			
			$('#hid_cod_producto').val(obj.cod_producto);
			
			$('#sel_producto').val(obj.cod_producto);
			$('#sel_producto').select2().trigger('change');
			
			$('#sel_uni_medida').val(obj.cod_producto);
			$('#txt_fec_vencimiento').val(obj.cod_producto);
			$('#txt_can_lote').val(obj.cod_producto);
			$('#txt_can_muestra').val(obj.cod_producto);
			$('#sel_primario').val(obj.cod_producto);
			$('#sel_olor').val(obj.cod_producto);
			$('#sel_textura').val(obj.cod_producto);
			$('#sel_secundario').val(obj.cod_producto);
			$('#sel_color').val(obj.cod_producto);
			$('#sel_sabor').val(obj.cod_producto);
			
			$('#div_det_alimentarios').modal('show');
		}
		
	});
	
	$('#href_ali_eliminar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
		tbl_det_alimentarios.dataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_alimentarios.dataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var cod_producto = listaControlCalidadCache[index].cod_producto;
				codigo = codigo + cod_producto + '_';
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
						cod_producto : codigo
					};
			
					consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							
							addSuccessMessage(null, respuesta.mensajeRespuesta);
							
						}
						loadding(false);
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_gra_alimentario').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_alimentarios.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = { 
				cod_producto : $('#hid_cod_producto').val(),
				cod_ddi : $('#sel_producto').val(),
				cod_ddi : $('#sel_uni_medida').val(),
				cod_ddi : $('#txt_fec_vencimiento').val(),
				cod_ddi : $('#txt_can_lote').val(),
				cod_ddi : $('#txt_can_muestra').val(),
				cod_ddi : $('#sel_primario').val(),
				cod_ddi : $('#sel_olor').val(),
				cod_ddi : $('#sel_textura').val(),
				cod_ddi : $('#sel_secundario').val(),
				cod_ddi : $('#sel_color').val(),
				cod_ddi : $('#sel_sabor').val()
			};
			
			loadding(true);
			
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					addSuccessMessage(null, respuesta.mensajeRespuesta);
					
				}
				loadding(false);
			});
			
		}
		
	});
	
	$('#href_no_ali_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_no_alimentarios').html('Nuevo Producto');
		frm_det_no_alimentarios.trigger('reset');
		$('#hid_cod_no_producto').val('');
		$('#div_det_no_alimentarios').modal('show');
		
	});
	
	$('#href_no_ali_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_no_alimentarios.dataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_no_alimentarios.dataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaNoAlimentariosCache[indices[0]];
			
			$('#h4_tit_no_alimentarios').html('Actualizar Producto');
			frm_det_no_alimentarios.trigger('reset');
			
			$('#hid_cod_no_producto').val(obj.cod_producto);
			
			$('#sel_no_producto').val(obj.cod_producto);
			$('#sel_no_producto').select2().trigger('change');
			
			$('#sel_no_uni_medida').val(obj.cod_producto);
			$('#txt_no_fec_vencimiento').val(obj.cod_producto);
			$('#txt_no_can_lote').val(obj.cod_producto);
			$('#txt_no_can_muestra').val(obj.cod_producto);
			$('#sel_no_primario').val(obj.cod_producto);
			$('#sel_no_tecnicas').val(obj.cod_producto);
			$('#sel_no_secundario').val(obj.cod_producto);
			$('#sel_no_conformidad').val(obj.cod_producto);
			
			$('#div_det_no_alimentarios').modal('show');
		}
		
	});
	
	$('#href_no_ali_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = ''
		tbl_det_no_alimentarios.dataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_no_alimentarios.dataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var cod_producto = listaNoAlimentariosCache[index].cod_producto;
				codigo = codigo + cod_producto + '_';
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
						cod_producto : codigo
					};
			
					consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							
							addSuccessMessage(null, respuesta.mensajeRespuesta);
							
						}
						loadding(false);
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_gra_no_alimentario').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_no_alimentarios.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = { 
				cod_producto : $('#hid_cod_no_producto').val(),
				cod_ddi : $('#sel_no_producto').val(),
				cod_ddi : $('#sel_no_uni_medida').val(),
				cod_ddi : $('#txt_no_fec_vencimiento').val(),
				cod_ddi : $('#txt_no_can_lote').val(),
				cod_ddi : $('#txt_no_can_muestra').val(),
				cod_ddi : $('#sel_no_primario').val(),
				cod_ddi : $('#sel_no_tecnicas').val(),
				cod_ddi : $('#sel_no_secundario').val(),
				cod_ddi : $('#sel_no_conformidad').val()
			};
			
			loadding(true);
			
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					addSuccessMessage(null, respuesta.mensajeRespuesta);
					
				}
				loadding(false);
			});
			
		}
		
	});
	
	$('#href_doc_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
		$('#frm_det_documentos').trigger('reset');
		$('#hid_cod_documento').val('');
		$('#div_det_documentos').modal('show');
		
	});
	
	$('#href_doc_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_documentos.dataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.dataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaDocumentosCache[indices[0]];
			
			$('#h4_tit_documentos').html('Actualizar Documento');
			$('#frm_det_documentos').trigger('reset');
			
			$('#hid_cod_documento').val(obj.cod_producto);
			
			$('#sel_tip_producto').val(obj.cod_producto);
			$('#txt_nro_documento').val(obj.cod_producto);
			$('#txt_doc_fecha').val(obj.cod_producto);
			$('#txt_sub_archivo').val(obj.cod_producto);
			
			$('#div_det_documentos').modal('show');
		}
		
	});
	
	$('#href_doc_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = ''
		tbl_det_documentos.dataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.dataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var cod_producto = listaDocumentosCache[index].cod_producto;
				codigo = codigo + cod_producto + '_';
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
						cod_producto : codigo
					};
			
					consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							
							addSuccessMessage(null, respuesta.mensajeRespuesta);
							
						}
						loadding(false);
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_gra_documento').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = { 
				cod_producto : $('#hid_cod_documento').val(),
				cod_ddi : $('#sel_tip_producto').val(),
				cod_ddi : $('#txt_nro_documento').val(),
				cod_ddi : $('#txt_doc_fecha').val(),
				cod_ddi : $('#txt_sub_archivo').val()
			};
			
			loadding(true);
			
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					addSuccessMessage(null, respuesta.mensajeRespuesta);
					
				}
				loadding(false);
			});
			
		}
		
	});
	
});

function inicializarDatos() {
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		if (!esnulo(controlCalidad.idControlCalidad)) {
			
			
			
		} else {
			
			$('#txt_nro_con_calidad').val(controlCalidad.nroControlCalidad);
			
			$('#li_alimentarios').addClass('disabled');
			$('#li_no_alimentarios').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_con_calidad li.disabled a').removeAttr('data-toggle');

			$('#txt_anio').val(controlCalidad.codigoAnio);
			$('#txt_ddi').val(controlCalidad.nombreDdi);
			$('#txt_almacen').val(controlCalidad.nombreAlmacen);
			
		}
		
	}
	
	$('#sel_nro_ord_compra').select2().trigger('change');
	
}
