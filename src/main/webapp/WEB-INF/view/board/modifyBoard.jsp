<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>modifyBoard.jsp</title>
</head>
<body>
	<h1>게시물 수정</h1>
	<form method="post" action="/board/modifyBoard">
		<input type="hidden" name="boardNo" value="${board.boardNo}">
		<div>
			지역명 : <select name="localName">
				<c:forEach var="m" items="${localNameList}">
					<c:set var="isSelected" value=""></c:set>
					<c:if test="${m.localName == board.localName}">
						<c:set var="isSelected" value="selected"></c:set>
					</c:if>
					<option ${isSelected}>${m.localName}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			제목 : <input type="text" name="boardTitle" value="${board.boardTitle}">
		</div>
		<div>
			내용 : <textarea name="boardContent" rows="3" cols="50">${board.boardContent}</textarea>
		</div>
		<div>
			작성자 : <input type=text name="memberId" value="${board.memberId}" readonly> 
		</div>
		<div>
			생성일 : ${board.createdate}
		</div>
		<div>
			최근수정일 : ${board.updatedate}
		</div>
		<div>
			<button type="submit">수정</button>
		</div>
	</form>
</body>
</html>