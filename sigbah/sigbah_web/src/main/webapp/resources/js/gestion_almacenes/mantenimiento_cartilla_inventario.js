var listaProductosCache = new Object();
var listaAjustesCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_productos = $('#tbl_det_productos');
var frm_det_productos = $('#frm_det_productos');

var tbl_det_ajustes = $('#tbl_det_ajustes');
var frm_det_ajustes = $('#frm_det_ajustes');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#txt_fec_emision').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_cartilla').val();
			var params = {
				idCartilla : codigo,	
				idAlmacen : cartillaInventario.idAlmacen,
				codigoAnio : cartillaInventario.codigoAnio,
				codigoDdi : cartillaInventario.codigoDdi,
				idDdi : cartillaInventario.idDdi, 
				codigoAlmacen : cartillaInventario.codigoAlmacen,
				nroCartilla : $('#txt_nro_cartilla').val(),
				idResponsable : $('#sel_res_inventario').val(),
				fechaCartilla : $('#txt_fec_emision').val(),
				observacion : $('#txt_observaciones').val()
			};
			
			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/grabarCartillaInventario', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_cartilla').val(respuesta.idCartilla);
						$('#txt_nro_cartilla').val(respuesta.codigoCartilla);
						
						$('#li_productos').attr('class', '');
						$('#li_productos').closest('li').children('a').attr('data-toggle', 'tab');
						$('#li_estados').attr('class', '');
						$('#li_estados').closest('li').children('a').attr('data-toggle', 'tab');

						addSuccessMessage(null, 'Se genero el N° Cartilla: '+respuesta.codigoCartilla);
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/cartilla-inventario/inicio/1';
		$(location).attr('href', url);
		
	});
	
	$('#href_pro_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_alimentarios').html('Nuevo Producto');
		frm_det_productos.trigger('reset');
		
		$('#sel_producto').select2().trigger('change');
		$('#sel_producto').select2({
			  dropdownParent: $('#div_pro_det_productos')
		});
		
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
			
			$('#h4_tit_alimentarios').html('Actualizar Producto');
			frm_det_productos.trigger('reset');
			
			$('#hid_cod_producto').val(obj.idDetalleCartilla);
			
			var val_producto = obj.idProducto+'_'+obj.codigoProducto+'_'+obj.nombreUnidad+'_'+obtieneParametro(obj.nombreEnvase);
			$('#sel_producto').val(val_producto);

			$('#sel_producto').select2().trigger('change');
			$('#sel_producto').select2({
				  dropdownParent: $('#div_pro_det_productos')
			});
			
			$('#txt_pro_codigo').val(obj.codigoProducto);
			$('#txt_pro_uni_medida').val(obj.nombreUnidad);
			$('#txt_pro_envase').val(obj.nombreEnvase);
			
			cargarLoteProducto(obj.idProducto, obj.nroLote+'_'+obj.cantidadStock+'_'+obj.precioUnitario);
			
			$('#txt_pro_can_stock').val(obj.cantidadStock);
			$('#txt_pro_pre_unitario').val(obj.precioUnitario);
			
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
				var idDetalleCartilla = listaProductosCache[index].idDetalleCartilla;
				codigo = codigo + idDetalleCartilla + '_';
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
						arrIdDetalleCartillaInventario : codigo
					};
			
					consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/eliminarProductoCartillaInventario', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoCartillaInventario(true);
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
			
			var nroLote = null;
			var val_lote = $('#sel_pro_lote').val();
			if (!esnulo(val_lote)) {
				var arr_lote = val_lote.split('_');
				nroLote = arr_lote[0];
			}
			
			var params = { 
				idDetalleCartilla : $('#hid_cod_producto').val(),
				idCartilla : $('#hid_cod_cartilla').val(),
				idDdi : cartillaInventario.idDdi,
				idAlmacen : cartillaInventario.idAlmacen,
				idProducto : idProducto,
				nroLote : nroLote,
				cantidadStock : formatMonto($('#txt_pro_can_stock').val())
			};

			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/grabarProductoCartillaInventario', params, function(respuesta) {
				$('#div_det_productos').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoCartillaInventario(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_productos.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_producto, #btn_clo_productos').click(function(e) {
		e.preventDefault();
		frm_det_productos.data('bootstrapValidator').resetForm();
	});
	
	$('#sel_producto').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_pro_codigo').val(arr[1]);
				$('#txt_pro_uni_medida').val(arr[2]);
				$('#txt_pro_envase').val(arr[3]);
				$('#txt_pro_can_stock').val('');
				$('#txt_pro_pre_unitario').val('');
			} else {
				$('#txt_pro_codigo').val('');
				$('#txt_pro_uni_medida').val('');
				$('#txt_pro_envase').val('');
				$('#txt_pro_can_stock').val('');
				$('#txt_pro_pre_unitario').val('');
			}
			cargarLoteProducto(arr[0], null);
		} else {
			$('#txt_pro_codigo').val('');
			$('#txt_pro_uni_medida').val('');
			$('#txt_pro_envase').val('');
			$('#txt_pro_can_stock').val('');
			$('#txt_pro_pre_unitario').val('');
		}
	});
	
	$('#sel_pro_lote').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_pro_can_stock').val(arr[1]);
				$('#txt_pro_pre_unitario').val(arr[2]);
			} else {
				$('#txt_pro_can_stock').val('');
				$('#txt_pro_pre_unitario').val('');
			}			
		} else {
			$('#txt_pro_can_stock').val('');
			$('#txt_pro_pre_unitario').val('');
		}
	});
	
	$('#btn_agr_productos').click(function(e) {
		e.preventDefault();

		$.SmartMessageBox({
			title : 'Está usted seguro ?',
			content : '',
			buttons : '[No][Si]'
		}, function(ButtonPressed) {
			if (ButtonPressed === 'Si') {

				loadding(true);
				
				var params = { 
					idCartilla : $('#hid_cod_cartilla').val(),
					idDdi : cartillaInventario.idDdi,
					idAlmacen : cartillaInventario.idAlmacen,
				};
		
				consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/procesarProductosCartillaInventario', params, function(respuesta) {
					if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
						loadding(false);
						addErrorMessage(null, respuesta.mensajeRespuesta);
					} else {
						listarProductoCartillaInventario(true);
						addSuccessMessage(null, respuesta.mensajeRespuesta);							
					}
				});
				
			}	
		});

	});
	
	$('#href_aju_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_ajustes.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_ajustes.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaAjustesCache[indices[0]];
			
			frm_det_ajustes.trigger('reset');
			
			$('#hid_aju_cod_producto').val(obj.idDetalleCartilla);
			
			$('#txt_producto').val(obj.nombreProducto);
			$('#txt_aju_codigo').val(obj.codigoProducto);
			$('#txt_aju_uni_medida').val(obj.nombreUnidad);
			$('#txt_aju_envase').val(obj.nombreEnvase);			
			$('#txt_lote').val(obj.nroLote);
			$('#txt_aju_sto_sistema').val(obj.stockSistema);
			$('#txt_aju_sto_fisico').val(obj.stockFisico);
			$('#txt_aju_diferencia').val(obj.diferencia);
			
			$('#div_det_ajustes').modal('show');
		}
		
	});
	
	$('#btn_gra_ajuste').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_ajustes.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var params = { 
				idDetalleCartilla : $('#hid_aju_cod_producto').val(),
				stockFisico : formatMonto($('#txt_aju_sto_fisico').val())
			};

			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/actualizarAjusteProductoCartillaInventario', params, function(respuesta) {
				$('#div_det_ajustes').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarAjusteProductoCartillaInventario(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_ajustes.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_ajuste, #btn_clo_ajustes').click(function(e) {
		e.preventDefault();
		frm_det_ajustes.data('bootstrapValidator').resetForm();
	});
	
	$('#txt_aju_sto_fisico').change(function() {
		var sto_fisico = $(this).val();
		var sto_sistema = $('#txt_aju_sto_sistema').val();
		if (!esnulo(sto_fisico) && !esnulo(sto_sistema)) {
			var diferencia = parseInt(sto_fisico) - parseInt(sto_sistema);
			$('#txt_aju_diferencia').val(formatMontoSinComas(diferencia));
		} else {
			$('#txt_aju_diferencia').val('');
		}
	});
	
	$('#href_pro_ajuste').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_ajustes.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_ajustes.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaAjustesCache[indices[0]];
			var msj = '';
			
			if (!esnulo(obj.diferencia)) {
				if (parseInt(obj.diferencia) == 0) {
					msj = msj + 'No se puede efectuar ajuste, debido a que la Diferencia es cero.<br>';
				}				
			} else {
				msj = msj + 'No se puede efectuar ajuste, debido a que no dispone Diferencia.<br>';
			}
			
			if (!esnulo(obj.stockFisico)) {
				if (parseFloat(obj.stockFisico) < 0) {
					msj = msj + 'No se puede efectuar ajuste, debido a que el Stock Físico es menor a cero.<br>';
				}				
			} else {
				msj = msj + 'No se puede efectuar ajuste, debido a que no dispone Stock Físico.<br>';
			}
			
			if (!esnulo(obj.documentoAjuste)) {
				msj = msj + 'No se puede efectuar ajuste, debido a que ya dispone de un Nro. Documento Ajuste.<br>';			
			}
			
			if (!esnulo(msj)) {
				addWarnMessage(null, msj);
				return;
			}
			
			$.SmartMessageBox({
				title : 'Está usted seguro de efectuar el Ajuste ?',
				content : '',
				buttons : '[No][Si]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Si') {

					loadding(true);
					
					var params = { 
						idCartilla : $('#hid_cod_cartilla').val(),
						idDdi : cartillaInventario.idDdi,
						idAlmacen : cartillaInventario.idAlmacen,
					};
			
					consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/procesarAjusteProductoCartillaInventario', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoCartillaInventario(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
});

function inicializarDatos() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#ul_alm_inventarios').css('display', 'block');	
	$('#li_car_inventario').attr('class', 'active');
	$('#li_car_inventario').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {		
		
		$('#txt_ddi').val(cartillaInventario.nombreDdi);
		$('#txt_almacen').val(cartillaInventario.nombreAlmacen);
		$('#txt_estado').val(cartillaInventario.nombreEstado);
		
		if (!esnulo(cartillaInventario.idCartilla)) {
			
			$('#hid_cod_cartilla').val(cartillaInventario.idCartilla);
			
			$('#txt_nro_cartilla').val(cartillaInventario.codigoCartilla);
			$('#txt_fec_emision').val(cartillaInventario.fechaCartilla);
			$('#sel_res_inventario').val(cartillaInventario.idResponsable);
			$('#txt_observaciones').val(cartillaInventario.observacion);
					
			if (cartillaInventario.idEstado != ESTADO_GENERADO) {
				$('#btn_agr_productos').prop('disabled', true);
				$('#href_pro_nuevo').attr('data-original-title', '');
				$('#href_pro_nuevo').attr('id', 'href_nuevo');
			}
			
			if (cartillaInventario.idEstado == ESTADO_APROBADO || 
					cartillaInventario.idEstado == ESTADO_AJUSTE) {
				listarAjusteProductoCartillaInventario(false);
			} else {
				$('#li_ajustes').addClass('disabled');
				$('#ul_man_car_inventario li.disabled a').removeAttr('data-toggle');
			}
			
			listarProductoCartillaInventario(false);		
//			listarDocumentoCartillaInventario(false);
			
		} else {
			
			$('#li_productos').addClass('disabled');
			$('#li_ajustes').addClass('disabled');
			$('#li_estados').addClass('disabled');
			$('#ul_man_car_inventario li.disabled a').removeAttr('data-toggle');
			
			$('#txt_nro_cartilla').val(cartillaInventario.nroCartilla);
			$('#txt_fecha').datepicker('setDate', new Date());

			listarProductoCartillaInventario(new Object());
//			listarDetalleDocumentos(new Object());

		}
		
	}
	
}

function listarProductoCartillaInventario(indicador) {
	var params = { 
		idCartilla : $('#hid_cod_cartilla').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/cartilla-inventario/listarProductoCartillaInventario', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProducto(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleProducto(respuesta) {

	tbl_det_productos.dataTable().fnDestroy();
	
	tbl_det_productos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleCartilla',
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
			data : 'idDetalleCartilla',
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
			data : 'nave'
		}, {
			data : 'precioUnitario'
		}, {
			data : 'cantidadStock'
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

function listarAjusteProductoCartillaInventario(indicador) {
	var params = { 
		idCartilla : $('#hid_cod_cartilla').val()
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/cartilla-inventario/listarProductoCartillaInventario', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarAjusteDetalleProducto(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarAjusteDetalleProducto(respuesta) {

	tbl_det_ajustes.dataTable().fnDestroy();
	
	tbl_det_ajustes.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleCartilla',
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
			data : 'idDetalleCartilla',
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
			data : 'stockSistema'
		}, {
			data : 'stockFisico'
		}, {
			data : 'diferencia'
		}, {
			data : 'tipo'
		}, {
			data : 'documentoAjuste'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaAjustesCache = respuesta;

}

function cargarLoteProducto(idProducto, nroLote) {
	var params = {
		idDdi : cartillaInventario.idDdi,
		idAlmacen : cartillaInventario.idAlmacen,
		idProducto : idProducto
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/cartilla-inventario/listarStockAlmacenProductoLote', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.nroLote+'_'+item.cantidadStock+'_'+item.precioUnitario+'">'+item.nroLote+'</option>';
	        });
	        $('#sel_pro_lote').html(options);
	        if (nroLote != null) {
	        	$('#sel_pro_lote').val(nroLote);
	        }
	        frm_det_productos.bootstrapValidator('revalidateField', 'sel_pro_lote');
		}
		loadding(false);		
	});
}