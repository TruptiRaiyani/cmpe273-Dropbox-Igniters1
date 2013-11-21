package edu.sjsu.cmpe.dropbox.api.resources;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.sun.jersey.core.header.FormDataContentDisposition;

import edu.sjsu.cmpe.dropbox.domain.User;
import edu.sjsu.cmpe.dropbox.domain.userFile;
import edu.sjsu.cmpe.dropbox.dto.FileDto;
import edu.sjsu.cmpe.dropbox.dto.LinkDto;
import edu.sjsu.cmpe.dropbox.view.UploadView;

public class DropboxFileManagement {
	
	private static HashMap<String, User> users = new HashMap<String, User>();
	private MongoDBInstance mongodb = new MongoDBInstance();
	private DBCollection colluser = mongodb.getColluser();
	private DBCollection colldocument = mongodb.getColldocument();
	
	private static final String FILE_UPLOAD_PATH = "C:/dropbox";
	private static final int BUFFER_SIZE = 1024;
	
	
	public boolean checkOwnerOfFile(int userId, int fileId) {

		DBObject ownerID = null;
		BasicDBObject query = new BasicDBObject("fileID", fileId);
		query.append("owner", userId);

		BasicDBObject fields = new BasicDBObject();

		DBCursor cursor = colldocument.find(query, fields);

		while (cursor.hasNext()) {
			ownerID = cursor.next();
			System.out.println("ownerId" + ownerID.get("owner"));

		}

		if (ownerID == null) {
			System.out.println("OwnerId and userId doesn't match");

			return false;
		} else {
			System.out.println("OwnerId and userId match");

			return true;

		}
	}

	public boolean checkFileSharedWith(int userId, int fileId) {
		DBObject object = null;
		BasicDBObject query = new BasicDBObject("fileID", fileId);
		query.append("sharedWith", userId);
		BasicDBObject fields = new BasicDBObject();

		DBCursor cursor = colldocument.find(query, fields);

		while (cursor.hasNext()) {
			object = cursor.next();
			System.out.println("sharedWith" + object.get("sharedWith"));
		}

		if (object == null) {
			System.out.println("userFile cannot be shared with user");
			return false;
		} else {
			System.out
					.println("userFile can be shared with the user as user exists in sharedWith");
			return true;
		}
	}	
	
    public UploadView getUploadPage(int userID) {
        return new UploadView();
    }    
	
	public FileDto getMyFileByUserIdById( int userID, int id) {
		if (checkOwnerOfFile(userID, id)) {

			DBObject myFile = null;

			BasicDBObject query = new BasicDBObject("fileID", id);

			DBCursor cursor = colluser.find(query);

			while (cursor.hasNext()) {
				myFile = cursor.next();
				System.out.println("user id" + myFile);

			}

			FileDto fileResponse = new FileDto((userFile) myFile);
			fileResponse.addLink(new LinkDto("view-file", "/users/" + userID
					+ "/fil" + "es/" + id, "GET"));

			return fileResponse;
		} else {
			FileDto fileResponse = new FileDto();
			return fileResponse;
		}
	}
	
	public Response fileUpload(int userID, InputStream uploadedInputStream, FormDataContentDisposition fileInfo) throws IOException
    {
        Response.Status respStatus = Response.Status.OK; 
        if (fileInfo == null)
        {
            respStatus = Response.Status.INTERNAL_SERVER_ERROR;
        }
        else
        {
            final String fileName = fileInfo.getFileName();
            final String fileType = fileInfo.getType();
            final long fileSize = fileInfo.getSize();
            String uploadedFileLocation = FILE_UPLOAD_PATH + java.io.File.separator  + fileName;
 
            try
            {
                saveToDisc(uploadedInputStream, uploadedFileLocation);
                saveToMongoDB(userID,fileName,fileSize,fileType);
            }
            catch (Exception e)
            {
                respStatus = Response.Status.INTERNAL_SERVER_ERROR;
                e.printStackTrace();
            }
        }
 
        return Response.status(respStatus).build();
    }
 
    // save uploaded file to the specified location
    private void saveToDisc(final InputStream fileInputStream, final String fileUploadPath) throws IOException
    {
    	java.io.File file = new java.io.File(fileUploadPath);
    	final OutputStream out = new FileOutputStream(file);
        int read = 0;
        byte[] bytes = new byte[BUFFER_SIZE]; 
        while ((read = fileInputStream.read(bytes)) != -1)
        {
            out.write(bytes, 0, read);
        } 
        out.flush();
        out.close();
    }   


	private void saveToMongoDB(int userID, String fileName, long fileSize, String fileType) throws IOException {
		User user = users.get(userID);
		// users.get(email).getmyFiles().add(file);
		BasicDBObject ob = new BasicDBObject();
		ob.append("name", fileName);
		ob.append("owner ", userID);
		ob.append("size ", fileSize);
		// ob.append("accessType", file.getAccessType());
		colldocument.insert(ob);
		BasicDBObject query = new BasicDBObject("email", userID);
		DBCursor cursor;
		cursor = colldocument.find(query);
	}

	public LinkDto deleteMyFileByUserIdAndId(int userID, Integer id) {

//		if (checkOwnerOfFile(userID, id)) {
				GridFS myFS = new GridFS(mongodb.getdb(), "document");	
				myFS.remove(new BasicDBObject().append("metadata.fileID", id));
				//colldocument.remove(new BasicDBObject().append("metadata.fileID", id));
		/*	} else {
				System.out
						.println("userId does not have permission to delete the file");
			}*/
			return new LinkDto("create-file", "/users/" + userID, "POST");
	}
	
	public void updateFileByEmail(int userID, int id,String firstName) {

		boolean result = checkOwnerOfFile(userID, id);

		if (result) {
			DBObject myUserID = null;

			BasicDBObject query = new BasicDBObject("firstName", firstName);
			BasicDBObject fields = new BasicDBObject("userID", 1);

			DBCursor cursor = colluser.find(query, fields);

			while (cursor.hasNext()) {
				myUserID = cursor.next();
				System.out.println("user id" + myUserID);

			}

			myUserID.removeField("_id");

			BasicDBObject query2 = new BasicDBObject("fileID", id);
			BasicDBObject newDoc2 = new BasicDBObject().append("$push",
					new BasicDBObject("sharedWith", myUserID.get("userID")));
			colldocument.update(query2, newDoc2);
		}

		else {
			System.out
					.println("User does not have permission to share the file");

		}
	}

}
