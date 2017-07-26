$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			txt_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha.'
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
			sel_tip_movimiento : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Tipo Movimiento.'
					}
				}
			},
			rb_man_tie_programacion : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar El manifiesto tiene programación.'
					}
				}
			},
			sel_nro_programacion : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar N° Programacion.'
					}
				}
			},
			
			// Datos del Destino
			sel_almacen : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Almacen.'
					}
				}
			}
			
		}
	});
	
	frm_det_productos.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_producto : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Producto.'
					}
				}
			},
			txt_cantidad : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cantidad.'
					}
				}
			}
		}
	});
	
	frm_det_documentos.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_tip_producto : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Tipo Documento.'
					}
				}
			},
			txt_nro_documento : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar N° Documento.'
					}
				}
			},
			txt_doc_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha.'
					}
				}
			}
//			txt_lee_sub_archivo : {
//				validators : {
//					notEmpty : {
//						message : 'Debe cargar el Archivo.'
//					}
//				}
//			}
		}
	});
	
});
