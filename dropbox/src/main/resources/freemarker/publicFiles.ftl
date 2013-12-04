<table class="table table-hover">
                    
                    <th>File Name</th>
                    <th>File ID</th>
                    <th>Access Type</th>
                    <th>Action</th>
                    <#list publicFiles as file>
                        <tr>
                            <td>${file.filename}</td>
                           <td>${file.metadata.fileID}</td> 
                           <td>${file.metadata.accessType}</td>
                           <td><button id="V${file.metadata.fileID}" type="button" name="View" value="${userID}" class="searchMe" >VIEW</button></td>
                         
                        </tr>
                    </#list>
                    </table>
                    <script>
                       $(".searchMe").click(function() {
				        var str = this.id;
				           
						var fileID = str.substring(1);
				        var userID = this.value;
				        $.ajax({
		                  type: "GET",
		                  url:"/dropbox/v1/users/"+userID+"/publicFiles/" + fileID, 
		                  dataType: "json",
		                  contentType: "application/json",
		                 
		                  success: function(data) {
		                          //  alert('please refresh the page to see current status of books');
		                          
	                          }
			                });
				               
				});
				</script>