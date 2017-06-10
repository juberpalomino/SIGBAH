<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- #PLUGINS -->
<!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
<script src="${pageContext.request.contextPath}/resources/js/libs/jquery-2.1.1.min.js"></script>

<!-- IMPORTANT: APP CONFIG -->
<script src="${pageContext.request.contextPath}/resources/js/app.config.seed.js"></script>

<!-- BOOTSTRAP JS -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap/bootstrap.min.js"></script>

<!--[if IE 8]>
	<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>
<![endif]-->

<!-- MAIN APP JS FILE -->
<script src="${pageContext.request.contextPath}/resources/js/app.seed.js"></script>

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

<script type="text/javascript">
	var VAR_CONTEXT = '${pageContext.request.contextPath}';
</script>

<!-- BASE APP JS FILE -->
<script src="${pageContext.request.contextPath}/resources/js/base.js"></script>