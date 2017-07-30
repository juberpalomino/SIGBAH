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
		frm_det_alimentarios.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#txt_no_fec_vencimiento').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_det_no_alimentarios.bootstrapValidator('revalidateField', $(this).attr('id'));	
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
	
	$('#sel_tip_control').change(function() {
		var val_tip_control = $(this).val();		
		if (!esnulo(val_tip_control)) {
			
			frm_dat_generales.data('bootstrapValidator').resetForm();
			
			cargarTipoControl(val_tip_control);
			
			$('#sel_ori_almacen').val('');
			$('#sel_ori_en_almacen').val('');
			$('#sel_inspector').val('');
			$('#sel_proveedor').val('');
			$('#txt_representante').val('');
			$('#sel_emp_transporte').val('');
			$('#sel_chofer').val('');
			$('#txt_nro_placa').val('');
			
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_ori_almacen');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_proveedor');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_emp_transporte');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
			frm_dat_generales.bootstrapValidator('revalidateField', 'txt_nro_placa');
			
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
			consultarAjax('GET', '/programacion-bah/programacion/listarChofer', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '<option value="">Seleccione</option>';
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
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
		}
	});
	
	$('#sel_no_cat_producto').change(function() {
		var idCategoria = $(this).val();		
		if (!esnulo(idCategoria)) {					
			cargarProductoNoAlimentario(idCategoria, null);
		} else {
			$('#sel_no_producto').html('');
			frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_no_producto');
		}
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_programacion').val();
			var tipoBien = $('input[name="rb_tip_bien"]:checked').val();
			var idProveedor = null;
			var val_proveedor = $('#sel_proveedor').val();
			if (!esnulo(val_proveedor)) {
				var arr = val_proveedor.split('_');
				idProveedor = arr[0];
			}
			
			var val_ord_compra = $('#sel_nro_ord_compra').val();
			var arr_ord_compra = val_ord_compra.split('_');
			
			var params = {
				idProgramacion : codigo,	
				codigoAnio : $('#txt_anio').val(),
				codigoMes : controlCalidad.codigoMes,
				codigoDdi : controlCalidad.codigoDdi,
				idDdi : controlCalidad.idDdi, 
				idAlmacen : controlCalidad.idAlmacen,
				codigoAlmacen : controlCalidad.codigoAlmacen,
				fechaEmision : $('#txt_fecha').val(),
				idEstado : $('#sel_estado').val(),
				nroOrdenCompra : arr_ord_compra[0],
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
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProgramacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_programacion').val(respuesta.idProgramacion);
						$('#txt_nro_programacion').val(respuesta.nroProgramacion);
						
						if (tipoBien == '1') {					
							$('#li_alimentarios').attr('class', '');
							$('#li_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						} else {							
							$('#li_no_alimentarios').attr('class', '');
							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						}
						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');

						addSuccessMessage(null, 'Se genero el N° Control de Calidad: '+respuesta.nroProgramacion);
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bah/programacion/inicio/1';
		$(location).attr('href', url);
		
	});
	
	$('#href_ali_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_alimentarios').html('Nuevo Producto');
		frm_det_alimentarios.trigger('reset');
		
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
			
			$('#hid_cod_producto').val(obj.idDetalleProgramacion);
			
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
		var codigo = '';
		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleProgramacion = listaAlimentariosCache[index].idDetalleProgramacion;
				codigo = codigo + idDetalleProgramacion + '_';
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
						arrIdDetalleProgramacion : codigo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarProductoProgramacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoProgramacion(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
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
			var idProducto = null;
			var val_producto = $('#sel_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}			
			var params = { 
				idDetalleProgramacion : $('#hid_cod_producto').val(),
				idProgramacion : $('#hid_cod_programacion').val(),
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
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProductoProgramacion', params, function(respuesta) {
				$('#div_det_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoProgramacion(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_alimentarios.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_alimentario').click(function(e) {
		e.preventDefault();
		frm_det_alimentarios.data('bootstrapValidator').resetForm();
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
		
		$('#sel_no_producto').html('');
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
			
			$('#hid_cod_no_producto').val(obj.idDetalleProgramacion);
			
			$('#sel_no_cat_producto').val(obj.idCategoria);
			cargarProductoNoAlimentario(obj.idCategoria, obj.idProducto+'_'+obj.nombreUnidad);			
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
		var codigo = '';
		tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleProgramacion = listaNoAlimentariosCache[index].idDetalleProgramacion;
				codigo = codigo + idDetalleProgramacion + '_';
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
						arrIdDetalleProgramacion : codigo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarProductoProgramacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoProgramacion(true);
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
				idDetalleProgramacion : $('#hid_cod_no_producto').val(),
				idProgramacion : $('#hid_cod_programacion').val(),
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
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProductoProgramacion', params, function(respuesta) {
				$('#div_det_no_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoProgramacion(true);
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
		
		$('#hid_cod_documento').val('');
		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#fil_sub_archivo').val(null);
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
			
			$('#hid_cod_documento').val(obj.idDocumentoProgramacion);			
			$('#sel_tip_producto').val(obj.idTipoDocumento);
			$('#txt_nro_documento').val(obj.nroDocumento);
			$('#txt_doc_fecha').val(obj.fechaDocumento);
			$('#hid_cod_act_alfresco').val(obj.codigoArchivoAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
			$('#fil_sub_archivo').val(null);
			
			$('#div_det_documentos').modal('show');
		}
		
	});
	
	$('#href_doc_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = '';
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDocumentoProgramacion = listaDocumentosCache[index].idDocumentoProgramacion;
				codigo = codigo + idDocumentoProgramacion + '_';
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
						arrIdDocumentoProgramacion : codigo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarDocumentoProgramacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoProgramacion(true);
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
				idDocumentoProgramacion : $('#hid_cod_documento').val(),
				idProgramacion : $('#hid_cod_programacion').val(),
				idTipoDocumento : $('#sel_tip_producto').val(),
				nroDocumento : $('#txt_nro_documento').val(),
				fechaDocumento : $('#txt_doc_fecha').val(),
				nombreArchivo : $('#txt_lee_sub_archivo').val()
			};
			
			var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
			if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
				var file_name = $('#fil_sub_archivo').val();
				var file_data = null;
				if (!esnulo(file_name) && typeof file_name !== 'undefined') {
					file_data = $('#fil_sub_archivo').prop('files')[0];
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
	
	$('#btn_can_documento, #btn_clo_documentos').click(function(e) {
		e.preventDefault();
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
	
	$('#fil_sub_archivo').change(function(e) {
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
	
	$('#href_eli_archivo').click(function(e) {
		e.preventDefault();

		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#fil_sub_archivo').val(null);
		$('#txt_lee_sub_archivo').val('');
		
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
	$('#li_programacion').attr('class', 'active');
	$('#li_programacion').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_nro_programacion').val(controlCalidad.nroProgramacion);
		$('#txt_anio').val(controlCalidad.codigoAnio);
		$('#txt_ddi').val(controlCalidad.nombreDdi);
		$('#txt_almacen').val(controlCalidad.nombreAlmacen);
		
		if (!esnulo(controlCalidad.idProgramacion)) {
			
			$('#hid_cod_programacion').val(controlCalidad.idProgramacion);		
			if (controlCalidad.flagTipoBien == '1') {
				$('#li_no_alimentarios').addClass('disabled');
				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			} else {
				$('#li_alimentarios').addClass('disabled');
				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			}
			
			$('#txt_fecha').val(controlCalidad.fechaEmision);
			$('#sel_estado').val(controlCalidad.idEstado);
			$('#sel_nro_ord_compra').val(controlCalidad.nroOrdenCompra+'_'+controlCalidad.concepto);			
			$('#txt_det_ord_compra').val(controlCalidad.concepto);
			
			$('#sel_tip_control').val(controlCalidad.idTipoControl);
			cargarTipoControl(controlCalidad.idTipoControl);
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
			
			$('input[name=rb_tip_bien]').prop('disabled', true);
			
			
			
			listarProductoProgramacion(false);
			
			listarDocumentoProgramacion(false);
			
		} else {
			
			$('#li_alimentarios').addClass('disabled');
			$('#li_no_alimentarios').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
			
			$('#txt_fecha').datepicker('setDate', new Date());
			
			var arr = $('#sel_nro_ord_compra').val().split('_');
			if (arr.length > 1) {
				$('#txt_det_ord_compra').val(arr[1]);
			}
			
			var val_proveedor = $('#sel_proveedor').val();		
			if (!esnulo(val_proveedor)) {
				var arr = val_proveedor.split('_');
				if (arr.length > 1) {
					$('#txt_representante').val(arr[1]);
				} else {
					$('#txt_representante').val('');
				}			
			}
			
			cargarTipoControl($('#sel_tip_control').val());
			
			listarDetalleAlimentarios(new Object());
			listarDetalleNoAlimentarios(new Object());
			listarDetalleDocumentos(new Object());

		}
		
		$('#sel_nro_ord_compra').select2().trigger('change');

	}
	
}

function listarProductoProgramacion(indicador) {
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
		flagTipoProducto : $('input[name="rb_tip_bien"]:checked').val()
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProductoProgramacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			if ($('input[name="rb_tip_bien"]:checked').val() == '1') {
				listarDetalleAlimentarios(respuesta);
			} else {
				listarDetalleNoAlimentarios(respuesta);
			}
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

function listarDetalleAlimentarios(respuesta) {

	tbl_det_alimentarios.dataTable().fnDestroy();
	
	tbl_det_alimentarios.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleProgramacion',
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
			data : 'idDetalleProgramacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
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
	
	listaAlimentariosCache = respuesta;

}

function listarDetalleNoAlimentarios(respuesta) {

	tbl_det_no_alimentarios.dataTable().fnDestroy();
	
	tbl_det_no_alimentarios.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleProgramacion',
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
			data : 'idDetalleProgramacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
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
			data : 'valorEspecTecnicas'
		}, {
			data : 'valorConforProducto'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaNoAlimentariosCache = respuesta;

}

function listarDocumentoProgramacion(indicador) {
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val()
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarDocumentoProgramacion', params, function(respuesta) {
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
			data : 'idDocumentoProgramacion',
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
			data : 'idDocumentoProgramacion',
			render : function(data, type, full, meta){
				var row = meta.row + 1;
				return row;											
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
	consultarAjax('POST', '/programacion-bah/programacion/grabarDocumentoProgramacion', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoProgramacion(true);
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

function cargarProductoNoAlimentario(idCategoria, codigoProducto) {
	var params = { 
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/programacion-bah/programacion/listarProductoXCategoria', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'">'+item.nombreProducto+'</option>';
	        });
	        $('#sel_no_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_no_producto').val(codigoProducto);
				$('#sel_no_producto').select2().trigger('change');
				$('#sel_no_producto').select2({
					  dropdownParent: $('#div_pro_det_no_alimentarios')
				});	        	
	        } else {
	        	frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_no_producto');
	        }
		}
		loadding(false);		
	});
}

function cargarTipoControl(val_tip_control) {
	if (val_tip_control == '1') { // Ingreso por Compra de productos
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '2') { // Control Interno
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '3') { // Ingreso por Transferencias de Almacén
		$('#sel_ori_almacen').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '4') { // Salida de Productos por Emergencia
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '5') { // Ingreso por Donación
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '6') { // Salidas por Transferencias a Almacén
		$('#sel_ori_almacen').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	}
}