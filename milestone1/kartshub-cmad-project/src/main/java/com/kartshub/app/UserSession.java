package com.kartshub.app;

public class UserSession {
	private int sessId;
	private int userId;
	private String sessToken;

	public int getSessId() {
		return sessId;
	}

	public void setSessId(int sessId) {
		this.sessId = sessId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSessToken() {
		return sessToken;
	}

	public void setSessToken(String sessToken) {
		this.sessToken = sessToken;
	}

}
