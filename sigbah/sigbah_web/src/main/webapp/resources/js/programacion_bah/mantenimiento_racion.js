var listaProductoCache = new Object();

var frm_dat_generales_racion = $('#frm_dat_generales_racion');
var tbl_mnt_racion_ope = $('#tbl_mnt_racion_ope'); 
 
var frm_det_productos = $('#frm_det_productos');


$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();

	$('#btn_grabar_racion').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales_racion.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_raciones').val();
			var params = {
				tipoRacion : $('#sel_tipo_racion').val(),
				codRacion : $('#txt_cod_racion').val(),
				nombreRacion : $('#txt_nom_racion').val(),  
				diasAtencion : $('#txt_num_dias').val(),
				fechaRacion : $('#txt_fecha').val() 
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/racion/grabarRacion', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else { 
						
						addSuccessMessage(null, 'Se genero la racion el N°  '+$('#txt_cod_racion').val());   
						
						$('#div_tabla_prod').show();
						
//						$('#li_damnificados').attr('class', '');
//						$('#li_damnificados').closest('li').children('a').attr('data-toggle', 'tab');
//						
//						$('#txt_nro_req').val(requerimiento.numRequerimiento); 
//						$('#txt_des_req').val($('#txt_descripcion').val());
//						if($('input:radio[name=rb_req_sinpad]:checked').val()==2){ 
//							$('#btn_agregar_emergencia').attr("disabled", true);
//						}else{
//							$('#btn_agregar_emergencia').attr("disabled", false);
//						}
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});


	
	$('#btn_gra_prod').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_productos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_producto').val();
			var params = {
					fkIdProducto : $('#sel_producto').val(),
					pesoUnitarioPres : formatMonto($('#txt_uni_pres').val()),
					cantRacionKg : formatMonto($('#txt_gr_aprox').val())
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/racion/grabarProducto', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else { 
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
//						llamar para listado de grilla
						
//						$('#li_damnificados').attr('class', '');
//						$('#li_damnificados').closest('li').children('a').attr('data-toggle', 'tab');
//						
//						$('#txt_nro_req').val(requerimiento.numRequerimiento); 
//						$('#txt_des_req').val($('#txt_descripcion').val());
//						if($('input:radio[name=rb_req_sinpad]:checked').val()==2){ 
//							$('#btn_agregar_emergencia').attr("disabled", true);
//						}else{
//							$('#btn_agregar_emergencia').attr("disabled", false);
//						}
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	

	
//	$('.btn_retornar').click(function(e) {
//		e.preventDefault();
//
//		loadding(true);					
//		var url = VAR_CONTEXT + '/programacion-bath/requerimiento/inicio/1';
//		$(location).attr('href', url);
//		
//	});
	

	$('#href_nuevo_prod').click(function(e) {
		e.preventDefault();

		$('#h4_tit_productos').html('Productos');
		frm_det_productos.trigger('reset');
		
//		$('#sel_producto').select2().trigger('change');
//		$('#sel_producto').select2({
//			  dropdownParent: $('#div_pro_det_alimentarios')
//		});
//		
//		$('#hid_cod_producto').val('');
		$('#div_det_productos').modal('show');
		
	});
//	
//	$('#href_ali_editar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//			}
//		});
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else if (indices.length > 1) {
//			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
//		} else {
//			
//			var obj = listaAlimentariosCache[indices[0]];
//			
//			$('#h4_tit_alimentarios').html('Actualizar Producto');
//			frm_det_alimentarios.trigger('reset');
//			
//			$('#hid_cod_producto').val(obj.idDetalleControlCalidad);
//			
//			$('#sel_producto').val(obj.idProducto+'_'+obj.nombreUnidad);
//
//			$('#sel_producto').select2().trigger('change');
//			$('#sel_producto').select2({
//				  dropdownParent: $('#div_pro_det_alimentarios')
//			});
//			
//			$('#sel_uni_medida').val(obj.nombreUnidad);
//			$('#txt_fec_vencimiento').val(obj.fechaVencimiento);
//			$('#txt_can_lote').val(obj.cantidadLote);
//			$('#txt_can_muestra').val(obj.cantidadMuestra);
//			$('#sel_primario').val(obj.primario);
//			$('#sel_olor').val(obj.parOlor);
//			$('#sel_textura').val(obj.parTextura);
//			$('#sel_secundario').val(obj.secundario);
//			$('#sel_color').val(obj.parColor);
//			$('#sel_sabor').val(obj.parSabor);
//			
//			$('#div_det_alimentarios').modal('show');
//		}
//		
//	});
//	
//	$('#href_ali_eliminar').click(function(e) {
//		e.preventDefault();
//
//		var indices = [];
//		var codigo = ''
//		tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
//			if (tbl_det_alimentarios.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
//				indices.push(index);
//				var idDetalleControlCalidad = listaAlimentariosCache[index].idDetalleControlCalidad;
//				codigo = codigo + idDetalleControlCalidad + '_';
//			}
//		});
//		
//		if (!esnulo(codigo)) {
//			codigo = codigo.substring(0, codigo.length - 1);
//		}
//		
//		if (indices.length == 0) {
//			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
//		} else {
//			var msg = '';
//			if (indices.length > 1) {
//				msg = 'Está seguro de eliminar los siguientes registros ?';
//			} else {
//				msg = 'Está seguro de eliminar el registro ?';
//			}
//			
//			$.SmartMessageBox({
//				title : msg,
//				content : '',
//				buttons : '[Cancelar][Aceptar]'
//			}, function(ButtonPressed) {
//				if (ButtonPressed === 'Aceptar') {
//	
//					loadding(true);
//					
//					var params = { 
//						arrIdDetalleControlCalidad : codigo
//					};
//			
//					consultarAjax('POST', '/gestion-almacenes/control-calidad/eliminarProductoControlCalidad', params, function(respuesta) {
//						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
//							loadding(false);
//							addErrorMessage(null, respuesta.mensajeRespuesta);
//						} else {
//							listarProductoControlCalidad(true);
//							addSuccessMessage(null, respuesta.mensajeRespuesta);							
//						}
//					});
//					
//				}	
//			});
//			
//		}
//		
//	});





//	
//	tbl_det_documentos.on('click', '.btn_exp_doc', function(e) {
//		e.preventDefault();
//		
//		var id = $(this).attr('id');
//		var name = $(this).attr('name');
//		if (!esnulo(id) && !esnulo(name)) {
//			descargarDocumento(id, name);
//		} else {
//			addInfoMessage(null, 'No dispone de documento adjunto asociado.');
//		}
//		
//	});
	
});

function inicializarDatos() {
	
	$('#div_tabla_prod').hide();//ocultamos la tabla

	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		//inicializar los valores
		$('#txt_cod_racion').val(racion.codRacion);
		$('#txt_fecha').val(racion.fechaRacion);
		
		
		if (!esnulo(racion.codRacion)) {
			
//			$('#li_damnificados').attr('class', '');
//			$('#li_damnificados').closest('li').children('a').attr('data-toggle', 'tab');
//			
//			$('#txt_descripcion').val(requerimiento.nomRequerimiento);
//			$('#txt_observaciones').val(requerimiento.observacion);
//
//			 $('input[name=rb_req_sinpad][value="'+requerimiento.flgSinpad+'"]').prop('checked', true);
//			if (requerimiento.flgSinpad == '1') {
//				$('#btn_agregar_emergencia').attr("disabled", false);
//			} else {
//				$('#btn_agregar_emergencia').attr("disabled", true);
//			}
//			$('#sel_fenomeno').val(requerimiento.fkIdeFenomeno);
//			$('#sel_region').val(requerimiento.fkIdeRegion);
//			
//			$('#txt_nro_req').val(requerimiento.numRequerimiento);
//			$('#txt_des_req').val(requerimiento.nomRequerimiento); 
//			
//			listarDetalleRequerimiento(lista_requerimiento);
			
		} else {
			

		}
	}
	
}







function listarDetalleRequerimiento(respuesta) {

	tbl_mnt_racion_ope.dataTable().fnDestroy();
	tbl_mnt_racion_ope.dataTable({
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
				{ width : '15%', targets : 5 }
			]
		});
		
	listaProductoCache = respuesta;

	}
