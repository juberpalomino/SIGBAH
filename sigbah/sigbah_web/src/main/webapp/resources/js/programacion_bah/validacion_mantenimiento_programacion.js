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
					notEmpty : {
						message : 'Debe seleccionar Almacén.'
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
