var listaControlCalidadCache = new Object();

$(document).ready(function() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#li_con_calidad').addClass('active');
	
	inicializarDatos();
	
	$('#frm_con_calidad').bootstrapValidator({
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
		
		var bootstrapValidator = $('#frm_con_calidad').data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				cod_anio : $('#sel_anio').val(),
				cod_ddi : $('#sel_ddi').val(),
				cod_almacen : $('#sel_almacen').val()
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
		var tbl_mnt_con_calidad = $('#tbl_mnt_con_calidad').DataTable();
		tbl_mnt_con_calidad.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_con_calidad.rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idUbigeo = listaControlCalidadCache[index].idubigeo;
				codigo = codigo + idUbigeo + '_';
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
	
});

function inicializarDatos() {
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {


		
		
	}
}

function listarControlCalidad(respuesta) {

	var tbl_mnt_con_calidad = $('#tbl_mnt_con_calidad');

	tbl_mnt_con_calidad.dataTable().fnDestroy();
	
	tbl_mnt_con_calidad.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idubigeo',
//			sClass : 'opc-center',
			render: function(data, type, row) {
				if (row.idubigeo != null) {
					return '<label class="checkbox">'+
								'<input type="checkbox" id="chk_ubigeo_'+data+'" name="chk_ubigeo"><i></i>'+
							'</label>';	
				} else {
					return '';	
				}											
			}	
		}, {
			data : 'coddpto'
		}, {
			data : 'nombre'
		}, {
			data : 'codprov'
		}, {
			data : 'codprov'
		}, {
			data : 'codprov'
		}, {
			data : 'codprov'
		}, {
			data : 'codprov'
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

