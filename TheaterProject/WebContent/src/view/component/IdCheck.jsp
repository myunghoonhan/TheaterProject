<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <c:set var="count" value="${count}"/>
	<table border=0 align = "center">
      <tr>
         <td align = "center">
         <c:if test="${count==1}">
		   <br>
           ${id}는 이미 존재하는 ID입니다. &nbsp;<br><br>
         <input type="button" value="닫기" onClick="javascript:self.close();
               opener.document.regForm.mem_id.focus();">
		 </c:if>
		 
		 <c:if test="${count!=1}">
		   <br>
         	${id}는 사용가능한 ID입니다. &nbsp;<br><br>
         <input type="button" value="닫기" onClick="javascript:self.close();
                 opener.document.regForm.mem_passwd.focus();">
		 </c:if>
         </td>
      </tr>
   </table>
</body>
</html>