package edu.sjsu.cmpe.dropbox.domain;
 	
import java.util.ArrayList;

public class userFile {
	private int fileID;
	private String name;
	private int owner;
	private String accessType;
	private ArrayList<Integer> sharedWith= new ArrayList<Integer>();
	private boolean update;

	public userFile()
	{
		setAccessType("private");
		setUpdate(false);
	}
	public int getFileID() {
		return fileID;
	}
	public void setFileID(int fileID) {
		this.fileID = fileID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public ArrayList<Integer> getSharedWith() {
		return sharedWith;
	}
	public void setSharedWith(ArrayList<Integer> sharedWith) {
		this.sharedWith = sharedWith;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	
}
