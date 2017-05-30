<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
    
<div class="main-content-inner">
	<h2>${title}</h2>
	<p>
	    <spring:message code="error_resourcenotfound_problemdescription" />
	</p>
	<c:if test="${not empty exception}">
	    <strong>
	        <h4>
	            <spring:message code="exception_details" />
	        </h4>
	
	        <spring:message var="message" code="exception_message" />
	        <div id="_exception">
	            <c:out value="${exception.localizedMessage}" />
	        </div>
	
	        <spring:message var="stacktrace" code="exception_stacktrace" />
	        <div id="_stacktrace">
	            <c:forEach items="${exception.stackTrace}" var="trace">
	              <c:out value="${trace}" />
	              <br />
	            </c:forEach>
	        </div>
	    </strong>
	</c:if>
</div>
  