<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Donaciones</li>
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

					<!-- widget div-->
					<div>
	
						<!-- widget content -->
						<div class="widget-body">
	
							<ul id="ul_man_con_calidad" class="nav nav-tabs bordered">
								<li class="active">
									<a href="#div_dat_generales" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Datos Generales</a>
								</li>
								<li>
									<a href="#div_documentos" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Documentos</a>
								</li>
								<li>
									<a href="#div_no_alimentarios" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Productos</a>
								</li>
								
							</ul>
	
							<div id="div_tab_content" class="tab-content padding-10">
								<div class="tab-pane fade in active" id="div_dat_generales">
								
									<form id="frm_dat_generales" class="form-horizontal">
									
										<input type="hidden" id="hid_codigo" value="${codigo}">
											
										<div class="header-form opc-center">	
											<strong>Control de Calidad</strong>
										</div>
										
										<div class="form-group"></div>
										
										<div class="form-group">
											<div class="col-sm-3"></div>
											<label class="col-sm-3 control-label label-bold">N° Control de Calidad:</label>
											<div class="col-sm-2">
												<input type="text" name="txt_nro_con_calidad" name="txt_nro_con_calidad" class="form-control" disabled>
											</div>
										</div>												
																
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Datos Generales</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="form-group">
														<label class="col-sm-2 control-label">Año:</label>
														<label id="lbl_anio" class="col-sm-2 control-label"></label>
														
														<label class="col-sm-2 control-label">DDI:</label>
														<label id="lbl_ddi" class="col-sm-2 control-label"></label>
														
														<label class="col-sm-2 control-label">Almacén:</label>
														<label id="lbl_almacen" class="col-sm-2 control-label"></label>
													</div>
													
													<div class="form-group">
														<label class="col-sm-2 control-label">Fecha:</label>
														<div class="col-sm-2 smart-form">
															<label class="input"> 
																<i class="icon-append fa fa-calendar"></i>
																<input type="text" name="txt_fecha" id="txt_fecha" class="datepicker">
															</label>
														</div>
														
														<label class="col-sm-2 control-label">Estado:</label>
														<div class="col-sm-2">
															<select id="sel_estado" name="sel_estado" class="form-control">
																<c:forEach items="${lista_ddi}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-2 control-label">Nº orden Compra:</label>
														<div class="col-sm-2">
															<select id="sel_nro_ord_compra" name="sel_nro_ord_compra" class="form-control">
																<c:forEach items="${lista_almacen}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													
													<div class="form-group">
														<label class="col-sm-2 control-label">Tipo de Control:</label>
														<div class="col-sm-3">
															<select id="sel_tip_control" name="sel_tip_control" class="form-control">
																<c:forEach items="${lista_almacen}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
								
												</div>
												<!-- end widget content -->
								
											</div>
											<!-- end widget div -->
								
										</div>
										<!-- end widget -->
										
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Datos del Origen / Destino - Reponsables</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="form-group">
														<label class="col-sm-3 control-label">Almacén:</label>
														<div class="col-sm-3">
															<select id="sel_ori_almacen" name="sel_ori_almacen" class="form-control">
																<c:forEach items="${lista_anio}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-3 control-label">Encargado de Almacén:</label>
														<div class="col-sm-3">
															<select id="sel_ori_en_almacen" name="sel_ori_en_almacen" class="form-control">
																<c:forEach items="${lista_ddi}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													
													<div class="form-group">
														<label class="col-sm-9 control-label">Inspector:</label>
														<div class="col-sm-3">
															<select id="sel_inspector" name="sel_inspector" class="form-control">
																<c:forEach items="${lista_almacen}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
								
												</div>
												<!-- end widget content -->
								
											</div>
											<!-- end widget div -->
								
										</div>
										<!-- end widget -->
										
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Datos de Proveedor</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body widget-body-50">
								
													<div class="form-group">
														<label class="col-sm-3 control-label">Proveedor:</label>
														<div class="col-sm-3">
															<select id="sel_proveedor" name="sel_proveedor" class="form-control">
																<c:forEach items="${lista_anio}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-3 control-label">Representante:</label>
														<div class="col-sm-3">
															<select id="sel_representante" name="sel_representante" class="form-control">
																<c:forEach items="${lista_ddi}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
								
												</div>
												<!-- end widget content -->
								
											</div>
											<!-- end widget div -->
								
										</div>
										<!-- end widget -->
										
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Datos del Transporte</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="form-group">
														<label class="col-sm-3 control-label">Empresa de Transporte:</label>
														<div class="col-sm-3">
															<select id="sel_ori_almacen" name="sel_ori_almacen" class="form-control">
																<c:forEach items="${lista_anio}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-3 control-label">Chofer:</label>
														<div class="col-sm-3">
															<select id="sel_ori_en_almacen" name="sel_ori_en_almacen" class="form-control">
																<c:forEach items="${lista_ddi}" var="item">
																    <option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													
													<div class="form-group">
														<label class="col-sm-3 control-label">N° de Placa:</label>
														<div class="col-sm-2">
															<input type="text" name="txt_nro_placa" id="txt_nro_placa" class="form-control">
														</div>
													</div>
								
												</div>
												<!-- end widget content -->
								
											</div>
											<!-- end widget div -->
								
										</div>
										<!-- end widget -->
										
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Productos</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="form-group">
														<label class="col-sm-2 control-label">Tipo de Bien:</label>
														<div class="col-sm-10">
															<div class="inline-group">
																<label class="radio">
																	<input type="radio" name="rb_tip_bien">
																	<i></i>Alimentarios
																</label>
																<label class="radio">
																	<input type="radio" name="rb_tip_bien">
																	<i></i>No Alimentarios
																</label>
															</div>
														</div>
													</div>
													
													<div class="form-group">
														<div class="col-sm-6 smart-form">
															<section>														
																<label class="control-label">Conclusiones:</label>
																<label class="textarea textarea-resizable"> 										
																	<textarea rows="3" name="txt_conclusiones" id="txt_conclusiones" class="custom-scroll"></textarea> 
																</label>
															</section>
														</div>
														
														<div class="col-sm-6 smart-form">
															<section>
																<label class="control-label">Recomendaciones:</label>
																<label class="textarea textarea-resizable"> 										
																	<textarea rows="3" name="txt_recomendaciones" id="txt_recomendaciones" class="custom-scroll"></textarea> 
																</label>
															</section>
														</div>
													</div>	

												</div>
												<!-- end widget content -->
								
											</div>
											<!-- end widget div -->
								
										</div>
										<!-- end widget -->
										
														
										<div class="form-actions">
											<div class="row">
												<div class="col-md-12 opc-center">
													<button class="btn btn-primary" type="button" id="btn_grabar">
														<i class="fa fa-floppy-o"></i>
														Grabar
													</button>
													
													&nbsp; &nbsp;
													
													<button class="btn btn-default" type="button" id="btn_salir">
														<i class="fa fa-mail-forward"></i>
														Retornar
													</button>
												</div>
											</div>
										</div>					
												
									</form>
										
								</div>
								
								<div class="tab-pane fade" id="div_alimentarios">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Detalle de Productos</h2>
											
											<div class="jarviswidget-ctrls" role="menu">   
												<a href="#" id="href_ali_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Nuevo">
													<i class="fa fa-file-o"></i>
												</a>
												<a href="#" id="href_ali_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Editar">
													<i class="fa fa-edit"></i>
												</a>
												<a href="#" id="href_ali_eliminar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Eliminar">
													<i class="fa fa-trash-o"></i>
												</a>
											</div>
										</header>
						
										<!-- widget div-->
										<div>
															
											<!-- widget content -->
											<div class="widget-body">

												<table id="tbl_det_alimentarios" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
															<th>N°<th>
															<th>Producto</th>
															<th>Unidad de Medida</th>
															<th>Lote</th>
															<th>Fecha Vencimiento</th>
															<th>Cantidad de Lote</th>
															<th>Cantidad para la Muestra</th>
															<th>Envase Primario</th>
															<th>Envase Secundario</th>
															<th>Olor</th>
															<th>Color</th>
															<th>Textura</th>
															<th>Sabor</th>
														</tr>
													</thead>
												</table>

											</div>
											<!-- end widget content -->
						
										</div>
										<!-- end widget div -->
						
									</div>
									<!-- end widget -->
									
								</div>
								
								<div class="tab-pane fade" id="div_no_alimentarios">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Productos de la Donación</h2>
											
											<div class="jarviswidget-ctrls" role="menu">   
												<a href="#" id="href_no_ali_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Nuevo">
													<i class="fa fa-file-o"></i>
												</a>
												<a href="#" id="href_no_ali_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Editar">
													<i class="fa fa-edit"></i>
												</a>
												<a href="#" id="href_no_ali_eliminar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Eliminar">
													<i class="fa fa-trash-o"></i>
												</a>
											</div>
										</header>
						
										<!-- widget div-->
										<div>
															
											<!-- widget content -->
											<div class="widget-body">

												<table id="tbl_det_no_alimentarios" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
															<th>N°<th>
															<th>Producto</th>
															<th>Unidad de Medida</th>
															<th>Lote</th>
															<th>Fecha Vencimiento</th>
															<th>Cantidad de Lote</th>
															<th>Cantidad para la Muestra</th>
															<th>Envase Primario</th>
															<th>Especif. Técnicas</th>
															<th>Conformidad</th>
														</tr>
													</thead>
												</table>

											</div>
											<!-- end widget content -->
						
										</div>
										<!-- end widget div -->
						
									</div>
									<!-- end widget -->
									
								</div>
								
								<div class="tab-pane fade" id="div_documentos">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Relación de Documentos</h2>
											
											<div class="jarviswidget-ctrls" role="menu">   
												<a href="#" id="href_doc_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Nuevo">
													<i class="fa fa-file-o"></i>
												</a>
												<a href="#" id="href_doc_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Editar">
													<i class="fa fa-edit"></i>
												</a>
												<a href="#" id="href_doc_eliminar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Eliminar">
													<i class="fa fa-trash-o"></i>
												</a>
											</div>
										</header>
						
										<!-- widget div-->
										<div>
															
											<!-- widget content -->
											<div class="widget-body">

												<table id="tbl_det_documentos" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
															<th>N°<th>
															<th>Tipo Documento</th>
															<th>Nro Documento</th>
															<th>Fecha</th>
														</tr>
													</thead>
												</table>

											</div>
											<!-- end widget content -->
						
										</div>
										<!-- end widget div -->
						
									</div>
									<!-- end widget -->
									
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

<!-- Modal -->
<div id="div_det_alimentarios" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="h4_tit_alimentarios">Registro de Productos</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_det_alimentarios" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_producto" name="hid_cod_producto">
						
							<div class="form-group">																				
								<label class="col-sm-3 control-label">Categoría de productos:</label>
								<div class="col-sm-3">
									<select id="sel_producto" name="sel_producto" class="form-control select2">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>										

							<div class="form-group">
								<label class="col-sm-3 control-label">Unidad Medida:</label>
								<div class="col-sm-3">
									<select id="sel_uni_medida" name="sel_uni_medida" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>

								<label class="col-sm-3 control-label">Fecha Vencimiento:</label>
								<div class="col-sm-3 smart-form">
									<label class="input"> 
										<i class="icon-append fa fa-calendar"></i>
										<input type="text" name="txt_fec_vencimiento" id="txt_fec_vencimiento" class="datepicker">
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Cantidad de Lote:</label>
								<div class="col-sm-3">
									<input type="text" name="txt_can_lote" id="txt_can_lote" class="form-control">
								</div>

								<label class="col-sm-3 control-label">Cantidad de Muestra:</label>
								<div class="col-sm-3">
									<input type="text" name="txt_can_muestra" id="txt_can_muestra" class="form-control">
								</div>
							</div>
							
							<div class="header-form opc-center">	
								<strong>Verificacion Fisica del Envase</strong>
							</div>
							
							<div class="form-group">																				
								<label class="col-sm-2 control-label">Primario:</label>
								<div class="col-sm-2">
									<select id="sel_primario" name="sel_primario" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Olor:</label>
								<div class="col-sm-2">
									<select id="sel_olor" name="sel_olor" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Textura:</label>
								<div class="col-sm-2">
									<select id="sel_textura" name="sel_textura" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group">																				
								<label class="col-sm-2 control-label">Secundario:</label>
								<div class="col-sm-2">
									<select id="sel_secundario" name="sel_secundario" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Color:</label>
								<div class="col-sm-2">
									<select id="sel_color" name="sel_color" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Sabor:</label>
								<div class="col-sm-2">
									<select id="sel_sabor" name="sel_sabor" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
						</form>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="btn_gra_alimentario">
					<i class="fa fa-floppy-o"></i>
					Grabar
				</button>
				
				&nbsp; &nbsp;
				
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div id="div_det_no_alimentarios" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_no_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="h4_tit_no_alimentarios">Nuevo Producto</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_det_no_alimentarios" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_no_producto" name="hid_cod_no_producto">
						
							<div class="form-group">																				
								<label class="col-sm-3 control-label">Producto:</label>
								<div class="col-sm-3">
									<select id="sel_no_producto" name="sel_no_producto" class="form-control select2">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>										

							<div class="form-group">
								<label class="col-sm-3 control-label">Unidad Medida:</label>
								<div class="col-sm-3">
									<select id="sel_no_uni_medida" name="sel_no_uni_medida" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>

								<label class="col-sm-3 control-label">Fecha Vencimiento:</label>
								<div class="col-sm-3 smart-form">
									<label class="input"> 
										<i class="icon-append fa fa-calendar"></i>
										<input type="text" name="txt_no_fec_vencimiento" id="txt_no_fec_vencimiento" class="datepicker">
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Cantidad de Lote:</label>
								<div class="col-sm-3">
									<input type="text" name="txt_no_can_lote" id="txt_no_can_lote" class="form-control">
								</div>

								<label class="col-sm-3 control-label">Cantidad de Muestra:</label>
								<div class="col-sm-3">
									<input type="text" name="txt_no_can_muestra" id="txt_no_can_muestra" class="form-control">
								</div>
							</div>
							
							<div class="header-form opc-center">	
								<strong>Verificacion Fisica del Envase</strong>
							</div>
							
							<div class="form-group">																				
								<label class="col-sm-2 control-label">Primario:</label>
								<div class="col-sm-2">
									<select id="sel_no_primario" name="sel_no_primario" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Técnicas:</label>
								<div class="col-sm-2">
									<select id="sel_no_tecnicas" name="sel_no_tecnicas" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group">																				
								<label class="col-sm-2 control-label">Secundario:</label>
								<div class="col-sm-2">
									<select id="sel_no_secundario" name="sel_no_secundario" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Conformidad:</label>
								<div class="col-sm-2">
									<select id="sel_no_conformidad" name="sel_no_conformidad" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
						</form>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="btn_gra_no_alimentario">
					<i class="fa fa-floppy-o"></i>
					Grabar
				</button>
				
				&nbsp; &nbsp;
				
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div id="div_det_documentos" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_documentos" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="h4_tit_documentos">Nuevo Documento</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_det_documentos" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_documento" name="hid_cod_documento">
						
							<div class="form-group">																				
								<label class="col-sm-3 control-label">Tipo Documento:</label>
								<div class="col-sm-3">
									<select id="sel_tip_producto" name="sel_tip_producto" class="form-control">
										<c:forEach items="${lista_ddi}" var="item">
											<option value="${item.cod_comprobante}">${item.nom_comprobante}</option>
										</c:forEach>
									</select>
								</div>
							</div>										

							<div class="form-group">
								<label class="col-sm-3 control-label">N° Documento:</label>
								<div class="col-sm-3">
									<input type="text" name="txt_nro_documento" id="txt_nro_documento" class="form-control">
								</div>								
							</div>										

							<div class="form-group">
								<label class="col-sm-3 control-label">Fecha:</label>
								<div class="col-sm-3 smart-form">
									<label class="input"> 
										<i class="icon-append fa fa-calendar"></i>
										<input type="text" name="txt_doc_fecha" id="txt_doc_fecha" class="datepicker">
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Subir Archivo:</label>
								<div class="col-sm-3">
									<div class="input input-file">
										<span class="button">
											<input type="file" id="txt_sub_archivo" name="txt_sub_archivo" onchange="this.parentNode.nextSibling.value = this.value">
											Cargar
										</span>
										<input type="text" readonly>
									</div>
								</div>								
							</div>
							
						</form>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="btn_gra_documento">
					<i class="fa fa-floppy-o"></i>
					Grabar
				</button>
				
				&nbsp; &nbsp;
				
				<button type="button" class="btn btn-default" data-dismiss="modal">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- inline scripts related to this page -->
<script src="${pageContext.request.contextPath}/resources/js/donaciones/mantenimiento_donaciones.js"></script>
