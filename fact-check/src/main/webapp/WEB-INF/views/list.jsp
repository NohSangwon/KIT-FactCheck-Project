<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<title>KIT FactCheck</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
   href="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.css" />
<script
   src="https://cdn.datatables.net/t/bs-3.3.6/jqc-1.12.0,dt-1.10.11/datatables.min.js"></script>
<script>
   jQuery(function($) {
      $("#table").DataTable({
         order : [ [ 0, "desc" ] ],
         lengthMenu : [ 5, 10, 20, 50 ],
         columnDefs : [ {
            "searchable" : false,
            "targets" : 1
         }, {
            "searchable" : false,
            "targets" : 3
         } ],
         oLanguage : {
            "sSearch" : "이메일 검색 : ",
            "sLengthMenu" : "페이지당 보여지는 갯수 : _MENU_ ",
            "oPaginate" : {
               "sFirst" : "처음", // This is the link to the first page
               "sPrevious" : "이전", // This is the link to the previous page
               "sNext" : "다음", // This is the link to the next page
               "sLast" : "마지막" // This is the link to the last page
            }
         }
      });
   });
</script>
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

/* -- Material Design Table style -------------- */
.table {
   width: 100%;
   width: 100%;
   margin-bottom: 2rem;
   background-color: #fff;
   max-width: none !important;
}

.table>thead>tr, .table>tbody>tr, .table>tfoot>tr {
   -webkit-transition: all 0.3s ease;
   -o-transition: all 0.3s ease;
   transition: all 0.3s ease;
}

.table>thead>tr>th, .table>tbody>tr>th, .table>tfoot>tr>th, .table>thead>tr>td,
   .table>tbody>tr>td, .table>tfoot>tr>td {
   text-align: left;
   padding: 1.6rem;
   vertical-align: top;
   border-top: 0;
   -webkit-transition: all 0.3s ease;
   -o-transition: all 0.3s ease;
   transition: all 0.3s ease;
}

.table>tbody>tr>td>div {
   width: 80%;
   white-space: nowrap;
   overflow: hidden;
   text-overflow: ellipsis;
}

.table>thead>tr>th {
   font-weight: 400;
   color: #757575;
   vertical-align: bottom;
   border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

.table>caption+thead>tr:first-child>th, .table>colgroup+thead>tr:first-child>th,
   .table>thead:first-child>tr:first-child>th, .table>caption+thead>tr:first-child>td,
   .table>colgroup+thead>tr:first-child>td, .table>thead:first-child>tr:first-child>td
   {
   border-top: 0;
}

.table>tbody+tbody {
   border-top: 1px solid rgba(0, 0, 0, 0.12);
}

.table .table {
   background-color: #fff;
}

.table .no-border {
   border: 0;
}

.table-condensed>thead>tr>th, .table-condensed>tbody>tr>th,
   .table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td,
   .table-condensed>tbody>tr>td, .table-condensed>tfoot>tr>td {
   padding: 0.8rem;
}

.table-bordered {
   border: 0;
}

.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th,
   .table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td,
   .table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {
   border: 0;
   border-bottom: 1px solid #e0e0e0;
}

.table-bordered>thead>tr>th, .table-bordered>thead>tr>td {
   border-bottom-width: 2px;
}

.table-striped>tbody>tr:nth-child(odd)>td, .table-striped>tbody>tr:nth-child(odd)>th
   {
   background-color: #f5f5f5;
}

.table-hover>tbody>tr:hover>td, .table-hover>tbody>tr:hover>th {
   background-color: rgba(0, 0, 0, 0.12);
}
</style>

<body>
   <div id="demo">
      <h1>Kit Fact Check</h1>
      <input type="button" class="btn btn-sm btn-primary" onclick="location.href='register'"
         style="float: right; margin-bottom: 10px; font-size: 1.4rem" value="신청하기">
      <br>

      <!-- Responsive table starts here -->
      <!-- For correct display on small screens you must add 'data-title' to each 'td' in your table -->
      <div class="table-responsive-vertical shadow-z-1">
         <!-- Table starts here -->
         <table id="table" class="table table-hover table-mc-light-blue">
            <thead>
               <tr>
                  <th>No.</th>
                  <th>제목</th>
                  <th>신청인</th>
                  <th>현재상태</th>
               </tr>
            </thead>
            <tbody>
               <c:forEach var="i" begin="0" end="${list.size()-1}">
                  <tr>
                     <td data-title="id">${list.get(i).getId()}</td>
                     <td data-title="title">
                        <div><a href='result?id=${list.get(i).getId()}' style="text-decoration:none;">
                        <c:forEach var="j" begin="0" end="${tlist.get(i).length()-1}">
                        ${tlist.get(i).get(j).get("sentence")}
                        </c:forEach>
                        </a></div>
                     </td>
                     <td data-title="user">${list.get(i).getUser()}</td>
                     <c:set var="sts" value='${list.get(i).getStatus()}' />
                                <c:choose>
                                    <c:when test="${sts eq '판정대기'}">
                                        <td data-title="status" style="color: red">${list.get(i).getStatus()}</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td data-title="status" style="color: blue">${list.get(i).getStatus()}</td>
                                    </c:otherwise>
                                </c:choose>
                  </tr>
               </c:forEach>
            </tbody>
         </table>
      </div>
   </div>
</body>

</html>