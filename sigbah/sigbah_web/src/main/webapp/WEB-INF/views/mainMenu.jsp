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
					<li id="li_emer_sinpad"  class="li_emer_sinpad">
						<a href="${pageContext.request.contextPath}/programacion-bath/emergencia/inicio/0">Emergencias en SINPAD</a>
					</li>
					<li id="li_req_edan"  class="li_req_edan">
						<a href="${pageContext.request.contextPath}/programacion-bath/requerimiento/inicio/0"> Requerimientos EDAN</a>
					</li>
					<li id="li_rac_ope" class="li_rac_ope">
						<a href="${pageContext.request.contextPath}/programacion-bath/racion/inicio/0">Raciones Operativas</a>
					</li>
					<li id="li_programacion" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/programacion-bah/programacion/inicio/0">Programación</a>
					</li>
					<li id="li_ped_compra" class="li_ped_compra">
						<a href="${pageContext.request.contextPath}/programacion-bath/pedido/inicio/0">Pedidos de Compra</a>
					</li>
					<li id="li_dec_emer" class="li_dec_emer">
						<a href="${pageContext.request.contextPath}/programacion-bath/decreto/inicio/0">Decretos de emergencia</a>
					</li>
					<li>
						<a href="#">Consultas</a>
						<ul id="ul_consultas_pro">
							<li id="li_con_prog" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/programacion-bath/consulta-programacion/inicio/0">Consulta de programaciones</a>
							</li>
							<li id="li_con_ped" class="li_men_opcion">
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
						<a href="${pageContext.request.contextPath}/gestion-almacenes/control-calidad/inicio">Control de Calidad</a>
					</li>
					<li>
						<a href="#">Ingresos</a>
						<ul id="ul_ord_ingreso">
							<li id="li_ord_ingreso" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/orden-ingreso/inicio">
									<i class="fa fa-caret-right"></i>Orden de Ingreso
								</a>
							</li>
						</ul>
					</li>
					<li>
						<a href="#">Salidas</a>
						<ul id="ul_alm_salidas">
							<li id="li_man_carga" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/proyecto-manifiesto/inicio">
									<i class="fa fa-caret-right"></i>Manifiesto de Carga
								</a>
							</li>
							<li id="li_ord_salida" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/orden-salida/inicio">
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
								<a href="${pageContext.request.contextPath}/gestion-almacenes/cartilla-inventario/inicio/0">
									<i class="fa fa-caret-right"></i>Cartilla de Inventario
								</a>
							</li>
							<li id="li_cie_mensual" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/gestion-almacenes/cierre-stock/inicio/0">
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
					<li id="li_alm_reportes" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/gestion-almacenes/reporte-almacen/inicio">Reportes</a>
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
					
					<li id="li_reg_donaciones_ingresos" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/donacionesIngreso/registro-donacionesIngreso/inicio">Orden de Ingreso</a>
					</li>
					
					<li>
						<a href="#">Salidas</a>
						<ul id="ul_don_salidas">
							<li id="li_reg_donaciones_salidas" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donacionesSalida/registro-donacionesSalida/inicio">
									<i class="fa fa-caret-right"></i>Orden de Salida
								</a>
							</li>
							<li id="li_gui_remision" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/guia-remision/inicio/0">
									<i class="fa fa-caret-right"></i>Guia de Remision
								</a>
							</li>
						</ul>
					</li>
					
					<li>
						<a href="#">Inventarios</a>
						<ul id="ul_don_inventarios">
							<li id="li_reg_donaciones_stock" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/stock-donacion/inicio/0">
									<i class="fa fa-caret-right"></i>Stock de Productos
								</a>
							</li>
							<li id="li_reg_donaciones_cartilla" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/cartilla-inventario/inicio/0">
									<i class="fa fa-caret-right"></i>Cartilla de Inventario
								</a>
							</li>
							<li id="li_reg_donaciones_cierre" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/cierre-stock/inicio/0">
									<i class="fa fa-caret-right"></i>Cierre Mensual
								</a>
							</li>
						</ul>
					</li>
					
					<li>
						<a href="#">Consultas</a>
						<ul id="ul_don_consultas">
							<li id="li_consultas_stock" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/consulta/inicio">
									<i class="fa fa-caret-right"></i>Stock de Productos
								</a>
							</li>
							<li id="li_consultas_historial" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/donaciones/cartilla-inventario/inicio/0">
									<i class="fa fa-caret-right"></i>Historial de Donaciones
								</a>
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
					<li id="li_tablas_generales" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/administracion/tablas-generales/inicioGeneral">Tablas Generales</a>
					</li>
					<li id="li_tablas_ddi" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/administracion/tablas-generales/inicioDdi">Tablas DDI</a>
					</li>
					<li id="li_tablas" class="li_men_opcion">
						<a href="${pageContext.request.contextPath}/administracion/tablas-generales/inicio">Consulta de Tablas</a>
					</li>
					
					<li>
						<a href="#">Catálogo de Productos</a>
						<ul id="ul_adm_catalogo">
							<li id="li_adm_catalogo" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/administracion/catalogo-productos/inicio">
									<i class="fa fa-caret-right"></i>Lista de Productos 
								</a>
							</li>
						</ul>
					</li>
					
					<li>
						<a href="#">Seguridad</a>
						<ul id="ul_adm_seguridad">
							<li id="li_adm_usuarios" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/administracion/usuarios/inicio">
									<i class="fa fa-caret-right"></i>Usuarios
								</a>
							</li>
							<li id="li_adm_roles" class="li_men_opcion">
								<a href="${pageContext.request.contextPath}/administracion/roles/inicio">
									<i class="fa fa-caret-right"></i>Roles
								</a>
							</li>
						</ul>
					</li>
					
					
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