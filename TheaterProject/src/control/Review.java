package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TheaterDao;

/**
 * Servlet implementation class Review
 */
@WebServlet("/Review.do")
public class Review extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");

        String sno = request.getParameter("sno");

        String contents = request.getParameter("contents");

        TheaterDao tdao = new TheaterDao();

        tdao.insertReview(id, sno, contents);

        request.setAttribute("sno", sno);

        RequestDispatcher dis = request.getRequestDispatcher("TicketInfo.do");
        dis.forward(request, response);
    }

}
