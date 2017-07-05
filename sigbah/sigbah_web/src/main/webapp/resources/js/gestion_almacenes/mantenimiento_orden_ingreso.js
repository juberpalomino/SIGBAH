var listaProductosCache = new Object();
var listaDocumentosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_productos = $('#tbl_det_productos');
var frm_det_productos = $('#frm_det_productos');

var tbl_det_documentos = $('#tbl_det_documentos');
var frm_det_documentos = $('#frm_det_documentos');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#txt_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#txt_fec_vencimiento').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_det_productos.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#txt_doc_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_det_documentos.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#sel_nro_ord_compra').change(function() {
		var codigo = $(this).val();
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_det_ord_compra').val(arr[1]);
		} else {
			$('#txt_det_ord_compra').val('');
		}
	});
	
	$('#sel_tip_movimiento').change(function() {
		var val_tip_movimiento = $(this).val();		
		if (!esnulo(val_tip_movimiento)) {
			
			cargarTipoMovimiento(val_tip_movimiento);
			

			
//			if (val_tip_control == '3' || // Ingreso por Transferencias de Almacén
//					val_tip_control == '6') { // Salidas por Transferencias a Almacén
//				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_ori_almacen');
//			} else if (val_tip_control == '1' || // Ingreso por Compra de productos
//						val_tip_control == '5') { // Ingreso por Donación
//				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_proveedor');
//				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_representante');
//				
//				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_emp_transporte');
//				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
//				frm_dat_generales.bootstrapValidator('revalidateField', 'txt_nro_placa');
//			}
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
	
	$('#sel_med_transporte').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			var params = { 
				icodigoParam2 : codigo
			};			
			loadding(true);
			consultarAjax('GET', '/gestion-almacenes/orden-ingreso/listarEmpresaTransporte', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
			        });
			        $('#sel_emp_transporte').html(options);
				}
				loadding(false);
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
			});
		} else {
			$('#sel_emp_transporte').html('');
		}
	});
	
	$('#sel_emp_transporte').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			var params = { 
				icodigo : codigo
			};			
			loadding(true);
			consultarAjax('GET', '/gestion-almacenes/orden-ingreso/listarChofer', params, function(respuesta) {
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
			var codigo = $('#hid_cod_con_calidad').val();
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
				codigoMes : ordenIngreso.codigoMes,
				codigoDdi : ordenIngreso.codigoDdi,
				idDdi : ordenIngreso.idDdi, 
				idAlmacen : ordenIngreso.idAlmacen,
				codigoAlmacen : ordenIngreso.codigoAlmacen,
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
			
			consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_con_calidad').val(respuesta.idControlCalidad);
						$('#txt_nro_ord_ingreso').val(respuesta.nroControlCalidad);
						
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
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/orden-ingreso/inicio';
		$(location).attr('href', url);
		
	});
	
	$('#href_ali_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_alimentarios').html('Nuevo Producto');
		frm_det_productos.trigger('reset');
		$('#txt_fec_vencimiento').datepicker('setDate', new Date());
		
		$('#sel_producto').select2().trigger('change');
		$('#sel_producto').select2({
			  dropdownParent: $('#div_pro_det_alimentarios')
		});
		
		$('#hid_cod_producto').val('');
		$('#div_det_alimentarios').modal('show');
		
	});
	
	$('#href_ali_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaProductosCache[indices[0]];
			
			$('#h4_tit_alimentarios').html('Actualizar Producto');
			frm_det_productos.trigger('reset');
			
			$('#hid_cod_producto').val(obj.idDetalleControlCalidad);
			
			$('#sel_producto').val(obj.idProducto+'_'+obj.nombreUnidad);

			$('#sel_producto').select2().trigger('change');
			$('#sel_producto').select2({
				  dropdownParent: $('#div_pro_det_alimentarios')
			});
			
			$('#sel_uni_medida').val(obj.nombreUnidad);
			$('#txt_fec_vencimiento').val(obj.fechaVencimiento);
			$('#txt_can_lote').val(obj.cantidadLote);
			$('#txt_can_muestra').val(obj.cantidadMuestra);
			$('#sel_primario').val(obj.primario);
			$('#sel_olor').val(obj.parOlor);
			$('#sel_textura').val(obj.parTextura);
			$('#sel_secundario').val(obj.secundario);
			$('#sel_color').val(obj.parColor);
			$('#sel_sabor').val(obj.parSabor);
			
			$('#div_det_alimentarios').modal('show');
		}
		
	});
	
	$('#href_ali_eliminar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
		tbl_det_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleControlCalidad = listaProductosCache[index].idDetalleControlCalidad;
				codigo = codigo + idDetalleControlCalidad + '_';
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
						arrIdDetalleControlCalidad : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/orden-ingreso/eliminarProductoControlCalidad', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoControlCalidad(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_gra_alimentario').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_productos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var idProducto = null;
			var val_producto = $('#sel_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}			
			var params = { 
				idDetalleControlCalidad : $('#hid_cod_producto').val(),
				idControlCalidad : $('#hid_cod_con_calidad').val(),
				idProducto : idProducto,
				fechaVencimiento : $('#txt_fec_vencimiento').val(),
				cantidadLote : formatMonto($('#txt_can_lote').val()),
				cantidadMuestra : formatMonto($('#txt_can_muestra').val()),
				primario : $('#sel_primario').val(),
				parOlor : $('#sel_olor').val(),
				parTextura : $('#sel_textura').val(),
				secundario : $('#sel_secundario').val(),
				parColor : $('#sel_color').val(),
				parSabor : $('#sel_sabor').val()
			};

			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarProductoControlCalidad', params, function(respuesta) {
				$('#div_det_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoControlCalidad(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_productos.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_alimentario').click(function(e) {
		e.preventDefault();
		frm_det_productos.data('bootstrapValidator').resetForm();
	});
	
	$('#sel_producto').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_uni_medida').val(arr[1]);
			} else {
				$('#txt_uni_medida').val('');
			}			
		} else {
			$('#txt_uni_medida').val('');
		}
	});
	
	$('#href_no_ali_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_no_alimentarios').html('Nuevo Producto');
		frm_det_no_alimentarios.trigger('reset');
		$('#txt_no_fec_vencimiento').datepicker('setDate', new Date());
		
		$('#sel_no_producto').select2().trigger('change');
		$('#sel_no_producto').select2({
			  dropdownParent: $('#div_pro_det_no_alimentarios')
		});
		
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
			
			$('#hid_cod_no_producto').val(obj.idDetalleControlCalidad);
			
			$('#sel_no_producto').val(obj.idProducto+'_'+obj.nombreUnidad);
			
			$('#sel_no_producto').select2().trigger('change');
			$('#sel_no_producto').select2({
				  dropdownParent: $('#div_pro_det_no_alimentarios')
			});
			
			$('#sel_no_uni_medida').val(obj.nombreUnidad);
			$('#txt_no_fec_vencimiento').val(obj.fechaVencimiento);
			$('#txt_no_can_lote').val(obj.cantidadLote);
			$('#txt_no_can_muestra').val(obj.cantidadMuestra);
			$('#sel_no_primario').val(obj.primario);
			$('#sel_no_tecnicas').val(obj.flagEspecTecnicas);
			$('#sel_no_secundario').val(obj.secundario);
			$('#sel_no_conformidad').val(obj.flagConforProducto);
			
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
				var idDetalleControlCalidad = listaNoAlimentariosCache[index].idDetalleControlCalidad;
				codigo = codigo + idDetalleControlCalidad + '_';
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
						arrIdDetalleControlCalidad : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/orden-ingreso/eliminarProductoControlCalidad', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoControlCalidad(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);
						}		
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
			var idProducto = null;
			var val_producto = $('#sel_no_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}
			var params = { 
				idDetalleControlCalidad : $('#hid_cod_no_producto').val(),
				idControlCalidad : $('#hid_cod_con_calidad').val(),
				idProducto : idProducto,
				fechaVencimiento : $('#txt_no_fec_vencimiento').val(),
				cantidadLote : formatMonto($('#txt_no_can_lote').val()),
				cantidadMuestra : formatMonto($('#txt_no_can_muestra').val()),
				primario : $('#sel_no_primario').val(),
				flagEspecTecnicas : $('#sel_no_tecnicas').val(),
				secundario : $('#sel_no_secundario').val(),
				flagConforProducto : $('#sel_no_conformidad').val()
			};
			
			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarProductoControlCalidad', params, function(respuesta) {
				$('#div_det_no_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoControlCalidad(true);
					addSuccessMessage(null, respuesta.mensajeRespuesta);	
				}
				frm_det_no_alimentarios.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_no_alimentario').click(function(e) {
		e.preventDefault();
		frm_det_no_alimentarios.data('bootstrapValidator').resetForm();
	});
	
	$('#sel_no_producto').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_no_uni_medida').val(arr[1]);
			} else {
				$('#txt_no_uni_medida').val('');
			}			
		} else {
			$('#txt_no_uni_medida').val('');
		}
	});
	
	$('#href_doc_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
		$('#frm_det_documentos').trigger('reset');
		
		$('#txt_doc_fecha').datepicker('setDate', new Date());
		$('#hid_cod_documento').val('');
		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#txt_sub_archivo').val(null);
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
			
			$('#hid_cod_documento').val(obj.idDocumentoControlCalidad);			
			$('#sel_tip_producto').val(obj.idTipoDocumento);
			$('#txt_nro_documento').val(obj.nroDocumento);
			$('#txt_doc_fecha').val(obj.fechaDocumento);
			$('#hid_cod_act_alfresco').val(obj.codigoArchivoAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
			$('#txt_sub_archivo').val(null);
			
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
				var idDocumentoControlCalidad = listaDocumentosCache[index].idDocumentoControlCalidad;
				codigo = codigo + idDocumentoControlCalidad + '_';
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
						arrIdDocumentoControlCalidad : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/orden-ingreso/eliminarDocumentoControlCalidad', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoControlCalidad(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
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
			loadding(true);
			
			var params = { 
				idDocumentoControlCalidad : $('#hid_cod_documento').val(),
				idControlCalidad : $('#hid_cod_con_calidad').val(),
				idTipoDocumento : $('#sel_tip_producto').val(),
				nroDocumento : $('#txt_nro_documento').val(),
				fechaDocumento : $('#txt_doc_fecha').val(),
				nombreArchivo : $('#txt_lee_sub_archivo').val()
			};
			
			var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
			if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
				var file_name = $('#txt_sub_archivo').val();
				var file_data = null;
				if (!esnulo(file_name) && typeof file_name !== 'undefined') {
					file_data = $('#txt_sub_archivo').prop('files')[0];
				}
				
				var formData = new FormData();
				formData.append('file_doc', file_data);
				// Carpeta contenedor, ubicado en config.properties
		    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.almacen');
		    	
				consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
					if (resArchivo == NOTIFICACION_ERROR) {
						$('#div_det_documentos').modal('hide');
						frm_det_documentos.data('bootstrapValidator').resetForm();
						loadding(false);
						addErrorMessage(null, mensajeCargaArchivoError);						
					} else {
						
						params.codigoArchivoAlfresco = resArchivo;

						grabarDetalleDocumento(params);					
					}
				});
				
			} else { // Archivo no cargado
				
				params.codigoArchivoAlfresco = $('#hid_cod_act_alfresco').val();

				grabarDetalleDocumento(params);				
			}
		}
		
	});
	
	$('#btn_can_documento').click(function(e) {
		e.preventDefault();
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
	
	$('#txt_sub_archivo').change(function(e) {
		e.preventDefault();
	    var file_name = $(this).val();
	    var file_read = file_name.split('\\').pop();
	    $('#txt_lee_sub_archivo').val($.trim(file_read).replace(/ /g, '_'));
	    if (esnulo($('#hid_cod_documento').val())) {
	    	$('#hid_cod_ind_alfresco').val('1'); // Nuevo registro
	    } else {
	    	$('#hid_cod_ind_alfresco').val('2'); // Registro modificado
	    }
	    frm_det_documentos.bootstrapValidator('revalidateField', 'txt_lee_sub_archivo');	    
	});
	
	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
		e.preventDefault();
		
		var id = $(this).attr('id');
		var name = $(this).attr('name');
		if (!esnulo(id) && !esnulo(name)) {
			descargarDocumento(id, name);
		} else {
			addInfoMessage(null, 'No dispone de documento adjunto asociado.');
		}
		
	});
	
});

function inicializarDatos() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#ul_ord_ingreso').css('display', 'block');	
	$('#li_ord_ingreso').attr('class', 'active');
	$('#li_ord_ingreso').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_nro_ord_ingreso').val(ordenIngreso.nroOrdenIngreso);
		$('#txt_anio').val(ordenIngreso.codigoAnio);
		$('#txt_ddi').val(ordenIngreso.nombreDdi);
		$('#txt_almacen').val(ordenIngreso.nombreAlmacen);
		
		if (!esnulo(ordenIngreso.idControlCalidad)) {
			
			$('#hid_cod_con_calidad').val(ordenIngreso.idControlCalidad);		
			if (ordenIngreso.flagTipoBien == '1') {
				$('#li_no_alimentarios').addClass('disabled');
				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			} else {
				$('#li_alimentarios').addClass('disabled');
				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			}
			
			$('#txt_fecha').val(ordenIngreso.fechaEmision);
			$('#sel_estado').val(ordenIngreso.idEstado);
			$('#sel_nro_ord_compra').val(ordenIngreso.nroOrdenCompra);
			$('#sel_tip_control').val(ordenIngreso.idTipoControl);
			$('#sel_ori_almacen').val(ordenIngreso.idAlmacenOrigen);
			$('#sel_ori_en_almacen').val(ordenIngreso.idEncargado);
			$('#sel_inspector').val(ordenIngreso.idInspector);			
			var val_idProveedor = ordenIngreso.provRep;
			$('#sel_proveedor').val(val_idProveedor);
			var arr = val_idProveedor.split('_');
			if (arr.length > 1) {
				$('#txt_representante').val(arr[1]);
			}
			$('#sel_emp_transporte').val(ordenIngreso.idEmpresaTransporte);
			$('#sel_chofer').val(ordenIngreso.idChofer);
			$('#txt_nro_placa').val(ordenIngreso.nroPlaca);
			$('input[name=rb_tip_bien][value="'+ordenIngreso.flagTipoBien+'"]').prop('checked', true);
			$('#txt_conclusiones').val(ordenIngreso.conclusiones);
			$('#txt_recomendaciones').val(ordenIngreso.recomendaciones);
			
			$('input[name=rb_tip_bien]').prop('disabled', true);
			
			listarProductoControlCalidad(false);			
			listarDocumentoControlCalidad(false);
			
		} else {
			
			$('#li_productos').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_ord_ingreso li.disabled a').removeAttr('data-toggle');
			
			$('#txt_fecha').datepicker('setDate', new Date());
			
			var val_proveedor = $('#sel_proveedor').val();		
			if (!esnulo(val_proveedor)) {
				var arr = val_proveedor.split('_');
				if (arr.length > 1) {
					$('#txt_representante').val(arr[1]);
				} else {
					$('#txt_representante').val('');
				}			
			}
			
			listarDetalleProductos(new Object());
			listarDetalleDocumentos(new Object());

		}
		
	}
	
	$('#sel_nro_ord_compra').select2().trigger('change');
	
}

function listarProductoControlCalidad(indicador) {
	var params = { 
		idControlCalidad : $('#hid_cod_con_calidad').val(),
		flagTipoProducto : ordenIngreso.flagTipoBien
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/orden-ingreso/listarProductoControlCalidad', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProductos(respuesta);
			if (indicador) {
				loadding(false);
			}
			if (respuesta != null && respuesta.length > 0) {
				$('input[name=rb_tip_bien]').prop('disabled', true);
			} else {
				$('input[name=rb_tip_bien]').prop('disabled', false);
			}
		}
	});
}

function listarDetalleProductos(respuesta) {

	tbl_det_productos.dataTable().fnDestroy();
	
	tbl_det_productos.dataTable({
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
			data : 'fechaVencimiento'
		}, {
			data : 'cantidadLote'
		}, {
			data : 'cantidadMuestra'
		}, {
			data : 'valorPrimario'
		}, {
			data : 'valorSecundario'
		}, {
			data : 'valorOlor'
		}, {
			data : 'valorColor'
		}, {
			data : 'valorTextura'
		}, {
			data : 'valorSabor'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaProductosCache = respuesta;

}

function listarDocumentoControlCalidad(indicador) {
	var params = { 
		idControlCalidad : $('#hid_cod_con_calidad').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/orden-ingreso/listarDocumentoControlCalidad', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleDocumentos(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleDocumentos(respuesta) {

	tbl_det_documentos.dataTable().fnDestroy();
	
	tbl_det_documentos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDocumentoControlCalidad',
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
			data : 'nombreDocumento'
		}, {
			data : 'nroDocumento',
			render: function(data, type, row) {
				if (data != null) {
					return '<button type="button" id="'+row.codigoArchivoAlfresco+'" name="'+row.nombreArchivo+'"'+ 
						   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
				} else {
					return '';	
				}											
			}
		}, {
			data : 'fechaDocumento'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaDocumentosCache = respuesta;

}

function grabarDetalleDocumento(params) {
	consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarDocumentoControlCalidad', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoControlCalidad(true);
			addSuccessMessage(null, respuesta.mensajeRespuesta);	
		}
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
}

function descargarDocumento(codigo, nombre) {	
	loadding(true);
	var url = VAR_CONTEXT + '/common/archivo/exportarArchivo/'+codigo+'/'+nombre+'/';	
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
}

function cargarTipoMovimiento(val_tip_movimiento) {
	if (val_tip_control == '1') { // Ajuste por Importe
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_ori_en_almacen').prop('disabled', false);
		$('#sel_inspector').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '2') { // Ajustes por inventario
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_ori_en_almacen').prop('disabled', false);
		$('#sel_inspector').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '3') { // Compra
		$('#sel_ori_almacen').prop('disabled', false);
		$('#sel_ori_en_almacen').prop('disabled', false);
		$('#sel_inspector').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '4') { // Inventario Inicial
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_ori_en_almacen').prop('disabled', false);
		$('#sel_inspector').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '5') { // Transferencia Interna
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_ori_en_almacen').prop('disabled', false);
		$('#sel_inspector').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '6') { // Transferencia entre Almacenes
		$('#sel_ori_almacen').prop('disabled', false);
		$('#sel_ori_en_almacen').prop('disabled', false);
		$('#sel_inspector').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	}
}