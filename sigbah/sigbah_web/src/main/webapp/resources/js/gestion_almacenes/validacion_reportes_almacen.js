$(document).ready(function() {

	frm_rep_almacen.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			rb_tip_reporte : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar el Tipo de Reporte.'
					}
				}
			},
			sel_anio : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Año.'
					}
				}
			},
			sel_mes_inicio : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Mes Inicio.'
					}
				}
			},
			sel_mes_fin : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Mes Fin.'
					}
				}
			}
			
			
			
			
		}
	});
	
});
