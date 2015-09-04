package com.kartshub.app;

import java.util.Date;

public class Question {
	private int quesId;
	private String quesTitle;
	private String quesDesc;
	private int userId;
	private int votes;
	private int views;
	private int answers;
	private String usersName;
	private Date createDate = new Date();

	public String getQuesTitle() {
		return quesTitle;
	}

	public void setQuesTitle(String quesTitle) {
		this.quesTitle = quesTitle;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getQuesId() {
		return quesId;
	}

	public void setQuesId(int quesId) {
		this.quesId = quesId;
	}

	public String getQuesDesc() {
		return quesDesc;
	}

	public void setQuesDesc(String quesDesc) {
		this.quesDesc = quesDesc;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getAnswers() {
		return answers;
	}

	public void setAnswers(int answers) {
		this.answers = answers;
	}

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	@Override
	public String toString() {
		return "User [qid=" + quesId + ", title=" + quesTitle + ", desc="
				+ quesDesc + ", userId=" + userId + ", votes=" + votes + "]";
	}

}
