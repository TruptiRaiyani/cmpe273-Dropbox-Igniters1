package edu.sjsu.cmpe.dropbox.api.resources;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.yammer.metrics.annotation.Timed;

import edu.sjsu.cmpe.dropbox.domain.User;
import edu.sjsu.cmpe.dropbox.domain.userFile;
import edu.sjsu.cmpe.dropbox.dto.FileDto;
import edu.sjsu.cmpe.dropbox.dto.LinkDto;
import edu.sjsu.cmpe.dropbox.dto.LinksDto;
import edu.sjsu.cmpe.dropbox.view.LoginView;
import edu.sjsu.cmpe.dropbox.view.UploadView;
import edu.sjsu.cmpe.dropbox.view.shareView;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;


@Path("/v1/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DropboxResource {
	
	private MongoDBInstance mongodb = new MongoDBInstance();
	private DB dropboxDB= mongodb.getdb();
	private DBCollection colluser = mongodb.getColluser();
	private DBCollection colldocument = mongodb.getColldocument();
	
	private DropboxFileManagement manageFile = new DropboxFileManagement();
	private Configuration cfg;
	private Template template;	

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
	@Timed(name = "update-sharedWith-file")
	public ResponseBuilder updateFileById(@PathParam("userID") int userID,	@PathParam("id") int id, @QueryParam("sharedWith") String username) {
        return manageFile.updateFileById(userID, id, username);
	}
	
//	
//	@PUT
//	@Path("/{userID}/files")
//	@Timed(name = "update-myFiles-file")
//	public ResponseBuilder updateMyFilesByFileId(@PathParam("userID") int userID, int id) {
//		
//       BasicDBObject query = new BasicDBObject("userID",userID);
//       BasicDBObject command = new BasicDBObject();
//       command.put("$push", new BasicDBObject("myFiles", id));
//       colluser.update(query, command);
//       return Response.status(200);		       
//	}
	
	

	@GET    
	@Path("/{userID}/myfiles/share")
	@Produces(MediaType.TEXT_HTML)
	@Timed(name = "share-file")
	public shareView getSharePage() {
		return new shareView();
	}
	
	
	@GET
    @Path("/{userID}/myfiles/share1")	  
	@Produces(MediaType.TEXT_HTML)
    @Timed(name = "search-user-for-file-share")
    public Response search(@PathParam("userID") long userid,@QueryParam("term")String term ) throws IOException {
		String template = "[{\"id\":\"Paul1\",\"label\":\"Paul1\",\"value\":\"Paul1\"},{\"id\":\"Susan\",\"label\":\"Susan\",\"value\":\"Susan\"},{\"id\":\"Andy\",\"label\":\"Andy\",\"value\":\"Andy\"},{\"id\":\"Lynette\",\"label\":\"Lynette\",\"value\":\"Lynette\"},{\"id\":\"Henry\",\"label\":\"Henry\",\"value\":\"Henry\"},{\"id\":\"Mike\",\"label\":\"Mike\",\"value\":\"Mike\"},{\"id\":\"Rennee\",\"label\":\"Rennee\",\"value\":\"Rennee\"},{\"id\":\"Felicia\",\"label\":\"Felicia\",\"value\":\"Felicia\"},{\"id\":\"Laura\",\"label\":\"Laura\",\"value\":\"Laura\"},{\"id\":\"Sara\",\"label\":\"Sara\",\"value\":\"Sara\"},{\"id\":\"George\",\"label\":\"George\",\"value\":\"George\"},{\"id\":\"Rex\",\"label\":\"Rex\",\"value\":\"Rex\"}]";
		 
//		String queryTerm = "\".*" + term + ".*\"";
//		BasicDBObject query = new BasicDBObject("username",term);
//		BasicDBObject field = new BasicDBObject("username",1);
//		field.append("_id",0);
//		DBCursor cursor = colluser.find(query,field);
//		while(cursor.hasNext()){
//			System.out.println(cursor.next());
//		}
	
		return Response.status(200).entity(template).build();
		//return template;
	}
	
	
	
	
	@GET    
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	@Timed(name = "login-file")
	public LoginView getLoginPage() {
		return new LoginView();
	}


	@POST
	@Path("/login")
	@Timed(name="login")	
	public Response getUsers(String loginJsonObStr) throws JsonParseException, IOException{	
		
	    Map<String,String> map = new HashMap<String,String>();
		ObjectMapper mapper = new ObjectMapper();
	    //convert JSON string to Map		
		try {			 
			//convert JSON string to Map
			map = mapper.readValue(loginJsonObStr, new TypeReference<HashMap<String,String>>(){});	 
			System.out.println(map);
	 
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		String userName =  ""; //(String) inputJsonObj.get("input");
	    String Password = ""; //(String) inputJsonObj.get("input");	    
		
	    Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        //System.out.println(pairs.getKey() + " = " + pairs.getValue());
	        switch(pairs.getKey().toString()){
	        	case "username": 
	        		userName = pairs.getValue().toString();
	        		break;
	        	case "password":
	        		Password = pairs.getValue().toString();
	        		break;
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
		
	    
		BasicDBObject query = new BasicDBObject();
		query.put("username", userName);

		BasicDBObject fields = new BasicDBObject("_id",0);
		fields.append("password", 1);

		DBCursor cursor = colluser.find(query,fields);
		String output = "";
		while (cursor.hasNext()) {
			output += cursor.next();
			System.out.println("user :"  + output);
		}

		ObjectMapper mapper1 = new ObjectMapper();
		
		com.fasterxml.jackson.core.JsonFactory factory = mapper.getFactory(); // since 2.1 use mapper.getFactory() instead
		JsonParser jp = factory.createJsonParser(output);
		JsonNode actualObj = mapper.readTree(jp);
		JsonNode pass = actualObj.get("password");
		
		
		if(pass.asText().equals(Password)){		
			System.out.println("password match");			
			return Response.status(200).entity(output).build();
		}
		else{
			System.out.println("password doesn't match");
			return Response.status(403).entity(output).build();
		}

	}
// Aradhana ends
// Sina Starts	
		@GET
		@Produces(MediaType.TEXT_HTML)
	    @Path("/{userID}/home")
	    public Response getHomePage(@PathParam("userID") int userID) 
	    {
			BasicDBObject query = new BasicDBObject("userID",userID);
			BasicDBObject user = (BasicDBObject)colluser.findOne(query);
			Writer output = new StringWriter();
			try {
	    		cfg = createFreemarkerConfiguration();
				template = cfg.getTemplate("homePage.ftl");
				SimpleHash root = new SimpleHash();
				root.put("user", user);
				root.put("userID", userID);
				template.process(root, output);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return Response.status(201).entity(output.toString()).build();
	    }
		
		
	    @DELETE
	    @Path("/{userID}")
	    @Timed(name = "delete-user")
	    public Response deleteUserByEmail(@PathParam("userID") int userID) {
		// FIXME - Dummy code
	    	BasicDBObject user = new BasicDBObject();
	    	user.put("userID", userID);
	    	DBCursor cursor = colluser.find(user);
	    	GridFS myFS = new GridFS(dropboxDB, "document");	
	    	while (cursor.hasNext()){
	    		 BasicDBList e = (BasicDBList) cursor.next().get("myFiles"); 
	    		 for (int i=0;i<e.size();i++) {
	    		    	myFS.remove(new BasicDBObject().append("metadata.fileID", e.get(i)));
	    			 }
	    	}

	    	colluser.remove(user);
	    	return Response.status(201).entity(new LinkDto("create-user", "/users","POST")).build();
	    }

	    @GET
	    @Path("/{userID}/filesShared/{id}")
	    @Timed(name = "view-filesShared")
	    public Response getFilesSharedByEmailById(@PathParam("userID") int userID, @PathParam("id") int id) throws IOException{

	    	BasicDBObject andQuery = new BasicDBObject();
	    	List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
	    	obj.add(new BasicDBObject("metadata.fileID", id));
	    	obj.add(new BasicDBObject("metadata.sharedWith", userID));
	    	andQuery.put("$and", obj);

	    	GridFS myFS = new GridFS(dropboxDB, "document");	
	    	 
			GridFSDBFile getfile = myFS.findOne(andQuery);
			String directoryName = "C:/testDB";
			File theDir = new File(directoryName);
			  // if the directory does not exist, create it
			  if (!theDir.exists()) {
			    System.out.println("creating directory: " + directoryName);
			    boolean result = theDir.mkdir();  

			     if(result) {    
			       System.out.println("DIR created");  
			     }
			  }
			  String filePath = "C:/testDB/" + getfile.getFilename();
			  File yourFile = new File(filePath);
			  if(!yourFile.exists()) {
			      yourFile.createNewFile();
			  } 
			FileOutputStream ofile = new FileOutputStream(filePath);
				
		     getfile.writeTo(ofile);
		     Desktop d =  Desktop.getDesktop();
		     d.open(yourFile);

	    	
	    	return Response.status(200).entity(true).build();
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
	    public Response getUserByUID(@PathParam("userID") int userID) {
	    	
	    	DBCursor cursor = colluser.find(new BasicDBObject().append("userID", userID ));
	    	
	    	String output = "";
	    	while(cursor.hasNext()) {
	    		output +=cursor.next();
	    	
	    	}

	    	return Response.status(200).entity(output).build();
	    }

		private Configuration createFreemarkerConfiguration() {
	        Configuration retVal = new Configuration();
	        retVal.setClassForTemplateLoading(DropboxResource.class, "/freemarker");
	        return retVal;
	    }

	    @GET
	    @Path("/{userid}/sharedFiles")
	    @Produces(MediaType.TEXT_HTML)
	    @Timed(name = "Get-filesshared")
	    public Response getSharedFilesByUserID(@PathParam("userid") Integer userID) {
	    	List<userFile> uf = new ArrayList<userFile>();
    		GridFS myFS = new GridFS(dropboxDB, "document");
		
    		userFile uf1 = null;
    		List<GridFSDBFile> getfile = myFS.find(new BasicDBObject("metadata.sharedWith" , userID ));
    		Writer output = new StringWriter();	    		
    		Iterator< GridFSDBFile> eachFile =  getfile.iterator();
    		while( eachFile.hasNext() )
    			{			 
    			uf1 = new userFile();
    			GridFSDBFile gf = eachFile.next();
    			uf1.setName(gf.getFilename());
    			DBObject db1 = gf.getMetaData();
    			uf1.setFileID((Integer) db1.get("fileID"));	  
    			uf1.setOwner((Integer)db1.get("owner"));
    			uf1.setAccessType((String)db1.get("accessType"));
    			uf.add(uf1);
    			}
	    	
	    	try {
	    		cfg = createFreemarkerConfiguration();
				template = cfg.getTemplate("sharedFiles.ftl");
				SimpleHash root = new SimpleHash();
				root.put("sharedFiles", uf);
				root.put("userID", userID);
				template.process(root, output);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	    	String output = "";
//	    	while(cursor.hasNext()) {
//	    	    output +=cursor.next();
//	    	}
	    	return Response.status(200).entity(output.toString()).build();
	    }
		@GET
	    @Path("/{userID}/doc")
	    public void createTestFiles(@PathParam("userID") int userID) throws IOException
	    {
	    FileInputStream inpus = null;
	    GridFS myFS = new GridFS(dropboxDB, "document");
	    GridFSInputFile file ;
	    BasicDBObject o;
	    inpus = new FileInputStream("C:\\Users\\Suavo\\Documents\\GitHub\\cmpe273-Dropbox-Igniters\\dropbox\\README.md");
	   // 
	    
	   file =  myFS.createFile(inpus , "README.md");
	     o = new BasicDBObject("owner" , 1);
	    o.append("fileID", 1);
	    o.append("accessType", "public");
	    o.append("fileType", "pdf");

		ArrayList<Integer> share = new ArrayList<Integer>();
		share.add(2);
		o.append("sharedWith", share);
	   file.setMetaData(o);
	   file.save();
	   
	   inpus = new FileInputStream("C:\\Users\\Suavo\\Documents\\GitHub\\cmpe273-Dropbox-Igniters\\dropbox\\JSON input.txt");
	   // 
	    
	   file =  myFS.createFile(inpus , "JSON input.txt");
	     o = new BasicDBObject("owner" , 2);
	    o.append("fileID", 2);
	    o.append("fileType", "txt");
	    o.append("accessType", "public");
	    share = new ArrayList<Integer>();
		o.append("sharedWith", share);
	   file.setMetaData(o);
	   file.save();
	   inpus = new FileInputStream("C:\\Users\\Suavo\\Documents\\GitHub\\cmpe273-Dropbox-Igniters\\dropbox\\index.html");
	   // 
	    
	   file =  myFS.createFile(inpus , "index.html");
	     o = new BasicDBObject("owner" , 1);
	    o.append("fileID", 3);
	    o.append("fileType", "doc");
	    o.append("accessType", "public");
	    share = new ArrayList<Integer>();
		o.append("sharedWith", share);
	   file.setMetaData(o);
	   file.save();
	   inpus = new FileInputStream("C:\\Users\\Suavo\\Desktop\\5.JPG");
	   // 
	    
	   file =  myFS.createFile(inpus , "5.JPG");
	    o = new BasicDBObject("owner" , 2);
	    o.append("fileID", 4);
	    o.append("fileType", "JPG");
	    o.append("accessType", "public");
	    share = new ArrayList<Integer>();
		share.add(1);
		o.append("sharedWith", share);
	   file.setMetaData(o);
	   file.save();
	   // retrive
	  
	    }
// Sina Ends	
	    
	    //Trupti Start
	    @GET
	    @Path("/{userID}/myfiles")
	    @Produces(MediaType.TEXT_HTML)
	    @Timed(name = "Get-myfiles")
	    public Response getMyFilesByUserID(@PathParam("userID") long userid) throws IOException {
	    		//MongoClient db = new MongoClient();
	    		//DB dbc = db.getDB("test");
	    		List<userFile> uf = new ArrayList<userFile>();
	    		GridFS myFS = new GridFS(mongodb.getdb(), "document");
			
	    		userFile uf1 = null;
	    		List<GridFSDBFile> getfile = myFS.find(new BasicDBObject("metadata.owner" , userid ));
	    		Writer output = new StringWriter();	    		
	    		Iterator< GridFSDBFile> eachFile =  getfile.iterator();
	    		while( eachFile.hasNext() )
	    			{			 
	    			uf1 = new userFile();
	    			GridFSDBFile gf = eachFile.next();
	    			uf1.setName(gf.getFilename());
	    			DBObject db1 = gf.getMetaData();
	    			uf1.setFileID((Integer) db1.get("fileID"));	  
	    			uf1.setOwner((Integer)db1.get("owner"));
	    			uf.add(uf1);
	    			}
	    		try {
		    		cfg = createFreemarkerConfiguration();
					template = cfg.getTemplate("myfiles.ftl");
					SimpleHash root = new SimpleHash();
					root.put("myfiles", uf);
					template.process(root, output);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		return Response.status(200).entity(output.toString()).build();
	    	//return new MyfilesView(manageFile.getMyFiles());
	    }
	    @PUT
	    @Path("/{userID}")
	    @Timed(name = "update-userdata")
	    public void updateUserdataByUserID(@PathParam("userID") long userid,@QueryParam("firstName") String firstName,@QueryParam("lastName") String lastName,@QueryParam("password") String password,@QueryParam("email") String email,@QueryParam("status") String status,@QueryParam("designation") String designation) {
		// FIXME - Dummy code
		   BasicDBObject ob = new BasicDBObject();
		   if(firstName != null)
	   	ob.append("firstName", firstName);
		   if(lastName != null)
	   	ob.append("lastName", lastName);
		   if(password != null)
	   	ob.append("password", password);
		   if(email != null)
	   	ob.append("email", email);
		   if(status != null)
	   	ob.append("status", status);
		   if(designation != null)
	   	ob.append("designation", designation);

	        	BasicDBObject query = new BasicDBObject().append("UserID", userid);
	        	BasicDBObject newDoc = new BasicDBObject().append("$set", ob);
	        	colluser.update(query,newDoc );
	        	
	   
	        	
	        	
}
	    @GET
		   @Path("/{userID}/mydoc/{fileID}")
		   @Timed(name = "update-userdata")
		    public void GetFile(@PathParam("fileID") long fileID) throws IOException
		    {
			  	  
		    	GridFS myFS = new GridFS(mongodb.getdb(), "document");	
		    	 
				GridFSDBFile getfile = myFS.findOne(new BasicDBObject("metadata.fileID" , fileID ));
				String directoryName = "C:/testDB";
				File theDir = new File(directoryName);
				  // if the directory does not exist, create it
				  if (!theDir.exists()) {
				    System.out.println("creating directory: " + directoryName);
				    boolean result = theDir.mkdir();  

				     if(result) {    
				       System.out.println("DIR created");  
				     }
				  }
				  String filePath = "C:/testDB/" + getfile.getFilename();
				  File yourFile = new File(filePath);
				  if(!yourFile.exists()) {
				      yourFile.createNewFile();
				  } 
				FileOutputStream ofile = new FileOutputStream(filePath);
					
			     getfile.writeTo(ofile);
			     Desktop d =  Desktop.getDesktop();
			     d.open(yourFile);
				
		    }
	    @GET
		   @Path("/signup")
	    @Produces(MediaType.TEXT_HTML)
		   @Timed(name = "signup")
		    public Response SignUp() 
		    {
	    	Writer output = new StringWriter();	
	    	try {
	    		cfg = createFreemarkerConfiguration();
				template = cfg.getTemplate("signup.ftl");
				SimpleHash root = new SimpleHash();
			//	root.put("myfiles", uf);
				template.process(root, output);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		return Response.status(200).entity(output.toString()).build();
		    }
	    @GET
		   @Path("/emailcheck/{email}")
		   @Timed(name = "update-userdata")
		    public boolean GetFile(@PathParam("email") String email)
	    {
	    	boolean isExists = false;
	    	DBCursor cursor = null;
	    	 cursor = colluser.find(new BasicDBObject("email", email ));
	    	if(cursor.itcount() > 0)
	    		isExists = true;
	    	return isExists;
	    }
	    //Trupti End

}
