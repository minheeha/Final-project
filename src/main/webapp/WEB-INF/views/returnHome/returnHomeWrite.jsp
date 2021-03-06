<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="${contextPath }/resources/css/signature/jquery.signaturepad.css" rel="stylesheet">
<style>
	.main-panel {
	    position: relative;
	    width: calc(100% - 240px);
	    height: 100vh;
	    min-height: 100%;
	    float: right;
	    transition: all .3s;
	    padding-top:100px;
	    padding-left: 3%;
	    padding-right: 3%;
	}
	
	.returnHomeContents{
		background:#fff;
		margin:0 auto;
	}
	.returnHomeContents hr{
		width:95%;
	}
	
	.kinderInfo table{
		margin-left: 3%;
	}
	
	.kinderInfo img{
		background:#fff;
		width:30px;
		height:30px; 
		border-radius: 30px;
	}
	
	.kinderInfo tr:first-child {
	 font-weight: bold;
	
	}
	.returnHomeDetail{
		width:100%;
	}
	.returnHomeDetail table{
		width:95%;
		margin:0 auto;
	}
	.returnHomeDetail td, .returnHomeDetail th{
		background:#fff;
		
	}

	.returnHomeDetail td{
		padding-left: 50px;
		padding-right: 50px;
	}

	.returnHomeDetail th{
		text-align:left;
		padding-left: 30px;
		padding-right: 30px;
		font-size: 1.4em;
	}
	.selectDateDiv{
		width:40%;
		height:90%;
		color:#fff;
		border-radius:5px;	
		vertical-align: middle;
		text-align:center;
		display: inline-block;
		padding-top:13px;
		padding-bottom: 13px;
	}
	.selectDateDiv label{
		color:#fff;
	}
	.selectDateDiv:first-child{
		background:#59f;
	}
	
	.selectDateDiv:nth-child(2){
		background:#554a44;
	}
	
	.selectDate{
		vertical-align: middle;
	}
	
	.selectTime select{
		width:70px;
		height:40px;
		font-size: 1.1em;
	}
	
	.signArea{
		width:100px;
		height:100px;
		background:#aaa;
		
	}
	
	.detailBtnArea>button:first-child{
		width:100%;
		height:40px;
		color:#fff;
		background:#58f;
		border:none;
		border-radius: 5px;
	}
	
	.detailBtnArea table{
		width:100%;
	}
	
	.detailBtnArea table button{
		width:80px;
		height:40px;
		color:#fff;
		background:#554a44;
		border:none;
		border-radius: 5px;
	}
	
	.returnWay label{
		color:#000;
	}
</style>
</head>
<body>
	<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>
	
	<c:set var="today" value="<%=new java.util.Date()%>" />
	<c:set var="todayDate1"><fmt:formatDate value="${today}" pattern="MM???dd???" /></c:set>
	<c:set var="todayDate2"><fmt:formatDate value="${today}" pattern="yyyy??? MM??? dd???" /></c:set>
	<c:set var="tomorrowDate" value="<%=new Date(new Date().getTime() + 60*60*24*1000)%>"/>
	<c:set var="tomorrow"><fmt:formatDate value="${tomorrowDate}" pattern="MM???dd???"/></c:set>
	
	<jsp:include page="../common/menubar.jsp"/>
	<div class="main-panel" id="firstContentArea">
		<div class="pageName">
			<table width=100%;>
				<tr>
					<td>
						<label style="font-weight:bold;">???????????????</label>
					</td>
				</tr>
			</table>
		</div>
		<hr />
		<form action="insertReturnHome.rh" method="post">
			<div class="returnHomeContents">
				<div class="kinderInfo">
					<table>
						<tr>
							<td><img src="${ contextPath }/resources/images/baby.png" alt="" />?????????</td>
						</tr>
						<tr>
							<td>
								<c:forEach var="childrenList" items="${list}" varStatus="status">
									<input type="checkbox" name="selectChild" value="${childrenList.childrenNo }" id="selectChild" checked="true"/><label for="selectChild" style="color:#000; font-size: 1.4em;"><c:out value="${childrenList.childrenName}" escapeXml="false"/></label>
								</c:forEach>
							</td>
						</tr>
					</table>
				</div>
				<hr />
				<div class="returnHomeDetail">
					<table>
						<tr>
							<th>???????????????</th>
						</tr>
						<tr>
							<td>
								<div class="selectDateDiv" id="selectDiv1">
									<label for="today" id="dateLabel1">??????(<c:out value="${todayDate1}" />)</label>
								</div>
								<div class="selectDateDiv" id="selectDiv2">
									<label for="tomorrow" id="dateLabel2">??????(<c:out value="${tomorrow}" />)</label>
								</div>
								<input type="radio" name="homeDate" id="today"  class="selectDate" value="<c:out value='${todayDate1}' />" checked="true" hidden/>
								<input type="radio" name="homeDate" id="tomorrow" class="selectDate" value="<c:out value='${tomorrow}' />" hidden/>
							</td>
						</tr>
						<tr>
							<th>??????????????????</th>
						</tr>
						<tr>
							<td class="selectTime">
								<select name="homeTime" id="noonCheck">
									<option value="??????">??????</option>
									<option value="??????">??????</option>
								</select>&nbsp;
								<select name="homeTime" id="hour">
									<option value="1???">1???</option>
									<option value="2???">2???</option>
									<option value="3???">3???</option>
									<option value="4???">4???</option>
									<option value="5???">5???</option>
									<option value="6???">6???</option>
									<option value="7???">7???</option>
									<option value="8???">8???</option>
									<option value="9???">9???</option>
									<option value="10???">10???</option>
									<option value="11???">11???</option>
									<option value="12???">12???</option>
								</select>&nbsp;
								<select name="homeTime" id="minute">
									<option value="00???">00???</option>
									<option value="10???">10???</option>
									<option value="20???">20???</option>
									<option value="30???">30???</option>
									<option value="40???">40???</option>
									<option value="50???">50???</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>????????????</th>
						</tr>
						<tr>
							<td>
								<input type="radio" name="homeWay" class="returnWay" value="??????"/>
								<label for="walk" style="color:#000;">??????</label>
								<input type="radio" name="homeWay" class="returnWay" value="??????"/>
								<label for="mycar" style="color:#000;">??????</label>
								<input type="radio" name="homeWay" class="returnWay" value="????????????"/>
								<label for="bus" style="color:#000;">????????????</label>
							</td>
						</tr>
						<tr>
							<th>?????????</th>
						</tr>
						<tr>
							<td>
								<label for="" style="color:#000;">?????? : </label>&nbsp;<input type="text" name="parentsName" id="family1" placeholder="????????? ??????" style="width:80px;"/> &nbsp;
								&nbsp;&nbsp;
								<label for="" style="color:#000;">????????? : </label>&nbsp;<input type="text" name="parentsPhone" id="phone1" placeholder='"-"??? ???????????? ??????'/>
							</td>
						</tr>
						<tr>
							<th>???????????????</th>
						</tr>
						<tr>
							<td>
								<label for="" style="color:#000;">?????? : </label>&nbsp;<input type="text" name="emergencyName" id="famaily2" placeholder="????????? ??????" style="width:80px;"/> &nbsp;
								&nbsp;&nbsp;
								<label for="" style="color:#000;">????????? : </label>&nbsp;<input type="text" name="emergencyPhone" id="phone2" placeholder='"-"??? ???????????? ??????'/>
							</td>
							
						</tr>
					</table>
					<hr />
				</div>
				<table style="width:95%; margin:0 auto;">
					<tr>
						<td style="width:84%; vertical-align: top;">
							<p style="margin-left:2.5%; font-size:0.8em;">???????????? ?????? ??? ??? ??????????????? ???????????? ?????????. <br />
							??? ????????? ????????? ?????? ???????????? ????????? ?????? ????????? ????????? ????????? ??????????????????. <br />
							???????????? ????????? ?????????????????? ???????????? ?????? ??????????????? ????????????.</p>
						</td>
						<td>
							<div class="sigPad">
								<ul class="sigNav">
							      <li class="clearButton"><a href="#clear">Clear</a></li>
							    </ul>
							    <div class="sig sigWrapper">
							      <div class="typed"></div>
							      <canvas class="pad" id="canvas" width="198" height="98px"></canvas>
									<img id="myImage">
							      <input type="hidden" name="dataURL" id="urlInput"/>
							      <!-- <input type="hidden" name="output" class="output" id="outout"> -->
							    </div>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="text-align: right;">
							<input type="text" name="writeDate" value="<c:out value='${todayDate2}' />"
										style="border:none; text-align: right;" readonly/>
										<c:out value="${loginUser.userName }"/>
						</td>
					</tr>
				</table>
			</div>
			<br />
			
			<c:if test="${ loginUser.classification eq '?????????' }">
				<div class="detailBtnArea">
					<button class="checkBtn">????????????</button>
					<table>
						<tr>
							<td style="text-align:left;"><button>??????</button></td>
							<td style="text-align:right;"><button>??????</button></td>
						</tr>
					</table>
				</div>
			</c:if>
			<c:if test="${ loginUser.classification eq '?????????' }">
				<div class="detailBtnArea">
					<table>
						<tr>
							<td style="text-align:left;"><button onclick="">??????</button></td>
							<td style="text-align:right;"><button type="submit">????????????</button></td>
						</tr>
					</table>
				</div>
			</c:if>
		</form>
		<br />
	<%-- <jsp:include page="require-drawn-signature.jsp"/> --%>
	</div>
	
  <!-- <script src="../jquery.signaturepad.js"></script> -->
  <script src="${contextPath }/resources/js/signature/jquery.signaturepad.js"></script>
  <script src="${contextPath }/resources/js/signature/jquery.signaturepad.js"></script>
  <script src="${contextPath }/resources/js/signature/jquery.signaturepad.js"></script>
  <script>
    $(document).ready(function() {
      $('.sigPad').signaturePad({drawOnly:true});
    });
    
    $(function(){
		var selectDiv1 = document.getElementById('selectDiv1');
		var selectDiv2 = document.getElementById('selectDiv2');
		$(".selectDateDiv").click(function(){
			var label = $(this.children[0].id);
			
			if(this.children[0].id == "dateLabel1"){
				$(this).css("background", "#59f");
				$("#selectDiv2").css("background", "#554a44");
				selectDiv1.children[0].click();
			}else{
				$(this).css("background", "#59f");
				$("#selectDiv1").css("background", "#554a44");
				selectDiv2.children[0].click();
			}
		});
		
		
		var canvas = document.getElementById('canvas');
		var padpad = $("#canvas");
    	
    	padpad.mouseout(function(){
    		var dataURL = canvas.toDataURL();
			var urlInput = document.getElementById('urlInput');
			console.log(urlInput);
    		console.log(dataURL);
    		urlInput.value = dataURL;
			
    	});
    });
    
  </script>
	
</body>
</html>






























