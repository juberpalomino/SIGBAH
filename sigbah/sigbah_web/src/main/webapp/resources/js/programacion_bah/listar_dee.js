var listaDeeCache = new Object();
var tbl_mnt_dee = $('#tbl_mnt_dee');
var frm_dee = $('#frm_dee');

$(document).ready(function() {

	inicializarDatos();
	
	$('#btn_aceptar').click(function(e) {
		e.preventDefault();
		cargarDee(true);
	});
	

	$('#href_exp_excel').click(function(e) {
		e.preventDefault();
		var row = $('#tbl_mnt_dee > tbody > tr').length;
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
		var codAnio = $('#sel_anio').val();
		var codMes = $('#sel_mes').val();
		var idEstado = $('#sel_estado').val();
		
		
		var url = VAR_CONTEXT + '/programacion-bath/decreto/exportarExcel/';
		url += verificaParametro(codAnio) + '/';
		url += verificaParametro(codMes) + '/';
		url += verificaParametro(idEstado);
		
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
	
	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/decreto/mantenimientoDee/0';
		$(location).attr('href', url);
		
	});
	
	
	$('#href_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		var codigo = '';
		tbl_mnt_dee.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_dee.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				// Verificamos que tiene mas de un registro marcado y salimos del bucle
				if (!esnulo(codigo)) {
					return false;
				}
				var idDee = listaDeeCache[index].idDee;
				codigo = codigo + idDee + '_';
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
			var url = VAR_CONTEXT + '/programacion-bath/decreto/mantenimientoDee/';
			$(location).attr('href', url + codigo);
		}
		
	});
	
	tbl_mnt_dee.on('click', '.btn_exp_doc', function(e) {//whr 
		e.preventDefault();
		
		var id = $(this).attr('id');
		var name = $(this).attr('name');
		if (!esnulo(id) && !esnulo(name)) {
			descargarDocumento(id, name);
		} else {
			addInfoMessage(null, 'No dispone de documento adjunto asociado.');
		}
		
	});
	
});

function inicializarDatos() {
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_dec_emer').attr('class', 'active');
	$('#li_dec_emer').closest('li').children('a').attr('href', '#');
	
	if (indicador == '1') { // Retorno
		cargarDee(true);
	} else {
		cargarDee(new Object());
	}
	
}


function descargarDocumento(codigo, nombre) {	//whr
	loadding(true);
	var url = VAR_CONTEXT + '/common/archivo/exportarArchivo/'+codigo+'/'+nombre+'/';	
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
}

function cargarDee(indicador) {
	var params = { 
			codAnio : $('#sel_anio').val(),
			codMes : $('#sel_mes').val(),
			idEstado : $('#sel_estado').val()
		};
	
		if (indicador) {
			loadding(true);
		}
		
		loadding(true);
		
		consultarAjax('GET', '/programacion-bath/decreto/listarDee', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarDee(respuesta);
			}
			loadding(false);
		});
}

function listarDee(respuesta) {

	tbl_mnt_dee.dataTable().fnDestroy();
	
	
	tbl_mnt_dee.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDee',
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
			data : 'idDee',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'codAnio'
		}, {
			data : 'nomMes'
		}, {
			data : 'numDee',
			render: function(data, type, row) { 
				if (data != null) {
					return '<button type="button" id="'+row.codigoArchivoAlfresco+'" name="'+row.nombreArchivo+'"'+ 
						   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
				} else {
					return '';	
				}											
			}
		}, {
			data : 'fechaIni'
		}, {
			data : 'fechaFin'
		}, {
			data : 'numDias'
		} , {
			data : 'nomDee'
		}, {
			data : 'nomEstado'
		}],
			
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
			{ width : '15%', targets : 3 },
			{ width : '15%', targets : 4 },
			{ width : '15%', targets : 5 },
			{ width : '18%', targets : 6 }
		]
	});
	
	listaDeeCache = respuesta;

}
