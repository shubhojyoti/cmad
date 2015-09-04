<%@ include file="includes/header.jsp" %>
	<div class="container">
		<h2>Ask a Question</h2>
		<div class="confirmation">Your question was submitted to our database.<span class="close confirmclose fa fa-times-circle-o fa-lg"></span></div>
		<form id="askquestion">
			<input type="text" id="quesTitle" name="quesTitle" size="100" placeholder="What is your Question?">
			<p id="titleQuestionArea" class="smallfont splabel">Max 100 characters allowed</p>
			<p>&nbsp;</p>
			<textarea rows="10" cols="30" id="quesDesc"></textarea>
			<p id="askQuestionArea" class="smallfont splabel">Max 500 characters allowed</p>
			<input type="submit" id="submit1" onclick="submitQuestion(event)">
		</form>
	</div>

<%@ include file="includes/footer.jsp" %>