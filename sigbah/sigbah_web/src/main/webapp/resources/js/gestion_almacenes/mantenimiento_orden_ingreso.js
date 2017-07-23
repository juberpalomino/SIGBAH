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
		var fecha = $(this).val();
		if (!esnulo(fecha)) {
			var mes = fecha.substring(3, 5);
		    var anio = fecha.substring(6, 10);		    
		    if (mes != ordenIngreso.codigoMes || anio != ordenIngreso.codigoAnio) {
		    	$('#hid_val_fec_trabajo').val('0');
		    	addWarnMessage(null, 'La fecha no corresponde al año y mes de trabajo.');
		    	$('#'+$(this).attr('id')).focus();
		    } else {
		    	$('#hid_val_fec_trabajo').val('1');
		    }
		}
		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#txt_fec_llegada').datepicker().on('changeDate', function(e) {
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
			
			frm_dat_generales.data('bootstrapValidator').resetForm();
			
			cargarTipoMovimiento(val_tip_movimiento, true);
			
			$('#sel_com_por').val('');
			$('input[name=rb_tie_nro_rep_con_calidad]').prop('checked', false);
			$('#sel_nro_con_calidad').val('');
			$('#sel_proveedor').val('');
			$('#txt_representante').val('');
			$('#sel_almacen').val('');
			$('#sel_med_transporte').val('');
			$('#sel_emp_transporte').val('');
			$('#txt_fec_llegada').val('');
			$('#sel_chofer').val('');
			$('#txt_nro_placa').val('');
			
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_nro_ord_compra');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_com_por');
			frm_dat_generales.bootstrapValidator('revalidateField', 'rb_tie_nro_rep_con_calidad');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_nro_con_calidad');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_proveedor');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_almacen');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_med_transporte');
			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_emp_transporte');
			frm_dat_generales.bootstrapValidator('revalidateField', 'txt_fec_llegada');
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
				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_emp_transporte');
				
				consultarAjax('GET', '/gestion-almacenes/control-calidad/listarChofer', {icodigo : $('#sel_emp_transporte').val()}, function(respuesta) {
					if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
						addErrorMessage(null, respuesta.mensajeRespuesta);
					} else {
						var options = '';
				        $.each(respuesta, function(i, item) {
				            options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
				        });
				        $('#sel_chofer').html(options);
					}
					frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
				});
				
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
			consultarAjax('GET', '/gestion-almacenes/control-calidad/listarChofer', params, function(respuesta) {
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
			
			if ($('#hid_val_fec_trabajo').val() == '0') {
		    	addWarnMessage(null, 'La fecha no corresponde al año y mes de trabajo.');
		    	return;
			}
			
			var codigo = $('#hid_cod_ord_ingreso').val();
			var flagControlCalidad = $('input[name="rb_tie_nro_rep_con_calidad"]:checked').val();
			var idProveedor = null;
			var val_proveedor = $('#sel_proveedor').val();
			if (!esnulo(val_proveedor)) {
				var arr = val_proveedor.split('_');
				idProveedor = arr[0];
			}			
			var val_ord_compra = $('#sel_nro_ord_compra').val();
			var nroOrdenCompra = null;
			if (!esnulo(val_ord_compra)) {
				var arr_ord_compra = val_ord_compra.split('_');
				nroOrdenCompra = arr_ord_compra[0];
			}
			
			var params = {
				idIngreso : codigo,
				codigoAnio : $('#txt_anio').val(),
				codigoMes : ordenIngreso.codigoMes,				
				fechaEmision : $('#txt_fecha').val(),
				idMedioTransporte : $('#sel_med_transporte').val(),
				idMovimiento : $('#sel_tip_movimiento').val(),
				codigoAlmacen : ordenIngreso.codigoAlmacen,
				idAlmacen : ordenIngreso.idAlmacen,
				idAlmacenProcedencia : $('#sel_almacen').val(),
				idProveedor : idProveedor,
				codigoDdi : ordenIngreso.codigoDdi,
				nroOrdenIngreso : $('#txt_nro_ord_ingreso').val(),
				idControlCalidad : $('#sel_nro_con_calidad').val(),
				idChofer : $('#sel_chofer').val(),
				nroPlaca : $('#txt_nro_placa').val(),
				flagTipoCompra : $('#sel_com_por').val(),
				fechaLlegada : $('#txt_fec_llegada').val(),
				observacion : $('#txt_observaciones').val(),
				idEstado : $('#sel_estado').val(),
				nroOrdenCompra : nroOrdenCompra,		
				flagControlCalidad : flagControlCalidad,
				idEmpresaTransporte : $('#sel_emp_transporte').val(),
				idResponsable : $('#sel_responsable').val()
			};
			
			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarOrdenIngreso', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_ord_ingreso').val(respuesta.idIngreso);
						$('#txt_nro_ord_ingreso').val(respuesta.codigoOrdenIngreso);
				
						$('#li_productos').attr('class', '');
						$('#li_productos').closest('li').children('a').attr('data-toggle', 'tab');

						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#txt_det_nro_ord_compra').val(nroOrdenCompra);

						addSuccessMessage(null, 'Se genero el N° Orden de Ingreso: '+respuesta.codigoOrdenIngreso);
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/orden-ingreso/inicio/1';
		$(location).attr('href', url);
		
	});
	
	$('#href_pro_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_productos').html('Nuevo Producto');
		limpiarFormularioProducto();
		$('#txt_fec_vencimiento').datepicker('setDate', new Date());
		
		$('#sel_producto').html('');
		if ($('#sel_producto').hasClass('select2-hidden-accessible')) {
			$('#sel_producto').select2('destroy');
		}
		
		$('#sel_lote').html('');
		
		$('#hid_cod_producto').val('');
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
			limpiarFormularioProducto();
			
			$('#hid_cod_producto').val(obj.idDetalleIngreso);
			
			$('#sel_cat_producto').val(obj.idCategoria);
			cargarProducto(obj.idCategoria, obj.idProducto+'_'+obj.nombreUnidad+'_'+obj.nombreEnvase, obj.nroLote);
			
			$('#txt_uni_medida').val(obj.nombreUnidad);
			$('#txt_envase').val(obj.nombreEnvase);
			$('#txt_fec_vencimiento').val(obj.fechaVencimiento);
			$('#txt_cantidad').val(obj.cantidad);
			$('#txt_pre_unitario').val(obj.precioUnitario);
			$('#txt_imp_total').val(obj.importeTotal);
			
			$('#div_det_productos').modal('show');
		}
		
	});
	
	$('#href_pro_eliminar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_det_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleIngreso = listaProductosCache[index].idDetalleIngreso;
				codigo = codigo + idDetalleIngreso + '_';
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
						arrIdDetalleIngreso : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/orden-ingreso/eliminarProductoOrdenIngreso', params, function(respuesta) {
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
			var val_producto = $('#sel_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}			
			var params = { 
				idDetalleIngreso : $('#hid_cod_producto').val(),
				idIngreso : $('#hid_cod_ord_ingreso').val(),
				idProducto : idProducto,
				cantidad : formatMonto($('#txt_cantidad').val()),
				precioUnitario : formatMonto($('#txt_pre_unitario').val()),
				importeTotal : formatMonto($('#txt_imp_total').val()),
				fechaVencimiento : $('#txt_fec_vencimiento').val(),
				nroLote : $('#sel_lote').val()
			};

			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarProductoOrdenIngreso', params, function(respuesta) {
				$('#div_det_productos').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoOrdenIngreso(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_productos.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_producto').click(function(e) {
		e.preventDefault();
		frm_det_productos.data('bootstrapValidator').resetForm();
	});
	
	$('#sel_cat_producto').change(function() {
		var idCategoria = $(this).val();		
		if (!esnulo(idCategoria)) {					
			cargarProducto(idCategoria, null, null);
		} else {
			$('#sel_producto').html('');
			frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_producto');
		}
	});
	
	$('#sel_producto').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_uni_medida').val(arr[1]);
				if (!esnulo(arr[2])) {
					$('#txt_envase').val(arr[2]);
				} else {
					$('#txt_envase').val('');
				}
			} else {
				$('#txt_uni_medida').val('');
				$('#txt_envase').val('');
			}
			cargarLote(arr[0], null);
		} else {
			$('#txt_uni_medida').val('');
			$('#txt_envase').val('');
		}
//		frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
	});
	
	
	$('#txt_cantidad').change(function() {
		var cantidad = $(this).val();
		var pre_unitario = $('#txt_pre_unitario').val();
		if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
			var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
			$('#txt_imp_total').val(formatMontoAll(imp_total));
		} else {
			$('#txt_imp_total').val('');
		}
	});
	
	$('#txt_pre_unitario').change(function() {
		var cantidad = $('#txt_cantidad').val();
		var pre_unitario = $(this).val();
		if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
			var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
			$('#txt_imp_total').val(formatMontoAll(imp_total));
		} else {
			$('#txt_imp_total').val('');
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
			
			$('#hid_cod_documento').val(obj.idDocumentoIngreso);			
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
		var codigo = '';
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDocumentoIngreso = listaDocumentosCache[index].idDocumentoIngreso;
				codigo = codigo + idDocumentoIngreso + '_';
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
						arrIdDocumentoIngreso : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/orden-ingreso/eliminarDocumentoOrdenIngreso', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoOrdenIngreso(true);
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
				idDocumentoIngreso : $('#hid_cod_documento').val(),
				idIngreso : $('#hid_cod_ord_ingreso').val(),
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
		
		$('#txt_anio').val(ordenIngreso.codigoAnio);
		$('#txt_ddi').val(ordenIngreso.nombreDdi);
		$('#txt_almacen').val(ordenIngreso.nombreAlmacen);
		
		if (!esnulo(ordenIngreso.idIngreso)) {
			
			$('#hid_cod_ord_ingreso').val(ordenIngreso.idIngreso);
			$('#txt_nro_ord_ingreso').val(ordenIngreso.codigoAnio+'-'+ordenIngreso.codigoDdi+'-'+ordenIngreso.nroOrdenIngreso);

			$('#txt_fecha').val(ordenIngreso.fechaEmision);
			$('#sel_estado').val(ordenIngreso.idEstado);
			$('#sel_tip_movimiento').val(ordenIngreso.idMovimiento);
			$('#sel_nro_ord_compra').val(ordenIngreso.nroOrdenCompra);			
			$('#sel_com_por').val(ordenIngreso.flagTipoCompra);			
			$('input[name=rb_tie_nro_rep_con_calidad][value="'+ordenIngreso.flagControlCalidad+'"]').prop('checked', true);
			$('#sel_nro_con_calidad').val(ordenIngreso.idControlCalidad);
			$('#sel_proveedor').val(ordenIngreso.provRep);
			$('#txt_representante').val(ordenIngreso.responsable);
			$('#sel_almacen').val(ordenIngreso.idAlmacenProcedencia);
			$('#sel_med_transporte').val(ordenIngreso.idMedioTransporte);
			$('#sel_emp_transporte').val(ordenIngreso.idEmpresaTransporte);
			$('#txt_fec_llegada').val(ordenIngreso.fechaLlegada);
			$('#sel_chofer').val(ordenIngreso.idChofer);
			$('#txt_nro_placa').val(ordenIngreso.nroPlaca);
			$('#sel_responsable').val(ordenIngreso.idResponsable);
			$('#txt_observaciones').val(ordenIngreso.observacion);
			
			$('#sel_nro_ord_compra').select2().trigger('change');

			cargarTipoMovimiento(ordenIngreso.idMovimiento, false);
			
			listarProductoOrdenIngreso(false);			
			listarDocumentoOrdenIngreso(false);
			
			$('#txt_det_nro_ord_compra').val(ordenIngreso.nroOrdenCompra);
			
		} else {
			
			$('#txt_nro_ord_ingreso').val(ordenIngreso.nroOrdenIngreso);
			
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
			
			$('#sel_nro_ord_compra').select2().trigger('change');
			
			cargarTipoMovimiento($('#sel_tip_movimiento').val(), true);
			
			listarDetalleProductos(new Object());
			listarDetalleDocumentos(new Object());

		}
		
	}
		
}

function listarProductoOrdenIngreso(indicador) {
	var params = { 
		idIngreso : $('#hid_cod_ord_ingreso').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/orden-ingreso/listarProductoOrdenIngreso', params, function(respuesta) {
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
			data : 'idDetalleIngreso',
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
			data : 'idDetalleIngreso',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'nroLote'
		}, {
			data : 'cantidad'
		}, {
			data : 'precioUnitario'
		}, {
			data : 'importeTotal'
		}, {
			data : 'fechaVencimiento'
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

function listarDocumentoOrdenIngreso(indicador) {
	var params = { 
		idIngreso : $('#hid_cod_ord_ingreso').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/orden-ingreso/listarDocumentoOrdenIngreso', params, function(respuesta) {
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
			data : 'idDocumentoIngreso',
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
			data : 'idDocumentoIngreso',
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
	consultarAjax('POST', '/gestion-almacenes/orden-ingreso/grabarDocumentoOrdenIngreso', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoOrdenIngreso(true);
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

function cargarTipoMovimiento(val_tip_movimiento, indicador) {
	
	$('#div_det_nro_ord_compra').hide();
	
	$('#txt_det_ord_compra').val('');
	
	if (val_tip_movimiento == '9') { // Ajuste por Importe
		if ($('#sel_nro_ord_compra').hasClass('select2-hidden-accessible')) {
			$('#sel_nro_ord_compra').select2('destroy');
		}		
		$('#sel_nro_ord_compra').val('');
		$('#sel_nro_ord_compra').prop('disabled', true);
		
		$('#sel_com_por').prop('disabled', true);
		$('input[name=rb_tie_nro_rep_con_calidad]').prop('disabled', true);		
		$('#sel_nro_con_calidad').prop('disabled', true);		
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_almacen').prop('disabled', true);
		$('#sel_med_transporte').prop('disabled', true);		
		$('#sel_emp_transporte').prop('disabled', true);
		$('#txt_fec_llegada').prop('disabled', true);
		if (!$('#txt_fec_llegada').hasClass('mod-readonly')) {
			$('#txt_fec_llegada').addClass('mod-readonly');
		}		
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_movimiento == '1') { // Ajustes por inventario
		if ($('#sel_nro_ord_compra').hasClass('select2-hidden-accessible')) {
			$('#sel_nro_ord_compra').select2('destroy');
		}		
		$('#sel_nro_ord_compra').val('');
		$('#sel_nro_ord_compra').prop('disabled', true);
		
		$('#sel_com_por').prop('disabled', true);
		$('input[name=rb_tie_nro_rep_con_calidad]').prop('disabled', true);		
		$('#sel_nro_con_calidad').prop('disabled', true);		
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_almacen').prop('disabled', true);
		$('#sel_med_transporte').prop('disabled', true);		
		$('#sel_emp_transporte').prop('disabled', true);
		$('#txt_fec_llegada').prop('disabled', true);
		if (!$('#txt_fec_llegada').hasClass('mod-readonly')) {
			$('#txt_fec_llegada').addClass('mod-readonly');
		}
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_movimiento == '8') { // Compra
		$('#sel_nro_ord_compra').prop('disabled', false);
		$('#sel_nro_ord_compra').select2().trigger('change');
		if (indicador) {
			$('#sel_nro_ord_compra').val($('#sel_nro_ord_compra option:first').val());
		}
		var arr = $('#sel_nro_ord_compra').val().split('_');
		if (arr.length > 1) {
			$('#txt_det_ord_compra').val(arr[1]);
		}

		$('#sel_com_por').prop('disabled', false);
		$('input[name=rb_tie_nro_rep_con_calidad]').prop('disabled', false);		
		$('#sel_nro_con_calidad').prop('disabled', false);		
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_almacen').prop('disabled', true);
		$('#sel_med_transporte').prop('disabled', false);		
		$('#sel_emp_transporte').prop('disabled', false);
		$('#txt_fec_llegada').prop('disabled', false);
		if ($('#txt_fec_llegada').hasClass('mod-readonly')) {
			$('#txt_fec_llegada').removeClass('mod-readonly');
		}
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
		
		$('#div_det_nro_ord_compra').show();
		
	} else if (val_tip_movimiento == '7') { // Inventario Inicial
		if ($('#sel_nro_ord_compra').hasClass('select2-hidden-accessible')) {
			$('#sel_nro_ord_compra').select2('destroy');
		}		
		$('#sel_nro_ord_compra').val('');
		$('#sel_nro_ord_compra').prop('disabled', true);
		
		$('#sel_com_por').prop('disabled', true);
		$('input[name=rb_tie_nro_rep_con_calidad]').prop('disabled', true);		
		$('#sel_nro_con_calidad').prop('disabled', true);		
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_almacen').prop('disabled', true);
		$('#sel_med_transporte').prop('disabled', true);		
		$('#sel_emp_transporte').prop('disabled', true);
		$('#txt_fec_llegada').prop('disabled', true);
		if (!$('#txt_fec_llegada').hasClass('mod-readonly')) {
			$('#txt_fec_llegada').addClass('mod-readonly');
		}
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_movimiento == '4') { // Transferencia Interna
		if ($('#sel_nro_ord_compra').hasClass('select2-hidden-accessible')) {
			$('#sel_nro_ord_compra').select2('destroy');
		}		
		$('#sel_nro_ord_compra').val('');
		$('#sel_nro_ord_compra').prop('disabled', true);
		
		$('#sel_com_por').prop('disabled', true);
		$('input[name=rb_tie_nro_rep_con_calidad]').prop('disabled', false);		
		$('#sel_nro_con_calidad').prop('disabled', false);		
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_almacen').prop('disabled', false);
		$('#sel_med_transporte').prop('disabled', false);		
		$('#sel_emp_transporte').prop('disabled', false);
		$('#txt_fec_llegada').prop('disabled', false);
		if ($('#txt_fec_llegada').hasClass('mod-readonly')) {
			$('#txt_fec_llegada').removeClass('mod-readonly');
		}
		
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_movimiento == '3') { // Transferencia entre Almacenes
		if ($('#sel_nro_ord_compra').hasClass('select2-hidden-accessible')) {
			$('#sel_nro_ord_compra').select2('destroy');
		}		
		$('#sel_nro_ord_compra').val('');
		$('#sel_nro_ord_compra').prop('disabled', true);
		
		$('#sel_com_por').prop('disabled', true);
		$('input[name=rb_tie_nro_rep_con_calidad]').prop('disabled', false);		
		$('#sel_nro_con_calidad').prop('disabled', false);		
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_almacen').prop('disabled', false);
		$('#sel_med_transporte').prop('disabled', false);		
		$('#sel_emp_transporte').prop('disabled', false);
		$('#txt_fec_llegada').prop('disabled', false);
		if ($('#txt_fec_llegada').hasClass('mod-readonly')) {
			$('#txt_fec_llegada').removeClass('mod-readonly');
		}
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	}
}

function cargarProducto(idCategoria, codigoProducto, codigoLote) {
	var params = { 
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/control-calidad/listarProductoXCategoria', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'_'+item.nombreEnvase+'">'+item.nombreProducto+'</option>';
	        });
	        $('#sel_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_producto').val(codigoProducto);
				cargarLote(codigoProducto, codigoLote);				
	        } else {
	        	var arr = $('#sel_producto').val().split('_');
				if (arr.length > 1) {
					$('#txt_uni_medida').val(arr[1]);
					if (!esnulo(arr[2])) {
						$('#txt_envase').val(arr[2]);
					} else {
						$('#txt_envase').val('');
					}
				} else {
					$('#txt_uni_medida').val('');
					$('#txt_envase').val('');
				}
//				frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
	        }
	        $('#sel_producto').select2().trigger('change');
			$('#sel_producto').select2({
				  dropdownParent: $('#div_pro_det_productos')
			});
		}
		loadding(false);		
	});
}

function cargarLote(idProducto, codigoLote) {
	var params = { 
		idProducto : idProducto
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/orden-ingreso/listarLoteProducto', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.nroLote+'">'+item.nroLote+'</option>';
	        });
	        $('#sel_lote').html(options);
	        if (codigoLote != null) {
	        	$('#sel_lote').val(codigoLote);       	
	        }
		}
		loadding(false);		
	});
}

function limpiarFormularioProducto() {
	$('#sel_cat_producto').val('');
	$('#sel_producto').val('');
	$('#sel_lote').val('');
	$('#txt_uni_medida').val('');
	$('#txt_envase').val('');
	$('#txt_fec_vencimiento').val('');
	$('#txt_cantidad').val('');
	$('#txt_pre_unitario').val('');
	$('#txt_imp_total').val('');
}
