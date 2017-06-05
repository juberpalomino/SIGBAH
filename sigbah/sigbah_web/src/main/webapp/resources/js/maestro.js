$(document).ready(function() {

		$('#frm_elements').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			txt_field : {
				validators : {
					notEmpty : {
						message : 'The first name is required'
					}
				}
			},
			txt_pas_field : {
				validators : {
					notEmpty : {
						message : 'The last name is required'
					}
				}
			},
			txt_area : {
				validators : {
					notEmpty : {
						message : 'The company name is required'
					}
				}
			}
		}
	});
	
	$('#btn_submit').click(function() {
		
		var bootstrapValidator = $('#frm_elements').data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = new Object();
			
			consultarAjaxSincrono('GET', (VAR_CONTEXT + '/maestro/listarMaestros'), params, VAR_CONTEXT, function(respuesta) {
				if (respuesta.codigoRespuesta == '99') {
					// mostrarMensajeFlotante(respuesta.mensajeRespuesta, fnComentario.NOTIFICACION_ERROR);
					alert(respuesta.mensajeRespuesta);
				} else {
					
					
					alert('ok');
					
					
				}
			});
			
		}
		
	});
	
});