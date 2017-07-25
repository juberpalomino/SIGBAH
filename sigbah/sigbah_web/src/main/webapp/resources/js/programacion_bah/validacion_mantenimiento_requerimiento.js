$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			txt_descripcion : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar decripción.'
					}
				}
			},
			txt_fecha_requerimiento : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha.'
					}
				}
			},
//			sel_region : { 
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar región.'
//					}
//				}
//			},
//			sel_fenomeno : {
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar Fenómeno.'
//					}
//				}
//			},
			rb_req_sinpad : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Requerimiento SINPAD.'
					}
				}
			}
			
			
			
		}
	});
	
	
//	frm_det_prog_ubigeo.bootstrapValidator({
//		framework : 'bootstrap',
//		excluded : [':disabled', ':hidden'],
//		fields : {
//			sel_anio : { 
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar Año.'
//					}
//				}
//			},
//			sel_mes : { 
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar Mes.'
//					}
//				}
//			},
//			sel_departamento : { 
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar departamento.'
//					}
//				}
//			},
//			sel_provincia : { 
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar Provincia.'
//					}
//				}
//			},
//			sel_fenomeno : {
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar Fenómeno.'
//					}
//				}
//			}	
//		}
//	});
	
});
