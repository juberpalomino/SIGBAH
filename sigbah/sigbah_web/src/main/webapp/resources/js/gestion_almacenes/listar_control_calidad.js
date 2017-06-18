var listaControlCalidadCache = new Object();

$(document).ready(function() {
	
	$('#li_ges_almacenes').addClass('active');
	$('#ul_ges_almacenes').css('display', 'block');
	$('#li_con_calidad').addClass('active');
	
	inicializarDatos();
	
	$('#btn_buscar').click(function() {

		var params = new Object();
		
		loadding(true);
		
		consultarAjaxSincrono('GET', '/gestion-almacenes/control-calidad/listarControlCalidad', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarControlCalidad(respuesta);
			}
			loadding(false);
		});
		
	});
	
	$('#href_edi_con_calidad').click(function() {

		var indices = [];
		var codigo = ''
		var tbl_mnt_con_calidad = $('#tbl_mnt_con_calidad').DataTable();
		tbl_mnt_con_calidad.rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_con_calidad.rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
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
			
		}
		
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

