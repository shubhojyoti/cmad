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

@Path("/questions")
public class QuestionResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> getQuestions(@QueryParam("uid") String uid) {
		
		List<Question> questions = null;
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		if (null != uid) {
			ObjectId userId = new ObjectId(uid);
			User user = datastore.createQuery(User.class).field("_id").equal(userId).asList().get(0);
			questions = datastore.createQuery(Question.class).field("user").equal(user).asList();
			System.out.println(questions);
		} else {
			questions = datastore.createQuery(Question.class).asList();
		}
		System.out.println(questions);
		Question cq;
		ObjectId cuid, cqid;
		int count;
		String usersname;
		for (Integer i=0; i<questions.size(); i++) {
			cqid = questions.get(i).getQuesId();
			questions.get(i).setQuesIdString(cqid.toString());
			cq = questions.get(i);
			count = Generic.countAnswers(cq);
			questions.get(i).setAnswers(count);
			cuid = questions.get(i).getUser().getUserId();
			usersname = Generic.getUsersName(cuid);
			questions.get(i).setUsersName(usersname);
		}
		return questions;
	}
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> getIndQuestion(@PathParam("id") String id) {
		
		List<Question> questions = null;
		ObjectId qid = new ObjectId(id);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		questions = datastore.createQuery(Question.class).field("_id").equal(qid).asList();
		Question cq;
		ObjectId cuid, cqid;
		int count;
		String usersname;
		for (Integer i=0; i<questions.size(); i++) {
			cqid = questions.get(i).getQuesId();
			questions.get(i).setQuesIdString(cqid.toString());
			cq = questions.get(i);
			count = Generic.countAnswers(cq);
			questions.get(i).setAnswers(count);
			cuid = questions.get(i).getUser().getUserId();
			usersname = Generic.getUsersName(cuid);
			questions.get(i).setUsersName(usersname);
		}
		return questions;
	}

	@POST
	@Path("/{uid}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Question addQuestion(Question q, @PathParam("uid") String uid) {
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		ObjectId id = new ObjectId(uid);
		User user = datastore.createQuery(User.class).field("_id").equal(id).asList().get(0);
		if (null != user) {
			q.setUser(user);
			datastore.save(q);
		}
		return q;
	}
	
	@PUT
	@Path("/{id}/votes")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> addVotes(@PathParam("id") String id,
			                       @QueryParam("op") String op) {
		Integer votes = null;
		List<Question> questions = null;
		Question question = null;
		ObjectId qid = new ObjectId(id);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		questions = datastore.createQuery(Question.class).field("_id").equal(qid).asList();
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
		
		Query<Question> query = datastore.createQuery(Question.class).field("_id").equal(qid);
		UpdateOperations<Question> ops = datastore.createUpdateOperations(Question.class).set("votes", votes);
		datastore.update(query, ops);
		Question cq;
		ObjectId cuid, cqid;
		int count;
		String usersname;
		for (Integer i=0; i<questions.size(); i++) {
			cqid = questions.get(i).getQuesId();
			questions.get(i).setQuesIdString(cqid.toString());
			cq = questions.get(i);
			count = Generic.countAnswers(cq);
			questions.get(i).setAnswers(count);
			cuid = questions.get(i).getUser().getUserId();
			usersname = Generic.getUsersName(cuid);
			questions.get(i).setUsersName(usersname);
		}
		List<Question> ques = new ArrayList<Question>();
		ques.add(question);
		return ques;
	}
	
	@PUT
	@Path("/{id}/views")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> addViews(@PathParam("id") String id,
			                       @QueryParam("op") String op) {
		Integer views = null;
		List<Question> questions = null;
		Question question = null;
		ObjectId qid = new ObjectId(id);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		questions = datastore.createQuery(Question.class).field("_id").equal(qid).asList();
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
		
		Query<Question> query = datastore.createQuery(Question.class).field("_id").equal(qid);
		UpdateOperations<Question> ops = datastore.createUpdateOperations(Question.class).set("views", views);
		datastore.update(query, ops);
		
		List<Question> ques = new ArrayList<Question>();
		ques.add(question);
		return ques;
	}
	
	@PUT
	@Path("/{id}/subscribe")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> addSubscribers(@PathParam("id") String id,
			                             @QueryParam("email") String email) {
		List<Question> questions = null;
		Question question = null;
		ObjectId qid = new ObjectId(id);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		questions = datastore.createQuery(Question.class).field("_id").equal(qid).asList();
		question = questions.get(0);		
		//question.setSubscribers(email);
		List<String> curr = question.getSubscribers();
		curr.add(email);
		
		Query<Question> query = datastore.createQuery(Question.class).field("_id").equal(qid);
		UpdateOperations<Question> ops = datastore.createUpdateOperations(Question.class).set("subscribers", curr);
		datastore.update(query, ops);
		System.out.println(question);
		List<Question> ques = new ArrayList<Question>();
		ques.add(question);
		return ques;
	}
	
	@PUT
	@Path("/{id}/unsubscribe")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Question> removeSubscribers(@PathParam("id") String id,
			                                @QueryParam("email") String email) {
		List<Question> questions = null;
		Question question = null;
		ObjectId qid = new ObjectId(id);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		questions = datastore.createQuery(Question.class).field("_id").equal(qid).asList();
		question = questions.get(0);		
		//question.setSubscribers(email);
		List<String> curr = question.getSubscribers();
		curr.remove(email);
		
		Query<Question> query = datastore.createQuery(Question.class).field("_id").equal(qid);
		UpdateOperations<Question> ops = datastore.createUpdateOperations(Question.class).set("subscribers", curr);
		datastore.update(query, ops);
		System.out.println(question);
		List<Question> ques = new ArrayList<Question>();
		ques.add(question);
		return ques;
	}
	
}
