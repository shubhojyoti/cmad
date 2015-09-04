package com.kartshub.app;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

public class Tag {
	@Id
	private ObjectId tagId;
	@Indexed(name="tagname", unique=true)
	private String tagName;

	public ObjectId getTagId() {
		return tagId;
	}

	public void setTagId(ObjectId tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
