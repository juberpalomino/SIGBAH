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
	$('#li_con_calidad').attr('class', 'active');
	$('#li_con_calidad').closest('li').children('a').attr('href', '#');
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#txt_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_dat_generales.bootstrapValidator('revalidateField', 'txt_fecha');	
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
	
	$('#sel_proveedor').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_representante').val(arr[1]);
			} else {
				$('#txt_representante').val('');
			}			
		} else {
			$('#txt_representante').val('');
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
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_con_calidad').val();
			var tipoBien = $('input[name="rb_tip_bien"]:checked').val();
			var idProveedor = null;
			var val_proveedor = $('#sel_proveedor').val();
			if (!esnulo(val_proveedor)) {
				var arr = val_proveedor.split('_');
				idProveedor = arr[0];
			}
			
			var params = {
				idControlCalidad : codigo,	
				codigoAnio : $('#txt_anio').val(),
				codigoMes : controlCalidad.codigoMes,
				codigoDdi : controlCalidad.codigoDdi,
				idAlmacen : controlCalidad.idAlmacen,
				codigoAlmacen : controlCalidad.codigoAlmacen,
				fechaEmision : $('#txt_fecha').val(),
				idEstado : $('#sel_estado').val(),
				nroOrdenCompra : $('#sel_nro_ord_compra').val(),
				idTipoControl : $('#sel_tip_control').val(),
				idAlmacenOrigen : $('#sel_ori_almacen').val(),
				idEncargado : $('#sel_ori_en_almacen').val(),
				idInspector : $('#sel_inspector').val(),
				idProveedor : idProveedor,
				idEmpresaTransporte : $('#sel_emp_transporte').val(),
				idChofer : $('#sel_chofer').val(),
				nroPlaca : $('#txt_nro_placa').val(),
				flagTipoBien : tipoBien,
				conclusiones : $('#txt_conclusiones').val(),
				recomendaciones : $('#txt_recomendaciones').val()				
			};
			
			loadding(true);
			
			consultarAjax('GET', '/gestion-almacenes/control-calidad/grabarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_con_calidad').val(respuesta.idControlCalidad);
						$('#txt_nro_con_calidad').val(respuesta.nroControlCalidad);
						
						if (tipoBien == '1') {					
							$('#li_alimentarios').attr('class', '');
							$('#li_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						} else {							
							$('#li_no_alimentarios').attr('class', '');
							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						}
						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');

						addSuccessMessage(null, 'Se genero el N° Control de Calidad: '+respuesta.nroControlCalidad);
						
					}
					
				}
				loadding(false);
			});			
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
		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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
		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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
		tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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
		tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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
		
		$('#txt_nro_con_calidad').val(controlCalidad.nroControlCalidad);
		$('#txt_anio').val(controlCalidad.codigoAnio);
		$('#txt_ddi').val(controlCalidad.nombreDdi);
		$('#txt_almacen').val(controlCalidad.nombreAlmacen);
		
		if (!esnulo(controlCalidad.idControlCalidad)) {
			
			$('#hid_cod_con_calidad').val(controlCalidad.idControlCalidad);		
			if (controlCalidad.flagTipoBien == '1') {
				$('#li_no_alimentarios').addClass('disabled');
				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			} else {
				$('#li_alimentarios').addClass('disabled');
				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			}
			
			$('#txt_fecha').val(controlCalidad.fechaEmision);
			$('#sel_estado').val(controlCalidad.idEstado);
			$('#sel_nro_ord_compra').val(controlCalidad.nroOrdenCompra);
			$('#sel_tip_control').val(controlCalidad.idTipoControl);
			$('#sel_ori_almacen').val(controlCalidad.idAlmacenOrigen);
			$('#sel_ori_en_almacen').val(controlCalidad.idEncargado);
			$('#sel_inspector').val(controlCalidad.idInspector);			
			var val_idProveedor = controlCalidad.provRep;
			$('#sel_proveedor').val(val_idProveedor);
			var arr = val_idProveedor.split('_');
			if (arr.length > 1) {
				$('#txt_representante').val(arr[1]);
			}
			$('#sel_emp_transporte').val(controlCalidad.idEmpresaTransporte);
			$('#sel_chofer').val(controlCalidad.idChofer);
			$('#txt_nro_placa').val(controlCalidad.nroPlaca);
			$('input[name=rb_tip_bien][value="'+controlCalidad.flagTipoBien+'"]').prop('checked', true);
			$('#txt_conclusiones').val(controlCalidad.conclusiones);
			$('#txt_recomendaciones').val(controlCalidad.recomendaciones);
			
			// listarDetalleAlimentarios(listaAlimentarios);
//			listarDetalleAlimentarios(new Object());
			
			var paramsProducto = { 
				idControlCalidad : 1,
				flagTipoProducto : '1'
			};
			
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarProductoControlCalidad', paramsProducto, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarDetalleAlimentarios(respuesta);
				}
			});
			
		} else {
			
			$('#li_alimentarios').addClass('disabled');
			$('#li_no_alimentarios').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_con_calidad li.disabled a').removeAttr('data-toggle');
			
			listarDetalleAlimentarios(new Object());

		}

	}
	
	$('#sel_nro_ord_compra').select2().trigger('change');
	
}

function listarDetalleAlimentarios(respuesta) {

	tbl_det_alimentarios.dataTable().fnDestroy();
	
	tbl_det_alimentarios.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleControlCalidad',
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
			data : 'nombreProducto'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'cantidadLote'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nombreUnidad'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaAlimentariosCache = respuesta;

}


