var listaProductosCache = new Object();
var listaDocumentosCache = new Object();
var frm_dat_generales = $('#frm_dat_generales');
//var tbl_mnt_racion_ope = $('#tbl_mnt_racion_ope'); 
// 
var frm_productos = $('#frm_productos');
var tbl_mnt_productos = $('#tbl_mnt_productos'); 

var frm_det_documentos = $('#frm_det_documentos');
var tbl_mnt_documentos = $('#tbl_mnt_documentos');  


$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();

	$('#btn_grabar_dat_gen').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			var codigo = $('#hid_cod_ped_compra').val();
			var params = {
					idPedidoCom :codigo,
					codPedido :pedido.codPedido,
					fecPedido : $('#txt_fecha_pedido').val(),
					codEstado : $('#sel_estado').val(),  
					codPedidoPor : $('#sel_pedidoPor').val(),
					descripcion : $('#txt_descripcion').val(),
					dee : $('#sel_dee').val()
				
			};
			
			loadding(true);
			
			consultarAjax('POST', '/programacion-bath/pedido/grabarPedido', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else { 
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						pedido.idPedidoCom=respuesta.idPedidoCom;
						$('#div_tabla_prod').show();//activamos tab productos y documentos

						$('#btn_grabar_dat_gen').attr("disabled", true); //deshabilitamos boton grabar
//						pedido.codPedidoConcate=pedido.codPedidoConcate+": "+$('#txt_descripcion').val();
						$('#hid_cod_ped_compra').val(respuesta.idPedidoCom); //usado para el listado de los detalles
						
						
						
						$('#li_productos').attr('class', ''); //activamos tab producto
						$('#li_productos').closest('li').children('a').attr('data-toggle', 'tab');
						
						$('#li_documentos').attr('class', '');//activamos tab documento
						$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
					}
					
				}
				loadding(false);
			});			
		}
		
	});


	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/pedido/inicio/1';
		$(location).attr('href', url);
		
	});
	

	
	$('#sel_cat_producto').change(function() {
		var idCategoria = $(this).val();		
		if (!esnulo(idCategoria)) {					
			cargarProducto(idCategoria, null, null);
		} else {
			$('#sel_lis_producto').html('');
//			frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_producto');
		}
	});




	$('#sel_lis_producto').change(function() {
		var codigo = $(this).val();	
		console.log(codigo);
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_unidad_med').val(arr[1]);
			} else {
				$('#txt_unidad_med').val('');
			}			
		} else {
			$('#txt_unidad_med').val('');
		}
	});
	
	$('#href_prod_nuevo').click(function(e) {
		e.preventDefault();

		frm_productos.trigger('reset');
		limpiarFormularioProducto();
		$('#sel_lis_producto').html('');
		if ($('#sel_lis_producto').hasClass('select2-hidden-accessible')) {
			$('#sel_lis_producto').select2('destroy');
		}
		
		$('#txt_desc_pedido').val(pedido.codPedidoConcate+": "+$('#txt_descripcion').val());
		$('#hid_cod_producto').val('');//para nuevo
		$('#div_det_productos').modal('show');
		
	});
	
	$('#href_doc_nuevo').click(function(e) {
		e.preventDefault();
		frm_det_documentos.trigger('reset');
		$('#txt_desc_pedido_doc').val(pedido.codPedidoConcate+ ": "+ $('#txt_descripcion').val());
		$('#txt_fecha_doc').val(pedido.fecPedido);
		$('#hid_cod_documento').val('');
		$('#fil_sub_archivo').val(null);
		$('#div_det_docu').modal('show');
	});

	
	$('#txt_cantidad').change(function() {
		var cantidad = $(this).val();
		var pre_unitario = $('#txt_pre_unitario').val();
		if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
			var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
			$('#txt_imp_total').val(formatMontoAll(imp_total));
		}
	});
	
	$('#txt_pre_unitario').change(function() {
		var cantidad = $('#txt_cantidad').val();
		var pre_unitario = $(this).val();
		if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
			var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
			$('#txt_imp_total').val(formatMontoAll(imp_total));
		}
	});
	
	
	$('#fil_sub_archivo').change(function(e) {
		e.preventDefault();
	    var file_name = $(this).val();
	    var file_read = file_name.split('\\').pop();
	    $('#txt_lee_sub_archivo').val($.trim(file_read).replace(/ /g, '_'));
	    if (esnulo($('#hid_cod_documento').val())) {
	    	$('#hid_cod_ind_alfresco').val('1'); // Nuevo registro
	    } else {
	    	$('#hid_cod_ind_alfresco').val('2'); // Registro modificado
	    }
	    frm_det_documentos.bootstrapValidator('revalidateField', 'txt_lee_sub_archivo');	    
	});
	
	$('#href_eli_archivo').click(function(e) {
		e.preventDefault();

		$('#hid_cod_act_alfresco').val('');
		$('#hid_cod_ind_alfresco').val('');
		$('#fil_sub_archivo').val(null);
		$('#txt_lee_sub_archivo').val('');
		
	});
	
	
	$('#btn_grabar_prod').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_productos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			loadding(true);
			var idProducto = null;
			var val_producto = $('#sel_lis_producto').val();
			if (!esnulo(val_producto)) {
				var arr = val_producto.split('_');
				idProducto = arr[0];
			}
			var params = {  
					idDetallePedidoCompra : $('#hid_cod_producto').val(),
					idProducto :idProducto, 
					idPedidoCompra : pedido.idPedidoCom,
					unidadMedida : $('#txt_unidad_med').val(),
					cantidad : $('#txt_cantidad').val(),
					precioUnitario : formatMonto($('#txt_pre_unitario').val()),
					importeTotal : formatMonto($('#txt_imp_total').val())
			};
			
			consultarAjax('POST', '/programacion-bath/pedido/grabarProductoPedidoCompra', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					loadding(false);
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					listarProductoPedidoCompra(true);					
					addSuccessMessage(null, respuesta.mensajeRespuesta);	
					$('#div_det_productos').modal('hide');
				}
				frm_productos.data('bootstrapValidator').resetForm();
				loadding(false);
			});			
		}
	});
	
	$('#btn_gra_documento').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_det_documentos.data('bootstrapValidator');
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {			
			loadding(true);
			
			var params = { 
				idDocumentoPedidoCompra : $('#hid_cod_documento').val(),
				idPedidoCompra : $('#hid_cod_ped_compra').val(),
				idTipoDocumento : $('#sel_tipo_doc').val(),
				nroDocumento : $('#txt_num_doc').val(),
				fechaDocumento : $('#txt_fecha_doc').val(),
				nombreArchivo : $('#txt_lee_sub_archivo').val()
			};
			
			var cod_ind_alfresco = $('#hid_cod_ind_alfresco').val();
			if (cod_ind_alfresco == '1' || cod_ind_alfresco == '2') { // Archivo cargado
				var file_name = $('#fil_sub_archivo').val();
				var file_data = null;
				if (!esnulo(file_name) && typeof file_name !== 'undefined') {
					file_data = $('#fil_sub_archivo').prop('files')[0];
				}
				
				var formData = new FormData();
				formData.append('file_doc', file_data);
				// Carpeta contenedor, ubicado en config.properties
		    	formData.append('uploadDirectory', 'params.alfresco.uploadDirectory.almacen');
		    	
				consultarAjaxFile('POST', '/common/archivo/cargarArchivo', formData, function(resArchivo) {
					if (resArchivo == NOTIFICACION_ERROR) {
						$('#div_det_docu').modal('hide');
						frm_det_documentos.data('bootstrapValidator').resetForm();
						loadding(false);
						addErrorMessage(null, mensajeCargaArchivoError);						
					} else {
						
						params.codigoArchivoAlfresco = resArchivo;

						grabarDetalleDocumento(params);					
					}
				});
				
			} else { // Archivo no cargado
				
				params.codigoArchivoAlfresco = $('#hid_cod_act_alfresco').val();

				grabarDetalleDocumento(params);				
			}
		}
		
	});
	
	$('#href_doc_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = '';
		tbl_mnt_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idDocumentoPedidoCompra = listaDocumentosCache[index].idDocumentoPedidoCompra;
				codigo = codigo + idDocumentoPedidoCompra + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					var params = { 
						arrIdDocumentoPedidoCompra : codigo
					};
					consultarAjax('POST', '/programacion-bath/pedido/eliminarDocumentoPedidoCompra', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarDocumentoPedidoCompra(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);							
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#href_prod_eliminar').click(function(e) {
		e.preventDefault();
		
		var indices = [];
		var codigo = '';
		tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
				var idProductoPedidoCompra = listaProductosCache[index].idDetallePedidoCompra;
				codigo = codigo + idProductoPedidoCompra + '_';
			}
		});
		
		if (!esnulo(codigo)) {
			codigo = codigo.substring(0, codigo.length - 1);
		}
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else {
			var msg = '';
			if (indices.length > 1) {
				msg = 'Está seguro de eliminar los siguientes registros ?';
			} else {
				msg = 'Está seguro de eliminar el registro ?';
			}
			
			$.SmartMessageBox({
				title : msg,
				content : '',
				buttons : '[Cancelar][Aceptar]'
			}, function(ButtonPressed) {
				if (ButtonPressed === 'Aceptar') {
	
					loadding(true);
					var params = { 
						arrIdProductoPedidoCompra : codigo
					};
					consultarAjax('POST', '/programacion-bath/pedido/eliminarProductoPedidoCompra', params, function(respuesta) {
						if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
							loadding(false);
							addErrorMessage(null, respuesta.mensajeRespuesta);
						} else {
							listarProductoPedidoCompra(true);
							addSuccessMessage(null, respuesta.mensajeRespuesta);	
							loadding(false); 
						}
					});
					
				}	
			});
			
		}
		
	});
	
	$('#href_prod_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_productos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaProductosCache[indices[0]];
			
			$('#h4_tit_alimentarios').html('Actualizar Documento');
			limpiarFormularioProducto();
			
			$('#hid_cod_producto').val(obj.idDetallePedidoCompra);			
			$('#sel_lis_producto').val(obj.idProducto);
//			$('#txt_num_doc').val(obj.idPedidoCompra);
			
			$('#txt_unidad_med').val(obj.unidadMedida);
			$('#sel_cat_producto').val(obj.idCategoria);
			
			var val_producto = obj.idProducto+'_'+obj.unidadMedida;
			cargarProducto(obj.idCategoria, val_producto);
			
			$('#txt_cantidad').val(obj.cantidad);
			$('#txt_pre_unitario').val(obj.precioUnitario);
			$('#txt_imp_total').val(obj.importeTotal);
			
			$('#div_det_productos').modal('show');
			
		}
	});
	
	$('#href_doc_editar').click(function(e) {
		e.preventDefault();

		var indices = [];
		tbl_mnt_documentos.DataTable().rows().$('input[type="checkbox"]').each(function(index) {
			if (tbl_mnt_documentos.DataTable().rows().$('input[type="checkbox"]')[index].checked) {
				indices.push(index);
			}
		});
		
		if (indices.length == 0) {
			addWarnMessage(null, 'Debe de Seleccionar por lo menos un Registro');
		} else if (indices.length > 1) {
			addWarnMessage(null, 'Debe de Seleccionar solo un Registro');
		} else {
			
			var obj = listaDocumentosCache[indices[0]];
			
			$('#h4_tit_documentos').html('Actualizar Documento');
			$('#frm_det_documentos').trigger('reset');
			
			$('#hid_cod_documento').val(obj.idDocumentoPedidoCompra);			
			$('#sel_tipo_doc').val(obj.idTipoDocumento);
			$('#txt_num_doc').val(obj.nroDocumento);
			$('#txt_fecha_doc').val(obj.fechaDocumento);
			$('#hid_cod_act_alfresco').val(obj.codigoArchivoAlfresco);
			$('#hid_cod_ind_alfresco').val('');
			$('#txt_lee_sub_archivo').val(obj.nombreArchivo);
			$('#fil_sub_archivo').val(null);
			$('#sel_tipo_doc').val(obj.idTipoDocumento);
			
			$('#div_det_docu').modal('show');
			
		}
	});

	
});

function inicializarDatos() {
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_ped_compra').attr('class', 'active');
	$('#li_ped_compra').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		

		
		if (!esnulo(pedido.idPedidoCom)) {//editar
			$('#hid_cod_ped_compra').val(pedido.idPedidoCom);//usamos paa el listado de detalle productos y documentos
			
			$('#txt_num_pedido').val(pedido.numPedidoCompra);
			$('#txt_fecha_pedido').val(pedido.fecPedido);
			$('#lista_estado').val(pedido.iEstado);
			$('#sel_pedidoPor').val(pedido.tipPedido);
			$('#txt_descripcion').val(pedido.descripcion);
			$('#sel_dee').val(pedido.dee);
			
			listarProductoPedidoCompra(false);
			listarDocumentoPedidoCompra(false);
			
			$('#li_productos').attr('class', '');
			$('#li_productos').closest('li').children('a').attr('data-toggle', 'tab');
			
			$('#li_documentos').attr('class', '');
			$('#li_documentos').closest('li').children('a').attr('data-toggle', 'tab');
		
		} else {//nuevo
			//inicializar los valores
			$('#txt_num_pedido').val(pedido.codPedidoConcate);
			$('#txt_fecha_pedido').val(pedido.fecPedido);
			
			$('#li_productos').addClass('disabled'); //Desactivamos tab producto
			$('#li_productos').closest('li').children('a').removeAttr('data-toggle');
			
			$('#li_documentos').addClass('disabled'); //Desactivamos tab documento
			$('#li_documentos').closest('li').children('a').removeAttr('data-toggle');

		}
	}
	
}

function grabarDetalleDocumento(params) {
	consultarAjax('POST', '/programacion-bath/pedido/grabarDocumentoPedidoCompra', params, function(respuesta) {
		$('#div_det_documentos').modal('hide');
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			loadding(false);			
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDocumentoPedidoCompra(true);
			addSuccessMessage(null, respuesta.mensajeRespuesta);	

			$('#div_det_docu').modal('hide');//cerramos le modal
		}
		frm_det_documentos.data('bootstrapValidator').resetForm();
	});
}

function listarDocumentoPedidoCompra(indicador) {
	var params = { 
			idPedidoCompra : $('#hid_cod_ped_compra').val()
	};			
	consultarAjaxSincrono('GET', '/programacion-bath/pedido/listarDocumentoPedidoCompra', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleDocumentos(respuesta);
			if (indicador) {
				loadding(false);
			}
		}
	});
}

function listarProductoPedidoCompra(indicador) {
	var params = { 
			idDetallePedidoCompra : $('#hid_cod_ped_compra').val() 
	};			
	consultarAjaxSincrono('GET', '/programacion-bath/pedido/listarProductoPedidoCompra', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			listarDetalleProductos(respuesta);
		}
	});
}

function listarDetalleProductos(respuesta) {

	tbl_mnt_productos.dataTable().fnDestroy();
	
	tbl_mnt_productos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDetallePedidoCompra',
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
			data : 'idDetallePedidoCompra',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreProducto'
		}, {
			data : 'unidadMedida'
		}, {
			data : 'cantidad'
		}, {
			data : 'precioUnitario'
		}, {
			data : 'importeTotal'
		}],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : true
	});
	
	listaProductosCache = respuesta;

}
function listarDetalleDocumentos(respuesta) {

	tbl_mnt_documentos.dataTable().fnDestroy();
	
	tbl_mnt_documentos.dataTable({
		data : respuesta,
		columns : [ {
			data : 'idDocumentoPedidoCompra',
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
			data : 'idDocumentoPedidoCompra',
			render : function(data, type, full, meta){
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'nombreDocumento'
		}, {
			data : 'nroDocumento',
			render: function(data, type, row) {
				if (data != null) {
					return '<button type="button" id="'+row.codigoArchivoAlfresco+'" name="'+row.nombreArchivo+'"'+ 
						   'class="btn btn-link input-sm btn_exp_doc">'+data+'</button>';
				} else {
					return '';	
				}											
			}
		}, {
			data : 'fechaDocumento'
		} , {
			data : 'nombreArchivo'
		}],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaDocumentosCache = respuesta;

}

function cargarProducto(idCategoria, codigoProducto) {
	var params = { 
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/programacion-bath/pedido/listarProductoXCategoria', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '<option value="">Seleccione</option>';
	        $.each(respuesta, function(i, item) {
				var det_option = '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'">';
				det_option = det_option + item.nombreProducto+'</option>';				
	            options += det_option;
	        });
	        $('#sel_lis_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_lis_producto').val(codigoProducto);      	
	        } else {
	        	var arr = $('#sel_lis_producto').val().split('_');
				if (arr.length > 1) {
					$('#txt_unidad_med').val(arr[1]);
				} else {
					$('#txt_unidad_med').val('');
				}
//				frm_productos.bootstrapValidator('revalidateField', 'sel_producto');
	        }
	        $('#sel_lis_producto').select2().trigger('change');
			$('#sel_lis_producto').select2({
				  dropdownParent: $('#div_pro_det_productos')
			});
		}
		loadding(false);		
	});
}

function limpiarFormularioProducto() {
	$('#sel_cat_producto').val('');
	$('#sel_producto').val('');
	$('#txt_unidad_med').val('');
	$('#txt_cantidad').val('');
	$('#txt_pre_unitario').val('');
	$('#txt_imp_total').val('');
}