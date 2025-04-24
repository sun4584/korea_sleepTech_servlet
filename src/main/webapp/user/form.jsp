<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 24.
  Time: 오전 10:39
  To change this template use File | Settings | File Templates.
--%>
<%--
    ===== JSP(JavaServer Pages) =====
    : HTML에 자바 코드를 삽입할 수 있는 서버용 페이지
    : .jsp
    - MVC 패턴에서 View의 역할(출력)

--%>
<%-- page 지시자: JSP 파일의 속성을 지정(UTF-8로 인코딩) --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Form</title>
</head>
<body>
<%-- 출력문(자바표현식): <%= 표현식 %> --%>
<%--
    <%
        String name = "이승아";
    %>
    <p>안녕하세요, <%= name%>님</p>
--%>

<%-- JSP에서 request 사용하기: 서블릿에서 데이터를 보낸 경우 --%>
<%--
    request.setAttribute("name", "lsa");
    >>> (JSP에서 출력) <%= request.getAttribute("name") %>
 --%>

<%--  EL(Expression Language)을 사용하여 조건식 처리: setAttribute를 통해 전달된 객체 참조  --%>
<h2>${user == null ? "New User" : "Edit User"}</h2>

<form action="${user == null ? 'insert' : 'update'}" method="post">
    <input type="hidden" name="id" value="${user.id}" />

    <input type="text" name="name" value="${user.name}" />
    <br />
    <input type="text" name="email" value="${user.email}" />
    <br />
    <input type="text" name="country" value="${user.country}" />
    <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>