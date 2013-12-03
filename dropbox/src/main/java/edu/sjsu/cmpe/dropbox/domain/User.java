package edu.sjsu.cmpe.dropbox.domain;

import java.util.ArrayList;

public class User {
    private int userID;
    private String username;
	private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String status;
    private String designation;
    private ArrayList<userFile> myFiles=new ArrayList<userFile>();
    private ArrayList<userFile> filesShared=new ArrayList<userFile>();
    // add more fields here

    /**
     * @return the isbn
     */
    public User()
    {
    	setPassword("user123");
    	setStatus("Activated");
    }

    public String getUserName() {
		return username;
	}
    
    public void setUserName(String username) {
		this.username = username;
	}
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<userFile> getmyFiles() {
		return myFiles;
	}

	public void setmyFiles(ArrayList<userFile> myFiles) {
		this.myFiles = myFiles;
	}

	public ArrayList<userFile> getFilesShared() {
		return filesShared;
	}

	public void setFilesShared(ArrayList<userFile> filesShared) {
		this.filesShared = filesShared;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
}
