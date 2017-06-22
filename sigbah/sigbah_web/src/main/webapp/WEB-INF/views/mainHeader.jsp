<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- #HEADER -->
<header id="header">

	<div id="logo-group">
		<div class="row">
			<div class="col-xs-12">
				<div class="col-sm-12">
					<img src="${pageContext.request.contextPath}/resources/img/logo-indeci.png" class="img-responsive">
				</div>
			</div>
		</div>
	</div>
	
	<div class="project-context hidden-xs">	
		<div class="row">
			<div class="col-xs-12">
				<div class="col-sm-4">
					<img src="${pageContext.request.contextPath}/resources/img/logo-wfp.png" class="img-responsive">
				</div>
				<div id="div-titulo" class="col-sm-4">
					<spring:message code="application_name" />
				</div>
				<div id="div-usuario" class="col-sm-4">
					Usuario: ${usuario.nombreUsuario} <br> ${usuario.nombreDdi}
				</div>
			</div>
		</div>	
	</div>
	
	<!-- #TOGGLE LAYOUT BUTTONS -->
	<!-- pulled right: nav area -->
	<div class="pull-right">
		<!-- collapse menu button -->
		<div id="hide-menu" class="btn-header pull-right">
			<span>
				<a href="javascript:void(0);" data-action="toggleMenu" title="Collapse Menu">
					<i class="fa fa-reorder"></i>
				</a>
			</span>
		</div>
		<!-- end collapse menu -->

		<!-- logout button -->
		<div id="logout" class="btn-header pull-right">
			<span> 
				<a href="${pageContext.request.contextPath}/logout/inicio" title="Cerrar Sesión">
					Salir
					<i class="fa fa-sign-out"></i>
				</a> 
			</span>
		</div>
		<!-- end logout button -->

	</div>
	<!-- end pulled right: nav area -->

</header>
<!-- END HEADER -->

<script type="text/javascript">
	try { 
		// Si la session se encuentra inactiva		
		if ('${usuario}' == null || '${usuario}' == '') {
			if (confirm('Su session se encuentra inactiva, inicie nuevamente !!!')) {
				window.location.href = '${pageContext.request.contextPath}/login';
			} else {
				window.location.href = '${pageContext.request.contextPath}/login';
			}
		}
		
	} catch(e) {}
</script>