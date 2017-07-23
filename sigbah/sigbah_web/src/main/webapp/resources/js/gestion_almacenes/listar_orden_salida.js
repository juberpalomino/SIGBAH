var listaOrdenSalidaCache = new Object();

var tbl_mnt_ord_salida = $('#tbl_mnt_ord_salida');
var frm_ord_salida = $('#frm_ord_salida');

$(document).ready(function() {
	
	frm_ord_salida.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_anio : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Año.'
					}
				}
			},
			sel_almacen : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Almacen.'
					}
				}
			}
		}
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_ord_salida.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoMes : $('#sel_mes').val(),
				idAlmacen : $('#sel_almacen').val(),
				idMovimiento : $('#sel_tip_movimiento').val()
			};
			
			loadding(true);
			
			consultarAjax('GET', '/gestion-almacenes/orden-salida/listarOrdenSalida', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarOrdenSalida(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	inicializarDatos();
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		var anio = '';
		tbl_mnt_ord_salida.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_ord_salida.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idSalida = listaOrdenSalidaCache[index].idSalida;
				codigo = codigo + idSalida + '_';
				anio = listaOrdenSalidaCache[index].codigoAnio;
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
			var url = VAR_CONTEXT + '/gestion-almacenes/orden-salida/mantenimientoOrdenSalida/';
			$(location).attr('href', url + codigo + '/' + anio);
		}
		
	});
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/orden-salida/mantenimientoOrdenSalida/0/0';
		$(location).attr('href', url);
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_ord_salida > tbody > tr').length;
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
		var codigoMes = $('#sel_mes').val();
		var idAlmacen = $('#sel_almacen').val();
		var idMovimiento = $('#sel_tip_movimiento').val();
		var url = VAR_CONTEXT + '/gestion-almacenes/orden-salida/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametro(codigoMes) + '/';
		url += verificaParametroInt(idAlmacen) + '/';
		url += verificaParametroInt(idMovimiento);
		
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
		var anio = '';
		tbl_mnt_ord_salida.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_ord_salida.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idSalida = listaOrdenSalidaCache[index].idSalida;
				codigo = codigo + idSalida + '_';
				anio = listaOrdenSalidaCache[index].codigoAnio;
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
			var url = VAR_CONTEXT + '/gestion-almacenes/orden-salida/exportarPdf/'+codigo+'/'+anio;
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
	$('#ul_alm_salidas').css('display', 'block');	
	$('#li_ord_salida').attr('class', 'active');
	$('#li_ord_salida').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		$('#sel_anio').val(ordenSalida.codigoAnio);
		$('#sel_mes').val(ordenSalida.codigoMes);
		$('#sel_almacen').val(ordenSalida.idAlmacen);
		$('#sel_almacen').prop('disabled', true);
		if (indicador == '1') { // Retorno
			$('#btn_buscar').click();
		} else {
			listarOrdenSalida(new Object());		
		}
	}
}

function listarOrdenSalida(respuesta) {

	tbl_mnt_ord_salida.dataTable().fnDestroy();
	
	tbl_mnt_ord_salida.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idSalida',
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
			data : 'idSalida',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'codigoAnio'
		}, {
			data : 'nombreMes'
		}, {
			data : 'nombreAlmacen'
		}, {
			data : 'nroOrdenSalida'
		}, {
			data : 'fechaEmision'
		}, {
			data : 'nombreMovimiento'
		}, {
			data : 'nroGuiaRemision'
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
  			{ width : '15%', targets : 3 },
			{ width : '15%', targets : 4 },
			{ width : '15%', targets : 7 }
  		]
	});
	
	listaOrdenSalidaCache = respuesta;

}

