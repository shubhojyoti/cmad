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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.kartshub.utility.GenerateSession;
import com.kartshub.utility.MorphiaUtil;
import com.mongodb.DuplicateKeyException;

@Path("/users")
public class UserResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUsers(@Context HttpHeaders headers) {
		String userName = null;
		String passWord = null;
		List<User> users = null;
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();

		if (headers.getRequestHeader("authorization") != null) {
			String authorization = headers.getRequestHeader("authorization").get(0);
			if (authorization != null && authorization.startsWith("Basic")) {
		        // Authorization: Basic base64credentials
		        String base64Credentials = authorization.substring("Basic".length()).trim();
		        String credentials = StringUtils.newStringUtf8(Base64.decodeBase64(base64Credentials));
		        // credentials = username:password
		        String[] creds = credentials.split(":");
		        userName = creds[0];
		        passWord = creds[1];	
			}
		}
		System.out.println("Username : " + userName + " Password : " + passWord);
		
		if ((null != userName) && (null != passWord)) {
			users = datastore.createQuery(User.class).field("username").equal(userName).field("password").equal(passWord).asList();
			if (!users.isEmpty()) {
				String token = checkSession(datastore, users.get(0));
				users.get(0).setToken(token);
			}
		} else {
			users = datastore.createQuery(User.class).asList();
		}
		System.out.println(users);
		return users;
	}
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUser(@PathParam("id") String id) {
		List<User> users = null;
		ObjectId uid = new ObjectId(id);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		try {
			users = datastore.createQuery(User.class).field("_id").equal(uid).asList();
		} catch (DuplicateKeyException e) {
			List<User> blank = new ArrayList<User>();
			return blank;
		}
		return users;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public User createUser(User u) {
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		datastore.save(u);
		return u;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public User updateUser(User u, @PathParam("id") String id) {
		System.out.println("In Here " + u);
		ObjectId uid = new ObjectId(id);
		u.setUserId(uid);
		System.out.println("For Update: " + u);
		MorphiaUtil morphiautil = MorphiaUtil.getInstance();
		Datastore datastore = morphiautil.getDatastore();
		String username = u.getUsername();
		String password = u.getPassword();
		String firstname = u.getFirstname();
		String lastname = u.getLastname();
		Query<User> query = datastore.createQuery(User.class).field("_id").equal(uid);
		UpdateOperations<User> ops = datastore.createUpdateOperations(User.class).set("username", username).set("password", password).set("firstname", firstname).set("lastname", lastname);
		datastore.update(query, ops);
		return u;
	}
	
	public String checkSession(Datastore datastore, User user) {
		String newToken = null;
		List<UserSession> usersession = null;
		usersession = datastore.createQuery(UserSession.class).field("sessionUser").equal(user).asList();
		if (!usersession.isEmpty()) {
			newToken = usersession.get(0).getSessToken();
		}
		if (newToken == null) {
			GenerateSession mysession = new GenerateSession();
			newToken = mysession.nextSessionId();
			UserSession token = new UserSession();
			token.setSessToken(newToken);
			token.setSessionUser(user);		
			datastore.save(token);
		}
		return newToken;
	}
	
}
