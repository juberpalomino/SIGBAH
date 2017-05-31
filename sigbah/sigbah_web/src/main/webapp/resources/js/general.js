//GENERALES
var oTable1 = null;

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

	        	ejecutarOpcionMenu('/maestro/inicio');
	        	
	            break;
	        case 'li_widgets':
	        	
	        	ejecutarOpcionMenu('/producto/inicio');

	            break;
	    }
    });

});

function ejecutarOpcionMenu(url) {
	$.ajax({
		type: 'POST',
		url: VAR_CONTEXT + url,
		data: { },
		dataType: 'text',
		success: function(respuesta) {						
			if (respuesta != null) {
				refreshAppBody(respuesta)
			} else {
				alert('error');
//				addWarnMessage(null, 'No se encuentra registrado la actual partida.');
			}	
		},
		error: function(jqXHR, error, errorThrown) {
			var msg = 'Error:<br/>';
			if (jqXHR.status && jqXHR.status == 400) {
				msg = msg + jqXHR.responseText;
			} else {
				msg = msg + errorThrown;
			}
//			addErrorMessage(null, msg);
			alert('error: '+msg);
		}
	});
}

function refreshAppBody(data) {
    refreshScript('main', data);
    $('#main').show();
}

function refreshScript(refreshDiv, data) {
    $('#' + refreshDiv).html(data);
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

function addInfoMessage(msg, detail) {
	$.gritter.add({
		title: msg,
		text: detail,
		sticky: false,
		before_open: function() {
			// Limitamos el numero de alertas 3 en la interfaz del sistema
			if ($('.gritter-item-wrapper').length >= 3) {
				return false;
			}
		},
		class_name: 'gritter-info gritter-light'
	});
}

function addWarnMessage(msg, detail) {
	$.gritter.add({
		title: msg,
		text: detail,
		sticky: false,
		before_open: function() {
			// Limitamos el numero de alertas 3 en la interfaz del sistema
			if ($('.gritter-item-wrapper').length >= 3) {
				return false;
			}
		},
		class_name: 'gritter-warning gritter-light'
	});
}

function addErrorMessage(msg, detail) {
	$.gritter.add({
		title: msg,
		text: detail,
		sticky: false,
		before_open: function() {
			// Limitamos el numero de alertas 3 en la interfaz del sistema
			if ($('.gritter-item-wrapper').length >= 3) {
				return false;
			}
		},
		class_name: 'gritter-error gritter-light'
	});
}


// ------------

// GESTION DE LOADERS
function startAjax() {
    // $(".loading").show();
    showLoader.show();
}

function endAjax() {
    // $(".loading").hide();
    showLoader.hide();
}
// -----------------

// FUNCION PARA CARGAR EL LOADER
var showLoader = (function($) {
    var $dialog = $('<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">' + 
						'<div class="modal-dialog modal-loader">' + 
						'<div class="modal-content">' + 
						'<i class="fa fa-cog fa-spin fa-3x" style="margin-left:9px">' + 
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

function simpleCboAjaxPopulate(parentId, targetId, href, key, defaultOption,
    showLoader) {
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

function simpleCboAjaxPopulateWithOptions(parentId, targetId, href, key,
    keyParamas, defaultOption, showLoader) {
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

// -----------
// GESTION DE DATATABLES

function simpleGenerateDatatable(url, layout, options, dblclick, fnimprimir) {
    var settings = $.extend({
        containerTable: '.containerDatatable',
        idTable: 'tblGenerate',
        styleTable: 'tblDatatable',
        loading: true,
        parameters: "",
        vTableOptions: {},
        tableOptions: $.extend({
            bFilter: false,
            paging: false,
            ordering: true,
            info: false,            
            aLengthMenu: [
                [5, 10, 20],
                [5, 10, 20]
            ],
            iDisplayLength: 100
        }, options.vTableOptions)
    }, options);

    // muestra loader
    if (settings.loading) {
        startAjax();
    }

    // EXTRACCIÓN DE DATA
    var keyParams = new Array();
    var headColumns = new Array();
    var bodyColumns = new Array();
    var no_ordenables = new Array();
    var COMODIN = "%%COMODIN%%";
    var COMODIN_LINK = COMODIN + "\")'>";

    $.each(layout, function(i, col) {
        var colSettings = $.extend({
            'title': '',
            'type': 'text',
            'field': '',
            'class': '',
            'method': 'alert',
            'label': '',
            'icon': 'glyphicon-pencil'
        }, col);
        keyParams[i] = colSettings.field; //setea el key a extraer

        if (colSettings['class'].indexOf("no-sort") > -1) {
            no_ordenables.push(i);
        }

        if (colSettings.type == "text") {
            headColumns[i] = {
                "title": colSettings.title,
                "class": colSettings['class']
            };
            bodyColumns[i] = "";
        } else if (colSettings.type == "link") {
            headColumns[i] = {
                "title": colSettings.title,
                "class": colSettings['class']
            };
			if (!esnulo(colSettings.label)) {
				bodyColumns[i] = "<a class='" + colSettings['class'] + "' onClick='" + colSettings.method + "(\"" + COMODIN + "\")'>" + colSettings.label + "</a>";
			} else {
				bodyColumns[i] = "<a class='" + colSettings['class'] + "' onClick='" + colSettings.method + "(\"" + COMODIN_LINK;
			}            
        } else if (colSettings.type == "button") {
            headColumns[i] = {
                "title": colSettings.label,
                "class": 'no-sort center'
            };
            bodyColumns[i] = "<button class='btn " + colSettings['class'] + " btn-xs' onClick='" +
                colSettings.method + "(\"" + COMODIN + "\")' " +
                "title='" + colSettings.title + "' field_cond='" + colSettings['field_cond'] + "'>" +
                "<i class='ace-icon " + colSettings['icon'] + " icon-only'></i>" +
                //		colSettings.label+
                "</button>";
            no_ordenables.push(i);
        } else if (colSettings.type == "enum") {
            var claseEnum = colSettings['class'] + ' colEnum';
            headColumns[i] = {
                "title": colSettings.title,
                "class": claseEnum
            };
            bodyColumns[i] = COMODIN;
        } else if (colSettings.type == "select") {
            headColumns[i] = {
                "title": colSettings.title,
                "class": 'no-sort chkSelection ' + colSettings['class']
            };
            bodyColumns[i] = "SELECT";
        } else if (colSettings.type == "chk") {
            headColumns[i] = {
                "title": colSettings.label,
                "class": 'center'
            };
            bodyColumns[i] = "<label class='pos-rel'>"+
							     "<input type='checkbox' class='ace' value='"+COMODIN+"' />"+
								 "<span class='lbl'></span>"+
							 "</label>";            
        }

    });

    getMapToDataSet(function(dataSet) {
            // reemplazando values
            $.each(dataSet, function(i, row) {
                $.each(row, function(j, col) {
                    if (bodyColumns[j] != "") {
                        if (bodyColumns[j] == COMODIN) {
                            dataSet[i][j] = i + 1;
                        } else if (bodyColumns[j] == 'SELECT') {
                            dataSet[i][j] = "";
                        } else {
                        	var val_link = COMODIN + "\")'>";
                        	if (bodyColumns[j].indexOf(val_link) > 0) {
                        		var arr_link = dataSet[i][j].split('|');
								var lab_link = '';
								for (var t = 0; t < arr_link.length ; t++) {
									if (t > 0) {
										lab_link = lab_link + arr_link[t].trim() + '-';
									}
								}
								lab_link = lab_link.substring(0, lab_link.length - 1);
								var val_link = arr_link[0] + "\")'>" + lab_link + "</a>";
								dataSet[i][j] = bodyColumns[j].replace(COMODIN_LINK, val_link);
                        	} else {
                        		dataSet[i][j] = bodyColumns[j].replace(COMODIN, dataSet[i][j]);
                        	}
                        }
                    }
                });
            });

            $(settings.containerTable).html("<table id='" + settings.idTable + "' class='table table-striped table-bordered table-hover " +
                settings.styleTable + "'></table>");

            oTable1 = $("#" + settings.idTable).dataTable({
                "data": dataSet,
                "columns": headColumns,
                "bFilter": settings.tableOptions.bFilter,
                "paging": settings.tableOptions.paging,
                "ordering": settings.tableOptions.ordering,
                "info": settings.tableOptions.info,
                "language": {
                    "url": VAR_CONTEXT + "/resources/js/Spanish.json"
                },
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': no_ordenables
                }],
                "iDisplayLength": settings.tableOptions.iDisplayLength,
                "aLengthMenu": settings.tableOptions.aLengthMenu,
                "fnDrawCallback": function() {
                    if (dblclick != null) {
                        clickRowHandler(settings.idTable, dblclick);
                    }
                }
            });

            //TableTools settings
            TableTools.classes.container = "btn-group btn-overlap";
            TableTools.classes.print = {
                "body": "DTTT_Print",
                "info": "tableTools-alert gritter-item-wrapper gritter-info gritter-center white",
                "message": "tableTools-print-navbar"
            };

            $('#div_table_tools').text('');

            var tableTools_obj = null;
            if (fnimprimir != null) {          	
                tableTools_obj = new $.fn.dataTable.TableTools(oTable1, {
                    "sSwfPath": VAR_CONTEXT + "/resources/js/dataTables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
    		        "sRowSelector": "td:not(:last-child)",
					"sRowSelect": "multi",
					"fnRowSelected": function(row) {
						$(row).find('input[type=checkbox]').get(0).checked = true;
					},
					"fnRowDeselected": function(row) {
						$(row).find('input[type=checkbox]').get(0).checked = false;
					},             
                    "sSelectedClass": "success",
                    "aButtons": [{
						"sExtends": "csv",
						"sToolTip": "Exportar a Excel",
						"sButtonClass": "btn btn-white btn-primary  btn-bold",
						"sButtonText": "<i class='fa fa-file-excel-o bigger-110 green'></i>"
					}, {
						"sExtends": "pdf",
						"sToolTip": "Exportar a PDF",
						"sButtonClass": "btn btn-white btn-primary  btn-bold",
						"sButtonText": "<i class='fa fa-file-pdf-o bigger-110 red'></i>"
					}]
                });
                
                //we put a container before our table and append TableTools element to it
                $(tableTools_obj.fnContainer()).appendTo($('.tableTools-container'));
                
                //select/deselect a row when the checkbox is checked/unchecked
				$('#'+settings.idTable).on('click', 'td input[type=checkbox]' , function() {
					var row = $(this).closest('tr').get(0);
					if (this.checked) {
						tableTools_obj.fnSelect(row);
					} else {
						tableTools_obj.fnDeselect($(this).closest('tr').get(0));
					}
				});
                
            } else {
                tableTools_obj = new $.fn.dataTable.TableTools(oTable1, {
                    "sSwfPath": VAR_CONTEXT + "/resources/js/dataTables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
                    "sRowSelector": "td:not(:last-child)",
                    "sRowSelect": "single",
                    "sSelectedClass": "success",
                    "aButtons": [{
                        "sExtends": "xls",
                        "sToolTip": "Exportar a Excel",
                        "sButtonClass": "btn btn-white btn-primary btn-bold",
                        "sButtonText": "<i class='fa fa-file-excel-o bigger-110 green'></i>"
                    }, {
						"sExtends": "pdf",
						"sToolTip": "Exportar a PDF",
						"sButtonClass": "btn btn-white btn-primary  btn-bold",
						"sButtonText": "<i class='fa fa-file-pdf-o bigger-110 red'></i>"
					}]
                });
                
                //we put a container before our table and append TableTools element to it
                $(tableTools_obj.fnContainer()).appendTo($('.tableTools-container'));
            }

            if (showLoader) {
                endAjax();
            }

        },
        url,
        settings.parameters,
        keyParams
    );
}

/* Click event handler */
function clickRowHandler(idDataTable, nameFunction) {

    var myFuncs = {
    	selUsuario: function(aData) {
            $('#hid_usuario').val(aData[4]);
        }
    };

    /* Link to detail page of selected row one click */
    $('#' + idDataTable + ' tbody tr').bind('click', function() {
        var aData = oTable1.fnGetData(this);
        myFuncs[nameFunction](aData);
    });
}

function simplePopulateDatatable(url, layout, options, dblclick, fnimprimir) {
    var settings = $.extend({
        containerTable: '.containerDatatable',
        idTable: 'tblGenerate',
        styleTable: 'tblDatatable',
        loading: true,
        parameters: "",
        vTableOptions: {},
        tableOptions: $.extend({
            bFilter: false,
            paging: false,
            ordering: true,
            info: false,            
            aLengthMenu: [
                [5, 10, 20],
                [5, 10, 20]
            ],
            iDisplayLength: 500
        }, options.vTableOptions)
    }, options);

    //			muestra loader
    if (settings.loading) {
        startAjax();
    }

    //			EXTRACCIÓN DE DATA
    var keyParams = new Array();
    var headColumns = new Array();
    var bodyColumns = new Array();
    var no_ordenables = new Array();
    var COMODIN = "%%COMODIN%%";

    $.each(layout, function(i, col) {
        var colSettings = $.extend({
            'title': '',
            'type': 'text',
            'field': '',
            'class': '',
            'method': 'alert',
            'label': '',
            'icon': 'glyphicon-pencil'
        }, col);
        keyParams[i] = colSettings.field; //setea el key a extraer

        if (colSettings['class'].indexOf("no-sort") > -1) {
            no_ordenables.push(i);
        }

        if (colSettings.type == "text") {
            headColumns[i] = {
                "title": colSettings.title,
                "class": colSettings['class']
            };
            bodyColumns[i] = "";
        } else if (colSettings.type == "link") {
            headColumns[i] = {
                "title": colSettings.title,
                "class": colSettings['class']
            };
            bodyColumns[i] = "<a class='" + colSettings['class'] + "'  onClick='" + colSettings.method + "(\"" + COMODIN + "\")'>" + colSettings.label + "</a>";
        } else if (colSettings.type == "button") {
            headColumns[i] = {
                "title": colSettings.label,
                "class": 'no-sort center'
            };
            bodyColumns[i] = "<button class='btn " + colSettings['class'] + " btn-xs' onClick='" +
                colSettings.method + "(\"" + COMODIN + "\")' " +
                "title='" + colSettings.title + "'>" +
                "<i class='ace-icon " + colSettings['icon'] + " icon-only'></i>" +
                //		colSettings.label+
                "</button>";
            no_ordenables.push(i);
        } else if (colSettings.type == "enum") {
            var claseEnum = colSettings['class'] + ' colEnum';
            headColumns[i] = {
                "title": colSettings.title,
                "class": claseEnum
            };
            bodyColumns[i] = COMODIN;
        } else if (colSettings.type == "select") {
            headColumns[i] = {
                "title": colSettings.title,
                "class": 'no-sort chkSelection ' + colSettings['class']
            };
            bodyColumns[i] = "SELECT";
        } else if (colSettings.type == "chk") {
            headColumns[i] = {
                "title": colSettings.label,
                "class": 'no-sort center'
            };
            bodyColumns[i] = "<label class='pos-rel'>"+
							     "<input type='checkbox' class='ace' value='"+COMODIN+"' />"+
								 "<span class='lbl'></span>"+
							 "</label>";            
            no_ordenables.push(i);
        }

    });

    getMapToDataSet(function(dataSet) {
            // reemplazando values
            $.each(dataSet, function(i, row) {
                $.each(row, function(j, col) {
                    if (bodyColumns[j] != "") {
                        if (bodyColumns[j] == COMODIN) {
                            dataSet[i][j] = i + 1;
                        } else if (bodyColumns[j] == "SELECT") {
                            dataSet[i][j] = "";
                        } else {
                            dataSet[i][j] = bodyColumns[j].replace(COMODIN, dataSet[i][j]);
                        }
                    }
                });
            });

            $(settings.containerTable).html("<table id='" + settings.idTable + "' class='table table-striped table-bordered table-hover " +
                settings.styleTable + "'></table>");

            oTable1 = $("#" + settings.idTable).dataTable({
                "data": dataSet,
                "columns": headColumns,
                "bFilter": settings.tableOptions.bFilter,
                "paging": settings.tableOptions.paging,
                "ordering": settings.tableOptions.ordering,
                "info": settings.tableOptions.info,
                "language": {
                    "url": VAR_CONTEXT + "/resources/js/Spanish.json"
                },
                "aoColumnDefs": [{
                    'bSortable': false,
                    'aTargets': no_ordenables
                }],
                "iDisplayLength": settings.tableOptions.iDisplayLength,
                "aLengthMenu": settings.tableOptions.aLengthMenu,
                "fnDrawCallback": function() {
                    if (dblclick != null) {
                        clickRowHandler(settings.idTable, dblclick);
                    }
                }
            });

            if (showLoader) {
                endAjax();
            }

        },
        url,
        settings.parameters,
        keyParams
    );
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

