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

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.kartshub.utility.Generic;
import com.kartshub.utility.MorphiaUtil;
import com.kartshub.utility.SendMail;

@Path("/answers")
public class AnswerResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Answer> getAnswers(@QueryParam("uId") String uId,
								   @QueryParam("qId") String qId) {
		
		List<Answer> answers = null;
		User user = null;
		Question question = null;
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		if (null != uId) {
			ObjectId userId = new ObjectId(uId);
			user = datastore.createQuery(User.class).field("_id").equal(userId).asList().get(0);
			System.out.println("In Answers for: " + user);
		}
		if (null != qId) {
			ObjectId quesId = new ObjectId(qId);
			question = datastore.createQuery(Question.class).field("_id").equal(quesId).asList().get(0);
		}
		if ((null != user) && (null != question)) {
			answers = datastore.createQuery(Answer.class).field("user").equal(user).field("question").equal(question).asList();
		} else if (null != user) {
			answers = datastore.createQuery(Answer.class).field("user").equal(user).asList();
		} else if (null != question) {
			answers = datastore.createQuery(Answer.class).field("question").equal(question).asList();
		} else {
			answers = datastore.createQuery(Answer.class).asList();
		}
		System.out.println("No of Answers " + answers.size());
		ObjectId cuid, cqid;
		String usersname, quesTitle;
		for (Integer i=0; i<answers.size(); i++) {
			answers.get(i).setAnsIdString(answers.get(i).getAnsId().toString());
			cuid = answers.get(i).getUser().getUserId();
			usersname = Generic.getUsersName(cuid);
			answers.get(i).setUsersName(usersname);
			cqid = answers.get(i).getQuestion().getQuesId();
			answers.get(i).setQuesIdString(cqid.toString());
			quesTitle = Generic.getQuesTitle(cqid);
			answers.get(i).setQuesTitle(quesTitle);
		}
		return answers;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Answer addAnswer(Answer a, @QueryParam("uid") String uid, @QueryParam("qid") String qid) {
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		ObjectId id = new ObjectId(uid);
		ObjectId quesId = new ObjectId(qid);
		User user = datastore.createQuery(User.class).field("_id").equal(id).asList().get(0);
		Question question = datastore.createQuery(Question.class).field("_id").equal(quesId).asList().get(0);
		if (null != user) {
			a.setUser(user);
			a.setQuestion(question);
			datastore.save(a);
		}
		try {
			informSubscribers(qid);
		}
		finally {
			//Nothing
		}
		return a;
	}
	
	public void informSubscribers(String qid) {
		ObjectId quesId = new ObjectId(qid);
		List<String> subscribers = Generic.getSubscribers(quesId);
		if (0 < subscribers.size()) {
			SendMail sendmail = new SendMail();
			sendmail.SendTheMail(subscribers, "New answers available for question ID " + qid,
					"Hi There,\n\nNew answers are available for the question you are interested in.\n\nView Answers Here: http://localhost:8616/kartshub-cmad-nosql/#/ques/"+qid);
		}
	}
	
	@PUT
	@Path("/{aid}/votes")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Answer> addVotes(@PathParam("aid") String ansId,
			                     @QueryParam("op") String op) {
		Integer votes = null;
		List<Answer> answers = null;
		Answer answer = null;
		ObjectId aid = new ObjectId(ansId);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		answers = datastore.createQuery(Answer.class).field("_id").equal(aid).asList();
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
		
		Query<Answer> query = datastore.createQuery(Answer.class).field("_id").equal(aid);
		UpdateOperations<Answer> ops = datastore.createUpdateOperations(Answer.class).set("votes", votes);
		datastore.update(query, ops);
		answer.setAnsIdString(answer.getAnsId().toString());
		
		List<Answer> answ = new ArrayList<Answer>();
		answ.add(answer);
		return answ;
	}
	
}
