<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags"	prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="info" property="principal" />
<!DOCTYPE html>
<html>
    <head>
        <title>로그인</title>
        <tiles:insertDefinition name="css" />
         <tiles:insertDefinition name="script"/>
    </head>
    <body>

    <div class="container">
    
    	<tiles:insertAttribute name="body" />
        
    </div>


	</body>

</html>
