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
import dto.QnABean;

/**
 * Servlet implementation class QnAInsert
 */
@WebServlet("/QnAInsert.do")
public class QnAInsert extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        QnABean bean = new QnABean();
        HttpSession session = request.getSession();
        String qid = (String) session.getAttribute("id");

        bean.setQid(qid);
        bean.setQsubject(request.getParameter("subject"));
        bean.setQpw(request.getParameter("pass"));
        bean.setQcontents(request.getParameter("contents"));

        TheaterDao tdao = new TheaterDao();

        tdao.qnaInsert(bean);

        RequestDispatcher dis = request.getRequestDispatcher("QnA.do");
        dis.forward(request, response);
    }

}
