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
import com.kartshub.app.Answer;

@Path("/answers")
public class AnswerResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Answer> getAnswers(@QueryParam("userId") Integer userId,
								   @QueryParam("quesId") Integer quesId) {
		
		List<Answer> answers = null;
		Session ses = HibernateUtil.currentSession();
		if ((userId != null) && (quesId != null)) {
			answers =  ses.createQuery("select a from Answer a where a.userId="+userId+" and a.quesId="+quesId).list();
		} else if (userId != null) {
			answers =  ses.createQuery("select a from Answer a where a.userId="+userId).list();
		} else if (quesId != null) {
			answers = ses.createQuery("select a from Answer a where a.quesId="+quesId).list();
		} else {
			answers = ses.createQuery("select a from Answer a").list();
		}
		HibernateUtil.closeSession();
		System.out.println("No of Answers " + answers.size());
		int cuid, cqid;
		String usersname, quesTitle;
		for (Integer i=0; i<answers.size(); i++) {
			cuid = answers.get(i).getUserId();
			usersname = Generic.getUsersName(cuid);
			answers.get(i).setUsersName(usersname);
			cqid = answers.get(i).getQuesId();
			quesTitle = Generic.getQuesTitle(cqid);
			answers.get(i).setQuesTitle(quesTitle);
		}
		return answers;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Answer addAnswer(Answer a) {
		Session ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.save(a);
		tx.commit();
		HibernateUtil.closeSession();
		return a;
	}
	
	@PUT
	@Path("/{aid}/votes")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Answer> addVotes(@PathParam("aid") Integer aid,
			                     @QueryParam("op") String op) {
		Integer votes = null;
		List<Answer> answers = null;
		Answer answer = null;
		Session ses = HibernateUtil.currentSession();
		answers = ses.createQuery("select a from Answer a where a.ansId="+aid).list();
		HibernateUtil.closeSession();
		answer = answers.get(0);
		System.out.println("Operation is " + op);
		votes = answer.getVotes();
		System.out.println("Votes before " + votes);
		if (op.equals("add")) {
			System.out.println("IN ADD");
			votes++;
		} else if (op.equals("minus")) {
			System.out.println("IN MINUS");
			votes--;
		}
		answer.setVotes(votes);
		System.out.println("Votes after " + votes);
		System.out.println(answer);
		ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.update(answer);
		tx.commit();
		HibernateUtil.closeSession();
		List<Answer> answ = new ArrayList<Answer>();
		answ.add(answer);
		return answ;
	}
	
}
