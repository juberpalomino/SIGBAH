<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>Sistema Sigbah</title>

	<tiles:insertAttribute name="resources" />

</head>
<body class="no-skin">
		
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
	
		<tiles:insertAttribute name="header" />
		
		<div class="main-content">
			
			<tiles:insertAttribute name="body" />
			
		</div><!-- /.main-content -->
		
		<tiles:insertAttribute name="footer" />

	</div><!-- /.main-container -->
		
</body>
</html>
