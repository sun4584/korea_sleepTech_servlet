<%@ page import="java.util.List" %>
<%@ page import="com.study.korea_sleeptech_servlet.model.User" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 24.
  Time: 오전 10:39
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List</title>
</head>
<body>
<h2>User List</h2>

<table border="1">
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>EMAIL</th>
        <th>COUNTRY</th>

        <%-- 수정과 삭제 링크가 위치할 열 --%>
        <th>ACTIONS</th>
    </tr>

    <%
        // request 객체에서 "listUser"라는 이름으로 전달된 사용자 목록을 받아옴
        List<User> listUser = (List<User>) request.getAttribute("listUser");

        // 사용자 목록이 null이 아닌 경우: 사용자 정보 O
        if (listUser != null) {
            for (User user: listUser) { // 순회되는 데이터(각 사용자 정보)를 행으로 출력(td 태그)
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td><%= user.getCountry() %></td>
        <td>
            <a href="edit?id=<%= user.getId() %>">Edit</a>
            <a href="delete?id=<%= user.getId() %>">Delete</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">No users found.</td>
    </tr>
    <%
        } // else문 종료
    %>

    <br />
    <%-- 새 사용자 추가를 위한 링크 '/new' 경로로 이동--%>
    <a href="new">Add New User</a>
</table>
</body>
</html>