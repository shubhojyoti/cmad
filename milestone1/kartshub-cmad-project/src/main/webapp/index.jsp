<%@ include file="includes/header.jsp" %>
	<div class="container">
		<div class="tabarea">
            <nav>
                <a href='#' class='tab1 selected' onclick="displayQuestions('current', 'createDate', event);">Latest</a>
                <a href='#' class="tab2" onclick="displayQuestions('current', 'votes', event);">Most Votes</a>
                <a href='#' class="tab3" onclick="displayQuestions('current', 'answers', event);">Most Answers</a>
                <a href='#' class="tab4" onclick="displayQuestions('current', 'views', event);">Most Views</a>
            </nav>
        </div>
		<div id="listquestions"></div>
	</div>

<%@ include file="includes/footer.jsp" %>