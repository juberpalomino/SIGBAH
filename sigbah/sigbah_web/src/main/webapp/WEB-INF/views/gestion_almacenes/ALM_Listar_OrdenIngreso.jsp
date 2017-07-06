<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Gesti�n de Almacenes</li>
		<li>Lista Orden de Ingreso</li>
	</ol>
	<!-- end breadcrumb -->
</div>
<!-- END RIBBON -->
    
<!-- MAIN CONTENT -->
<div id="content">
	
	<!-- widget grid -->
	<section id="sec_wid_grid" class="">
	
		<!-- row -->
		<div class="row">
		
			<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12">
		
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget">
					<header>
						<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
						<h2>B�squeda de �rdenes de Ingreso</h2>
					</header>
		
					<!-- widget div-->
					<div id="div_wid_bod_bus_con_calidad">
		
						<!-- widget content -->
						<div class="widget-body widget-body-50">
		
							<form id="frm_ord_ingreso" class="form-horizontal">
							
								<input type="hidden" id="hid_codigo" name="hid_codigo">
							
								<div class="row">
									<label class="col-sm-2 control-label">A�o:</label>
									<div class="col-sm-2 form-group">
										<select id="sel_anio" name="sel_anio" class="form-control">
											<c:forEach items="${lista_anio}" var="item">
											    <option value="${item.vcodigo}">${item.descripcion}</option>
											</c:forEach>
										</select>
									</div>
									
									<label class="col-sm-2 control-label">DDI:</label>
									<div class="col-sm-2 form-group">
										<select id="sel_ddi" name="sel_ddi" class="form-control">
											<c:forEach items="${lista_ddi}" var="item">
											    <option value="${item.vcodigo}">${item.descripcion}</option>
											</c:forEach>
										</select>
									</div>
									
									<label class="col-sm-2 control-label">Almac�n:</label>
									<div class="col-sm-2 form-group">
										<select id="sel_almacen" name="sel_almacen" class="form-control">
											<option value="">Todos</option>
											<c:forEach items="${lista_almacen}" var="item">
											    <option value="${item.vcodigo}">${item.descripcion}</option>
											</c:forEach>
										</select>
									</div>
								</div>
									
								<div class="row">
									<label class="col-sm-2 control-label">Tipo Movimiento:</label>
									<div class="col-sm-2 form-group">
										<select id="sel_tip_movimiento" name="sel_tip_movimiento" class="form-control">
											<option value="">Todos</option>
											<c:forEach items="${lista_tipo_movimiento}" var="item">
											    <option value="${item.icodigo}">${item.descripcion}</option>
											</c:forEach>
										</select>
									</div>
									
									<div class="col-sm-2 opc-center">
										<button class="btn btn-primary" type="button" id="btn_buscar">
											<i class="fa fa-search"></i>
											Buscar
										</button>
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
						<h2>Relaci�n de �rdenes de Ingreso</h2>
						
						<div class="jarviswidget-ctrls" role="menu">   
							<a href="#" id="href_exp_excel" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Exportar Excel">
								<i class="fa fa-file-excel-o"></i>
							</a> 
							<a href="#" id="href_imprimir" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Imprimir">
								<i class="fa fa-file-pdf-o"></i>
							</a>
							<a href="#" id="href_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Editar">
								<i class="fa fa-edit"></i>
							</a>
							<a href="#" id="href_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Nuevo">
								<i class="fa fa-file-o"></i>
							</a>
						</div>
					</header>
	
					<!-- widget div-->
					<div>
										
						<!-- widget content -->
						<div class="widget-body">

							<table id="tbl_mnt_ord_ingreso" class="table table-bordered table-hover tbl-responsive">
								<thead>			                
									<tr>
										<th></th>
										<th>A�o</th>
										<th>DDI</th>
										<th>Almac�n</th>
										<th>N� Orden de Ingreso</th>
										<th>Fecha</th>
										<th>Tipo de Movimiento</th>
										<th>Estado</th>
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
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/listar_orden_ingreso.js"></script>
