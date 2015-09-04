package com.kartshub.utility;

import java.util.List;

import org.hibernate.Session;

import com.kartshub.app.Answer;
import com.kartshub.app.Question;
import com.kartshub.app.User;

public class Generic {

	public static int countAnswers(int quesId) {
		int count = 0;
		Session ses = HibernateUtil.currentSession();
		List<Answer> answers = null;
		answers = ses.createQuery("select a from Answer a where a.quesId="+quesId).list();
		HibernateUtil.closeSession();
		if (answers != null) {
			count = answers.size();
		}
		return count;
	}
	
	public static String getUsersName(int userId) {
		String user = "";
		Session ses = HibernateUtil.currentSession();
		List<User> users = null;
		users = ses.createQuery("select u from User u where u.userId="+userId).list();
		HibernateUtil.closeSession();
		if (users != null) {
			user = users.get(0).getFirstname() + " " + users.get(0).getLastname();
		}
		return user;
	}
	
	public static String getQuesTitle(int quesId) {
		String quesTitle = "";
		Session ses = HibernateUtil.currentSession();
		List<Question> questions = null;
		questions = ses.createQuery("select q from Question q where q.quesId="+quesId).list();
		HibernateUtil.closeSession();
		if (questions != null) {
			quesTitle = questions.get(0).getQuesTitle();
		}
		return quesTitle;
	}
	
}
