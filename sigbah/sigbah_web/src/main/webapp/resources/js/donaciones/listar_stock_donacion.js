var listaStockAlmacenCache = new Object();

var tbl_mnt_sto_almacen = $('#tbl_mnt_sto_almacen');
var frm_sto_almacen = $('#frm_sto_almacen');

$(document).ready(function() {
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();

		var params = { 
			codigoCategoria : $('#sel_cat_producto').val(),
		};
		
		loadding(true);
		
		consultarAjax('GET', '/donaciones/stock-donacion/listarStockAlmacen', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarStockAlmacen(respuesta);
			}
			loadding(false);
		});

	});
	
	inicializarDatos();
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var tipoOrigen = '';
		var idAlmacen = '';		
		var idDdi = '';
		var codigo = '';
		tbl_mnt_sto_almacen.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_sto_almacen.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idProducto = listaStockAlmacenCache[index].idProducto;
				codigo = codigo + idProducto + '_';
				tipoOrigen = listaStockAlmacenCache[index].tipoOrigen;
				idAlmacen = listaStockAlmacenCache[index].idAlmacen;
				idDdi = listaStockAlmacenCache[index].idDdi;
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);
			var url = VAR_CONTEXT + '/donaciones/stock-donacion/mantenimientoStockAlmacen/';
			url = url + tipoOrigen + '/' + idAlmacen + '/' + idDdi + '/' + codigo;
			$(location).attr('href', url);
		}
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_sto_almacen > tbody > tr').length;
		var empty = null;
		$('tr.odd').each(function() {		
			empty = $(this).find('.dataTables_empty').text();
			return false;
		});					
		if (!esnulo(empty) || row < 1) {
			addWarnMessage(null, 'No se encuentran registros para generar el reporte.');
			return;
		}

		loadding(true);
		
		var idAlmacen = $('#sel_almacen').val();
		var codigoCategoria = $('#sel_cat_producto').val();
		var nombreProducto = $('#txt_producto').val().toUpperCase();
		var url = VAR_CONTEXT + '/donaciones/stock-donacion/exportarExcel/';
		url += verificaParametroInt(idAlmacen) + '/';
		url += verificaParametro(codigoCategoria) + '/';
		url += verificaParametro(nombreProducto);
		
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

	});
	
	$('#href_imprimir').click(function(e) {
		e.preventDefault();

		var indices = [];
		var tipoOrigen = '';
		var idAlmacen = '';		
		var idDdi = '';
		var codigo = '';
		tbl_mnt_sto_almacen.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_sto_almacen.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idProducto = listaStockAlmacenCache[index].idProducto;
				codigo = codigo + idProducto + '_';
				tipoOrigen = listaStockAlmacenCache[index].tipoOrigen;
				idAlmacen = listaStockAlmacenCache[index].idAlmacen;
				idDdi = listaStockAlmacenCache[index].idDdi;
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);			
			var url = VAR_CONTEXT + '/donaciones/stock-donacion/exportarPdf/';
			url = url + tipoOrigen + '/' + idAlmacen + '/' + idDdi + '/' + codigo;
			$.fileDownload(url).done(function(respuesta) {
				loadding(false);	
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else {
					addInfoMessage(null, mensajeReporteExito);
				}
			}).fail(function (respuesta) {
				loadding(false);
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else if (respuesta == NOTIFICACION_VALIDACION) {
					addWarnMessage(null, mensajeReporteValidacion);
				}
			});
		}
	});

});

function inicializarDatos() {
	
	$('#li_donaciones').addClass('active');
	$('#ul_donaciones').css('display', 'block');
	$('#ul_don_inventarios').css('display', 'block');	
	$('#li_reg_donaciones_stock').attr('class', 'active');
	$('#li_reg_donaciones_stock').closest('li').children('a').attr('href', '#');

	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
//
//		if (indicador == '1') { // Retorno
			$('#btn_buscar').click();
//		} else {
//			listarStockAlmacen(new Object());		
//		}
	}
}

function listarStockAlmacen(respuesta) {

	tbl_mnt_sto_almacen.dataTable().fnDestroy();
	
	tbl_mnt_sto_almacen.dataTable({
		data : respuesta,
		columns : [ {
			data : 'codigoProducto',
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
			data : 'codigoProducto',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreCategoria'
		}, {
			data : 'codigoProducto'
		}, {
			data : 'nombreProducto'
		}, {
			data : 'nombreUnidadMedida'
		}, {
			data : 'nombreEnvase'
		}, {
			data : 'precioUnitarioPromedio'
		}, {
			data : 'cantidad'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : true,
		ordering : false,
		info : true,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		],
		columnDefs : [
  			{ width : '25%', targets : 4 }
  		]
	});
	
	listaStockAlmacenCache = respuesta;

}
