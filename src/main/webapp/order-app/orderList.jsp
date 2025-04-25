<%@ page import="java.util.List" %>
<%@ page import="com.study.korea_sleeptech_servlet.order_app.entity.Order" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 4. 25.
  Time: 오전 11:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>주문 목록 - 쇼핑몰</title>
    <style>
        body { font-family: Arial; background-color: #f4f4f4; padding: 30px; }
        .container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border-bottom: 1px solid #ddd; text-align: center; }
        th { background-color: #28a745; color: white; }
        tr:hover { background-color: #f1f1f1; }
    </style>
</head>
<body>
<div class="container">
    <h2>주문 목록</h2>
    <table>
        <tr>
            <th>주문 ID</th>
            <th>사용자 ID</th>
            <th>상품명</th>
            <th>수량</th>
        </tr>
        <%
            List<Order> orderList = (List<Order>) request.getAttribute("orderList");

            if (orderList != null && !orderList.isEmpty()) {
                for (Order order : orderList) {
        %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= order.getUserId() %></td>
            <td><%= order.getProductName() %></td>
            <td><%= order.getAmount() %></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4">주문 정보가 없습니다.</td>
        </tr>
        <%
            }
        %>
    </table>
</div>
</body>
</html>