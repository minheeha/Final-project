<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	body{
		background:#eee;
	}
	.topArea {
		width:80%;
		margin:0 auto;
	}
	
	.topArea table{
		text-align:center;
		width:100%;
		height: 100px;
	}
	.topArea table tr:first-child td:nth-child(2n){
		width:30%;
		padding-left: 10px;
		padding-right: 10px;
		 
	}
	
	.topArea table tr:first-child td:nth-child(2n-1){
		width:100px;
		height:100px;
		text-align:center;
		vertical-align:middle;
		background:#ffcc00;
		color:#fff;
		font-size: 2em;
		font-weight: bold;
		border:1px solid black;
		border-radius: 100px;
		
	}
	.topArea table tr:last-child td{
		width:100px;
		font-size: 1em;
		font-weight: bold;
		
	}
	.kidsSelectArea{
		background:#fff;
		padding-bottom: 20px;
	}
	
	.kidsSelectArea label:first-child{
		font-weight: bold;
		font-size: 1.5em;
	}
	
	.kidsSelectArea input{
		width:20px;
		height:20px;
	}
	.kidsSelectArea label:nth-child(2){
		font-size: 1.2em;
		
	}
	.homeworkContents{
		background:#fff;
		padding:20px;
		text-align: center;
	}
	
	.homeworkContents textarea{
		width:100%;
		margin:0 auto;
		border: 1px solid #ccc;
		resize: none;
		font-size: 1.2em;
	}
	.homeworkContents table{
		margin:0 auto;
	}
	.classroom{
		width:35%;
		height:50px;
		vertical-align:middle;
		border-radius:5px;
		display: inline-block;
		margin-left: 5%;
		margin-right: 5%;
		background:#f55;
	}
	
	.classroom table{
		width:100%;
		text-align: center;
		vertical-align:middle;
		color:#fff;
		
	}
	.btnArea{
		width:100%;
		text-align: right;
		
	}
	.btnArea button{
		background:#fa0;
		width:90px;
		height:40px;
		margin-top: 10px;
		border:none;
		border-radius: 5px;
		color:#fff;
	}
	
	
</style>
</head>
<body>
	<div class="topArea">
		<table>
			<tr>
				<td style="background:#ffcc00">1</td>
				<td><hr /></td>
				<td>2</td>
				<td><hr /></td>
				<td>3</td>
			</tr>
			<tr>
				<td>????????????</td>
				<td></td>
				<td>????????????</td>
				<td></td>
				<td>??????</td>
			</tr>
		</table>
	</div>
	<hr />
	<form action="">
	<div class="kidsSelectArea">
		<label for=""><img src="" alt="" />????????????</label>
		<br />
		<br />
		<input type="checkbox" name="selectAll" id="selectAll"/>
		<label for="selectAll">????????????</label>
		<br />
		<br />
		<div class="classroom">
			<table>
				<tr>
					<td>?????????</td>
					<td>0/15</td>
				</tr>
			</table>
		</div>
		<div class="classroom">
			<table>
				<tr>
					<td>?????????</td>
					<td>0/17</td>
				</tr>
			</table>
		</div>
		<br />
	</div>
	<br />
	<div class="homeworkContents">
		<table style="width:95%;">
		<tr><td>
			<label for="">????????????</label><br />
		</td></tr>
		<tr><td>
			<textarea rows="8" cols="" placeholder="????????? ??????????????????"></textarea>
		</td></tr>
		</table>
	</div>
	<div class="btnArea">
		<button>??????</button>
	</div>
	</form>
	
			
</body>
</html>