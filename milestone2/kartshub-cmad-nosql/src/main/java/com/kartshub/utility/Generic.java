package com.kartshub.utility;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import com.kartshub.app.Answer;
import com.kartshub.app.Question;
import com.kartshub.app.User;

public class Generic {

	public static int countAnswers(Question ques) {
		int count = 0;
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		List<Answer> answers = datastore.createQuery(Answer.class).field("question").equal(ques).asList();
		if (null != answers) {
			count = answers.size();
		}
		return count;
	}
	
	public static String getUsersName(ObjectId usr) {
		String user = "";
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		List<User> users = datastore.createQuery(User.class).field("_id").equal(usr).asList();
		if (null != users) {
			user = users.get(0).getFirstname() + " " + users.get(0).getLastname();
		}
		return user;
	}
	
	public static String getQuesTitle(ObjectId quesId) {
		String quesTitle = "";
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		List<Question> questions = datastore.createQuery(Question.class).field("_id").equal(quesId).asList();
		if (null != questions) {
			quesTitle = questions.get(0).getQuesTitle();
		}
		return quesTitle;
	}
	
	public static List<String> getSubscribers(ObjectId quesId) {
		List<String> subscribers = new ArrayList<String>();
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		List<Question> questions = datastore.createQuery(Question.class).field("_id").equal(quesId).asList();
		if (null != questions) {
			subscribers = questions.get(0).getSubscribers();
		}
		return subscribers;
	}
	
}
