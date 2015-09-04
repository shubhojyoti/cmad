package com.kartshub.app;

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
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kartshub.utility.HibernateUtil;

@Path("/usersession")
public class SessionResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Integer getValidUser(@Context HttpHeaders headers) {
		Integer userId = null;
		List<UserSession> usersession = null;	
		if (headers.getRequestHeader("authorization") != null) {
			String authorization = headers.getRequestHeader("authorization").get(0);
			if (authorization != null && authorization.startsWith("UserToken")) {
				Session ses = HibernateUtil.currentSession();
		        // Authorization: Basic base64credentials
		        String base64Credentials = authorization.substring("UserToken".length()).trim();
		        String credentials = StringUtils.newStringUtf8(Base64.decodeBase64(base64Credentials));
		        // credentials = usertoken
		        usersession = ses.createQuery("select u from UserSession u where u.sessToken='"+credentials+"'").list();
				userId = usersession.get(0).getUserId();
				HibernateUtil.closeSession();
			}
		}
		System.out.println("Found user ID: " + userId);
		return userId;
	}
	
	@DELETE
	@Path("/{token}")
	public void deleteSession(@PathParam("token") String token) {
		//session.delete....
		List<UserSession> usersession = null;
		Session ses = HibernateUtil.currentSession();
		usersession = ses.createQuery("select u from UserSession u where u.sessToken='"+token+"'").list();
		Integer sessId = usersession.get(0).getSessId();
		HibernateUtil.closeSession();
		ses = HibernateUtil.currentSession();
		UserSession usersess = new UserSession();
		usersess.setSessId(sessId);
		Transaction tx = ses.beginTransaction();
		ses.delete(usersess);
		tx.commit();
		HibernateUtil.closeSession();
	}
}
