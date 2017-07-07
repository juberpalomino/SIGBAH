var listaOrdenIngresoCache = new Object();

var tbl_mnt_ord_ingreso = $('#tbl_mnt_ord_ingreso');
var frm_ord_ingreso = $('#frm_ord_ingreso');

$(document).ready(function() {
	
	inicializarDatos();
	
	frm_ord_ingreso.bootstrapValidator({
		framework : 'bootstrap',
		excluded : [':disabled', ':hidden'],
		fields : {
			sel_anio : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar Año.'
					}
				}
			},
			sel_ddi : {
				validators : {
					notEmpty : {
						message : 'Debe seleccionar DDI.'
					}
				}
			}
		}
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_ord_ingreso.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoDdi : $('#sel_ddi').val(),
				codigoAlmacen : $('#sel_almacen').val(),
				codigoMovimiento : $('#sel_tip_movimiento').val()
			};
			
			loadding(true);
			
			consultarAjax('GET', '/gestion-almacenes/orden-ingreso/listarOrdenIngreso', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarOrdenIngreso(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
		tbl_mnt_ord_ingreso.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_ord_ingreso.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idIngreso = listaOrdenIngresoCache[index].idIngreso;
				codigo = codigo + idIngreso + '_';
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
			var url = VAR_CONTEXT + '/gestion-almacenes/orden-ingreso/mantenimientoOrdenIngreso/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/orden-ingreso/mantenimientoOrdenIngreso/0';
		$(location).attr('href', url);
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		
		var row = $('#tbl_mnt_ord_ingreso > tbody > tr').length;
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
		var codigoDdi = $('#sel_ddi').val();
		var codigoAlmacen = $('#sel_almacen').val();
		var codigoMovimiento = $('#sel_tip_movimiento').val();
		var url = VAR_CONTEXT + '/gestion-almacenes/orden-ingreso/exportarExcel/';
		url += verificaParametro(codigoAnio) + '/';
		url += verificaParametro(codigoDdi) + '/';
		url += verificaParametro(codigoAlmacen) + '/';
		url += verificaParametro(codigoMovimiento);
		
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
		var codigo = ''
		tbl_mnt_ord_ingreso.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_ord_ingreso.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idIngreso = listaOrdenIngresoCache[index].idIngreso;
				codigo = codigo + idIngreso + '_';
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
			var url = VAR_CONTEXT + '/gestion-almacenes/orden-ingreso/exportarPdf/'+codigo;
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
	
});

function inicializarDatos() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#ul_ord_ingreso').css('display', 'block');	
	$('#li_ord_ingreso').attr('class', 'active');
	$('#li_ord_ingreso').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {

		listarOrdenIngreso(new Object());		
		
	}
}

function listarOrdenIngreso(respuesta) {

	tbl_mnt_ord_ingreso.dataTable().fnDestroy();
	
	tbl_mnt_ord_ingreso.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idIngreso',
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
			data : 'codigoAnio'
		}, {
			data : 'nombreDdi'
		}, {
			data : 'nombreAlmacen'
		}, {
			data : 'nroOrdenIngreso'
		}, {
			data : 'fechaEmision'
		}, {
			data : 'nombreMovimiento'
		}, {
			data : 'nombreEstado'
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
  			{ width : '15%', targets : 2 },
  			{ width : '15%', targets : 3 }
  		]
	});
	
	listaOrdenIngresoCache = respuesta;

}

