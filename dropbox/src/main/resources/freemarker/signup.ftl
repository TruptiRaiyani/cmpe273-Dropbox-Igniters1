<html>
<head>

 <title>SignUp</title>
             <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <!-- Latest compiled and minified CSS -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" media="screen">
            <!-- Optional theme -->
            <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
            <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"> </script> 
    
       
<script>  
</script>
</head>
<body >
       
          <div class="container">
                <div class="jumbotron">
      
        
           <div>
         			   First Name:
         			 
          				  <input type="text" id="firstName" name="firstName" value=""> *
        			  </div>
        			  <div>
         			   Last Name:
         			
          				  <input type="text" id="lastName" name="lastName" value=""> *
        			   </div>
        			   <div>
         			   User Name:
         			
          				  <input type="text" id="userName" name="userName" value=""> *
        			   </div>
        			  <div>
         			   Email:
         			
          				  <input type="text" id ="email" name="email" value=""> *
        			   </div>
        			  <div>
            		Password:
          			
            			<input type="password" id="password" name="password" value=""> *
          			 </div>
        			  <div>
         			   Designation:
         			
          				 <select id="designation" name="designation">
							<option value="Software Engineer">Software Engineer</option>
							<option value="Manager">Manager</option>
							<option value="Team Lead">Team Lead</option>
						</select> *
        			  </div>
        			  <div> <button id="btnSubmit" type="submit" >Submit</button> </div>
        			  
         
    </div>
</div></div>
             <!-- script tags -->
            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="//code.jquery.com/jquery.js"></script>
            <!-- Latest compiled and minified JavaScript -->
            <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
                 
                 
                 
   <script >
          
           $("#btnSubmit").click(function() {
         if(!validateForm())
         {        
         return;
         }
         var isExists = true;
       //  alert($('#userName').val());
         var formData = {"firstName" : $('#firstName').val(), "lastName" : $('#lastName').val(),"userName" : $('#userName').val(), "password" : $('#password').val(), "email": $('#email').val(), "status" : "activated", "designation" : $('#designation').val()}
	$.ajax({
		 type: "POST",
		  url: "/dropbox/v1/users" ,
		  data : JSON.stringify(formData),
		  dataType: "json",
		  contentType: "application/json",
		 
		 
		  success: function(data) {
			  isExists=data;
			  if(isExists)
				{
				alert('Email address already exists. Please try another one.');
				}
			else
			{
			alert('profile created successfully.');		
				 window.location = "/dropbox/v1/users/login";
			
			}
			 
			  }
		});
         
  
	
});


function validateForm()
{
if($('#firstName').val() == '' || $('#lastName').val() == '' || $('#password').val() == ''  || $('#email').val() == '')
{
//$('#errfn').innerHTML="this is invalid name";
alert('Please enter all required information');
return false;
}

else
return true;

}
function CreateUser()
{
var formData = {"firstName" : $('#firstName').val(), "lastName" : $('#lastName').val(), "password" : $('#password').val(), "email": $('#email').val(), "status" : "activated", "designation" : $('#designation').val()}
	$.ajax({
		  type: "POST",
		  url: "/dropbox/v1/users" ,
		  data : JSON.stringify(formData),
		  dataType: "json",
		  contentType: "application/json",
		 
		  success: function(data1) {
			    alert('profile created successfully.');
			  
			  }
		});
}
           </script>
        </body>
    
</html>