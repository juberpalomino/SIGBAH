var listaControlCalidadCache = new Object();

$(document).ready(function() {
	
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
	
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();
		
		loadding(true);					
		var url = VAR_CONTEXT + '/donaciones/registro-donaciones/mantenimientoDonaciones/0';
		
	
		$(location).attr('href', url);
		
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

