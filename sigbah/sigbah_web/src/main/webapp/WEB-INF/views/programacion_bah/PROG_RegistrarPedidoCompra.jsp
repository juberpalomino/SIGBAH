<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Programación</li>
		<li>Pedido de compra</li>
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
								<li id="li_dat_generales" class="active">
									<a href="#div_dat_generales" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Registrar pedidos de compra</a>
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
									
										<input type="hidden" id="hid_cod_ped_compra" name="hid_cod_ped_compra">
																				
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Pedidos de compra</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													
													<div class="row">
														<label class="col-sm-2 control-label">Nro. pedido de compra:</label>
														<div class="col-sm-2 form-group">
																<input type="text" id="txt_num_pedido" name="txt_num_pedido"  class="form-control" >
														</div>
													</div>	
													<div class="form-group"></div>
													<div class="row">
														<label class="col-sm-2 control-label">Datos generales de pedido</label>
														
													</div>	
													<div class="form-group"></div>
													<div class="row">
														<label class="col-sm-2 control-label">Fecha de pedido:</label>
														<div class="col-sm-2 smart-form form-group">
															<label class="input"> 
																<i class="icon-append fa fa-calendar"></i>
																<input type="text" name="txt_fecha_pedido" id="txt_fecha_pedido" class="datepicker" >
															</label>
														</div>
														<label class="col-sm-2 control-label">Estado:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_estado" name="sel_estado" class="form-control">
																	<c:forEach items="${lista_estado}" var="item">
																	    <option value="${item.icodigo}">${item.descripcion}</option>
																	</c:forEach>
															</select>
														</div>
													</div>
													<div class="row">
														<label class="col-sm-2 control-label">Pedido por:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_pedidoPor" name="sel_pedidoPor" class="form-control">
																<option value="">Seleccione</option>
																<option value="1">Reabastecimiento</option>
																<option value="2">Emergencia</option>
															</select>
														</div>
													</div>
													<div class="row">
														<label class="col-sm-2 control-label">Descripción:</label>
														<div class="col-sm-6 form-group">
																<input type="text" id="txt_descripcion" name="txt_descripcion" class="form-control" >
														</div>
													</div>
													<div class="row">
														<label class="col-sm-2 control-label">DEE:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_dee" name="sel_dee" class="form-control">
																<option value="">Seleccione</option>
																<c:forEach items="${lista_dee}" var="item">
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
										
										<div class="form-actions">
											<div class="row">
												<div class="col-md-12 opc-center">
													<button class="btn btn-primary" type="button" id="btn_grabar_dat_gen">
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
										<form id="frm_productos" class="form-horizontal">
											<input type="hidden" id="hid_cod_producto" name="hid_cod_producto">
											<div class="form-group"></div>
											<div class="row">
														<label class="col-sm-2 control-label">N° Pedido de compra:</label>
														<div class="col-sm-2 form-group">
																<input type="text" id="txt_desc_pedido" name="txt_desc_pedido"  class="form-control" >
														</div>
											</div>	
											<div class="form-group"></div>
											<div class="row">
												<label class="col-sm-2 control-label">Datos generales de pedido</label>
												
											</div>	
											<div class="form-group"></div>
											<div class="row">
												<label class="col-sm-2 control-label">Categoria de productos:</label>
												<div class="col-sm-2 form-group">
													<select id="sel_cate_prod" name="sel_cate_prod" class="form-control">
														<c:forEach items="${lista_categoria_prod}" var="item">
														    <option value="${item.icodigo}">${item.descripcion}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="row">
												<label class="col-sm-2 control-label">Producto:</label>
												<div class="col-sm-6 form-group">
													<select id="sel_cate_prod" name="sel_cate_prod" class="form-control">
														<c:forEach items="${lista_producto}" var="item">
														    <option value="${item.idProducto}">${item.nombreProducto}</option>
														</c:forEach>
													</select>
												</div>
											</div>
											<div class="row">
												<label class="col-sm-2 control-label">Unidad de medida:</label>
												<div class="col-sm-2 form-group">
														<input type="text" id="txt_unidad_med" name="txt_unidad_med" class="form-control" disabled>
												</div>
											</div>
											<div class="row">
												<label class="col-sm-2 control-label">Cantidad:</label>
												<div class="col-sm-2 form-group">
														<input type="text" id="txt_cantidad" name="txt_cantidad" class="form-control" >
												</div>
												<label class="col-sm-2 control-label">Precio unitario (S/):</label>
												<div class="col-sm-2 form-group">
														<input type="text" id="txt_precio_uni" name="txt_precio_uni" class="form-control" >
												</div>
												<label class="col-sm-2 control-label">Importe Total:</label>
												<div class="col-sm-2 form-group">
														<input type="text" id="txt_importe_tot" name="txt_importe_tot" class="form-control" disabled>
												</div>
											</div>
										<div class="form-actions">
											<div class="row">
												<div class="col-md-12 opc-center">
													<button class="btn btn-primary" type="button" id="btn_grabar_prod">
														<i class="fa fa-floppy-o"></i>
														Grabar
													</button>
													
												</div>
											</div>
										</div>	
										</form>
										
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Productos</h2>
											
											<div class="jarviswidget-ctrls" role="menu">   
												<a href="#" id="href_prod_nuevo" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Nuevo">
													<i class="fa fa-file-o"></i>
												</a>
												<a href="#" id="href_prod_editar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Editar">
													<i class="fa fa-edit"></i>
												</a>
												<a href="#" id="href_prod_eliminar" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Eliminar">
													<i class="fa fa-trash-o"></i>
												</a>
											</div>
										</header>
						
										<!-- widget div-->
										<div>
															
											<!-- widget content -->
											<div class="widget-body">

												<table id="tbl_mnt_productos" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
															<th>Nº</th>
															<th>Producto</th>
															<th>Unidad de medida</th>
															<th>Cantidad</th>
															<th>Precio unitario (S/)</th>
															<th>Importe total (S/)</th>
														</tr>
													</thead>
												</table>

											</div>
											<!-- end widget content -->
						
										</div>
										<!-- end widget div -->
						
									</div>
									<!-- end widget -->
									
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_afectados">
											<i class="fa fa-mail-forward"></i>
											Retornar
										</button>
									</div>
									
								</div>
								
								<div class="tab-pane fade" id="div_documentos">
									<form id="frm_documento" class="form-horizontal">
											<input type="hidden" id="hid_cod_documento" name="hid_cod_documento">
											<div class="form-group"></div>
											<div class="row">
														<label class="col-sm-2 control-label">Pedido de compras:</label>
														<div class="col-sm-2 form-group">
																<input type="text" id="txt_desc_pedido_doc" name="txt_desc_pedido_doc"  class="form-control" >
														</div>
											</div>	
											<div class="form-group"></div>
											<div class="row">
												<label class="col-sm-2 control-label">Registro de Documentos</label>
												
											</div>	
											<div class="form-group"></div>
											
											<div class="row">
												<label class="col-sm-2 control-label">Tipo de documento:</label>
												<div class="col-sm-2 form-group">
													<select id="sel_cate_prod" name="sel_cate_prod" class="form-control">
														<c:forEach items="${lista_tipo_doc}" var="item">
														    <option value="${item.icodigo}">${item.descripcion}</option>
														</c:forEach>
													</select>
												</div>
												
												<label class="col-sm-1 control-label">N° doc.:</label>
												<div class="col-sm-1 form-group">
														<input type="text" id="txt_num_doc" name="txt_num_doc" class="form-control" disabled>
												</div>
												
												<label class="col-sm-1 control-label">Fecha:</label>
												<div class="col-sm-2 smart-form form-group">
													<label class="input"> 
														<i class="icon-append fa fa-calendar"></i>
														<input type="text" name="txt_fecha_doc" id="txt_fecha_doc" class="datepicker" >
													</label>
												</div>
												
												<div class="form-group">
													<label class="col-sm-1 control-label">Subir Archivo:</label>
													<div class="col-sm-2 smart-form">
														<div class="input input-file">
															<span id="sp_sub_archivo" class="button">
																<input type="file" id="fil_sub_archivo" name="fil_sub_archivo">
																Cargar
															</span>
															<input type="text" id="txt_lee_sub_archivo" name="txt_lee_sub_archivo" readonly>
														</div>
													</div>
													<div class="col-sm-1">
														<a href="#" id="href_eli_archivo" class="btn btn-default txt-color-red" rel="tooltip" 
															data-placement="right" data-original-title="Eliminar Archivo" data-html="true">
															<i class="fa fa-eraser fa-lg"></i>
														</a>
													</div>					
												</div>
											</div>
											
										<div class="form-actions">
											<div class="row">
												<div class="col-md-12 opc-center">
													<button class="btn btn-primary" type="button" id="btn_grabar_prod">
														<i class="fa fa-floppy-o"></i>
														Grabar
													</button>
													
												</div>
											</div>
										</div>	
									</form>
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

<!-- Modal  Agregar UBIGEO INEI-->
<div id="div_det_prog_ubigeo" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title label-bold" id="h4_tit_alimentarios">Seleccionar distritos según INEI</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_documento" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_producto" name="hid_cod_producto">
						
							<div class="row">
								<label class="col-sm-1 control-label">Año:</label>
								<div class="col-sm-2 ">
									<select id="sel_anio_ubi" name="sel_anio_ubi" class="form-control">
										<c:forEach items="${lista_anio}" var="item">
										    <option value="${item.vcodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Departamento:</label>
								<div class="col-sm-2">
									<select id="sel_departamento_ubi" name="sel_departamento_ubi" class="form-control">
										<c:forEach items="${lista_departamento}" var="item">
										    <option value="${item.coddpto}">${item.nombre}</option>
										</c:forEach>
									</select>
								</div>
								  <label class="col-sm-1 control-label">Provincia:</label>	
								<div class="col-sm-2 ">
									<select id="sel_provincia_ubi" name="sel_provincia_ubi" class="form-control">
										<option value="">Todos</option>
									</select>
								</div>																		
								
								<div class="col-sm-2">
									<button class="btn btn-primary" type="button" id="btn_aceptar_ubigeo">
										<i class="fa fa-search"></i>
										Aceptar
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>	
				
				<div class="row">&nbsp;</div>	
				<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12">
	
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-blueLight">
				
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i> </span>
						<h2>Distritos de la provincia</h2>
					</header>
	
					<!-- widget div-->
					<div>
										
						<!-- widget content -->
						<div class="widget-body " >

							<table id="tbl_mnt_ubigeo_inei" class="table table-bordered table-hover tbl-responsive">
								<thead>			                
									<tr>
										<th></th>
										<th>Nº</th>
										<th>Ubigeo</th>
										<th>Provincia</th>
										<th>Distrito</th>
										<th>Población INEI</th>
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
			
			<div class="modal-footer">
				<div class="form-group">
					<label class="col-sm-3 control-label label-bold">Total seleccionado:</label>
					<div class="col-sm-2">
						<input type="text" id="txt_nro_selec_ubi" class="form-control" disabled>
					</div>
				</div>		
				<button type="button" class="btn btn-primary" id="btn_pasar_distrito_ubigeo">
					<i class="fa fa-floppy-o"></i>
					Pasar Distritos Seleccionados al  Requerimiento
				</button>
				
				&nbsp; &nbsp;
				
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_distrito_ubigeo">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!-- Modal  Agregar EMERGENCIAS ACTIVAS-->
<div id="div_det_prog_emerg" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title label-bold" id="h4_tit_alimentarios">Emergencias</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form id="frm_det_prog_ubigeo" class="form-horizontal" role="form">
							
							<input type="hidden" id="hid_cod_producto" name="hid_cod_producto">
						
							<div class="row">
								<label class="col-sm-2 control-label">Año:</label>
								<div class="col-sm-2 form-group">
									<select id="sel_anio_emer" name="sel_anio_emer" class="form-control">
										<c:forEach items="${lista_anio}" var="item">
										    <option value="${item.vcodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Mes:</label>
								<div class="col-sm-2">
									<select id="sel_mes_emer" name="sel_mes_emer" class="form-control">
										<option value="">Todos</option>
										<c:forEach items="${lista_mes}" var="item">
										    <option value="${item.vcodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Departamento:</label>
								<div class="col-sm-2">
									<select id="sel_departamento_emer" name="sel_departamento_emer" class="form-control">
										<c:forEach items="${lista_departamento}" var="item">
										    <option value="${item.coddpto}">${item.nombre}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div  class="row">	
							    <label class="col-sm-2 control-label">Provincia:</label>	
								<div class="col-sm-2 form-group">
									<select id="sel_provincia_emer" name="sel_provincia_emer" class="form-control">
										<option value="">Todos</option>
									</select>
								</div>																		
								<label class="col-sm-2 control-label">Fenomeno:</label>
								<div class="col-sm-2">
									<select id="sel_fenomeno_emer" name="sel_fenomeno_emer" class="form-control">
										<option value="0">Todos</option>
										<c:forEach items="${lista_fenomeno}" var="item">
										    <option value="${item.icodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-2">
								<button class="btn btn-primary" type="button" id="btn_aceptar_emer">
									<i class="fa fa-search"></i>
									Aceptar
								</button>
							</div>
							</div>
						</form>
					</div>
				</div>	
				
				<div class="row">&nbsp;</div>	
				<!-- NEW WIDGET START -->
			<article class="col-xs-12 col-sm-12">
	
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-blueLight">
				
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i> </span>
						<h2>Relación de Emergencias</h2>
						
						<div class="jarviswidget-ctrls" role="menu">   
							<a href="#" id="href_emer_acti_exp_excel" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
								data-original-title="Exportar Excel">
								<i class="fa fa-file-excel-o"></i>
							</a> 
						</div>
					</header>
	
					<!-- widget div-->
					<div>
										
						<!-- widget content -->
						<div class="widget-body " >

							<table id="tbl_mnt_emer_act" class="table table-bordered table-hover tbl-responsive">
								<thead>			                
									<tr>
										<th></th>
										<th>Nº</th>
										<th>Año</th>
										<th>Mes</th>
										<th>Fecha</th>
										<th>Código SINPAD</th>
										<th>Fenómeno</th>
										<th>Nombre Emergencias</th>
										<th>Región</th>
										<th>Provincia</th>
										<th>Distrito</th>
										<th>Fam. afect.</th>
										<th>Fam. Dam</th>
										<th>Total Fam.</th>
										<th>Pers. Afect.</th>
										<th>Pers. Dam.</th>
										<th>Total Pers.</th>
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
			
			<div class="modal-footer">
				<div class="form-group">
					<label class="col-sm-3 control-label label-bold">Total seleccionado:</label>
					<div class="col-sm-2">
						<input type="text" id="txt_nro_selec" class="form-control" disabled>
					</div>
				</div>		
				<button type="button" class="btn btn-primary" id="btn_pasar_distrito">
					<i class="fa fa-floppy-o"></i>
					Pasar Distritos Seleccionados al  Requerimiento
				</button>
				
				&nbsp; &nbsp;
				
				<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_emer">
					<i class="fa fa-mail-forward"></i>
					Cancelar
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<!-- inline scripts related to this page -->
<script> var pedido = JSON.parse('${pedido}'); </script>
<script> var lista_pedido = JSON.parse('${lista_pedido}'); </script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/mantenimiento_pedido_compra.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/validacion_mantenimiento_pedidos_compra.js"></script>
