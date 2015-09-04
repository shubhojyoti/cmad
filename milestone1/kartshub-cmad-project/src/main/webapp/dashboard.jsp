<%@ include file="includes/header.jsp" %>
	<div class="container">
	
		<div class="tabarea">
            <nav>
                <a href='#' class='tab1 selected' onclick="displayQuestions('current', 'createDate', event);">Update Profile</a>
                <a href='#' class="tab2" onclick="displayQuestions('current', 'votes', event);">My Questions</a>
                <a href='#' class="tab3" onclick="displayQuestions('current', 'answers', event);">My Answers</a>
            </nav>
            <div class="tabs">
                <div class="tab1 selected">
                	<div class="confirmation">Your user profile was updated.<span class="close confirmclose fa fa-times-circle-o fa-lg"></span></div>
                	<form id="profileUpdate">
						<p><label for="firstname">First Name</label><br><input type="text" id="firstname"></p>
						<p><label for="lastname">Last Name</label><br><input type="text" id="lastname"></p>
						<p><label for="username">Username/Email</label><br><input type="text" id="username" disabled></p>
						<p><label for="password">Password</label><br><input type="password" id="password"></p>
						<p><input type="submit" id="submit2" value="Update Profile" onclick="updateProfile(event)"></p>
					</form>
                </div>
                <div class="tab2">
                	<div id="myQuestions"></div>
                </div>
                <div class="tab3">
                	<div id="myAnswers"></div>
                </div>
            </div>
        </div>
        
	</div>

<%@ include file="includes/footer.jsp" %>