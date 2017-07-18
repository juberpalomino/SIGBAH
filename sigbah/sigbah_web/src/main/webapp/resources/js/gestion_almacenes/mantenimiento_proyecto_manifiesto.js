var listaProductosCache = new Object();
var listaVehiculosCache = new Object();
var listaDocumentosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_productos = $('#tbl_det_productos');
var frm_det_productos = $('#frm_det_productos');

var tbl_det_vehiculos = $('#tbl_det_vehiculos');

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
		    if (mes != proyectoManifiesto.codigoMes || anio != proyectoManifiesto.codigoAnio) {
		    	$('#hid_val_fec_trabajo').val('0');
		    	addWarnMessage(null, 'Está ingresando una fecha con año diferente al mes de cierre.');
		    	$('#'+$(this).attr('id')).focus();
		    } else {
		    	$('#hid_val_fec_trabajo').val('1');
		    }
		}
		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#txt_doc_fecha').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_det_documentos.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('input[type=radio][name=rb_man_tie_programacion]').change(function() {
		cargarIndicadorProgramacion(this.value);
    });
	
	$('#sel_nro_programacion').change(function() {
		var codigo = $(this).val();
		var idProyectoManifiesto = $('#hid_cod_proyecto').val();
		if (!esnulo(codigo) && !esnulo(idProyectoManifiesto)) {			
			var params = {
				idProyectoManifiesto : idProyectoManifiesto
			};
			consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/verificarProductosProgramacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var indicador = parseInt(respuesta);
					if (indicador > 0) {					
						$.SmartMessageBox({
							title : 'Se va reemplazar los productos de la programación.<br>Está usted seguro ?',
							content : '',
							buttons : '[NO][SI]'
						}, function(ButtonPressed) {
							if (ButtonPressed === 'SI') {				
								loadding(true);								
								var params_proy = { 
									idProyectoManifiesto : idProyectoManifiesto,
									idProgramacion : codigo
								};						
								consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/procesarProyectoManifiesto', params_proy, function(respuesta) {
									if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
										loadding(false);
										addErrorMessage(null, respuesta.mensajeRespuesta);
									} else {
										listarProductoProyectoManifiesto(true);
										addSuccessMessage(null, respuesta.mensajeRespuesta);							
									}
								});							
							}	
						});
					}
				}
			});
		}
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			if ($('#hid_val_fec_trabajo').val() == '0') {
		    	addWarnMessage(null, 'Está ingresando una fecha con año diferente al mes de cierre.');
		    	return;
			}
			
			var codigo = $('#hid_cod_proyecto').val();
			var flagProgramacion = $('input[name="rb_man_tie_programacion"]:checked').val();
			var params = {
				idProyectoManifiesto : codigo,
				codigoAnio : $('#txt_anio').val(),
				codigoDdi : proyectoManifiesto.codigoDdi,
				codigoMes : proyectoManifiesto.codigoMes,
				idAlmacen : proyectoManifiesto.idAlmacen,				
				codigoAlmacen : proyectoManifiesto.codigoAlmacen,
				idAlmacenDestino : $('#sel_almacen').val(),
				nroProyectoManifiesto : $('#txt_nro_pro_manifiesto').val(),
				fechaEmision : $('#txt_fecha').val(),
				flagProgramacion : flagProgramacion,
				idProgramacion : $('#sel_nro_programacion').val(),
				idMovimiento : $('#sel_tip_movimiento').val(),
				idEstado : $('#sel_estado').val(),
				observacion : $('#txt_observaciones').val()
			};
			
			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/grabarProyectoManifiesto', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_proyecto').val(respuesta.idProyectoManifiesto);
						$('#txt_nro_pro_manifiesto').val(respuesta.codigoProyectoManifiesto);
				
						$('#li_productos').attr('class', '');
						$('#li_productos').closest('li').children('a').attr('data-toggle', 'tab');

						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
						
						addSuccessMessage(null, 'Se creó el Proyecto de Manifiesto N° '+respuesta.codigoProyectoManifiesto);
						
						listarVehiculoProyectoManifiesto(false);
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/proyecto-manifiesto/inicio/1';
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
			
			$('#hid_cod_producto').val(obj.idDetalleProyecto);
			
			$('#sel_cat_producto').val(obj.idCategoria);
			
			var val_producto = obj.idProducto+'_'+obj.nombreUnidad+'_'+obj.pesoUnitarioBruto+'_'+obj.volumenUnitario;
			cargarProducto(obj.idCategoria, val_producto);

			$('#txt_uni_medida').val(obj.nombreUnidad);
			$('#txt_pes_net_unitario').val(obj.pesoUnitarioBruto);
			$('#txt_vol_unitario').val(obj.volumenUnitario);
			$('#txt_cantidad').val(obj.cantidad);
			
			$('#txt_imp_total').val(obj.pesoTotal);
			
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
				var idDetalleProyecto = listaProductosCache[index].idDetalleProyecto;
				codigo = codigo + idDetalleProyecto + '_';
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
						arrIdDetalleProyectoManifiesto : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/eliminarProductoProyectoManifiesto', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoProyectoManifiesto(true);
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
			var idDetalleProyecto = $('#hid_cod_producto').val();
			var params = { 
				idDetalleProyecto : idDetalleProyecto,
				idProyectoManifiesto : $('#hid_cod_proyecto').val(),
				idProducto : idProducto,
				cantidad : formatMonto($('#txt_cantidad').val()),
				idAlmacen : proyectoManifiesto.idAlmacen
			};

			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/grabarProductoProyectoManifiesto', params, function(respuesta) {
				$('#div_det_productos').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoProyectoManifiesto(true);					
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
			cargarProducto(idCategoria, null);
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
					$('#txt_pes_net_unitario').val(arr[2]);
				} else {
					$('#txt_pes_net_unitario').val('');
				}
				if (!esnulo(arr[3])) {
					$('#txt_vol_unitario').val(arr[3]);
				} else {
					$('#txt_vol_unitario').val('');
				}
			} else {
				$('#txt_uni_medida').val('');
				$('#txt_pes_net_unitario').val('');
				$('#txt_vol_unitario').val('');
			}
		} else {
			$('#txt_uni_medida').val('');
			$('#txt_pes_net_unitario').val('');
			$('#txt_vol_unitario').val('');
		}
//		frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
	});
	
	
	$('#txt_cantidad').change(function() {
		var cantidad = $(this).val();
		var pre_unitario = $('#txt_pes_net_unitario').val();
		if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
			var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
			$('#txt_imp_total').val(formatMontoAll(imp_total));
		} else {
			$('#txt_imp_total').val('');
		}
	});
	
	$('#btn_recalcular').click(function(e) {
		e.preventDefault();

		var indices = [];		
		var arrFlagVehiculo= [];	
		var arrIdTipoCamion = [];
		var arrVolumen = [];
		tbl_det_vehiculos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_vehiculos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				arrFlagVehiculo.push(listaVehiculosCache[index].flagVehiculo);
				arrIdTipoCamion.push(listaVehiculosCache[index].idTipoCamion);
				arrVolumen.push(listaVehiculosCache[index].volumen);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {		
			loadding(true);
			var params = {
				idProyectoManifiesto : $('#hid_cod_proyecto').val(),
				arrFlagVehiculo : arrFlagVehiculo,
				arrIdTipoCamion : arrIdTipoCamion,
				arrVolumen : arrVolumen
			};
			consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/procesarManifiestoVehiculo', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoProyectoManifiesto(true);
					addSuccessMessage(null, respuesta.mensajeRespuesta);							
				}
			});	
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
			
			$('#hid_cod_documento').val(obj.idDocumentoProyecto);			
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
				var idDocumentoProyecto = listaDocumentosCache[index].idDocumentoProyecto;
				codigo = codigo + idDocumentoProyecto + '_';
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
						arrIdDocumentoProyectoManifiesto : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/eliminarDocumentoProyectoManifiesto', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoProyectoManifiesto(true);
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
			
			var idDocumentoProyecto = $('#hid_cod_documento').val();
			
			var params = { 
				idDocumentoProyecto : idDocumentoProyecto,
				idProyectoManifiesto : $('#hid_cod_proyecto').val(),
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
	$('#ul_alm_salidas').css('display', 'block');	
	$('#li_man_carga').attr('class', 'active');
	$('#li_man_carga').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_nro_pro_manifiesto').val(proyectoManifiesto.nroProyectoManifiesto);
		$('#txt_anio').val(proyectoManifiesto.codigoAnio);
		$('#txt_ddi').val(proyectoManifiesto.nombreDdi);
		$('#txt_almacen').val(proyectoManifiesto.nombreAlmacen);
		
		if (!esnulo(proyectoManifiesto.idProyectoManifiesto)) {
			
			$('#hid_cod_proyecto').val(proyectoManifiesto.idProyectoManifiesto);		

			$('#txt_fecha').val(proyectoManifiesto.fechaEmision);
			$('#sel_estado').val(proyectoManifiesto.idEstado);
			$('#sel_tip_movimiento').val(proyectoManifiesto.idMovimiento);			
			$('input[name=rb_man_tie_programacion][value="'+proyectoManifiesto.flagProgramacion+'"]').prop('checked', true);			
			$('#sel_nro_programacion').val(proyectoManifiesto.idProgramacion);
			$('#sel_almacen').val(proyectoManifiesto.idAlmacenDestino);
			$('#txt_observaciones').val(proyectoManifiesto.observacion);

			listarProductoProyectoManifiesto(false);
			listarVehiculoProyectoManifiesto(false);
			listarDocumentoProyectoManifiesto(false);
			
		} else {
			
			$('#li_productos').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#ul_man_ord_ingreso li.disabled a').removeAttr('data-toggle');
			
			$('#txt_fecha').datepicker('setDate', new Date());
			
			cargarIndicadorProgramacion(null);

			listarDetalleProductos(new Object());
			listarDetalleDocumentos(new Object());

		}
		
	}
		
}

function listarProductoProyectoManifiesto(indicador) {
	var params = { 
		idProyectoManifiesto : $('#hid_cod_proyecto').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/proyecto-manifiesto/listarProductoProyectoManifiesto', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProductos(respuesta);
			if (indicador) {
				loadding(false);
			}
//			if (respuesta != null && respuesta.length > 0) {
//				$('#sel_tip_movimiento').prop('disabled', true);
//			} else {
//				$('#sel_tip_movimiento').prop('disabled', false);
//			}
		}
	});
}

function listarDetalleProductos(respuesta) {

	tbl_det_productos.dataTable().fnDestroy();
	
	tbl_det_productos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleProyecto',
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
			data : 'idDetalleProyecto',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'nombreUnidad'
		}, {
			data : 'cantidad'
		}, {
			data : 'pesoUnitarioBruto'
		}, {
			data : 'pesoTotal'
		}, {
			data : 'volumenUnitario'
		}, {
			data : 'volumenTotal'
		}, {
			data : 'costoBruto'
		}, {
			data : 'costoTotal'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true,
		'footerCallback' : function ( row, data, start, end, display ) {
			var api = this.api(), data;	 
			
			// Remove the formatting to get integer data for summation
			var intVal = function ( i ) {
				return typeof i === 'string' ? i.replace(/[\$,]/g, '')*1 : typeof i === 'number' ?	i : 0;
			};
 
			// total_page_peso over this page
			total_page_peso = api.column(6, { page: 'current'} ).data().reduce( function (a, b) {
				return intVal(a) + intVal(b);
			}, 0 );
			
			// total_page_peso over this page
			total_page_volumen = api.column(8, { page: 'current'} ).data().reduce( function (a, b) {
				return intVal(a) + intVal(b);
			}, 0 );

			// Update footer
			$('#sp_tot_peso').html(parseFloat(total_page_peso).toFixed(2));
			$('#sp_tot_volumen').html(parseFloat(total_page_volumen).toFixed(2));
		}
	});
	
	listaProductosCache = respuesta;

}

function listarVehiculoProyectoManifiesto(indicador) {
	var params = { 
		idProyectoManifiesto : $('#hid_cod_proyecto').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/proyecto-manifiesto/listarManifiestoVehiculo', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleVehiculos(respuesta);
			if (indicador) {
				loadding(false);
			}
//			if (respuesta != null && respuesta.length > 0) {
//				$('#sel_tip_movimiento').prop('disabled', true);
//			} else {
//				$('#sel_tip_movimiento').prop('disabled', false);
//			}
		}
	});
}

function listarDetalleVehiculos(respuesta) {

	tbl_det_vehiculos.dataTable().fnDestroy();
	
	tbl_det_vehiculos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idProyectoManifiesto',
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
			data : 'idProyectoManifiesto',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'descripcionCamion'
		}, {
			data : 'volumen'
		}, {
			data : 'cantidadVehiculos'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaVehiculosCache = respuesta;

}

function listarDocumentoProyectoManifiesto(indicador) {
	var params = { 
		idProyectoManifiesto : $('#hid_cod_proyecto').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/proyecto-manifiesto/listarDocumentoProyectoManifiesto', params, function(respuesta) {
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
			data : 'idDocumentoProyecto',
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
			data : 'idDocumentoProyecto',
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
	consultarAjax('POST', '/gestion-almacenes/proyecto-manifiesto/grabarDocumentoProyectoManifiesto', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoProyectoManifiesto(true);
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

function cargarProducto(idCategoria, codigoProducto) {
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
				var det_option = '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'_'+item.pesoUnitarioNeto+'_'+item.pesoUnitarioBruto+'">';
				det_option = det_option + item.nombreProducto+'</option>';				
	            options += det_option;
	        });
	        $('#sel_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_producto').val(codigoProducto);      	
	        } else {
	        	var arr = $('#sel_producto').val().split('_');
				if (arr.length > 1) {
					$('#txt_uni_medida').val(arr[1]);
					if (!esnulo(arr[2])) {
						$('#txt_pes_net_unitario').val(arr[2]);
					} else {
						$('#txt_pes_net_unitario').val('');
					}
					if (!esnulo(arr[3])) {
						$('#txt_vol_unitario').val(arr[3]);
					} else {
						$('#txt_vol_unitario').val('');
					}
				} else {
					$('#txt_uni_medida').val('');
					$('#txt_pes_net_unitario').val('');
					$('#txt_vol_unitario').val('');
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

function limpiarFormularioProducto() {
	$('#sel_cat_producto').val('');
	$('#sel_producto').val('');
	$('#txt_uni_medida').val('');
	$('#txt_pes_net_unitario').val('');
	$('#txt_vol_unitario').val('');
	$('#txt_cantidad').val('');
	$('#txt_pre_unitario').val('');
	$('#txt_imp_total').val('');
}

function cargarIndicadorProgramacion(valor) {
	if (valor == '1') {
		$('#sel_nro_programacion').prop('disabled', false);	
	} else if (valor == '0') {
		$('#sel_nro_programacion').prop('disabled', true);
		$('#sel_nro_programacion').val('');
	} else {
		$('#sel_nro_programacion').prop('disabled', true);
		$('#sel_nro_programacion').val('');
	}
}

