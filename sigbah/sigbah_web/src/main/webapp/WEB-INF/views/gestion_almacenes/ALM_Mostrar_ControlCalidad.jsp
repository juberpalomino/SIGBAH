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
				<div class="jarviswidget">

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
	
							<div id="div_tab_content" class="tab-content padding-10">
								<div class="tab-pane fade in active" id="div_dat_generales">
								
									<form id="frm_con_calidad" class="form-horizontal">
											
										<div class="header-form opc-center">	
											<strong><spring:message code="mantenimiento.control.calidad.titulo" /></strong>
										</div>
										
										<div class="form-group"></div>
										
										<div class="form-group">
											<div class="col-sm-3"></div>
											<label class="col-sm-3 control-label label-sm label-bold"><spring:message code="mantenimiento.control.calidad.nro.control" />:</label>
											<div class="col-sm-2">
												<input type="text" name="txt_nro_con_calidad" name="txt_nro_con_calidad" class="form-control" disabled>
											</div>
										</div>												
																
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2><spring:message code="mantenimiento.control.calidad.pestania.titulo1" /></h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="form-group">
														<label class="col-sm-2 control-label label-sm"><spring:message code="listar.control.calidad.busqueda.anio" />:</label>
														<div class="col-sm-2">
															<select id="sel_anio" class="form-control input-sm">
																<option value=""><spring:message code="select.seleccione" /></option>
																<c:forEach items="${lista_anio}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-2 control-label label-sm"><spring:message code="listar.control.calidad.busqueda.ddi" />:</label>
														<div class="col-sm-2">
															<select id="sel_ddi" class="form-control input-sm">
																<option value=""><spring:message code="select.seleccione" /></option>
																<c:forEach items="${lista_ddi}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-2 control-label label-sm"><spring:message code="listar.control.calidad.busqueda.almacen" />:</label>
														<div class="col-sm-2">
															<select id="sel_almacen" class="form-control input-sm">
																<option value=""><spring:message code="select.seleccione" /></option>
																<c:forEach items="${lista_almacen}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													
													<div class="form-actions">
														<div class="row">
															<div class="col-md-12">
																<button class="btn btn-primary" type="button" id="btn_buscar">
																	<i class="fa fa-search"></i>
																	<spring:message code="button.search" />
																</button>
															</div>
														</div>
													</div>
								
												</div>
												<!-- end widget content -->
								
											</div>
											<!-- end widget div -->
								
										</div>
										<!-- end widget -->
									
									</form>
										
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
