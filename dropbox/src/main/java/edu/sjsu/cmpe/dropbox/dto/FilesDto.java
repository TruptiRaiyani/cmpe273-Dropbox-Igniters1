package edu.sjsu.cmpe.dropbox.dto;

import java.util.ArrayList;

import edu.sjsu.cmpe.dropbox.domain.userFile;

public class FilesDto extends LinksDto {

    private ArrayList<userFile> userFiles = new ArrayList<userFile>();

    /**
     * @param book
     */
    public FilesDto(ArrayList<userFile> userFiles) {
	super();
	this.setFiles(userFiles);
    }

	public ArrayList<userFile> getFiles() {
		return userFiles;
	}

	public void setFiles(ArrayList<userFile> userFiles) {
		this.userFiles = userFiles;
	}

}
