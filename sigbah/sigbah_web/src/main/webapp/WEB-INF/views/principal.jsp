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
		<li>Home</li><li>Tables</li><li>Data Tables</li>
	</ol>
	<!-- end breadcrumb -->
</div>
<!-- END RIBBON -->
    
<!-- MAIN CONTENT -->
<div id="content">  
     
	<div class="row">
		<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
			<h1 class="page-title txt-color-blueDark">
				<i class="fa fa-table fa-fw "></i> 
					Table 
				<span>> 
					Data Tables
				</span>
			</h1>
		</div>
	</div>
	
	<!-- widget grid -->
	<section id="widget-grid" class="">
	
		<!-- row -->
		<div class="row">
	
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
									<tr>
										<td>2</td>
										<td>Clark</td>
										<td>1-516-859-1120</td>
										<td>Nam Ac Inc.</td>
										<td>7162</td>
										<td>Machelen</td>
										<td>03/23/13</td>
									</tr>
									<tr>
										<td>3</td>
										<td>Brendan</td>
										<td>1-724-406-2487</td>
										<td>Enim Commodo Limited</td>
										<td>98611</td>
										<td>Norman</td>
										<td>02/13/14</td>
									</tr>
									<tr>
										<td>4</td>
										<td>Warren</td>
										<td>1-412-485-9725</td>
										<td>Odio Etiam Institute</td>
										<td>10312</td>
										<td>Sautin</td>
										<td>01/01/13</td>
									</tr>
									<tr>
										<td>5</td>
										<td>Rajah</td>
										<td>1-849-642-8777</td>
										<td>Neque Ltd</td>
										<td>29131</td>
										<td>Glovertown</td>
										<td>02/16/13</td>
									</tr>
									<tr>
										<td>6</td>
										<td>Demetrius</td>
										<td>1-470-329-9627</td>
										<td>Euismod In Ltd</td>
										<td>1883</td>
										<td>Kapolei</td>
										<td>03/15/13</td>
									</tr>
									<tr>
										<td>7</td>
										<td>Keefe</td>
										<td>1-188-191-2346</td>
										<td>Molestie Industries</td>
										<td>2211BM</td>
										<td>Modena</td>
										<td>07/08/13</td>
									</tr>
									<tr>
										<td>8</td>
										<td>Leila</td>
										<td>1-663-655-8904</td>
										<td>Est LLC</td>
										<td>75286</td>
										<td>Hondelange</td>
										<td>12/09/12</td>
									</tr>
									<tr>
										<td>9</td>
										<td>Fritz</td>
										<td>1-598-234-7837</td>
										<td>Et Ultrices Posuere Institute</td>
										<td>2324</td>
										<td>Monte San Pietrangeli</td>
										<td>12/29/12</td>
									</tr>
									<tr>
										<td>10</td>
										<td>Cassady</td>
										<td>1-212-965-8381</td>
										<td>Vitae Erat Vel Company</td>
										<td>5898</td>
										<td>Huntly</td>
										<td>10/07/13</td>
									</tr>
									<tr>
										<td>11</td>
										<td>Rogan</td>
										<td>1-541-654-9030</td>
										<td>Iaculis Incorporated</td>
										<td>6464JN</td>
										<td>Carson City</td>
										<td>05/30/13</td>
									</tr>
									<tr>
										<td>12</td>
										<td>Candice</td>
										<td>1-153-708-6027</td>
										<td>Pellentesque Company</td>
										<td>8565</td>
										<td>Redruth</td>
										<td>02/25/14</td>
									</tr>
									<tr>
										<td>13</td>
										<td>Brittany</td>
										<td>1-987-452-6038</td>
										<td>Suspendisse Inc.</td>
										<td>4031</td>
										<td>Carapicuíba</td>
										<td>10/13/13</td>
									</tr>
									<tr>
										<td>14</td>
										<td>Baxter</td>
										<td>1-281-147-5085</td>
										<td>Nulla Donec Non Associates</td>
										<td>53067</td>
										<td>Yellowknife</td>
										<td>08/14/14</td>
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
