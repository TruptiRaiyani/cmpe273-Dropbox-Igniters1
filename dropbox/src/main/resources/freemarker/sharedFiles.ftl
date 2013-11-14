<html>
        <body>
            <!-- calls getBooks() from HomeResource -->
            <table border="1">
            <#list sharedFiles as file>
                <tr>
                	<td>${file.fileID}</td>
                    <td>${file.name}</td>
                    <td>${file.accessType}</td>
                    <td>${file.owner}</td>
                </tr>
            </#list>
            </table>
        </body>
</html>