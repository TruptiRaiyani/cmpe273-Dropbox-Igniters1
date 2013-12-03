package edu.sjsu.cmpe.dropbox.api.resources;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDBInstance {
	
	private static MongoClient mongoClient;
	
	public MongoDBInstance() {
		try {
			mongoClient = new MongoClient("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DB getdb(){
		return  mongoClient.getDB("test");
	}
	
	public DBCollection getColluser(){
		return  mongoClient.getDB("test").getCollection("user");
	}
	
	public DBCollection getColldocument(){
		return  mongoClient.getDB("test").getCollection("document");
	}
	
	public DBCollection getColldocument_files(){
		return mongoClient.getDB("test").getCollection("document.files");
	}


}
