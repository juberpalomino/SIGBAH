var listaControlCalidadCache = new Object();

var tbl_mnt_con_calidad = $('#tbl_mnt_con_calidad');
var frm_con_calidad = $('#frm_con_calidad');

$(document).ready(function() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#li_con_calidad').attr('class', 'active');
	$('#li_con_calidad').closest('li').children('a').attr('href', '#');
	
	inicializarDatos();
	
	frm_con_calidad.bootstrapValidator({
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
		
		var bootstrapValidator = frm_con_calidad.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoDdi : $('#sel_ddi').val(),
				codigoAlmacen : $('#sel_almacen').val()
			};
			
			loadding(true);
			
			consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarControlCalidad(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = ''
		tbl_mnt_con_calidad.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_con_calidad.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idControlCalidad = listaControlCalidadCache[index].idControlCalidad;
				codigo = codigo + idControlCalidad + '_';
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
			var url = VAR_CONTEXT + '/gestion-almacenes/control-calidad/mantenimientoControlCalidad/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/gestion-almacenes/control-calidad/mantenimientoControlCalidad/0';
		$(location).attr('href', url);
		
	});
	
	$('#href_exp_excel').click(function(e) {
		e.preventDefault();

		loadding(true);
		
		var codigoAnio = $('#sel_anio').val();
		var codigoDdi = $('#sel_ddi').val();
		var codigoAlmacen = $('#sel_almacen').val();
		var url = VAR_CONTEXT + '/gestion-almacenes/control-calidad/exportarExcel/';
		url = url + codigoAnio + '/'+ codigoDdi + '/' + codigoAlmacen;
		
		$.fileDownload(url).done(function (respuesta) {
			loadding(false);	
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
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

		 
		
	});
	
});

function inicializarDatos() {
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {


		
		
	}
}

function listarControlCalidad(respuesta) {

	tbl_mnt_con_calidad.dataTable().fnDestroy();
	
	tbl_mnt_con_calidad.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idControlCalidad',
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
			data : 'nroControlCalidad'
		}, {
			data : 'fechaEmision'
		}, {
			data : 'tipoControlCalidad'
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
		]
	});
	
	listaControlCalidadCache = respuesta;

}

