

/* sortData()
 * Sorts the data objects based on the key required
 */
function sortData(arr, key, reverse) {
	sorted = arr.sort(function(a,b){
		  var akey = a[key], bkey = b[key];
		  return akey > bkey ? 1 : akey < bkey ? -1 : 0;
		 });
	if (reverse === true) {
		return sorted.reverse();
	} else {
		return sorted;
	}
}


/* getUrlParams(param)
 * Returns the URL parameter value for the param "param"
 */
function getUrlParams(param) {
	var pageUrl = window.location.search.substring(1);
	var pageVars = pageUrl.split('&');
	for (var i=0; i<pageVars.length; i++) {
		var indPar = pageVars[i].split('=');
		if (indPar[0] == param) {
			return indPar[1];
		}
	}
}

/* textareaWordCount()
 * Count characters in textarea
 */
function textareaWordCount( val ){
    return {
        charactersNoSpaces : val.replace(/\s+/g, '').length,
        characters         : val.length,
        words              : val.match(/\S+/g).length,
        lines              : val.split(/\r*\n/).length
    }
}

/* getUserIdFromToken()
 * Gets the user id after verifying the security token
 */
function getUserIdFromToken() {
	var request = {
			url : 'services/usersession',
			type : 'get',
			async : false,
			cache : false,
			dataType : 'json',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
				'Authorization' : 'UserToken ' + btoa(USER_TOKEN)
			},
			success : function(data) {
				if ((data !== undefined) && (data !== null) && (data > 0)) {
					USER_ID = data;
				}
			}
	}
	$.ajax(request);
}

/* registerUser()
 * Makes an ajax call to add a new user to the database
 */
function registerUser(evt) {
	evt.preventDefault();
	var userName = $("#emailaddress").val();
	var passWord = $("#password").val();
	var firstName = $("#firstname").val();
	var lastName = $("#lastname").val();
	var postData = {
		"username" : userName,
		"password" : passWord,
		"firstname" : firstName,
		"lastname" : lastName
	}
	var jsonData = JSON.stringify(postData);
	var request = {
		url : 'services/users',
		type : 'post',
		data : jsonData,
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		success : function(data) {
			window.location.replace("login-register.jsp?section=login");
		}
	}
	$.ajax(request);
}

/* loginUser()
 * Makes an ajax call to verify a user's credentials against the database entry
 */
function loginUser(evt) {
	evt.preventDefault();
	var userName = $("#emailaddress").val();
	var passWord = $("#password").val();
	var request = {
			url : 'services/users',
			type : 'get',
			async : false,
			cache : false,
			dataType : 'json',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
				'Authorization' : 'Basic ' + btoa(userName + ':' + passWord)
			},
			success : function(data) {
				CURR_DATA = data;
				if (data[0] === undefined) {
					$("#errorlogin").show();
					setTimeout(function(){ $('#errorlogin').fadeOut() }, 2000);
				} else {
					var currToken = {"token": data[0].token, "firstName": data[0].firstname, "lastName": data[0].lastname};
					localStorage.setItem('cmadToken', JSON.stringify(currToken));
					var urlPath = window.location.pathname;
					var refer = document.referrer;
					if (refer === "") {
						refer = "dashboard.jsp";
					} else if (urlPath.match(/login-register.jsp/)) {
						refer = "dashboard.jsp";
					}
					window.location.replace('index.jsp');
				}
			}
	}
	$.ajax(request);
}

/* showLoggedinUser()
 * Processes the logged in user from the session storage
 */
function showLoggedinUser() {
	var urlPath = window.location.pathname;
	var sessionLogin = localStorage.getItem('cmadToken');
    var currSessionToken = $.parseJSON(sessionLogin);
   	if ((currSessionToken !== null) && ('token' in currSessionToken) && (currSessionToken.token !== null)) {
   		$("#registerlink").hide();
		USER_TOKEN = currSessionToken.token;
		if (urlPath.match(/login-register.jsp/)) {
			var refer = document.referrer;
			if (refer === "") {
				refer = "dashboard.jsp";
			}
			window.location.replace(refer);
		} else if (urlPath.match(/dashboard.jsp$/)) {
			profileUpdate();
			myQuestions(USER_ID);
			myAnswers(USER_ID);
		} else if (urlPath.match(/questions.jsp$/)) {
			window.location.replace("index.jsp");
		}
		$(".welcome .specialtoo").html(currSessionToken.firstName + " " + currSessionToken.lastName);
   	} else {	
	    if (urlPath.match(/index.jsp$/)) {
	   		$("#loginlink").html('<a href="login-register.jsp" title="Login">Login</a>');
	   		getQuestions();
		} else if (!urlPath.match(/login-register.jsp$/)) {
			window.location.replace("login-register.jsp");
		}
	    $("#loginlink").siblings().hide();
	    $("#registerlink").show();
   	}
}

/* logoutUser()
 * Logs out the user and clears the session storage
 */
function logoutUser() {
	var sessionLogin = localStorage.getItem('cmadToken');
    var currSessionToken = $.parseJSON(sessionLogin);
    if (('token' in currSessionToken) && (currSessionToken.token !== null)) {
    	USER_TOKEN = '';
    	var request = {
    			url : 'services/usersession/'+currSessionToken.token,
    			type : 'delete',
    			async : false,
       			headers : {
    				'Accept' : 'application/json',
    				'Content-Type' : 'application/json' 
    			}
    	}
    	$.ajax(request);
    }
}

/* profileUpdate()
 * Updating the User Profile
 */
function profileUpdate() {
	getUserIdFromToken();
	if (USER_ID > 0) {
		var request = {
				url : 'services/users/' + USER_ID,
				type : 'get',
				dataType : 'json',
				cache : false,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json',
				},
				success : function(data) {
					CURR_DATA = data;
					fillProfileUpdateForm(data);
				}
		}
		$.ajax(request);
	}
}
function fillProfileUpdateForm(data) {
	$("#profileUpdate #firstname").val(data[0].firstname);
	$("#profileUpdate #lastname").val(data[0].lastname);
	$("#profileUpdate #username").val(data[0].username);
	$("#profileUpdate #password").val(data[0].password);
}
function updateProfile(evt) {
	evt.preventDefault();
	var firstName = $("#firstname").val();
	var lastName = $("#lastname").val();
	var userName = $("#username").val();
	var passWord = $("#password").val();
	getUserIdFromToken();
	if (USER_ID > 0) {
		var postData = {
				"username" : userName,
				"password" : passWord,
				"firstname" : firstName,
				"lastname" : lastName
		}
		console.log(postData);
		var jsonData = JSON.stringify(postData);
		var request = {
			url : 'services/users/'+USER_ID,
			type : 'put',
			data : jsonData,
			async : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			success : function(data) {
				CURR_DATA = data;
				$(".confirmation").show();
			}
		}
		$.ajax(request);
	}
}

/* myQuestions()
 * Populating the myQuestions tab in Dashboard
 */
function myQuestions(userId) {
	var request = {
			url : 'services/questions?userId='+userId,
			type : 'get',
			async : false,
			dataType : 'json',
			cache : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
			},
			success : function(data) {
				CURR_DATA = data;
				html = '';
				for (var ques in data) {
					html = html + '<p><a href="question.jsp?quesId=' + data[ques].quesId +'">' + data[ques].quesTitle +'</a> - Answers (' + data[ques].answers + ')</p>';
				}
				$("#myQuestions").html(html);
			}
	}
	$.ajax(request);
}

/* myAnswers()
 * Populating the myAnswers tab in Dashboard
 */
function myAnswers(userId) {
	var request = {
			url : 'services/answers?userId='+userId,
			type : 'get',
			async : false,
			dataType : 'json',
			cache : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
			},
			success : function(data) {
				CURR_DATA = data;
				html = '';
				for (var ans in data) {
					html = html + '<div><h3><a href="question.jsp?quesId=' + data[ans].quesId +'">' + data[ans].quesTitle + '</a></h3><p><span>Your Answer</span> : ' + data[ans].ansDesc + '</p></div>';
				}
				$("#myAnswers").html(html);
			}
	}
	$.ajax(request);
}

/* displayIndividualQuestionPage()
 * Displays the Question in Individual Page View
 */
function displayQuestionInIndividualPage(quesId) {
	var request = {
			url : 'services/questions/'+quesId,
			type : 'get',
			dataType : 'json',
			cache : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
			},
			success : function(data) {
				CURR_DATA = data;
				$("h2#quesTitle").html(data[0].quesTitle);
				
				var question = '<ul class="allquestions">';
				question = question + '<li class="quesId' + data[0].quesId + '">';
				question = question + '<div class="quesVotes">';
				question = question + '<a href="#" class="quesVote" onclick="questionVotes(event, \'add\', ' + data[0].quesId + ')"><span class="fa fa-thumbs-up fa-lg"></span></a>';
				question = question + '<span class="quesVotesCount">' + data[0].votes + '</span>';
				question = question + '<a href="#" class="quesVote negatecolor" onclick="questionVotes(event, \'minus\', ' + data[0].quesId + ')"><span class="fa fa-thumbs-down fa-lg"></span></a>';
				question = question + '</div>';
				question = question + '<div class="quesArticle"><p>' + data[0].quesDesc + '</p></div>';
				question = question + '</li>';
				question = question + '</ul>';
				//question = question + '<button id="addAnswerForm" onclick="showAnswerForm()">Provide an Answer</button>';
				$("#indquestion").html(question);
				
				generateAnswerForm(data[0].quesId);	
			}
	}
	$.ajax(request);
}
function generateAnswerForm(quesId) {
	html = '<textarea rows="10" cols="30" id="ansDesc"></textarea>';
	html = html + '<p class="smallfont splabel">Max 500 characters allowed</p>';
	html = html + '<input type="submit" id="submit1" onclick="submitAnswer(event, ' + quesId + ')">';
	$("#ansQuestion").html(html);
}
function showAnswerForm() {
	$("#ansQuestion").show();
	$("#addAnswerForm").hide();
}

/* displayAnswersInIndividualPage()
 * Displays the Answers in Individual Page View
 */
function displayAnswersInIndividualPage(quesId) {
	var request = {
			url : 'services/answers?quesId='+quesId,
			type : 'get',
			dataType : 'json',
			cache : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
			},
			success : function(data) {
				CURR_DATA = data;
				displayAnswers(data, quesId);	
			}
	}
	$.ajax(request);
}
function displayAnswers(data, quesId) {
	data = sortData(data, 'createDate', false);
	$("#answers").html("");
	if (data.length == 0) {
		var html = '<h3>No answers yet!</h3>';
	} else {
		var html = '<ul class="allquestions">';
		for (var ans in data) {
			html = html + '<li class="ansId' + data[ans].ansId + '">';
			html = html + '<div class="quesVotes">';
			html = html + '<a href="#" class="quesVote" onclick="answerVotes(event, \'add\', ' + data[ans].ansId + ')"><span class="fa fa-thumbs-up"></span></a>';
			html = html + '<span class="quesVotesCount">' + data[ans].votes + '</span>';
			html = html + '<a href="#" class="quesVote negatecolor" onclick="answerVotes(event, \'minus\', ' + data[ans].ansId + ')"><span class="fa fa-thumbs-down"></span></a>';
			html = html + '</div>';
			html = html + '<div class="quesArticle"><p>' + data[ans].ansDesc + '</p><p class="smallfont">Answered by ' + data[ans].usersName + '</p></div>';
			html = html + '</li>';
		}
		html = html + '</ul>';
	}
	$("#answers").html(html);
	$("#ansCount").html(' (' + data.length + ')');
}


/* getQuestions()
 * Gets the questions
 */
function getQuestions() {
	var request = {
			url : 'services/questions',
			type : 'get',
			async : false,
			dataType : 'json',
			cache : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json',
			},
			success : function(data) {
				CURR_DATA = data;
				displayQuestions(data);
			}
	}
	$.ajax(request);
}
function displayQuestions(data, view, evt) {
	if (evt !== undefined) {
		evt.preventDefault();
	}
	view = typeof view !== undefined ? view : 'createDate';
	if (data === 'current') {
		data = CURR_DATA;
	}
	data = sortData(data, view, true);
	$("#listquestions").html("");
	var html = '<ul class="allquestions">';
	for (var ques in data) {
		html = html + '<li class="quesId' + data[ques].quesId + '">';
		html = html + '<div class="quesVotes">';
		html = html + '<a href="#" class="quesVote" onclick="questionVotes(event, \'add\', ' + data[ques].quesId + ')"><span class="fa fa-thumbs-up"></span></a>';
		html = html + '<span class="quesVotesCount">' + data[ques].votes + '</span>';
		html = html + '<a href="#" class="quesVote negatecolor" onclick="questionVotes(event, \'minus\', ' + data[ques].quesId + ')"><span class="fa fa-thumbs-down"></span></a>';
		html = html + '</div>';
		html = html + '<div class="quesArticle"><h3><a href="question.jsp?quesId=' + data[ques].quesId + '">' + data[ques].quesTitle + '</a></h3><div class="meta"><span class="fa fa-user"></span><span class="metatext"> ' + data[ques].usersName + '</span><span class="fa fa-eye"></span><span class="metatext"> ' + data[ques].views + ' Views</span><span class="fa fa-pencil"></span><a href="question.jsp?quesId=' + data[ques].quesId + '"><span class="metatext"> ' + data[ques].answers + ' Answers</span></a></div><p>' + data[ques].quesDesc + '</p></div>';
		html = html + '</li>';
	}
	html = html + '</ul>';
	$("#listquestions").html(html);
}

/* submitQuestion()
 * Submits a new question 
 */
function submitQuestion(evt) {
	evt.preventDefault();
	var quesTitle = $("#quesTitle").val();
	var quesDesc = $("#quesDesc").val();
	getUserIdFromToken();
	if (USER_ID > 0) {
		var postData = {
			"quesTitle" : quesTitle,
			"quesDesc" : quesDesc,
			"userId" : USER_ID
		}
		var jsonData = JSON.stringify(postData);
		var request = {
			url : 'services/questions',
			type : 'post',
			data : jsonData,
			async : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			success : function(data) {
				$(".confirmation").show();
				$("#quesTitle").val("");
				$("#quesDesc").val("");
			}
		}
		$.ajax(request);
	}
}

/* submitAnswer()
 * Submits a new answer
 */
function submitAnswer(evt, quesId) {
	evt.preventDefault();
	var ansDesc = $("#ansDesc").val();
	getUserIdFromToken();
	if (USER_ID > 0) {
		var postData = {
			"ansDesc" : ansDesc,
			"quesId" : quesId,
			"userId" : USER_ID
		}
		var jsonData = JSON.stringify(postData);
		var request = {
			url : 'services/answers',
			type : 'post',
			data : jsonData,
			async : false,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			success : function(data) {
				$(".confirmation").show();
				$("#ansDesc").val("");
				displayAnswersInIndividualPage(quesId);
			}
		}
		$.ajax(request);
	}
}

/* questionVotes()
 * Handles votes for question 
 */
function questionVotes(evt, op, quesId) {
	evt.preventDefault();
	if ((op === "add") || (op === "minus")) {
		getUserIdFromToken();
		if (USER_ID > 0) {
			var request = {
				url : 'services/questions/'+quesId+'/votes?op='+op,
				type : 'put',
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				success : function(data) {
					var currId = "quesId" + data[0].quesId;
					$("li." + currId + " .quesVotes span.quesVotesCount").html(data[0].votes);
				}
			}
			$.ajax(request);
		}
	}
}

/* questionVotes()
 * Handles votes for question 
 */
function answerVotes(evt, op, ansId) {
	evt.preventDefault();
	if ((op === "add") || (op === "minus")) {
		getUserIdFromToken();
		if (USER_ID > 0) {
			var request = {
				url : 'services/answers/'+ansId+'/votes?op='+op,
				type : 'put',
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				success : function(data) {
					var currId = "ansId" + data[0].ansId;
					$("li." + currId + " .quesVotes span.quesVotesCount").html(data[0].votes);
				}
			}
			$.ajax(request);
		}
	}
}

/* updateViewCount()
 * Updates views for question 
 */
function updateViewCount(quesId) {
	getUserIdFromToken();
	if (USER_ID > 0) {
		var request = {
			url : 'services/questions/'+quesId+'/views?op=add',
			type : 'put',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			}
		}
		$.ajax(request);
	}
}

$(document).ready(function() {
	// Globals
	var USER_TOKEN = "";
	var USER_ID = "";
	var CURR_DATA = "";
	
	var urlPath = window.location.pathname;
	var pageSection = getUrlParams('section');
	var quesId = getUrlParams('quesId');
	
	$("#errorlogin").hide();
	if (pageSection === "register") {
		$(".register").show();
		$(".login").hide();
	} else if (pageSection === "logout") {
		logoutUser();
		localStorage.removeItem("cmadToken");
		window.location.replace("index.jsp");
	} else {
		$(".register").hide();
		$(".login").show();
	}
	$(".confirmation").hide();
	showLoggedinUser();
	
	$(".confirmclose").click(function() {
		$(this).parent().hide();
	})
	
	if ((quesId !== null) && (quesId !== undefined)) {
		displayQuestionInIndividualPage(quesId);
		displayAnswersInIndividualPage(quesId);
	}
	
	$(".tabarea > nav > a").click(function() {
        $(".selected").removeClass("selected");
        $(this).addClass("selected");
        var classList =$(this).attr('class').split(/\s+/);
        $.each( classList, function(index, item){
            if (item.match(/^tab/)) {
               $(".tabarea > .tabs ." + item).addClass("selected");
            }
        });
    });
	
	/*$("#quesTitle").on("keydown keyup click input submit mouseenter", function () {
		var c = this.value.length;
		var ch = 100 - c;
		console.log(ch);
		$("#titleQuestionArea").html('Characters remaining ' + ch);
	    $("div").text(this.value);
	}, false);*/
	
	$("textarea#quesDesc").on('input', function() {
		  var c = textareaWordCount( this.value );
		  var ch = 500 - c.characters;
		  $("p#askQuestionArea").html('Characters remaining ' + ch);
	});
	$("textarea#quesDesc").on('focusout', function() {
		  if ($.trim(this.value).length === 0) {
			  $("p#askQuestionArea").html('Max 500 characters allowed');
		  }
	});
	
});

$(window).load(function() {
	var pathurl = window.location.pathname;
	if (pathurl.match(/question.jsp$/)) {
		var quesId = getUrlParams('quesId');
		if ((quesId !== null) && (quesId !== undefined)) { 
			updateViewCount(quesId);
		}
	} else if (pathurl.match(/index.jsp$/)) {
		getQuestions();
	}
});
