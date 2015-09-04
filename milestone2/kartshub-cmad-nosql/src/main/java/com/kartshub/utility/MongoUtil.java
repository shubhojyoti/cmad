package com.kartshub.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoUtil {

	private Properties prop = new Properties();
	private InputStream input = null;
	private String dbUrl;
	private String dbName;
	private String dbUsername;
	private String dbPassword;
	MongoClient mongoClient;
	DB mongoDatabase;

	public DB getMongoDatabase() {
		return mongoDatabase;
	}

	public void setMongoDatabase(DB mongoDatabase) {
		this.mongoDatabase = mongoDatabase;
	}

	@SuppressWarnings("deprecation")
	public void createInstance() {
		try {
			input = MongoUtil.class.getClassLoader().getResourceAsStream(
					"config.properties");
			prop.load(input);
			this.dbUrl = prop.getProperty("dbUrl");
			this.dbName = prop.getProperty("dbName");
			this.dbUsername = prop.getProperty("dbUsername");
			this.dbPassword = prop.getProperty("dbPassword");
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		MongoClientURI connectionString = new MongoClientURI("mongodb://"
				+ this.dbUsername + ":" + this.dbPassword + "@" + this.dbUrl
				+ "/" + this.dbName);
		this.mongoClient = new MongoClient(connectionString);

		this.mongoDatabase = mongoClient.getDB("cmadproject");

	}

	public void closeInstance() {
		this.mongoClient.close();
	}

}
