<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="MQServlet" method="post">
		<div class="DIV_ONE">
			<textarea name = "MSGBODY" rows="15" cols="75" placeholder = "input MQ message..."></textarea>
		</div>
		<div>
			<select name="MQSelections">
				<option value="HK-T24Dev">HK-T24Dev</option>
				<option value="HK-T24SIT">HK-T24SIT</option>
				<option value="HK-T24UAT">HK-T24UAT</option>
			</select>
		</div>

		<div class="DIV_TWO">
		<input type="submit" value="submit"/>
		<input type="reset" value="reset"/>
		</div>
	</form>

</body>
</html>