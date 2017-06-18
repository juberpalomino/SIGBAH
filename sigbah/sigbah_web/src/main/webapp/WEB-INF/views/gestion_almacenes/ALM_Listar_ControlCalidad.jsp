<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
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
						<h2>Búsqueda de Controles de Calidad</h2>
						
<!-- 						<div id="div_wid_ctrl_bus_con_calidad" class="jarviswidget-ctrls" role="menu">    -->
<!-- 							<a href="#" class="button-icon jarviswidget-toggle-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Collapse"> -->
<!-- 								<i class="fa fa-minus"></i> -->
<!-- 							</a> -->
<!-- 						</div> -->
						
					</header>
		
					<!-- widget div-->
					<div id="div_wid_bod_bus_con_calidad">
		
						<!-- widget content -->
						<div class="widget-body">
		
							<form id="frm_con_calidad" class="form-horizontal">
							
								<div class="form-group">
									<label class="col-sm-2 control-label label-sm">Año:</label>
									<div class="col-sm-2">
										<select id="sel_anio" class="form-control input-sm">
											<option value="">&#60Seleccione&#62</option>
											<c:forEach items="${lista_anio}" var="item">
											    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
											</c:forEach>
										</select>
									</div>
									
									<label class="col-sm-2 control-label label-sm">DDI:</label>
									<div class="col-sm-2">
										<select id="sel_anio" class="form-control input-sm">
											<option value="">&#60Seleccione&#62</option>
											<c:forEach items="${lista_ddi}" var="item">
											    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
											</c:forEach>
										</select>
									</div>
									
									<label class="col-sm-2 control-label label-sm">Almacén:</label>
									<div class="col-sm-2">
										<select id="sel_anio" class="form-control input-sm">
											<option value="">&#60Seleccione&#62</option>
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
												Buscar
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
						<h2>Relación de Controles de Calidad</h2>
						
						<div class="jarviswidget-ctrls" role="menu">   
							<a href="#" class="button-icon" rel="tooltip" title="" data-placement="bottom" data-original-title="Exportar Excel">
								<i class="fa fa-file-excel-o"></i>
							</a> 
							<a href="#" class="button-icon" rel="tooltip" title="" data-placement="bottom" data-original-title="Imprimir">
								<i class="fa fa-file-pdf-o"></i>
							</a>
							<a href="#" id="href_edi_con_calidad" class="button-icon" rel="tooltip" title="" data-placement="bottom" data-original-title="Editar">
								<i class="fa fa-edit"></i>
							</a>
							<a href="#" class="button-icon" rel="tooltip" title="" data-placement="bottom" data-original-title="Nuevo">
								<i class="fa fa-file-o"></i>
							</a>
						</div>
					</header>
	
					<!-- widget div-->
					<div>
					
						<!-- widget edit box -->
						<div class="jarviswidget-editbox">
							<!-- This area used as dropdown edit box -->
							<input class="form-control" type="text">
							<span class="note"><i class="fa fa-check text-success"></i> Change title to update and save instantly!</span>
							
						</div>
						<!-- end widget edit box -->
					
						<!-- widget content -->
						<div class="widget-body">

							<table id="tbl_mnt_con_calidad" class="table table-bordered table-hover tbl-responsive">
								<thead>			                
									<tr>
										<th>Item</th>
										<th>Año</th>
										<th>DDI</th>
										<th>Almacén</th>
										<th>N° Reporte de Control de Calidad</th>
										<th>Fecha</th>
										<th>Tipo de Control</th>
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
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/listar_control_calidad.js"></script>
