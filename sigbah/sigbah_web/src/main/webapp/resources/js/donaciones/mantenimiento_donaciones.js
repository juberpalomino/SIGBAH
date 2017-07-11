var listaAlimentariosCache = new Object();
var listaProductosCache = new Object();
var listaDocumentosCache = new Object();


var frm_dat_generales = $('#frm_dat_generales');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy'
	});
	
	inicializarDatos();
	
	$('#txt_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#frm_dat_generales').bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			txt_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Fecha.'
					}
				}
			},
			sel_estado : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Estado.'
					}
				}
			}
			
			
		}
	});
	
	 
	
	$('#frm_det_documentos').bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_tip_producto : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Tipo Documento.'
					}
				}
			},
			txt_nro_documento : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar N° Documento.'
					}
				}
			},
			txt_doc_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha.'
					}
				}
			},
			txt_sub_archivo : {
				validators : {
					notEmpty : {
						message : 'Debe cargar el Archivo.'
					}
				}
			}
		}
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var codigo = $('#hid_cod_con_calidad').val();
		//	var tipoBien = $('input[name="rb_tip_bien"]:checked').val();
		//	var idProveedor = null;
			var val_proveedor = $('#sel_proveedor').val();
			if (!esnulo(val_proveedor)) {
				var arr = val_proveedor.split('_');
				idProveedor = arr[0];
			}
			
			var params = {
					
					idDonacion : codigo,
					idDdi : controlCalidad.idDdi, 
					fechaEmision : $('#txt_fecha').val(),
					idPaisDonante : $('#sel_ori_donacion').val(), 
					tipoDonante : $('#sel_tip_persona').val(), 
					tipoOrigenDonante : $('#sel_dontante').val(),
					idOficina : '123', 
					idPersonal : '23',
					idDonante : '33',
					finalidad : '',
					observacion : '',
					idEstado : '22',
					textoa   : '',
					textob   : '',
					codigoDdi : controlCalidad.codigoDdi,
					CodigoDonacion : $('#txt_cod_donacion').val(),
					CodigoAnio : $('#txt_anio').val(),
					IdDee : $('#sel_nro_ord_compra').val(),
					TipoDonacion : $('#sel_tip_donacion').val() 
			};
			
			loadding(true);
			
			consultarAjax('POST', '/donaciones/registro-donaciones/grabarDonaciones', params, function(respuesta) {
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
		$('#frm_det_alimentarios').trigger('reset');
		$('#hid_cod_producto').val('');
		$('#div_det_alimentarios').modal('show');
		
	});
	
	$('#href_ali_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var tbl_det_alimentarios = $('#tbl_det_alimentarios').DataTable();
		tbl_det_alimentarios.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_alimentarios.rows().$('input[type="checkbox"]')[index].checked) {
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
			$('#frm_det_alimentarios').trigger('reset');
			
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
		var tbl_det_documentos = $('#tbl_det_documentos').DataTable();
		tbl_det_documentos.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.rows().$('input[type="checkbox"]')[index].checked) {
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
		var tbl_det_documentos = $('#tbl_det_documentos').DataTable();
		tbl_det_documentos.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.rows().$('input[type="checkbox"]')[index].checked) {
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
		
		var bootstrapValidator = $('#frm_det_documentos').data('bootstrapValidator');
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
		
  	$('#li_donaciones').addClass('active');
	$('#ul_donaciones').css('display', 'block');
	$('#li_reg_donaciones').attr('class', 'active');
	$('#li_reg_donaciones').closest('li').children('a').attr('href', '#');
	
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
			
			$('input[name=rb_tip_bien]').prop('disabled', true);
			
			listarProductoControlCalidad(false);
			
			listarDocumentoControlCalidad(false);
			
		} else {
			
			$('#li_alimentarios').addClass('disabled');
			$('#li_no_alimentarios').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_con_calidad li.disabled a').removeAttr('data-toggle');
			
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
			
 
 
	//		listarDetalleDocumentos(new Object());

		}
		$('.btn_retornar').click(function(e) {
			e.preventDefault();

			loadding(true);					
			var url = VAR_CONTEXT + '/donaciones/registro-donaciones/inicio';
			$(location).attr('href', url);
			
		});
		
		
		$('#sel_nro_ord_compra').select2().trigger('change');

	}
	
	function listarDocumentoDonaciones(indicador) {
		var params = { 
				idDonacion : $('#hid_cod_con_calidad').val()
		};			
		consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarDocumentoControlCalidad', params, function(respuesta) {
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

}

