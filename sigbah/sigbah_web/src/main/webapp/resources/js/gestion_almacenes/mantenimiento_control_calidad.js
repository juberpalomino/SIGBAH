var listaControlCalidadCache = new Object();

$(document).ready(function() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#li_con_calidad').addClass('active');
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy'
	});
	
	inicializarDatos();
	
	$('#frm_dat_generales').bootstrapValidator({
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
			sel_ddi : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar DDI.'
					}
				}
			},
			sel_almacen : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Almacen.'
					}
				}
			},
			txt_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Fecha.'
					}
				}
			},
			sel_estado : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Estado.'
					}
				}
			},
			sel_nro_ord_compra : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Nº orden Compra.'
					}
				}
			},
			txt_nro_placa : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar N° de Placa.'
					}
				}
			}
		}
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = $('#frm_dat_generales').data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			
			addSuccessMessage(null, 'Grabar');
			
		}
		

//		var params = { 
//			cod_anio : $('#sel_anio').val(),
//			cod_ddi : $('#sel_ddi').val(),
//			cod_almacen : $('#sel_almacen').val()
//		};
//		
//		loadding(true);
//		
//		consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
//			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//				addErrorMessage(null, respuesta.mensajeRespuesta);
//			} else {
//				listarControlCalidad(respuesta);
//			}
//			loadding(false);
//		});
		
	});
	
	$('#btn_salir').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/control-calidad/inicio';
		$(location).attr('href', url);
		
	});
	
});

function inicializarDatos() {
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {


		
		
	}
}

