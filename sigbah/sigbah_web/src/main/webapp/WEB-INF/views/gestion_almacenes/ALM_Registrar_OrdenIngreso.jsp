<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Gestion de Almacenes</li>
		<li>Ingresos</li>
		<li>NEA</li>
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
	
							<ul id="ul_man_ord_ingreso" class="nav nav-tabs bordered">
								<li id="li_dat_generales" class="active">
									<a href="#div_dat_generales" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Datos Generales</a>
								</li>
								<li id="li_productos">
									<a href="#div_productos" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Productos</a>
								</li>
								<li id="li_documentos">
									<a href="#div_documentos" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Documentos</a>
								</li>
							</ul>
	
							<div id="div_tab_content" class="tab-content padding-10">
								<div class="tab-pane fade in active" id="div_dat_generales">
								
									<form id="frm_dat_generales" class="form-horizontal">
									
										<input type="hidden" id="hid_cod_con_calidad" name="hid_cod_con_calidad">
									
										<div class="header-form opc-center">	
											<strong>Orden Ingreso</strong>
										</div>
										
										<div class="form-group"></div>
										
										<div class="form-group">
											<div class="col-sm-3"></div>
											<label class="col-sm-3 control-label label-bold">N� Orden Ingreso:</label>
											<div class="col-sm-2">
												<input type="text" id="txt_nro_ord_ingreso" class="form-control" disabled>
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
								
													<div class="row">
														<label class="col-sm-2 control-label">A�o:</label>
														<div class="col-sm-2 form-group">
															<input type="text" id="txt_anio" class="form-control" disabled>
														</div>

														<label class="col-sm-2 control-label">DDI:</label>
														<div class="col-sm-2 form-group">
															<input type="text" id="txt_ddi" class="form-control" disabled>
														</div>

														<label class="col-sm-2 control-label">Almac�n:</label>
														<div class="col-sm-2 form-group">
															<input type="text" id="txt_almacen" class="form-control" disabled>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Fecha:</label>
														<div class="col-sm-2 smart-form form-group">
															<label class="input"> 
																<i class="icon-append fa fa-calendar"></i>
																<input type="text" name="txt_fecha" id="txt_fecha" class="datepicker" readonly>
															</label>
														</div>
														
														<label class="col-sm-2 control-label">Estado:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_estado" name="sel_estado" class="form-control">
																<c:forEach items="${lista_estado}" var="item">
																    <option value="${item.vcodigo}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-2 control-label">Tipo Movimiento:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_tip_movimiento" name="sel_tip_movimiento" class="form-control">
																<c:forEach items="${lista_tipo_movimiento}" var="item">
																    <option value="${item.icodigo}">${item.descripcion}</option>
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
												<h2>Datos Orden de Compra</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">		
													
													<div class="row">
														<label class="col-sm-2 control-label">N� orden Compra:</label>
														<div class="col-sm-3 smart-form form-group">
															<select id="sel_nro_ord_compra" name="sel_nro_ord_compra" class="select2 form-control">
																<c:forEach items="${lista_orden_compra}" var="item">
																    <option value="${item.nroOrdenCompra}_${item.concepto}">${item.nroOrdenCompra}</option>
																</c:forEach>
															</select>
														</div>
													
														<div class="col-sm-7 smart-form">
															<section>														
																<label class="textarea textarea-resizable"> 										
																	<textarea rows="2" id="txt_det_ord_compra" class="form-control custom-scroll" disabled></textarea> 
																</label>
															</section>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Compra Por:</label>
														<div class="col-sm-3 smart-form form-group">
															<select id="sel_com_por" name="sel_com_por" class="form-control">
																<option value="">Seleccione</option>
																<option value="1">Emergencia</option>
																<option value="2">Reabastecimiento</option>
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
												<h2>Datos Control de Calidad</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-3 control-label">Tiene N� Rep. Control Calidad:</label>
														
														<div class="col-sm-1"></div>
														
														<div class="col-sm-3 form-group">
															<label class="radio radio-inline">
																<input type="radio" name="rb_tie_nro_rep_con_calidad" value="1">
																Si
															</label>
															
															<label class="radio radio-inline">
																<input type="radio" name="rb_tie_nro_rep_con_calidad" value="0">
																No
															</label>																		
														</div>
														
														<label class="col-sm-2 control-label">N� Control de Calidad:</label>
														<div class="col-sm-3 smart-form form-group">
															<select id="sel_nro_con_calidad" name="sel_nro_con_calidad" class="form-control">
																<c:forEach items="${lista_nro_control_calidad}" var="item">
																    <option value="${item.idControlCalidad}">${item.nroControlCalidad}</option>
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
								
													<div class="row">
														<label class="col-sm-3 control-label">Proveedor:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_proveedor" name="sel_proveedor" class="form-control">
																<option value="">Seleccione</option>
																<c:forEach items="${lista_proveedor}" var="item">
																	<option value="${item.vcodigo}_${item.descripcionCorta}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-3 control-label">Representante:</label>
														<div class="col-sm-3 form-group">
															<input type="text" id="txt_representante" class="form-control" disabled>
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
												<h2>Datos de la Procedencia</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-3 control-label">Almacen:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_almacen" name="sel_almacen" class="form-control">
																<option value="">Seleccione</option>
																<c:forEach items="${lista_almacen}" var="item">
																    <option value="${item.vcodigo}">${item.descripcion}</option>
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
								
													<div class="row">
														<label class="col-sm-3 control-label">Medio de Transporte:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_med_transporte" name="sel_med_transporte" class="form-control">
																<option value="">Seleccione</option>
																<c:forEach items="${lista_medio_transporte}" var="item">
																    <option value="${item.icodigo}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
													
														<label class="col-sm-3 control-label">Empresa de Transporte:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_emp_transporte" name="sel_emp_transporte" class="form-control">
															</select>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-3 control-label">Fecha de Llegada:</label>
														<div class="col-sm-3 smart-form form-group">
															<label class="input"> 
																<i class="icon-append fa fa-calendar"></i>
																<input type="text" name="txt_fec_llegada" id="txt_fec_llegada" class="datepicker" readonly>
															</label>
														</div>
														
														<label class="col-sm-3 control-label">Chofer:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_chofer" name="sel_chofer" class="form-control">
															</select>
														</div>
													</div>
													
													<div class="row">
														<div class="col-sm-6"></div>
													
														<label class="col-sm-3 control-label">N� de Placa:</label>
														<div class="col-sm-3 form-group">
															<input type="text" name="txt_nro_placa" id="txt_nro_placa" class="form-control" maxlength="10">
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
												<h2>Responsable de Recepci�n</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-3 control-label">Responsable:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_responsable" name="sel_responsable" class="form-control">
																<c:forEach items="${lista_personal}" var="item">
																    <option value="${item.vcodigo}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													
													<div class="row">
														<div class="col-sm-12 smart-form">
															<section>														
																<label class="control-label">Observaciones:</label>
																<label class="textarea textarea-resizable"> 										
																	<textarea rows="3" name="txt_observaciones" id="txt_observaciones" 
																		maxlength="500" class="custom-scroll"></textarea> 
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
													
													<button class="btn btn-default btn_retornar" type="button">
														<i class="fa fa-mail-forward"></i>
														Retornar
													</button>
												</div>
											</div>
										</div>				
												
									</form>
										
								</div>
								
								<div class="tab-pane fade" id="div_productos">
									
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

												<table id="tbl_det_productos" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
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
									
									<div class="form-actions">
										<div class="row">
											<div class="col-md-12 opc-center">
												<button class="btn btn-default btn_retornar" type="button">
													<i class="fa fa-mail-forward"></i>
													Retornar
												</button>
											</div>
										</div>
									</div>
									
								</div>
								
								<div class="tab-pane fade" id="div_documentos">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Relaci�n de Documentos</h2>
											
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
									
									<div class="form-actions">
										<div class="row">
											<div class="col-md-12 opc-center">
												<button class="btn btn-default btn_retornar" type="button">
													<i class="fa fa-mail-forward"></i>
													Retornar
												</button>
											</div>
										</div>
									</div>
									
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
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title label-bold" id="h4_tit_alimentarios">Nuevo Producto</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_det_alimentarios" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_producto" name="hid_cod_producto">
						
							<div id="div_pro_det_alimentarios" class="row">																				
								<label class="col-sm-3 control-label">Producto:</label>
								<div class="col-sm-5 smart-form form-group">
									<select id="sel_producto" name="sel_producto" class="select2 form-control ">
										<c:forEach items="${lista_producto}" var="item">
											<option value="${item.idProducto}_${item.nombreUnidadMedida}">${item.nombreProducto}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="row">&nbsp;</div>

							<div class="row">
								<label class="col-sm-3 control-label">Unidad Medida:</label>
								<div class="col-sm-3 form-group">
									<input type="text" id="txt_uni_medida" class="form-control" disabled>
								</div>

								<label class="col-sm-3 control-label">Fecha Vencimiento:</label>
								<div class="col-sm-3 smart-form form-group">
									<label class="input"> 
										<i class="icon-append fa fa-calendar"></i>
										<input type="text" name="txt_fec_vencimiento" id="txt_fec_vencimiento" class="datepicker" readonly>
									</label>
								</div>
							</div>
							
							<div class="row">
								<label class="col-sm-3 control-label">Cantidad de Lote:</label>
								<div class="col-sm-3 form-group">
									<input type="text" name="txt_can_lote" id="txt_can_lote" class="form-control monto-format" maxlength="10">
								</div>

								<label class="col-sm-3 control-label">Cantidad de Muestra:</label>
								<div class="col-sm-3 form-group">
									<input type="text" name="txt_can_muestra" id="txt_can_muestra" class="form-control monto-format" maxlength="10">
								</div>
							</div>
							
							<div class="header-form opc-center">	
								<strong>Verificacion Fisica del Envase</strong>
							</div>
							
							<div class="form-group"></div>
							
							<div class="row">																				
								<label class="col-sm-2 control-label">Primario:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_primario" name="sel_primario" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Olor:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_olor" name="sel_olor" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Textura:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_textura" name="sel_textura" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
							</div>
							
							<div class="row">																				
								<label class="col-sm-2 control-label">Secundario:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_secundario" name="sel_secundario" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Color:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_color" name="sel_color" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Sabor:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_sabor" name="sel_sabor" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
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
				
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_alimentario">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal -->
<div id="div_det_no_alimentarios" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_no_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title label-bold" id="h4_tit_no_alimentarios">Nuevo Producto</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_det_no_alimentarios" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_no_producto" name="hid_cod_no_producto">
						
							<div id="div_pro_det_no_alimentarios" class="row">																				
								<label class="col-sm-3 control-label">Producto:</label>
								<div class="col-sm-6 smart-form form-group">
									<select id="sel_no_producto" name="sel_no_producto" class="select2 form-control">
										<c:forEach items="${lista_producto}" var="item">
											<option value="${item.idProducto}_${item.nombreUnidadMedida}">${item.nombreProducto}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="row">&nbsp;</div>									

							<div class="row">
								<label class="col-sm-3 control-label">Unidad Medida:</label>
								<div class="col-sm-3 form-group">
									<input type="text" id="txt_no_uni_medida" class="form-control" disabled>
								</div>

								<label class="col-sm-3 control-label">Fecha Vencimiento:</label>
								<div class="col-sm-3 smart-form form-group">
									<label class="input"> 
										<i class="icon-append fa fa-calendar"></i>
										<input type="text" name="txt_no_fec_vencimiento" id="txt_no_fec_vencimiento" class="datepicker" readonly>
									</label>
								</div>
							</div>
							
							<div class="row">
								<label class="col-sm-3 control-label">Cantidad de Lote:</label>
								<div class="col-sm-3 form-group">
									<input type="text" name="txt_no_can_lote" id="txt_no_can_lote" class="form-control monto-format" maxlength="10">
								</div>

								<label class="col-sm-3 control-label">Cantidad de Muestra:</label>
								<div class="col-sm-3 form-group">
									<input type="text" name="txt_no_can_muestra" id="txt_no_can_muestra" class="form-control monto-format" maxlength="10">
								</div>
							</div>
							
							<div class="header-form opc-center">	
								<strong>Verificacion Fisica del Envase</strong>
							</div>
							
							<div class="form-group"></div>
							
							<div class="row">																				
								<label class="col-sm-2 control-label">Primario:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_no_primario" name="sel_no_primario" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">T�cnicas:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_no_tecnicas" name="sel_no_tecnicas" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Cumple</option>
										<option value="2">No Cumple</option>
										<option value="3">No Aplica</option>
									</select>
								</div>
							</div>
							
							<div class="row">																				
								<label class="col-sm-2 control-label">Secundario:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_no_secundario" name="sel_no_secundario" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Conformidad:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_no_conformidad" name="sel_no_conformidad" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Cumple</option>
										<option value="2">No Cumple</option>
										<option value="3">No Aplica</option>
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
				
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_no_alimentario">
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
							<input type="hidden" id="hid_cod_act_alfresco" name="hid_cod_act_alfresco">
							<input type="hidden" id="hid_cod_ind_alfresco" name="hid_cod_ind_alfresco">
						
							<div class="form-group">																				
								<label class="col-sm-3 control-label">Tipo Documento:</label>
								<div class="col-sm-8">
									<select id="sel_tip_producto" name="sel_tip_producto" class="form-control">
										<c:forEach items="${lista_tipo_documento}" var="item">
											<option value="${item.vcodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
							</div>								

							<div class="form-group">
								<label class="col-sm-3 control-label">N� Documento:</label>
								<div class="col-sm-4">
									<input type="text" name="txt_nro_documento" id="txt_nro_documento" class="form-control" maxlength="18">
								</div>								
							</div>										

							<div class="form-group">
								<label class="col-sm-3 control-label">Fecha:</label>
								<div class="col-sm-4 smart-form">
									<label class="input"> 
										<i class="icon-append fa fa-calendar"></i>
										<input type="text" name="txt_doc_fecha" id="txt_doc_fecha" class="datepicker" readonly>
									</label>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-3 control-label">Subir Archivo:</label>
								<div class="col-sm-8 smart-form">
									<div class="input input-file">
										<span id="sp_sub_archivo" class="button">
											<input type="file" id="txt_sub_archivo" name="txt_sub_archivo">
											Cargar
										</span>
										<input type="text" id="txt_lee_sub_archivo" name="txt_lee_sub_archivo" readonly>
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
				
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_documento">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- inline scripts related to this page -->
<script> var ordenIngreso = JSON.parse('${ordenIngreso}'); </script>
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/mantenimiento_orden_ingreso.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/gestion_almacenes/validacion_mantenimiento_orden_ingreso.js"></script>
