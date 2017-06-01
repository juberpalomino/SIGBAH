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
										<th data-hide="phone">ID</th>
										<th data-class="expand"><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> Name</th>
										<th data-hide="phone"><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> Phone</th>
										<th>Company</th>
										<th data-hide="phone,tablet"><i class="fa fa-fw fa-map-marker txt-color-blue hidden-md hidden-sm hidden-xs"></i> Zip</th>
										<th data-hide="phone,tablet">City</th>
										<th data-hide="phone,tablet"><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> Date</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>1</td>
										<td>Jennifer</td>
										<td>1-342-463-8341</td>
										<td>Et Rutrum Non Associates</td>
										<td>35728</td>
										<td>Fogo</td>
										<td>03/04/14</td>
									</tr>
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
<script type="text/javascript">

	$(document).ready(function() {

		$('#frm_elements').bootstrapValidator({
			feedbackIcons : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				txt_field : {
					validators : {
						notEmpty : {
							message : 'The first name is required'
						}
					}
				},
				txt_pas_field : {
					validators : {
						notEmpty : {
							message : 'The last name is required'
						}
					}
				},
				txt_area : {
					validators : {
						notEmpty : {
							message : 'The company name is required'
						}
					}
				}
			}
		});
		
		$('#btn_submit').click(function() {
			
			var bootstrapValidator = $('#frm_elements').data('bootstrapValidator');
			bootstrapValidator.validate();
			if (bootstrapValidator.isValid()) {
				
				alert('procesar logica OK');
				
			}
			
		});
		
	});
	
</script>
		
		
