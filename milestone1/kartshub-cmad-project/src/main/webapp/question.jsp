<%@ include file="includes/header.jsp" %>
	<div class="container">
		<h2 id="quesTitle"></h2>
		<div id="indquestion"></div>
		
		<div class="tabarea">
            <nav>
                <a href='#' class='tab1 selected' onclick="event.preventDefault();">Answers<span id="ansCount"></span></a>
                <a href='#' class="tab2" onclick="event.preventDefault();">Provide an Answer</a>
            </nav>
            <div class="tabs">
                <div class="tab1 selected">
                	<div id="answers"></div>
                </div>
                <div class="tab2">
                	<div class="confirmation">Your answer was submitted to our database.<span class="close confirmclose fa fa-times-circle-o fa-lg"></span></div>
					<form id="ansQuestion"></form>
                </div>
            </div>
        </div>
		
		<!--  <div class="confirmation">Your answer was submitted to our database.<span class="close confirmclose fa fa-times-circle-o fa-lg"></span></div>
		<form id="ansQuestion"></form>
		<div id="answers"></div>-->
	</div>

<%@ include file="includes/footer.jsp" %>