package com.kartshub.app;

import java.util.Date;

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

@Entity(value = "answer", noClassnameStored = true)
@Indexes({@Index(name = "quesAnswer", value = "question, ansId"),
	      @Index(name = "userAnswer", value = "user, ansId"),
	      @Index(name = "answerText", fields = {
                      @Field(value = "ansDesc", type = IndexType.TEXT)})
})
public class Answer {
	@Id
	private ObjectId ansId;
	private String ansDesc;
	@Indexed @Reference
	private Question question;
	@Indexed @Reference
	private User user;
	private int votes;
	private Date createDate = new Date();
	@Transient
	private String usersName;
	@Transient
	private String quesIdString;
	@Transient
	private String quesTitle;
	@Transient
	private String ansIdString;

	public ObjectId getAnsId() {
		return ansId;
	}

	public void setAnsId(ObjectId ansId) {
		this.ansId = ansId;
	}

	public String getAnsDesc() {
		return ansDesc;
	}

	public void setAnsDesc(String ansDesc) {
		this.ansDesc = ansDesc;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
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

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

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

	public String getAnsIdString() {
		return ansIdString;
	}

	public void setAnsIdString(String ansIdString) {
		this.ansIdString = ansIdString;
	}

	public String getQuesIdString() {
		return quesIdString;
	}

	public void setQuesIdString(String quesIdString) {
		this.quesIdString = quesIdString;
	}

}
