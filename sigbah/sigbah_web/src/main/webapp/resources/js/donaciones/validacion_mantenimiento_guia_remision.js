$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			sel_mot_traslado : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Motivo Traslado.'
					}
				}
			},
			sel_estado : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Estado.'
					}
				}
			}
			
		}
	});
	
});
