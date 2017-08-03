$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({  
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
		
			sel_pedidoPor : {
				validators : { 
					notEmpty : {
						message : 'Debe seleccionar pedido por.'
					}
				}
			},
			txt_descripcion : { 
				validators : {
					notEmpty : {
						message : 'Debe ingresar descripción.'
					}
				}
			},
			sel_dee : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar DEE.'
					}
				}
			}
			
		}
	});
	

});
