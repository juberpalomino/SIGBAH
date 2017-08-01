var listaDetalleAlmacenesCache = new Object();

var listaDetalleAlmacenesCache = new Object();
var listaDetalleAlmacenesCache = new Object();

var listaProductosRacionCache = new Object();
var listaProgramacionAlimentosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_almacenes = $('#tbl_det_almacenes');

var tbl_pro_racion = $('#tbl_pro_racion');
var tbl_det_pro_alimentos = $('#tbl_det_pro_alimentos');
var tbl_res_pro_alimentos = $('#tbl_res_pro_alimentos');

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
	
	$('#txt_fec_programacion').datepicker().on('changeDate', function(e) {
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
	
	$('#sel_nro_requerimiento').change(function() {
		obtenerRequerimiento($(this).val());
	});

	$('#sel_nro_racion').change(function() {
		obtenerRacion($(this).val());
	});
	
	$('#sel_nro_dee').change(function() {
		obtenerNroDee($(this).val());
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		$('#hid_ind_programacion').val('1');
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_programacion').val();
			var tipoControl = 'I'; // Insert
			if (!esnulo(codigo)) {
				tipoControl = 'U'; // Update
			}

			var val_requerimiento = $('#sel_nro_requerimiento').val();
			var arr_requerimiento = val_requerimiento.split('_');
			
			var val_nro_racion = $('#sel_nro_racion').val();
			var arr_nro_racion = val_nro_racion.split('_');
			
			var val_nro_dee = $('#sel_nro_dee').val();
			var arr_nro_dee = val_nro_dee.split('_');
			
			var tipoAtencion = $('#sel_ate_con').val();
			
			var params = {
				idProgramacion : codigo,	
				codigoAnio : programacion.codigoAnio,
				idRequerimiento : arr_requerimiento[0],
				fechaProgramacion : $('#txt_fec_programacion').val(),
				idDdi : programacion.idDdi, 
				codigoDdi : programacion.codigoDdi,
				idRegion : $('#sel_reg_destino').val(),				
				idRacion : arr_nro_racion[0],
				nombreProgramacion : $('#txt_descripcion').val(),				
				idNroDee : arr_nro_dee[0],	
				tipoAtencion : tipoAtencion,
				observacion : $('#txt_observaciones').val(),
				tipoControl : tipoControl
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
						$('#txt_estado').val(respuesta.nombreEstado);
						$('#sel_almacen').prop('disabled', false);
						$('#sel_almacen').val($('#sel_almacen option:first').val());
						$('#btn_alm_agregar').prop('disabled', false);
						$('#btn_alm_eliminar').prop('disabled', false);
						
						$('#txt_programacion').val(respuesta.codigoProgramacion+'-'+$('#txt_descripcion').val());
						$('#txt_pro_racion').val($('#txt_des_racion').val());
						
						if (tipoAtencion == '1') { // Alimentos			
							$('#li_alimentos').attr('class', '');
							$('#li_alimentos').closest('li').children('a').attr('data-toggle', 'tab');
						} else if (tipoAtencion == '2') { // No Alimentarios
							$('#li_no_alimentarios').attr('class', '');
							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						} else { // Ambos
							$('#li_alimentos').attr('class', '');
							$('#li_alimentos').closest('li').children('a').attr('data-toggle', 'tab');
							$('#li_no_alimentarios').attr('class', '');
							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						}
						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#li_estados').attr('class', '');
						$('#li_estados').closest('li').children('a').attr('data-toggle', 'tab');

						addSuccessMessage(null, 'Se genero el N° Programación: '+respuesta.nroProgramacion);
						
					}
					
				}
				frm_dat_generales.data('bootstrapValidator').resetForm();
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
	
	$('#btn_alm_agregar').click(function(e) {
		e.preventDefault();
		
		$('#hid_ind_programacion').val('2');

		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = { 
				idProgramacion : $('#hid_cod_programacion').val(),
				idAlmacen : $('#sel_almacen').val()
			};

			loadding(true);
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProgramacionAlmacen', params, function(respuesta) {
				$('#div_det_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProgramacionAlmacenes(true);
					frm_dat_generales.data('bootstrapValidator').resetForm();
					addSuccessMessage(null, respuesta.mensajeRespuesta);
				}
			});
		}

	});
	
	$('#btn_alm_eliminar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_det_almacenes.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_almacenes.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idProgramacionAlmacen = listaDetalleAlmacenesCache[index].idProgramacionAlmacen;
				codigo = codigo + idProgramacionAlmacen + '_';
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
						arrIdDetalleProgramacionAlmacen : codigo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarProgramacionAlmacen', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProgramacionAlmacenes(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
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
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_programacion').attr('class', 'active');
	$('#li_programacion').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_nro_programacion').val(programacion.nroProgramacion);
		if (!esnulo(programacion.idProgramacion)) {
			
			$('#hid_cod_programacion').val(programacion.idProgramacion);		
			if (programacion.flagTipoBien == '1') {
				$('#li_no_alimentarios').addClass('disabled');
				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			} else {
				$('#li_alimentarios').addClass('disabled');
				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			}
			
			$('#txt_fec_programacion').val(programacion.fechaProgramacion);
			$('#txt_estado').val(programacion.nombreEstado);
			$('#txt_descripcion').val(programacion.nombreProgramacion);
			$('#sel_nro_requerimiento').val(programacion.idRequerimiento+'_'+programacion.nombreRequerimiento);			
			$('#txt_des_requerimiento').val(programacion.nombreRequerimiento);			
			$('#sel_nro_racion').val(programacion.idRacion+'_'+programacion.nombreRacion);			
			$('#txt_des_racion').val(programacion.nombreRacion);			
			$('#sel_nro_dee').val(programacion.idNroDee+'_'+programacion.nombreDeclarion);			
			$('#txt_des_nro_dee').val(programacion.nombreDeclarion);
			$('#sel_reg_destino').val(programacion.idRegion);
			$('#sel_ate_con').val(programacion.tipoAtencion);
			$('#txt_observaciones').val(programacion.observacion);
			
			$('#txt_programacion').val($('#txt_descripcion').val());
			$('#txt_pro_racion').val($('#txt_des_racion').val());
			
			listarDetalleProgramacionAlmacenes(programacion.almacenes);
			cargarAlmacenes();
			
			if (programacion.tipoAtencion == '1') { // Alimentos			
				$('#li_no_alimentarios').addClass('disabled');
				$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
				listarProgramacionRacionOperativa(false);
			} else if (programacion.tipoAtencion == '2') { // No Alimentarios
				$('#li_alimentos').addClass('disabled');
				$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
			} else if (programacion.tipoAtencion == '3') { // Ambos
				listarProgramacionRacionOperativa(false);
			}
			
//			listarProductoProgramacion(false);			
//			listarDocumentoProgramacion(false);
			
		} else {
			
			$('#li_alimentos').addClass('disabled');
			$('#li_no_alimentarios').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#li_estados').addClass('disabled');
			$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
			
			$('#txt_fec_programacion').datepicker('setDate', new Date());
			
			obtenerRequerimiento($('#sel_nro_requerimiento').val());
			obtenerRacion($('#sel_nro_racion').val());
			obtenerNroDee($('#sel_nro_dee').val());
			cargarAlmacenes();
			$('#sel_almacen').val('');
			$('#sel_almacen').prop('disabled', true);
			$('#btn_alm_agregar').prop('disabled', true);
			$('#btn_alm_eliminar').prop('disabled', true);
			
			listarDetalleProgramacionAlmacenes(new Object());
			listarDetalleAlimentarios(new Object());
			listarDetalleNoAlimentarios(new Object());
			listarDetalleDocumentos(new Object());

		}
		
	}
	
}

function listarProgramacionAlmacenes(indicador) {
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacionAlmacen', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProgramacionAlmacenes(respuesta);
			cargarAlmacenes();
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleProgramacionAlmacenes(respuesta) {

	tbl_det_almacenes.dataTable().fnDestroy();
	
	tbl_det_almacenes.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idProgramacionAlmacen',
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
			data : 'idProgramacionAlmacen',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreAlmacen'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaDetalleAlmacenesCache = respuesta;

}

function listarProgramacionRacionOperativa(indicador) {
	var val_nro_racion = $('#sel_nro_racion').val();
	var arr_nro_racion = val_nro_racion.split('_');
	var params = { 
		idRacionOperativa : arr_nro_racion[0]
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacionRacionOperativa', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleRacionOperativa(respuesta);
			if (respuesta.length > 0) {
				$('#txt_dia_atencion').val(respuesta[0].diasAtencion);
			} else {
				$('#txt_dia_atencion').val('');
			}
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleRacionOperativa(respuesta) {

	tbl_pro_racion.dataTable().fnDestroy();
	
	tbl_pro_racion.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idDetalleRacionOperativa',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'cantidadRacionKg'
		}, {
			data : 'pesoUnidadPres'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		'footerCallback' : function ( row, data, start, end, display ) {
			var api = this.api(), data;	 
			
			// Remove the formatting to get integer data for summation
			var intVal = function ( i ) {
				return typeof i === 'string' ? i.replace(/[\$,]/g, '')*1 : typeof i === 'number' ?	i : 0;
			};
 
			// total_page_peso over this page
			var tot_gr = api.column(2, { page: 'current'} ).data().reduce( function (a, b) {
				return intVal(a) + intVal(b);
			}, 0 );

			// Update footer
			$('#sp_tot_gr').html(parseFloat(tot_gr).toFixed(2));
		}
	});
	
	listaProductosRacionCache = respuesta;

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

function obtenerRequerimiento(codigo) {
	if (!esnulo(codigo)) {
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_des_requerimiento').val(arr[1]);
		} else {
			$('#txt_des_requerimiento').val('');
		}			
	} else {
		$('#txt_des_requerimiento').val('');
	}
}

function obtenerRacion(codigo) {
	if (!esnulo(codigo)) {
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_des_racion').val(arr[1]);
		} else {
			$('#txt_des_racion').val('');
		}			
	} else {
		$('#txt_des_racion').val('');
	}
}

function obtenerNroDee(codigo) {
	if (!esnulo(codigo)) {
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_des_nro_dee').val(arr[1]);
		} else {
			$('#txt_des_nro_dee').val('');
		}			
	} else {
		$('#txt_des_nro_dee').val('');
	}
}

function cargarAlmacenes() {
	var idProgramacion = $('#hid_cod_programacion').val();
	var options = '';
	if (!esnulo(idProgramacion)) {
	    $.each(listaAlmacenesCache, function(i, item) {
	    	if (verificarAlmacen(item.vcodigo) != '1') {
	    		options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
	    	}
	    });	    
	} else {
		$.each(listaAlmacenesCache, function(i, item) {
	        options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
	    });
	}
    $('#sel_almacen').html(options);
	if (programacion.codigoDdi == CODIGO_DDI_INDECI_CENTRAL) {
		$('#sel_almacen').prepend("<option value=''>Seleccione</option>").val('');
	}
}


function verificarAlmacen(vcodigo) {
	var indicador = null;
    $.each(listaDetalleAlmacenesCache, function(i, item) {
		if (vcodigo == item.idAlmacen) {
			indicador = '1';
			return false;
		}
    });
    return indicador;
}
