<!-- #PLUGINS -->
<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
<script src="${pageContext.request.contextPath}/resources/js/libs/jquery-2.1.1.min.js"></script>

<!-- IMPORTANT: APP CONFIG -->
<script src="${pageContext.request.contextPath}/resources/js/app.config.js"></script>

<!-- BOOTSTRAP JS -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>

<!--[if IE 8]>
	<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>
<![endif]-->

<!-- MAIN APP JS FILE -->
<script src="${pageContext.request.contextPath}/resources/js/app.min.js"></script>

<!-- BOOSTRAP VALIDATE -->
<script src="${pageContext.request.contextPath}/resources/js/plugin/bootstrapvalidator/bootstrapValidator.min.js"></script>

<!-- PAGE RELATED PLUGIN(S) -->
<script src="${pageContext.request.contextPath}/resources/js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

<!-- CUSTOM NOTIFICATION -->
<script src="${pageContext.request.contextPath}/resources/js/notification/SmartNotification.min.js"></script>

<!-- JARVIS WIDGETS -->
<script src="${pageContext.request.contextPath}/resources/js/smartwidgets/jarvis.widget.min.js"></script>

<!-- JQUERY SELECT2 INPUT -->
<script src="${pageContext.request.contextPath}/resources/js/plugin/select2/select2.min.js"></script>

<!-- JQUERY CALENDAR INPUT -->
<script src="${pageContext.request.contextPath}/resources/js/date-time/bootstrap-datepicker.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/jquery.fileDownload-1.4.0.js"></script>


<script type="text/javascript">
	var VAR_CONTEXT = '${pageContext.request.contextPath}';
	var codigoRespuesta = '${base.codigoRespuesta}';
	var mensajeRespuesta = '${base.mensajeRespuesta}';
	var mensajeReporteExito = 'El archivo se descarg&oacute; exitosamente.';
	var mensajeReporteError = 'No se pudo completar la descarga del archivo, por favor intentar nuevamente.';
</script>

<!-- BASE APP JS FILE -->
<script src="${pageContext.request.contextPath}/resources/js/base.js"></script>