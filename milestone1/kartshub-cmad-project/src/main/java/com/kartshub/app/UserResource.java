package com.kartshub.app;

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
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kartshub.utility.GenerateSession;
import com.kartshub.utility.HibernateUtil;
import com.kartshub.app.User;

@Path("/users")
public class UserResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUsers(@Context HttpHeaders headers) {
		String userName = null;
		String passWord = null;
		List<User> users = null;
		Session ses = HibernateUtil.currentSession();

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
		
		if ((userName != null) && (passWord != null)) {
			users = ses.createQuery("select u from User u where u.username='"+userName+"' and u.password='"+passWord+"'").list();
			if (!users.isEmpty()) {
				String token = checkSession(ses, users.get(0).getUserId());
				users.get(0).setToken(token);
			}
		} else {
			users = ses.createQuery("select u from User u").list();
		}
		HibernateUtil.closeSession();
		System.out.println(users);
		return users;
	}
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> getUser(@PathParam("id") Integer id) {
		List<User> users = null;
		Session ses = HibernateUtil.currentSession();
		users = ses.createQuery("select u from User u where u.id="+id).list();
		HibernateUtil.closeSession();
		return users;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public User createUser(User u) {
		Session ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.save(u);
		tx.commit();
		HibernateUtil.closeSession();
		return u;
	}
	
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public User updateUser(User u, @PathParam("id") Integer id) {
		System.out.println("In Here " + u);
		u.setUserId(id);
		Session ses = HibernateUtil.currentSession();
		Transaction tx = ses.beginTransaction();
		ses.update(u);
		tx.commit();
		HibernateUtil.closeSession();
		return u;
	}
	
	public String checkSession(Session ses, Integer userId) {
		String newToken = null;
		List<UserSession> usersession = null;
		usersession = ses.createQuery("select s from UserSession s where s.userId="+userId).list();
		if (!usersession.isEmpty()) {
			newToken = usersession.get(0).getSessToken();
		}
		if (newToken == null) {
			GenerateSession mysession = new GenerateSession();
			newToken = mysession.nextSessionId();
			UserSession token = new UserSession();
			token.setSessToken(newToken);
			token.setUserId(userId);
			Transaction tx = ses.beginTransaction();
			ses.save(token);		
			tx.commit();
		}
		return newToken;
	}
	
}
