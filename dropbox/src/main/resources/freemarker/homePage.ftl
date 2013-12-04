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
                    <form method="post" role="search">
                    	<input type="text" name="name" class="form-control" placeholder="Search" value="${name}">
                    </form>
                    <button type="submit" id="searchButton">Submit</button>
                    <button id="logout" type="button" name="logout" >Log Out</button>
                    </div>
                    <div id="publicFiles"></div>
                    <div>
                    <button id="myFiles" type="button" name="myFiles" >My Files</button>
                    <button id="upload" type="button" name="upload" >Upload File</button>
                    <button id="sharedFiles" type="button" name="sharedFiles" >Shared Files</button>
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
			window.location = "/dropbox/v1/users/"+${userID}+"/files";
			});
		
		$("#sharedFiles").click(function() {
		window.location = "/dropbox/v1/users/"+${userID}+"/sharedFiles";
			});

		$("#logout").click(function() {
		window.location = "/dropbox/v1/users/login";
			});
			
			$.fn.serializeObject = function()
		{
		    var o = {};
		    var a = this.serializeArray();
		    $.each(a, function() {
		        if (o[this.name] !== undefined) {
		            if (!o[this.name].push) {
		                o[this.name] = [o[this.name]];
		            }
		            o[this.name].push(this.value || '');
		        } else {
		            o[this.name] = this.value || '';
		        }
		    });
		    return o;
		};
		
	    $('#searchButton').click(function() {
//		alert(JSON.stringify($('form').serializeObject()));
		$.ajax({
		url: "/dropbox/v1/users/${userID}/publicFiles",
        type: 'POST',    
        contentType: 'application/json',
        data:JSON.stringify($('form').serializeObject()), 
        error:function(){
        alert("something is wrong!");
  		},
        success: function(data) {
           $("#publicFiles").empty().append(data);
  	}
  	});

	    });


           </script>
           
		   
</body>
</html>