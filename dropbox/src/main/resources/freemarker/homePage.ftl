<html>
<head>
 <title>Home</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" media="screen">
            <!-- Optional theme -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
            <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"> </script>

<script>  
</script>
</head>
<body>

	 <div class="container">
                <div class="jumbotron">
                    <!-- calls getBooks() from HomeResource -->
                    <div>
                    <p>welcome ${user.firstName} ${user.lastName}</p>
                    <button id="logout" type="button" name="logout" >Log Out</button>
                    </div>
                    <div>
                    <button id="myFiles" type="button" name="myFiles" >My Files</button>
                    <button id="upload" type="button" name="upload" >Upload File</button>
                    <button id="sharedFiles" type="button" name="sharedFiles" >Shared Files</button>
                    <button id="publicFiles" type="button" name="publicFiles" >Public Files</button>
                    </div>
                    
                </div>
            </div> <!-- end of container -->
            
          
	        
	 
	    </div> 

             <!-- script tags -->
            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="//code.jquery.com/jquery.js"></script>
            <!-- Latest compiled and minified JavaScript -->
            <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
            <script >
           
           $("#myFiles").click(function() {
			window.location = "/dropbox/v1/users/"+${userID}+"/myfiles";
			});

           $("#upload").click(function() {
			window.location = "/dropbox/v1/users/"+${userID}+"/upload";
			});
		
		$("#sharedFiles").click(function() {
		window.location = "/dropbox/v1/users/"+${userID}+"/sharedFiles";
			});
			
		$("#publicFiles").click(function() {
		window.location = "/dropbox/v1/users/"+${userID}+"/publicFiles";
			});
		$("#logout").click(function() {
		window.location = "/dropbox/v1/users/login";
			});
           </script>
           
          
</body>
</html>