<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- modelAttribute에 member는 컨트롤러에서 선언한 key값이어야함. 내가 넘기고 있는 프로퍼티도 같아야 함 -->
	<!-- 
		Map으로 넘긴 데이터를 items 속성에 넣어, Map의 key는 value가 되고 Map의 value는 label의 몸체 값으로 셋팅된다.
	 -->
	
	<form:form modelAttribute="member" method="post" action="/formtag/register">
		<table	border="1">
			<tr>
				<td>취미(hobbyMap)</td>
				<td>
					<form:checkboxes items="${hobbyMap }" path="hobbyMap"/>
				</td>
			</tr>

		</table>
		<form:button name="register">등록</form:button>	
	</form:form>
</body>
</html>