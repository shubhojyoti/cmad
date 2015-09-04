package com.kartshub.app;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

@Entity(value = "session", noClassnameStored = true)
public class UserSession {
	@Id
	private ObjectId sessId;
	@Reference
	private User sessionUser;
	private String sessToken;

	public ObjectId getSessId() {
		return sessId;
	}

	public void setSessId(ObjectId sessId) {
		this.sessId = sessId;
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	public String getSessToken() {
		return sessToken;
	}

	public void setSessToken(String sessToken) {
		this.sessToken = sessToken;
	}

}
