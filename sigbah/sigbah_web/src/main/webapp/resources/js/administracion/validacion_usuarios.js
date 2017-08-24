$(document).ready(function() {

	frm_usuarios.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			
			txt_usuario : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Usuario.'
					}
				}
			},
			
			txt_password : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Contraseña.'
					}
				}
			},
			
			txt_nombre : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Nombre.'
					}
				}
			},
			
			txt_cargo : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cargo.'
					}
				}
			},
			
	
			txt_correo : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Correo.'
					}
				}
			},
			
			sel_ddi : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Ddi.'
					}
				}
			}
		
			
		}
	});
	
	
	
});
