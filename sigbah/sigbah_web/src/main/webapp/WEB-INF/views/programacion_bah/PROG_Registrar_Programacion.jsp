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
								<li id="li_alimentos">
									<a href="#div_alimentos" data-toggle="tab"><i class="fa fa-fw fa-lg fa-arrow-circle-o-down"></i> 
									Alimentos</a>
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
										<input type="hidden" id="hid_ind_programacion" name="hid_ind_programacion">
					
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
																    <option value="${item.icodigo}">${item.descripcion}</option>
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
											</header>
								
											<!-- widget div-->
											<div>
								
												<!-- widget content -->
												<div class="widget-body">
								
													<div class="row">
														<label class="col-sm-1 control-label">Almacén:</label>
														<div class="col-sm-2 form-group">
															<select id="sel_almacen" name="sel_almacen" class="form-control">
															</select>
														</div>
														
														<div class="col-sm-2 opc-center">
															<button class="btn btn-primary" type="button" id="btn_alm_agregar">
																<i class="fa fa-plus"></i>
																Agregar Almacén
															</button>
														</div>
													
														<div class="col-sm-2 opc-center">
															<button class="btn btn-danger" type="button" id="btn_alm_eliminar">
																<i class="fa fa-trash-o"></i>
																Eliminar Almacén
															</button>
														</div>
													</div>
													
													<div class="form-group">
														<div class="col-sm-4">
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
														<div class="col-sm-8"></div>
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
								
								<div class="tab-pane fade" id="div_alimentos">
								
									<form class="form-horizontal">
								
										<div class="row">
											<label class="col-sm-2 control-label label-bold">Programación:</label>
											<div class="col-sm-10 form-group">
												<input type="text" id="txt_ali_programacion" class="form-control" disabled>
											</div>
										</div>
																				
										<div class="panel-group smart-accordion-default" id="div_acc_alimentos">
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title">
														<a data-toggle="collapse" data-parent="#div_acc_alimentos" href="#div_col_pro_alimentos">
															<i class="fa fa-fw fa-plus-circle txt-color-green"></i>
															<i class="fa fa-fw fa-minus-circle txt-color-red"></i> 
															Programación Alimentos 
														</a>
													</h4>
												</div>
												
												<div id="div_col_pro_alimentos" class="panel-collapse collapse in">
													<div class="panel-body">
														<div class="row">
															<label class="col-sm-3 control-label label-bold">Productos de la Ración:</label>
															<div class="col-sm-9 form-group">
																<input type="text" id="txt_pro_racion" class="form-control" disabled>
															</div>
														</div>
		
														<table id="tbl_pro_racion" class="table table-bordered table-hover tbl-responsive">
															<thead>			                
																<tr>
																	<th>Nº</th>
																	<th>Producto</th>
																	<th>Gramos por Racion</th>
																	<th>Presentacion</th>
																</tr>
															</thead>
															<tfoot>
																<tr>
																	<td colspan="2">
																		<span class="label-bold" style="float:right;">Total (gr):</span>
																	</td>
																	<td colspan="1">
																		<span id="sp_tot_gr"></span>
																	</td>
																	<td colspan="1"></td>
																</tr>
															</tfoot>
														</table>
														
														<div class="row">
															<label class="col-sm-6 control-label label-bold">Dias de Atención:</label>
															<div class="col-sm-2 form-group">
																<input type="text" id="txt_dia_atencion" class="form-control" disabled>
															</div>
															<div class="col-sm-2 opc-center">
																<button class="btn btn-primary" type="button" id="btn_ali_actualizar">
																	<i class="fa fa-refresh"></i>
																	Actualizar
																</button>
															</div>
														</div>														
													</div>
												</div>
											</div>
											
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title">
														<a data-toggle="collapse" data-parent="#div_acc_alimentos" href="#div_col_det_pro_alimentos" class="collapsed">
															<i class="fa fa-fw fa-plus-circle txt-color-green"></i>
															<i class="fa fa-fw fa-minus-circle txt-color-red"></i> 
															Detalle de la Programación de Alimentos 
														</a>
													</h4>
												</div>
												<div id="div_col_det_pro_alimentos" class="panel-collapse collapse">
													<div class="panel-body">
													
														<div class="row">
															<div class="col-sm-2 opc-center">
																<button class="btn btn-default" type="button" id="btn_ali_exp_excel">
																	<i class="fa fa-file-excel-o"></i>
																	Exportar Excel
																</button>
															</div>
															
															<div class="col-sm-2 opc-center">
																<button class="btn btn-primary" type="button" id="btn_ali_editar">
																	<i class="fa fa-edit"></i>
																	Editar
																</button>
															</div>
															
															<div class="col-sm-2 opc-center">
																<button class="btn btn-danger" type="button" id="btn_ali_eliminar">
																	<i class="fa fa-trash-o"></i>
																	Eliminar
																</button>
															</div>
														</div>
														
														<div class="row">
															<div class="col-sm-12 form-group">&nbsp;</div>
														</div>
														
														<div id="div_det_pro_alimentos" class="table-scroll">													
															<table id="tbl_det_pro_alimentos" class="table table-bordered table-hover tbl-responsive">
																<thead>			                
																	<tr>
																		<th>Sel</th>
																		<th>Nº</th>
																		<th>Departamento</th>
																		<th>Provincia</th>
																		<th>Distrito</th>
																		<th>Pers. Afect.</th>
																		<th>Pers. Dam.</th>
																		<th>Total Pers.</th>
																		<th>Total Raciones</th>
																		
																		<th>Total (TM)</th>
																	</tr>
																</thead>
															</table>
														</div>
														
													</div>
												</div>
											</div>
											
											<div class="panel panel-default">
												<div class="panel-heading">
													<h4 class="panel-title">
														<a data-toggle="collapse" data-parent="#div_acc_alimentos" href="#div_col_res_alimentos" class="collapsed">
															<i class="fa fa-fw fa-plus-circle txt-color-green"></i>
															<i class="fa fa-fw fa-minus-circle txt-color-red"></i> 
															Resumen Alimentos
														</a>
													</h4>
												</div>
												
												<div id="div_col_res_alimentos" class="panel-collapse collapse">
													<div class="panel-body">
														
														<table id="tbl_res_pro_alimentos" class="table table-bordered table-hover tbl-responsive">
															<thead>			                
																<tr>
																	<th>Nº</th>
																	<th>Producto</th>
																	<th>Stock (TM)</th>
																	<th>Total (TM)</th>
																	<th>Saldo (TM)</th>
																</tr>
															</thead>
														</table>
														
													</div>
												</div>
											</div>
										</div>
										
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
										
									</form>	
									
								</div>
								
								<div class="tab-pane fade" id="div_no_alimentarios">
								
									<div class="row">
										<label class="col-sm-2 control-label label-bold">Programación:</label>
										<div class="col-sm-10 form-group">
											<input type="text" id="txt_no_ali_programacion" class="form-control" disabled>
										</div>
									</div>
									
									<div class="panel-group smart-accordion-default" id="div_acc_no_alimentarios">
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#div_acc_no_alimentarios" href="#div_col_pro_no_alimentarios">
														<i class="fa fa-fw fa-plus-circle txt-color-green"></i>
														<i class="fa fa-fw fa-minus-circle txt-color-red"></i> 
														Programación de Bienes No Alimentarios
													</a>
												</h4>
											</div>
											
											<div id="div_col_pro_no_alimentarios" class="panel-collapse collapse in">
												<div class="panel-body">
												
													<div class="row">														
														<div class="col-sm-2 opc-center">
															<button class="btn btn-primary" type="button" id="btn_pro_editar">
																<i class="fa fa-edit"></i>
																Editar
															</button>
														</div>
														
														<div class="col-sm-2 opc-center">
															<button class="btn btn-primary" type="button" id="btn_pro_nuevo">
																<i class="fa fa-file-o"></i>
																Nuevo
															</button>
														</div>
														
														<div class="col-sm-2 opc-center">
															<button class="btn btn-danger" type="button" id="btn_pro_eliminar">
																<i class="fa fa-trash-o"></i>
																Eliminar
															</button>
														</div>
													</div>
													
													<div class="row">
														<div class="col-sm-12 form-group">&nbsp;</div>
													</div>
	
													<table id="tbl_pro_no_alimentarios" class="table table-bordered table-hover tbl-responsive">
														<thead>			                
															<tr>
																<th>Sel</th>
																<th>Nº</th>
																<th>Producto</th>
																<th>Distribuir Por</th>
																<th>Cantidad</th>
															</tr>
														</thead>
													</table>
													
													<div class="row">
														<div class="col-sm-8 form-group"></div>
														<div class="col-sm-2 opc-center">
															<button class="btn btn-primary" type="button" id="btn_no_ali_actualizar">
																<i class="fa fa-refresh"></i>
																Actualizar
															</button>
														</div>
													</div>														
												</div>
											</div>
										</div>
										
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#div_acc_no_alimentarios" href="#div_col_det_pro_no_alimentarios" class="collapsed">
														<i class="fa fa-fw fa-plus-circle txt-color-green"></i>
														<i class="fa fa-fw fa-minus-circle txt-color-red"></i> 
														Detalle de la Programación No Alimentarios 
													</a>
												</h4>
											</div>
											<div id="div_col_det_pro_no_alimentarios" class="panel-collapse collapse">
												<div class="panel-body">
												
													<div class="row">
														<div class="col-sm-2 opc-center">
															<button class="btn btn-default" type="button" id="btn_no_ali_exp_excel">
																<i class="fa fa-file-excel-o"></i>
																Exportar Excel
															</button>
														</div>
														
														<div class="col-sm-2 opc-center">
															<button class="btn btn-primary" type="button" id="btn_no_ali_editar">
																<i class="fa fa-edit"></i>
																Editar
															</button>
														</div>
														
														<div class="col-sm-2 opc-center">
															<button class="btn btn-danger" type="button" id="btn_no_ali_eliminar">
																<i class="fa fa-trash-o"></i>
																Eliminar
															</button>
														</div>
													</div>
													
													<div class="row">
														<div class="col-sm-12 form-group">&nbsp;</div>
													</div>
													
													<div id="div_det_pro_no_alimentarios" class="table-scroll">													
														<table id="div_det_pro_no_alimentarios" class="table table-bordered table-hover tbl-responsive">
															<thead>			                
																<tr>
																	<th>Sel</th>
																	<th>Nº</th>
																	<th>Departamento</th>
																	<th>Provincia</th>
																	<th>Distrito</th>
																	<th>Fam. Afect.</th>
																	<th>Fam. Dam.</th>
																	<th>Total Fam.</th>
																	<th>Pers. Afect.</th>
																	<th>Pers. Dam.</th>
																	<th>Total Pers.</th>
																</tr>
															</thead>
														</table>
													</div>
													
												</div>
											</div>
										</div>
										
										<div class="panel panel-default">
											<div class="panel-heading">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#div_acc_no_alimentarios" href="#div_col_res_no_alimentarios" class="collapsed">
														<i class="fa fa-fw fa-plus-circle txt-color-green"></i>
														<i class="fa fa-fw fa-minus-circle txt-color-red"></i> 
														Resumen Alimentos
													</a>
												</h4>
											</div>
											
											<div id="div_col_res_no_alimentarios" class="panel-collapse collapse">
												<div class="panel-body">
													
													<table id="tbl_res_pro_no_alimentarios" class="table table-bordered table-hover tbl-responsive">
														<thead>			                
															<tr>
																<th>Nº</th>
																<th>Producto</th>
																<th>Stock</th>
																<th>Total</th>
																<th>Saldo</th>
															</tr>
														</thead>
													</table>
													
												</div>
											</div>
										</div>
									</div>
									
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
<div id="div_edi_pro_alimentos" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title label-bold">Actualiza Detalle Programación de Alimentos</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form class="form-horizontal" role="form">

							<div class="row">
								<label class="col-sm-2 control-label">Departamento:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_departamento" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Provincia:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_provincia" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Distrito:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_distrito" class="form-control" disabled>
								</div>
							</div>
							
							<div class="row">
								<label class="col-sm-2 control-label">Pers. Afect.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_per_afect" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Pers. Dam.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_per_dam" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Total Pers.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_tot_pers" class="form-control" disabled>
								</div>
							</div>
							
							<div id="div_ali_unidades"></div>
							
							<div class="row">
								<label class="col-sm-6 control-label">Total (TM):</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_ali_tot_tm" class="form-control" disabled>
								</div>
							</div>
							
						</form>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="btn_gra_pro_alimentos">
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
<div id="div_pro_no_alimentarios" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
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
						<form id="frm_pro_no_alimentarios" class="form-horizontal" role="form">
							
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
								<label class="col-sm-2 control-label">Distribuir:</label>
								<div class="col-sm-6 form-group">
									<label class="radio radio-inline">
										<input type="radio" name="rb_distribuir" value="F">
										Familia
									</label>
									
									<label class="radio radio-inline">
										<input type="radio" name="rb_distribuir" value="P">
										Persona
									</label>																											
								</div>

								<label class="col-sm-2 control-label">Cantidad:</label>
								<div class="col-sm-2 form-group">
									<input type="text" name="txt_no_cantidad" id="txt_no_cantidad" class="form-control only-numbers-format" maxlength="10">
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
<div id="div_edi_pro_no_alimentarios" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" id="btn_clo_pro_no_alimentarios" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title label-bold">Actualiza Detalle Programación No Alimentarios</h4>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<form class="form-horizontal" role="form">

							<div class="row">
								<label class="col-sm-2 control-label">Departamento:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_departamento" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Provincia:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_provincia" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Distrito:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_distrito" class="form-control" disabled>
								</div>
							</div>
							
							<div class="row">
								<label class="col-sm-2 control-label">Fam. Afect.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_fam_afect" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Fam. Dam.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_fam_dam" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Total Fam.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_tot_fam" class="form-control" disabled>
								</div>
							</div>
							
							<div class="row">
								<label class="col-sm-2 control-label">Pers. Afect.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_per_afect" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Pers. Dam.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_per_dam" class="form-control" disabled>
								</div>
								
								<label class="col-sm-2 control-label">Total Pers.:</label>
								<div class="col-sm-2 form-group">
									<input type="text" id="txt_no_ali_tot_pers" class="form-control" disabled>
								</div>
							</div>
							
							<div id="div_no_ali_unidades"></div>
							
<!-- 							<div class="row"> -->
<!-- 								<label class="col-sm-6 control-label">Total (TM):</label> -->
<!-- 								<div class="col-sm-2 form-group"> -->
<!-- 									<input type="text" id="txt_no_ali_tot_tm" class="form-control" disabled> -->
<!-- 								</div> -->
<!-- 							</div> -->
							
						</form>
					</div>
				</div>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="btn_gra_pro_no_alimentarios">
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
<script> var programacion = JSON.parse('${programacion}'); </script>
<script> var listaAlmacenesCache = JSON.parse('${listaAlmacen}'); </script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/mantenimiento_programacion.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/programacion_bah/validacion_mantenimiento_programacion.js"></script>
