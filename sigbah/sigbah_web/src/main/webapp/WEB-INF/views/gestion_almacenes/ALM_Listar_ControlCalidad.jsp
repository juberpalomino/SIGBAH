<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Gestión de Almacenes</li>
		<li>Control de Calidad</li>
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
					<header>
						<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
						<h2><spring:message code="listar.control.calidad.busqueda.titulo" /></h2>
					</header>
		
					<!-- widget div-->
					<div id="div_wid_bod_bus_con_calidad">
		
						<!-- widget content -->
						<div class="widget-body">
		
							<form id="frm_con_calidad" class="form-horizontal">
							
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
		
							</form>
		
						</div>
						<!-- end widget content -->
		
					</div>
					<!-- end widget div -->
		
				</div>
				<!-- end widget -->
				
			</article>	
		
	
			<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12">
	
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-blueLight">
				
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i> </span>
						<h2><spring:message code="listar.control.calidad.grilla.titulo" /></h2>
						
						<div class="jarviswidget-ctrls" role="menu">   
							<a href="#" id="href_exp_excel" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="<spring:message code="button.report.xls" />">
								<i class="fa fa-file-excel-o"></i>
							</a> 
							<a href="#" id="href_imprimir" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="<spring:message code="button.print" />">
								<i class="fa fa-file-pdf-o"></i>
							</a>
							<a href="#" id="href_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="<spring:message code="button.edit" />">
								<i class="fa fa-edit"></i>
							</a>
							<a href="#" id="href_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="<spring:message code="button.nuevo" />">
								<i class="fa fa-file-o"></i>
							</a>
						</div>
					</header>
	
					<!-- widget div-->
					<div>
										
						<!-- widget content -->
						<div class="widget-body">

							<table id="tbl_mnt_con_calidad" class="table table-bordered table-hover tbl-responsive">
								<thead>			                
									<tr>
										<th><spring:message code="table.column.item" /></th>
										<th><spring:message code="table.column.anio" /></th>
										<th><spring:message code="listar.control.calidad.grilla.ddi" /></th>
										<th><spring:message code="listar.control.calidad.grilla.almacen" /></th>
										<th><spring:message code="listar.control.calidad.grilla.nro.rep.con.calidad" /></th>
										<th><spring:message code="table.column.fecha" /></th>
										<th><spring:message code="listar.control.calidad.grilla.tipo.control" /></th>
										<th><spring:message code="table.column.estado" /></th>
									</tr>
								</thead>
							</table>

						</div>
						<!-- end widget content -->
	
					</div>
					<!-- end widget div -->
	
				</div>
				<!-- end widget -->

			</article>
			<!-- WIDGET END -->
	
		</div>	
		<!-- end row -->
	
	</section>
	<!-- end widget grid -->
	
</div>
<!-- END MAIN CONTENT -->

<!-- inline scripts related to this page -->
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/listar_control_calidad.js"></script>
