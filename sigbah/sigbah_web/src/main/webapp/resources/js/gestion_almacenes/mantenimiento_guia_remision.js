var frm_dat_generales = $('#frm_dat_generales');

$(document).ready(function() {
	
	inicializarDatos();
	
	$('#sel_estado').change(function() {
		var codigo = $(this).val();
		if (codigo == '0') {		
			$.SmartMessageBox({
				title : 'La Anulación Generara números automáticos de Guía de Remisión, Manifiesto de Carga y Acta de Entrega.<br>Está usted seguro ?',
				content : '',
				buttons : '[NO][SI]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'SI') {				
					loadding(true);								
					var params = { 
						idSalida : $('#hid_cod_ord_salida').val(),
						idGuiaRemision : $('#hid_cod_gui_remision').val(),
						codigoAnio : $('#hid_cod_anio').val(),
						codigoMes : $('#hid_cod_mes').val(),
						codigoDdi : $('#hid_cod_ddi').val(),
						codigoAlmacen : $('#hid_cod_almacen').val(),
						fechaEmision : $('#txt_fecha').val(),
						idEstado : $('#sel_estado').val()						
					};						
					consultarAjax('POST', '/gestion-almacenes/guia-remision/anularGuiaRemision', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							frm_dat_generales.data('bootstrapValidator').resetForm();
							cargarDatosGuiaRemision(respuesta);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
						loadding(false);
					});							
				}	
			});
		}
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var params = {
				idSalida : $('#hid_cod_ord_salida').val(),
				idGuiaRemision : $('#hid_cod_gui_remision').val(),
				idEstado : $('#sel_estado').val(),
				observacionGuiaRemision : $('#txt_obs_gui_remision').val(),
				observacionManifiestoCarga : $('#txt_obs_man_carga').val(),
				observacionActaEntregaRecepcion : $('#txt_obs_act_entrega').val()
			};
			
			loadding(true);
			
			consultarAjax('POST', '/gestion-almacenes/guia-remision/actualizarGuiaRemision', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {					
					addSuccessMessage(null, respuesta.mensajeRespuesta);					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/guia-remision/inicio/1';
		$(location).attr('href', url);
		
	});
	
});

function inicializarDatos() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#ul_alm_salidas').css('display', 'block');	
	$('#li_gui_remision').attr('class', 'active');
	$('#li_gui_remision').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		cargarDatosGuiaRemision(guiaRemision);

	}
		
}

function cargarDatosGuiaRemision(guiaRemision) {
	$('#hid_cod_gui_remision').val(guiaRemision.idGuiaRemision);
	$('#hid_cod_ord_salida').val(guiaRemision.idSalida);	
	$('#hid_cod_anio').val(guiaRemision.codigoAnio);
	$('#hid_cod_mes').val(guiaRemision.codigoMes);
	$('#hid_cod_ddi').val(guiaRemision.codigoDdi);
	$('#hid_cod_almacen').val(guiaRemision.codigoAlmacen);
	$('#txt_nro_ord_salida').val(guiaRemision.nroOrdenSalida);
	$('#txt_fecha').val(guiaRemision.fechaEmision);
	$('#txt_tip_movimiento').val(guiaRemision.nombreMovimiento);
	$('#txt_ddi').val(guiaRemision.nombreDdi);		
	$('#txt_almacen').val(guiaRemision.nombreAlmacen);		
	$('#txt_nro_gui_remision').val(guiaRemision.nroGuiaRemision);
	$('#sel_estado').val(guiaRemision.idEstado);
	$('#txt_obs_gui_remision').val(guiaRemision.observacionGuiaRemision);
	$('#txt_nro_man_carga').val(guiaRemision.nroManifiestoCarga);
	$('#txt_obs_man_carga').val(guiaRemision.observacionManifiestoCarga);
	$('#txt_nro_act_entrega').val(guiaRemision.nroActaEntregaRecepcion);
	$('#txt_obs_act_entrega').val(guiaRemision.observacionActaEntregaRecepcion);
}