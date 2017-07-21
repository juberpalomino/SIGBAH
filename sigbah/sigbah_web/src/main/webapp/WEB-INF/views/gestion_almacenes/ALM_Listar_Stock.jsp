<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Gesti�n de Almacenes</li>
		<li>Stock</li>
		<li>Lista</li>
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
						<h2>B�squeda de Stock de Productos de Almac�n</h2>
					</header>
		
					<!-- widget div-->
					<div id="div_wid_bod_bus_con_calidad">
		
						<!-- widget content -->
						<div class="widget-body widget-body-50">
		
							<form id="frm_gui_remision" class="form-horizontal">
							
								<input type="hidden" id="hid_codigo" name="hid_codigo">
							
								<div class="row">

									<label class="col-sm-2 control-label">Almac�n:</label>
									<div class="col-sm-2 form-group">
										<select id="sel_almacen" name="sel_almacen" class="form-control">
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
						<h2>Relaci�n de Stock de Productos de Almac�n</h2>
						
						<div class="jarviswidget-ctrls" role="menu">   
							<a href="#" id="href_exp_excel" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Exportar Excel">
								<i class="fa fa-file-excel-o"></i>
							</a> 
							<a href="#" id="href_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Editar">
								<i class="fa fa-edit"></i>
							</a>
						</div>
					</header>
	
					<!-- widget div-->
					<div>
										
						<!-- widget content -->
						<div class="widget-body">

							<table id="tbl_mnt_gui_remision" class="table table-bordered table-hover tbl-responsive">
								<thead>			                
									<tr>
										<th></th>
										<th>N�</th>
										<th>A�o</th>
										<th>Mes</th>
										<th>Fecha</th>
										<th>N� Orden de Salida</th>
										<th>N� Guia de Remisi�n</th>
										<th>N� Manifiesto de Carga</th>
										<th>N� Acta de Entrega Recepci�n</th>
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
<script> var guiaRemision = JSON.parse('${guiaRemision}'); </script>
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/listar_stock_almacen.js"></script>
