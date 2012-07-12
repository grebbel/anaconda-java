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

<body>
	<div id="container">
		<section class="main">
			<h2>Upload requests</h2>
				<form action="<spring:url value="/upload/requests"/>" enctype="multipart/form-data" method="post">
					<fieldset>
						<legend>Import Analyses</legend>
						<div>
							<label for="requests-file">File</label>
							<input type="file" id="requests-file" name="file" />
						</div>
						<div>
							<label for="delete-existing-requests">Delete Existing</label>
							<input type="checkbox" id="delete-existing-requests" name="deleteExisting" value="true" />
							<label for="delete-existing-requests" class="checkbox"></label>
						</div>
						<div>
							<label>&nbsp;</label>
							<input type="submit" />
						</div>
					</fieldset>
				</form>
			</section>
	</div>


</body>
