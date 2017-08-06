var listaDetalleAlmacenesCache = new Object();

var listaProductosRacionCache = new Object();
var listaProgramacionAlimentosCache = new Object();
var programacionAlimentosCache = new Object();

var listaNoAlimentariosCache = new Object();
var listaProgramacionNoAlimentariosCache = new Object();
var programacionNoAlimentariosCache = new Object();

var listaDocumentosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_almacenes = $('#tbl_det_almacenes');

var tbl_pro_racion = $('#tbl_pro_racion');
var tbl_res_pro_alimentos = $('#tbl_res_pro_alimentos');

var tbl_pro_no_alimentarios = $('#tbl_pro_no_alimentarios');
var frm_pro_no_alimentarios = $('#frm_pro_no_alimentarios');
var tbl_res_pro_no_alimentarios = $('#tbl_res_pro_no_alimentarios');

var tbl_det_documentos = $('#tbl_det_documentos');
var frm_det_documentos = $('#frm_det_documentos');

var tbl_det_estados = $('#tbl_det_estados');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#txt_fec_programacion').datepicker().on('changeDate', function(e) {
		e.preventDefault();
		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
	});
	
	$('#sel_nro_requerimiento').change(function() {
		obtenerRequerimiento($(this).val());
	});

	$('#sel_nro_racion').change(function() {
		var idProgramacion = $('#hid_cod_programacion').val();
		if (!esnulo(idProgramacion)) {
			$.SmartMessageBox({
				title : 'Si cambia la Ración se perderá toda la programación realizada, esta Usted está seguro de continuar?',
				content : '',
				buttons : '[NO][SI]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'SI') {
	
					loadding(true);
					
					var params = { 
						idProgramacion : idProgramacion
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarProgramacionAlimento', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							var tipoAtencion = $('#sel_ate_con').val();
							if (tipoAtencion == '1') { // Alimentos
								listarProgramacionRacionOperativa(false);
								listarDetalleProgramacionAlimento(true);
							}
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
				
				if (ButtonPressed === 'NO') {
					$('#sel_nro_racion').val(programacion.idRacion+'_'+programacion.nombreRacion);
				}
			});
		} else {		
			obtenerRacion($(this).val());
		}
	});
	
	$('#sel_nro_dee').change(function() {
		obtenerNroDee($(this).val());
	});
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		$('#hid_ind_programacion').val('1');
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_programacion').val();
			var tipoControl = 'I'; // Insert
			if (!esnulo(codigo)) {
				tipoControl = 'U'; // Update
			}

			var val_requerimiento = $('#sel_nro_requerimiento').val();
			var arr_requerimiento = val_requerimiento.split('_');
			
			var val_nro_racion = $('#sel_nro_racion').val();
			var arr_nro_racion = val_nro_racion.split('_');
			
			var val_nro_dee = $('#sel_nro_dee').val();
			var arr_nro_dee = val_nro_dee.split('_');
			
			var tipoAtencion = $('#sel_ate_con').val();
			
			var params = {
				idProgramacion : codigo,	
				codigoAnio : programacion.codigoAnio,
				idRequerimiento : arr_requerimiento[0],
				fechaProgramacion : $('#txt_fec_programacion').val(),
				idDdi : programacion.idDdi, 
				codigoDdi : programacion.codigoDdi,
				idRegion : $('#sel_reg_destino').val(),				
				idRacion : arr_nro_racion[0],
				nombreProgramacion : $('#txt_descripcion').val(),				
				idNroDee : arr_nro_dee[0],	
				tipoAtencion : tipoAtencion,
				observacion : $('#txt_observaciones').val(),
				tipoControl : tipoControl
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProgramacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_cod_programacion').val(respuesta.idProgramacion);
						$('#txt_nro_programacion').val(respuesta.nroProgramacion);
						$('#txt_estado').val(respuesta.nombreEstado);
						$('#sel_almacen').prop('disabled', false);
						$('#sel_almacen').val($('#sel_almacen option:first').val());
						$('#btn_alm_agregar').prop('disabled', false);
						$('#btn_alm_eliminar').prop('disabled', false);
						
						$('input[name="txt_programacion"]').val(respuesta.codigoProgramacion+' - '+$('#txt_descripcion').val());
						$('#txt_pro_racion').val($('#txt_des_racion').val());
						
						programacion.idRacion = arr_nro_racion[0];
						programacion.nombreRacion = arr_nro_racion[1];
						
						if (tipoAtencion == '1') { // Alimentos			
							$('#li_alimentos').attr('class', '');
							$('#li_alimentos').closest('li').children('a').attr('data-toggle', 'tab');
						} else if (tipoAtencion == '2') { // No Alimentarios
							$('#li_no_alimentarios').attr('class', '');
							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						} else { // Ambos
							$('#li_alimentos').attr('class', '');
							$('#li_alimentos').closest('li').children('a').attr('data-toggle', 'tab');
							$('#li_no_alimentarios').attr('class', '');
							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
						}
						$('#li_documentos').attr('class', '');
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#li_estados').attr('class', '');
						$('#li_estados').closest('li').children('a').attr('data-toggle', 'tab');

						addSuccessMessage(null, 'Se genero el N° Programación: '+respuesta.nroProgramacion);
						
					}
					
				}
				frm_dat_generales.data('bootstrapValidator').resetForm();
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bah/programacion/inicio/1';
		$(location).attr('href', url);
		
	});
	
	$('#btn_alm_agregar').click(function(e) {
		e.preventDefault();
		
		$('#hid_ind_programacion').val('2');

		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = { 
				idProgramacion : $('#hid_cod_programacion').val(),
				idAlmacen : $('#sel_almacen').val()
			};

			loadding(true);
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProgramacionAlmacen', params, function(respuesta) {
				$('#div_det_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProgramacionAlmacenes(true);
					frm_dat_generales.data('bootstrapValidator').resetForm();
					addSuccessMessage(null, respuesta.mensajeRespuesta);
				}
			});
		}

	});
	
	$('#btn_alm_eliminar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_det_almacenes.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_almacenes.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idProgramacionAlmacen = listaDetalleAlmacenesCache[index].idProgramacionAlmacen;
				codigo = codigo + idProgramacionAlmacen + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					
					var params = { 
						arrIdDetalleProgramacionAlmacen : codigo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarProgramacionAlmacen', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProgramacionAlmacenes(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_ali_actualizar').click(function(e) {
		e.preventDefault();
		
		loadding(true);
		
		var params = { 
			idProgramacion : $('#hid_cod_programacion').val()
		};

		consultarAjax('POST', '/programacion-bah/programacion/actualizarProgramacionRacionOperativa', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				loadding(false);
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarProgramacionRacionOperativa(false);
				listarDetalleProgramacionAlimento(true);
				addSuccessMessage(null, respuesta.mensajeRespuesta);							
			}
		});
		
	});
	
	$('#btn_ali_editar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = '';
		var row = 0;
		$.each(listaProgramacionAlimentosCache, function(i, item) {
			if ($('#chk_ali_'+item.idProgramacionUbigeo).is(':checked')) {
				indices.push(row);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				codigo = item.idProgramacionUbigeo;
			}
			row = row + 1;
	    });	
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			programacionAlimentosCache = listaProgramacionAlimentosCache[indices];

			$('#txt_ali_departamento').val(programacionAlimentosCache.departamento);
			$('#txt_ali_provincia').val(programacionAlimentosCache.provincia);
			$('#txt_ali_distrito').val(programacionAlimentosCache.distrito);
			$('#txt_ali_per_afect').val(programacionAlimentosCache.persAfect);
			$('#txt_ali_per_dam').val(programacionAlimentosCache.persDam);
			$('#txt_ali_tot_pers').val(programacionAlimentosCache.totalPers);
			$('#txt_ali_tot_tm').val(programacionAlimentosCache.totalTm);
			
			var contenido = '';
			$.each(listaProductosRacionCache, function(i, item) {
				contenido = contenido + '<div class="row">'+
										'<label class="col-sm-6 control-label">'+item.nombreProducto+':</label>'+
										'<div class="col-sm-2 form-group">';
								
				$.each(programacionAlimentosCache.listaProducto, function(i, item_prod) {
					if (item.idProducto == item_prod.idProducto) {
						contenido = contenido + '<input type="text" id="txt_ali_uni_'+item_prod.idProducto+'" onchange="sumarUnidadAlimentos();" '+ 
									'class="form-control monto-format" value="'+obtieneParametro(item_prod.unidad)+'">';
					}					
			    });	
								
				contenido = contenido + '</div></div>';
		    });			
			$('#div_ali_unidades').html(contenido);
			
			$('#div_edi_pro_alimentos').modal('show');
		}
		
	});
	
	$('#btn_gra_pro_alimentos').click(function(e) {
		e.preventDefault();
		
		loadding(true);
		
		var arrIdProducto = [];
		var arrUnidad = [];			
		
		$.each(listaProductosRacionCache, function(i, item) {			
			var unidad = $('#txt_ali_uni_'+item.idProducto).val();
			$.each(programacionAlimentosCache.listaProducto, function(i, item_prod) {
				if (item.idProducto == item_prod.idProducto && unidad != obtieneParametro(item_prod.unidad)) {
					var indicador = false;
					if (!esnulo(unidad) && !esnulo(item_prod.unidad)) {
						if (parseFloat(formatMonto(unidad)) != parseFloat(item_prod.unidad)) {
							indicador = true;
						}
					} else {
						if (unidad != obtieneParametro(item_prod.unidad)) {
							indicador = true;
						}
					}
					if (indicador) {			
						arrIdProducto.push(item.idProducto);
						arrUnidad.push(formatMonto(unidad));
					}
				}					
		    });
	    });
		
		var params = { 
			idProgramacionUbigeo : programacionAlimentosCache.idProgramacionUbigeo,
			arrIdProducto : arrIdProducto,
			arrUnidad : arrUnidad
		};

		consultarAjax('POST', '/programacion-bah/programacion/actualizarDetalleProgramacionAlimento', params, function(respuesta) {
			$('#div_edi_pro_alimentos').modal('hide');
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				loadding(false);
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarDetalleProgramacionAlimento(true);
				addSuccessMessage(null, respuesta.mensajeRespuesta);							
			}
		});
		
	});
	
	$('#btn_ali_eliminar').click(function(e) {
		e.preventDefault();
		
		var arrIdDetalleProgramacionUbigeo = [];
		$.each(listaProgramacionAlimentosCache, function(i, item) {
			if ($('#chk_ali_'+item.idProgramacionUbigeo).is(':checked')) {
				arrIdDetalleProgramacionUbigeo.push(item.idProgramacionUbigeo);
			}
	    });	
		
		if (arrIdDetalleProgramacionUbigeo.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (arrIdDetalleProgramacionUbigeo.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					
					var params = { 
						arrIdDetalleProgramacionUbigeo : arrIdDetalleProgramacionUbigeo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarDetalleProgramacionAlimento', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDetalleProgramacionAlimento(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_ali_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = 0;
		$('tr.item_ali').each(function() {	
			row = row + 1;
			if (row > 0) {
				return false;
			}
		});
			
		if (row == 0) {
			addWarnMessage(null, 'No se encuentran registros para generar el reporte.');
			return;
		}

		loadding(true);
		
		var arrIdProducto = [];
		var arrNombreProducto = [];
		$.each(listaProductosRacionCache, function(i, item) {
			arrIdProducto.push(item.idProducto);
			arrNombreProducto.push(item.nombreProducto);
	    });
		
		var arrUnidadProducto = [];
		arrUnidadProducto.push($('#pro_ali_per_afect').html());
		arrUnidadProducto.push($('#pro_ali_per_dam').html());
		arrUnidadProducto.push($('#pro_ali_tot_pers').html());
		arrUnidadProducto.push($('#pro_ali_tot_raciones').html());
		$.each(listaProductosRacionCache, function(i, item) {
			arrUnidadProducto.push($('#td_ali_'+item.idProducto).html());
	    });		
		arrUnidadProducto.push($('#pro_ali_total_tm').html());

		var params = { 
			idProgramacion : $('#hid_cod_programacion').val(),
			arrIdProducto : arrIdProducto,
			arrNombreProducto : arrNombreProducto,
			arrUnidadProducto : arrUnidadProducto
		};	
		
		var url = VAR_CONTEXT + '/programacion-bah/programacion/exportarExcelAlimento';
		$.fileDownload(url, {
		    httpMethod : 'GET',
		    data : params,
		    successCallback : function (respuesta, url) {
		    	loadding(false);	
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else {
					addInfoMessage(null, mensajeReporteExito);
				}
		    },
		    failCallback : function (respuesta, url) {
		    	loadding(false);
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else {
					addInfoMessage(null, mensajeReporteExito);
				}
		    }
		});

	});
	
	$('#btn_pro_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_no_alimentarios').html('Nuevo Producto');
		frm_pro_no_alimentarios.trigger('reset');
		
		$('#sel_no_producto').html('');
		$('#sel_no_producto').select2().trigger('change');
		$('#sel_no_producto').select2({
			  dropdownParent: $('#div_pro_det_no_alimentarios')
		});
		
		$('#hid_cod_no_producto').val('');
		$('#div_pro_no_alimentarios').modal('show');
		
	});
	
	$('#btn_pro_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_pro_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_pro_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaNoAlimentariosCache[indices[0]];
			
			$('#h4_tit_no_alimentarios').html('Actualizar Producto');
			frm_pro_no_alimentarios.trigger('reset');
			
			$('#hid_cod_no_producto').val(obj.idDetalleProductoNoAlimentario);
			
			$('#sel_no_cat_producto').val(obj.idCategoria);
			cargarProductoNoAlimentario(obj.idCategoria, obj.idProducto);			
			$('input[name=rb_distribuir][value="'+obj.tipoEntrega+'"]').prop('checked', true);
			$('#txt_no_cantidad').val(obj.cantidad);
			
			$('#div_pro_no_alimentarios').modal('show');
		}
		
	});
	
	$('#btn_pro_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = '';
		tbl_pro_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_pro_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDetalleProductoNoAlimentario = listaNoAlimentariosCache[index].idDetalleProductoNoAlimentario;
				codigo = codigo + idDetalleProductoNoAlimentario + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					
					var params = { 
						arrIdDetalleControlCalidad : codigo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarProductoNoAlimentarioProgramacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoNoAlimentarioProgramacion(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);
						}		
					});
					
				}	
			});
			
		}
		
	});
	
	$('#sel_no_cat_producto').change(function() {
		var idCategoria = $(this).val();		
		if (!esnulo(idCategoria)) {					
			cargarProductoNoAlimentario(idCategoria, null);
		} else {
			$('#sel_no_producto').html('');
			if ($('#sel_producto').hasClass('select2-hidden-accessible')) {
				$('#sel_producto').select2('destroy');
			}
			frm_pro_no_alimentarios.bootstrapValidator('revalidateField', 'sel_no_producto');
		}
	});
	
	$('#btn_gra_no_alimentario').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_pro_no_alimentarios.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var params = { 
				idDetalleProductoNoAlimentario : $('#hid_cod_no_producto').val(),
				idProgramacion : $('#hid_cod_programacion').val(),
				idProducto : $('#sel_no_producto').val(),
				cantidad : formatMonto($('#txt_no_cantidad').val()),
				tipoEntrega : $('input[name="rb_distribuir"]:checked').val()
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bah/programacion/grabarProductoNoAlimentarioProgramacion', params, function(respuesta) {
				$('#div_pro_no_alimentarios').modal('hide');
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoNoAlimentarioProgramacion(true);
					addSuccessMessage(null, respuesta.mensajeRespuesta);	
				}
				frm_pro_no_alimentarios.data('bootstrapValidator').resetForm();
			});
			
		}
		
	});
	
	$('#btn_can_no_alimentario, #btn_clo_no_alimentarios').click(function(e) {
		e.preventDefault();
		frm_pro_no_alimentarios.data('bootstrapValidator').resetForm();
	});
	
	$('#btn_no_ali_actualizar').click(function(e) {
		e.preventDefault();
		
		loadding(true);
		
		var params = { 
			idProgramacion : $('#hid_cod_programacion').val()
		};

		consultarAjax('POST', '/programacion-bah/programacion/actualizarProgramacionNoAlimentario', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				loadding(false);
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarProductoNoAlimentarioProgramacion(false);
				listarDetalleProgramacionNoAlimentario(true);
				addSuccessMessage(null, respuesta.mensajeRespuesta);							
			}
		});
		
	});
	
	$('#btn_no_ali_editar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = '';
		var row = 0;
		$.each(listaProgramacionNoAlimentariosCache, function(i, item) {
			if ($('#chk_no_ali_'+item.idProgramacionUbigeo).is(':checked')) {
				indices.push(row);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				codigo = item.idProgramacionUbigeo;
			}
			row = row + 1;
	    });	
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			programacionNoAlimentariosCache = listaProgramacionNoAlimentariosCache[indices];

			$('#txt_no_ali_departamento').val(programacionNoAlimentariosCache.departamento);
			$('#txt_no_ali_provincia').val(programacionNoAlimentariosCache.provincia);
			$('#txt_no_ali_distrito').val(programacionNoAlimentariosCache.distrito);
			$('#txt_no_ali_fam_afect').val(programacionNoAlimentariosCache.famAfect);
			$('#txt_no_ali_fam_dam').val(programacionNoAlimentariosCache.famDam);
			$('#txt_no_ali_tot_fam').val(programacionNoAlimentariosCache.totalFam);
			$('#txt_no_ali_per_afect').val(programacionNoAlimentariosCache.persAfect);
			$('#txt_no_ali_per_dam').val(programacionNoAlimentariosCache.persDam);
			$('#txt_no_ali_tot_pers').val(programacionNoAlimentariosCache.totalPers);
			
			var contenido = '';
			$.each(listaNoAlimentariosCache, function(i, item) {
				contenido = contenido + '<div class="row">'+
										'<label class="col-sm-6 control-label">'+item.nombreProducto+':</label>'+
										'<div class="col-sm-2 form-group">';
								
				$.each(programacionNoAlimentariosCache.listaProducto, function(i, item_prod) {
					if (item.idProducto == item_prod.idProducto) {
//						contenido = contenido + '<input type="text" id="txt_no_ali_uni_'+item_prod.idProducto+'" onchange="sumarUnidadNoAlimentarios();" '+ 
						contenido = contenido + '<input type="text" id="txt_no_ali_uni_'+item_prod.idProducto+'" '+ 
									'class="form-control monto-format" value="'+obtieneParametro(item_prod.unidad)+'">';
					}					
			    });	
								
				contenido = contenido + '</div></div>';
		    });			
			$('#div_no_ali_unidades').html(contenido);
			
			$('#div_edi_pro_no_alimentarios').modal('show');
		}
		
	});
	
	$('#btn_gra_pro_no_alimentarios').click(function(e) {
		e.preventDefault();
		
		loadding(true);
		
		var arrIdProducto = [];
		var arrUnidad = [];			
		
		$.each(listaNoAlimentariosCache, function(i, item) {			
			var unidad = $('#txt_no_ali_uni_'+item.idProducto).val();
			$.each(programacionNoAlimentariosCache.listaProducto, function(i, item_prod) {
				if (item.idProducto == item_prod.idProducto && unidad != obtieneParametro(item_prod.unidad)) {
					var indicador = false;
					if (!esnulo(unidad) && !esnulo(item_prod.unidad)) {
						if (parseFloat(formatMonto(unidad)) != parseFloat(item_prod.unidad)) {
							indicador = true;
						}
					} else {
						if (unidad != obtieneParametro(item_prod.unidad)) {
							indicador = true;
						}
					}
					if (indicador) {			
						arrIdProducto.push(item.idProducto);
						arrUnidad.push(formatMonto(unidad));
					}
				}					
		    });
	    });
		
		var params = { 
			idProgramacionUbigeo : programacionNoAlimentariosCache.idProgramacionUbigeo,
			arrIdProducto : arrIdProducto,
			arrUnidad : arrUnidad
		};

		consultarAjax('POST', '/programacion-bah/programacion/actualizarDetalleProgramacionNoAlimentario', params, function(respuesta) {
			$('#div_edi_pro_no_alimentarios').modal('hide');
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				loadding(false);
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarDetalleProgramacionNoAlimentario(true);
				addSuccessMessage(null, respuesta.mensajeRespuesta);							
			}
		});
		
	});
	
	$('#btn_no_ali_eliminar').click(function(e) {
		e.preventDefault();
		
		var arrIdDetalleProgramacionUbigeo = [];
		$.each(listaProgramacionNoAlimentariosCache, function(i, item) {
			if ($('#chk_no_ali_'+item.idProgramacionUbigeo).is(':checked')) {
				arrIdDetalleProgramacionUbigeo.push(item.idProgramacionUbigeo);
			}
	    });	
		
		if (arrIdDetalleProgramacionUbigeo.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (arrIdDetalleProgramacionUbigeo.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					
					var params = { 
						arrIdDetalleProgramacionUbigeo : arrIdDetalleProgramacionUbigeo
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarDetalleProgramacionNoAlimentario', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDetalleProgramacionNoAlimentario(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_no_ali_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = 0;
		$('tr.item_no_ali').each(function() {	
			row = row + 1;
			if (row > 0) {
				return false;
			}
		});
			
		if (row == 0) {
			addWarnMessage(null, 'No se encuentran registros para generar el reporte.');
			return;
		}

		loadding(true);
		
		var arrIdProducto = [];
		var arrNombreProducto = [];
		$.each(listaNoAlimentariosCache, function(i, item) {
			arrIdProducto.push(item.idProducto);
			arrNombreProducto.push(item.nombreProducto);
	    });
		
		var arrUnidadProducto = [];
		arrUnidadProducto.push($('#pro_no_ali_fam_afect').html());
		arrUnidadProducto.push($('#pro_no_ali_fam_dam').html());
		arrUnidadProducto.push($('#pro_no_ali_tot_fam').html());
		arrUnidadProducto.push($('#pro_no_ali_per_afect').html());
		arrUnidadProducto.push($('#pro_no_ali_per_dam').html());
		arrUnidadProducto.push($('#pro_no_ali_tot_pers').html());
		$.each(listaNoAlimentariosCache, function(i, item) {
			arrUnidadProducto.push($('#td_no_ali_'+item.idProducto).html());
	    });		
//		arrUnidadProducto.push($('#pro_no_ali_total_tm').html());

		var params = { 
			idProgramacion : $('#hid_cod_programacion').val(),
			arrIdProducto : arrIdProducto,
			arrNombreProducto : arrNombreProducto,
			arrUnidadProducto : arrUnidadProducto
		};	
		
		var url = VAR_CONTEXT + '/programacion-bah/programacion/exportarExcelNoAlimentario';
		$.fileDownload(url, {
		    httpMethod : 'GET',
		    data : params,
		    successCallback : function (respuesta, url) {
		    	loadding(false);	
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else {
					addInfoMessage(null, mensajeReporteExito);
				}
		    },
		    failCallback : function (respuesta, url) {
		    	loadding(false);
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else {
					addInfoMessage(null, mensajeReporteExito);
				}
		    }
		});

	});
	
	$('#href_doc_nuevo').click(function(e) {
		e.preventDefault();

		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
		$('#frm_det_documentos').trigger('reset');
		
		$('#txt_doc_fecha').datepicker('setDate', new Date());
		$('#hid_cod_documento').val('');
		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#fil_sub_archivo').val(null);
		$('#div_det_documentos').modal('show');
		
	});
	
	$('#href_doc_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaDocumentosCache[indices[0]];
			
			$('#h4_tit_documentos').html('Actualizar Documento');
			$('#frm_det_documentos').trigger('reset');
			
			$('#hid_cod_documento').val(obj.idDocumentoProgramacion);			
			$('#sel_tip_documento').val(obj.idTipoDocumento);
			$('#txt_nro_documento').val(obj.nroDocumento);
			$('#txt_doc_fecha').val(obj.fechaDocumento);
			$('#hid_cod_act_alfresco').val(obj.codigoArchivoAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
			$('#fil_sub_archivo').val(null);
			
			$('#div_det_documentos').modal('show');
		}
		
	});
	
	$('#href_doc_eliminar').click(function(e) {
		e.preventDefault();
		
		var arrIdDocumentoProgramacion = [];
		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				arrIdDocumentoProgramacion.push(listaDocumentosCache[index].idDocumentoProgramacion);
			}
		});

		if (arrIdDocumentoProgramacion.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (arrIdDocumentoProgramacion.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					
					var params = { 
						arrIdDocumentoProgramacion : arrIdDocumentoProgramacion
					};
			
					consultarAjax('POST', '/programacion-bah/programacion/eliminarDocumentoProgramacion', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoProgramacion(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#btn_gra_documento').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {			
			loadding(true);
			
			var indControl = null;
			var idDocumentoProgramacion = $('#hid_cod_documento').val();
			if (esnulo(idDocumentoProgramacion)) {
				indControl = 'I'; // I= INSERT
			} else {
				indControl = 'U'; // U= UPDATE
			}
			
			var params = { 
				idDocumentoProgramacion : idDocumentoProgramacion,
				idProgramacion : $('#hid_cod_programacion').val(),
				idTipoDocumento : $('#sel_tip_documento').val(),
				nroDocumento : $('#txt_nro_documento').val(),
				fechaDocumento : $('#txt_doc_fecha').val(),
				nombreArchivo : $('#txt_lee_sub_archivo').val(),
				indControl : indControl
			};
			
			var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
			if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
				var file_name = $('#fil_sub_archivo').val();
				var file_data = null;
				if (!esnulo(file_name) && typeof file_name !== 'undefined') {
					file_data = $('#fil_sub_archivo').prop('files')[0];
				}
				
				var formData = new FormData();
				formData.append('file_doc', file_data);
				// Carpeta contenedor, ubicado en config.properties
		    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.programacion');
		    	
				consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
					if (resArchivo == NOTIFICACION_ERROR) {
						$('#div_det_documentos').modal('hide');
						frm_det_documentos.data('bootstrapValidator').resetForm();
						loadding(false);
						addErrorMessage(null, mensajeCargaArchivoError);						
					} else {
						
						params.codigoArchivoAlfresco = resArchivo;

						grabarDetalleDocumento(params);					
					}
				});
				
			} else { // Archivo no cargado
				
				params.codigoArchivoAlfresco = $('#hid_cod_act_alfresco').val();

				grabarDetalleDocumento(params);				
			}
		}
		
	});
	
	$('#btn_can_documento, #btn_clo_documentos').click(function(e) {
		e.preventDefault();
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
	
	$('#fil_sub_archivo').change(function(e) {
		e.preventDefault();
	    var file_name = $(this).val();
	    var file_read = file_name.split('\\').pop();
	    $('#txt_lee_sub_archivo').val($.trim(file_read).replace(/ /g, '_'));
	    if (esnulo($('#hid_cod_documento').val())) {
	    	$('#hid_cod_ind_alfresco').val('1'); // Nuevo registro
	    } else {
	    	$('#hid_cod_ind_alfresco').val('2'); // Registro modificado
	    }
	    frm_det_documentos.bootstrapValidator('revalidateField', 'txt_lee_sub_archivo');	    
	});
	
	$('#href_eli_archivo').click(function(e) {
		e.preventDefault();

		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#fil_sub_archivo').val(null);
		$('#txt_lee_sub_archivo').val('');
		
	});
	
	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
		e.preventDefault();
		
		var id = $(this).attr('id');
		var name = $(this).attr('name');
		if (!esnulo(id) && !esnulo(name)) {
			descargarDocumento(id, name);
		} else {
			addInfoMessage(null, 'No dispone de documento adjunto asociado.');
		}
		
	});

});

function inicializarDatos() {
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_programacion').attr('class', 'active');
	$('#li_programacion').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_nro_programacion').val(programacion.nroProgramacion);
		if (!esnulo(programacion.idProgramacion)) {
			
			$('#hid_cod_programacion').val(programacion.idProgramacion);		
			if (programacion.flagTipoBien == '1') {
				$('#li_no_alimentarios').addClass('disabled');
				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			} else {
				$('#li_alimentarios').addClass('disabled');
				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
			}
			
			$('#txt_fec_programacion').val(programacion.fechaProgramacion);
			$('#txt_estado').val(programacion.nombreEstado);
			$('#txt_descripcion').val(programacion.nombreProgramacion);
			$('#sel_nro_requerimiento').val(programacion.idRequerimiento+'_'+programacion.nombreRequerimiento);			
			$('#txt_des_requerimiento').val(programacion.nombreRequerimiento);			
			$('#sel_nro_racion').val(programacion.idRacion+'_'+programacion.nombreRacion);			
			$('#txt_des_racion').val(programacion.nombreRacion);			
			$('#sel_nro_dee').val(programacion.idNroDee+'_'+programacion.nombreDeclarion);			
			$('#txt_des_nro_dee').val(programacion.nombreDeclarion);
			$('#sel_reg_destino').val(programacion.idRegion);
			$('#sel_ate_con').val(programacion.tipoAtencion);
			$('#txt_observaciones').val(programacion.observacion);
			
			$('input[name="txt_programacion"]').val(programacion.codigoProgramacion+' - '+$('#txt_descripcion').val());
			$('#txt_pro_racion').val($('#txt_des_racion').val());
			
			listarDetalleProgramacionAlmacenes(programacion.almacenes);
			cargarAlmacenes();
			
			if (programacion.tipoAtencion == '1') { // Alimentos			
				$('#li_no_alimentarios').addClass('disabled');
				$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
				listarProgramacionRacionOperativa(false);
				listarDetalleProgramacionAlimento(false);
			} else if (programacion.tipoAtencion == '2') { // No Alimentarios
				$('#li_alimentos').addClass('disabled');
				$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
				listarProductoNoAlimentarioProgramacion(false);
				listarDetalleProgramacionNoAlimentario(false);
			} else if (programacion.tipoAtencion == '3') { // Ambos
				listarProgramacionRacionOperativa(false);
				listarDetalleProgramacionAlimento(false);
				listarProductoNoAlimentarioProgramacion(false);
				listarDetalleProgramacionNoAlimentario(false);
			}
					
			listarDocumentoProgramacion(false);
			listarEstadoProgramacion(false);
			
		} else {
			
			$('#li_alimentos').addClass('disabled');
			$('#li_no_alimentarios').addClass('disabled');
			$('#li_documentos').addClass('disabled');
			$('#li_estados').addClass('disabled');
			$('#ul_man_programacion li.disabled a').removeAttr('data-toggle');
			
			$('#txt_fec_programacion').datepicker('setDate', new Date());
			
			obtenerRequerimiento($('#sel_nro_requerimiento').val());
			obtenerRacion($('#sel_nro_racion').val());
			obtenerNroDee($('#sel_nro_dee').val());
			cargarAlmacenes();
			$('#sel_almacen').val('');
			$('#sel_almacen').prop('disabled', true);
			$('#btn_alm_agregar').prop('disabled', true);
			$('#btn_alm_eliminar').prop('disabled', true);
			
			listarDetalleProgramacionAlmacenes(new Object());
			listarDetalleDocumentos(new Object());
			listarDetalleEstadoProgramacion(new Object());

		}
		
	}
	
}

function listarProgramacionAlmacenes(indicador) {
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val()
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacionAlmacen', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProgramacionAlmacenes(respuesta);
			cargarAlmacenes();
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleProgramacionAlmacenes(respuesta) {

	tbl_det_almacenes.dataTable().fnDestroy();
	
	tbl_det_almacenes.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idProgramacionAlmacen',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox"><i></i>'+
						   '</label>';	
				} else {
					return '';	
				}											
			}
		}, {	
			data : 'idProgramacionAlmacen',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreAlmacen'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
		
	listaDetalleAlmacenesCache = verificarListaJson(respuesta);
	
}

function listarProgramacionRacionOperativa(indicador) {
	var val_nro_racion = $('#sel_nro_racion').val();
	var arr_nro_racion = val_nro_racion.split('_');
	var params = { 
		idRacionOperativa : arr_nro_racion[0]
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacionRacionOperativa', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleRacionOperativa(respuesta);
			if (respuesta.length > 0) {
				$('#txt_dia_atencion').val(respuesta[0].diasAtencion);
			} else {
				$('#txt_dia_atencion').val('');
			}
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleRacionOperativa(respuesta) {

	tbl_pro_racion.dataTable().fnDestroy();
	
	tbl_pro_racion.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idDetalleRacionOperativa',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'cantidadRacionKg'
		}, {
			data : 'pesoUnidadPres'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		columnDefs : [
			{ width : '60%', targets : 1 }
		],
		'footerCallback' : function ( row, data, start, end, display ) {
			var api = this.api(), data;	 
			
			// Remove the formatting to get integer data for summation
			var intVal = function ( i ) {
				return typeof i === 'string' ? i.replace(/[\$,]/g, '')*1 : typeof i === 'number' ?	i : 0;
			};
 
			// total_page_peso over this page
			var tot_gr = api.column(2, { page: 'current'} ).data().reduce( function (a, b) {
				return intVal(a) + intVal(b);
			}, 0 );

			// Update footer
			$('#sp_tot_gr').html(parseFloat(tot_gr).toFixed(2));
		}
	});
	
	listaProductosRacionCache = verificarListaJson(respuesta);

}

function listarDetalleProgramacionAlimento(indicador) {
	
	if (listaProductosRacionCache.length > 0) {
	
		var arrIdProducto = [];
		$.each(listaProductosRacionCache, function(i, item) {
			arrIdProducto.push(item.idProducto);
		});
		
		var params = { 
			idProgramacion : $('#hid_cod_programacion').val(),
			arrIdProducto : arrIdProducto		
		};		
		
		consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacionAlimento', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				
				var table = $('<table id="tbl_det_pro_alimentos" />').addClass('table table-bordered table-hover tbl-responsive');
				var row = $('<tr/>');
				row.append($('<th class="table-dinamic" />').text('Sel'));
				row.append($('<th class="table-dinamic" />').text('Nº'));
				row.append($('<th class="table-dinamic" />').text('Departamento'));
				row.append($('<th class="table-dinamic" />').text('Provincia'));
				row.append($('<th class="table-dinamic" />').text('Distrito'));
				row.append($('<th class="table-dinamic" />').text('Pers. Afect.'));
				row.append($('<th class="table-dinamic" />').text('Pers. Dam.'));
				row.append($('<th class="table-dinamic" />').text('Total Pers.'));
				row.append($('<th class="table-dinamic" />').text('Total Raciones'));
				$.each(listaProductosRacionCache, function(i, item) {
					row.append($('<th class="table-dinamic" />').text(item.nombreProducto));
			    });			
				row.append($('<th class="table-dinamic" />').text('Total (TM)'));
				table.append(row);
		
				if (respuesta.length > 0) {
					var row_num = 1;
					
					var tot_persAfect = 0;
					var tot_persDam = 0;
					var tot_totalPers = 0;
					var tot_totalRaciones = 0;				
					var tot_totalTm = 0;
					
					$.each(respuesta, function(index, item) {
						row = $('<tr class="item_ali" />');
						row.append($('<td class="opc-checkbox" />').html('<label class="checkbox"><input type="checkbox" id="chk_ali_'+item.idProgramacionUbigeo+'"><i></i></label>'));
						row.append($('<td/>').html(row_num));
						row.append($('<td/>').html(item.departamento));
						row.append($('<td/>').html(item.provincia));
						row.append($('<td/>').html(item.distrito));
						row.append($('<td/>').html(item.persAfect));
						row.append($('<td/>').html(item.persDam));
						row.append($('<td/>').html(item.totalPers));
						row.append($('<td/>').html(item.totalRaciones));
						$.each(item.listaProducto, function(i, item_prod) {
							row.append($('<td class="pro_ali_'+item_prod.idProducto+'" />').html(item_prod.unidad));
					    });					
						row.append($('<td/>').html(item.totalTm));
						table.append(row);
						row_num++;
						
						tot_persAfect = tot_persAfect + parseFloat(verificaParametroInt(item.persAfect));
						tot_persDam = tot_persDam + parseFloat(verificaParametroInt(item.persDam));
						tot_totalPers = tot_totalPers + parseFloat(verificaParametroInt(item.totalPers));
						tot_totalRaciones = tot_totalRaciones + parseFloat(verificaParametroInt(item.totalRaciones));
						tot_totalTm = tot_totalTm + parseFloat(verificaParametroInt(item.totalTm));
					});
					
					row = $('<tr/>');
					row.append($('<td class="opc-right" colspan="5" />').html("Total:"));
					row.append($('<td id="pro_ali_per_afect" />').html(tot_persAfect));
					row.append($('<td id="pro_ali_per_dam" />').html(tot_persDam));
					row.append($('<td id="pro_ali_tot_pers" />').html(tot_totalPers));
					row.append($('<td id="pro_ali_tot_raciones" />').html(tot_totalRaciones));
					$.each(listaProductosRacionCache, function(i, item) {
						row.append($('<td id="td_ali_'+item.idProducto+'" />').html(''));
				    });
					row.append($('<td id="pro_ali_total_tm" />').html(tot_totalTm));
					table.append(row);
				}
				
				$('#div_det_pro_alimentos').html(table);
				
				if (respuesta.length > 0) {
					$.each(listaProductosRacionCache, function(i, item) {
						var can_tot_unidad = 0;
						$('tr.item_ali').each(function() {	
							var can_unidad = $(this).find('.pro_ali_'+item.idProducto).html();
							can_tot_unidad = can_tot_unidad + parseFloat(verificaParametroInt(can_unidad));
						});
						$('#td_ali_'+item.idProducto).html(can_tot_unidad);
					});
				}
				
				listaProgramacionAlimentosCache = respuesta;
				
				listarResumenStockAlimento(indicador);
				
			}
		});
		
	} else {
		
		var table = $('<table id="tbl_det_pro_alimentos" />').addClass('table table-bordered table-hover tbl-responsive');
		var row = $('<tr/>');
		row.append($('<th class="table-dinamic" />').text('Sel'));
		row.append($('<th class="table-dinamic" />').text('Nº'));
		row.append($('<th class="table-dinamic" />').text('Departamento'));
		row.append($('<th class="table-dinamic" />').text('Provincia'));
		row.append($('<th class="table-dinamic" />').text('Distrito'));
		row.append($('<th class="table-dinamic" />').text('Pers. Afect.'));
		row.append($('<th class="table-dinamic" />').text('Pers. Dam.'));
		row.append($('<th class="table-dinamic" />').text('Total Pers.'));
		row.append($('<th class="table-dinamic" />').text('Total Raciones'));		
		row.append($('<th class="table-dinamic" />').text('Total (TM)'));
		table.append(row);
		
		$('#div_det_pro_alimentos').html(table);
	}
		
}

function listarResumenStockAlimento(indicador) {
	
	var val_nro_racion = $('#sel_nro_racion').val();
	var arr_nro_racion = val_nro_racion.split('_');

	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
		idRacionOperativa : arr_nro_racion[0]	
	};		

	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarResumenStockAlimento', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleResumenStockAlimento(respuesta);
			if (indicador) {
				loadding(false);
			}
		}		
	});
}

function listarDetalleResumenStockAlimento(respuesta) {
	tbl_res_pro_alimentos.dataTable().fnDestroy();
	
	tbl_res_pro_alimentos.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idProducto',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'totalStock'
		}, {
			data : 'totalConsumo'
		}, {
			data : 'totalSaldo'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
}

function listarProductoNoAlimentarioProgramacion(indicador) {
	
	var val_nro_racion = $('#sel_nro_racion').val();
	var arr_nro_racion = val_nro_racion.split('_');

	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
		idRacionOperativa : arr_nro_racion[0]	
	};		

	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProductoNoAlimentarioProgramacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProductoNoAlimentarioProgramacion(respuesta);
			if (indicador) {
				loadding(false);
			}
		}		
	});
}

function listarDetalleProductoNoAlimentarioProgramacion(respuesta) {
	tbl_pro_no_alimentarios.dataTable().fnDestroy();
	
	tbl_pro_no_alimentarios.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetalleProductoNoAlimentario',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox"><i></i>'+
						   '</label>';	
				} else {
					return '';	
				}											
			}
		}, {	
			data : 'idDetalleProductoNoAlimentario',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'distribuirPor'
		}, {
			data : 'cantidad'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaNoAlimentariosCache = verificarListaJson(respuesta);
}

function listarDetalleProgramacionNoAlimentario(indicador) {
	
	if (listaNoAlimentariosCache.length > 0) {
		
		var arrIdProducto = [];
		$.each(listaNoAlimentariosCache, function(i, item) {
			arrIdProducto.push(item.idProducto);
	    });
		
		var params = { 
			idProgramacion : $('#hid_cod_programacion').val(),
			arrIdProducto : arrIdProducto		
		};		

		consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacionNoAlimentario', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				
				var table = $('<table id="tbl_det_pro_no_alimentarios" />').addClass('table table-bordered table-hover tbl-responsive');
				var row = $('<tr/>');
				row.append($('<th class="table-dinamic" />').text('Sel'));
				row.append($('<th class="table-dinamic" />').text('Nº'));
				row.append($('<th class="table-dinamic" />').text('Departamento'));
				row.append($('<th class="table-dinamic" />').text('Provincia'));
				row.append($('<th class="table-dinamic" />').text('Distrito'));
				row.append($('<th class="table-dinamic" />').text('Fam. Afect.'));
				row.append($('<th class="table-dinamic" />').text('Fam. Dam.'));
				row.append($('<th class="table-dinamic" />').text('Total Fam.'));
				row.append($('<th class="table-dinamic" />').text('Pers. Afect.'));
				row.append($('<th class="table-dinamic" />').text('Pers. Dam.'));
				row.append($('<th class="table-dinamic" />').text('Total Pers.'));
				$.each(listaNoAlimentariosCache, function(i, item) {
					row.append($('<th class="table-dinamic" />').text(item.nombreProducto));
			    });			
//				row.append($('<th class="table-dinamic" />').text('Total (TM)'));
				table.append(row);

				if (respuesta.length > 0) {
					var row_num = 1;
					
					var tot_famAfect = 0;
					var tot_famDam = 0;
					var tot_totalFam = 0;
					var tot_persAfect = 0;
					var tot_persDam = 0;
					var tot_totalPers = 0;
//					var tot_totalTm = 0;
					
					$.each(respuesta, function(index, item) {
						row = $('<tr class="item_no_ali" />');
						row.append($('<td class="opc-checkbox" />').html('<label class="checkbox"><input type="checkbox" id="chk_no_ali_'+item.idProgramacionUbigeo+'"><i></i></label>'));
						row.append($('<td/>').html(row_num));
						row.append($('<td/>').html(item.departamento));
						row.append($('<td/>').html(item.provincia));
						row.append($('<td/>').html(item.distrito));
						row.append($('<td/>').html(item.famAfect));
						row.append($('<td/>').html(item.famDam));
						row.append($('<td/>').html(item.totalFam));
						row.append($('<td/>').html(item.persAfect));
						row.append($('<td/>').html(item.persDam));
						row.append($('<td/>').html(item.totalPers));
						$.each(item.listaProducto, function(i, item_prod) {
							row.append($('<td class="pro_ali_'+item_prod.idProducto+'" />').html(item_prod.unidad));
					    });					
//						row.append($('<td/>').html(item.totalTm));
						table.append(row);
						row_num++;
						
						tot_famAfect = tot_famAfect + parseFloat(verificaParametroInt(item.famAfect));
						tot_famDam = tot_famDam + parseFloat(verificaParametroInt(item.famDam));
						tot_totalFam = tot_totalFam + parseFloat(verificaParametroInt(item.totalFam));
						tot_persAfect = tot_persAfect + parseFloat(verificaParametroInt(item.persAfect));
						tot_persDam = tot_persDam + parseFloat(verificaParametroInt(item.persDam));
						tot_totalPers = tot_totalPers + parseFloat(verificaParametroInt(item.totalPers));
//						tot_totalTm = tot_totalTm + parseFloat(verificaParametroInt(item.totalTm));
					});
					
					row = $('<tr/>');
					row.append($('<td class="opc-right" colspan="5" />').html("Total:"));
					row.append($('<td id="pro_no_ali_fam_afect" />').html(tot_famAfect));
					row.append($('<td id="pro_no_ali_fam_dam" />').html(tot_famDam));
					row.append($('<td id="pro_no_ali_tot_fam" />').html(tot_totalFam));
					row.append($('<td id="pro_no_ali_pers_afect" />').html(tot_persAfect));
					row.append($('<td id="pro_no_ali_pers_dam" />').html(tot_persDam));
					row.append($('<td id="pro_no_ali_tot_pers" />').html(tot_totalPers));
					$.each(listaNoAlimentariosCache, function(i, item) {
						row.append($('<td id="td_no_ali_'+item.idProducto+'" />').html(''));
				    });
//					row.append($('<td id="pro_no_ali_total_tm" />').html(tot_totalTm));
					table.append(row);
				}
				
				$('#div_det_pro_no_alimentarios').html(table);
				
				if (respuesta.length > 0) {
					$.each(listaNoAlimentariosCache, function(i, item) {
						var can_tot_unidad = 0;
						$('tr.item_no_ali').each(function() {	
							var can_unidad = $(this).find('.pro_no_ali_'+item.idProducto).html();
							can_tot_unidad = can_tot_unidad + parseFloat(verificaParametroInt(can_unidad));
						});
						$('#td_no_ali_'+item.idProducto).html(can_tot_unidad);
					});
				}
				
				listaProgramacionNoAlimentariosCache = respuesta;
				
				listarResumenStockNoAlimentario(indicador);
				
			}
		});
		
	} else {
		
		var table = $('<table id="tbl_det_pro_no_alimentarios" />').addClass('table table-bordered table-hover tbl-responsive');
		var row = $('<tr/>');
		row.append($('<th class="table-dinamic" />').text('Sel'));
		row.append($('<th class="table-dinamic" />').text('Nº'));
		row.append($('<th class="table-dinamic" />').text('Departamento'));
		row.append($('<th class="table-dinamic" />').text('Provincia'));
		row.append($('<th class="table-dinamic" />').text('Distrito'));
		row.append($('<th class="table-dinamic" />').text('Fam. Afect.'));
		row.append($('<th class="table-dinamic" />').text('Fam. Dam.'));
		row.append($('<th class="table-dinamic" />').text('Total Fam.'));
		row.append($('<th class="table-dinamic" />').text('Pers. Afect.'));
		row.append($('<th class="table-dinamic" />').text('Pers. Dam.'));
		row.append($('<th class="table-dinamic" />').text('Total Pers.'));
		$.each(listaNoAlimentariosCache, function(i, item) {
			row.append($('<th class="table-dinamic" />').text(item.nombreProducto));
	    });			
//		row.append($('<th class="table-dinamic" />').text('Total (TM)'));
		table.append(row);

		$('#div_det_pro_no_alimentarios').html(table);
		
	}
	
}

function listarResumenStockNoAlimentario(indicador) {
	
	var val_nro_racion = $('#sel_nro_racion').val();
	var arr_nro_racion = val_nro_racion.split('_');

	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
		idRacionOperativa : arr_nro_racion[0]	
	};		

	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarResumenStockNoAlimentario', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleResumenStockNoAlimentario(respuesta);
			if (indicador) {
				loadding(false);
			}
		}		
	});
}

function listarDetalleResumenStockNoAlimentario(respuesta) {
	tbl_res_pro_no_alimentarios.dataTable().fnDestroy();
	
	tbl_res_pro_no_alimentarios.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idProducto',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'totalStock'
		}, {
			data : 'totalConsumo'
		}, {
			data : 'totalSaldo'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
}

function obtenerRequerimiento(codigo) {
	if (!esnulo(codigo)) {
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_des_requerimiento').val(arr[1]);
		} else {
			$('#txt_des_requerimiento').val('');
		}			
	} else {
		$('#txt_des_requerimiento').val('');
	}
}

function obtenerRacion(codigo) {
	if (!esnulo(codigo)) {
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_des_racion').val(arr[1]);
		} else {
			$('#txt_des_racion').val('');
		}			
	} else {
		$('#txt_des_racion').val('');
	}
}

function obtenerNroDee(codigo) {
	if (!esnulo(codigo)) {
		var arr = codigo.split('_');
		if (arr.length > 1) {
			$('#txt_des_nro_dee').val(arr[1]);
		} else {
			$('#txt_des_nro_dee').val('');
		}			
	} else {
		$('#txt_des_nro_dee').val('');
	}
}

function cargarAlmacenes() {
	var idProgramacion = $('#hid_cod_programacion').val();
	var options = '';
	if (!esnulo(idProgramacion)) {
	    $.each(listaAlmacenesCache, function(i, item) {
	    	if (verificarAlmacen(item.vcodigo) != '1') {
	    		options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
	    	}
	    });	    
	} else {
		$.each(listaAlmacenesCache, function(i, item) {
	        options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
	    });
	}
    $('#sel_almacen').html(options);
	if (programacion.codigoDdi == CODIGO_DDI_INDECI_CENTRAL) {
		$('#sel_almacen').prepend("<option value=''>Seleccione</option>").val('');
	}
}


function verificarAlmacen(vcodigo) {
	var indicador = null;
    $.each(listaDetalleAlmacenesCache, function(i, item) {
		if (vcodigo == item.idAlmacen) {
			indicador = '1';
			return false;
		}
    });
    return indicador;
}

function sumarUnidadAlimentos() {
	var total = 0;
	$.each(listaProductosRacionCache, function(i, item) {			
		var unidad = $('#txt_ali_uni_'+item.idProducto).val();
		if (!esnulo(unidad)) {
			total = total + parseFloat(unidad);
		}			
    });
	$('#txt_ali_tot_tm').val(formatMontoAll(total));
}

function cargarProductoNoAlimentario(idCategoria, codigoProducto) {
	var params = { 
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/control-calidad/listarProductoXCategoria', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.idProducto+'">'+item.nombreProducto+'</option>';
	        });
	        $('#sel_no_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_producto').val(codigoProducto);      	
	        } else {
//				frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
	        }
	        $('#sel_no_producto').select2().trigger('change');
			$('#sel_no_producto').select2({
				  dropdownParent: $('#div_pro_det_no_alimentarios')
			});
		}
		loadding(false);		
	});
}

function sumarUnidadNoAlimentarios() {
	var total = 0;
	$.each(listaNoAlimentariosCache, function(i, item) {			
		var unidad = $('#txt_no_ali_uni_'+item.idProducto).val();
		if (!esnulo(unidad)) {
			total = total + parseFloat(unidad);
		}			
    });
	$('#txt_no_ali_tot_tm').val(formatMontoAll(total));
}

function listarDocumentoProgramacion(indicador) {
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val()
	};			
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarDocumentoProgramacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleDocumentos(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleDocumentos(respuesta) {

	tbl_det_documentos.dataTable().fnDestroy();
	
	tbl_det_documentos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDocumentoProgramacion',
			sClass : 'opc-center',
			render: function(data, type, row) {
				if (data != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox"><i></i>'+
							'</label>';	
				} else {
					return '';	
				}											
			}
		}, {	
			data : 'idDocumentoProgramacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDocumento'
		}, {
			data : 'nroDocumento',
			render: function(data, type, row) {
				if (data != null) {
					return '<button type="button" id="'+row.codigoArchivoAlfresco+'" name="'+row.nombreArchivo+'"'+ 
						   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
				} else {
					return '';	
				}											
			}
		}, {
			data : 'fechaDocumento'
		}, {
			data : 'nombreArchivo'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaDocumentosCache = respuesta;

}

function grabarDetalleDocumento(params) {
	consultarAjax('POST', '/programacion-bah/programacion/grabarDocumentoProgramacion', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoProgramacion(true);
			addSuccessMessage(null, respuesta.mensajeRespuesta);	
		}
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
}

function descargarDocumento(codigo, nombre) {	
	loadding(true);
	var url = VAR_CONTEXT + '/common/archivo/exportarArchivo/'+codigo+'/'+nombre+'/';	
	$.fileDownload(url).done(function(respuesta) {
		loadding(false);	
		if (respuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, mensajeReporteError);
		} else {
			addInfoMessage(null, mensajeReporteExito);
		}		
	}).fail(function (respuesta) {
		loadding(false);
		addErrorMessage(null, mensajeReporteError);
	});	
}

function listarEstadoProgramacion(indicador) {
	
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val()
	};		

	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarEstadoProgramacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleEstadoProgramacion(respuesta);
			if (indicador) {
				loadding(false);
			}
		}		
	});
}

function listarDetalleEstadoProgramacion(respuesta) {
	tbl_det_estados.dataTable().fnDestroy();
	
	tbl_det_estados.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idEstado',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreEstado'
		}, {
			data : 'fechaEstado'
		}, {
			data : 'observacion'
		}, {
			data : 'usuario'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
}