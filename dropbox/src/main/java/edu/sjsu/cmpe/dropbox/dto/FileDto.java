package edu.sjsu.cmpe.dropbox.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.dropbox.domain.userFile;

@JsonPropertyOrder(alphabetic = true)
public class FileDto extends LinksDto {
    private userFile userFile;

    /**
     * @param book
     */
    public FileDto(userFile userFile) {
	super();
	this.setFile(userFile);
    }

	public userFile getFile() {
		return userFile;
	}

	public void setFile(userFile userFile) {
		this.userFile = userFile;
	}

}
