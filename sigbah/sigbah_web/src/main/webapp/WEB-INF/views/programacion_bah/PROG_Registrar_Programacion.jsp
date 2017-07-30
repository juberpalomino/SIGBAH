<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Programación</li>
		<li>Programación de Requerimientos</li>
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
	
							<ul id="ul_man_programacion" class="nav nav-tabs bordered">
								<li id="li_dat_generales" class="active">
									<a href="#div_dat_generales" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-dow "></i> 
									Datos Generales</a>
								</li>
								<li id="li_alimentarios">
									<a href="#div_alimentarios" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down"></i> 
									Alimentarios</a>
								</li>
								<li id="li_no_alimentarios">
									<a href="#div_no_alimentarios" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down"></i> 
									No Alimentarios</a>
								</li>
								<li id="li_documentos">
									<a href="#div_documentos" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down"></i> 
									Documentos</a>
								</li>
								<li id="li_estados">
									<a href="#div_estados" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down"></i> 
									Estados</a>
								</li>
							</ul>
	
							<div id="div_tab_content" class="tab-content padding-10">
								<div class="tab-pane fade in active" id="div_dat_generales">
								
									<form id="frm_dat_generales" class="form-horizontal">
									
										<input type="hidden" id="hid_cod_programacion" name="hid_cod_programacion">
					
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Datos de Programación</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-2 control-label">Nro Programación:</label>
														<div class="col-sm-2 form-group">
															<input type="text" id="txt_nro_programacion" class="form-control" disabled>
														</div>

														<label class="col-sm-2 control-label">Fecha Programación:</label>
														<div class="col-sm-2 smart-form form-group">
															<label class="input"> 
																<i class="icon-append fa fa-calendar"></i>
																<input type="text" name="txt_fec_programacion" id="txt_fec_programacion" class="datepicker" readonly>
															</label>
														</div>
														
														<label class="col-sm-2 control-label">Estado:</label>
														<div class="col-sm-2 form-group">
															<input type="text" id="txt_estado" class="form-control" disabled>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Descripción:</label>
														<div class="col-sm-10 form-group">
															<input type="text" name="txt_descripcion" id="txt_descripcion" class="form-control">
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">N° de Requerimiento:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_nro_requerimiento" name="sel_nro_requerimiento" class="form-control">
																<c:forEach items="${lista_requerimiento}" var="item">
																    <option value="${item.idRequerimiento}_${item.nomRequerimiento}">${item.codRequerimiento}</option>
																</c:forEach>
															</select>
														</div>
														
														<div class="col-sm-7">
															<input type="text" id="txt_des_requerimiento" class="form-control" disabled>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">N° Ración:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_nro_racion" name="sel_nro_racion" class="form-control">
																<c:forEach items="${lista_racion}" var="item">
																    <option value="${item.idRacionOperativa}_${item.nombreRacion}">${item.codigoRacionOperativa}</option>
																</c:forEach>
															</select>
														</div>
														
														<div class="col-sm-7">
															<input type="text" id="txt_des_racion" class="form-control" disabled>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">N° DEE:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_nro_dee" name="sel_nro_dee" class="form-control">
																<c:forEach items="${lista_dee}" var="item">
																    <option value="${item.icodigo}_${item.descripcion}">${item.descripcionCorta}</option>
																</c:forEach>
															</select>
														</div>
														
														<div class="col-sm-7">
															<input type="text" id="txt_des_nro_dee" class="form-control" disabled>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Region Destino:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_reg_destino" name="sel_reg_destino" class="form-control">
																<c:forEach items="${lista_region}" var="item">
																    <option value="${item.descripcionCorta}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
														
														<label class="col-sm-2 control-label">Atención con:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_ate_con" name="sel_ate_con" class="form-control">
																<option value="1">Alimentos</option>
																<option value="2">Bienes no Alimentarios</option>
																<option value="3">Ambos</option>
															</select>
														</div>
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Observaciones:</label>
														<div class="col-sm-10 smart-form">
															<section>														
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
										
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Almacenes</h2>
												
												<div class="jarviswidget-ctrls" role="menu">   
													<a href="#" id="href_alm_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
														data-original-title="Agregar Almacén">
														<i class="fa fa-file-o"></i>
													</a>
													<a href="#" id="href_alm_eliminar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
														data-original-title="Eliminar Almacén">
														<i class="fa fa-trash-o"></i>
													</a>
												</div>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-3 control-label">Almacén:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_almacen" name="sel_almacen" class="form-control">
															</select>
														</div>
													</div>
													
													<div class="row">
														<div class="col-sm-4 form-group">
															<table id="tbl_det_almacenes" class="table table-bordered table-hover tbl-responsive">
																<thead>			                
																	<tr>
																		<th></th>
																		<th>Nº</th>
																		<th>Almacén</th>
																	</tr>
																</thead>
															</table>
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
															<th>Nº</th>
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
								
								<div class="tab-pane fade" id="div_no_alimentarios">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Detalle de Productos</h2>
											
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
															<th>Nº</th>
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
															<th>Nº</th>
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
								
								<div class="tab-pane fade" id="div_estados">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Relación de Estados</h2>
											
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

												<table id="tbl_det_estados" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
															<th>Nº</th>
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
								<div class="col-sm-5 form-group">
									<select id="sel_producto" name="sel_producto" class="form-control ">
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
							
							<div class="row">
								<div class="col-sm-4 header-modal-form opc-center">
									<strong>Verificacion Fisica del Envase</strong>
								</div>
								<div class="col-sm-8 header-modal-form opc-center">
									<strong>Verificación Física del Producto</strong>
								</div>
							</div>
							
							<div class="form-group"></div>
							
							<div class="row">																				
								<label class="col-sm-2 control-label">Primario:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_primario" name="sel_primario" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
										<option value="3">No Aplica</option>
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
										<option value="3">No Aplica</option>
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
							
							<div class="row">																				
								<label class="col-sm-3 control-label">Categoría de Producto:</label>
								<div class="col-sm-3 form-group">
									<select id="sel_no_cat_producto" name="sel_no_cat_producto" class="form-control">
										<option value="">Seleccione</option>
										<c:forEach items="${lista_categoria}" var="item">
										    <option value="${item.icodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
							</div>
						
							<div id="div_pro_det_no_alimentarios" class="row">																				
								<label class="col-sm-3 control-label">Producto:</label>
								<div class="col-sm-6 form-group">
									<select id="sel_no_producto" name="sel_no_producto" class="form-control">
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
							
							<div class="row">
								<div class="col-sm-4 header-modal-form opc-center">
									<strong>Verificacion Fisica del Envase</strong>
								</div>
								<div class="col-sm-8 header-modal-form opc-center">
									<strong>Verificación Física del Producto</strong>
								</div>
							</div>
							
							<div class="form-group"></div>
							
							<div class="row">																				
								<label class="col-sm-2 control-label">Primario:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_no_primario" name="sel_no_primario" class="form-control">
										<option value="">Seleccione</option>
										<option value="1">Bueno</option>
										<option value="2">Malo</option>
										<option value="3">No Aplica</option>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Técnicas:</label>
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
										<option value="3">No Aplica</option>
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
										<option value="">Seleccione</option>
										<c:forEach items="${lista_tipo_documento}" var="item">
											<option value="${item.vcodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
							</div>								

							<div class="form-group">
								<label class="col-sm-3 control-label">N° Documento:</label>
								<div class="col-sm-4">
									<input type="text" name="txt_nro_documento" id="txt_nro_documento" class="form-control upperValue" maxlength="18">
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
								<div class="col-sm-7 smart-form">
									<div class="input input-file">
										<span id="sp_sub_archivo" class="button">
											<input type="file" id="fil_sub_archivo" name="fil_sub_archivo">
											Cargar
										</span>
										<input type="text" id="txt_lee_sub_archivo" name="txt_lee_sub_archivo" readonly>
									</div>
								</div>
								<div class="col-sm-2">
									<a href="#" id="href_eli_archivo" class="btn btn-default txt-color-red" rel="tooltip" 
										data-placement="right" data-original-title="Eliminar Archivo" data-html="true">
										<i class="fa fa-eraser fa-lg"></i>
									</a>
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
<script> var programacion = JSON.parse('${programacion}'); </script>
<script> var listaAlmacenesCache = JSON.parse('${listaAlmacen}'); </script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/mantenimiento_programacion.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/validacion_mantenimiento_programacion.js"></script>
