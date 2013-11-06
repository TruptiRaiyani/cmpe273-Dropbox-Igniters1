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
// Sina Starts		
	    @DELETE
	    @Path("/{userID}")
	    @Timed(name = "delete-user")
	    public Response deleteUserByEmail(@PathParam("userID") int userID) {
		// FIXME - Dummy code
	    	BasicDBObject user = new BasicDBObject();
	    	user.put("userID", userID);
	    	DBCursor cursor = colluser.find(user);

	    	while (cursor.hasNext()){
	    		 BasicDBList e = (BasicDBList) cursor.next().get("myFiles"); 
	    		 for (int i=0;i<e.size();i++) {
	    			 BasicDBObject file = new BasicDBObject();
	    		    	file.put("fileID", e.get(i));
	    		    	colldocument.remove(file);
	    			 }
	    	}

	    	colluser.remove(user);
	    	return Response.status(201).entity(new LinkDto("create-user", "/users","POST")).build();
	    }

	    @GET
	    @Path("/{userID}/filesShared/{id}")
	    @Timed(name = "view-filesShared")
	    public Response getFilesSharedByEmailById(@PathParam("userID") int userID, @PathParam("id") int id) {

	    	BasicDBObject andQuery = new BasicDBObject();
	    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    	obj.add(new BasicDBObject("fileID", id));
	    	obj.add(new BasicDBObject("sharedWith", userID));
	    	andQuery.put("$and", obj);
	    	DBCursor cursor = colldocument.find(andQuery);
	    	String output = "";
	    	while(cursor.hasNext()) {
	    	    output +=cursor.next();
	    	}

	    	return Response.status(200).entity(output).build();
	    }
	    
	    @POST
	    @Timed(name = "create-user")
	    public Response setUserByEmail(User user) {

	    	BasicDBObject query = new BasicDBObject();
	    	BasicDBObject field = new BasicDBObject();
	    	field.put("userCount", 1);
	    	DBCursor cursor = colluser.find(query,field);
	    	int userID=99;
	    	BasicDBObject obj = (BasicDBObject) cursor.next();
	    	userID=obj.getInt("userCount"); 	
	   
	    	BasicDBObject ob = new BasicDBObject();
	    	ob.append("userID", userID);
	    	ob.append("firstName", user.getFirstName());
	    	ob.append("lastName", user.getLastName());
	    	ob.append("password", user.getPassword());
	    	ob.append("email", user.getEmail());
	    	ob.append("status", user.getStatus());
	    	ob.append("designation", user.getDesignation());
	    	ob.append("myFiles",new ArrayList<String>());
	    	ob.append("filesShared",new ArrayList<String>());   	
	    	colluser.insert(ob);
	    	BasicDBObject countQuery = new BasicDBObject().append("userCount", userID);
	    	BasicDBObject newDoc = new BasicDBObject();
	    	newDoc.append("$set", new BasicDBObject("userCount",++userID));
	    	colluser.update(countQuery,newDoc );
	    	
	    	LinksDto links = new LinksDto();
	    	links.addLink(new LinkDto("view-user", "/users/" + user.getEmail(),
	    		"GET"));
	    	links.addLink(new LinkDto("update-user",
	    		"/users/" + user.getEmail(), "PUT"));
	    	links.addLink(new LinkDto("update-user",
	        		"/users/" + user.getEmail(), "POST"));
	    	links.addLink(new LinkDto("delete-user",
	        		"/users/" + user.getEmail(), "DELETE"));
	    	links.addLink(new LinkDto("create-file",
	        		"/users/" + user.getEmail() +"/files", "POST"));

	    	return Response.status(201).entity(links).build();
	  	
	    }
	    
	    @ GET
	    @Path("/{userID}")
	    @Timed(name = "view-user")
	    public Response getUserByUID(@PathParam("userID") int uID) {
	    	
	    	DBCursor cursor = colluser.find(new BasicDBObject().append("userID", uID ));
	    	
	    	String output = "";
	    	while(cursor.hasNext()) {
	    		output +=cursor.next();
	    	
	    	}

	    	return Response.status(200).entity(output).build();
	    }
// Sina Ends	    
}
