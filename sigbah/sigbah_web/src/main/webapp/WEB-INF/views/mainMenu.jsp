<!-- #NAVIGATION -->
<!-- Left panel : Navigation area -->
<!-- Note: This width of the aside area can be adjusted through LESS/SASS variables -->
<aside id="left-panel">

	<nav>
		<ul id="ul_men_opcion">
		
			<li id="li_pro_bah">
				<a href="#">
					<i class="fa fa-lg fa-fw fa-desktop"></i> 
					<span class="menu-item-parent">Programación de BAH</span>
				</a>
				<ul id="ul_pro_bah">
					<li class="li_men_opcion">
						<a href="general-elements.html">Emergencias en SINPAD</a>
					</li>
					<li class="li_men_opcion">
						<a href="buttons.html">Requerimientos EDAN</a>
					</li>
					<li class="li_men_opcion">
						<a href="grid.html">Raciones Operativas</a>
					</li>
					<li class="li_men_opcion">
						<a href="treeview.html">Programación</a>
					</li>
					<li class="li_men_opcion">
						<a href="nestable-list.html">Pedidos de Compra</a>
					</li>
					<li class="li_men_opcion">
						<a href="jqui.html">Consultas</a>
					</li>
				</ul>
			</li>
			
			<li id="li_ges_almacenes">
				<a href="#">
					<i class="fa fa-lg fa-fw fa-pencil-square-o"></i> 
					<span class="menu-item-parent">Gestión de Almacenes</span>
				</a>
				<ul id="ul_ges_almacenes">
					<li id="li_con_calidad" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/gestion-almacenes/control-calidad/inicio">Control de Calidad</a>
					</li>
					<li>
						<a href="#">Ingresos</a>
						<ul>
							<li class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/orden-ingreso/inicio">
									<i class="fa fa-caret-right"></i>Orden de Ingreso
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Salidas</a>
						<ul>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Manifiesto de Carga</a>
							</li>
							<li class="li_men_opcion">
								<a href="glyph.html"><i class="fa fa-caret-right"></i>Orden de Salida</a>
							</li>
							<li class="li_men_opcion">
								<a href="glyph.html"><i class="fa fa-caret-right"></i>Guia de Remision</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Inventarios</a>
						<ul>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Cartilla de Inventario</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Cierre Mensual</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Stock de Productos</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Reportes</a>
						<ul>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Orden de Ingreso</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Orden de Salida</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Guia de Remision</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Manifiesto de Carga</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Acta de Entrega y Recepcion</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Reporte de Control de Calidad</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Kardex / Bincard</a>
							</li>
						</ul>
					</li>
				</ul>
			</li>
		
			<li id="li_donaciones">
				<a href="#">
					<i class="fa fa-lg fa-fw fa-archive"></i> 
					<span class="menu-item-parent">Donaciones</span>
				</a>
				<ul id="ul_donaciones">
					<li id="li_reg_donaciones" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/donaciones/registro-donaciones/inicio">Registro de Donaciones</a>
					</li>
					<li id="li_reg_donaciones_ingresos" >
						<a href="#">Ingresos</a>
						<ul id="ul_reg_donaciones_ingresos">
							<li id="li_reg_orden_ingresos" class="li_men_opcion">							
								<a href="${pageContext.request.contextPath}/donaciones/registro-donaciones/ingreso"><i class="fa fa-caret-right"></i>Orden de Ingreso - Donaciones</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Salidas</a>
						<ul>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Orden Salida - Donaciones</a>
							</li>
							<li class="li_men_opcion">
								<a href="glyph.html"><i class="fa fa-caret-right"></i>Guia de Remisión - Donaciones</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Reportes</a>
						<ul>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Reporte de Donaciones</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Ingreso de Donaciones</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Salida de Donaciones</a>
							</li>
						</ul>
					</li>
				</ul>
			</li>
		
			<li id="li_administracion">
				<a href="#">
					<i class="fa fa-lg fa-fw fa-book"></i> 
					<span class="menu-item-parent">Administración</span>
				</a>
				<ul id="ul_administracion">
					<li class="li_men_opcion">
						<a href="general-elements.html">Parámetros</a>
					</li>
					<li>
						<a href="#">Mantenimiento Tablas</a>
						<ul>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Direcciones Desconcetradas</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Almacenes</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Gobiernos Regionales</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Gobiernos Locales</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Ubigeo</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Jefes de la DDI</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Catalogo de Productos</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Tipos de Movimiento</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Empleados</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Unidades de Medida</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Usuarios del Sistema</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Tipos de Envase</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Marcas</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Donantes</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Empresas de Transportes</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Choferes</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Tablas de Referencia</a>
							</li>
							<li class="li_men_opcion">
								<a href="fa.html"><i class="fa fa-caret-right"></i>Declaratorias de Emergencia</a>
							</li>
						</ul>
					</li>
					<li class="li_men_opcion">
						<a href="general-elements.html">Raciones</a>
					</li>
				</ul>
			</li>
	
		</ul>
	</nav>

	<span class="minifyme" data-action="minifyMenu"> 
		<i class="fa fa-arrow-circle-left hit"></i> 
	</span>

</aside>
<!-- END NAVIGATION -->