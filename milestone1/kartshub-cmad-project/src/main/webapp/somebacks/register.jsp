<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css"
	media="all" />
<link rel="stylesheet" href="js/formalize/css/formalize.css" />
<script src="js/jquery.js"></script>
<script src="js/formalize/js/jquery.formalize.js"></script>
<script src="js/cmad.js"></script>

<title>Signup - CMAD Stack Exchange</title>
</head>
<body id="managepage">

	<div id="header">
		<h1 onclick="location.href='manage.php';"
			onmouseover="document.body.style.cursor='pointer';"
			onmouseout="document.body.style.cursor='default';">Bee Torrent</h1>
		<p id="ciscologo">
			<img src="images/ciscologo.png" width="72" height="38" alt="Cisco" />
		</p>
	</div>

	<div id="container">
		<h2>Register a New User</h2>
		<p class="subhead">
			Fields marked with a <sup class="imp">*</sup> are mandatory
		</p>
		<form id="register">
			<div class="new_user_name">
				<div class="row">
					<div>
						<label for="firstname">First Name:</label><br> <input
							type="text" name="firstname" id="firstname" size="30">
					</div>
					<div>
						<label for="lastname">Last Name:</label><br> <input
							type="text" name="lastname" id="lastname" size="30">
					</div>
				</div>
				<div class="row">
					<div>
						<label for="email">Email Address:&nbsp;<sup class="imp">*</sup></label><br>
						<input type="text" name="email" id="emailaddress" size="30">
					</div>
				</div>
				<div class="row">
					<div>
						<label for="password">Password:&nbsp;<sup class="imp">*</sup></label><br>
						<input type="password" name="password" id="password" size="30">
					</div>
				</div>
				<div class="row">
					<div>
						<input type="button" value="Register User" onclick="registerUser()"></input>
						&nbsp;&nbsp;&nbsp;<input type="reset" value="Reset Form">
					</div>
				</div>
			</div>
		</form>

	</div>

</body>
</html>