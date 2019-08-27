<%@ page language ="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<h1 align="center">Spring에서의 ajax 사용 테스트 페이지 </h1>
	<!-- <button onclick="location.href='testtest.do'">테스트버튼</button> -->
	<ol>
	<li>
			서버 쪽 컨트롤러 값 보내기
			<button id="test1">테스트</button>
			
			<script>
				$(function(){
					$("#test1").on("click",function(){
						$.ajax({
							url:"test1",
							data:{name : "신사임당",age:"47"},
							type:"post",
							success: function(data){
								if(data=="ok"){
									alert("전송성공")
								}else{
									alert("전송 실패");
								}
							},
							error:function(request,status,errorData){
								alert("error code:" +request.status+"\n"+"message : "+request.responseText+"\n"
										+"error:"+errorData);
							}
						});
					})
				});
			
			</script>
	
	</li>
	
		<li>
			컨트롤러에서 리턴하는 JSON객체 받아서 출력하기
			<button id="test2">테스트</button>
			<div id="d2"></div>
			
			<script>
				$(function(){
					$("#test2").on("click",function(){
						$.ajax({
							url:"test2",
							dataType: "Json",
							success:function(data){
								//data = JSON.parse(data);
								
								$("#d2").html("번호 : " +data.no+
												"<br>제목 :"+data.title
												+"<br>작성자 :"+decodeURIComponent(data.writer)
												+"<br>내용 :"+decodeURIComponent(data.content.replace(/\+/g," ")));
								
							},
							error:function(request,status,errorData){
								alert("error code:" +request.status+"\n"+"message : "+request.responseText+"\n"
										+"error:"+errorData);
							}
						});
						
					});
				});
			</script>
		</li>
		
		
		
			<li>
			컨트롤러에서 리턴하는 JSON객체 받아서 출력하기
			<button id="test3">테스트</button>
			<div id="d3"></div>
			
			<script>
				$(function(){
					$("#test3").on("click",function(){
						$.ajax({
							url:"test3",
							//dataType: "Json",
							success:function(data){
								//data = JSON.parse(data);
								
								$("#d3").html("번호 : " +data.no+
												"<br>제목 :"+data.title
												+"<br>작성자 :"+decodeURIComponent(data.writer)
												+"<br>내용 :"+decodeURIComponent(data.content.replace(/\+/g," ")));
								
							},
							error:function(request,status,errorData){
								alert("error code:" +request.status+"\n"+"message : "+request.responseText+"\n"
										+"error:"+errorData);
							}
						});
						
					});
				});
			</script>
		</li>
		
		
		<li>
			컨트롤러에서 리턴하는 JSON 배열 받아 출력하기
			<button id="test4">테스트</button>
			<div id="d4"></div>
		
		
		<script>
			$(function(){
				$("#test4").on("click",function(){
					$.ajax({
						
						url:"test4",
						success: function(data){
							var values = $('#d4').html();
							
							for(var i in data.list){
								values += "<div style='border:1px solid black;inline'>"+data.list[i].userId +","+"</div>"
									   + data.list[i].userPwd +","
									   + data.list[i].userName+","
									   + data.list[i].age +","
									   + data.list[i].email +","
									   + data.list[i].phone +"<br>";
							}
							
							$("#d4").html(values);
						}
					});
				});
			});
		</script>
	</li>
	
	
	<li>
	
		컨트롤러에서 리턴하는 Map객체를 받아서 출력하기
		<button id="test5" >테스트</button>
		<div id="d5"></div>
		
		<script>
			$(function(){
				$("#test5").on("click",function(){
					$.ajax({
						url:"test5",
						success: function(data){
							
							$("#d5").html("받을 맵 객체 안의 smap 객체 정보 확인 <br>"
										   +"이름 : "+data.samp.name
										   +",나이 : "+data.samp.age);
							
						},
						error:function(request,status,errorData){
							alert("error code:" +request.status+"\n"+"message : "+request.responseText+"\n"
									+"error:"+errorData);
						}
						
					});
				});
			});
		
		</script>
	
	
	</li>
	
		<li>
			뷰에서 JSON객체를 컨트롤러로 보내기
			<button id="test6">테스트</button>
			
			<script>
				$(function(){
						$('#test6').on("click",function(){
							
							var obj = new Object();
							obj.name = "박신우";
							obj.age = 26;
							
							$.ajax({
								
								url:"test6",
								data: JSON.stringify(obj),
								type:"post",
								contentType:"application/json; charset=utf-8",
								success: function(data){
									alert("서버로 전송 성공!"+data);
								},
								error:function(request,status,errorData){
									alert("error code:" +request.status+"\n"+"message : "+request.responseText+"\n"
											+"error:"+errorData);
								}
								
							});
						});
				});
			</script>
		
		</li>
		
	</ol>
</body>
</html>
