var frm_dat_generales = $('#frm_dat_generales');

$(document).ready(function() {
	
	$("#txt_cod_siga").mask("99.99.9999.9999");
	inicializarDatos();
	
	
	$('#btn_grabar').click(function(e) {
		e.preventDefault();
		
		var bootstrapValidator = frm_dat_generales.data('bootstrapValidator');
		
		bootstrapValidator.validate();
		if (bootstrapValidator.isValid()) {
			
			var params = {
					idProducto : $('#hid_id_catalogo_producto').val(),
					codigoProducto : $('#txt_cod_producto').val(),
					nombreProducto : $('#txt_nombre').val(),
					codigoSiga: $('#txt_cod_siga').val(), 
					idCategoria: $('#sel_categoría').val(), 
					idEnvase : $('#sel_tipo_envase').val(), 
					idUnidadMedida :  $('#sel_unidad_medida').val(), 
					observacion : $('#txt_observaciones').val(), 
					dimLargo : $('#txt_largo').val(), 
					dimAncho : $('#txt_ancho').val(), 
					dimAlto :  $('#txt_alto').val(), 
					pesoNeto :  $('#txt_peso_neto').val(), 
					pesoBruto :  $('#txt_peso_bruto').val(), 
					estado :  $('#sel_estado').val() 

			};
			
			loadding(true);
			
			consultarAjax('POST', '/administracion/catalogo-productos/grabarCatalogoProducto', params, function(respuesta) {
				if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
					addErrorMessage(null, respuesta.mensajeRespuesta);
				} else {
					var codigo=$('#hid_id_catalogo_producto').val();
					if (!esnulo(codigo)) {
						
						addSuccessMessage(null, respuesta.mensajeRespuesta);
						
					} else {
						
						$('#hid_id_catalogo_producto').val(respuesta.idProducto);

						addSuccessMessage(null, 'Se genero el Producto: '+respuesta.idProducto);
						
					}
					
				}
				loadding(false);
			});			
		}
		
	});
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/administracion/catalogo-productos/inicio';
		$(location).attr('href', url);
		
	});
	
	
	$('#txt_cantidad').change(function() {	
		var cantidad =  $(this).val();
		var pre_unitario = $('#txt_precio').val();
		
		if(cantidad!='0'){
			if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
				var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
				$('#txt_imp_total').val(formatMontoAll(imp_total));
			} else {
				$('#txt_imp_total').val('');
			}
		}else{
			addWarnMessage(null, 'La cantidad no debe ser 0.');
	    	$('#txt_cantidad').val('');
	    	$('#'+$(this).attr('id')).focus();
		}		
		frm_det_productos.bootstrapValidator('revalidateField', 'txt_cantidad');
	});
	
	$('#txt_precio').change(function() {	
		var cantidad =  $('#txt_cantidad').val();
		var pre_unitario = $(this).val();
		
		if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
			var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
			$('#txt_imp_total').val(formatMontoAll(imp_total));
		} else {
			$('#txt_imp_total').val('');
		}
		frm_det_productos.bootstrapValidator('revalidateField', 'sel_producto');
	});
	
	var cantidad = $(this).val();
	var pre_unitario = $('#txt_pre_unitario').val();
	if (!esnulo(cantidad) && !esnulo(pre_unitario)) {
		var imp_total = parseFloat(cantidad) * parseFloat(pre_unitario);
		$('#txt_imp_total').val(formatMontoAll(imp_total));
	} else {
		$('#txt_imp_total').val('');
	}
	
});

function inicializarDatos() {
		
  	$('#li_administracion').addClass('active');
	$('#ul_administracion').css('display', 'block');
	$('#ul_adm_catalogo').css('display', 'block');
	$('#li_adm_catalogo').attr('class', 'active');
	$('#li_adm_catalogo').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {
		

		if (!esnulo(catalogo_producto.idProducto)) {
			$('#hid_id_catalogo_producto').val(catalogo_producto.idProducto);	

			$('#txt_cod_producto').val(catalogo_producto.codigoProducto);
			$('#txt_cod_siga').val(catalogo_producto.codigoSiga);
			
			$('#sel_estado').val(catalogo_producto.estado);
			$('#sel_categoría').val(catalogo_producto.idCategoria);
			$('#txt_nombre').val(catalogo_producto.nombreProducto);
			$('#sel_unidad_medida').val(catalogo_producto.idUnidadMedida);
			$('#sel_tipo_envase').val(catalogo_producto.idEnvase);
			$('#txt_peso_neto').val(catalogo_producto.pesoNeto);
			$('#txt_peso_bruto').val(catalogo_producto.pesoBruto);
			var largo=catalogo_producto.dimLargo;
			var alto=catalogo_producto.dimAlto;
			var ancho=catalogo_producto.dimAncho;
			$('#txt_largo').val(catalogo_producto.dimLargo);
			$('#txt_alto').val(catalogo_producto.dimAlto);
			$('#txt_ancho').val(catalogo_producto.dimAncho);
			
			var volumen = parseFloat(largo) * parseFloat(alto) * parseFloat(ancho);
			
			$('#txt_volumen').val(formatMontoAll(volumen));
			
			$('#txt_observaciones').val(catalogo_producto.observacion);
			
		} else {
			
			$('#hid_id_catalogo_producto').val('');	
			$('#txt_cod_producto').val(catalogo_producto.codigoProducto);
			$('#txt_cod_siga').val('');
			
			$('#sel_estado').val('A');
			$('#sel_categoría').val('');
			$('#txt_nombre').val('');
			$('#sel_unidad_medida').val('');
			$('#sel_tipo_envase').val('');
			$('#txt_peso_neto').val('');
			$('#txt_peso_bruto').val('');
		}



	}


}

