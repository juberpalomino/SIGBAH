var listaDamnificadosCache = new Object();
var listaEmergenciasActivosCache = new Object();
var listaUbigeosIneiCache = new Object();
var listaDetalleRequerimientoCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');
var tbl_det_afectados = $('#tbl_det_afectados'); 


var frm_det_prog_ubigeo = $('#frm_det_alimentarios');
var tbl_mnt_emer_act = $('#tbl_mnt_emer_act');

var tbl_mnt_ubigeo_inei = $('#tbl_mnt_ubigeo_inei');

var frm_afecta_damni = $('#frm_afecta_damni');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('#sel_departamento_emer').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			cargarProvincia(codigo, null);
		} else {
			$('#sel_provincia_emer').html('');
		}
	});
	$('#sel_departamento_ubi').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {						
			cargarProvinciaUbi(codigo, null);
		} else {
			$('#sel_provincia_ubi').html('');
		}
	});

	
	$('#btn_grabar_dat_gen').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_requerimiento').val();
			
			var numReq = null;
				var numReqConcat = $('#txt_num_requerimiento').val();
				if (!esnulo(numReqConcat)) {
					var arr = numReqConcat.split('-'); 
					numReq = arr[0]; 
				}
			
			
			var params = {
					idRequerimiento:codigo,
				codAnio : $('#txt_anio').val(),
				codMes : requerimiento.codMes,
				fkIdeDdi : requerimiento.fkIdeDdi,
				idDdi : requerimiento.idDdi, 
				fkIdeRegion : $('#sel_region').val(),
				codRequerimiento : requerimiento.codRequerimiento,
				nomRequerimiento :$('#txt_descripcion').val(),
				fechaRequerimiento : $('#txt_fecha_requerimiento').val(),
				flgSinpad : $('input[name="rb_req_sinpad"]:checked').val(),
				fkIdeFenomeno :  $('#sel_fenomeno').val(),
				observacion : $('#txt_observaciones').val()
						
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/requerimiento/grabarRequerimiento', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else { 
						
						addSuccessMessage(null, 'Se genero el N° Requerimiento '+$('#txt_num_requerimiento').val());
						requerimiento.idRequerimiento=respuesta.idRequerimiento;
						$('#li_damnificados').attr('class', '');
						$('#li_damnificados').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#txt_nro_req').val(requerimiento.numRequerimiento); 
						$('#txt_des_req').val($('#txt_descripcion').val());
						if($('input:radio[name=rb_req_sinpad]:checked').val()==2){ 
							$('#btn_agregar_emergencia').attr("disabled", true);
						}else{
							$('#btn_agregar_emergencia').attr("disabled", false);
						}
						
						$('#hid_cod_requerimiento').val(respuesta.idRequerimiento); 
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('#btn_aceptar_emer').click(function(e) {
		e.preventDefault();
		var params = { 
				codAnio : $('#sel_anio_emer').val(),
				codMes : $('#sel_mes_emer').val(),
				codDpto: $('#sel_departamento_emer').val(),
				codProvincia : $('#sel_provincia_emer').val(),
				idFenomeno : $('#sel_fenomeno_emer').val()
			};
			
			loadding(true);
			
			consultarAjax('GET', '/programacion-bath/requerimiento/listarEmergenciasActivas', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarEmergenciasActivas(respuesta);
				}
				loadding(false);
			});
	});
	
	$('#href_emer_acti_exp_excel').click(function(e) {
		e.preventDefault();
		var row = $('#tbl_mnt_emer_act > tbody > tr').length;
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
		
		var codAnio = $('#sel_anio_emer').val();
		var codMes = $('#sel_mes_emer').val();
		var codDpto = $('#sel_departamento_emer').val();
		var codProvincia = $('#sel_provincia_emer').val();
		var idFenomeno = $('#sel_fenomeno_emer').val();
		
		var url = VAR_CONTEXT + '/programacion-bath/requerimiento/exportarExcelEmergenciasActivas/';
		url += verificaParametro(codAnio) + '/';
		url += verificaParametro(codMes) + '/';
		url += verificaParametro(codDpto) + '/';
		url += verificaParametro(codProvincia) + '/';
		url += verificaParametro(idFenomeno);
		
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
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/requerimiento/inicio/1';
		$(location).attr('href', url);
		
	});
	
	
	$('#btn_agregar_emergencia').click(function(e) {
		e.preventDefault(); 
		$('#div_det_prog_emerg').modal('show');
		
	});
	
	$('#btn_pasar_distrito').click(function(e) {
		e.preventDefault();
		var indices = [];
		
		tbl_mnt_emer_act.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_emer_act.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			loadding(true);
			var mensaje="";
			for (var i = 0; i < indices.length; i++) {
				var params = { 
						fkIdRequerimiento : $('#hid_cod_requerimiento').val(), 
						idEmergencia : listaEmergenciasActivosCache[indices[i]].idEmergencia,
						codDistrito : listaEmergenciasActivosCache[indices[i]].codDistrito,
						famAfectado : listaEmergenciasActivosCache[indices[i]].famAfectado,
						famDamnificado : listaEmergenciasActivosCache[indices[i]].famDamnificado,
						persoAfectado :listaEmergenciasActivosCache[indices[i]].persoAfectado,
						persoDamnificado : listaEmergenciasActivosCache[indices[i]].persoDamnificado
//						famAfectadoReal : listaEmergenciasActivosCache[index].codAnio,
//						famDamnificadoReal : listaEmergenciasActivosCache[index].codAnio,
//						persoAfectadoReal : listaEmergenciasActivosCache[index].codAnio,
//						persoDamnificadoReal : listaEmergenciasActivosCache[index].codAnio
					};
				
				consultarAjax('GET', '/programacion-bath/requerimiento/pasarDistritos', params, function(respuesta) {
					if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
						addErrorMessage(null, respuesta.mensajeRespuesta);
					} else {
						mensaje=	respuesta.mensajeRespuesta;	
						
						cargarRequerimientoDetalle(true);		
					}
					loadding(false);
				});
			}
			if(mensaje){//muestra solo 1 vez el msj
				addSuccessMessage(null, mensaje);	
			}
		}
});
	
	$('#btn_pasar_distrito_ubigeo').click(function(e) {
		e.preventDefault();
		var indices = [];
		
		tbl_mnt_ubigeo_inei.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_ubigeo_inei.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);				
				
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			loadding(true);
			var mensaje="";
			for (var i = 0; i < indices.length; i++) {
				var params = { 
						fkIdRequerimiento :$('#hid_cod_requerimiento').val(), 
						codDistrito : listaUbigeosIneiCache[indices[i]].coddist, 
						poblacionINEI : listaUbigeosIneiCache[indices[i]].poblacionInei
			
					};
				
				consultarAjax('GET', '/programacion-bath/requerimiento/pasarDistritosUbigeo', params, function(respuesta) {
					if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
						addErrorMessage(null, respuesta.mensajeRespuesta);
					} else {
						mensaje=	respuesta.mensajeRespuesta;	
						cargarRequerimientoDetalle(true);		
					}
					loadding(false);
				});
			}
//			if(mensaje){
				addSuccessMessage(null, mensaje);	
//			}
			
		}
});	
		
		
tbl_mnt_emer_act.on('click', '.checkbox', function(e) {//Contador
	var indices = [];
	tbl_mnt_emer_act.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
		if (tbl_mnt_emer_act.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
			indices.push(index);
		}
	});
	
	$('#txt_nro_selec').val(indices.length);
});

tbl_mnt_ubigeo_inei.on('click', '.checkbox', function(e) {//Contador
	var indices = [];
	tbl_mnt_ubigeo_inei.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
		if (tbl_mnt_ubigeo_inei.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
			indices.push(index);
		}
	});
	
	$('#txt_nro_selec_ubi').val(indices.length);
});

$('#btn_agregar_ubigeo').click(function(e) {
	e.preventDefault();
	$('#div_det_prog_ubigeo').modal('show');
	
});

$('#btn_aceptar_ubigeo').click(function(e) { 
	e.preventDefault();
	var params = { 
			codAnio : $('#sel_anio_ubi').val(),
			coddpto: $('#sel_departamento_ubi').val(),
			codprov : $('#sel_provincia_ubi').val()
		};
		
		loadding(true);
		
		consultarAjax('GET', '/programacion-bath/requerimiento/listarUbigeoInei', params, function(respuesta) {
			if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
				addErrorMessage(null, respuesta.mensajeRespuesta);
			} else {
				listarUbigeoInei(respuesta);
			}
			loadding(false);
		});
});


		$('#btn_pro_editar').click(function(e) {
			e.preventDefault();
		
			var indices = [];
			tbl_det_afectados.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
				if (tbl_det_afectados.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
					indices.push(index);
				}
			});
			
			if (indices.length == 0) {
				addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
			} else if (indices.length > 1) {
				addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
			} else {
				
				var obj = listaDetalleRequerimientoCache[indices[0]];
				
				$('#h4_tit_afectados').html('Actualizar Afectados y Damnificados');
				
				frm_afecta_damni.trigger('reset');
				
				$('#hid_cod_dam_afec').val(obj.fkIdRequerimientoDamni);
				
				$('#txt_fam_afec').val(obj.famAfectadoReal);
				$('#txt_fam_dam').val(obj.famDamnificadoReal);
				$('#txt_per_afec').val(obj.persoAfectadoReal);
				$('#txt_per_dam').val(obj.persoDamnificadoReal);
				
				$('#div_mod_actualiza_emer').modal('show');
			}
			
		});

		
		$('#btn_can_actualiza_emer').click(function(e) {
			e.preventDefault();
			frm_afecta_damni.data('bootstrapValidator').resetForm();
		});

		$('#btn_gra_actualiza_emer').click(function(e) {
			e.preventDefault();
			
			var bootstrapValidator = frm_afecta_damni.data('bootstrapValidator');
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				
				var params = { 
						//			idEmergencia : $('#hid_cod_dam_afec').val(),
									fkIdRequerimiento : $('#hid_cod_dam_afec').val(), 
									famAfectadoReal : $('#txt_fam_afec').val(),
									famDamnificadoReal : $('#txt_fam_dam').val(),
									persoAfectadoReal : $('#txt_per_afec').val(),
									persoDamnificadoReal :  $('#txt_per_dam').val()
									
								};
				
				loadding(true);
				
				consultarAjax('POST', '/programacion-bath/requerimiento/actualizarDamnificados', params, function(respuesta) {
					if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
						addErrorMessage(null, respuesta.mensajeRespuesta);
					} else {
							cargarRequerimientoDetalle(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);	
					}
					loadding(false);
					$('#div_mod_actualiza_emer').modal('hide');
				});			
			}
			
		});
		
	
});

function inicializarDatos() {
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_req_edan').attr('class', 'active');
	$('#li_req_edan').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		
		$('#txt_num_requerimiento').val(requerimiento.numRequerimiento);
		$('#txt_anio').val(requerimiento.codAnio);
		$('#txt_fecha_requerimiento').val(requerimiento.fechaRequerimiento);
		
		if (!esnulo(requerimiento.idRequerimiento)) {//editar
			
			$('#li_damnificados').attr('class', '');
			$('#li_damnificados').closest('li').children('a').attr('data-toggle', 'tab');
			
			$('#hid_cod_requerimiento').val(pedido.idRequerimiento);//usamos paa el listado de detalle requerimientos
			

			$('#txt_descripcion').val(requerimiento.nomRequerimiento);
			$('#txt_observaciones').val(requerimiento.observacion);
			
//			listarProductoPedidoCompra(false);
//			listarDocumentoPedidoCompra(false);
			
		} else {//nuevo
			
			$('#li_damnificados').addClass('disabled'); //whr descomentar de abajo para activar bloqueo de tab
			$('#li_damnificados').closest('li').children('a').removeAttr('data-toggle'); 
			
			$('#txt_descripcion').val(requerimiento.nomRequerimiento);
			$('#txt_observaciones').val(requerimiento.observacion);

			 $('input[name=rb_req_sinpad][value="'+requerimiento.flgSinpad+'"]').prop('checked', true);
			if (requerimiento.flgSinpad == '1') {
				$('#btn_agregar_emergencia').attr("disabled", false);
			} else {
				$('#btn_agregar_emergencia').attr("disabled", true);
			}
			$('#sel_fenomeno').val(requerimiento.fkIdeFenomeno);
			$('#sel_region').val(requerimiento.fkIdeRegion);
			
			$('#txt_nro_req').val(requerimiento.numRequerimiento);
			$('#txt_des_req').val(requerimiento.nomRequerimiento); 
			
			listarDetalleRequerimiento(lista_requerimiento);

		}
		
	}
	
}

function cargarRequerimientoDetalle(indicador) {
	var params = { 
			idRequerimiento : $('#hid_cod_requerimiento').val() ,
			codAnio :  $('#txt_anio').val()
	};			
	consultarAjaxSincrono('GET', '/programacion-bath/requerimiento/listarRequerimientoDetalle', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleRequerimiento(respuesta);
		}
	});
}

function cargarProvincia(codigo, codigoProvincia) {
	var params = { 
		coddpto : codigo
	};			
	loadding(true);
	consultarAjax('GET', '/programacion-bath/requerimiento/listarProvincia', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
			$.each(respuesta, function(i, item) {
				options += '<option value="'+item.codprov+'">'+item.nombre+'</option>';
			});
			$('#sel_provincia_emer').html(options);
			if (codigoProvincia != null) {
				$('#sel_provincia_emer').val(codigoProvincia);       	
			}
		}
		loadding(false);
	});
}

function cargarProvinciaUbi(codigo, codigoProvincia) {
	var params = { 
		coddpto : codigo
	};			
	loadding(true);
	consultarAjax('GET', '/programacion-bath/requerimiento/listarProvincia', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
			$.each(respuesta, function(i, item) {
				options += '<option value="'+item.codprov+'">'+item.nombre+'</option>';
			});
			$('#sel_provincia_ubi').html(options);
			if (codigoProvincia != null) {
				$('#sel_provincia_ubi').val(codigoProvincia);       	
			}
		}
		loadding(false);
	});
}

function listarEmergenciasActivas(respuesta) {

	tbl_mnt_emer_act.dataTable().fnDestroy();
	
	tbl_mnt_emer_act.dataTable({
			data : respuesta,
			columns : [ {
				data : 'idEmergencia',
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
				data : 'idEmergencia',
				render : function(data, type, full, meta) {
					var row = meta.row + 1;
					return row;											
				}
			}, {
				data : 'codAnio'
			}, {
				data : 'nombreMes'
			}, {
				data : 'fecha'
			}, {
				data : 'idEmergencia'
			}, {
				data : 'descFenomeno'
			}, {
				data : 'nombreEmergencia'
			}, {
				data : 'desDepartamento'
			}, {
				data : 'desProvincia'
			}, {
				data : 'desDistrito'
			}, {
//				data : 'poblacionInei'
//			}, {
				data : 'famAfectado'
			}, {
				data : 'famDamnificado'
			}, {
				data : 'totalFam'
			}, {
				data : 'persoAfectado'
			}, {
				data : 'persoDamnificado'
			}, {
				data : 'totalPerso'
			} ],
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
				{ width : '18%', targets : 7 }
			]
		});
		
	listaEmergenciasActivosCache = respuesta;

	}

function listarUbigeoInei(respuesta) {

	tbl_mnt_ubigeo_inei.dataTable().fnDestroy();
	tbl_mnt_ubigeo_inei.dataTable({
			data : respuesta,
			columns : [ {
					data : 'coddpto',
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
					data : 'coddpto',
					render : function(data, type, full, meta) {
						var row = meta.row + 1;
						return row;											
					}
				}, {data : 'coddist'}, 
				{data : 'desprov'}, 
				{data : 'desdist'}, 
				{data : 'poblacionInei'} 
			],
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
				{ width : '15%', targets : 5 }
			]
		});
		
	listaUbigeosIneiCache = respuesta;

	}



function listarDetalleRequerimiento(respuesta) {

	tbl_det_afectados.dataTable().fnDestroy();
	tbl_det_afectados.dataTable({
			data : respuesta,
			columns : [ {
					data : 'idEmergencia',
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
					data : 'idEmergencia',
					render : function(data, type, full, meta) {
						var row = meta.row + 1;
						return row;											
					}
				}, {data : 'desDepartamento'}, 
				{data : 'desProvincia'}, 
				{data : 'desDistrito'}, 
				{data : 'idEmergencia'},
				{data : 'poblacionINEI'},
				{data : 'famAfectado'},
				{data : 'famDamnificado'},
				{data : 'totalFam'},
				{data : 'persoAfectado'},
				{data : 'persoDamnificado'},
				{data : 'totalPerso'},
				{data : 'famAfectadoReal'},
				{data : 'famDamnificadoReal'},
				{data : 'totalFamReal'},
				{data : 'persoAfectadoReal'}, 
				{data : 'persoDamnificadoReal'}, 
				{data : 'totalPersoReal'}
			],
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
				{ width : '15%', targets : 5 }
			]
		});
		
	listaDetalleRequerimientoCache = respuesta;

	}
