var listaPedidoCompraCache = new Object();
var tbl_mnt_ped_compra = $('#tbl_mnt_ped_compra');
var frm_pedi_compra = $('#frm_pedi_compra');

$(document).ready(function() {

	inicializarDatos();
	
	$('#btn_aceptar').click(function(e) {
		e.preventDefault();

			var params = { 
				codAnio : $('#sel_anio').val(),
				fkIdeDdi : $('#sel_ddi').val(),
				codEstado : $('#sel_estado').val()
			};
			
			
			loadding(true);
			
			consultarAjax('GET', '/programacion-bath/pedido/listarPedidosCompra', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarPedidoCompra(respuesta);
				}
				loadding(false);
			});
		
	});
	

	$('#href_nuevo').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/pedido/mantenimientoPedido/0';
		$(location).attr('href', url);
		
	});
	
//	$('#href_editar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = '';
//		tbl_mnt_ped_compra.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_mnt_ped_compra.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);				
//				// Verificamos que tiene mas de un registro marcado y salimos del bucle
//				if (!esnulo(codigo)) {
//					return false;
//				}
//				var idRequerimiento = listaPedidoCompraCache[index].idRequerimiento;
//				codigo = codigo + idRequerimiento + '_';
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
//			var url = VAR_CONTEXT + '/programacion-bath/racion/mantenimientoRacion/';
//			$(location).attr('href', url + codigo);
//		}
//		
//	});
//	

//	
//	$('#href_exp_copiar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = '';
//		var anio = '';
//		var ddi = '';
//		tbl_mnt_ped_compra.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_mnt_ped_compra.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);				
//				// Verificamos que tiene mas de un registro marcado y salimos del bucle
//				if (!esnulo(codigo)) {
//					return false;
//				}
//				var idRacionOpe = listaPedidoCompraCache[index].idRacionOpe;
//				codigo = codigo + idRacionOpe + '_';
//				anio = listaPedidoCompraCache[index].codAnio;
//				ddi = listaPedidoCompraCache[index].idDdi;
//			}
//		});
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//		}
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			loadding(true);
//			var obj = listaPedidoCompraCache[indices.length-1];
//			var params = { 
//					codAnio : anio,
//					idDdi : ddi,
//					idRacionOpe: codigo	
//				};
//			loadding(true);
//			
//			consultarAjax('POST', '/programacion-bath/racion/copiarRacion', params, function(respuesta) {
//				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//					addErrorMessage(null, respuesta.mensajeRespuesta);
//				} else {
//					addSuccessMessage(null, respuesta.mensajeRespuesta);
//				}
//				loadding(false);
//			});
//		
//			
//		}
//		
//		
//	});
//	
//	$('#href_exp_excel').click(function(e) {
//		e.preventDefault();
//		
//		var row = $('#tbl_mnt_ped_compra > tbody > tr').length;
//		var empty = null;
//		$('tr.odd').each(function() {		
//			empty = $(this).find('.dataTables_empty').text();
//			return false;
//		});					
//		if (!esnulo(empty) || row < 1) {
//			addWarnMessage(null, 'No se encuentran registros para generar el reporte.');
//			return;
//		}
//
//		loadding(true);
//		
//		var codAnio = $('#sel_anio').val();
//		var codMesRacion = $('#sel_mes').val();
//		var tipoRacion = $('#sel_tipo_racion').val();
//		
//		
//		var url = VAR_CONTEXT + '/programacion-bath/racion/exportarExcel/';
//		url += verificaParametro(codAnio) + '/';
//		url += verificaParametro(codMesRacion) + '/';
//		url += verificaParametro(tipoRacion);
//		
//		$.fileDownload(url).done(function(respuesta) {
//			loadding(false);	
//			if (respuesta == NOTIFICACION_ERROR) {
//				addErrorMessage(null, mensajeReporteError);
//			} else {
//				addInfoMessage(null, mensajeReporteExito);
//			}
//		}).fail(function (respuesta) {
//			loadding(false);
//			addErrorMessage(null, mensajeReporteError);
//		});
//
//	});
	
//	$('#href_imprimir').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = '';
//		tbl_mnt_ped_compra.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_mnt_ped_compra.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
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

function listarPedidoCompra(respuesta) {

	tbl_mnt_ped_compra.dataTable().fnDestroy();
	
	
	tbl_mnt_ped_compra.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idPedidoCom',
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
			data : 'idPedidoCom',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'codAnio'
		}, {
			data : 'fkIdeDdi'
		}, {
			data : 'codPedido'
		}, {
			data : 'fecPedido'
		}, {
			data : 'dde'
		}, {
			data : 'tipFenomeno'
		} , {
			data : 'NomEstado'
		}],
	
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
	
	listaPedidoCompraCache = respuesta;

}
