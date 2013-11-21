<html>
<head>
 <title>Home</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" media="screen">
            <!-- Optional theme -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
            <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"> </script>
 		    <script type="text/javascript" src="js/script.js"></script>

<script>  
</script>
</head>
<body>
<div> <table><tr> <td> </td> </tr> </table> </div>
	 <div class="container">
                <div class="jumbotron">
                    <!-- calls getBooks() from HomeResource -->
                    <table class="table table-hover">
                    <#list myfiles as file>
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
            
          
	        
	 
	    </div> 

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
                
});
           </script>
           
          
</body>
</html>