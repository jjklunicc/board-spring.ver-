<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>우리들의 여행지도</title>
	<link rel="icon" href="/img/favicon.png">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
	<script>
		$(document).ready(function(){
			const x = [];
			const y = [];
			
			// 동기 호출로 x,y값을 세팅
			$.ajax({
				async: false, //true:비동기(기본값), false:동기
				url: '/rest/localNameList',
				type: 'get',
				success: function(model){
					// 원래 역할 : 백엔드 모델을 프론트엔드 모델로 변경
					
					// model -> {'model':[{localName: '부산', cnt: 10},,,,]}
					model.forEach(function(item, index){
						$('#target').append('<tr>');
						$('#target').append('<td>' + item.localName + '</td>');
						$('#target').append('<td>' + item.cnt + '</td>');
						$('#target').append('</tr>');
						x.push(item.localName);
						y.push(item.cnt);
					});
				}
			});

			new Chart("target2", {
			  type: "bar",
			  data: {
			    labels: x,
			    datasets: [{
			      label: "게시글수",
			      data: y,
			      backgroundColor: ["red", "orange", "yellow", "green", "blue", "navy", "purple", "gray"]
			    }]
			  },
			  options: {
				  title: {
				      display: true,
				      text: "지역별 게시글수"
				  }
			  }
			});
		});
	</script>
</head>
<body>
	<h1>AJAX API 사용으로 localNameList 가져오기</h1>
	
	<table border="1">
		<thead>
			<tr>
				<th>지역이름</th>
				<th>게시글수</th>
			</tr>
		</thead>
		<tbody id="target">
		</tbody>
	</table>
	<canvas id="target2" style="width:100%;max-width:700px"></canvas>
</body>
</html>