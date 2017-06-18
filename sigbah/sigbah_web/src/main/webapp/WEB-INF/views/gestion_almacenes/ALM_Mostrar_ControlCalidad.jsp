<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Gestión de Almacenes</li>
		<li>Control de Calidad</li>
		<li>Registro</li>
	</ol>
	<!-- end breadcrumb -->
</div>
<!-- END RIBBON -->
    
<!-- MAIN CONTENT -->
<div id="content">
	
	<!-- widget grid -->
	<section id="widget-grid" class="">
	
		<!-- row -->
		<div class="row">
		
			<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12">
			
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget well">

					<header></header>
	
					<!-- widget div-->
					<div>
	
						<!-- widget content -->
						<div class="widget-body">
	
							<ul id="ul_man_con_calidad" class="nav nav-tabs bordered">
								<li class="active">
									<a href="#div_dat_generales" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									<spring:message code="mantenimiento.control.calidad.pestania.titulo1" /></a>
								</li>
								<li>
									<a href="#div_alimentarios" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									<spring:message code="mantenimiento.control.calidad.pestania.titulo2" /></a>
								</li>
								<li>
									<a href="#div_no_alimentarios" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									<spring:message code="mantenimiento.control.calidad.pestania.titulo3" /></a>
								</li>
								<li>
									<a href="#div_documentos" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									<spring:message code="mantenimiento.control.calidad.pestania.titulo4" /></a>
								</li>
							</ul>
	
							<div id="myTabContent1" class="tab-content padding-10">
								<div class="tab-pane fade in active" id="div_dat_generales">
									<p>
										I have six locks on my door all in a row. When I go out, I lock every other one. I figure no matter how long somebody stands there picking the locks, they are always locking three.
									</p>
								</div>
								<div class="tab-pane fade" id="div_alimentarios">
									<p>
										Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee.
									</p>
								</div>
								<div class="tab-pane fade" id="div_no_alimentarios">
									<p>
										I have six locks on my door all in a row. When I go out, I lock every other one. I figure no matter how long somebody stands there picking the locks, they are always locking three.
									</p>
								</div>
								<div class="tab-pane fade" id="div_documentos">
									<p>
										I have six locks on my door all in a row. When I go out, I lock every other one. I figure no matter how long somebody stands there picking the locks, they are always locking three.
									</p>
								</div>
							</div>
	
						</div>
						<!-- end widget content -->
	
					</div>
					<!-- end widget div -->
	
				</div>
				<!-- end widget -->
			
			</article>	
	
		</div>	
		<!-- end row -->
	
	</section>
	<!-- end widget grid -->
	
</div>
<!-- END MAIN CONTENT -->

<!-- inline scripts related to this page -->
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/mantenimiento_control_calidad.js"></script>
