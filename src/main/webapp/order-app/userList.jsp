<%@ page import="com.study.korea_sleeptech_servlet.order_app.entity.User" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 25.
  Time: 오전 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>회원 목록 - 쇼핑몰</title>
  <style>
    body { background-color: #f4f4f4; padding: 30px; }
    .container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
    h2 { text-align: center; }
    table { width: 100%; border-collapse: collapse; margin-top: 20px; }
    th, td { padding: 10px; border-bottom: 1px solid #ddd; text-align: center; }
    th { background-color: #007bff; color: white; }
    tr:hover { background-color: #f1f1f1; }
  </style>
</head>
<body>
<div class="container">
  <h2>회원 목록</h2>
  <table>
    <tr>
      <th>ID</th>
      <th>이름</th>
      <th>이메일</th>
    </tr>
    <%
      List<User> userList = (List<User>) request.getAttribute("userList");

      if (userList != null) {
        for (User user : userList) {
    %>
    <tr>
      <td><%= user.getId() %></td>
      <td><%= user.getName() %></td>
      <td><%= user.getEmail() %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="3">사용자 정보가 없습니다.</td>
    </tr>
    <%
      }
    %>
  </table>
</div>
</body>
</html>