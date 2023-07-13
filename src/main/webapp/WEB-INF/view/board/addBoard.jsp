<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>addBoard.jsp</title>
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
	});
	</script>
</head>
<body>
	<h1>게시물 입력</h1>
	<form method="post" action="/board/addBoard" enctype="multipart/form-data">
		<div>
			지역명 : <select name="localName">
				<c:forEach var="m" items="${localNameList}">
					<option>${m.localName}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			제목 : <input type="text" name="boardTitle" required>
		</div>
		<div>
			내용 : <textarea name="boardContent" required></textarea>
		</div>
		<div id="boardFileList">
			<div>
				<button type="button" id="addFile">추가</button>
				<button type="button" id="delFile">삭제</button>
			</div>
			이미지 : <input type="file" name="multipartFile" class="boardFile">
		</div>
		<div>
			작성자 : <input type="text" name="memberId" required>
		</div>
		<div>
			<button type="submit">입력</button>
		</div>
	</form>
</body>
</html>