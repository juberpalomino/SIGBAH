<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!-- RIBBON -->
<div id="ribbon">
	<span class="ribbon-button-alignment"> 
		<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> Warning! This will reset all your widget settings." data-html="true">
			<i class="fa fa-refresh"></i>
		</span> 
	</span>

	<!-- breadcrumb -->
	<ol class="breadcrumb">
		<li>Maestro</li><li>Inicio</li>
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
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false">
					<header>
						<span class="widget-icon"> <i class="fa fa-eye"></i> </span>
						<h2>Default Elements</h2>
		
					</header>
		
					<!-- widget div-->
					<div>
		
						<!-- widget edit box -->
						<div class="jarviswidget-editbox">
							<!-- This area used as dropdown edit box -->
		
						</div>
						<!-- end widget edit box -->
		
						<!-- widget content -->
						<div class="widget-body">
		
							<form id="frm_elements" class="form-horizontal">
							
								<div class="form-group">
									<label class="col-md-2 control-label">Text field</label>
									<div class="col-md-10">
										<input class="form-control" name="txt_field" placeholder="Default Text Field" type="text">
									</div>
								</div>
		
								<div class="form-group">
									<label class="col-md-2 control-label">Password field</label>
									<div class="col-md-10">
										<input class="form-control" name="txt_pas_field" placeholder="Password field" type="password" value="mypassword">
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-md-2 control-label">Textarea</label>
									<div class="col-md-10">
										<textarea class="form-control" name="txt_area" placeholder="Textarea" rows="4"></textarea>
									</div>
								</div>
								
								<div class="form-actions">
									<div class="row">
										<div class="col-md-12">
											<button class="btn btn-default" type="submit">
												Cancel
											</button>
											<button class="btn btn-primary" type="button" id="btn_submit">
												<i class="fa fa-save"></i>
												Submit
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
			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
	
				<!-- Widget ID (each widget will need unique ID)-->
				<div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
				
					<header>
						<span class="widget-icon"> <i class="fa fa-table"></i> </span>
						<h2>Standard Data Tables </h2>
	
					</header>
	
					<!-- widget div-->
					<div>
	
						<!-- widget edit box -->
						<div class="jarviswidget-editbox">
							<!-- This area used as dropdown edit box -->
	
						</div>
						<!-- end widget edit box -->
	
						<!-- widget content -->
						<div class="widget-body no-padding">
	
							<table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
								<thead>			                
									<tr>
										<th data-hide="phone">Item</th>
										<th data-class="expand"><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> Codigo</th>
										<th data-hide="phone"><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> Descripcion</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${lis_maestro}" var="item">
										<tr>
											<td>${item.idubigeo}</td>
											<td>${item.coddpto}</td>
											<td>${item.nombre}</td>
										</tr>
									</c:forEach>
								</tbody>
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
	
		<!-- end row -->
	
	</section>
	<!-- end widget grid -->
	
</div>
<!-- END MAIN CONTENT -->

<!-- inline scripts related to this page -->
<script src="${pageContext.request.contextPath}/resources/js/modulo/maestro.js"></script>