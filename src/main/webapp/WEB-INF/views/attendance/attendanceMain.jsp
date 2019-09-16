<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script>
num1 = 0;
$(function(){
	$("#datepicker").datepicker({
		changeMonth: true, 
        changeYear: true,
        nextText: '다음 달',
        prevText: '이전 달',
        colseText:'닫기',
        minDate:"-1M",
        maxDAte:"+0D",
        dateFormat:"yy-mm-dd",
        dayNames: ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'],
        dayNamesMin: ['월', '화', '수', '목', '금', '토', '일'], 
        monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'],
        monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        onSelect:function(){
        	day = $("#datepicker").val();
        	var date = $("#today").html(day);
        	location.href = "changeAttendance.at";
        	var date = $("#today").html();
        }
	});
});

function goMonth(){
	location.href="month.at";
};

$(document).on('change', 'select', function() {
	if(num1==0){
		var saveb = '<button>저장</button>';
		$("#saveBtn").append(saveb);
		num1+=1;
	};
	if($(this).parent().parent().children("td").eq(5).html() == ""){
	$(this).parent().parent().children("td").eq(5).append("<button onclick='bgo();' name=''>비고</button>");
	}
	var tv = $(this).val();
	if(tv == "V" || tv =="★" || tv=="/"){
		console.log(tv);
		$(this).parent().parent().children("td").eq(3).append("<button class='timepicker'>딸깍!</button>");
		
			$('.timepicker').timepicker({
			    timeFormat: 'h:mm p',
			    interval: 15,
			    minTime: '10',
			    maxTime: '6:00pm',
			    defaultTime: '9',
			    startTime: '10:00',
			    dynamic: false,
			    dropdown: true,
			    scrollbar: true
		});
			
	}
	
});

/* $("#statusArea").click(function(){
	$(this).children("select").css('visibility', 'visible');
	console.log("왜안나와");
}); */
function bgo(){
	console.log(this);
}


</script>

</head>
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
	.pageName label{
		font-weight: bold;
		font-size: 1.5em;
	}
 	.status{
 	width:100%;
 	height:100%;
 	border : none;
 	background:url('arrow.jsp') no-repeat 95% 50%;
 	appearnace:none;
 	color:white;
	}
	.status > option{
 	text-align:center;
 	margin:0 auto;
 	color:black;
	}
	.pageName tr td:first-child{
		width:60%;
	}
	.pageName img{
		width:30px;
		height:30px;
	}
	.pageName button{
		background:#665b55;
		color:#fff;
		border:none;
		border-radius: 5px; 
		padding-top:5px;
		padding-bottom: 5px;
		float:right;
		width:100%;
	}
	#datepicker{
	background:#665b55;
		color:#fff;
		border:none;
		border-radius: 5px; 
		padding-top:5px;
		padding-bottom: 5px;
		float:right;
		width:100%;
	}
	.attendanceArea{
		margin:0 auto;
		align:center;
		background:#555;
		color:#fff;
		text-align:center;
	}
	#timecheck{
	float:left;
	margin-top:2%;
	width:100%;
	}
	#timecheck > label{
	color:white;
	}
	#attendance{
	width:100%;
	color:white;
	}
	#right{
	text-align:right;
	margin-left:20%;
	}
	#btntable > td{
	float:right;
	}
	#btntable{
	width:100%;
	}
	.subBtn{
	float:left;
	margin-left:5%;
	margin-top:2%;
	}
</style>
<body>
	<jsp:include page="../common/menubar.jsp"/>
	<div class="main-panel" id="firstContentArea">
		<div class="pageName">
		
			<table id="btntable">
				<tr>
					<td colspan="14">
						<img src="${contextPath }/resources/images/schedule.png" alt="" /><label style="font-weight:bold; font-size: 20px!important;">출석부</label>
						
					</td>
					<td><button onclick="goMonth();">월별출석부</button></td>
					<td><input type="button" id="datepicker" value="일별출석부"></td>
					<td><button id="setbtn">설정</button></td>
					</tr>
				
			</table>
		</div>
		<hr />
		<div class="attendanceArea">
		<div id="timecheck"><label>총원 : ${ day }명(출석 : ${day }명)</label>&nbsp;&nbsp;
		<span id="right">V 출석 × 결석 ◎ 병결 ○ 사고 / 입소 ★ 퇴소</span></div>
						<table id="attendance" align="center">
							<tr>
							<td colspan="3">
							<button onclick="preBtn();">&lt;</button></td>
							<td colspan="2"><h3 id="today" name="time">${ day }</h3></td>
							<td colspan="2"><button onclick="afterBtn();">&gt;</button></td>
							</tr>
							<tr>
							<td>No.</td>
							<td>원아명</td>
							<td>출결상태</td>
							<td>등원 시간</td>
							<td>하원 시간</td>
							<td>비고</td>
							<td>보호자 확인</td>
							</tr>
							<tbody>
							<c:forEach var="a" items="${ list }">
							<tr style="border:1px solid white;">
								<td></td>
								<td><c:out value="${ a.childrenName }"/></td>
								<td id="statusArea">
								<select class="status" name="status">
									<option value="">선택</option>
									<option value="V">출결</option>
									<option value="×">결석</option>
									<option value="◎">병결</option>
									<option value="○">사고</option>
									<option value="/">입소</option>
									<option value="★">퇴소</option>
								</select>
								</td>
								<td class="timepicker">
								<!-- 등원시간 -->
						<!-- 		<div class="form-group attendance-time-list">
                        <span class="base-time-margin text-left">등원 시간</span>
                        <form id="baseAttendanceInTimeForm" class="form-timepicker-wrapper base-time-form">
                            <select class="form-control form-timepicker" id="selectInTimeAMPM" name="ampm" style="width:30%;">
                                <option value="-1" disabled selected></option>
                                <option id="selectInTimeAM">오전</option>
                                <option id="selectInTimePM">오후</option>
                            </select>
                            <select class="form-control form-timepicker" id="selectInTimeHour" name="hour" style="width:30%;">
                                <option id="selectInTimeHourDefault" value="-1" disabled selected></option>
                                <option id="selectInTimeHour">08</option>
                                <option id="selectInTimeHour">09</option>
                                <option id="selectInTimeHour">10</option>
                                <option id="selectInTimeHour">11</option>
                                <option id="selectInTimeHour">12</option>
                            </select>
                            <select class="form-control form-timepicker" id="selectInTimeMin" name="minute" style="width:30%;">
                                <option id="selectInTimeMinDefault" value="-1" disabled selected></option>
                                <option id="selectInTimeMin">00</option>
                                <option id="selectInTimeMin">15</option>
                                <option id="selectInTimeMin">30</option>
                                <option id="selectInTimeMin">45</option>
                            </select>
                        </form>
                    </div> -->
								</td>
								<td>
								<!-- 하원시간 -->
								</td>
								<td></td>
								<td></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					<!-- <button class="subBtn">다운로드</button> -->
					<a href="/attendance/2019/9/11/download/?qs_class=" type="button" class="btn btn-default hidden-print"
                   onclick="ga('send', 'event', 'attendance', 'Click', 'dailyAttendance|download');">
                    <i class="kn kn-download-box"></i> 다운로드
                </a>
					<!-- <button class="subBtn">출력</button> -->
					<a href="/attendance/2019/9/11/print/?qs_class=" target="_blank" type="button" class="btn btn-gray btn-fix-width-sm hidden-print"
               id="dailyAttendancePrintLink"
               onclick="ga('send', 'event', 'attendance', 'Click', 'dailyAttendance|print');">
                <i class="kn2 kn-print"></i> 출력
            </a>
					<div id="saveBtn"></div>
		</div>
	</div>
</body>
</html>
























