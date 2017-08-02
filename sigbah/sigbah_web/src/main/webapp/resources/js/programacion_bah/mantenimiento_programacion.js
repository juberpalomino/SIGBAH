var listaDetalleAlmacenesCache = new Object();

var listaDetalleAlmacenesCache = new Object();
var listaDetalleAlmacenesCache = new Object();

var listaProductosRacionCache = new Object();
var listaProgramacionAlimentosCache = new Object();
var programacionAlimentosCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_almacenes = $('#tbl_det_almacenes');

var tbl_pro_racion = $('#tbl_pro_racion');
var tbl_res_pro_alimentos = $('#tbl_res_pro_alimentos');

//var tbl_det_no_alimentarios = $('#tbl_det_no_alimentarios');
//var frm_det_no_alimentarios = $('#frm_det_no_alimentarios');

//var tbl_det_documentos = $('#tbl_det_documentos');
//var frm_det_documentos = $('#frm_det_documentos');

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
						
						$('#txt_programacion').val(respuesta.codigoProgramacion+'-'+$('#txt_descripcion').val());
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
		$.each(listaProgramacionAlimentosCache.listaProgramacionAlimento, function(i, item) {
			if ($('#chk_'+item.idProgramacionUbigeo).is(':checked')) {
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
			programacionAlimentosCache = listaProgramacionAlimentosCache.listaProgramacionAlimento[indices];

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
					arrIdProducto.push(item.idProducto);
					arrUnidad.push(unidad);
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
		$.each(listaProgramacionAlimentosCache.listaProgramacionAlimento, function(i, item) {
			if ($('#chk_'+item.idProgramacionUbigeo).is(':checked')) {
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
			
			$('#txt_programacion').val($('#txt_descripcion').val());
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
			} else if (programacion.tipoAtencion == '3') { // Ambos
				listarProgramacionRacionOperativa(false);
				listarDetalleProgramacionAlimento(false);
			}
			
//			listarProductoProgramacion(false);			
//			listarDocumentoProgramacion(false);
			
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
//			listarDetalleAlimentarios(new Object());
//			listarDetalleNoAlimentarios(new Object());
//			listarDetalleDocumentos(new Object());

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
	
	listaDetalleAlmacenesCache = respuesta;

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
	
	listaProductosRacionCache = respuesta;

}

function listarDetalleProgramacionAlimento(indicador) {
	
	var arrIdProducto = [];
	$.each(listaProductosRacionCache, function(i, item) {
		arrIdProducto.push(item.idProducto);
    });
	
	var val_nro_racion = $('#sel_nro_racion').val();
	var arr_nro_racion = val_nro_racion.split('_');

	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
		idRacionOperativa : arr_nro_racion[0],
		arrIdProducto : arrIdProducto		
	};		

	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarDetalleProgramacionAlimento', params, function(respuesta) {
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

			if (respuesta.listaProgramacionAlimento.length > 0) {
				var row_num = 1;
				$.each(respuesta.listaProgramacionAlimento, function(index, item) {
					row = $('<tr/>');
					row.append($('<td class="opc-right" />').html('<label class="checkbox"><input type="checkbox" id="chk_'+item.idProgramacionUbigeo+'"><i></i></label>'));
					row.append($('<td/>').html(row_num));
					row.append($('<td/>').html(item.departamento));
					row.append($('<td/>').html(item.provincia));
					row.append($('<td/>').html(item.distrito));
					row.append($('<td/>').html(item.persAfect));
					row.append($('<td/>').html(item.persDam));
					row.append($('<td/>').html(item.totalPers));
					row.append($('<td/>').html(item.totalRaciones));
					$.each(item.listaProducto, function(i, item_prod) {
						row.append($('<td/>').html(item_prod.unidad));
				    });					
					row.append($('<td/>').html(item.totalTm));
					table.append(row);
					row_num++;
				});
			}
			
			$('#div_det_pro_alimentos').html(table);
			
			listarResumenStock(respuesta.listaResumenStock);
			
			listaProgramacionAlimentosCache = respuesta;

			if (indicador) {
				loadding(false);
			}
		}
	});
}


function listarResumenStock(respuesta) {
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
			data : 'cantidad'
		}, {
			data : 'cantidad'
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
