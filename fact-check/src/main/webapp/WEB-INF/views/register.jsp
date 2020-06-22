<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<title>KIT FactCheck</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
</head>

<style>
/* -- import Roboto Font ---------------------------- */
@font-face {
	font-family: 'Roboto';
	font-style: italic;
	font-weight: 100;
	src: local('Roboto Thin Italic'), local('Roboto-ThinItalic'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOiCnqEu92Fr1Mu51QrEzQdKg.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: italic;
	font-weight: 300;
	src: local('Roboto Light Italic'), local('Roboto-LightItalic'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51TjASc-CsE.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: italic;
	font-weight: 400;
	src: local('Roboto Italic'), local('Roboto-Italic'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOkCnqEu92Fr1Mu51xMIzc.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: italic;
	font-weight: 500;
	src: local('Roboto Medium Italic'), local('Roboto-MediumItalic'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51S7ACc-CsE.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: italic;
	font-weight: 700;
	src: local('Roboto Bold Italic'), local('Roboto-BoldItalic'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51TzBic-CsE.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: italic;
	font-weight: 900;
	src: local('Roboto Black Italic'), local('Roboto-BlackItalic'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51TLBCc-CsE.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 100;
	src: local('Roboto Thin'), local('Roboto-Thin'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOkCnqEu92Fr1MmgVxMIzc.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 300;
	src: local('Roboto Light'), local('Roboto-Light'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmSU5fABc9.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 400;
	src: local('Roboto'), local('Roboto-Regular'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOmCnqEu92Fr1Mu5mxP.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 500;
	src: local('Roboto Medium'), local('Roboto-Medium'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmEU9fABc9.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 700;
	src: local('Roboto Bold'), local('Roboto-Bold'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmWUlfABc9.ttf)
		format('truetype');
}

@font-face {
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 900;
	src: local('Roboto Black'), local('Roboto-Black'),
		url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmYUtfABc9.ttf)
		format('truetype');
}

/* -- You can use this tables in Bootstrap (v3) projects. -- */
/* -- Box model ------------------------------- */
*, *:after, *:before {
	-webkit-box-sizing: border-box;
	-moz-box-sizing: border-box;
	box-sizing: border-box;
}

/* -- Demo style ------------------------------- */
html, body {
	position: relative;
	min-height: 100%;
	height: 100%;
}

html {
	position: relative;
	overflow-x: hidden;
	margin: 16px;
	padding: 0;
	min-height: 100%;
	font-size: 62.5%;
}

body {
	font-family: 'RobotoDraft', 'Roboto', 'Helvetica Neue, Helvetica, Arial',
		sans-serif;
	font-style: normal;
	font-weight: 300;
	font-size: 1.4rem;
	line-height: 2rem;
	letter-spacing: 0.01rem;
	color: #212121;
	background-color: #f5f5f5;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-rendering: optimizeLegibility;
}

#demo {
	margin: 20px auto;
	width: 960px;
	max-width: none !important;
}

#demo h1 {
	font-size: 2.4rem;
	line-height: 3.2rem;
	letter-spacing: 0;
	font-weight: 300;
	color: #212121;
	text-transform: inherit;
	margin-bottom: 1rem;
	text-align: center;
}

#demo h2 {
	font-size: 1.5rem;
	line-height: 2.8rem;
	letter-spacing: 0.01rem;
	font-weight: 400;
	color: #212121;
	text-align: center;
}

.mb-3>input {
	font-size: 1.4rem;
	height: 30px;
}

.mb-3>textarea {
	font-size: 1.4rem;
}

.container {
	width: 960px;
	max-width: none !important;
}
</style>

<body>
	<div id="demo">
		<h1>Kit Fact Check</h1>
		<br>
		<article>

			<div class="container" role="main">

				<form name="form" id="form" role="form" method="post" action="write">

					<div class="mb-3">

						<label for="user">신청자 이메일</label> <input type="text"
							class="form-control" name="user" id="user"
							placeholder="이메일을 입력해 주세요">

					</div>

					<div class="mb-3">

						<label for="title">제목</label> <input type="text"
							class="form-control" name="title" id="title"
							placeholder="제목을 입력해 주세요">

					</div>

					<div class="mb-3">

						<label for="url">URL</label> <input type="text"
							class="form-control" name="url" id="url"
							placeholder="URL을 입력해 주세요 (기사만 해당)">

					</div>



					<div class="mb-3">

						<label for="content">내용</label>

						<textarea class="form-control" rows="15" name="content"
							id="content" placeholder="내용을 입력해 주세요"></textarea>

					</div>

					<div>
						<input type="submit" class="btn btn-sm btn-primary"
							style="font-size: 1.4rem; background-color: #337ab7; width: 6%"
							value="신청"> 
						<input type="button"
							class="btn btn-sm btn-primary"
							style="font-size: 1.4rem; background-color: #337ab7; width: 6%"
							onclick="location.href='list'" value="목록">
					</div>
				</form>

			</div>

		</article>
	</div>
</body>

</html>