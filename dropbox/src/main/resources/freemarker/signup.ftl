<html>
<head>
 <title>Home</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" media="screen">
            <!-- Optional theme -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
         <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>   
<script>  
</script>
</head>
        <body>
          
            <table border="1">
           
                <tr>
                	<td >
         			   First Name:
         			 </td>
         			 <td>
          				  <input type="text" id="firstName" name="firstName" value="">
        			  </td>
                </tr>
           		<tr>
                	<td >
         			   Last Name:
         			 </td>
         			 <td>
          				  <input type="text" id="lastName" name="lastName" value="">
        			  </td>
                </tr>
                <tr>
                	<td >
         			   Email:
         			 </td>
         			 <td>
          				  <input type="text" id ="email" name="email" value="">
        			  </td>
                </tr>
                 <tr>
        		  <td >
            		Password:
          			</td>
          			<td>
            			<input type="password" id="password" name="password" value="">
          			</td>
          		</tr>
                <tr>
                	<td >
         			   Designation:
         			 </td>
         			 <td>
          				 <select id="designation" name="designation">
							<option value="Software Engineer">Software Engineer</option>
							<option value="Manager">Manager</option>
							<option value="Team Lead">Team Lead</option>
						</select>
        			  </td>
                </tr>
                <tr><td> <button id="btnSubmit" type="submit" >Submit</button></td></tr>
            </table>
             <!-- script tags -->
            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="//code.jquery.com/jquery.js"></script>
            <!-- Latest compiled and minified JavaScript -->
            <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
                 
                 
                 
   <script >
           $(document).ready(function() {
           $("#btnSubmit").click(function() {
         
         //  alert( $('#firstName').val());
  
	var formData = {"firstName" : $('#firstName').val(), "lastName" : $('#lastName').val(), "password" : $('#password').val(), "email": $('#email').val(), "status" : "activated", "designation" : $('#designation').val()}
	$.ajax({
		  type: "POST",
		  url: "/dropbox/v1/users" ,
		  data : JSON.stringify(formData),
		  dataType: "json",
		  contentType: "application/json",
		 
		  success: function(data) {
			   // alert('please refresh the page to see current status of books');
			  
			  }
		});
});});
           </script>
        </body>
</html>