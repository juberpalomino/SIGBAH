var listaAlimentariosCache = new Object();
var listaNoAlimentariosCache = new Object();
var listaLocalidadesCache = new Object();

var frm_dat_generales = $('#frm_dat_generales');

var tbl_det_localidades = $('#tbl_det_localidades');

var tbl_det_alimentarios = $('#tbl_det_alimentarios');
//var frm_det_alimentarios = $('#frm_det_alimentarios');

var tbl_det_no_alimentarios = $('#tbl_det_no_alimentarios');
//var frm_det_no_alimentarios = $('#frm_det_no_alimentarios');

//var tbl_det_documentos = $('#tbl_det_documentos');
//var frm_det_documentos = $('#frm_det_documentos');

$(document).ready(function() {
	
	$('.datepicker').datepicker({
		autoclose: true,
		todayHighlight: true,
		dateFormat: 'dd/mm/yy',
		clearBtn: true
	});
	
	inicializarDatos();
	
	$('.btn_retornar').click(function(e) {
		e.preventDefault();

		loadding(true);					
		var url = VAR_CONTEXT + '/programacion-bath/emergencia/inicio/0';
		$(location).attr('href', url);
		
	});
	

	
	$('#sel_producto').change(function() {
		var codigo = $(this).val();		
		if (!esnulo(codigo)) {
			var arr = codigo.split('_');
			if (arr.length > 1) {
				$('#txt_uni_medida').val(arr[1]);
			} else {
				$('#txt_uni_medida').val('');
			}			
		} else {
			$('#txt_uni_medida').val('');
		}
	});
	   

	
});

function inicializarDatos() {
	
	$('#li_pro_bah').addClass('active');
	$('#ul_pro_bah').css('display', 'block');
	$('#li_emer_sinpad').attr('class', 'active');
	$('#li_emer_sinpad').closest('li').children('a').attr('href', '#');
	
	if (codigoRespuesta == NOTIFICACION_ERROR) {
		addErrorMessage(null, mensajeRespuesta);
	} else {

		$('#txt_cod_sinpad').val(cabecera.idEmergencia);
		$('#txt_desc_sinpad').val(cabecera.nombreEmergencia);
		$('#txt_fecha').val(cabecera.fechaEmergencia);
		$('#txt_fenomeno').val(cabecera.descFenomeno);
		$('#txt_ubigeo').val(cabecera.ubigeo);
		
		listarDetalleLocalidad(lista_localidad);
		listarDetalleAlimentarios(lista_alimentaria);
		listarDetalleNoAlimentarios(lista_no_alimentaria);
	}
}

function listarDetalleLocalidad(respuesta) {
	
	tbl_det_localidades.dataTable().fnDestroy();
	tbl_det_localidades.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idEmergencia',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'desDepartamento'
		}, {
			data : 'desProvincia'
		}, {
			data : 'desDistrito' 
		}, {
			data : 'desCentroPoblado'
		}, {
			data : 'famAfectado'
		} , {
			data : 'famDamnificado'
		}, {
			data : 'persoAfectado'
		}, {
			data : 'persoDamnificado'
		}],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	   
	listaLocalidadesCache = respuesta;
}

function listarDetalleAlimentarios(respuesta) {

	tbl_det_alimentarios.dataTable().fnDestroy();
	
	tbl_det_alimentarios.dataTable({
		data : respuesta,
		columns : [ {	
			data : 'idEmergencia',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'tipoProducto'
		}, {
			data : 'cantidad'
		}],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaAlimentariosCache = respuesta;

}

function listarDetalleNoAlimentarios(respuesta) {

	tbl_det_no_alimentarios.dataTable().fnDestroy();
	
	tbl_det_no_alimentarios.dataTable({
		data : respuesta,
		columns : [  {	
			data : 'idEmergencia',
			render : function(data, type, full, meta) {
				var row = meta.row + 1;
				return row;											
			}
		}, {
			data : 'categoriaProducto'
		}, {
			data : 'tipoProducto'
		}, {
			data : 'cantidad'
		} ],
		language : {
			'url' : VAR_CONTEXT + '/resources/js/Spanish.json'
		},
		bFilter : false,
		paging : false,
		ordering : false,
		info : false
	});
	
	listaNoAlimentariosCache = respuesta;

}





function cargarProductoNoAlimentario(idCategoria, codigoProducto) {
	var params = { 
		idCategoria : idCategoria
	};			
	loadding(true);
	consultarAjax('GET', '/gestion-almacenes/control-calidad/listarProductoXCategoria', params, function(respuesta) {
		if (respuesta.codigoRespuesta == NOTIFICACION_ERROR) {
			addErrorMessage(null, respuesta.mensajeRespuesta);
		} else {
			var options = '';
	        $.each(respuesta, function(i, item) {
	            options += '<option value="'+item.idProducto+'_'+item.nombreUnidadMedida+'">'+item.nombreProducto+'</option>';
	        });
	        $('#sel_no_producto').html(options);
	        if (codigoProducto != null) {
	        	$('#sel_no_producto').val(codigoProducto);
				$('#sel_no_producto').select2().trigger('change');
				$('#sel_no_producto').select2({
					  dropdownParent: $('#div_pro_det_no_alimentarios')
				});	        	
	        } else {
	        	frm_det_no_alimentarios.bootstrapValidator('revalidateField', 'sel_no_producto');
	        }
		}
		loadding(false);		
	});
}

function cargarTipoControl(val_tip_control) {
	if (val_tip_control == '1') { // Ingreso por Compra de productos
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '2') { // Control Interno
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '3') { // Ingreso por Transferencias de Almacén
		$('#sel_ori_almacen').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '4') { // Salida de Productos por Emergencia
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	} else if (val_tip_control == '5') { // Ingreso por Donación
		$('#sel_ori_almacen').prop('disabled', true);
		$('#sel_proveedor').prop('disabled', false);
		$('#sel_emp_transporte').prop('disabled', false);
		$('#sel_chofer').prop('disabled', false);
		$('#txt_nro_placa').prop('disabled', false);
	} else if (val_tip_control == '6') { // Salidas por Transferencias a Almacén
		$('#sel_ori_almacen').prop('disabled', false);
		$('#sel_proveedor').prop('disabled', true);
		$('#sel_emp_transporte').prop('disabled', true);
		$('#sel_chofer').prop('disabled', true);
		$('#txt_nro_placa').prop('disabled', true);
	}
}