<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<header id="header">

	<div id="logo-group">
		<span id="logo"> 
			<img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="Ingreso al Sistema de Gestión de Bienes de Ayuda Humanitaria - SIGBAH">
		</span>
	</div>
	
	<span class="opc-center"> <br><b>Ingreso al Sistema de Gestión de Bienes de Ayuda Humanitaria - SIGBAH</b> </span>

</header>

<div id="main" role="main">

	<!-- MAIN CONTENT -->
	<div id="content" class="container">

		<div class="row">
			<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 hidden-xs hidden-sm"></div>

			<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
				<div class="well">
					
					<header> Bienvenido, ingresa tu usuario y contraseña aqui. </header>

					<spring:nestedPath path="usuario">
                        <form id="loginForm" class="smart-form client-form" name="loginForm" action="${pageContext.request.contextPath}/login" method="POST" autocomplete="off">
							<fieldset>	
								<section>
									<label class="label">Usuario</label> 
									<label class="input">
										<i class="icon-append fa fa-user"></i> 
										<input type="text" id="usuario" name="usuario" />											
										<b class="tooltip tooltip-top-right">
											<i class="fa fa-user txt-color-teal"></i> 
											Por favor ingrese el usuario
										</b>
									</label>
								</section>
	
								<section>
									<label class="label">Contraseña</label> 
									<label class="input">
										<i class="icon-append fa fa-lock"></i> 
										<input type="password" id="password" name="password"> 
										<b class="tooltip tooltip-top-right">
											<i class="fa fa-lock txt-color-teal"></i> 
											Por favor ingrese la contraseña
										</b>
									</label>
								</section>
							</fieldset>
							
							<footer>
								<button type="submit" class="btn btn-primary">Ingresar</button>
							</footer>
						</form>
                    </spring:nestedPath>
				
				</div>

			</div>
		</div>
	</div>

</div>

<script type="text/javascript">

	$(function() {
		// Validation
		$("#loginForm").validate({
			// Rules for form validation
			rules : {
				usuario : {
					required : true
				},
				password : {
					required : true
				}
			},

			// Messages for form validation
			messages : {
				usuario : {
					required : 'Por favor ingrese el usuario'
				},
				password : {
					required : 'Por favor ingrese la contraseña'
				}
			},

			// Do not change code below
			errorPlacement : function(error, element) {
				error.insertAfter(element.parent());
			}
		});
	});
	
</script>