var listaLotesCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_lotes = $('#tbl_det_lotes');
var frm_det_lotes = $('#frm_det_lotes');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();

		var params = {
			idAlmacen : stockAlmacen.idAlmacen,
			idDdi : stockAlmacen.idDdi,
			idProducto : stockAlmacen.idProducto,				
			stockSeguridad : formatMonto($('#txt_sto_seguridad').val()),
			codigoAlmacen : usuarioBean.codigoAlmacen,
			idTipoEnvaseSecundario : $('#sel_env_secundario').val(),
			unidadesEnvaseSecundario : formatMonto($('#txt_uni_envase').val()),
			descripcionEnvaseSecundario : $('#txt_des_env_sec').val(),
			observacion : $('#txt_observaciones').val()
		};
		
		loadding(true);
		
		consultarAjax('POST', '/gestion-almacenes/stock-almacen/actualizarStockAlmacen', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				
				addSuccessMessage(null, respuesta.mensajeRespuesta);

			}
			loadding(false);
		});			

	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/stock-almacen/inicio/1';
		$(location).attr('href', url);
		
	});
	
	$('#href_pro_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_lotes.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_lotes.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaLotesCache[indices[0]];
			
			$('#h4_tit_productos').html('Actualizar Producto');
			limpiarFormularioProducto();
			
			$('#hid_cod_producto').val(obj.idDetalleSalida);
			
			$('#sel_cat_producto').val(obj.idCategoria);
			
			var val_producto = obj.idProducto+'_'+obj.nombreUnidad+'_'+obj.pesoUnitarioNeto+'_'+obj.pesoUnitarioBruto;
			cargarProducto(obj.idCategoria, val_producto, obj.nroLote);

			$('#txt_uni_medida').val(obj.nombreUnidad);
			$('#txt_pes_net_unitario').val(obj.pesoUnitarioNeto);
			$('#txt_pes_bru_unitario').val(obj.pesoUnitarioBruto);
			$('#txt_cantidad').val(obj.cantidad);
			$('#txt_pre_unitario').val(obj.precioUnitario);
			$('#txt_imp_total').val(obj.importeTotal);
			
			$('#div_det_lotes').modal('show');
		}
		
	});
	
	$('#btn_gra_lote').click(function(e) {
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
			var indControl = null;
			var idDetalleSalida = $('#hid_cod_producto').val();
			if (esnulo(idDetalleSalida)) {
				indControl = 'I'; // I= INSERT
			} else {
				indControl = 'U'; // U= UPDATE
			}
			var params = { 
				idDetalleSalida : idDetalleSalida,
				idSalida : $('#hid_cod_ord_salida').val(),
				idProducto : idProducto,
				cantidad : formatMonto($('#txt_cantidad').val()),
				precioUnitario : formatMonto($('#txt_pre_unitario').val()),
				importeTotal : formatMonto($('#txt_imp_total').val()),
				nroLote : $('#sel_lote').val(),
				indControl : indControl
			};

			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/stock-almacen/grabarStockAlmacenLote', params, function(respuesta) {
				$('#div_det_lotes').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarStockAlmacenLote(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				frm_det_productos.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});

});

function inicializarDatos() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#ul_alm_inventarios').css('display', 'block');	
	$('#li_sto_productos').attr('class', 'active');
	$('#li_sto_productos').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_almacen').val(stockAlmacen.nombreAlmacen);
		$('#txt_nro_kardex').val(stockAlmacen.nroKardex);
		$('#txt_nom_producto').val(stockAlmacen.nombreProducto);
		$('#txt_codigo').val(stockAlmacen.codigoProducto);
		$('#txt_categoria').val(stockAlmacen.nombreCategoria);
		$('#txt_cod_siga').val(stockAlmacen.codigoSiga);
		$('#txt_uni_medida').val(stockAlmacen.nombreUnidadMedida);		
		$('#txt_env_primario').val(stockAlmacen.nombreEnvasePrimario);
		$('#txt_pes_uni_neto').val(stockAlmacen.pesoUnitarioNeto);
		$('#txt_pes_uni_bruto').val(stockAlmacen.pesoUnitarioBruto);
		$('#txt_largo').val(stockAlmacen.dimLargo);
		$('#txt_vol_uni_m3').val(stockAlmacen.volumenUnitario);
		$('#txt_alto').val(stockAlmacen.dimAlto);
		$('#txt_vol_tot_m3').val(stockAlmacen.volumenTotal);
		$('#txt_ancho').val(stockAlmacen.dimAncho);		
		$('#txt_stock').val(stockAlmacen.cantidadStock);
		$('#txt_pre_promedio').val(stockAlmacen.precioPromedio);
		$('#txt_sto_seguridad').val(stockAlmacen.stockSeguridad);
		if (!esnulo(stockAlmacen.idTipoEnvaseSecundario)) {
			$('#sel_env_secundario').val(stockAlmacen.idTipoEnvaseSecundario);
		}
		$('#txt_des_env_sec').val(stockAlmacen.descripcionEnvaseSecundario);
		$('#txt_uni_envase').val(stockAlmacen.unidadesEnvaseSecundario);
		$('#txt_can_envases').val(stockAlmacen.cantidadEnvases);
		$('#txt_uni_sueltas').val(stockAlmacen.unidadesSueltas);
		$('#txt_observaciones').val(stockAlmacen.observacion);
		
		obtenerProductoStockAlmacen(false);

		listarStockAlmacenLote(false);

	}
		
}

function obtenerProductoStockAlmacen(indicador) {
	if (indicador) {
		loadding(true);
	}
	var params = { 
		idAlmacen : stockAlmacen.idAlmacen,
		idDdi : stockAlmacen.idDdi,
		idProducto : stockAlmacen.idProducto
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/stock-almacen/obtenerProductoStockAlmacen', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {			
			$('#txt_producto').val(respuesta.nombreProducto);
			$('#txt_cod_producto').val(respuesta.codigoProducto);
			$('#txt_det_cod_siga').val(respuesta.codigoSiga);
			$('#txt_sto_total').val(respuesta.cantidadStock);
			$('#txt_det_uni_medida').val(respuesta.unidadMedida);
			$('#txt_det_tip_envase').val(respuesta.nombreEnvase);
			$('#txt_det_pre_promedio').val(respuesta.precioPromedio);			
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarStockAlmacenLote(indicador) {
	var params = { 
		idAlmacen : stockAlmacen.idAlmacen,
		idDdi : stockAlmacen.idDdi,
		idProducto : stockAlmacen.idProducto
	};			
	consultarAjaxSincrono('GET', '/gestion-almacenes/stock-almacen/listarStockAlmacenLote', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleAlmacenLote(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleAlmacenLote(respuesta) {

	tbl_det_lotes.dataTable().fnDestroy();
	
	tbl_det_lotes.dataTable({
		data : respuesta,
		columns : [ {
			data : 'nroLote',
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
			data : 'nroLote',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
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
		}, {
			data : 'fechaProduccion'
		}, {
			data : 'fechaAlta'
		}, {
			data : 'planta'
		}, {
			data : 'nave'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaLotesCache = respuesta;

}
