var listaGuiaRemisionCache = new Object();

var tbl_mnt_gui_remision = $('#tbl_mnt_gui_remision');
var frm_gui_remision = $('#frm_gui_remision');

$(document).ready(function() {
	
	frm_gui_remision.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
//			sel_anio : {
//				validators : {
//					notEmpty : {
//						message : 'Debe seleccionar Año.'
//					}
//				}
//			}
		}
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_gui_remision.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoMes : $('#sel_mes').val(),
				idMovimiento : $('#sel_tip_movimiento').val()
			};
			
			loadding(true);
			
			consultarAjax('GET', '/donaciones/guia-remision/listarGuiaRemision', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarGuiaRemision(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	inicializarDatos();
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_mnt_gui_remision.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_gui_remision.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idGuiaRemision = listaGuiaRemisionCache[index].idGuiaRemision;
				codigo = codigo + idGuiaRemision + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else if (indices.length > 1) {
			addWarnMessage(null, mensajeValidacionSeleccionarSoloUnRegistro);
		} else {
			loadding(true);
			var url = VAR_CONTEXT + '/donaciones/guia-remision/mantenimientoGuiaRemision/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_gui_remision > tbody > tr').length;
		var empty = null;
		$('tr.odd').each(function() {		
			empty = $(this).find('.dataTables_empty').text();
			return false;
		});					
		if (!esnulo(empty) || row < 1) {
			addWarnMessage(null, mensajeReporteRegistroValidacion);
			return;
		}

		loadding(true);
		
		var codigoAnio = $('#sel_anio').val();
		var codigoMes = $('#sel_mes').val();
		var idMovimiento = $('#sel_tip_movimiento').val();
		var url = VAR_CONTEXT + '/donaciones/guia-remision/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametro(codigoMes) + '/';
		url += verificaParametroInt(idMovimiento);
		console.log("ENTRO ");
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
		tbl_mnt_gui_remision.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_gui_remision.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idGuiaRemision = listaGuiaRemisionCache[index].idGuiaRemision;
				codigo = codigo + idGuiaRemision + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, mensajeValidacionSeleccionarRegistro);
		} else if (indices.length > 1) {
			addWarnMessage(null, mensajeValidacionSeleccionarSoloUnRegistro);
		} else {
			$('#hid_codigo').val(codigo);
			$('#chk_gui_remision').prop('checked', false);
			$('#chk_man_carga').prop('checked', false);
			$('#chk_act_ent_recepcion').prop('checked', false);
			$('#div_imp_pdf').modal('show');
		}
	});
	
	$('#btn_exportar').click(function(e) {
		e.preventDefault();
		var codigo = $('#hid_codigo').val();
		var ind_gui = $('#chk_gui_remision').is(':checked') ? '1' : '0';
		var ind_man = $('#chk_man_carga').is(':checked') ? '1' : '0';
		var ind_act = $('#chk_act_ent_recepcion').is(':checked') ? '1' : '0';
		var url = VAR_CONTEXT + '/donaciones/guia-remision/exportarPdf/';
		url = url + codigo + '/' + ind_gui + '/'+ ind_man + '/' + ind_act;
		console.log("RUtass: "+url);		
		if ((ind_gui == '0' && ind_man == '0' && ind_act == '0') || 
				(ind_gui == '1' && ind_man == '1' && ind_act == '1') || 
				(ind_gui == '1' && ind_man == '1' && ind_act == '0') || 
				(ind_gui == '1' && ind_man == '0' && ind_act == '1') || 
				(ind_gui == '0' && ind_man == '1' && ind_act == '1') ) {
			addWarnMessage(null, 'Debe de Seleccionar solo un tipo reporterrrr.');
			return;
		}
		loadding(true);
		$.fileDownload(url).done(function(respuesta) {
//			$('#div_imp_pdf').modal('hide');
			loadding(false);	
			if (respuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, mensajeReporteError);
			} else {
				addInfoMessage(null, mensajeReporteExito);
			}
		}).fail(function (respuesta) {
//			$('#div_imp_pdf').modal('hide');
			loadding(false);
			if (respuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, mensajeReporteError);
			} else if (respuesta == NOTIFICACION_VALIDACION) {
				addWarnMessage(null, mensajeReporteValidacion);
			}
		});

	});
	
});

function inicializarDatos() {

  	$('#li_gui_remision').addClass('active');
	$('#ul_donaciones').css('display', 'block');
	$('#ul_don_salidas').css('display', 'block');
	$('#li_gui_remision').attr('class', 'active');
	$('#li_gui_remision').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
//		$('#sel_anio').val(guiaRemision.codigoAnio);
//		$('#sel_mes').val(guiaRemision.codigoMes);
//		$('#sel_almacen').val(guiaRemision.idAlmacen);
//		$('#sel_almacen').prop('disabled', true);
//		if (indicador == '1') { // Retorno
			$('#btn_buscar').click();
//		} else {
//			listarGuiaRemision(new Object());		
//		}
	}
}

function listarGuiaRemision(respuesta) {

	tbl_mnt_gui_remision.dataTable().fnDestroy();
	
	tbl_mnt_gui_remision.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idGuiaRemision',
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
			data : 'idGuiaRemision',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'codigoAnio'
		}, {
			data : 'nombreMes'
		}, {
			data : 'fechaEmision'
		}, {
			data : 'nroOrdenSalida'
		}, {
			data : 'nroGuiaRemision'
		}, {
			data : 'nroManifiestoCarga'
		}, {
			data : 'nroActaEntregaRecepcion'
		}, {
			data : 'nombreMovimiento'
		}, {
			data : 'nombreEstado'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false,
		iDisplayLength : 15,
		aLengthMenu : [
			[15, 50, 100],
			[15, 50, 100]
		],
		columnDefs : [
  			{ width : '14%', targets : 5 },
  			{ width : '14%', targets : 6 },
			{ width : '15%', targets : 7 },
			{ width : '14%', targets : 8 }
  		]
	});
	
	listaGuiaRemisionCache = respuesta;

}

