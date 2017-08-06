$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			txt_fec_programacion : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha Programación.'
					}
				}
			},
			txt_descripcion : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Descripción.'
					}
				}
			},
			sel_nro_requerimiento : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar N° de Requerimiento.'
					}
				}
			},
			sel_nro_racion : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar N° Ración.'
					}
				}
			},
			sel_nro_dee : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar N° DEE.'
					}
				}
			},
			sel_reg_destino : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Region Destino.'
					}
				}
			},
			sel_ate_con : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Atención con.'
					}
				}
			},
			
			// Almacenes
			sel_almacen : {
				validators : {
					callback: {
		                callback: function(value, validator, field) {
		                	var idProgramacion = $('#hid_cod_programacion').val();
		                	var ind_programacion = $('#hid_ind_programacion').val(); 
                    		if (!esnulo(idProgramacion) && ind_programacion == '2') {
                    			if (esnulo(value)) {
                    				return { valid: false, message: 'Debe seleccionar Almacén.' }
                    			}
		            		}
		            		return true;
		                }
		            }
				}
			}		
		}
	});
	
	frm_pro_no_alimentarios.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_no_cat_producto : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Categoria de Producto.'
					}
				}
			},
			sel_no_producto : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Producto.'
					}
				}
			},
			rb_distribuir : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Distribuir.'
					}
				}
			},
			txt_no_cantidad : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cantidad.'
					}
				}
			}
		}
	});
	
});
