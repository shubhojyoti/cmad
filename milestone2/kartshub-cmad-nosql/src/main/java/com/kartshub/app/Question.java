package com.kartshub.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Transient;
import org.mongodb.morphia.utils.IndexType;

@Entity(value = "question", noClassnameStored = true)
@Indexes({
		@Index(name = "userQuestion", value = "user, quesId"),
		@Index(name = "questionText", fields = {
				@Field(value = "quesTitle", type = IndexType.TEXT, weight = 10),
				@Field(value = "quesDesc", type = IndexType.TEXT, weight = 8) }) })
public class Question {
	@Id
	private ObjectId quesId;
	private String quesTitle;
	private String quesDesc;
	@Indexed
	@Reference
	private User user;
	private int votes = 0;
	private int views = 0;
	@Reference
	private List<Tag> tags;
	@Transient
	private int answers;
	@Transient
	private String usersName;
	@Transient
	private String quesIdString;
	private Date createDate = new Date();
	private List<String> subscribers = new ArrayList<String>();

	public ObjectId getQuesId() {
		return quesId;
	}

	public void setQuesId(ObjectId quesId) {
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getQuesIdString() {
		return quesIdString;
	}

	public void setQuesIdString(String quesIdString) {
		this.quesIdString = quesIdString;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<String> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(String subscriber) {
		System.out.println(this.subscribers);
		this.subscribers.add(subscriber);
		System.out.println(this.subscribers);
	}
	
	public void removeSubscribers(String subscriber) {
		this.subscribers.remove(subscriber);
	}

	@Override
	public String toString() {
		return "User [qid=" + quesId + ", title=" + quesTitle + ", desc="
				+ quesDesc + ", user=" + user + ", votes=" + votes + "]";
	}

}
