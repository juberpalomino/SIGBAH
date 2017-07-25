<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- RIBBON -->
<div id="ribbon">
	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Programación</li>
		<li>Requerimiento</li>
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
									Datos Generales</a>
								</li>
								<li id="li_alimentarios">
									<a href="#div_damnificados" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down "></i> 
									Damnificados</a>
								</li>
							</ul>
	
							<div id="div_tab_content" class="tab-content padding-10">
								<div class="tab-pane fade in active" id="div_dat_generales">
								
									<form id="frm_dat_generales" class="form-horizontal">
									
										<input type="hidden" id="hid_cod_requerimiento" name="hid_cod_requerimiento">
																				
										<div class="jarviswidget">
											<header>
												<span class="widget-icon"><i class="fa fa-file-text-o"></i></span>
												<h2>Requerimientos BAH</h2>
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-2 control-label">Año:</label>
														<div class="col-sm-2 form-group">
																<input type="text" id="txt_anio" class="form-control" disabled>
														</div>
														
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Requerimiento Nro.:</label>
														<div class="col-sm-2 form-group">
																<input type="text" id="txt_num_requerimiento" class="form-control" >
														</div>
														
														<label class="col-sm-2 control-label">Descripción:</label>
														<div class="col-sm-2 form-group">
																<input type="text" id="txt_descripcion" name="txt_descripcion" class="form-control" >
														</div>
														
													</div>
													
													<div class="row">
														<label class="col-sm-2 control-label">Fecha de requerimiento:</label>
														<div class="col-sm-2 smart-form form-group">
															<label class="input"> 
																<i class="icon-append fa fa-calendar"></i>
																<input type="text" name="txt_fecha_requerimiento" id="txt_fecha_requerimiento" class="datepicker" >
															</label>
														</div>
														
														<label class="col-sm-2 control-label">Region destino:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_region" name="sel_region" class="form-control">
																<c:forEach items="${lista_region}" var="item">
																    <option value="${item.icodigo}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
														
													</div>
														
													<div class="row">
														<label class="col-sm-2 control-label">El requerimiento se hace con SINPAD?:</label>
														<div class="col-sm-1"></div>
														<div class="col-sm-6 form-group">
															<label class="radio radio-inline">
																<input type="radio" name="rb_req_sinpad" value="1">
																Si
															</label>
															
															<label class="radio radio-inline">
																<input type="radio" name="rb_req_sinpad" value="2">
																No 
															</label>																											
														</div>
												
														<label class="col-sm-2 control-label">Fenomeno:</label>
														<div class="col-sm-3 form-group">
															<select id="sel_fenomeno" name="sel_fenomeno" class="form-control">
																<c:forEach items="${lista_fenomeno}" var="item">
																    <option value="${item.icodigo}">${item.descripcion}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													
													<div class="row">
														<div class="col-sm-6 smart-form">
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
								
								<div class="tab-pane fade" id="div_damnificados">
									
									<!-- Widget ID (each widget will need unique ID)-->
									<div class="jarviswidget jarviswidget-color-blueLight">
									
										
										<form id="frm_dat_generales" class="form-horizontal">
									
										<input type="hidden" id="hid_cod_con_calidad" name="hid_cod_con_calidad">
									
										
										
										<div class="form-group"></div>
										
										<div class="form-group">
											
											<label class="col-sm-3 control-label label-bold">N° de requerimiento:</label>
											<div class="col-sm-2">
												<input type="text" id="txt_nro_req" class="form-control" >
											</div>
											<div class="col-sm-2">
												<input type="text" id="txt_des_req" class="form-control" >
											</div>
										</div>		
										<div class="form-actions">
											<div class="row">
												<div class="col-md-12 opc-center">
													<button class="btn btn-primary" type="button" id="btn_agregar_ubigeo">
														<i class="fa fa-floppy-o"></i>
														Agregar ubigeo INEI
													</button>
													
													&nbsp; &nbsp;
													
													<button class="btn btn-primary" type="button" id="btn_agregar_emergencia">
														<i class="fa fa-floppy-o"></i>
														Agregar emergencia del SINPAD
													</button>
												</div>
											</div>
										</div>	
										</form>
										
										<header>
											<span class="widget-icon"> <i class="fa fa-table"></i> </span>
											<h2>Afectados y damnificados según distrito</h2>
											
											<div class="jarviswidget-ctrls" role="menu">   
												<a href="#" id="href_exp_excel" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Exportar Excel">
													<i class="fa fa-file-excel-o"></i>
												</a> 
												<a href="#" id="href_imprimir" class="button-icon" rel="tooltip" title="" data-placement="bottom" 
													data-original-title="Imprimir">
													<i class="fa fa-file-pdf-o"></i>
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

												<table id="tbl_det_afectados" class="table table-bordered table-hover tbl-responsive">
													<thead>			                
														<tr>
															<th></th>
															<th>Nº</th>
															<th>Región</th>
															<th>Provincia</th>
															<th>Distrito</th>
															<th>Código SINPAD</th>
															<th>Pob. INEI</th>
															<th>Fam. Afect.</th>
															<th>Fam. dam</th>
															<th>Total fam.</th>
															<th>Pers. afect.</th>
															<th>Pers. dam.</th>
															<th>Total pers.</th>
															<th>Fam. Afect.</th>
															<th>Fam. dam</th>
															<th>Total fam.</th>
															<th>Pers. afect.</th>
															<th>Pers. dam.</th>
															<th>Total pers.</th>
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
										<button type="button" class="btn btn-primary" id="btn_gra_afectados">
											<i class="fa fa-floppy-o"></i>
											Grabar
										</button>
										
										&nbsp; &nbsp;
										
										<button type="button" class="btn btn-default" data-dismiss="modal" id="btn_can_afectados">
											<i class="fa fa-mail-forward"></i>
											Cancelar
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
									<select id="sel_anio_ubi" name="sel_anio_ubi" class="form-control">
										<c:forEach items="${lista_anio}" var="item">
										    <option value="${item.vcodigo}">${item.descripcion}</option>
										</c:forEach>
									</select>
								</div>
								
								<label class="col-sm-2 control-label">Mes:</label>
								<div class="col-sm-2">
									<select id="sel_mes_ubi" name="sel_mes_ubi" class="form-control">
										<option value="">Todos</option>
										<c:forEach items="${lista_mes}" var="item">
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
							</div>
							<div  class="row">	
							    <label class="col-sm-2 control-label">Provincia:</label>	
								<div class="col-sm-2 form-group">
									<select id="sel_provincia_ubi" name="sel_provincia_ubi" class="form-control">
										<option value="">Todos</option>
									</select>
								</div>																		
								<label class="col-sm-2 control-label">Fenomeno:</label>
								<div class="col-sm-2">
									<select id="sel_fenomeno_ubi" name="sel_fenomeno_ubi" class="form-control">
										<option value="0">Todos</option>
										<c:forEach items="${lista_fenomeno}" var="item">
										    <option value="${item.icodigo}">${item.descripcion}</option>
										</c:forEach>
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
						<div class="widget-body">

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
				<button type="button" class="btn btn-primary" id="btn_gra_alimentario">
					<i class="fa fa-floppy-o"></i>
					Pasar Distritos Seleccionados al  Requerimiento
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
<script> var requerimiento = JSON.parse('${requerimiento}'); </script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/mantenimiento_requerimiento.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/validacion_mantenimiento_requerimiento.js"></script>
