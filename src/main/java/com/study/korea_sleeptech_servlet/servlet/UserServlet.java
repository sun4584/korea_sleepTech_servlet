package com.study.korea_sleeptech_servlet.servlet;

/*
 * 1. 서블릿
 * : 자바로 만들어진 웹 프로그래밍 도구
 * : 동적 웹 페이지를 만들 때 사용되는 자바 기반의 웹 애플리케이션 프로그래밍 기술
 * - 웹 요청과 응답의 흐름을 메서드 호출만으로 체계적인 설계를 다룸
 *
 * 2. 서블릿 컨테이너
 * : 구현되어 있는 Servlet 클래스의 규칙에 맞게 서블릿을 담고 관리해주는 컨테이너
 * - 클라이언트에서 요청을 하면 컨테이너는 HttpServletRequest, HttpServletResponse 두 객체를 생성
 *       + post, get 여부에 따라 동적인 페이지를 생성하여 응답을 전송
 *
 * 3. 서블릿 전체 동작 흐름
 * 1) 웹브라우저에서 요청
 * 2) 톰캣(Web Application Server)이 요청 받음
 * 3) 톰캣이 서블릿 컨테이너에 전달: web.xml 또는 @WebServlet을 보고 해당하는 서블릿 확인
 * 4) 서블릿 컨테이너가 서블릿을 호출: init() >> doGet() || doPost()
 * 5) 동적 페이지 생성
 * 6) 응답 전달
 * */

// jakarta.servlet: 서블릿 관련 기본 인터페이스 제공

import com.study.korea_sleeptech_servlet.dao.UserDao;
import com.study.korea_sleeptech_servlet.model.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
// 서블릿 URL 매핑을 위한 어노테이션
// : 루트 경로("/")에 매핑되어 모든 요청을 받아 처리
public class UserServlet extends HttpServlet {
    private UserDao userDao; // 사용자 데이터 처리를 위한 DAO 객체

    // cf) 서블릿 생명주기
    // 1) init()
    // : 서블릿 생성 시 단 한 번만 호출
    // - DB 연결, 초기 설정 등에 사용

    // 2) doGet(), doPost()
    // : 사용자 요청 처리 (실제 비즈니스 로직)

    // 서블릿 초기화 메서드
    // : 최초 요청 시 한 번만 실행
    public void init() {
        userDao = new UserDao();
    }

    // GET 요청 처리 메서드
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 현재 요청한 경로(URL 패턴)를 가져옴
        String action = request.getServletPath();

        try {
            // URL 경로에 따라 서로 다른 메서드 호출
            switch (action) {
                case "/new": // "새 사용자 입력 폼 표시"
                    showNewForm(request, response);
                    break;
                case "/insert": // 새 사용자 정보 저장
                    insertUser(request, response);
                    break;
                case "/edit": // "수정 폼 표시"
                    showEditForm(request, response);
                    break;
                case "/update": // 기존 사용자 정보 수정
                    updateUser(request, response);
                    break;
                case "/delete": // 사용자 삭제
                    deleteUser(request, response);
                    break;
                default: // 사용자 목록 출력 (기본 동작)
                    listUser(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // POST 요청 처리 메서드 (POST 요청을 GET 요청처럼 처리)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // ========================================================================== //
    // 1. 새 사용자 입력 폼을 보여주는 메서드
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // 사용자 입력 폼 페이지로 요청 전달
        // : 서버 내부에서 화면 전환
        // RequestDispatcher: 요청을 다른 자원(JSP, 서블릿 등)으로 넘기는 객체
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/form.jsp");
        dispatcher.forward(request, response); // 해당 경로로 서버 내부에서 이동
    }

    // 2. 새로운 사용자 정보를 DB에 삽입하는 메서드
    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // 요청 파라미터에서 name, email, country 값을 받아오기
        // : request.getParameter(String attrValue)
        // - 자바 서블릿에서 클라이언트가 보낸 HTML 폼 데이터나 쿼리스트링 데이터를 서버에서 꺼낼 때 사용
        // cf) attrValue: 클라이언트가 전송한 데이터의 파라미터 이름 (input 태그의 name 속성)
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User newUser = new User(0, name, email, country);

        userDao.insertUser(newUser); // DAO를 통해 사용자 정보 DB에 저장

        // response.sendRedirect("URL")
        // : 자바 서블릿에서 클라이언트에게 다른 페이지로 이동을 명령하는 기능
        response.sendRedirect("list"); // '/list'로 가라! (내부적으로 새로운 요청을 발생시키는 방식)
    }

    // 3. 기존 사용자 정보 수정 폼을 보여주는 메서드
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // URL 파라미터에서 전달된 사용자 id 가져오기
        int id = Integer.parseInt(request.getParameter("id"));

        User existingUser = userDao.selectUser(id); // 요청 받은 id의 (기존) 사용자 정보를 조회

        // 조회한 사용자 정보를 request 객체에 저장 (JSP에서 사용하기 위함)
        request.setAttribute("user", existingUser);

        // 사용자 폼 페이지로 이동
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/form.jsp");
        dispatcher.forward(request, response);
    }

    // 4. 사용자 정보를 업데이트(수정)하는 메서드
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        // 요청 파라미터에서 데이터 추출
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        // 수정된 정보를 가진 User 객체 생성
        User user = new User(id, name, email, country);

        userDao.updateUser(user);

        // 사용자 목록 페이지로 리다이렉트
        response.sendRedirect("list");
    }

    // 5. 사용자 정보를 삭제하는 메서드
    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));

        userDao.deleteUser(id);

        response.sendRedirect("list");
    }

    // 6. 전체 사용자 목록을 조회하여 보여주는 메서드
    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<User> listUser = userDao.selectAllUsers();

        request.setAttribute("listUser", listUser);

        // 사용자 목록을 보여주는 JSP 페이지로 이동
        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/list.jsp");
        dispatcher.forward(request, response);
    }
}