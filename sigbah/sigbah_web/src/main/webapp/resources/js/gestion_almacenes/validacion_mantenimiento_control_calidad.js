$(document).ready(function() {

	frm_dat_generales.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			// Datos Generales
			txt_fecha : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Fecha.'
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
			sel_nro_ord_compra : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Nº orden Compra.'
					}
				}
			},
			sel_tip_control : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Tipo de Control.'
					}
				}
			},
			
			// Datos del Origen / Destino - Reponsables
			sel_ori_almacen : {
				validators : {
					callback: {
				        callback: function(value, validator, field) {
				        	var val_tip_control = $('#sel_tip_control').val();                 		
				    		if (val_tip_control == '3' || // Ingreso por Transferencias de Almacén
				    				val_tip_control == '6') { // Salidas por Transferencias a Almacén
				    			if (esnulo(value)) {
				    				return { valid: false, message: 'Debe seleccionar Tipo de Control.'}
				    			}
				    		}
				    		return true;
				        }
				    }
				}
			},
			sel_ori_en_almacen : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Encargado de Almacén.'
					}
				}
			},
			sel_inspector : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Inspector.'
					}
				}
			},
			
			// Datos de Proveedor
			sel_proveedor : {
				validators : {
					callback: {
				        callback: function(value, validator, field) {
				        	var val_tip_control = $('#sel_tip_control').val();                 		
				    		if (val_tip_control == '1' || // Ingreso por Compra de productos
				    				val_tip_control == '5') { // Ingreso por Donación
				    			if (esnulo(value)) {
				    				return { valid: false, message: 'Debe seleccionar Proveedor.'}
				    			}   
				    		}
				            return true;
				        }
				    }
				}
			},
			sel_representante : {
				validators : {
					callback: {
				        callback: function(value, validator, field) {
				        	var val_tip_control = $('#sel_tip_control').val();                 		
				    		if (val_tip_control == '1' || // Ingreso por Compra de productos
				    				val_tip_control == '5') { // Ingreso por Donación
				    			if (esnulo(value)) {
				    				return { valid: false, message: 'Debe seleccionar Representante.'}
				    			}
				    		}
				            return true;
				        }
				    }
				}
			},
			
			// Datos del Transporte
			sel_emp_transporte : {
				validators : {
					callback: {
				        callback: function(value, validator, field) {
				        	var val_tip_control = $('#sel_tip_control').val();                 		
				    		if (val_tip_control == '1' || // Ingreso por Compra de productos
				    				val_tip_control == '5') { // Ingreso por Donación
				    			if (esnulo(value)) {
				    				return { valid: false, message: 'Debe seleccionar Empresa de Transporte.'}
				    			}
				    		}
				            return true;
				        }
				    }
				}
			},
			sel_chofer : {
				validators : {
					callback: {
				        callback: function(value, validator, field) {
				        	var val_tip_control = $('#sel_tip_control').val();                 		
				    		if (val_tip_control == '1' || // Ingreso por Compra de productos
				    				val_tip_control == '5') { // Ingreso por Donación
				    			if (esnulo(value)) {
				    				return { valid: false, message: 'Debe seleccionar Chofer.'}
				    			}
				    		}
				            return true;
				        }
				    }
				}
			},
			txt_nro_placa : {
				validators : {
					callback: {
				        callback: function(value, validator, field) {
				        	var val_tip_control = $('#sel_tip_control').val();                 		
				    		if (val_tip_control == '1' || // Ingreso por Compra de productos
				    				val_tip_control == '5') { // Ingreso por Donación
				    			if (esnulo(value)) {
				    				return { valid: false, message: 'Debe seleccionar N° de Placa.'}
				    			}
				    		}
				            return true;
				        }
				    }
				}
			}
			
		}
	});
	
	frm_det_alimentarios.bootstrapValidator({
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
			sel_uni_medida : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Unidad Medida.'
					}
				}
			},
			txt_fec_vencimiento : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha Vencimiento.'
					}
				}
			},
			txt_can_lote : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cantidad de Lote.'
					}
				}
			},
			txt_can_muestra : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cantidad de Muestra.'
					}
				}
			},
			sel_primario : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Primario.'
					}
				}
			},
			sel_olor : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Olor.'
					}
				}
			},
			sel_textura : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Textura.'
					}
				}
			},
			sel_secundario : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Secundario.'
					}
				}
			},
			sel_color : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Color.'
					}
				}
			},
			sel_sabor : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Sabor.'
					}
				}
			}
		}
	});
	
	frm_det_no_alimentarios.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_no_producto : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Producto.'
					}
				}
			},
			sel_no_uni_medida : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Unidad Medida.'
					}
				}
			},
			txt_no_fec_vencimiento : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Fecha Vencimiento.'
					}
				}
			},
			txt_no_can_lote : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cantidad de Lote.'
					}
				}
			},
			txt_no_can_muestra : {
				validators : {
					notEmpty : {
						message : 'Debe ingresar Cantidad de Muestra.'
					}
				}
			},
			sel_no_primario : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Primario.'
					}
				}
			},
			sel_no_tecnicas : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Técnicas.'
					}
				}
			},
			sel_no_secundario : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Secundario.'
					}
				}
			},
			sel_no_conformidad : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Conformidad.'
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
			},
			txt_sub_archivo : {
				validators : {
					notEmpty : {
						message : 'Debe cargar el Archivo.'
					}
				}
			}
		}
	});
	
});
