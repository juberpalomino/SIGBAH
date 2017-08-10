var listaProgramacionCache = new Object();

var tbl_mnt_programacion = $('#tbl_mnt_programacion');
var frm_programacion = $('#frm_programacion');

var frm_gra_estado = $('#frm_gra_estado');

$(document).ready(function() {

	frm_programacion.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_anio : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Año.'
					}
				}
			}
		}
	});
	
	frm_gra_estado.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_estado : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Estado.'
					}
				}
			}
		}
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_programacion.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			listarProgramacion(true);			
		}
		
	});
	
	inicializarDatos();
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_mnt_programacion.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_programacion.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idProgramacion = listaProgramacionCache[index].idProgramacion;
				codigo = codigo + idProgramacion + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);
			var url = VAR_CONTEXT + '/programacion-bah/programacion/mantenimientoProgramacion/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bah/programacion/mantenimientoProgramacion/0';
		$(location).attr('href', url);
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_programacion > tbody > tr').length;
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
		
		var codigoAnio = $('#sel_anio').val();
		var codigoMes = $('#sel_mes').val();
		var idDdi = usuarioBean.idDdi;
		var idFenomeno = $('#sel_fenomeno').val();
		var idEstado = $('#sel_estado').val();
		var url = VAR_CONTEXT + '/programacion-bah/programacion/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametro(codigoMes) + '/';
		url += verificaParametroInt(idDdi) + '/';
		url += verificaParametroInt(idFenomeno) + '/';
		url += verificaParametroInt(idEstado);
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
	
	$('#href_imprimir').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_mnt_programacion.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_programacion.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idProgramacion = listaProgramacionCache[index].idProgramacion;
				codigo = codigo + idProgramacion + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);
			var url = VAR_CONTEXT + '/programacion-bah/programacion/exportarPdf/'+codigo;
			$.fileDownload(url).done(function(respuesta) {
				loadding(false);	
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else {
					addInfoMessage(null, mensajeReporteExito);
				}
			}).fail(function (respuesta) {
				loadding(false);
				if (respuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, mensajeReporteError);
				} else if (respuesta == NOTIFICACION_VALIDACION) {
					addWarnMessage(null, mensajeReporteValidacion);
				}
			});
		}
	});
	
	$('#href_estados').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		var nombreProgramacion = '';
		tbl_mnt_programacion.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_programacion.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idProgramacion = listaProgramacionCache[index].idProgramacion;
				codigo = codigo + idProgramacion + '_';
				nombreProgramacion = listaProgramacionCache[index].nombreProgramacion;
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			loadding(true);			
			var params = { 
				idProgramacion : codigo
			};			
			consultarAjax('GET', '/programacion-bah/programacion/obtenerEstadosProgramacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					$('#hid_cod_programacion').val(codigo);
					$('#txt_programacion').val(nombreProgramacion);
					$('#txt_observacion').val('');
					var options = '';
			        $.each(respuesta, function(i, item) {
			            options += '<option value="'+item.idEstado+'">'+item.nombreEstado+'</option>';
			        });
			        $('#sel_pro_estado').html(options);
					$('#div_gra_estado').modal('show');
				}
				loadding(false);
			});
		}

	});
	
	$('#btn_gra_estado').click(function(e) {
		e.preventDefault();

		var bootstrapValidator = frm_gra_estado.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var idEstado = $('#sel_estado').val();
			if (idEstado == ESTADO_ANULADO) {				
				$.SmartMessageBox({
					title : 'Esa seguro que desea cambiar de estado ?',
					content : '',
					buttons : '[No][Si]'
				}, function(ButtonPressed) {
					if (ButtonPressed === 'Si') {	
						cambiarEstado();						
					}	
				});				
			} else {				
				cambiarEstado();				
			}
		}
	});
	
	$('#btn_can_estado, #btn_clo_estado').click(function(e) {
		e.preventDefault();
		frm_gra_estado.data('bootstrapValidator').resetForm();
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
		$('#sel_anio').val(usuarioBean.codigoAnio);
		$('#sel_fenomeno').select2().trigger('change');
		$('#txt_fecha').val(get_date_form()); // Fecha actual del sistema
		if (indicador == '1') { // Retorno
			$('#btn_buscar').click();
		} else {
			listarDetalleProgramacion(new Object());
		}
	}
}

function listarProgramacion(indicador) {
	var params = { 
		codigoAnio : $('#sel_anio').val(),
		codigoMes : $('#sel_mes').val(),
		idDdi : usuarioBean.idDdi,
		idFenomeno : $('#sel_fenomeno').val(),
		idEstado : $('#sel_estado').val()
	};
	if (indicador) {
		loadding(true);
	}
	consultarAjaxSincrono('GET', '/programacion-bah/programacion/listarProgramacion', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProgramacion(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarDetalleProgramacion(respuesta) {

	tbl_mnt_programacion.dataTable().fnDestroy();
	
	tbl_mnt_programacion.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idProgramacion',
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
			data : 'idProgramacion',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'codigoAnio'
		}, {
			data : 'nombreMes'
		}, {
			data : 'nroProgramacion'
		}, {
			data : 'fechaProgramacion'
		}, {
			data : 'nombreFenomeno'
		}, {
			data : 'nombreProgramacion'
		}, {
			data : 'nroDee'
		}, {
			data : 'nombreEstado'
		}, {
			data : 'nombreRegion'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		columnDefs : [
			{ width : '15%', targets : 6 },
			{ width : '15%', targets : 7 },
			{ width : '15%', targets : 8 }
		]
	});
	
	listaProgramacionCache = respuesta;

}

function cambiarEstado() {
	var params = { 
		idProgramacion : $('#hid_cod_programacion').val(),
		idEstado : $('#sel_estado').val(),
		fechaEstado : $('#txt_fecha').val(),
		observacion : $('#txt_observacion').val()
	};
	
	loadding(true);
	
	consultarAjax('POST', '/programacion-bah/programacion/grabarEstadoProgramacion', params, function(respuesta) {
		$('#div_gra_estado').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {				
			listarProgramacion(false);
			addSuccessMessage(null, respuesta.mensajeRespuesta);
		}		
		frm_gra_estado.data('bootstrapValidator').resetForm();
		loadding(false);
	});
}