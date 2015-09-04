package com.kartshub.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MorphiaUtil {
	
	private static MorphiaUtil morphiautil = new MorphiaUtil();
	private Properties prop = new Properties();
	private InputStream input = null;
	private String dbUrl;
	private String dbName;
	private String dbUsername;
	private String dbPassword;
	Datastore datastore;

	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbUsername() {
		return dbUsername;
	}

	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	private MorphiaUtil() {
		try {
			input = MorphiaUtil.class.getClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			this.dbUrl = prop.getProperty("dbUrl");
			this.dbName = prop.getProperty("dbName");
			this.dbUsername = prop.getProperty("dbUsername");
			this.dbPassword = prop.getProperty("dbPassword");
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		MongoClientURI connectionString = new MongoClientURI("mongodb://" + this.dbUsername + ":" + this.dbPassword + "@" + this.dbUrl + "/" + this.dbName);
		MongoClient mongoClient = new MongoClient(connectionString);
		Morphia morphia = new Morphia();
		morphia.mapPackage("com.kartshub.app");
		this.datastore = morphia.createDatastore(mongoClient, "cmadproject");
		datastore.ensureIndexes();
	}
	
	public static MorphiaUtil getInstance() {
		/*if (null == morphiautil) {
			morphiautil = new MorphiaUtil();
		}*/
		return morphiautil;
	}

}
