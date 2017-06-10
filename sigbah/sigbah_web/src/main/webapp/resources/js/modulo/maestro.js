$(document).ready(function() {

		$('#frm_elements').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			txt_field : {
				validators : {
					notEmpty : {
						message : 'The first name is required'
					}
				}
			},
			txt_pas_field : {
				validators : {
					notEmpty : {
						message : 'The last name is required'
					}
				}
			},
			txt_area : {
				validators : {
					notEmpty : {
						message : 'The company name is required'
					}
				}
			}
		}
	});
	
	$('#btn_submit').click(function() {
		
		var bootstrapValidator = $('#frm_elements').data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = new Object();
			
			loadding(true);
			
			consultarAjaxSincrono('GET', '/maestro/listarMaestros', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarComercial(respuesta);
				}
				loadding(false);
			});

		}
		
	});
	
});

function listarComercial(respuesta) {

	var tbl_mnt_comercial = $('#tbl_mnt_comercial');

	tbl_mnt_comercial.dataTable().fnDestroy();
	
	tbl_mnt_comercial.dataTable({
		data : respuesta,
		columns : [
				{	
						data : 'idubigeo'
				}, {	
						data : 'coddpto'
				}, {
						data : 'nombre'
				}
//						}, {
//								data : 'id_seguimiento',
//								sClass : 'opc-center ',
//								render: function(data, type, row) {
//									if (ind_comercial == '1' && row.estado != '1') { // Anulado
//										var btn_editar = '';
//										if (row.estado != '5') { // Entregado Cliente
//											btn_editar = '<button type="button" class="btn btn-success btn-xs btn_edi_reg_comercial" title="Editar Solicitud">'+
//															'<span class="glyphicon glyphicon-pencil"></span>'+
//														 '</button>';
//										
//										}
//										var btn_anular = ' ';	 
//										if (esnulo(row.cod_ord_pedido)) {
//											btn_anular += '<button type="button" class="btn btn-danger btn-xs btn_anu_reg_comercial" title="Anular Solicitud">'+
//															'<span class="glyphicon glyphicon-remove"></span>'+
//														  '</button>';
//										}
//										return btn_editar + btn_anular;
//									} else {
//										return '';
//									}											
//								}
//						}
		],
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
	
//			tbl_mnt_comercial.$('tr').click( function () {
//				solicitud = tbl_mnt_comercial.fnGetData(this);
//			});

	tbl_mnt_comercial.resize();
}



