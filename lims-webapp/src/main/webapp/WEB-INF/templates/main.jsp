<%@page session="false" %>
<%@ include file="taglibs.jsp" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:jawr="jawr">
<head th:fragment="head">
  <base href="<%= request.getContextPath() %>/" />
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Anaconda</title>
	<link rel="stylesheet" href="stylesheets/themes/cobalt/jquery-wijmo.css" type="text/css" media="screen, projection"/>
	<link rel="stylesheet" href="stylesheets/screen.css" type="text/css" media="screen, projection" />
	<link rel="stylesheet" href="stylesheets/print.css" type="text/css" media="print" />
	<!--[if IE]>
	<link rel="stylesheet" href=stylesheets/ie.css" type="text/css" media="print" />
	<![endif]-->
  <!--[if lt IE 9]>
  <script src="javascript/ie/html5.js"></script>
  <![endif]-->
</head>

<body class="dashboard standard-layout">

	<div id="container">
	</div>
	
	<hb:scripts locationPattern="/WEB-INF/templates/handlebars/*.html" />
	
	<hb:scripts locationPattern="/WEB-INF/templates/handlebars/admin/*.html" namePrefix="admin." role="ROLE_ADMIN" />

	<hb:scripts locationPattern="/WEB-INF/templates/handlebars/activity/*.html" namePrefix="activity."  />

	<hb:scripts locationPattern="/WEB-INF/templates/handlebars/task/*.html" namePrefix="task."  />
	
	<jwr:script src="/bundles/libs.js" useRandomParam="false" />
	<jwr:script src="/bundles/app.js" useRandomParam="false" />
	<jwr:script src="/bundles/main.js" useRandomParam="false" /> 		
	<jwr:script src="/bundles/init.js" useRandomParam="false" />
	

</body>


</html>