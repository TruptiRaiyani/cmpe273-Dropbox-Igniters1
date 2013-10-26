package edu.sjsu.cmpe.dropbox.api.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.dropbox.domain.User;
import edu.sjsu.cmpe.dropbox.dto.FileDto;
import edu.sjsu.cmpe.dropbox.dto.LinkDto;
import edu.sjsu.cmpe.dropbox.dto.LinksDto;
import edu.sjsu.cmpe.dropbox.view.UploadView;


@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DropboxResource {
	
	private MongoDBInstance mongodb = new MongoDBInstance();
	private DBCollection colluser = mongodb.getColluser();
	private DBCollection colldocument = mongodb.getColldocument();
	
	private DropboxFileManagement manageFile = new DropboxFileManagement();
	
//	Aradhana		
		@GET    
	    @Path("/{userID}/files")
	    @Produces(MediaType.TEXT_HTML)
	    @Timed(name = "upload-file")
	    public UploadView getUploadPage(@PathParam("userID") int userID) {
			return manageFile.getUploadPage(userID);
	    }    
		
		@GET
		@Path("/{userID}/files/{id}")
		@Timed(name = "view-file")
		public FileDto getMyFileByUserIdById(@PathParam("userID") int userID, @PathParam("id") int id) {
			return manageFile.getMyFileByUserIdById(userID, id);
		}
		
		@POST
	    @Path("/{userID}/files")
	    @Consumes(MediaType.MULTIPART_FORM_DATA)
	    @Timed(name = "create-file")    
	    public Response fileUpload(@PathParam("userID") int userID, @FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileInfo) throws IOException
	    {
	        return manageFile.fileUpload(userID, uploadedInputStream, fileInfo);
	    } 

		@DELETE
		@Path("/{userID}/files/{id}")
		@Timed(name = "delete-file")
		public LinkDto deleteMyFileByUserIdAndId(@PathParam("userID") int userID, @PathParam("id") Integer id) {
	      return manageFile.deleteMyFileByUserIdAndId(userID, id);
		}
		
		@PUT
		@Path("/{userID}/files/{id}")
		@Timed(name = "update-files")
		public void updateFileByEmail(@PathParam("userID") int userID,	@PathParam("id") int id, @QueryParam("sharedWith") String firstName) {
	        manageFile.updateFileByEmail(userID, id, firstName);
		}
// Aradhana ends
		
}
