package com.kartshub.app;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.kartshub.utility.MorphiaUtil;

@Path("/usersession")
public class UserSessionResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<String> getValidUser(@Context HttpHeaders headers) {
		ObjectId userId = null;
		List<UserSession> usersession = null;
		List<String> answer = new ArrayList<String>();
		if (headers.getRequestHeader("authorization") != null) {
			String authorization = headers.getRequestHeader("authorization").get(0);
			if (authorization != null && authorization.startsWith("UserToken")) {
				MorphiaUtil morphiautil = MorphiaUtil.getInstance();
				Datastore datastore = morphiautil.getDatastore();
		        // Authorization: Basic base64credentials
		        String base64Credentials = authorization.substring("UserToken".length()).trim();
		        String credentials = StringUtils.newStringUtf8(Base64.decodeBase64(base64Credentials));
		        // credentials = usertoken
		        usersession = datastore.createQuery(UserSession.class).field("sessToken").equal(credentials).asList();
		        userId = usersession.get(0).getSessionUser().getUserId();
			}
		}
		System.out.println("Found user ID: " + userId);
		System.out.println("IN STRING: " + userId.toString());
		answer.add(userId.toString());
		return answer;
	}
	
	@DELETE
	@Path("/{token}")
	public void deleteSession(@PathParam("token") String token) {
		//session.delete....
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		Query<UserSession> usersession = datastore.createQuery(UserSession.class).field("sessToken").equal(token);
		datastore.delete(usersession);
	}
}
