<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>RegisterForm</title>
</head>
<body>
	<h3>REGISTER FORM</h3>
	<hr/><br/>
	
	<h4>2. 요청 처리</h4>
	<hr/>
	
	<p>1) URL 경로 상의 쿼리 파라미터 정보로부터 요청 데이터를 취득할 수 있다.</p>
	<a href="/register?userId=hongkd&password=1234">Button1</a>
	<br/>
	
	<p>2) URL 경로 상의 경로 변수로부터 요청 데이터를 취득할 수 있다.</p>
	<a href="register/hongkd">Button2</a>
	
	<p>
		3) HTML Form 필드명과 컨트롤러 매개변수명이 일치하면 요청 데이터를 취득할 수 있다.<br/>	
	</p>
	<form action="/register01" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	<p>4) HTML Form 필드 값이 숫자일 경우에는 숫자로 타입 변환되어 데이터를 취득할 수 있다. </p>
	<form action="/register02" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	<h4>3. 요청 데이터 처리 어노테이션</h4>
	<hr/>
	
	<p>1) URL 경로 상의 경로 변수가 여러 개일 때, @PathVariable 어노테이션을 사용하여 특정한 경로 변수명을 지정해준다.</p>
	<a href="/register/hongkd/100">Button3</a><br/>
	
	
	<p>2) @RequestParam 어노테이션을 사용하여 특정한 HTML Form의 필드명을 지정하여 요청을 처리 한다. </p>
	<form action="/register0202" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	<hr/><br/>
	
	<h4>4. 요청 처리 자바빈즈</h4>
	<hr/>
	
	<p>1) 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리한다.</p>
	<form action="/beans/register01" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	
	<p>2) 폼 텍스트 필드 요소값을 자바빈즈 매개변수와 기본 데이터 타입인 정수 타입 매개변수로 처리한다.</p>
	<form action="/beans/register02" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		coin : <input type="text" name="coin"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	<hr/><br/>
	
	<h4>5. Date 타입 처리</h4>
	<hr/>
	
	<p>1) 쿼리 파라미터(dateOfBirth=1234)로 전달받은 값이 날짜 문자열 형식에 맞지 않아 Date 타입으로 변환에 실패한다.</p>
	<a href="/registerByGet01?userId=hong&dateOfBirth=1234">Button1</a>
	
	<p>2) 쿼리 파라미터(dateOfBirth=2018-09-08)로 전달받은 값이 날짜 문자열 형식으로 설정 시, Date 타입으로 받는가?</p>
	<a href="/registerByGet01?userId=hong&dateOfBirth=2018-09-08">Button2</a>
	
	<p>3) 쿼리 파라미터(dateOfBirth=20180908)로 전달받은 값이 날짜 문자열 형식으로 설정 시, Date 타입으로 받는가?</p>
	<a href="/registerByGet01?userId=hong&dateOfBirth=20180908">Button3</a>
	
	<p>4) 쿼리 파라미터(dateOfBirth=2018/09/08)로 전달받은 값이 날짜 문자열 형식으로 설정 시, Date 타입으로 받는가?</p>
	<a href="/registerByGet01?userId=hong&dateOfBirth=2018/09/08">Button4</a>

	<p>5) Member 매개변수와 쿼리 파라미터(dateOfBirth=20180908)로 전달받은 값이 날짜 문자열 형식으로 설정 시, Date 타입으로 받는가?</p>
	<a href="/registerByGet02?userId=hong&dateOfBirth=20180908">Button5</a>

	<p>6) Member 매개변수와 폼 방식 요청 전달받은 값이 날짜 문자열 형식으로 설정 시, Date 타입으로 받는가?</p>
	<form action="/register" method="post">
		userId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		dateOfBirth : <input type="text" name="dateOfBirth"/><br/>
		<input type="submit" value="전송"/>
	</form>


	<hr/><br/>
	
	<h4>6. @DateTimeFormat 어노테이션</h4>
	<hr/>


	<p>1) Member 매개변수와 폼 방식 요청 전달받은 값이 날자 문자열 형식으로 설정시, Date 타입으로 받는가?</p>
	<form action="/registerByGet03" method="post">
		userId : <input type="text" name="userId"/><br/>	
		dateOfBirth : <input type="text" name="dateOfBirth"/><br/>
		<input type="submit" value="전송"/>
	</form>
	
	<hr/><br/>
	
	<h4>7. 폼 방식 요청 처리</h4>
	<hr/>	
	
	<p>1) 폼 텍스트 필드 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.</p>
	<form action="/registerUserId" method="post">
		userId : <input type="text" name="userID"/><br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	<p>2) 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리한다.</p>
	<form action="/registerMemberUserId" method="post">
		userId : <input type="text" name="userID"/><br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>3) 폼 비밀번호 필드 요소값을 자바빈즈 매개변수로 처리한다.</p>
	<form action="/registerPassword" method="post">
		password : <input type="password" name="password"/><br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	<p>4) 폼 라디오버튼 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.</p>
	<form action="/registerRadio" method="post">
		gender : <br/> 
		<input type="radio" name="gender" value="male" checked="checked"/>Male<br/>
		<input type="radio" name="gender" value="female" />Female<br/>
		<input type="radio" name="gender" value="other" />Other<br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>5) 폼 셀렉트 박스 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.</p>
	<form action="/registerSelect" method="post">
		nationality : <br/>
		<select name="nationality">
			<option value="korea">대한민국</option>
			<option value="germany">독일</option>
			<option value="austrailia">호주</option>
			<option value="canada">캐나다</option>
			<option value="usa">미국</option>
		</select>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>6) 복수선택이 가능한 폼 셀렉트 박스 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.</p>
	<form action="/registerMultiSelect01" method="post">
		cars : <br/>
		<select name="cars" multiple="multiple">
			<option value="jeep">JEEP</option>
			<option value="volvo">VOLVO</option>
			<option value="bmw">BMW</option>
			<option value="audi">AUDI</option>		
		</select>
		<input type="submit" value="전송"/><br/>
	</form>
	
	<p>7) 복수선택이 가능한 폼 셀렉트 박스 요소값을 문자열 배열 타입 매개변수로 처리한다.</p>
	<form action="/registerMultiSelect02" method="post">
		carArray : <br/>
		<select name="carArray" multiple="multiple">
			<option value="jeep">JEEP</option>
			<option value="volvo">VOLVO</option>
			<option value="bmw">BMW</option>
			<option value="audi">AUDI</option>		
		</select>
		<input type="submit" value="전송"/><br/>
	</form>	
	
	<p>8) 복수선택이 가능한 폼 셀렉트 박스 요소값을 문자열 요소를 가진 리스트 컬렉션 타입 매개변수로 처리한다.</p>
	<form action="/registerMultiSelect03" method="post">
		carList : <br/>
		<select name="carList" multiple="multiple">
			<option value="jeep">JEEP</option>
			<option value="volvo">VOLVO</option>
			<option value="bmw">BMW</option>
			<option value="audi">AUDI</option>		
		</select>
		<input type="submit" value="전송"/><br/>
	</form>	
	
	<p>9) 폼 체크박스 요소값을 기본 데이터 타입인 문자열 타입 매개변수로 처리한다.</p>
	<form action="/registerCheckbox01" method="post">
		hobby : <br/>
		<input type="checkbox" name="hobby" value="sports">Sports<br/>
		<input type="checkbox" name="hobby" value="music">Music<br/>
		<input type="checkbox" name="hobby" value="movie">Movie<br/>
		<input type="checkbox" name="hobby" value="book">Book<br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>10) 폼 체크박스 요소값을 문자열 배열 타입 매개변수로 처리한다.</p>
	<form action="/registerCheckbox02" method="post">
		hobbyArray : <br/>
		<input type="checkbox" name="hobbyArray" value="sports">Sports<br/>
		<input type="checkbox" name="hobbyArray" value="music">Music<br/>
		<input type="checkbox" name="hobbyArray" value="movie">Movie<br/>
		<input type="checkbox" name="hobbyArray" value="book">Book<br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>11) 폼 체크박스 요소값을 문자열 요소를 가진 리스트 컬렉션 타입 매개변수로 처리한다.</p>
	<form action="/registerCheckbox03" method="post">
		hobbyList : <br/>
		<input type="checkbox" name="hobbyList" value="sports">Sports<br/>
		<input type="checkbox" name="hobbyList" value="music">Music<br/>
		<input type="checkbox" name="hobbyList" value="movie">Movie<br/>
		<input type="checkbox" name="hobbyList" value="book">Book<br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>13) 폼 체크박스 요소값을 기본 데이터 타입인 불리언 타입 매개변수로 처리한다.</p>
	<form action="/registerCheckbox05" method="post">
		foreigner : <br/>
		<input type="checkbox" name="foreigner" value="true"><br/>
		<input type="submit" value="전송"/><br/>
	</form>
	
	
	<p>14) 폼 텍스트 필드 요소값을 자바빈즈 매개변수로 처리한다.</p>	
	<form action="/registerAddress" method="post">
		postCode : <input type="text" name="postCode"/><br/>
		location : <input type="text" name="location"/><br/>
		<input type = "submit" value="전송"/>
	</form>

	
	<p>15) 폼 텍스트 필드 요소값을 중첩된 자바빈즈 매개변수로 처리한다.</p>	
	<form action="/registerUserAddress" method="post">
		postCode : <input type="text" name="address.postCode"/><br/>
		location : <input type="text" name="address.location"/><br/>
		<input type = "submit" value="전송"/>
	</form>	
	
	
	<p>16) 폼 텍스트 필드 요소값을 중첩된 자바빈즈 매개변수로 처리한다.</p>	
	<form action="/registerUserCardList" method="post">
		카드1 - 번호 : <input type="text" name="cardList[0].no"/><br/>
		카드1 - 유효년월 : <input type="text" name="cardList[0].validMonth"/><br/>	
		카드2 - 번호 : <input type="text" name="cardList[1].no"/><br/>
		카드2 - 유효년월 : <input type="text" name="cardList[1].validMonth"/><br/>
		<input type = "submit" value="전송"/>
	</form>		
	
	
	<br/>
	
	
	<h3>8. 파일업로드 폼 방식 요청 처리</h3>
	<hr/>
	
	<!-- method="post" enctype="multipart/form-data" => 파일 전송시 필수 세팅 -->
	<p>3) 파일 업로드 폼 파일 요소값과 텍스트 필드 요소값을 MultipartFile 매개변수와 자바빈즈 매개변수로 처리한다.</p>
	<form action="/registerFile03" method="post" enctype="multipart/form-data">
		uesrId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="file" name="picture"/><br/>
		<input type="submit" value="업로드"/>
	</form>
	
	
	<p> 4) 파일업로드 폼 파일 요소값과 텍스트 필드 요소값을 FileMember 타입의 자바빈즈 매개변수로 처리한다.</p>
	<form action="/registerFile04" method="post" enctype="multipart/form-data">
		uesrId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="file" name="picture"/><br/>
		<input type="submit" value="업로드"/>
	</form>
	
	
	<p> 5) 여러개의 파일업로드 폼 파일 요소값을 여러개의 MultipartFile 매개변수로 처리한다.</p>
	<form action="/registerFile05" method="post" enctype="multipart/form-data">
		uesrId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="file" name="picture"/><br/>
		<input type="file" name="picture2"/><br/>
		<input type="submit" value="업로드"/>
	</form>
	
	
	<p> 6) 여러개의 파일업로드를 폼 파일 요소 값을 MultipartFile 타입 요소를 가진 리스트 컬렉션 타입 매개변수로 처리한다.</p>
	<form action="/registerFile06" method="post" enctype="multipart/form-data">
		uesrId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="file" name="pictureList[0]"/><br/>
		<input type="file" name="pictureList[1]"/><br/>
		<input type="submit" value="업로드"/>
	</form>
	
		
	<p> 7) 여러개의 파일업로드를 폼 파일 요소 값과 텍스트 필드 요소값을 MultipartMember 타입의 자바빈즈 매개변수로 처리한다.</p>
	<form action="/registerFile07" method="post" enctype="multipart/form-data">
		uesrId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="file" name="pictureList" multiple="multiple"/><br/>
		<input type="submit" value="업로드"/>
	</form>
	
	
	<p> 8) 파일업로드 폼 파일 요소값과 텍스트 필드 요소값을 MultipartFile 타입의 배열 매개변수로 처리한다.</p>
	<form action="/registerFile08" method="post" enctype="multipart/form-data">
		uesrId : <input type="text" name="userId"/><br/>
		password : <input type="text" name="password"/><br/>
		<input type="file" name="pictureArray" multiple="multiple"/><br/>
		<input type="submit" value="업로드"/>
	</form>
	
	

















</body>
</html>