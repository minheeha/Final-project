<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>투약보고서</title>
<style>
.main-panel {
	position: relative;
	width: calc(100% - 240px);
	height: 100vh;
	min-height: 100%;
	float: right;
	transition: all .3s;
}

#titleArea {
	background: #f5f5f5;
	height: 55px;
}

#title {
	padding: 15px;
}

#profileArea {
	margin-left: 20px;
}

#contentsArea {
	height: 700px;
	background: white;
}

#profileImg {
	width: 50px;
	height: 50px;
	border-radius: 50%;
}

#symptomArea, #dosageArea {
	line-height: 230%;
	font-size: 14px;
	margin-left: 20px;
}

#tableArea {
	border: 0.5px solid #d6d6d6;
	width: 95%;
	height: 300px;
}

tr {
	border: 0.5px solid #d6d6d6;
}

th, td {
	padding-left: 20px;
}
#msgArea {
	float:right;
	margin-right:10%;
	display:inline;
}
#signArea {
	width:70px;
	height:70px;
	border:solid 1px black;
	float:right;
	display:inline;
	margin-right: 20px;
}
#a1 {
	height:60px;
	background:#e6edfa;
	color:#2196f3;
    font-size: 16px;
    line-height: 20px;
    padding:19px;
    font-weight:bold;
}
#reportArea {
	height:350px;
	background:#edf5ff;
}
#reportArea p, #reportArea textarea {
	margin-left:20px;
}
#textA1 {
	width:96%;
	resize: none;
}
.btns {
	width:200px;
	height:50px;
	color:white;
}
#btn1 {
	background:#8f8f8f;
	font-weight:bold;
	border: 0.5px solid #7d7d7d;
}
#btn2 {
	background:#ff7575;
	font-weight:bold;
}
</style>

</head>
<body>
	<jsp:include page="../common/menubar.jsp" />

	<div class="main-panel">
		<div class="content">
			<div class="page-inner">
				<div class="page-header">
					<i class="fas fa-notes-medical" style="font-size: 20px;"></i>&nbsp;&nbsp;
					<h4 class="page-title">투약보고서 작성</h4>
				</div>
				<hr>
				<div class="page-category">
					<div id="mainArea">
						<div id="titleArea">
							<h2 id="title">
								<b>하뽀송의 투약의뢰서</b>
							</h2>
						</div>
						<div id="contentsArea">
							<br>
							<div id="profileArea">
								<i><img src="${ contextPath }/resources/images/woman.png"
									id="profileImg"></i> &nbsp;&nbsp;<b>하뽀송</b>
							</div>
							<br>
							<p style="font-size: 1.15em; margin-left: 20px;">금일 자녀의 투약을
								선생님께 의뢰합니다.</p>
							<hr align="center"
								style="width: 96%; border: none; border: 0.8px dashed lightgray;">
							<div id="symptomArea">
								<i class="fas fa-briefcase-medical"></i>&nbsp;&nbsp;<span><b>증상</b></span>&nbsp;&nbsp;&nbsp;<span>감기</span><br>
							</div>
							<hr align="center"
								style="width: 96%; border: none; border: 0.8px dashed lightgray;">
							<div id="dosageArea">
								<i class="fas fa-pills"></i>&nbsp;&nbsp;<span><b>투약내용</b></span><br>
								<table id="tableArea">
									<tr style="background: #f0f0f0;">
										<th width="30%;">약의 종류</th>
										<td>감기약</td>
									</tr>
									<tr>
										<th>투약 용량</th>
										<td>10 cc/ml</td>
									</tr>
									<tr>
										<th>투약 횟수</th>
										<td>1 회</td>
									</tr>
									<tr>
										<th>투약 시간</th>
										<td>점심 후</td>
									</tr>
									<tr>
										<th>보관 방법</th>
										<td>실온</td>
									</tr>
									<tr>
										<th>특이사항</th>
										<td></td>
									</tr>
								</table>
							</div>
							<br><br>
							<div id="msgArea">
								<p>투약으로 인한 책임은 의뢰자가 집니다.</p>
								<span>2019.08.30</span>&nbsp;&nbsp;&nbsp;<span><b>하민희</b></span>
							</div>
							<div id="signArea">
								싸인
							</div>
						</div>
						<br>
						<div id="a1">
							<i class="fas fa-file-medical"></i>&nbsp;<span>투약보고서 작성</span>
						</div>
						<div id="reportArea">
							<br>
							<p>금일 본 원의 하뽀송 원아에 대해 의뢰하신 내용대로 투약하였음을 보고합니다.</p>
							<p>2019.08.31. 하민희</p>
							<br>
							<p style="color:#8f8f8f">투약보고서는 실제 투약 후 작성될 수 있으니 참고해 주세요.</p>
							<hr width="96%">
							<p><b>특이사항</b></p>
							<textarea class="form-control" rows="5" id="textA1" placeholder="특이사항이 있는 경우 작성해 주세요."></textarea>
						</div>
						<br>
						<div id="btnArea" align="center">
							<button type="button" class="btn btns" id="btn1" onclick="showDrugDetail()">취소</button>&nbsp;&nbsp;
							<button type="button" class="btn btns" id="btn2">보내기</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	function showDrugDetail(){
			location.href="drugDetailList.pl";
		}
	</script>

</body>
</html>





































