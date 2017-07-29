
$(document).ready(function() {
	
	inicializarDatos();

	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		if ($('#sel_estado').is(':disabled')) {
			grabarDetalleCierreStock(false);
		} else {
			var idEstado = $('#sel_estado').val();
			if (idEstado == '0') { // Pendiente
				grabarDetalleCierreStock(false);
			} else { // Cerrado
				var msg = 'Esta usted seguro de efectuar el cierre del Mes: ';
				msg = msg + $('#txt_mes').val();
				
				$.SmartMessageBox({
					title : msg,
					content : '',
					buttons : '[No][Si]'
				}, function(ButtonPressed) {
					if (ButtonPressed === 'Si') {
						grabarDetalleCierreStock(true);						
					}
					if (ButtonPressed === 'No') {
						retonarListadoCierreStock();
					}
				});
			}
		}

	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		retonarListadoCierreStock()
		
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
		
		$('#txt_almacen').val(cierreStock.nombreAlmacen);
		$('#txt_mes').val(cierreStock.nombreMes);
		$('#sel_responsable').val(cierreStock.idResponsable);
		$('#txt_observaciones').val(cierreStock.observacion);
				
		if (cierreStock.nombreEstado == 'CERRADO') {
			$('#sel_estado').val('');
			$('#sel_estado').prop('disabled', true);			
		} else if (cierreStock.nombreEstado == 'PENDIENTE') {
			$('#sel_estado').val(cierreStock.flagCierreAlmacen);
		}

	}
	
}

function grabarDetalleCierreStock(indicador) {
	var params = {
		idAlmacen : cierreStock.idAlmacen,
		codigoAnio : cierreStock.codigoAnio,
		codigoMes : cierreStock.codigoMes,
		flagCierreAlmacen : $('#sel_estado').val(),
		idResponsable : $('#sel_responsable').val(),
		observacion : $('#txt_observaciones').val()
	};
	
	loadding(true);
	
	consultarAjax('POST', '/gestion-almacenes/cierre-stock/grabarCierreStock', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {					
			addSuccessMessage(null, respuesta.mensajeRespuesta);
		}
		
		if (indicador) {
			retonarListadoCierreStock()
		} else {
			loadding(false);
		}
	});
}

function retonarListadoCierreStock() {
	loadding(true);					
	var url = VAR_CONTEXT + '/gestion-almacenes/cierre-stock/inicio/1';
	$(location).attr('href', url);
}
