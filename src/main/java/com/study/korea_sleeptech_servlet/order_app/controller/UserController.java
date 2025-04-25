package com.study.korea_sleeptech_servlet.order_app.controller;

import com.study.korea_sleeptech_servlet.order_app.dao.UserDao;
import com.study.korea_sleeptech_servlet.order_app.dao.impl.UserDaoImpl;
import com.study.korea_sleeptech_servlet.order_app.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/*
 * === MVC 패턴의 컨트롤러 ===
 *
 * View와 Model 사이를 통제
 * : JSP와 비즈니스로직(DAO)+DB 사이를 Servlet이 통제
 *   >> Servlet 컨트롤러
 * */
@WebServlet("/order-app/user")
public class UserController extends HttpServlet {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        System.out.println("qweqwe");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name == null || name.isEmpty() || email == null || !email.contains("@")) {
            request.setAttribute("message", "입력값이 올바르지 않습니다.");
            request.getRequestDispatcher("/order-app/userForm.jsp").forward(request, response);
            return;
        }

        User user = new User(name, email);
        System.out.println(user.getName() + user.getEmail());
        boolean result = userDao.save(user);
        request.setAttribute("message", result ? "회원가입 성공" : "회원가입 실패");

        if (result) {
            response.sendRedirect("user");
        } else {
            request.setAttribute("message", "회원가입 실패");
            request.getRequestDispatcher("/order-app/userForm.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userDao.findAll();
        request.setAttribute("userList", users);
        request.getRequestDispatcher("/order-app/userList.jsp").forward(request, response);
    }
}