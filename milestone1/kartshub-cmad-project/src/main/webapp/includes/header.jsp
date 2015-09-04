<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="js/formalize/css/formalize.css" />
<link rel="stylesheet" href="css/font-awesome.min.css">
<link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Vollkorn' rel='stylesheet' type='text/css'>
<script src="js/jquery-2.1.3.js"></script>
<script src="js/formalize/js/jquery.formalize.js"></script>
<script src="js/cmad.js"></script>
<title>CMAD Stack Overflow</title>
</head>
<body>

	<div class="header">
		<div class="container">
			<h1 onclick="location.href='dashboard.jsp';"
				onmouseover="document.body.style.cursor='pointer';"
				onmouseout="document.body.style.cursor='default';">CMAD</h1>
			<p id="ciscologo">
				<img src="images/ciscologo.png" width="72" height="38" alt="Cisco" />
			</p>
		</div>
	</div>
	<div class="topnavigation">
		<div class="navholder">
			<p class="welcome">
				Welcome <span class="specialtoo">Stranger</span>
			</p>
			<ul>
				<li><a href="dashboard.jsp" title="Dashboard">Dashboard</a></li>
				<li><a href="askaquestion.jsp" title="View Questions">Ask a Question</a></li>
				<li><a href="index.jsp" title="View Questions">View Questions</a></li>
				<li id="loginlink"><a href="login-register.jsp?section=logout" title="Logout">Logout</a></li>
				<li id="registerlink"><a href="login-register.jsp?section=register" title="Register">Register</a></li>
			</ul>
		</div>
	</div>