<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<div class="main-content-inner">
	<!-- #section:basics/content.breadcrumbs -->
	<div class="breadcrumbs" id="breadcrumbs">
		<ul class="breadcrumb">
			<li class="active">
				<i class="ace-icon fa fa-sign-out"></i>
				Mantenimiento
			</li>
			<li class="active">Mantenimiento Productos</li>
		</ul>
	</div>

	<div class="page-content">		
		<div class="row">
			<div class="col-xs-12">
				<form class="form-horizontal" role="form">
					<input type="hidden" id="hid_financiero">
					
					<fieldset class="fsStyle">
						<legend class="legendStyle">Filtros para la Busqueda de Productos</legend>
							
						<div class="form-group">	
							<div class="col-sm-10">
								<div class="form-group">
									<label class="col-sm-2 control-label label-sm no-padding-right">Codigo:</label>
									<div class="col-sm-3">
										<input type="text" id="txt_codigo" class="form-control input-sm onlyNumbers" maxlength="3" />
									</div>
									<label class="col-sm-2 control-label label-sm no-padding-right">Unid. Medida</label>
									<div class="col-sm-2">
										<select id="sel_uni_medida" class="form-control input-sm">
											<option>Seleccionar</option>
											<c:forEach items="${lis_uni_operativa}" var="item">
											    <option value="${item.cod_mediopagos}">${item.nom_entidad}</option>
											</c:forEach>
										</select>
									</div>
								</div>		
											
								<div class="form-group">
									<label class="col-sm-2 control-label label-sm no-padding-right">Nombre de Producto:</label>
									<div class="col-sm-4">
										<input type="text" id="txt_descripcion" class="form-control input-sm "   maxlength="30" />
									</div>
									<div class="col-sm-6">
										<div class="checkbox">
											<label class="input-sm">
												<input type="checkbox" class="ace input-sm" id="chk_activo" checked />
												<span class="lbl"> Activo</span>
											</label>
										</div>
									</div>	
								</div>
							</div>
								
							<div class="col-sm-2">
								<div class="form-group">	
									<div class="col-sm-12">
										<button id="btn_buscar" class="btn btn-sm btn-info" type="button">
											<i class="ace-icon fa fa-search"></i>
											Buscar
										</button>
									</div>
								</div>
																		
								<div class="form-group">	
									<div class="col-sm-12">
										<button id="btn_nuevo" class="btn btn-sm btn-success" type="button">
											<i class="ace-icon fa fa-plus-square"></i>
											Nuevo
										</button>
									</div>
								</div>
							</div>		
						</div>	
					</fieldset>
					<br>
				</form>
			</div>			
		</div>
		
		<div class="row">
			<div class="col-xs-12">
				<div class="table-responsive div_producto">
					<table id="tbl_producto" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>Item</th>
								<th>Codigo</th>
								<th>Nombre</th>
							</tr>
						</thead>
						<tbody>										
							<c:forEach items="${lis_ubigeo}" var="item">
								<tr>
									<td>${item.idubigeo}</td>
									<td>${item.coddpto}</td>
									<td>${item.nombre}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>