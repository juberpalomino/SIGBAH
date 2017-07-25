var listaDamnificadosCache = new Object();
var listaEmergenciasActivosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');
var tbl_det_afectados = $('#tbl_det_afectados'); 


var frm_det_prog_ubigeo = $('#frm_det_alimentarios');
var tbl_mnt_emer_act = $('#tbl_mnt_emer_act');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#sel_departamento_ubi').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			cargarProvincia(codigo, null);
		} else {
			$('#sel_provincia_ubi').html('');
		}
	});
//	$('#txt_fecha').datepicker().on('changeDate', function(e) {
//		e.preventDefault();
//		frm_dat_generales.bootstrapValidator('revalidateField', $(this).attr('id'));	
//	});
//	
//	$('#txt_fec_vencimiento').datepicker().on('changeDate', function(e) {
//		e.preventDefault();
//		frm_det_alimentarios.bootstrapValidator('revalidateField', $(this).attr('id'));	
//	});
//	
//	$('#txt_no_fec_vencimiento').datepicker().on('changeDate', function(e) {
//		e.preventDefault();
//		frm_det_no_alimentarios.bootstrapValidator('revalidateField', $(this).attr('id'));	
//	});
//	
//	$('#txt_doc_fecha').datepicker().on('changeDate', function(e) {
//		e.preventDefault();
//		frm_det_documentos.bootstrapValidator('revalidateField', $(this).attr('id'));	
//	});
//	
//	$('#sel_nro_ord_compra').change(function() {
//		var codigo = $(this).val();
//		var arr = codigo.split('_');
//		if (arr.length > 1) {
//			$('#txt_det_ord_compra').val(arr[1]);
//		} else {
//			$('#txt_det_ord_compra').val('');
//		}
//	});
//	
//	$('#sel_tip_control').change(function() {
//		var val_tip_control = $(this).val();		
//		if (!esnulo(val_tip_control)) {
//			
//			frm_dat_generales.data('bootstrapValidator').resetForm();
//			
//			cargarTipoControl(val_tip_control);
//			
//			$('#sel_ori_almacen').val('');
//			$('#sel_ori_en_almacen').val('');
//			$('#sel_inspector').val('');
//			$('#sel_proveedor').val('');
//			$('#txt_representante').val('');
//			$('#sel_emp_transporte').val('');
//			$('#sel_chofer').val('');
//			$('#txt_nro_placa').val('');
//			
//			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_ori_almacen');
//			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_proveedor');
//			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_emp_transporte');
//			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
//			frm_dat_generales.bootstrapValidator('revalidateField', 'txt_nro_placa');
//			
//		}
//	});
//	
//	$('#sel_proveedor').change(function() {
//		var codigo = $(this).val();		
//		if (!esnulo(codigo)) {
//			var arr = codigo.split('_');
//			if (arr.length > 1) {
//				$('#txt_representante').val(arr[1]);
//			} else {
//				$('#txt_representante').val('');
//			}			
//		} else {
//			$('#txt_representante').val('');
//		}
//	});
//	
//	$('#sel_emp_transporte').change(function() {
//		var codigo = $(this).val();		
//		if (!esnulo(codigo)) {						
//			var params = { 
//				icodigo : codigo
//			};			
//			loadding(true);
//			consultarAjax('GET', '/gestion-almacenes/control-calidad/listarChofer', params, function(respuesta) {
//				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//					addErrorMessage(null, respuesta.mensajeRespuesta);
//				} else {
//					var options = '<option value="">Seleccione</option>';
//			        $.each(respuesta, function(i, item) {
//			            options += '<option value="'+item.vcodigo+'">'+item.descripcion+'</option>';
//			        });
//			        $('#sel_chofer').html(options);
//				}
//				loadding(false);
//				frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
//			});
//		} else {
//			$('#sel_chofer').html('');
//			frm_dat_generales.bootstrapValidator('revalidateField', 'sel_chofer');
//		}
//	});
//	
//	$('#sel_no_cat_producto').change(function() {
//		var idCategoria = $(this).val();		
//		if (!esnulo(idCategoria)) {					
//			cargarProductoNoAlimentario(idCategoria, null);
//		} else {
//			$('#sel_no_producto').html('');
//			frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_no_producto');
//		}
//	});
	
	$('#btn_grabar_dat_gen').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_requerimiento').val();
			var anio = $('#txt_anio').val();
			var nombreReq = $('#txt_descripcion').val();
			var numReq = null;
				var numReqConcat = $('#txt_num_requerimiento').val();
				if (!esnulo(numReqConcat)) {
					var arr = numReqConcat.split('-'); 
					numReq = arr[0]; 
				}
			var fechaReq = $('#txt_fecha_requerimiento').val();  
			var flgSinpad = $('input[name="rb_req_sinpad"]:checked').val();
			var idfenomeno = $('#sel_fenomeno').val();
			var observacion = $('#txt_observaciones').val();
			var idRegion = $('#sel_region').val();
			
			var params = {
				
				codAnio : $('#txt_anio').val(),
				codMes : requerimiento.codMes,
				fkIdeDdi : requerimiento.fkIdeDdi,
				idDdi : requerimiento.idDdi, 
				fkIdeRegion : $('#sel_region').val(),
				codRequerimiento : requerimiento.codRequerimiento,
				nomRequerimiento :$('#txt_descripcion').val(),
				fechaRequerimiento : $('#txt_fecha_requerimiento').val(),
				flgSinpad : flgSinpad,
				fkIdeFenomeno :  $('#sel_fenomeno').val(),
				observacion : $('#txt_observaciones').val()
						
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/requerimiento/grabarRequerimiento', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
//						$('#hid_cod_con_calidad').val(respuesta.idControlCalidad);
//						$('#txt_nro_con_calidad').val(respuesta.nroControlCalidad);
//						
//						if (tipoBien == '1') {					
//							$('#li_alimentarios').attr('class', '');
//							$('#li_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
//						} else {							
//							$('#li_no_alimentarios').attr('class', '');
//							$('#li_no_alimentarios').closest('li').children('a').attr('data-toggle', 'tab');
//						}
//						$('#li_documentos').attr('class', '');
//						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');

						addSuccessMessage(null, 'Se genero el N° Requerimiento ');
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('#btn_aceptar_ubigeo').click(function(e) {
		e.preventDefault();
		
//		var bootstrapValidator = frm_det_prog_ubigeo.data('bootstrapValidator');
//		bootstrapValidator.validate();
//		if (bootstrapValidator.isValid()) {
			var params = { 
				codAnio : $('#sel_anio_ubi').val(),
				codMes : $('#sel_mes_ubi').val(),
				codDpto: $('#sel_departamento_ubi').val(),
				codProvincia : $('#sel_provincia_ubi').val(),
				idFenomeno : $('#sel_fenomeno_ubi').val()
			};
			
			loadding(true);
			
			consultarAjax('GET', '/programacion-bath/requerimiento/listarEmergenciasActivas', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarEmergenciasActivas(respuesta);
				}
				loadding(false);
			});
			
//		}
		
	});
	
	$('#href_emer_acti_exp_excel').click(function(e) {
		e.preventDefault();
		var row = $('#tbl_mnt_emer_act > tbody > tr').length;
		var empty = null;
		$('tr.odd').each(function() {		
			empty = $(this).find('.dataTables_empty').text();
			return false;
		});					
		if (!esnulo(empty) || row < 1) {
			addWarnMessage(null, 'No se encuentran registros para generar el reporte.');
			return;
		}

		loadding(true);
		
		var codAnio = $('#sel_anio_ubi').val();
		var codMes = $('#sel_mes_ubi').val();
		var codDpto = $('#sel_departamento_ubi').val();
		var codProvincia = $('#sel_provincia_ubi').val();
		var idFenomeno = $('#sel_fenomeno_ubi').val();
		
		var url = VAR_CONTEXT + '/programacion-bath/requerimiento/exportarExcelEmergenciasActivas/';
		url += verificaParametro(codAnio) + '/';
		url += verificaParametro(codMes) + '/';
		url += verificaParametro(codDpto) + '/';
		url += verificaParametro(codProvincia) + '/';
		url += verificaParametro(idFenomeno);
		
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

	});
//	$('.btn_retornar').click(function(e) {
//		e.preventDefault();
//
//		loadding(true);					
//		var url = VAR_CONTEXT + '/gestion-almacenes/control-calidad/inicio/1';
//		$(location).attr('href', url);
//		
//	});
	
	$('#btn_agregar_ubigeo').click(function(e) {
	e.preventDefault();

//	$('#h4_tit_alimentarios').html('Nuevo Producto');
//	frm_det_alimentarios.trigger('reset');
//	
//	$('#sel_producto').select2().trigger('change');
//	$('#sel_producto').select2({
//		  dropdownParent: $('#div_pro_det_alimentarios')
//	});
	
	$('#hid_cod_producto').val('');
	$('#div_det_prog_ubigeo').modal('show');
	
});
	
//	$('#href_ali_nuevo').click(function(e) {
//		e.preventDefault();
//
//		$('#h4_tit_alimentarios').html('Nuevo Producto');
//		frm_det_alimentarios.trigger('reset');
//		
//		$('#sel_producto').select2().trigger('change');
//		$('#sel_producto').select2({
//			  dropdownParent: $('#div_pro_det_alimentarios')
//		});
//		
//		$('#hid_cod_producto').val('');
//		$('#div_det_alimentarios').modal('show');
//		
//	});
//	
//	$('#href_ali_editar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//			}
//		});
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			
//			var obj = listaAlimentariosCache[indices[0]];
//			
//			$('#h4_tit_alimentarios').html('Actualizar Producto');
//			frm_det_alimentarios.trigger('reset');
//			
//			$('#hid_cod_producto').val(obj.idDetalleControlCalidad);
//			
//			$('#sel_producto').val(obj.idProducto+'_'+obj.nombreUnidad);
//
//			$('#sel_producto').select2().trigger('change');
//			$('#sel_producto').select2({
//				  dropdownParent: $('#div_pro_det_alimentarios')
//			});
//			
//			$('#sel_uni_medida').val(obj.nombreUnidad);
//			$('#txt_fec_vencimiento').val(obj.fechaVencimiento);
//			$('#txt_can_lote').val(obj.cantidadLote);
//			$('#txt_can_muestra').val(obj.cantidadMuestra);
//			$('#sel_primario').val(obj.primario);
//			$('#sel_olor').val(obj.parOlor);
//			$('#sel_textura').val(obj.parTextura);
//			$('#sel_secundario').val(obj.secundario);
//			$('#sel_color').val(obj.parColor);
//			$('#sel_sabor').val(obj.parSabor);
//			
//			$('#div_det_alimentarios').modal('show');
//		}
//		
//	});
//	
//	$('#href_ali_eliminar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = ''
//		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//				var idDetalleControlCalidad = listaAlimentariosCache[index].idDetalleControlCalidad;
//				codigo = codigo + idDetalleControlCalidad + '_';
//			}
//		});
//		
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//		}
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else {
//			var msg = '';
//			if (indices.length > 1) {
//				msg = 'Está seguro de eliminar los siguientes registros ?';
//			} else {
//				msg = 'Está seguro de eliminar el registro ?';
//			}
//			
//			$.SmartMessageBox({
//				title : msg,
//				content : '',
//				buttons : '[Cancelar][Aceptar]'
//			}, function(ButtonPressed) {
//				if (ButtonPressed === 'Aceptar') {
//	
//					loadding(true);
//					
//					var params = { 
//						arrIdDetalleControlCalidad : codigo
//					};
//			
//					consultarAjax('POST', '/gestion-almacenes/control-calidad/eliminarProductoControlCalidad', params, function(respuesta) {
//						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//							loadding(false);
//							addErrorMessage(null, respuesta.mensajeRespuesta);
//						} else {
//							listarProductoControlCalidad(true);
//							addSuccessMessage(null, respuesta.mensajeRespuesta);							
//						}
//					});
//					
//				}	
//			});
//			
//		}
//		
//	});
//	
//	$('#btn_gra_alimentario').click(function(e) {
//		e.preventDefault();
//		
//		var bootstrapValidator = frm_det_alimentarios.data('bootstrapValidator');
//		bootstrapValidator.validate();
//		if (bootstrapValidator.isValid()) {
//			var idProducto = null;
//			var val_producto = $('#sel_producto').val();
//			if (!esnulo(val_producto)) {
//				var arr = val_producto.split('_');
//				idProducto = arr[0];
//			}			
//			var params = { 
//				idDetalleControlCalidad : $('#hid_cod_producto').val(),
//				idControlCalidad : $('#hid_cod_con_calidad').val(),
//				idProducto : idProducto,
//				fechaVencimiento : $('#txt_fec_vencimiento').val(),
//				cantidadLote : formatMonto($('#txt_can_lote').val()),
//				cantidadMuestra : formatMonto($('#txt_can_muestra').val()),
//				primario : $('#sel_primario').val(),
//				parOlor : $('#sel_olor').val(),
//				parTextura : $('#sel_textura').val(),
//				secundario : $('#sel_secundario').val(),
//				parColor : $('#sel_color').val(),
//				parSabor : $('#sel_sabor').val()
//			};
//
//			loadding(true);
//			
//			consultarAjax('POST', '/gestion-almacenes/control-calidad/grabarProductoControlCalidad', params, function(respuesta) {
//				$('#div_det_alimentarios').modal('hide');
//				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//					loadding(false);
//					addErrorMessage(null, respuesta.mensajeRespuesta);
//				} else {
//					listarProductoControlCalidad(true);					
//					addSuccessMessage(null, respuesta.mensajeRespuesta);					
//				}
//				frm_det_alimentarios.data('bootstrapValidator').resetForm();
//			});
//			
//		}
//		
//	});
//	
//	$('#btn_can_alimentario').click(function(e) {
//		e.preventDefault();
//		frm_det_alimentarios.data('bootstrapValidator').resetForm();
//	});
//	
//	$('#sel_producto').change(function() {
//		var codigo = $(this).val();		
//		if (!esnulo(codigo)) {
//			var arr = codigo.split('_');
//			if (arr.length > 1) {
//				$('#txt_uni_medida').val(arr[1]);
//			} else {
//				$('#txt_uni_medida').val('');
//			}			
//		} else {
//			$('#txt_uni_medida').val('');
//		}
//	});
//	
//	$('#href_no_ali_nuevo').click(function(e) {
//		e.preventDefault();
//
//		$('#h4_tit_no_alimentarios').html('Nuevo Producto');
//		frm_det_no_alimentarios.trigger('reset');
//		
//		$('#sel_no_producto').html('');
//		$('#sel_no_producto').select2().trigger('change');
//		$('#sel_no_producto').select2({
//			  dropdownParent: $('#div_pro_det_no_alimentarios')
//		});
//		
//		$('#hid_cod_no_producto').val('');
//		$('#div_det_no_alimentarios').modal('show');
//		
//	});
//	
//	$('#href_no_ali_editar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//			}
//		});
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			
//			var obj = listaNoAlimentariosCache[indices[0]];
//			
//			$('#h4_tit_no_alimentarios').html('Actualizar Producto');
//			frm_det_no_alimentarios.trigger('reset');
//			
//			$('#hid_cod_no_producto').val(obj.idDetalleControlCalidad);
//			
//			$('#sel_no_cat_producto').val(obj.idCategoria);
//			cargarProductoNoAlimentario(obj.idCategoria, obj.idProducto+'_'+obj.nombreUnidad);			
//			$('#sel_no_uni_medida').val(obj.nombreUnidad);
//			$('#txt_no_fec_vencimiento').val(obj.fechaVencimiento);
//			$('#txt_no_can_lote').val(obj.cantidadLote);
//			$('#txt_no_can_muestra').val(obj.cantidadMuestra);
//			$('#sel_no_primario').val(obj.primario);
//			$('#sel_no_tecnicas').val(obj.flagEspecTecnicas);
//			$('#sel_no_secundario').val(obj.secundario);
//			$('#sel_no_conformidad').val(obj.flagConforProducto);
//			
//			$('#div_det_no_alimentarios').modal('show');
//		}
//		
//	});
//	
//	$('#href_no_ali_eliminar').click(function(e) {
//		e.preventDefault();
//		
//		var indices = [];
//		var codigo = ''
//		tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_no_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//				var idDetalleControlCalidad = listaNoAlimentariosCache[index].idDetalleControlCalidad;
//				codigo = codigo + idDetalleControlCalidad + '_';
//			}
//		});
//		
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//		}
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else {
//			var msg = '';
//			if (indices.length > 1) {
//				msg = 'Está seguro de eliminar los siguientes registros ?';
//			} else {
//				msg = 'Está seguro de eliminar el registro ?';
//			}
//			
//			$.SmartMessageBox({
//				title : msg,
//				content : '',
//				buttons : '[Cancelar][Aceptar]'
//			}, function(ButtonPressed) {
//				if (ButtonPressed === 'Aceptar') {
//	
//					loadding(true);
//					
//					var params = { 
//						arrIdDetalleControlCalidad : codigo
//					};
//			
//					consultarAjax('POST', '/gestion-almacenes/control-calidad/eliminarProductoControlCalidad', params, function(respuesta) {
//						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//							loadding(false);
//							addErrorMessage(null, respuesta.mensajeRespuesta);
//						} else {
//							listarProductoControlCalidad(true);
//							addSuccessMessage(null, respuesta.mensajeRespuesta);
//						}		
//					});
//					
//				}	
//			});
//			
//		}
//		
//	});
//	
//	$('#btn_gra_no_alimentario').click(function(e) {
//		e.preventDefault();
//		
//		var bootstrapValidator = frm_det_no_alimentarios.data('bootstrapValidator');
//		bootstrapValidator.validate();
//		if (bootstrapValidator.isValid()) {
//			var idProducto = null;
//			var val_producto = $('#sel_no_producto').val();
//			if (!esnulo(val_producto)) {
//				var arr = val_producto.split('_');
//				idProducto = arr[0];
//			}
//			var params = { 
//				idDetalleControlCalidad : $('#hid_cod_no_producto').val(),
//				idControlCalidad : $('#hid_cod_con_calidad').val(),
//				idProducto : idProducto,
//				fechaVencimiento : $('#txt_no_fec_vencimiento').val(),
//				cantidadLote : formatMonto($('#txt_no_can_lote').val()),
//				cantidadMuestra : formatMonto($('#txt_no_can_muestra').val()),
//				primario : $('#sel_no_primario').val(),
//				flagEspecTecnicas : $('#sel_no_tecnicas').val(),
//				secundario : $('#sel_no_secundario').val(),
//				flagConforProducto : $('#sel_no_conformidad').val()
//			};
//			
//			loadding(true);
//			
//			consultarAjax('POST', '/gestion-almacenes/control-calidad/grabarProductoControlCalidad', params, function(respuesta) {
//				$('#div_det_no_alimentarios').modal('hide');
//				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//					loadding(false);
//					addErrorMessage(null, respuesta.mensajeRespuesta);
//				} else {
//					listarProductoControlCalidad(true);
//					addSuccessMessage(null, respuesta.mensajeRespuesta);	
//				}
//				frm_det_no_alimentarios.data('bootstrapValidator').resetForm();
//			});
//			
//		}
//		
//	});
//	
//	$('#btn_can_no_alimentario').click(function(e) {
//		e.preventDefault();
//		frm_det_no_alimentarios.data('bootstrapValidator').resetForm();
//	});
//	
//	$('#sel_no_producto').change(function() {
//		var codigo = $(this).val();		
//		if (!esnulo(codigo)) {
//			var arr = codigo.split('_');
//			if (arr.length > 1) {
//				$('#txt_no_uni_medida').val(arr[1]);
//			} else {
//				$('#txt_no_uni_medida').val('');
//			}			
//		} else {
//			$('#txt_no_uni_medida').val('');
//		}
//	});
//	
//	$('#href_doc_nuevo').click(function(e) {
//		e.preventDefault();
//
//		$('#h4_tit_no_alimentarios').html('Nuevo Documento');
//		$('#frm_det_documentos').trigger('reset');
//		
//		$('#hid_cod_documento').val('');
//		$('#hid_cod_act_alfresco').val('');
//		$('#hid_cod_ind_alfresco').val('');
//		$('#txt_sub_archivo').val(null);
//		$('#div_det_documentos').modal('show');
//		
//	});
//	
//	$('#href_doc_editar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//			}
//		});
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			
//			var obj = listaDocumentosCache[indices[0]];
//			
//			$('#h4_tit_documentos').html('Actualizar Documento');
//			$('#frm_det_documentos').trigger('reset');
//			
//			$('#hid_cod_documento').val(obj.idDocumentoControlCalidad);			
//			$('#sel_tip_producto').val(obj.idTipoDocumento);
//			$('#txt_nro_documento').val(obj.nroDocumento);
//			$('#txt_doc_fecha').val(obj.fechaDocumento);
//			$('#hid_cod_act_alfresco').val(obj.codigoArchivoAlfresco);
//			$('#hid_cod_ind_alfresco').val('');
//			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
//			$('#txt_sub_archivo').val(null);
//			
//			$('#div_det_documentos').modal('show');
//		}
//		
//	});
//	
//	$('#href_doc_eliminar').click(function(e) {
//		e.preventDefault();
//		
//		var indices = [];
//		var codigo = ''
//		tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//				var idDocumentoControlCalidad = listaDocumentosCache[index].idDocumentoControlCalidad;
//				codigo = codigo + idDocumentoControlCalidad + '_';
//			}
//		});
//		
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//		}
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else {
//			var msg = '';
//			if (indices.length > 1) {
//				msg = 'Está seguro de eliminar los siguientes registros ?';
//			} else {
//				msg = 'Está seguro de eliminar el registro ?';
//			}
//			
//			$.SmartMessageBox({
//				title : msg,
//				content : '',
//				buttons : '[Cancelar][Aceptar]'
//			}, function(ButtonPressed) {
//				if (ButtonPressed === 'Aceptar') {
//	
//					loadding(true);
//					
//					var params = { 
//						arrIdDocumentoControlCalidad : codigo
//					};
//			
//					consultarAjax('POST', '/gestion-almacenes/control-calidad/eliminarDocumentoControlCalidad', params, function(respuesta) {
//						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//							loadding(false);
//							addErrorMessage(null, respuesta.mensajeRespuesta);
//						} else {
//							listarDocumentoControlCalidad(true);
//							addSuccessMessage(null, respuesta.mensajeRespuesta);							
//						}
//					});
//					
//				}	
//			});
//			
//		}
//		
//	});
//	
//	$('#btn_gra_documento').click(function(e) {
//		e.preventDefault();
//		
//		var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
//		bootstrapValidator.validate();
//		if (bootstrapValidator.isValid()) {			
//			loadding(true);
//			
//			var params = { 
//				idDocumentoControlCalidad : $('#hid_cod_documento').val(),
//				idControlCalidad : $('#hid_cod_con_calidad').val(),
//				idTipoDocumento : $('#sel_tip_producto').val(),
//				nroDocumento : $('#txt_nro_documento').val(),
//				fechaDocumento : $('#txt_doc_fecha').val(),
//				nombreArchivo : $('#txt_lee_sub_archivo').val()
//			};
//			
//			var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
//			if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
//				var file_name = $('#txt_sub_archivo').val();
//				var file_data = null;
//				if (!esnulo(file_name) && typeof file_name !== 'undefined') {
//					file_data = $('#txt_sub_archivo').prop('files')[0];
//				}
//				
//				var formData = new FormData();
//				formData.append('file_doc', file_data);
//				// Carpeta contenedor, ubicado en config.properties
//		    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.almacen');
//		    	
//				consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
//					if (resArchivo == NOTIFICACION_ERROR) {
//						$('#div_det_documentos').modal('hide');
//						frm_det_documentos.data('bootstrapValidator').resetForm();
//						loadding(false);
//						addErrorMessage(null, mensajeCargaArchivoError);						
//					} else {
//						
//						params.codigoArchivoAlfresco = resArchivo;
//
//						grabarDetalleDocumento(params);					
//					}
//				});
//				
//			} else { // Archivo no cargado
//				
//				params.codigoArchivoAlfresco = $('#hid_cod_act_alfresco').val();
//
//				grabarDetalleDocumento(params);				
//			}
//		}
//		
//	});
//	
//	$('#btn_can_documento').click(function(e) {
//		e.preventDefault();
//		frm_det_documentos.data('bootstrapValidator').resetForm();
//	});
//	
//	$('#txt_sub_archivo').change(function(e) {
//		e.preventDefault();
//	    var file_name = $(this).val();
//	    var file_read = file_name.split('\\').pop();
//	    $('#txt_lee_sub_archivo').val($.trim(file_read).replace(/ /g, '_'));
//	    if (esnulo($('#hid_cod_documento').val())) {
//	    	$('#hid_cod_ind_alfresco').val('1'); // Nuevo registro
//	    } else {
//	    	$('#hid_cod_ind_alfresco').val('2'); // Registro modificado
//	    }
//	    frm_det_documentos.bootstrapValidator('revalidateField', 'txt_lee_sub_archivo');	    
//	});
//	
//	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
//		e.preventDefault();
//		
//		var id = $(this).attr('id');
//		var name = $(this).attr('name');
//		if (!esnulo(id) && !esnulo(name)) {
//			descargarDocumento(id, name);
//		} else {
//			addInfoMessage(null, 'No dispone de documento adjunto asociado.');
//		}
//		
//	});
	
});

function inicializarDatos() {
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_req_edan').attr('class', 'active');
	$('#li_req_edan').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_num_requerimiento').val(requerimiento.numRequerimiento);
		$('#txt_anio').val(requerimiento.codAnio);
		$('#txt_fecha_requerimiento').val(requerimiento.fechaRequerimiento);
		
		
//		if (!esnulo(controlCalidad.idControlCalidad)) {
//			
//			$('#hid_cod_con_calidad').val(controlCalidad.idControlCalidad);		
//			if (controlCalidad.flagTipoBien == '1') {
//				$('#li_no_alimentarios').addClass('disabled');
//				$('#li_no_alimentarios').closest('li').children('a').removeAttr('data-toggle');
//			} else {
//				$('#li_alimentarios').addClass('disabled');
//				$('#li_alimentarios').closest('li').children('a').removeAttr('data-toggle');
//			}
//			
//			$('#txt_fecha').val(controlCalidad.fechaEmision);
//			$('#sel_estado').val(controlCalidad.idEstado);
//			$('#sel_nro_ord_compra').val(controlCalidad.nroOrdenCompra+'_'+controlCalidad.concepto);			
//			$('#txt_det_ord_compra').val(controlCalidad.concepto);
//			
//			$('#sel_tip_control').val(controlCalidad.idTipoControl);
//			cargarTipoControl(controlCalidad.idTipoControl);
//			$('#sel_ori_almacen').val(controlCalidad.idAlmacenOrigen);
//			$('#sel_ori_en_almacen').val(controlCalidad.idEncargado);
//			$('#sel_inspector').val(controlCalidad.idInspector);			
//			var val_idProveedor = controlCalidad.provRep;
//			$('#sel_proveedor').val(val_idProveedor);
//			var arr = val_idProveedor.split('_');
//			if (arr.length > 1) {
//				$('#txt_representante').val(arr[1]);
//			}
//			$('#sel_emp_transporte').val(controlCalidad.idEmpresaTransporte);
//			$('#sel_chofer').val(controlCalidad.idChofer);
//			$('#txt_nro_placa').val(controlCalidad.nroPlaca);
//			$('input[name=rb_tip_bien][value="'+controlCalidad.flagTipoBien+'"]').prop('checked', true);
//			$('#txt_conclusiones').val(controlCalidad.conclusiones);
//			$('#txt_recomendaciones').val(controlCalidad.recomendaciones);
//			
//			$('input[name=rb_tip_bien]').prop('disabled', true);
//			
//			
//			
//			listarProductoControlCalidad(false);
//			
//			listarDocumentoControlCalidad(false);
//			
//		} else {
//			
//			$('#li_alimentarios').addClass('disabled');
//			$('#li_no_alimentarios').addClass('disabled');
//			$('#li_documentos').addClass('disabled');
//			$('#ul_man_con_calidad li.disabled a').removeAttr('data-toggle');
//			
//			$('#txt_fecha').datepicker('setDate', new Date());
//			
//			var arr = $('#sel_nro_ord_compra').val().split('_');
//			if (arr.length > 1) {
//				$('#txt_det_ord_compra').val(arr[1]);
//			}
//			
//			var val_proveedor = $('#sel_proveedor').val();		
//			if (!esnulo(val_proveedor)) {
//				var arr = val_proveedor.split('_');
//				if (arr.length > 1) {
//					$('#txt_representante').val(arr[1]);
//				} else {
//					$('#txt_representante').val('');
//				}			
//			}
//			
//			cargarTipoControl($('#sel_tip_control').val());
//			
//			listarDetalleAlimentarios(new Object());
//			listarDetalleNoAlimentarios(new Object());
//			listarDetalleDocumentos(new Object());
//
//		}
		
//		$('#sel_nro_ord_compra').select2().trigger('change');

	}
	
}

function cargarProvincia(codigo, codigoProvincia) {
	var params = { 
		coddpto : codigo
	};			
	loadding(true);
	consultarAjax('GET', '/programacion-bath/requerimiento/listarProvincia', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
			$.each(respuesta, function(i, item) {
				options += '<option value="'+item.codprov+'">'+item.nombre+'</option>';
			});
			$('#sel_provincia_ubi').html(options);
			if (codigoProvincia != null) {
				$('#sel_provincia_ubi').val(codigoProvincia);       	
			}
		}
		loadding(false);
	});
}

function listarEmergenciasActivas(respuesta) {

	tbl_mnt_emer_act.dataTable().fnDestroy();
	
	tbl_mnt_emer_act.dataTable({
			data : respuesta,
			columns : [ {
				data : 'idEmergencia',
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
				data : 'idEmergencia',
				render : function(data, type, full, meta) {
					var row = meta.row + 1;
					return row;											
				}
			}, {
				data : 'codAnio'
			}, {
				data : 'nombreMes'
			}, {
				data : 'fecha'
			}, {
				data : 'idEmergencia'
			}, {
				data : 'descFenomeno'
			}, {
				data : 'nombreEmergencia'
			}, {
				data : 'desDepartamento'
			}, {
				data : 'desProvincia'
			}, {
				data : 'desDistrito'
			}, {
//				data : 'poblacionInei'
//			}, {
				data : 'famAfectado'
			}, {
				data : 'famDamnificado'
			}, {
				data : 'totalFam'
			}, {
				data : 'persoAfectado'
			}, {
				data : 'persoDamnificado'
			}, {
				data : 'totalPerso'
			} ],
			language : {
				'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
			},
			bFilter : false,
			paging : true,
			ordering : false,
			info : true,
			iDisplayLength : 15,
			aLengthMenu : [
				[15, 50, 100],
				[15, 50, 100]
			],
			columnDefs : [
				{ width : '15%', targets : 3 },
				{ width : '15%', targets : 4 },
				{ width : '15%', targets : 5 },
				{ width : '18%', targets : 7 }
			]
		});
		
	listaEmergenciasActivosCache = respuesta;

	}