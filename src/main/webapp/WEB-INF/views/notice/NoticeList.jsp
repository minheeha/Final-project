<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
.main-panel {
	position: relative;
	width: calc(100% - 240px);
	height: 100vh;
	min-height: 100%;
	float: right;
	transition: all .3s;
	padding-top: 100px;
	padding-left: 3%;
	padding-right: 3%;
}

.pageName table {
	font-weight: bold;
	font-size: 1.5em;
}

.pageName tr td:first-child {
	width: 60%;
}

.pageName img {
	width: 30px;
	height: 30px;
}

.pageName button {
	background: #665b55;
	color: #fff;
	border: none;
	border-radius: 5px;
	padding-top: 5px;
	padding-bottom: 5px;
}

.summaryDiv {
	display: inline-block;
	background: #fff;
	width: 30%;
	height: 180px;
	margin-left: 1%;
	margin-right: 1%;
	margin-top: 3%;
}

.summaryDiv table {
	width: 100%;
}

.summaryDiv img {
	width: 40px;
	height: 40px;
	border-radius: 20px;
}

.summaryDiv tr:first-child {
	text-align: right;
}

.summaryDiv tr:nth-child(2) {
	font-weight: bold;
	font-size: 1.2em;
}

.summaryDiv tr:last-child {
	height: 70px;
	font-size: 0.8em;
	vertical-align: bottom;
}

.printBtn {
	background: #F95;
	color: #fff;
}

 .summaryDiv:hover {
	background: #fafafa;
	cursor: pointer;
} 

.titleView{
	font-size: 20px;
	padding-bottom:5%;
}
</style>

<body>
	<jsp:include page="../common/menubar.jsp" />
	<div class="main-panel" id="firstContentArea">
		<div class="pageName">
			<table width=100%;>
				<tr>
					<td><i class="fas fa-book" style="size: 25px"></i> <label
						style="font-weight: bold; font-size: 20px; color: black">공지사항</label>
					</td>
					<td>
						<button class="btn btn-warning" onclick="goNoticeWrite()"
							style="margin-left: 50%">작성하기</button>
					</td>
				</tr>
			</table>
		</div>
		<hr />

		<c:forEach var="w" items="${WhoAreYou}">
			<c:out value="${w.classNum}" />
		</c:forEach>

		<div class="homeworkDiarySummary">
		<c:forEach var="l" items="${list}">
			<div class="summaryDiv">
				<table class="tq">
						<tr class="first">
							<td class="tqtq"><c:out value="${l.boardNum}" /></td>
						</tr>
						<tr>
							<td><c:out value="${l.className}"/>교사</td>
							<td width="30px;">
							<img src="${ contextPath }/resources/images/woman.png" alt="" /></td>
						</tr>
						<tr>
							<td class="titleView"><c:out value="${l.boardTitle}"/></td>
						</tr>
						<tr>
							<td><c:out value="${l.boardContent}"/></td>
						</tr>
						<tr>
							<td><c:out value="${l.boardDate}" /></td>
						</tr>
						
				</table>
			</div>
		</c:forEach>
		</div>
		<br>
		<div class="searchArea">
			<div style="margin-left: 75%">
				<input type="text"
					style="width: 80%; border: 1px solid #dadada; border-radius: 10px"
					placeholder=" 제목 검색">
				<button
					style="background-color: #f5d442; border: 1px solid #fac852; border-radius: 4px">
					<i class="fas fa-search" style="size: 15px;"></i>
				</button>
			</div>
		</div>
		<div>
			<!-- 페이징 -->
		<div id="paginArea" align="center">
			<c:if test="${ pi.currentPage <= 1 }">
				[이전] &nbsp;
			</c:if>
			<c:if test="${ pi.currentPage > 1 }">
				<c:url var="blistBack" value="/selectList.bo">
					<c:param name="currentPage" value="${ pi.currentPage - 1 }"/>
				</c:url>
				<a href="${ blistBack }">[이전]</a>&nbsp;
			</c:if>

			<c:forEach var="p" begin="${ pi.startPage }" end="${ pi.endPage }">
				<c:if test="${ p eq pi.currentPage }">
					<font color="red" size="4"><b>[${ p }]</b></font>
				</c:if>
				<c:if test="${ p ne pi.currentPage }">
					<c:url var="blistCheck" value="selectList.bo">
						<c:param name="currentPage" value="${ p }"/>
					</c:url>
					<a href="${ blistCheck }">${ p }</a>
				</c:if>
			</c:forEach>

			<c:if test="${ pi.currentPage < pi.maxPage }">
				<c:url var="blistEnd" value="selectList.bo">
					<c:param name="currentPage" value="${ pi.currentPage + 1}"/>
				</c:url>
				<a href="${ blistEnd }">&nbsp; [다음]</a>
			</c:if>
			<c:if test="${ pi.currentPage >= pi.maxPage }">
				&nbsp; [다음]
			</c:if>
		</div>


	</div>
	<script>
		function goNoticeWrite(){
			location.href="NoticeWhoAreYou.no";
		};
		
		/* function showDetailNotice(){
			location.href = "DetailNotice.pl";
		} */
		
		//$(function(){
			/* $("#summaryDiv").find("td").mouseenter(function(){
				$(this).parents("tr").css({"background":"orangered", "cursor":"pointer"});
			}).mouseout(function(){
				$(this).parents("tr").css({"background":"white"});
			}).click(function(){
				var bid = $(this).children("tr").find("first").text(); */
				//console.log(this);
			//});
				//location.href="selectOne.bo?bid=" + bid;
		//};
		$(".homeworkDiarySummary").click(function(){
			//var ab = $(this).children("").eq(0).children(".tqtq").val();
		//var bid=$(this).children("tr").eq(0).children().text();	
		var test = $(this).children("table").children("tr").eq(0).children("td").val();
			console.log(test);
			
		});
	
	</script>
</body>
</html>






































