<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="beny" tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-112463679-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-112463679-1');
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Beny's TODO List</title>
<link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
</head>
<body>
	<script src="/js/jquery/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-ui/jquery-ui.js"></script>

	<jsp:include page="header.jsp" />

	<section id="content">
		<c:forEach items="${categoryTree.iterator}" var="item">
			<div id="${item.data.id}"
				class="category unwrapped childOf${item.data.parentCategoryId}"
				style="padding-left: ${item.depth * 10}px">
				<beny:category-header category="${item.data}" />
				<beny:entry-table categoryId="${item.data.id}"
					entries="${item.elements}" />
				<beny:add-entry-form categoryId="${item.data.id}" />
			</div>
		</c:forEach>
	</section>

	<jsp:include page="footer.jsp" />

	<script type="text/javascript" src="js/todo-list.js"></script>
	<script type="text/javascript">
		$("document").ready(function() {
			initWindow();
			initList();
			initButtons();
		});
	</script>
</body>
</html>