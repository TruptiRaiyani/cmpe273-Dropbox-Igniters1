<html>
<head>
<script>    
    window.onload = function () {
    	var googleJqueryLink = "//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js";
    	loadScript(googleJqueryLink, onLoad);
    }
     
     function loadScript(url, callback) {
         var head = document.getElementsByTagName('head')[0];
         var script = document.createElement('script');
         script.type = 'text/javascript';
         script.src = url;
         script.onreadystatechange = callback;
         script.onload = callback;
         head.appendChild(script);
     }             
    
    function onLoad(){
    	$(':file').change(function(){
		    var file = this.files[0];
		    name = file.name;
		    size = file.size;
		    type = file.type;
		    //Your validation
	    	$("#progressBar").css("visibility","visible");
	    });
    	$(':button').click(function(){
		    var formData = new FormData($('form')[0]);
		    $.ajax({
		        url: document.URL,  
		        type: 'POST',
		        xhr: function() {  
		            var myXhr = $.ajaxSettings.xhr();
		            if(myXhr.upload){ // Check if upload property exists
		                myXhr.upload.addEventListener('progress',progressHandlingFunction, false); // For handling the progress of the upload
		            }
		            return myXhr;
		        },
		        beforeSend: beforeSendHandler,
		        success: completeHandler,
		        error: errorHandler,
		        data: formData,
		        //Options to tell jQuery not to process data or worry about content-type.
		        cache: false,
		        contentType: false,
		        processData: false
		    });
	   });
    }    
    
    function errorHandler(){
    	
    }
    
    function beforeSendHandler(){
    	
    }
    
    function completeHandler(){
    	$("#progressBar").css("visibility","hidden");
    }
	    
    function progressHandlingFunction(e){
        if(e.lengthComputable){
            $('progress').attr({value:e.loaded,max:e.total});
        }
    }
	    
</script>
</head>
<body>
	<div>
		<form enctype="multipart/form-data">
			<input name="file" type="file" /> <input type="button"
				value="Upload" />
		</form>
		<progress id="progressBar" style="visibility:hidden;"></progress>
	</div>
</body>
</html>