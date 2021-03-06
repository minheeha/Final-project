<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css?family=Poor+Story&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css?family=Sunflower:300&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body{
font-family: 'Poor Story', cursive;
}
#content {
	text-align: center;
}

.mainimage {
	height: 70%;
	width: 50%;
	padding-top:8%;
}

body {
	margin-left: 2%;
	margin-right: 2%;
}

.introduce {
	background-color:#4d94ff;
	width: 100%;
	height: 600px;
}

.introduce2 {
	background-color:#4d94ff;
	width: 100%;
	height: 150px;
}

.hand {
	width: 80%;
	height: 30%;
}

.mainmain {
	height: 800px;
	width:100%;
	padding-top:50px;
}

.donggurami {
	background: #ff6666;
	width: 150px;
	height: 30px;
	margin:0 auto;
	border-radius: 100px;
	vertical-align: bottom;
	padding-top: 120px;
	padding-left:30%;
}
.donggurami2 > h2{
width:150px;
text-align:center;
}
.donggurami > contents{
width:150px;
float:center;
}
.donggurami h5{
	margin: 0px 0px 0px 0px;	
}

.donggurami2 {
	background: #6666ff;
	width: 150px;
	height: 30px;
	margin:0 auto;
	border-radius: 100px;
	vertical-align: bottom;
	padding-top: 120px;
	padding-right:30%;
	
}
.donggurami2 h5{
	margin: 0px 0px 0px 0px;		
}
.some td{
	 text-align: center;
}
table td { text-align:center;}

h1,h2{
	color:white;
}
.some{
	height:80%;
	width:80%;
}

body{

font-family: 'Sunflower', sans-serif;

}


</style>
</head>
<body>
	<c:set var="contextPath"
		value="${ pageContext.servletContext.contextPath }"
		scope="application" />

	<jsp:include page="main-include.jsp" />
	<form>
		<%-- <div id="content" style="background-image: URL(${ contextPath }/resources/images/introduce.PNG); background-size:100% 100%;">
	
	
	</div> --%>
		<%-- <div>
		<img class="mainimage"src="${ contextPath }/resources/images/background2.jpg" />
		</div> --%>
	<div class="mainmain" align="center" style="background-image: URL(${ contextPath }/resources/images/anisky.jpg); background-size:100% 100%;">
	<img class="mainimage"src="${ contextPath }/resources/images/mainicon.png" />
		</div>
		<div class="introduce">
		<br><br><br><br>

			<h1 align="center">???????????????</h1>
			<h2 align="center">???????????? ???????????? ??????????????? ?????????</h2>
			<h2 align="center">??????????????? ???????????? ?????????????????? ????????????</h2>
			<h2 align="center">????????? ?????? ???????????? ??????????????? ?????????????????????.</h2>
			<div align="center">
			<table class="some">
					<tr>
						<td>
							<div class="donggurami" style="background-image: url('${ contextPath }/resources/images/people.png'); background-size:100%;">
							<br>
							</div>
						</td>
						<td style="width:40%;">
						<img class="hand" src="${ contextPath }/resources/images/hand2.png" />
						</td>
						<td>
						<div class="donggurami2" style="background-image: url('${ contextPath }/resources/images/kid.png'); background-size:100%;">
							<br>
						</div>
						</td>
					</tr>
					<tr>
					<td style="padding-top:0">
						<h2 align="center">??????</h2>
					</td>
					<td></td>
					<td>
						<h2 align="center">&nbsp;&nbsp;???</h2>
					</td>
					
					</tr>
			</table>
				<div class="introduce2">
				
				
				</div>
			
			</div>
		</div>
	</form>

</body>
	<br><br><br><br>
	<jsp:include page="../common/footer.jsp" />
</html>