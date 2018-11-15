<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<header>
	<table>
		<tr>
			<td id="headingCell">
				<h1>
					<spring:message code="header.headline" />
				</h1>
			</td>
			<td id="headerBtnsCell">
				<div id="undoAndRedoBtns">
					<form action="/undo" method="post">
						<c:choose>
							<c:when test="${canUndo}">
								<input type="submit" value="" id="undoBtn" class="headerBtnWithImgBackground"/>
							</c:when>
							<c:otherwise>
								<input type="submit" value="" id="disabledUndoBtn" class="headerBtnWithImgBackground" disabled/>
							</c:otherwise>
						</c:choose>
					</form>
					<form action="/redo" method="post">
						<c:choose>
							<c:when test="${canRedo}">
								<input type="submit" value="" id="redoBtn" class="headerBtnWithImgBackground"/>
							</c:when>
							<c:otherwise>
								<input type="submit" value="" id="disabledRedoBtn" class="headerBtnWithImgBackground" disabled />
							</c:otherwise>
						</c:choose>
					</form>
				</div>
				<form id="backupForm" action="/createBackup" method="get">
					<input type="submit" value="" id="backupBtn" class="headerBtnWithImgBackground"/>
				</form> <input type="button" value="" id="restoreBtn" class="headerBtnWithImgBackground"/>
			</td>
		</tr>
	</table>
</header>


<script type="text/javascript">
	function LoadByName(windowHeight, windowWidth) {
		var centerWidth = (window.screen.width - windowWidth) / 2;
		var centerHeight = (window.screen.height - windowHeight) / 2;
		newWindow = window.open('restore-from-backup', 'upload-file-form-window', 'resizable=0,width='
				+ windowWidth + ',height=' + windowHeight + ',left='
				+ centerWidth + ',top=' + centerHeight);
		newWindow.divHiding(1);
		newWindow.focus();
	}
</script>