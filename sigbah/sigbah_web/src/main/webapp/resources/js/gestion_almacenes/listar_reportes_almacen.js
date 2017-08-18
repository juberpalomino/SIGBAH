var frm_rep_almacen = $('#frm_rep_almacen');
var frm_tip_reporte = $('#frm_tip_reporte');

$(document).ready(function() {
	
	inicializarDatos();
	
	$('input[type=radio][name=rb_tip_reporte]').change(function() {
		var tipoReporte = this.value;
		frm_rep_almacen.data('bootstrapValidator').resetForm();
		if (tipoReporte != '5') {	
			var params = { 
				tipoReporte : tipoReporte
			};			
			loadding(true);
			$('#sel_producto').val('');
			$('#txt_nro_kardex').val('');
			$('#sel_nro_bincard').html('');
			$('#sel_mes_fin').val($('#sel_mes_fin option:first').val());
			$('#sel_producto').prop('disabled', true);
			$('#sel_nro_bincard').prop('disabled', true);
			$('#sel_mes_fin').prop('disabled', false);
			$('#sel_tip_movimiento').prop('disabled', false);
			$('#chk_inc_producto').prop('disabled', false);
			if ($('#sel_producto').hasClass('select2-hidden-accessible')) {
				$('#sel_producto').select2('destroy');
			}
			consultarAjax('GET', '/gestion-almacenes/reporte/listarTipoMovimiento', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '<option value="">Todos</option>';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.icodigo+'">'+item.descripcion+'</option>';
			        });
			        $('#sel_tip_movimiento').html(options);
				}
				loadding(false);
			});
		} else { // Reporte de Kardex y Bincard
			$('#sel_mes_fin').val('');
			$('#sel_tip_movimiento').html('');
			$('#sel_producto').val($('#sel_producto option:first').val());
			$('#sel_producto').prop('disabled', false);
			$('#sel_nro_bincard').prop('disabled', false);			
			$('#sel_mes_fin').prop('disabled', true);
			$('#sel_tip_movimiento').prop('disabled', true);
			$('#chk_inc_producto').prop('disabled', true);
			$('#sel_producto').select2();
		}
		
    });
	
	$('#sel_mes_fin').change(function() {
		frm_rep_almacen.bootstrapValidator('revalidateField', 'sel_mes_inicio');
	});
	
	$('#sel_producto').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_nro_kardex').val(arr[1]);
			} else {
				$('#txt_nro_kardex').val('');
			}
			loadding(true);
			var params = { 
				idDdi : usuarioBean.idDdi,
				idAlmacen : usuarioBean.idAlmacen,
				idProducto : arr[0]
			};
			consultarAjax('GET', '/gestion-almacenes/reporte/listarStockProductoLote', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var options = '';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.nroLote+'">'+item.lote+'</option>';
			        });
			        $('#sel_nro_bincard').html(options);
				}
				loadding(false);
			});			
			
		} else {
			$('#txt_nro_kardex').val('');
		}
	});
	
	$('#btn_exp_pdf').click(function(e) {
		e.preventDefault();

		var bootstrapValidatorRepAlmacen = frm_rep_almacen.data('bootstrapValidator');
		bootstrapValidatorRepAlmacen.validate();
		var bootstrapValidatorTipReporte = frm_tip_reporte.data('bootstrapValidator');
		bootstrapValidatorTipReporte.validate();
		if (bootstrapValidatorRepAlmacen.isValid() && bootstrapValidatorTipReporte.isValid()) {
			loadding(true);
			var url = VAR_CONTEXT + '/gestion-almacenes/reporte/exportarPdf/'+codigo+'/'+anio;
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
	
	$('#btn_exp_excel').click(function(e) {
		e.preventDefault();

		var bootstrapValidatorRepAlmacen = frm_rep_almacen.data('bootstrapValidator');
		bootstrapValidatorRepAlmacen.validate();
		var bootstrapValidatorTipReporte = frm_tip_reporte.data('bootstrapValidator');
		bootstrapValidatorTipReporte.validate();
		if (bootstrapValidatorRepAlmacen.isValid() && bootstrapValidatorTipReporte.isValid()) {
			loadding(true);
			var url = VAR_CONTEXT + '/gestion-almacenes/reporte/exportarExcel/'+codigo+'/'+anio;
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
	$('#li_alm_reportes').attr('class', 'active');
	$('#li_alm_reportes').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		$('#sel_anio').val(usuarioBean.codigoAnio);		
		$('#sel_producto').val('');
		$('#sel_producto').prop('disabled', true);
		$('#sel_nro_bincard').prop('disabled', true);
	}
}
