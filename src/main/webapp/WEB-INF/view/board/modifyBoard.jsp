<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>우리들의 여행지도</title>
	<link rel="icon" href="/img/favicon.png">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script>
	$(document).ready(function(){
		$('#addFile').click(function(){
			if($('.boardFile').length == 0) {
				$('#boardFileList').append('<input type="file" name="multipartFile" class="boardFile">');
			} else {
				if($('.boardFile').last().val() == '') {
					alert('빈 파일업로드 태그이 있습니다');
				} else {
					$('#boardFileList').append('<input type="file" name="multipartFile" class="boardFile">');
				}
			}
		});
		
		$('#delFile').click(function(){
			if($('.boardFile').length == 1){
				return;
			}
			if($('.boardFile').last().val() != '') {
				alert('빈 파일업드로 태그가 없습니다.');
			} else {
				$('.boardFile').last().remove();
			}
		});
		$('.delBoardfile').click(function(){
			$(this).prev('img').remove();
			$(this).next('input').remove();
			$(this).remove();
		});
	});
	</script>
</head>
<body>
	<h1>게시물 수정</h1>
	<form method="post" action="/board/modifyBoard" enctype="multipart/form-data">
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
		<div id="boardFileList">
			<c:forEach var="bf" items="${boardfile}">
				<img src="/upload/${bf.saveFilename}" width="200px" height="200px">
				<button type="button" class="delBoardfile">삭제</button>
				<input type="hidden" name="boardfileNo" value="${bf.boardfileNo}">
			</c:forEach>
			<div>
				<button type="button" id="addFile">추가</button>
				<button type="button" id="delFile">삭제</button>
			</div>
			이미지 추가 : <input type="file" name="multipartFile" class="boardFile">
		</div>
		<div>
			<button type="submit">수정</button>
		</div>
	</form>
</body>
</html>