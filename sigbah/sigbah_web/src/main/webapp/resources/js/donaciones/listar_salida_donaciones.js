var listaControlCalidadCache = new Object();

var frm_don_salida = $('#frm_don_salida');
$(document).ready(function() {
	
	inicializarDatos();
	 
	$('#frm_don_salida').bootstrapValidator({
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
		var url = VAR_CONTEXT + '/donaciones/registro-donaciones/registroOrdenSalida';
		
	
		$(location).attr('href', url);
		
	});
	
	$('#btn_buscar').click(function(e) {
		e.preventDefault();
	
		var bootstrapValidator = frm_don_salida.data('bootstrapValidator');
	
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {

			var params = { 
				codigoAnio : $('#sel_anio').val(),
				codigoDdi : $('#sel_ddi').val(),
				codigoEstado : $('#sel_estado').val()
			};
			
			loadding(true);
		
			consultarAjax('GET', '/donaciones/registro-donaciones/listarSalidaDonaciones', params, function(respuesta) {
			
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
	$('#ul_reg_donaciones_ingresos').css('display', 'block');	
	$('#li_reg_orden_ingresos').attr('class', 'active');
	$('#li_reg_orden_ingresos').closest('li').children('a').attr('href', '#');
	
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

