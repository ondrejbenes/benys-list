<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<script src="/js/jquery/jquery.js"></script>

	<c:choose>
		<c:when test="${loggedIn}">
			<form action="${uploadUrl}" method="post"
				enctype="multipart/form-data" class="uploadFileForm">
				<input type="file" name="file">
				<input type="submit" value="Upload" />
				<input type="button" value="Cancel" onclick="window.close()" />
			</form>
		</c:when>
		<c:otherwise>
			<p>Please log in!</p>
		</c:otherwise>
	</c:choose>

	<script type="text/javascript">
		$("document").ready(function() {
			$(".uploadFileForm").submit(function(event) {
				window.close();
			});
		});
	</script>

</body>
</html>