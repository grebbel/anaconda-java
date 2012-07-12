<%@page session="false" %>
<%@ include file="WEB-INF/templates/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <base href="<%= request.getContextPath() %>/" />
	<meta http-equiv="Content-type" content="text/html; charset=utf-8" />
	<title>Anaconda</title>
	<link rel="stylesheet" href="stylesheets/screen.css" type="text/css" media="screen, projection" />
	<link rel="stylesheet" href="stylesheets/print.css" type="text/css" media="print" />
	<!--[if IE]>
	<link rel="stylesheet" href=stylesheets/ie.css" type="text/css" media="print" />
	<![endif]-->
  <!--[if lt IE 9]>
  <script src="javascript/ie/html5.js"></script>
  <![endif]-->
</head>

<body class="login standard-layout">

	<div id="container">
		<header>
		  <div class="background"></div>
			<h1>Anaconda</h1>
		</header>		
		<div id="main">
		  <div class="background"></div>
			<form action="j_spring_security_check" method="POST">
  		  <h2>Welcome, please log in:</h2>
				<fieldset>
					<label for="username">E-mail</label>
					<input id="username" type="text" name="j_username" value="${param['j_username']}" autofocus="autofocus" maxlength="64" class="text"/>
				</fieldset>
				<fieldset>
					<label for="password">Password</label>					
					<input id="password" type="password" name="j_password" maxlength="64" class="text"/>
				</fieldset>
				<fieldset>
					<input id="remember-me" type="checkbox" name="_spring_security_remember_me" class="first" <%=  (request.getParameter("_spring_security_remember_me") !=null? "checked=\"checked\"" : "") %>>
					<label for="remember-me" class="remember-me">Keep me logged in</label>
				</fieldset>
				<fieldset class="command">
					<input type="submit" value="Login" class="first" />
				</fieldset>
		    <% if (pageContext.findAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) {%>
		      <div class="error message">
		        ${SPRING_SECURITY_LAST_EXCEPTION.message}
		      </div>
		    <% }%>
			</form>
	  </div>
	</div>
	
</body>


</html>