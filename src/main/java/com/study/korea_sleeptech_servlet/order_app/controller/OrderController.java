package com.study.korea_sleeptech_servlet.order_app.controller;

import com.study.korea_sleeptech_servlet.order_app.dao.OrderDao;
import com.study.korea_sleeptech_servlet.order_app.dao.UserDao;
import com.study.korea_sleeptech_servlet.order_app.dao.impl.OrderDaoImpl;
import com.study.korea_sleeptech_servlet.order_app.dao.impl.UserDaoImpl;
import com.study.korea_sleeptech_servlet.order_app.entity.Order;
import com.study.korea_sleeptech_servlet.order_app.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-app/order")
public class OrderController extends HttpServlet {
    private final OrderDao orderDao = new OrderDaoImpl();
    private final UserDao userDao = new UserDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int userId = Integer.parseInt(request.getParameter("userId"));
        String productName = request.getParameter("productName");
        int amount = Integer.parseInt(request.getParameter("amount"));

        User user = userDao.findById(userId);

        if (user == null) {
            response.getWriter().println("해당 사용자가 존재하지 않습니다.");
            return;
        }

        Order order = new Order(userId, productName, amount);
        boolean result = orderDao.save(order);
        request.setAttribute("message", result ? "주문 완료" : "주문 실패");

        if (result) {
            response.sendRedirect("order");
        } else {
            request.setAttribute("message", "주문 실패");
            request.getRequestDispatcher("/order-app/orderForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        String userIdParam = request.getParameter("userId");
        List<Order> orderList;

        if (userIdParam != null && !userIdParam.isEmpty()) {
            try {
                int userId = Integer.parseInt(userIdParam);
                orderList = orderDao.findByUserId(userId);

            } catch (NumberFormatException e) {
                orderList = orderDao.findAll();
            }
        } else {
            orderList = orderDao.findAll(); // 파라미터가 없으면 전체 조회
        }

        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/order-app/orderList.jsp").forward(request, response);
    }
}