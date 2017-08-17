var frm_rep_almacen = $('#frm_rep_almacen');

$(document).ready(function() {
	
	inicializarDatos();
	
	$('input[type=radio][name=rb_tip_reporte]').change(function() {
		cargarTipoReporte(this.value);
    });
	
	$('#btn_exp_pdf').click(function(e) {
		e.preventDefault();
/*
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
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else if (indices.length > 1) {
			addWarnMessage(null, mensajeValidacionSeleccionarSoloUnRegistro);
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
		*/
	});
	
	$('#btn_exp_excel').click(function(e) {
		e.preventDefault();
		/*
		var row = $('#tbl_mnt_ord_salida > tbody > tr').length;
		var empty = null;
		$('tr.odd').each(function() {		
			empty = $(this).find('.dataTables_empty').text();
			return false;
		});					
		if (!esnulo(empty) || row < 1) {
			addWarnMessage(null, mensajeReporteRegistroValidacion);
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
*/
	});
	
});

function inicializarDatos() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#li_alm_reportes').attr('class', 'active');
	$('#li_alm_reportes').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		$('#sel_anio').val(usuarioBean.codigoAnio);
		$('#sel_producto').prop('disabled', true);
	}
}

function cargarTipoReporte(tipoReporte) {
	var params = { 
		tipoReporte : tipoReporte
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/reporte/listarTipoMovimiento', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.icodigo+'">'+item.descripcion+'</option>';
	        });
	        $('#sel_tip_movimiento').html(options);
		}
		loadding(false);
	});
	
}
