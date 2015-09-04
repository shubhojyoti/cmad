package com.kartshub.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;

import com.kartshub.utility.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

@Path("/search")
public class SearchResource {
	@GET
	@Path("/questions")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SearchResult> searchQuestions(@QueryParam("terms") String terms) {
		System.out.println("Came here");
		List<SearchResult> searchresults = new ArrayList<SearchResult>();
		MongoUtil mongoutil = new MongoUtil();
		mongoutil.createInstance();
		DBCollection question = mongoutil.getMongoDatabase().getCollection("question");
		
		DBCursor questions = searchFromQuestion(question, terms);
	    while (questions.hasNext()) {
	    	Map ques = questions.next().toMap();
	    	System.out.println(ques);
	    	SearchResult searchresult = new SearchResult();
	    	searchresult.setQuesId(ques.get("_id").toString());
	    	searchresult.setQuesTitle(ques.get("quesTitle").toString());
	    	searchresult.setQuesDesc(ques.get("quesDesc").toString());
	    	searchresult.setScore((Double) ques.get("score"));
	    	System.out.println(searchresult);
	    	searchresults.add(searchresult);
	    }
	    
	    mongoutil.closeInstance();
		return searchresults;
	}
	
	@GET
	@Path("/answers")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<SearchResult> searchAnswers(@QueryParam("terms") String terms) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		System.out.println("Came here");
		List<SearchResult> searchresults = new ArrayList<SearchResult>();
		MongoUtil mongoutil = new MongoUtil();
		mongoutil.createInstance();
		DBCollection answer = mongoutil.getMongoDatabase().getCollection("answer");
		DBCollection question = mongoutil.getMongoDatabase().getCollection("question");
		
		DBCursor answers = searchFromAnswer(answer, terms);
	    while (answers.hasNext()) {
	    	Map ans = answers.next().toMap();
	    	System.out.println(ans);
	    	SearchResult searchresult = new SearchResult();
	    	searchresult.setAnsId(ans.get("_id").toString());
	    	searchresult.setAnsDesc(ans.get("ansDesc").toString());
	    	searchresult.setScore((Double) ans.get("score"));
	    	Map quest = getQuestionFromAnswer(question, ans.get("question").toString());
	    	searchresult.setQuesId(quest.get("_id").toString());
	    	searchresult.setQuesTitle(quest.get("quesTitle").toString());
	    	searchresult.setQuesDesc(quest.get("quesDesc").toString());
	    	System.out.println(searchresult);
	    	searchresults.add(searchresult);
	    }
	    
	    mongoutil.closeInstance();
		return searchresults;
	}
	
	private DBCursor searchFromAnswer(DBCollection answer, String terms) {
		BasicDBObject textSearch = new BasicDBObject("$search", terms);
	    BasicDBObject search = new BasicDBObject("$text", textSearch);

	    BasicDBObject meta = new BasicDBObject("$meta", "textScore");
	    BasicDBObject score = new BasicDBObject("score", meta);

	    DBCursor answers = answer.find(search, score).sort(score);		
		return answers;
	}
	
	private DBCursor searchFromQuestion(DBCollection question, String terms) {
		BasicDBObject textSearch = new BasicDBObject("$search", terms);
	    BasicDBObject search = new BasicDBObject("$text", textSearch);

	    BasicDBObject meta = new BasicDBObject("$meta", "textScore");
	    BasicDBObject score = new BasicDBObject("score", meta);

	    DBCursor questions = question.find(search, score).sort(score);
		return questions;
	}
	
	private Map getQuestionFromAnswer(DBCollection question, String quesobj) {
		Map quest = null;
		String[] objsplit = quesobj.split("\"");
		int quesobjstring = objsplit.length - 2;
		ObjectId qid = new ObjectId(objsplit[quesobjstring]);
		BasicDBObject quesId = new BasicDBObject("_id", qid);
		DBCursor ques = question.find(quesId);
		while (ques.hasNext()) {
			quest = ques.next().toMap();
			System.out.println(quest);
		}
		
		return quest;
	}
	
}
