package com.kartshub.app;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kartshub.utility.Generic;
import com.kartshub.utility.HibernateUtil;

@Path("/questions")
public class QuestionResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> getQuestions(@QueryParam("userId") Integer userId) {
		
		List<Question> questions = null;
		Session ses = HibernateUtil.currentSession();
		if (userId != null) {
			questions =  ses.createQuery("select q from Question q where q.userId="+userId).list();
		} else {
			questions = ses.createQuery("select q from Question q").list();
		}
		HibernateUtil.closeSession();
		int cqid, cuid, count;
		String usersname;
		for (Integer i=0; i<questions.size(); i++) {
			cqid = questions.get(i).getQuesId();
			count = Generic.countAnswers(cqid);
			questions.get(i).setAnswers(count);
			cuid = questions.get(i).getUserId();
			usersname = Generic.getUsersName(cuid);
			questions.get(i).setUsersName(usersname);
		}
		return questions;
	}
	
	@GET
	@Path("/{qid}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> getIndQuestion(@PathParam("qid") Integer qid) {
		
		List<Question> questions = null;
		Session ses = HibernateUtil.currentSession();
		questions =  ses.createQuery("select q from Question q where q.quesId="+qid).list();
		HibernateUtil.closeSession();
		return questions;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Question addQuestion(Question q) {
		Session ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.save(q);
		tx.commit();
		HibernateUtil.closeSession();
		return q;
	}
	
	@PUT
	@Path("/{qid}/votes")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> addVotes(@PathParam("qid") Integer qid,
			                       @QueryParam("op") String op) {
		Integer votes = null;
		List<Question> questions = null;
		Question question = null;
		Session ses = HibernateUtil.currentSession();
		questions = ses.createQuery("select q from Question q where q.quesId="+qid).list();
		HibernateUtil.closeSession();
		question = questions.get(0);
		System.out.println("Operation is " + op);
		votes = question.getVotes();
		System.out.println("Votes before " + votes);
		if (op.equals("add")) {
			System.out.println("IN ADD");
			votes++;
		} else if (op.equals("minus")) {
			System.out.println("IN MINUS");
			votes--;
		}
		question.setVotes(votes);
		System.out.println("Votes after " + votes);
		System.out.println(question);
		ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.update(question);
		tx.commit();
		HibernateUtil.closeSession();
		List<Question> ques = new ArrayList<Question>();
		ques.add(question);
		return ques;
	}
	
	@PUT
	@Path("/{qid}/views")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> addViews(@PathParam("qid") Integer qid,
			                       @QueryParam("op") String op) {
		Integer views = null;
		List<Question> questions = null;
		Question question = null;
		Session ses = HibernateUtil.currentSession();
		questions = ses.createQuery("select q from Question q where q.quesId="+qid).list();
		HibernateUtil.closeSession();
		question = questions.get(0);
		System.out.println("Operation is " + op);
		views = question.getViews();
		System.out.println("Views before " + views);
		if (op.equals("add")) {
			System.out.println("IN ADD");
			views++;
		} else if (op.equals("minus")) {
			System.out.println("IN MINUS");
			views--;
		}
		question.setViews(views);
		System.out.println("Views after " + views);
		System.out.println(question);
		ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.update(question);
		tx.commit();
		HibernateUtil.closeSession();
		List<Question> ques = new ArrayList<Question>();
		ques.add(question);
		return ques;
	}
	
}
