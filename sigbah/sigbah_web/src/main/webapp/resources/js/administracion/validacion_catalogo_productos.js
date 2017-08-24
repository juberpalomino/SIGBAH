$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			
			sel_categoria : {
				validators : {
					notEmpty : {
						message : 'Debe Seleccionar Categoría del Producto.'
					}
				}
			},
			
			txt_nombre : {
				validators : {
					notEmpty : {
						message : 'Debe Ingresar Nombre del Producto.'
					}
				}
			},
			
			sel_unidad_medida : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Unidad de Medida.'
					}
				}
			},
			
			sel_tipo_envase : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Tipo de Envase.'
					}
				}
			},
			
			txt_peso_neto : {
				validators : {
					notEmpty : {
						message : 'Debe Ingresar Peso Neto.'
					}
				}
			},
			
			txt_peso_bruto : {
				validators : {
					notEmpty : {
						message : 'Debe Ingresar Peso Bruto.'
					}
				}
			}
			
		}
	});
	
	
	
});
