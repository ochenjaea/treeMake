<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
    <head>
        <title><tiles:getAsString name="title"/></title>
        <tiles:insertAttribute name="css" />
         <tiles:insertDefinition name="script"/>
    </head>
    <body>
		<tiles:insertAttribute name="header" />
		<tiles:insertAttribute name="body" />
		<tiles:insertAttribute name="footer"/>
    </body>

</html>
