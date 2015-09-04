<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="./css/style.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="js/formalize/css/formalize.css" />
<link href='http://fonts.googleapis.com/css?family=PT+Sans' rel='stylesheet' type='text/css'>
<script src="js/jquery.js"></script>
<script src="js/formalize/js/jquery.formalize.js"></script>
<script src="js/cmad.js"></script>
<title>CMAD Stack Exchange</title>
</head>
<body>
	<div class="wrapper">
		<div class='login-wrapper'>
			
			<div class="logincontainer">
				<div class="loginbox-top">
					<h2>
						CMAD<span class="special login">&nbsp;Login</span><span class="special register">&nbsp;Register</span>
					</h2>
				</div>
				<form id="login">
					<div class="loginbox-body">
						<div id="errorlogin">Either username or password was wrong</div>
						<div class="register">
							<label for="firstname">First Name</label><br />
							<input type="text" name="firstname" id="firstname" size="25" />
						</div>
						<div class="register">
							<label for="lastname">Last Name</label><br />
							<input type="text" name="lastname" id="lastname" size="25" />
						</div>
						<div>
							<label for="username">Email</label><br />
							<input type="text" name="username" id="emailaddress" size="25" />
						</div>
						<div>
							<label for="password">Password</label><br />
							<input type="password" name="password" id="password" size="25" />
						</div>
					</div>
					<div class="loginbox-bottom">
						<div class="loginbuttons">
							<input type="submit" class="loginbutton login" name="submit" value="Login" onclick="loginUser(event)">
							<input type="submit" class="loginbutton register" name="submit" value="Register" onclick="registerUser(event)">
							<div class="login"><a href="login-register.jsp?section=register">New User? Register Here</a></div>
							<div class="register"><a href="login-register.jsp?section=login">Already have an account? Login Here</a></div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>