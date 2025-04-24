package com.study.korea_sleeptech_servlet;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

// @WebServlet 어노테이션
// : 해당 서블릿을 무낮열의 URL 패턴과 매핑
// : "/hello-servlet" 요청의 경우 '현재의 서블릿이 처리됨'
// @WebServlet(name = "helloServlet", value = "/hello")
public class HelloServlet extends HttpServlet {
    /*
    * === HelloServlet 클래스 ===
    * : 서블릿 내부에서는 Http 요청을 처리하는 HttpServlet 을 상속받는 각각의 서블릿이 존재
    *
    * 서블릿 동작 원리
    * 1) 클라이언트 요청: 사용자가 브라우저에서 http://localhost:8080/hello 요청
    * 2) 톰캣 수신: 톰캣이 HTTP 요청을 받음
    * 3) 매핑 검사: 톰캣이 web.xml 또는 @WebServlet에 정의된 URL 패턴으로 서블릿 클래스를 탐색
    * 4) 서블릿 호출
    *       - 해당 서블릿 인스턴스를 찾아서 service() 호출
    *       >> GET 요청이면 doGet(), POST 요청이면 doPost()가 실행
    * 5) 응답 처리
    *       - 서블릿이 HTML, JSON 파일 등으로 요청을 생성
    *
    * */
    private String message;

    public void init() {
        message = "첫 번째 서블릿 페이지 입니다.";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}