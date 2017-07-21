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
					<li class="li_emer_sinpad">
						<a href="${pageContext.request.contextPath}/programacion-bath/emergencia/inicio/0">Emergencias en SINPAD</a>
					</li>
					<li class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/programacion-bath/requerimiento/inicio/0"> Requerimientos EDAN</a>
					</li>
					<li class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/programacion-bath/racion/inicio/0">Raciones Operativas</a>
					</li>
					<li class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/programacion-bath/programacion/inicio/0">Programación</a>
					</li>
					<li class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/programacion-bath/pedido/inicio/0">Pedidos de Compra</a>
					</li>
					<li class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/programacion-bath/decreto/inicio/0">Decretos de emergencia</a>
					</li>
					<li>
						<a href="#">Consultas</a>
						<ul>
							<li class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/programacion-bath/consulta-programacion/inicio/0">Consulta de programaciones</a>
							</li>
							<li class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/programacion-bath/consulta-pedido/inicio/0">Consulta de pedido de compra</a>
							</li>
						</ul>
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
						<a href="${pageContext.request.contextPath}/gestion-almacenes/control-calidad/inicio/0">Control de Calidad</a>
					</li>
					<li>
						<a href="#">Ingresos</a>
						<ul id="ul_ord_ingreso">
							<li id="li_ord_ingreso" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/orden-ingreso/inicio/0">
									<i class="fa fa-caret-right"></i>Orden de Ingreso
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Salidas</a>
						<ul id="ul_alm_salidas">
							<li id="li_man_carga" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/proyecto-manifiesto/inicio/0">
									<i class="fa fa-caret-right"></i>Manifiesto de Carga
								</a>
							</li>
							<li id="li_ord_salida" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/orden-salida/inicio/0">
									<i class="fa fa-caret-right"></i>Orden de Salida
								</a>
							</li>
							<li id="li_gui_remision" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/guia-remision/inicio/0">
									<i class="fa fa-caret-right"></i>Guia de Remision
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Inventarios</a>
						<ul id="ul_alm_inventarios">
							<li id="li_car_inventario" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Cartilla de Inventario
								</a>
							</li>
							<li id="li_cie_mensual" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Cierre Mensual
								</a>
							</li>
							<li id="li_sto_productos" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/stock-almacen/inicio/0">
									<i class="fa fa-caret-right"></i>Stock de Productos
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Reportes</a>
						<ul id="ul_alm_reportes">
							<li id="li_rep_ord_ingreso" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Orden de Ingreso
								</a>
							</li>
							<li id="li_rep_ord_salida" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Orden de Salida
								</a>
							</li>
							<li id="li_rep_gui_remision" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Guia de Remision
								</a>
							</li>
							<li id="li_rep_man_carga" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Manifiesto de Carga
								</a>
							</li>
							<li id="li_rep_act_ent_recepcion" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Acta de Entrega y Recepcion
								</a>
							</li>
							<li id="li_rep_con_calidad" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Reporte de Control de Calidad
								</a>
							</li>
							<li id="li_rep_kar_bincard" class="li_men_opcion">
								<a href="fa.html">
									<i class="fa fa-caret-right"></i>Kardex / Bincard
								</a>
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
								<a href="${pageContext.request.contextPath}/donacionesIngreso/registro-donacionesIngreso/inicio"><i class="fa fa-caret-right"></i>Orden de Ingreso - Donaciones</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Salidas</a>
						<ul>
							<li class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/registro-donaciones/salida"><i class="fa fa-caret-right"></i>Orden Salida - Donaciones</a>
							</li>
							<li class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/registro-donaciones/guiaRemision"><i class="fa fa-caret-right"></i>Guia de Remisión - Donaciones</a>
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