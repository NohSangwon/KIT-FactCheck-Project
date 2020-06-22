<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <title>KIT FactCheck</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
</head>

<style>
    /* -- import Roboto Font ---------------------------- */
    @font-face {
        font-family: 'Roboto';
        font-style: italic;
        font-weight: 100;
        src: local('Roboto Thin Italic'), local('Roboto-ThinItalic'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOiCnqEu92Fr1Mu51QrEzQdKg.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: italic;
        font-weight: 300;
        src: local('Roboto Light Italic'), local('Roboto-LightItalic'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51TjASc-CsE.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: italic;
        font-weight: 400;
        src: local('Roboto Italic'), local('Roboto-Italic'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOkCnqEu92Fr1Mu51xMIzc.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: italic;
        font-weight: 500;
        src: local('Roboto Medium Italic'), local('Roboto-MediumItalic'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51S7ACc-CsE.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: italic;
        font-weight: 700;
        src: local('Roboto Bold Italic'), local('Roboto-BoldItalic'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51TzBic-CsE.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: italic;
        font-weight: 900;
        src: local('Roboto Black Italic'), local('Roboto-BlackItalic'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOjCnqEu92Fr1Mu51TLBCc-CsE.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 100;
        src: local('Roboto Thin'), local('Roboto-Thin'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOkCnqEu92Fr1MmgVxMIzc.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 300;
        src: local('Roboto Light'), local('Roboto-Light'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmSU5fABc9.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 400;
        src: local('Roboto'), local('Roboto-Regular'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOmCnqEu92Fr1Mu5mxP.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 500;
        src: local('Roboto Medium'), local('Roboto-Medium'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmEU9fABc9.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 700;
        src: local('Roboto Bold'), local('Roboto-Bold'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmWUlfABc9.ttf) format('truetype');
    }

    @font-face {
        font-family: 'Roboto';
        font-style: normal;
        font-weight: 900;
        src: local('Roboto Black'), local('Roboto-Black'),
            url(https://fonts.gstatic.com/s/roboto/v20/KFOlCnqEu92Fr1MmYUtfABc9.ttf) format('truetype');
    }

    /* -- You can use this tables in Bootstrap (v3) projects. -- */
    /* -- Box model ------------------------------- */
    *,
    *:after,
    *:before {
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }

    /* -- Demo style ------------------------------- */
    html,
    body {
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

    blockquote {
        background: #e9ecef;
        border-radius: 5px;
        padding: 10px;
        border-color: #ced4da;
        border-style: solid;
        border-width: 1px;
    }
</style>

<body>
    <script language=javascript>
        function showLayer(layerID) {
            document.all[layerID].style.visibility = "visible";
        }

        function hideLayer(layerID) {
            document.all[layerID].style.visibility = "hidden";
        }
    </script>

    <div id="demo">
        <h1>Kit Fact Check</h1>
        <br>
        <article>

            <div class="container" role="main">

                <form name="result" id="form">

                    <div class="mb-3">
                        <label for="user">신청자 이메일</label>
                        <blockquote>${result.getUser()}</blockquote>
                    </div>

                    <div class="mb-3">
                        <label for="title">제목</label>
                        <blockquote>
                            <c:forEach var="i" begin="0" end="${titleArray.length()-1}">
                                ${titleArray.get(i).get("preBlank")}
                                <c:set var="rank" value='${titleArray.get(i).get("rank")}' />
                                <c:choose>
                                    <c:when test="${rank eq '1'}">
                                        <a href="${titleArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: red" onmouseout="javascript:hideLayer('title_tf${i}');" onmouseover="javascript:showLayer('title_tf${i}');">${titleArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '2'}">
                                        <a href="${titleArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: orange" onmouseout="javascript:hideLayer('title_tf${i}');" onmouseover="javascript:showLayer('title_tf${i}');">${titleArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '3'}">
                                        <a href="${titleArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: yellowgreen" onmouseout="javascript:hideLayer('title_tf${i}');" onmouseover="javascript:showLayer('title_tf${i}');">${titleArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '4'}">
                                        <a href="${titleArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: green" onmouseout="javascript:hideLayer('title_tf${i}');" onmouseover="javascript:showLayer('title_tf${i}');">${titleArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '5'}">
                                        <a href="${titleArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: blue" onmouseout="javascript:hideLayer('title_tf${i}');" onmouseover="javascript:showLayer('title_tf${i}');">${titleArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a style="color: black">${titleArray.get(i).get("sentence")}</a>
                                    </c:otherwise>
                                </c:choose>
                                <div id="title_tf${i}" style="position: absolute; visibility: hidden;">
                                    <table border=1 onmouseout="javascript:hideLayer('title_tf${i}');" onmouseover="javascript:showLayer('title_tf${i}');">
                                        <tr>
                                            <td bgcolor=white>
				                                <c:set var="rank" value='${titleArray.get(i).get("rank")}' />
					                                <c:choose>
					                                    <c:when test="${rank eq '1'}">
					                                       <a style="font-weight: bold;">[거짓] - ${titleArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                    <c:when test="${rank eq '2'}">
					                                       <a style="font-weight: bold;">[대체로 거짓] - ${titleArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                    <c:when test="${rank eq '3'}">
					                                       <a style="font-weight: bold;">[절반의 사실] - ${titleArray.get(i).get("tfsentence")}</a>
														</c:when>
					                                    <c:when test="${rank eq '4'}">
					                                        <a style="font-weight: bold;">[대체로 사실] - ${titleArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                    <c:when test="${rank eq '5'}">
															<a style="font-weight: bold;">[사실] - ${titleArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                </c:choose>
                                                <br/>클릭 시 다음 링크로 이동 : ${titleArray.get(i).get("url")}<br/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>                                
                            </c:forEach>
                        </blockquote>
                    </div>

                    <div class="mb-3">
                        <label for="url">URL</label>
                        <blockquote>${result.getUrl()}</blockquote>
                    </div>


                    <div class="mb-3">
                        <label for="content">내용</label>
                        <blockquote>
                            <c:forEach var="i" begin="0" end="${contentArray.length()-1}">
                                ${contentArray.get(i).get("preBlank")}
                                <c:set var="rank" value='${contentArray.get(i).get("rank")}' />
                                <c:choose>
                                    <c:when test="${rank eq '1'}">
                                        <a href="${contentArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: red" onmouseout="javascript:hideLayer('content_tf${i}');" onmouseover="javascript:showLayer('content_tf${i}');">${contentArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '2'}">
                                        <a href="${contentArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: orange" onmouseout="javascript:hideLayer('content_tf${i}');" onmouseover="javascript:showLayer('content_tf${i}');">${contentArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '3'}">
                                        <a href="${contentArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: yellowgreen" onmouseout="javascript:hideLayer('content_tf${i}');" onmouseover="javascript:showLayer('content_tf${i}');">${contentArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '4'}">
                                        <a href="${contentArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: green" onmouseout="javascript:hideLayer('content_tf${i}');" onmouseover="javascript:showLayer('content_tf${i}');">${contentArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:when test="${rank eq '5'}">
                                        <a href="${contentArray.get(i).get("url")}" target="_blank" style="text-decoration:none;color: blue" onmouseout="javascript:hideLayer('content_tf${i}');" onmouseover="javascript:showLayer('content_tf${i}');">${contentArray.get(i).get("sentence")}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <a style="color: black">${contentArray.get(i).get("sentence")}</a>
                                    </c:otherwise>
                                </c:choose>
                                <div id="content_tf${i}" style="position: absolute; visibility: hidden;">
                                    <table border=1 onmouseout="javascript:hideLayer('content_tf${i}');" onmouseover="javascript:showLayer('content_tf${i}');">
                                        <tr>
                                            <td bgcolor=white>
				                                <c:set var="rank" value='${contentArray.get(i).get("rank")}' />
					                                <c:choose>
					                                    <c:when test="${rank eq '1'}">
					                                       <a style="font-weight: bold;">[거짓] - ${contentArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                    <c:when test="${rank eq '2'}">
					                                        <a style="font-weight: bold;">[대체로 거짓] - ${contentArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                    <c:when test="${rank eq '3'}">
					                                        <a style="font-weight: bold;">[절반의 사실] - ${contentArray.get(i).get("tfsentence")}</a>
														</c:when>
					                                    <c:when test="${rank eq '4'}">
					                                        <a style="font-weight: bold;">[대체로 사실] - ${contentArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                    <c:when test="${rank eq '5'}">
					                                        <a style="font-weight: bold;">[사실] - ${contentArray.get(i).get("tfsentence")}</a>
					                                    </c:when>
					                                </c:choose>
                                                <br/>클릭 시 다음 링크로 이동 : ${contentArray.get(i).get("url")}<br/>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </c:forEach>
                        </blockquote>
                    </div>

                    <div>
                        <input type="button" class="btn btn-sm btn-primary" style="font-size: 1.4rem; background-color: #337ab7; width: 6%" onclick="location.href='list'" value="목록">
                    </div>

                </form>
            </div>

        </article>
    </div>
</body>

</html>