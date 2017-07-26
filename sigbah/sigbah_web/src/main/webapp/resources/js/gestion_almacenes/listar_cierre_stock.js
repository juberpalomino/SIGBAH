var listaCierreStockCache = new Object();

var tbl_mnt_cie_stock = $('#tbl_mnt_cie_stock');

$(document).ready(function() {

	$('#btn_buscar').click(function(e) {
		e.preventDefault();
		
		var params = { 
			codigoAnio : $('#sel_anio').val(),
			idAlmacen : $('#sel_almacen').val()
		};
		
		loadding(true);
		
		consultarAjax('GET', '/gestion-almacenes/cierre-stock/listarCierreStock', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarCierreStock(respuesta);
			}
			loadding(false);
		});
	});
	
	inicializarDatos();
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_mnt_cie_stock.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_cie_stock.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idCartilla = listaCierreStockCache[index].idCartilla;
				codigo = codigo + idCartilla + '_';
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
			var url = VAR_CONTEXT + '/gestion-almacenes/cierre-stock/mantenimientoCierreStock/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/cierre-stock/mantenimientoCierreStock/0';
		$(location).attr('href', url);
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_cie_stock > tbody > tr').length;
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
		
		var codigoAnio = $('#sel_anio').val();
		var idAlmacen = $('#sel_almacen').val();
		var url = VAR_CONTEXT + '/gestion-almacenes/cierre-stock/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametroInt(idAlmacen);
		
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
		var codigo = '';
		tbl_mnt_cie_stock.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_cie_stock.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idCartilla = listaCierreStockCache[index].idCartilla;
				codigo = codigo + idCartilla + '_';
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
			var url = VAR_CONTEXT + '/gestion-almacenes/cierre-stock/exportarPdf/'+codigo;
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
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#ul_alm_inventarios').css('display', 'block');	
	$('#li_cie_mensual').attr('class', 'active');
	$('#li_cie_mensual').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		$('#sel_anio').val(usuarioBean.codigoAnio);
		$('#sel_almacen').val(usuarioBean.idAlmacen);
		$('#sel_almacen').prop('disabled', true);
		if (indicador == '1') { // Retorno
			$('#btn_buscar').click();
		} else {
			listarCierreStock(new Object());
		}
	}
}

function listarCierreStock(respuesta) {

	tbl_mnt_cie_stock.dataTable().fnDestroy();
	
	tbl_mnt_cie_stock.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idCartilla',
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
			data : 'idCartilla',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreAlmacen'
		}, {
			data : 'nombreAlmacen'
		}, {
			data : 'responsable'
		}, {
			data : 'nombreEstado'
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
			{ width : '20%', targets : 2 },
			{ width : '15%', targets : 3 },
			{ width : '30%', targets : 4 },
			{ width : '20%', targets : 5 }
		]
	});
	
	listaCierreStockCache = respuesta;

}
