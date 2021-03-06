<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css">	   
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
<title>Dropbox</title>
</head>
<style type="text/css">
div.cn {
    width: 100%;
    height: 100%;
    vertical-align: middle;
    text-align: center;
}

div.inner {
    display: inline-block;
    width: 330px;
    height: 256px;
}
</style>
<body bgcolor="#4FAEE6">
 <script>
 function login(){
	 var username= $("#user").val();
     var password= $("#pass").val();        
        $.ajax({
           type: "post",
           url: "http://localhost:8080/dropbox/v1/users/login",
           contentType: "application/json; charset=utf-8",     
           dataType: "json",
           data: JSON.stringify({ "username": username, "password" : password }),
           success: function(result){
             window.location = "/dropbox/v1/users/"+ result.userID + "/home";     
           },
           error: function(xhr){
             var errorStr = 'HTTP Error: ' + xhr.status + ' ' +  xhr.statusText + ' Login Failed!'
             $("#alertSpan").text(errorStr);
           }
        });              
       
 }
        
 </script>
 
 
<body style="width:100%;height:100%">  
 <div class="cn">
    <div class="inner" id="loginform">
        <form class="form-signin">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input id="user" name="username" type="text" class="form-control" placeholder="Username" required autofocus>
        <input id="pass" name="password" type="password" class="form-control" placeholder="Password" required>
       &nbsp&nbsp&nbsp
        <button class="btn btn-lg btn-primary btn-block"  type="button" onclick="login()">Sign in</button>
	    <span id='alertSpan' style = "color:red"></span>
	    &nbsp&nbsp
	    <a href="/dropbox/v1/users/signup">Register Me</a>
      </form>
    </div>
</div>

 </body>
</html>