<div
    xmlns:tiles        ="http://tiles.apache.org/tags-tiles"
    xmlns:c            ="http://java.sun.com/jsp/jstl/core"
    xmlns:spring    ="http://www.springframework.org/tags"
    xmlns:jsp        ="http://java.sun.com/JSP/Page"
    version            ="3.0">

  <jsp:directive.page contentType="text/html;charset=UTF-8" />
  <jsp:output omit-xml-declaration="yes" />
  <spring:message var="title" code="error_dataaccessfailure_title" htmlEscape="false" />
        <h2>${title}</h2>
        <p>
            <spring:message code="error_dataaccessfailure_problemdescription" />
        </p>
        <c:if test="${not empty exception}">
            <p>
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
            </p>
        </c:if>
</div>
