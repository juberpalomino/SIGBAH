var listaRequerimientoEdanCache = new Object();
var tbl_mnt_req_edan = $('#tbl_mnt_req_edan');
var frm_req_edan = $('#frm_req_edan');

$(document).ready(function() {

	inicializarDatos();
	
	frm_req_edan.bootstrapValidator({
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
	
	$('#btn_aceptar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_req_edan.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var params = { 
				codAnio : $('#sel_anio').val(),
				codMes : $('#sel_mes').val(),
//				idDdi : $('#sel_ddi').val(),
				idFenomeno : $('#sel_fenomeno').val()
			};
			
			loadding(true);
			
			consultarAjax('GET', '/programacion-bath/requerimiento/listarRequerimientos', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarRequerimiento(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	
	
//	$('#href_editar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = '';
//		var codigoAnio = '';
//		tbl_mnt_req_edan.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_mnt_req_edan.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);				
//				// Verificamos que tiene mas de un registro marcado y salimos del bucle
//				if (!esnulo(codigo)) {
//					return false;
//				}
//				var idEmergencia = listaRequerimientoEdanCache[index].idEmergencia;
//				codigo = codigo + idEmergencia + '_';
//				
//				var codAnio = listaRequerimientoEdanCache[index].codAnio;
//				codigoAnio = codigoAnio + codAnio + '_';
//			}
//		});
//		
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//			codigoAnio = codigoAnio.substring(0, codigoAnio.length - 1);
//		}
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			loadding(true);
//			var url = VAR_CONTEXT + '/programacion-bath/emergencia/mantenimientoEmergencia/'+codigo+'/'+codigoAnio;
//			$(location).attr('href', url );
//			
//		}
//		
//	});
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/requerimiento/mantenimientoRequerimiento/0';
		$(location).attr('href', url);
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_req_edan > tbody > tr').length;
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
		
		var codAnio = $('#sel_anio').val();
		var codMes = $('#sel_mes').val();
//		var codDdi = $('#sel_ddi').val();
		var codFenomeno = $('#sel_fenomeno').val();
		
		var url = VAR_CONTEXT + '/programacion-bath/requerimiento/exportarExcel/';
		url += verificaParametro(codAnio) + '/';
		url += verificaParametro(codMes) + '/';
//		url += verificaParametro(codDdi) + '/';
		url += verificaParametro(codFenomeno);
		
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
	
//	$('#href_imprimir').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = '';
//		tbl_mnt_req_edan.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_mnt_req_edan.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);				
//				// Verificamos que tiene mas de un registro marcado y salimos del bucle
//				if (!esnulo(codigo)) {
//					return false;
//				}
//				var idEmergencia = listaRequerimientoEdanCache[index].idEmergencia;
//				codigo = codigo + idEmergencia + '_';
//			}
//		});
//		
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//		}
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			loadding(true);
//			var url = VAR_CONTEXT + '/programacion-bath/emergencia/exportarPdf/'+codigo;
//			$.fileDownload(url).done(function(respuesta) {
//				loadding(false);	
//				if (respuesta == NOTIFICACION_ERROR) {
//					addErrorMessage(null, mensajeReporteError);
//				} else {
//					addInfoMessage(null, mensajeReporteExito);
//				}
//			}).fail(function (respuesta) {
//				loadding(false);
//				if (respuesta == NOTIFICACION_ERROR) {
//					addErrorMessage(null, mensajeReporteError);
//				} else if (respuesta == NOTIFICACION_VALIDACION) {
//					addWarnMessage(null, mensajeReporteValidacion);
//				}
//			});
//		}
//	});
	
});

function inicializarDatos() {
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_req_edan').attr('class', 'active');
	$('#li_req_edan').closest('li').children('a').attr('href', '#');
	

	
}

function listarRequerimiento(respuesta) {

	tbl_mnt_req_edan.dataTable().fnDestroy();
	
	
	tbl_mnt_req_edan.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idRequerimiento',
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
			data : 'idRequerimiento',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'codAnio'
		}, {
			data : 'nomMes'
		}, {
			data : 'fechaRequerimiento'
		}, {
			data : 'numRequerimiento'
		}, {
			data : 'nomRequerimiento'
		}, {
			data : 'descFenomeno'
		}, {
			data : 'nomRegion'
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
	
	listaRequerimientoEdanCache = respuesta;

}