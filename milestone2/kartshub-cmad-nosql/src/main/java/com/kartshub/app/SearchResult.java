package com.kartshub.app;


public class SearchResult {
	private String quesId = "";
	private String quesTitle = "";
	private String quesDesc = "";
	private String ansId = "";
	private String ansDesc = "";
	private double score = 0.0;

	public String getQuesId() {
		return quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	public String getQuesTitle() {
		return quesTitle;
	}

	public void setQuesTitle(String quesTitle) {
		this.quesTitle = quesTitle;
	}

	public String getQuesDesc() {
		return quesDesc;
	}

	public void setQuesDesc(String quesDesc) {
		this.quesDesc = quesDesc;
	}

	public String getAnsId() {
		return ansId;
	}

	public void setAnsId(String ansId) {
		this.ansId = ansId;
	}

	public String getAnsDesc() {
		return ansDesc;
	}

	public void setAnsDesc(String ansDesc) {
		this.ansDesc = ansDesc;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Result: \nquesId=" + quesId + " \nquesTitle=" + quesTitle + " \nquesDesc=" + quesDesc + " \nansId=" + ansId + " \nansDesc=" + ansDesc;
	}

}
