var listaDonacionesCache = new Object();

var tbl_mnt_con_donaciones = $('#tbl_mnt_con_donaciones');
var frm_con_donaciones = $('#frm_con_donaciones');

$(document).ready(function() {
	
	inicializarDatos();
	 
	frm_con_donaciones.bootstrapValidator({
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
	
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();
		
		loadding(true);					
		var url = VAR_CONTEXT + '/donaciones/registro-donaciones/mantenimientoDonaciones/0';
		
	
		$(location).attr('href', url);
		
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
	
		var bootstrapValidator = frm_con_donaciones.data('bootstrapValidator');
	
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoDdi : $('#sel_ddi').val(),
				codigoEstado : $('#sel_estado').val()
			};
			
			loadding(true);
		
			consultarAjax('GET', '/donaciones/registro-donaciones/listarDonaciones', params, function(respuesta) {
			
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarControlCalidad(respuesta);
				}
				loadding(false);
			});
			
		}
		
	});
	
	
});

function inicializarDatos() {
	
  	$('#li_donaciones').addClass('active');
	$('#ul_donaciones').css('display', 'block');
	$('#li_reg_donaciones').attr('class', 'active');
	$('#li_reg_donaciones').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {


		
		
	}
}

function listarControlCalidad(respuesta) {

	tbl_mnt_con_donaciones.dataTable().fnDestroy();
	
	tbl_mnt_con_donaciones.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDonacion',
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
			data : 'codigoDdi'
		}, {
			data : 'codigoDonacion'
		}, {
			data : 'fechaEmision'
		}, {
			data : 'nombrePais'
		}, {
			data : 'nombreDonante'
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
	
	listaDonacionesCache = respuesta;

}

