<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>우리들의 여행지도</title>
	<link rel="icon" href="/img/favicon.png">
</head>
<body>
	<div>
		<h1>게시물 정보</h1>
		<div>
			지역명 : ${board.localName}
		</div>
		<div>
			제목 : ${board.boardTitle}
		</div>
		<div>
			내용 : ${board.boardContent}
		</div>
		<div>
			작성자 : ${board.memberId}
		</div>
		<div>
			생성일 : ${board.createdate}
		</div>
		<div>
			최근수정일 : ${board.updatedate}
		</div>
		<div>
			<c:forEach var="bf" items="${boardfile}">
				<img src="/upload/${bf.saveFilename}" width="200px" height="200px">
			</c:forEach>
		</div>
		<div>
			<a href="/board/modifyBoard?boardNo=${board.boardNo}">수정</a>
			<a href="/board/deleteBoard?boardNo=${board.boardNo}">삭제</a>
		</div>
	</div>
</body>
</html>