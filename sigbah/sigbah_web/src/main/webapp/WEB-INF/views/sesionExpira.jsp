<jsp:root xmlns:tiles="http://tiles.apache.org/tags-tiles" version="2.0" xmlns:c="http://java.sun.com/jsp/jstl/core"
          xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:spring="http://www.springframework.org/tags"
          xmlns:form="http://www.springframework.org/tags/form">
    <jsp:directive.page contentType="text/html;charset=UTF-8"/>
    <jsp:output omit-xml-declaration="yes"/>

    <c:set var="divId" value="wExpira" scope="request"/>
    <c:set var="posiciona" value="width:400px; height:200px;" scope="request"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}" scope="request" />

    <tiles:insertTemplate template="/WEB-INF/templates/wmodaldefault.jspx" flush="true">
        <tiles:putAttribute name="title" value="Sesion Expirada"/>
        <tiles:putAttribute name="body">
            <script languaje="javascript">
                function salir_expira(url) {
                     window.location.replace(url);
                };                
            </script>
            
            <form name="formExpira" action="inicioErr.do" method="POST" id="formExpira">
                <h5><p class="text-danger">Su Sesion ha Expirado por inactividad y/o cierre de session</p></h5> 
            </form>
        </tiles:putAttribute>
        <tiles:putAttribute name="footer">
            <button title ="Regresar a modulo de Inicio" class="btn btn-default btn-lg" onclick="salir_expira('${ctx}/index.jsp');" type="button" value="Inicio">
                <span class="glyphicon glyphicon-log-out"><jsp:text/></span> Inicio</button>
        </tiles:putAttribute>
        
    </tiles:insertTemplate>


</jsp:root>