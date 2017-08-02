var listaAlimentariosCache = new Object();
var listaProductosCache = new Object();
var listaDocumentosCache = new Object();
var listaEstadosCache = new Object();

var tbl_det_productos = $('#tbl_det_productos');
var tbl_det_documentos = $('#tbl_det_documentos');
var tbl_det_estados = $('#tbl_det_estados');


var frm_dat_generales = $('#frm_dat_generales');
var frm_det_documentos = $('#frm_det_documentos');
var frm_det_productos = $('#frm_det_productos');

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
	
	$('#txt_doc_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_det_documentos.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#txt_pro_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_det_productos.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	

	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var codigo = $('#hid_cod_con_calidad').val();
		//	var tipoBien = $('input[name="rb_tip_bien"]:checked').val();
			var idDonante = null;
			var val_donante = $('#sel_donante').val();
			if (!esnulo(val_donante)) {
				var arr = val_donante.split('_');
				idDonante = arr[0];
			}
			
			var idDee = null;
			var val_dee = $('#sel_dee').val();
			if (!esnulo(val_dee)) {
				var arr = val_dee.split('_');
				idDee = arr[0];
			}
			
			var params = {
					
					idDdi : $('#txt_idDdi').val(),
					idDonacion : $('#hid_id_donacion').val(),
					fechaEmision : $('#txt_fecha').val(),
					idPaisDonante : $('#sel_ori_pais').val(), 
					tipoDonante : $('#sel_tip_persona').val(), 
					tipoOrigenDonante : $('#sel_ori_donacion').val(),
					idOficina :  $('#sel_oficina').val(), 
					idPersonal :  $('#sel_personal_oficina').val(), 
					idDonante :  idDonante, 
					finalidad :  $('#txt_finalidad').val(), 
					observacion :  $('#sel_ori_donacion').val(), 
					idEstado :  $('#sel_estado').val(), 
					textoa   :  $('#txt_a').val(), 
					textob   :  $('#txt_b').val(), 
					codigoDdi : $('#txt_codDdi').val(),
					codigoDonacion : $('#txt_cod_id').val(),
					codigoAnio : $('#txt_anio').val(),
					idDee : idDee,
					tipoDonacion : $('#sel_tip_donacion').val() 
			};
			
			loadding(true);
			
			consultarAjax('POST', '/donaciones/registro-donaciones/grabarDonaciones', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					listarEstadoDonacion(false);
					listarProductoDonacion(false);
					listarDocumentoDonacion(false);
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_id_donacion').val(respuesta.idDonacion);
						
						
						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#li_productos').attr('class', '');
						$('#li_productos').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#li_estados').attr('class', '');
						$('#li_estados').closest('li').children('a').attr('data-toggle', 'tab');
//						$('#txt_nro_con_calidad').val(respuesta.nroControlCalidad);
//						
//						if (tipoBien == '1') {					
//							$('#li_alimentarios').attr('class', '');
//							$('#li_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
//						} else {							
//							$('#li_no_alimentarios').attr('class', '');
//							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
//						}
//						$('#li_documentos').attr('class', '');
//						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
						actualizarCampos(respuesta.textoCodigo, respuesta.idDonacion);
						addSuccessMessage(null, 'Se genero el N° de Donación: '+respuesta.textoCodigo);
						
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
		
		$('#txt_doc_fecha').datepicker('setDate', new Date());
		$('#txt_codigo_cod_pro').val('');
		$('#hid_cod_documento').val('');
		$('#txt_descripcion_pro').val('');
		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#txt_sub_archivo').val(null);
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
			
			$('#hid_cod_documento').val(obj.idDocumentoDonacion);			
			$('#sel_tipo_documento').val(obj.idTipoDocumento);
			$('#txt_nro_documento').val(obj.nroDocumento);
			$('#txt_doc_fecha').val(obj.fecha);
			$('#hid_cod_act_alfresco').val(obj.codAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_descripcion_pro').val(obj.observacion);
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
				var idDocumentoDonacion = listaDocumentosCache[index].idDocumentoDonacion;
				codigo = codigo + idDocumentoDonacion + '_';
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
						arrIdDocumentoDonacion : codigo,
						idDonacion : $('#hid_id_donacion').val()
					};
			
					consultarAjax('POST', '/donaciones/registro-donaciones/eliminarDocumentoDonacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoDonacion(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	
	
	$('#href_pro_nuevo').click(function(e) {
		e.preventDefault();
		$('#txt_pro_fecha').datepicker('setDate', new Date());
		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
		$('#frm_det_productos').trigger('reset');
		$('#hid_cod_documento').val('');
		$('#div_det_productos').modal('show');
		
	});
	
	$('#href_pro_editar').click(function(e) {
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
			
			$('#h4_tit_productos').html('Actualizar Producto');
			//limpiarFormularioProducto();
			
			$('#hid_cod_producto').val(obj.idDetDonacion);
			
			$('#sel_cat_producto').val(obj.idCategoria);
			cargarProducto(obj.idCategoria, obj.idProducto+'_'+obj.unidadMedida);
			
			$('#txt_uni_medida').val(obj.unidadMedida);
			$('#txt_fec_vencimiento').val(obj.fecVencimiento);
			$('#txt_cantidad').val(obj.cantidad);
			$('#sel_monedas').val(obj.idMoneda);
			$('#txt_imp_origen').val(obj.monOrigen);
			$('#txt_imp_soles').val(obj.monSoles);
			$('#txt_imp_dolares').val(obj.monDolares);
			
			$('#div_det_productos').modal('show');
		}
		
	});
	
	$('#href_pro_eliminar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
		tbl_det_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleIngreso = listaProductosCache[index].idDetDonacion;
				codigo = codigo + idDetalleIngreso + '_';
			}
		});
		console.log(codigo);
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		console.log(codigo);
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
						idDetDonacion : codigo
					};
			
					consultarAjax('POST', '/donaciones/registrar-donaciones/eliminarProductoDonacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoOrdenIngreso(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_gra_producto').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_productos.data('bootstrapValidator');
		bootstrapValidator.validate();

		if (bootstrapValidator.isValid()) {
			var idProducto = null;
			var val_producto = $('#sel_lis_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}			
			var params = { 
				idProducto : idProducto,
				idDonacion : $('#hid_id_donacion').val(),
				idDetDonacion : 0,
				idMoneda : $('#sel_monedas').val(),
				cantidad : formatMonto($('#txt_cantidad').val()),
				fecVencimiento : $('#txt_fec_vencimiento').val(),
				monOrigen : formatMonto($('#txt_imp_origen').val()),
				monSoles : formatMonto($('#txt_imp_soles').val()),
				monDolares : formatMonto($('#txt_imp_dolares').val())
			};

			loadding(true);
			
			consultarAjax('POST', '/donaciones/registro-donaciones/grabarProductoDonacion', params, function(respuesta) {
				$('#div_det_productos').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoDonacion(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_productos.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
//	$('#sel_cat_producto').change(function() {
//		var idCategoria = $(this).val();		
//		if (!esnulo(idCategoria)) {					
//			cargarProductos(idCategoria, null);
//		} else {
//			$('#sel_no_producto').html('');
//			frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_no_producto');
//		}
//	});
//	
//	function cargarProductos(idCategoria, codigoProducto) {
//		var params = { 
//			idCategoria : idCategoria
//		};			
//		loadding(true);
//		consultarAjax('GET', '/donaciones/registro-donaciones/listarProductosXCategoria', params, function(respuesta) {
//			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//				addErrorMessage(null, respuesta.mensajeRespuesta);
//			} else {
//				var options = '';
//		        $.each(respuesta, function(i, item) {
//		            options += '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'">'+item.nombreProducto+'</option>';
//		        });
//		        $('#sel_producto').html(options);
////		        if (codigoProducto != null) {
////		        	$('#sel_producto').val(codigoProducto);
////					$('#sel_producto').select2().trigger('change');
////					$('#sel_producto').select2({
////						  dropdownParent: $('#frm_det_productos')
////					});	        	
////		        } else {
////		        	frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
////		        }
//			}
//			loadding(false);		
//		});
//	}
	
	$('#sel_dee').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_dee').val(arr[1]);
			} else {
				$('#txt_dee').val('');
			}			
		} else {
			$('#txt_dee').val('');
		}
	});
	
	$('#sel_tip_persona').change(function() {
		var codigo = $(this).val();		
		$('#txt_representante').val('');
		listarDonadores(codigo, null);
		
	});
	
	$('#sel_donante').change(function() {
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
	
	$('#sel_oficina').change(function() {
		var codigo = $(this).val();		
		var ddi = $(txt_codDdi).val();
		if (!esnulo(codigo)) {						
			var params = { 
				icodigo : codigo,
				icodigoParam2 : ddi
			};			
			loadding(true);
			consultarAjax('GET', '/donaciones/registro-donaciones/listarPersonalOficina', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '<option value="">Seleccione</option>';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.icodigo+'">'+item.descripcion+'</option>';
			        });
			        $('#sel_personal_oficina').html(options);
				}
				loadding(false);
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_personal_oficina');
			});
		} else {
			$('#sel_personal_oficina').html('');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_personal_oficina');
		}
	});
	
	
	$('#sel_cat_producto').change(function() {
		var codigo = $(this).val();		
		var ddi = $(txt_codDdi).val();
		if (!esnulo(codigo)) {						
			var params = { 
				icodigo : codigo,
				icodigoParam2 : ddi
			};			
			loadding(true);
			consultarAjax('GET', '/donaciones/registro-donaciones/listarProductosXCategoria', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '<option value="">Seleccione</option>';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.icodigo+'">'+item.descripcion+'</option>';
			        });
			        $('#sel_producto').html(options);
				}
				loadding(false);
				frm_det_productos.bootstrapValidator('revalidateField', 'sel_productos');
			});
		} else {
			$('#sel_personal_oficina').html('');
			frm_det_productos.bootstrapValidator('revalidateField', 'sel_productos');
		}
	});
	
	$('#sel_cat_producto').change(function() {
		var codigo = $(this).val();		
		var ddi = $(txt_codDdi).val();
		if (!esnulo(codigo)) {						
			var params = { 
				idProducto : codigo,
				idCategoria : codigo
			};			
			loadding(true);
			consultarAjax('GET', '/donaciones/registro-donaciones/listarProductosXCategoria', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
					console.log("SI ERROR");
				} else {
					var options = '<option value="">Seleccione</option>';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'">'+item.nombreProducto+'</option>';
			        });
			        $('#sel_lis_producto').html(options);
				}
				loadding(false);
				frm_det_productos.bootstrapValidator('revalidateField', 'sel_lis_producto');
			});
		} else {
			$('#sel_lis_producto').html('');
			frm_det_productos.bootstrapValidator('revalidateField', 'sel_lis_producto');
		}
	});
	
	$('#sel_ori_donacion').change(function() {
		var codigo = $(this).val();		
		if(codigo==1){
			$('#sel_ori_pais').val(179);
			$('#sel_ori_pais').prop('disabled', true);
			
		}else{
			$('#sel_ori_pais').val('');
			$('#sel_ori_pais').prop('disabled', false);
		}
		frm_dat_generales.bootstrapValidator('revalidateField', 'sel_ori_pais');
	});
	
	$('#sel_lis_producto').change(function() {
		var codigo = $(this).val();	
		console.log(codigo);
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_uni_medida_pro').val(arr[1]);
			} else {
				$('#txt_uni_medida_pro').val('');
			}			
		} else {
			$('#txt_uni_medida_pro').val('');
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
		
		//$('#txt_dee').val(donaciones.nombreDdi);

		if (!esnulo(donaciones.idDonacion)) {
			console.log("TEXTO !"+donaciones.textoa);
			$('#txt_cod_donacion').val(donaciones.textoCodigo);
			$('#txt_cod_id').val(donaciones.codigoDonacion);
			$('#txt_anio').val(donaciones.codigoAnio);
			$('#txt_ddi').val(donaciones.nombreDdi);
			$('#txt_codDdi').val(donaciones.codigoDdi);
			$('#txt_idDdi').val(donaciones.idDdi);
			
			
			
			$('#hid_id_donacion').val(donaciones.idDonacion);	
			$('#txt_fecha').val(donaciones.fechaEmision);
			$('#txt_representante').val(donaciones.representante);
			$('#txt_finalidad').val(donaciones.finalidad);
			$('#txt_a').val(donaciones.textoa);
			$('#txt_b').val(donaciones.textob);
			$('#txt_dee').val(donaciones.nombreDeclaratoria);
			
			
			
			$('#txt_codigo_cod_pro').val(donaciones.textoCodigo);
			$('#txt_codigo_cod_doc').val(donaciones.textoCodigo);
			$('#txt_codigo_cod_est').val(donaciones.textoCodigo);
			
			$('#sel_dee').val(donaciones.idDee+"_"+donaciones.nombreDeclaratoria);
			$('#sel_tip_donacion').val(donaciones.tipoDonacion);
			$('#sel_ori_donacion').val(donaciones.tipoOrigenDonante);
			$('#sel_ori_pais').val(donaciones.idPaisDonante);
			$('#sel_tip_persona').val(donaciones.tipoDonante);
			$('#sel_oficina').val(donaciones.idOficina);
			$('#sel_personal_oficina').val(donaciones.idPersonal);
			listarDonadores(donaciones.tipoDonante,donaciones.idDonante+"_"+donaciones.representante);
			listarPersonalOficina(donaciones.idOficina, donaciones.idPersonal);
			
			listarProductoDonacion(false);
			listarDocumentoDonacion(false);
			listarEstadoDonacion(false);
//			if (controlCalidad.flagTipoBien == '1') {
//				$('#li_no_alimentarios').addClass('disabled');
//				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
//			} else {
//				$('#li_alimentarios').addClass('disabled');
//				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
//			}
			
//			$('#txt_fecha').val(donaciones.fechaEmision);
//			$('#sel_estado').val(donaciones.idEstado);
//			$('#sel_nro_ord_compra').val(donaciones.nroOrdenCompra);
//			$('#sel_tip_control').val(donaciones.idTipoControl);
//			$('#sel_ori_almacen').val(donaciones.idAlmacenOrigen);
//			$('#sel_ori_en_almacen').val(donaciones.idEncargado);
//			$('#sel_inspector').val(donaciones.idInspector);			
//			var val_idProveedor = donaciones.provRep;
//			$('#sel_proveedor').val(val_idProveedor);
//			var arr = val_idProveedor.split('_');
//			if (arr.length > 1) {
//				$('#txt_representante').val(arr[1]);
//			}
//			$('#sel_emp_transporte').val(controlCalidad.idEmpresaTransporte);
//			$('#sel_chofer').val(controlCalidad.idChofer);
//			$('#txt_nro_placa').val(controlCalidad.nroPlaca);
//			$('input[name=rb_tip_bien][value="'+controlCalidad.flagTipoBien+'"]').prop('checked', true);
//			$('#txt_conclusiones').val(controlCalidad.conclusiones);
//			$('#txt_recomendaciones').val(controlCalidad.recomendaciones);
			
			$('input[name=rb_tip_bien]').prop('disabled', true);
			
//			listarProductoControlCalidad(false);
//			
//			listarDocumentoControlCalidad(false);
			
		} else {
			
			console.log("TEXTO !"+donaciones.textoa);
			$('#txt_cod_donacion').val(donaciones.textoCodigo);
			$('#txt_cod_id').val(donaciones.codigoDonacion);
			$('#txt_anio').val(donaciones.codigoAnio);
			$('#txt_ddi').val(donaciones.nombreDdi);
			$('#txt_codDdi').val(donaciones.codigoDdi);
			$('#txt_idDdi').val(donaciones.idDdi);
			
			$('#li_estados').addClass('disabled');
			$('#li_productos').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_con_calidad li.disabled a').removeAttr('data-toggle');
			
			$('#txt_fecha').datepicker('setDate', new Date());
			
			var val_donante = $('#sel_donante').val();		
			if (!esnulo(val_donante)) {
				var arr = val_donante.split('_');
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


function listarProductoDonacion(indicador) {
	var params = { 
		idDonacion : $('#hid_id_donacion').val()
	};			
	consultarAjaxSincrono('GET', '/donaciones/registro-donaciones/listarProductoDonacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProductos(respuesta);
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

function listarDetalleProductos(respuesta) {

	tbl_det_productos.dataTable().fnDestroy();
	
	tbl_det_productos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idProducto',
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
			data : 'idDetDonacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'unidadMedida'
		}, {
			data : 'cantidad'
		}, {
			data : 'nombreMoneda'
		}, {
			data : 'monOrigen'
		}, {
			data : 'monSoles'
		}, {
			data : 'fecVencimiento'
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

function cargarProducto(idCategoria, codigoProducto) {
	var params = { 
		idProducto : idCategoria,
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/donaciones/registro-donaciones/listarProductosXCategoria', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.idProducto+'_'+item.unidadMedida+'">'+item.nombreProducto+'</option>';
	        });
	        $('#sel_lis_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_lis_producto').val(codigoProducto);
//				cargarLote(codigoProducto, codigoLote);				
	        } else {
	        	var arr = $('#sel_lis_producto').val().split('_');
				if (arr.length > 1) {
					$('#txt_uni_medida').val(arr[1]);
					
				} else {
					$('#txt_uni_medida').val('');
	
				}
				frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
	        }
	        $('#sel_lis_producto').select2().trigger('change');
			$('#sel_lis_producto').select2({
				  dropdownParent: $('#div_pro_det_productos')
			});
		}
		loadding(false);		
	});
}

function listarDocumentoDonacion(indicador) {
	var params = { 
		idDonacion : $('#hid_id_donacion').val()
	};			
	consultarAjaxSincrono('GET', '/donaciones/registro-donaciones/listarDocumentoDonacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleDocumentos(respuesta);
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

function listarDetalleDocumentos(respuesta) {

	tbl_det_documentos.dataTable().fnDestroy();
	
	tbl_det_documentos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDocumentoDonacion',
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
			data : 'idDetDonacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDocumento'
		}, {
			data : 'nroDocumento',
				render: function(data, type, row) {
					if (data != null) {
						return '<button type="button" id="'+row.codAlfresco+'" name="'+row.nombreArchivo+'"'+ 
							   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
					} else {
						return '';	
					}											
				}
		}, {
			data : 'fecha'
		}, {
			data : 'observacion'
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

function listarEstadoDonacion(indicador) {
	var params = { 
		idDonacion : $('#hid_id_donacion').val()
	};			
	consultarAjaxSincrono('GET', '/donaciones/registro-donaciones/listarEstadoDonacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleEstados(respuesta);
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

function listarDetalleEstados(respuesta) {

	tbl_det_estados.dataTable().fnDestroy();
	
	tbl_det_estados.dataTable({
		data : respuesta,
		columns : [  {	
			data : 'icodigo',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'vcodigoParam2'
		}, {
			data : 'vcodigoParam3'
		}, {
			data : 'vcodigoParam4'
		}],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaEstadosCache = respuesta;

}

$('#btn_gra_documento').click(function(e) {
	e.preventDefault();
	
	var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
	bootstrapValidator.validate();
	if (bootstrapValidator.isValid()) {			
		loadding(true);
		
		var params = { 
			idDonacion : $('#hid_id_donacion').val(),
			idDocumentoDonacion: $('#hid_cod_documento').val(),
			idTipoDocumento : $('#sel_tipo_documento').val(),
			nroDocumento : $('#txt_nro_documento').val(),
			fecha : $('#txt_doc_fecha').val(),
			nombreArchivo : $('#txt_lee_sub_archivo').val(),
			observacion : $('#txt_descripcion_pro').val(),
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
	    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.donaciones');
	    	
			consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
				console.log("ARCHIVO1: "+resArchivo);
				if (resArchivo == NOTIFICACION_ERROR) {
					console.log("ARCHIVO2: "+resArchivo);
					$('#div_det_documentos').modal('hide');
					//frm_det_documentos.data('bootstrapValidator').resetForm();
					loadding(false);
					addErrorMessage(null, mensajeCargaArchivoError);						
				} else {
					
					params.codAlfresco = resArchivo;
					console.log("ARCHIVO3: "+resArchivo);
					grabarDetalleDocumento(params);					
				}
			});
			
		} else { // Archivo no cargado
			
			params.codAlfresco = $('#hid_cod_act_alfresco').val();

			grabarDetalleDocumento(params);				
		}
	}
	
});

function grabarDetalleDocumento(params) {
	consultarAjax('POST', '/donaciones/registro-donaciones/grabarDocumentoDonacion', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoDonacion(true);
			addSuccessMessage(null, respuesta.mensajeRespuesta);	
		}
		//frm_det_documentos.data('bootstrapValidator').resetForm();
	});
}

function listarDonadores(indicador, idDonante) {
	var params = { 
		tipoDonante : indicador
	};			
	loadding(true);
	consultarAjax('GET', '/donaciones/registro-donaciones/listarDonadores', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.icodigo+'_'+item.descripcion+'">'+item.descripcionCorta+'</option>';
	        });
	        $('#sel_donante').html(options);
	        if (idDonante != null) {
	        	$('#sel_donante').val(idDonante);		
	        } else {
	        	$('#sel_donante').val('');	
	        }
//	        	var arr = $('#sel_donante').val().split('_');
//				if (arr.length > 1) {
//					$('#txt_uni_medida').val(arr[1]);
//					
//				} else {
//					$('#txt_uni_medida').val('');
//	
//				}
//				frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
//	        }
//	        $('#sel_donante').select2().trigger('change');
//			$('#sel_donante').select2({
//				  dropdownParent: $('#div_pro_det_productos')
//			});
		}
		loadding(false);		
	});
}

function listarPersonalOficina(codigo,dato) {
	var codigo = codigo;		
	var ddi = $(txt_codDdi).val();
	if (!esnulo(codigo)) {						
		var params = { 
			icodigo : codigo,
			icodigoParam2 : ddi
		};			
		loadding(true);
		consultarAjax('GET', '/donaciones/registro-donaciones/listarPersonalOficina', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				var options = '<option value="">Seleccione</option>';
		        $.each(respuesta, function(i, item) {
		            options += '<option value="'+item.icodigo+'">'+item.descripcion+'</option>';
		        });
		        $('#sel_personal_oficina').html(options);
		        if (dato != null) {
		        	$('#sel_personal_oficina').val(dato);		
		        } else {
		        	$('#sel_personal_oficina').val('');	
		        }
			}
			loadding(false);
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_personal_oficina');
		});
	} else {
		$('#sel_personal_oficina').html('');
		frm_dat_generales.bootstrapValidator('revalidateField', 'sel_personal_oficina');
	}
}

function actualizarCampos(concatenado, idDonacion){
	
	$('#hid_id_donacion').val(idDonacion);
	$('#txt_cod_donacion').val(concatenado);
	$('#txt_codigo_cod_doc').val(concatenado);
	$('#txt_codigo_cod_pro').val(concatenado);
	$('#txt_codigo_cod_est').val(concatenado);
	listarProductoDonacion(false);
	listarDocumentoDonacion(false);
	listarEstadoDonacion(new Object());
	
}

