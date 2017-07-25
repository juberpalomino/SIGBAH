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
			  dropdownParent: $('#div_pro_det_alimentarios')
		});
		
		$('#hid_cod_producto').val('');
		$('#div_det_alimentarios').modal('show');
		
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
				idDetalleCartilla : $('#hid_cod_producto').val(),
				idCartilla : $('#hid_cod_cartilla').val(),
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
			
			consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/grabarProductoCartillaInventario', params, function(respuesta) {
				$('#div_det_alimentarios').modal('hide');
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
		frm_det_ajustes.trigger('reset');
		
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
			
			$('#h4_tit_no_alimentarios').html('Actualizar Producto');
			frm_det_ajustes.trigger('reset');
			
			$('#hid_cod_no_producto').val(obj.idDetalleCartilla);
			
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
		tbl_det_ajustes.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_ajustes.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleCartilla = listaAjustesCache[index].idDetalleCartilla;
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
	
	$('#btn_gra_no_alimentario').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_ajustes.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var idProducto = null;
			var val_producto = $('#sel_no_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}
			var params = { 
				idDetalleCartilla : $('#hid_cod_no_producto').val(),
				idCartilla : $('#hid_cod_cartilla').val(),
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
			
			consultarAjax('POST', '/gestion-almacenes/cartilla-inventario/grabarProductoCartillaInventario', params, function(respuesta) {
				$('#div_det_no_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoCartillaInventario(true);
					addSuccessMessage(null, respuesta.mensajeRespuesta);	
				}
				frm_det_ajustes.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_no_alimentario').click(function(e) {
		e.preventDefault();
		frm_det_ajustes.data('bootstrapValidator').resetForm();
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
//			listarDetalleNoAlimentarios(new Object());
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
			data : 'item'
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

function listarDetalleNoAlimentarios(respuesta) {

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
	
	listaAjustesCache = respuesta;

}

function cargarProductoNoAlimentario(idCategoria, codigoProducto) {
	var params = { 
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/cartilla-inventario/listarProductoXCategoria', params, function(respuesta) {
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
	        	frm_det_ajustes.bootstrapValidator('revalidateField', 'sel_no_producto');
	        }
		}
		loadding(false);		
	});
}