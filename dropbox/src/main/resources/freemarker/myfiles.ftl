<html>
<head>
 <title>Home</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" media="screen">
            <!-- Optional theme -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
<script>  
</script>
</head>
<body>
	 <div class="container">
                <div class="jumbotron">
                    <!-- calls getBooks() from HomeResource -->
                    <table class="table table-hover">
                    <#list myfiles as file>
                        <tr>
                            <td>${file.name}</td>
                           <td>${file.fileID}</td> 
                           
                            <td><button id="${file.fileID}" type="button" >View</button></td>
                       
                        </tr>
                    </#list>
                    </table>
                </div>
            </div> <!-- end of container -->
             <!-- script tags -->
            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="//code.jquery.com/jquery.js"></script>
            <!-- Latest compiled and minified JavaScript -->
            <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
           <script >
           
           $(":button").click(function() {
	var fileID = this.id;
	//alert('About to report lost on ISBN ' + isbn);
	//alert(status);
	
	$.ajax({
		  type: "GET",
		  url: "/dropbox/v1/users/1/mydoc/" + fileID ,
	
		  dataType: "json",
		  contentType: "application/json",
		 
		  success: function(data) {
			  //  alert('please refresh the page to see current status of books');
			  
			  }
		});
});
           </script>
</body>
</html>