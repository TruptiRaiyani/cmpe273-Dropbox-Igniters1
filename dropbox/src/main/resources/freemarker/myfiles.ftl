<html>
<head>
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">           
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
 <title>MyFiles</title>
           

<script>  
</script>
</head>
<body>
<div>
 </div>
 <div class="container">
                <div class="jumbotron">
                    <!-- calls getBooks() from HomeResource -->
                    <table class="table table-hover" border="1">
                    <#list myfiles as file>
                    <tr> <td> <button id="home" type="button" name="home" value="${file.owner}" >Home</button>
<button id="logout" type="button" name="logout" value="${file.owner}" >Log Out</button> </td> </tr>
                        <tr>
                            <td>${file.name}</td>
                           <td>${file.fileID}</td> 
                           
                           <td><button id="V${file.fileID}" type="button" name="View" value="${file.owner}" >VIEW</button></td>
                            <td><button id="D${file.fileID}" type="button" name="Delete" value="${file.owner}" >DELETE</button></td>
                        
                        </tr>
                    </#list>
                    </table>
                </div>
            </div> <!-- end of container -->
             
	 </center>
          
	        
	 
	  

             <!-- script tags -->
            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="//code.jquery.com/jquery.js"></script>
            <!-- Latest compiled and minified JavaScript -->
            <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
            <script >
           
           $(":button").click(function() {
        var str = this.id;
           
	var fileID = str.substring(1);
       var userID = this.value;
        if(this.name == 'View')
	{
        $.ajax({
                  type: "GET",
                  url: "/dropbox/v1/users/"+userID+"/mydoc/" + fileID ,
        
                  dataType: "json",
                  contentType: "application/json",
                 
                  success: function(data) {
                          //  alert('please refresh the page to see current status of books');
                          
                          }
                });
                }
                else if(this.name == "Delete")
		{
		
		$.ajax({
		  type: "DELETE",
		  url: "/dropbox/v1/users/"+ userID +"/files/" + fileID ,
	
		  dataType: "json",
		  contentType: "application/json",
		 
		  success: function(data) {
			  
			  }
		});}
		
        else  if(this.name == 'logout')
	{
	 window.location = "/dropbox/v1/users/login";
	}
	 else  if(this.name == 'home')
	{
	 window.location = "/dropbox/v1/users/"+userID+"/home";
	}
	        
});
           </script>
           
</body>
</html>