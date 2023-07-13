<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>deleteBoard.jsp</title>
</head>
<body>
	<form method="post" action="/board/deleteBoard">
		<input type="hidden" name="boardNo" value="${board.boardNo}">
		<div>
			삭제확인을 위해 아이디를 입력하세요 : <input type=text name="memberId"> 
		</div>
		<div>
			<button type="submit">삭제</button>
		</div>
	</form>
</body>
</html>