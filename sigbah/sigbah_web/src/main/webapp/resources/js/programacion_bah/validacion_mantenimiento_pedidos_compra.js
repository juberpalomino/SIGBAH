$(document).ready(function() {

	frm_pedi_compra.bootstrapValidator({ 
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			sel_tipo_racion : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Tipo de Ración.'
					}
				}
			},
			txt_nom_racion : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Nombre Ración.'
					}
				}
			},
			txt_num_dias : { 
				validators : {
					notEmpty : {
						message : 'Debe ingresar numero dias de atención.'
					}
				}
			},
			txt_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar fecha.'
					}
				}
			}
			
		}
	});
	

});
