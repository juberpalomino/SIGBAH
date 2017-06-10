//GENERALES
var NOTIFICACION_OK = '01';
var NOTIFICACION_ERROR = '99';

$(function() {

    $(".onlyNumbers").bind('paste', function() {
        event.preventDefault();
    });

    $('.onlyNumbers').keypress(function(event) {
        var key = (document.all) ? event.keyCode : event.which;
        switch (key) {
            case 0:
                return true;
                break;
            case 8:
                return true;
                break;
            default:
                var patron = /[0-9]/;
                var te = String.fromCharCode(key);
                return patron.test(te);
                break;
        }
    });

    $('.onlyAmounts').keypress(function(event) {
        var key = (document.all) ? event.keyCode : event.which;
        switch (key) {
            case 0:
                return true;
                break;
            case 8:
                return true;
                break;
            case 46:
                return true;
                break;
            default:
                var patron = /[0-9]/;
                var te = String.fromCharCode(key);
                return patron.test(te);
                break;
        }
    });
    
    $('.onlyAmountsNegative').keypress(function(event) {
        var key = (document.all) ? event.keyCode : event.which;
        switch (key) {
            case 0:
                return true;
                break;
            case 8:
                return true;
                break;
            case 46:
                return true;
                break;
            case 45:
                return true;
                break;
            default:
                var patron = /[0-9]/;
                var te = String.fromCharCode(key);
                return patron.test(te);
                break;
        }
    });
	
	$('.upperValue').change(function(e) {					
		e.preventDefault();		
		var val_campo = $(this);
		val_campo.val((val_campo.val()).toUpperCase());	
	});

    $('[data-hide]').on('click', function() {
        $(this).closest('.' + $(this).attr('data-hide')).hide();
    });
    
    $('#ul_men_opcion').on('click', 'li.li_men_opcion', function(event) {
    	var id_menu = $(this).attr('id');
        switch (id_menu) {
	        case 'li_maestro':
				$(location).attr('href', VAR_CONTEXT + '/maestro/inicio');	
	            break;
	        case 'li_widgets':
	        	$(location).attr('href', VAR_CONTEXT + '/producto/inicio');
	            break;
	    }
    });

});

/**
 * Componente que permite realizar peticiones de tipo Ajax al servidor.
 * 
 * @param metodoEnv - Metodo de envio. Puede ser POST o GET.
 * @param direccionUrl - Url del controlador que gestionara la peticion.
 * @param jsonString - Objeto en formato Json que contiene los datos de la
 *            		   peticion.
 * @param idBoton - Identificador del boton que dispara la peticion. Si no
 *            		es disparado por un boton, sera null.
 * @param callback - Nombre de la funcion que se ejecutara al finalizar el
 *            		 proceso. De indicarse null, se omite su uso.
 */
function consultarAjax(metodoEnv, direccionUrl, jsonString, callback) {
	$.ajax({
		type : metodoEnv,
		data : jsonString,
		async : true,
		url : VAR_CONTEXT + direccionUrl,
		timeout : 30000,
		contentType : 'application/json',
		success : function(respuesta) {
			callback(respuesta);
		},
		error : function(respuesta) {
			var resp = null;
			if (respuesta.status == '404') {
				resp = {
					'mensajeRespuesta' : 'El recurso solicitado no existe (HTTP: 404).',
					'codigoRespuesta' : '99'
				};
			} else {
				resp = {
					'mensajeRespuesta' : 'Error no identificado. ' + respuesta.status,
					'codigoRespuesta' : '99'
				};
			}
			callback(resp);
		}
	});
}

/**
 * Componente que permite realizar peticiones sincronas de tipo Ajax al servidor.
 * 
 * @param metodoEnv - Metodo de envio. Puede ser POST o GET.
 * @param direccionUrl - Url del controlador que gestionara la peticion.
 * @param jsonString - Objeto en formato Json que contiene los datos de la
 *            		   peticion.
 * @param idBoton - Identificador del boton que dispara la peticion. Si no
 *            		es disparado por un boton, sera null.
 * @param callback - Nombre de la funcion que se ejecutara al finalizar el
 *            		 proceso. De indicarse null, se omite su uso.
 */
function consultarAjaxSincrono(metodoEnv, direccionUrl, jsonString, callback) {
	$.ajax({
		type : metodoEnv,
		data : jsonString,
		async : false,
		url : VAR_CONTEXT + direccionUrl,
		timeout : 30000,
		contentType : 'application/json',
		success : function(respuesta) {
			callback(respuesta);
		},
		error : function(respuesta) {
			var resp = null;
			if (respuesta.status == '404') {
				resp = {
					'mensajeRespuesta' : 'El recurso solicitado no existe (HTTP: 404).',
					'codigoRespuesta' : '99'
				};
			} else {
				resp = {
					'mensajeRespuesta' : 'Error no identificado. ' + respuesta.status,
					'codigoRespuesta' : '99'
				};
			}
			callback(resp);
		}
	});
}


function padDigits(number, digits) {
    return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
}

// PARA MOSTRAR ALERTAS

function showMessage(msg) {
    bootbox.dialog({
        message: "<span class='bigger-110'><b>" + msg + "</b></span>",
        buttons: {
            cancel: {
                label: 'Cerrar',
                className: 'btn-sm btn-danger'
            }
        }
    });
}

function addInfoMessage(title, content) {
	if (title != null) {
		$.smallBox({
			title : title,
			content : content,
			color : '#3276B1',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	} else {
		$.smallBox({
			content : content,
			color : '#3276B1',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	}	
}

function addWarnMessage(title, content) {
	if (title != null) {
		$.smallBox({
			title : title,
			content : content,
			color : '#C79121',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	} else {
		$.smallBox({
			content : content,
			color : '#C79121',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	}	
}

function addErrorMessage(title, content) {
	if (title != null) {
		$.smallBox({
			title : title,
			content : content,
			color : '#C46A69',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	} else {
		$.smallBox({
			content : content,
			color : '#C46A69',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	}	
}

function addSuccessMessage(title, content) {
	if (title != null) {
		$.smallBox({
			title : title,
			content : content,
			color : '#739E73',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	} else {
		$.smallBox({
			content : content,
			color : '#739E73',
			iconSmall : 'fa fa-times bounce animated',
			timeout : 3000
		})
	}	
}


// ------------

// GESTION DE LOADERS
function loadding(indicador) {
    if (indicador) {
    	showLoader.show();
    } else {
    	showLoader.hide();
    }
}

// -----------------

// FUNCION PARA CARGAR EL LOADER
var showLoader = (function($) {
    var $dialog = $('<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">' + 
						'<div class="modal-dialog modal-loader">' + 
						'<div class="modal-content">' + 
						'<a href="#" class="btn bg-color-red txt-color-white"><i class="fa fa-gear fa-4x fa-spin"></i></a>' + 
					'</div></div></div>');
    return {
        show: function() {
            $dialog.modal();
        },
        hide: function() {
            $dialog.modal('hide');
        }
    };
})(jQuery);

// --------------
// FUNCION PARA REPOBLAR UN COMBO CON AJAX

function simpleCboAjaxPopulate(parentId, targetId, href, key, defaultOption, showLoader) {
    if (showLoader) {
        startAjax();
    }
    var params = new Object();
    params[key] = $(parentId).val();
    var keyParams = ["argumento", "funcion"]; // key para cada columna
    $(targetId).html("");
    getMapToDataSet(function(dataSet) {
        var options = "";

        if (defaultOption) {
            options += "<option value=''>&#60Seleccione&#62</option>";
        }

        $.each(dataSet, function(i, item) {
            options += "<option value='" + item[0] + "'>" + item[1] + "</option>";
        });
        $(targetId).html(options);

        if (showLoader) {
            endAjax();
        }
    }, href, params, keyParams);
}

function simpleCboAjaxPopulateWithOptions(parentId, targetId, href, key, keyParamas, defaultOption, showLoader) {
    if (showLoader) {
        startAjax();
    }
    var params = new Object();
    params[key] = $(parentId).val();
    var keyParams = keyParamas; // key para cada columna
    $(targetId).html("");
    getMapToDataSet(function(dataSet) {
        var options = "";
        if (defaultOption) {
            options += "<option value=''>&#60Seleccione&#62</option>";
        }
        $.each(dataSet, function(i, item) {
            options += "<option value='" + item[0] + "'>" + item[1] + "</option>";
        });
        $(targetId).html(options);
        if (showLoader) {
            endAjax();
        }
    }, href, params, keyParams);
}

function simpleCboAjaxPopulateParam(parentId, parentParam, href, keyParamas, defaultOption, showLoader) {
    if (showLoader) {
        startAjax();
    }
    $(parentId).html("");
    getMapToDataSet(function(dataSet) {
        var options = "";
        if (defaultOption) {
            options += "<option value=''>&#60Seleccione&#62</option>";
        }
        $.each(dataSet, function(i, item) {
            options += "<option value='" + item[0] + "'>" + item[1] + "</option>";
        });
        $(parentId).html(options);
        if (showLoader) {
            endAjax();
        }
    }, href, keyParamas, parentParam);
}

function getMapToDataSet(parentFunction, href, params, keyParams) {
    var dataSet = [];
    var url = VAR_CONTEXT + href;
    $.getJSON(url, params, function(data) {
        $.each(data, function(i, item) {
            var dataRow = [];
            $.each(keyParams, function(j, keyParam) {
                if (keyParam instanceof Array) {
                    var parametro = '';
                    var arrayParams = keyParam.slice().reverse();
                    $.each(arrayParams, function(key, value) {
                        parametro = item[value] + '|' + parametro;
                    });
                    var tamanio = parametro.length;
                    dataRow[j] = parametro.substring(0, tamanio - 1);
                } else {
                    dataRow[j] = item[keyParam];
                }
            });
            dataSet[i] = dataRow;
        });
        // parentFunction(dataSet);
    }).success(function() {
        // parentFunction(dataSet);
    }).error(function(err) {
        var msg = 'Se genero un error en la consulta: <br/>' + err;
		addErrorMessage(null, msg);
    }).complete(function() {
        parentFunction(dataSet);
    });

};

// -------------------------------------

function centraVentana(ancho, alto) {
    if (window.screen) {
        var aw = screen.availWidth;
        var ah = screen.availHeight;
        window.resizeTo(ancho, alto);
        window.moveTo((aw - ancho) / 2, (ah - alto) / 2);
    }
}

function algunCheck(form) {
    for (var i = 0; i < form.elements.length; i++) {
        if (form.elements[i].type == "checkbox") {
            if (form.elements[i].checked) {
                return true;
            }
        }
    }
    return false;
}

function validarHora(strHora) {
    if (longitudcorrecta(strHora, 5)) {
        strHora += ":00";
    }
    return !(!(/[0-2][0-9]:[0-5][0-9]:[0-5][0-9]/.test(strHora)) || (strHora
        .substring(0, 2) < 0 || strHora.substring(0, 2) > 23));
}

function formateafecha(valor) {
    var l = StringTokenizer(valor, "/");
    return rellena(l[0], "0", 2) + "/" + rellena(l[1], "0", 2) + "/" + l[2];
}

function estelefono(valor) {
    var pattern = "/\\b(^(\\d+)(\\-\\d+)$)\\b/gi";
    return valor.match(eval(pattern));
}

function esEntero(numero) {
    tokens = StringTokenizer(numero, '.');
    return ((tokens.length <= 1) && (esnumero(numero)));
}

function validaDecimal(numero, dec) {
    tokens = StringTokenizer(numero, '.');
    return ((tokens.length > 1) ? (tokens[tokens.length - 1].length > 0 && tokens[tokens.length - 1].length <= dec) : true) && (esdecimal(numero));
}

function esdecimal(valor) {
    var pattern = "/\\b(^(\\d+)(\\.\\d+)$)\\b/gi";
    return valor.match(eval(pattern));
}

function formatearMonto(value) {
    return value.toFixed(2).replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
}

function validacorreo(myString) {
    return myString.match(/\b(^(\S+@).+((\.gob)|(\.com)|(\.net)|(\.edu)|(\.mil)|(\.gov)|(\.org)|(\..{2,2}))$)\b/gi);
}

function validaFecha(myString) {
    return myString.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
}

/* fn & ext Rt T F */
function validanombrearchivo(nombre, ext) {
    var pattern = "/\\b(^(((\\S)|(\\s))+)(\\." + ext + ")$)\\b/gi";
    return nombre.match(eval(pattern));
}

/* Rt Arr */
function StringTokenizer(cad, delim) {
    var cads = new Array();
    var n = cad.length;
    var j = 0;
    var ic = 0;
    for (var i = 0; i < n; i++) {
        if (cad.charAt(i) == delim) {
            cads[j] = cad.substring(ic, i);
            ic = i + 1;
            j++;
        }
    }
    cads[j] = cad.substring(ic, n);
    return cads;
}

/* Rt m ltr: mes # / 1-12, may 1 o 0 M o m, cap 1 o 0 M 1ra lt */
function mesenletras(mes, may, cap) {
    if (!esnumero(mes)) {
        return "-";
    }
    var imes = parseInt(mes, 10);
    var tmes = "";
    if (imes == 1) {
        tmes = "enero";
    } else if (imes == 2) {
        tmes = "febrero";
    } else if (imes == 3) {
        tmes = "marzo";
    } else if (imes == 4) {
        tmes = "abril";
    } else if (imes == 5) {
        tmes = "mayo";
    } else if (imes == 6) {
        tmes = "junio";
    } else if (imes == 7) {
        tmes = "julio";
    } else if (imes == 8) {
        tmes = "agosto";
    } else if (imes == 9) {
        tmes = "setiembre";
    } else if (imes == 10) {
        tmes = "octubre";
    } else if (imes == 11) {
        tmes = "noviembre";
    } else if (imes == 12) {
        tmes = "diciembre";
    }

    if (may == 1) {
        tmes = tmes.toUpperCase();
    }
    if (cap == 1) {
        tmes = tmes.substring(0, 1).toUpperCase() + tmes.substring(1, tmes.length);
    }
    return tmes;
}

/* -1: err, 1: f1>f2, 2: f1<f2, 0: f1=f2 */
function comparafecha(fecha1, fecha2) {
    if (!checkdate(fecha1) || !checkdate(fecha2)) {
        return -1;
    }
    dia = fecha1.substring(0, 2);
    mes = fecha1.substring(3, 5);
    anho = fecha1.substring(6, 10);
    fecha1x = anho + mes + dia;
    dia = fecha2.substring(0, 2);
    mes = fecha2.substring(3, 5);
    anho = fecha2.substring(6, 10);
    fecha2x = anho + mes + dia;
    return (fecha1x > fecha2x ? 1 : (fecha1x < fecha2x ? 2 : 0));
}

function corta(campo, longitud, cars) {
    if (campo.value.length > longitud) {
        campo.value = campo.value.substring(0, longitud);
	}
    cuenta(campo, cars);
}

function cuenta(campo, cars) {
    cars.value = campo.value.length;
}

function rellena(dato, caracter, tamanho) {
    dato_trim = trim(dato);
    len = dato_trim.length;
    dato_fill = "";
    for (var i = 0; i < tamanho - len; i++) {
        dato_fill += caracter;
    }
    dato_fill += dato_trim;
    return dato_fill;
}

function checkdate(fecha) {
    var err = 0;
    if (fecha.length != 10) {
        err = 1;
    }
    var dia = fecha.substring(0, 2);
    var slash1 = fecha.substring(2, 3);
    var mes = fecha.substring(3, 5);
    var slash2 = fecha.substring(5, 6);
    var anho = fecha.substring(6, 10);
    if (dia < 1 || dia > 31) {
        err = 1;
    }
    if (slash1 != '/') {
        err = 1;
    }
    if (mes < 1 || mes > 12) {
        err = 1;
    }
    if (slash1 == '/' && slash2 != '/') {
        err = 1;
    }
    if (anho < 0 || anho > 2200) {
        err = 1;
    }
    if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
        if (dia == 31) {
            err = 1;
        }
    }
    if (mes == 2) {
        var g = parseInt(anho / 4);
        if (isNaN(g)) {
            err = 1;
        }
        if (dia > 29) {
            err = 1;
        }
        if (dia == 29 && ((anho / 4) != parseInt(anho / 4))) {
            err = 1;
        }
    }
    return (!(err == 1));
}

function esnulo(campo) {
    return (campo == null || campo == '' || campo == 'null');
}

function validarnulo(campo) {
    if (esnulo(campo)) {
        return null;
    }
    return campo;
}

function esnulooguion(campo) {
    return esnulo(campo) || (trim(campo) == '-');
}

function esnumero(campo) {
    return (!(isNaN(campo)));
}

function longitudcorrecta(campo, len) {
    if (campo != null) {
        return (campo.length == len);
    } else {
        return false;
    }
}

function mayuscula(campo) {
    return campo.toUpperCase();
}

function minuscula(campo) {
    return campo.toLowerCase();
}

function eslongrucok(ruc) {
    return (ruc.length == 11);
}

function eslongcontrasenhaok(contrasenha) {
    return (contrasenha.length >= longcontrasenhaok());
}

function longcontrasenhaok() {
    return 6;
}

function esnegativo(valor) {
    return (valor < 0);
}

function esrucok(ruc) {
    return (!(esnulo(ruc) || !esnumero(ruc) || !eslongrucok(ruc) || !valruc(ruc)));
}

function valruc(valor) {
    valor = trim(valor);
    if (esnumero(valor)) {
        if (valor.length == 8) {
            suma = 0;
            for (var i = 0; i < valor.length - 1; i++) {
                digito = valor.charAt(i) - '0';
                if (i == 0) {
                    suma += (digito * 2);
                } else {
                    suma += (digito * (valor.length - i));
                }
            }
            resto = suma % 11;
            if (resto == 1) {
                resto = 11;
            }
            if (resto + (valor.charAt(valor.length - 1) - '0') == 11) {
                return true;
            }
        } else if (valor.length == 11) {
            suma = 0;
            x = 6;
            for (i = 0; i < valor.length - 1; i++) {
                if (i == 4) {
                    x = 8;
                }
                digito = valor.charAt(i) - '0';
                x--;
                if (i == 0) {
                    suma += (digito * x);
                } else {
                    suma += (digito * x);
                }
            }
            resto = suma % 11;
            resto = 11 - resto;

            if (resto >= 10) {
                resto = resto - 10;
            }
            if (resto == valor.charAt(valor.length - 1) - '0') {
                return true;
            }
        }
    }
    return false;
}

function longitudmayor(campo, len) {
    return (campo != null) ? (campo.length > len) : false;
}

function estaentre(campo, inicio, fin) {
    if (campo != null)
        return (campo.length >= inicio && campo.length <= fin);
    else
        return false;
}

function trim(cadena) {
    cadena2 = "";
    len = cadena.length;
    for (var i = 0; i <= len; i++) {
        if (cadena.charAt(i) != " ") {
            cadena2 += cadena.charAt(i);
        }
    }
    return cadena2;
}

// para comparar valores vacios
function isEmpty(object) {
    if ($(object).val() == "") {
        return true;
    } else {
        return false;
    }
}

// pad_zero(14, 4)
function pad_zero(n, length) {
    var str = n.toString();
    while (str.length < length)	{
        str = '0' + str;
	}
	return str;
}

function get_for_date(fecha) {
	var dia = parseInt(fecha.substring(0, 2)) + 1;
	var mes = fecha.substring(3, 5);
	var anio = fecha.substring(6, 10);
	var fec_form = anio + '-' + mes + '-' + dia;
	return new Date(fec_form);
}

