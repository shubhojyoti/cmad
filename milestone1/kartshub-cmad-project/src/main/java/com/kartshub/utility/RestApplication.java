package com.kartshub.utility;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.kartshub.app.AnswerResource;
import com.kartshub.app.QuestionResource;
import com.kartshub.app.SessionResource;
import com.kartshub.app.UserResource;

@ApplicationPath("/services")
public class RestApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	public RestApplication() {
		singletons.add(new UserResource());
		singletons.add(new QuestionResource());
		singletons.add(new AnswerResource());
		singletons.add(new SessionResource());
	}
	public Set<Class<?>> getClasses() {
		return empty;
	}
	public Set<Object> getSingletons() {
		return singletons;
	}	
}
