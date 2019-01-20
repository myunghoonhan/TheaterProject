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
 * Servlet implementation class QnAUpdate
 */
@WebServlet("/QnAUpdate.do")
public class QnAUpdate extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String pass = request.getParameter("pass"); // 수정할때폼에 입력된 비밀번호
        String qno = request.getParameter("qno"); // qnainfo에서 넘어온 qno

        QnABean bean = new QnABean();
        TheaterDao tdao = new TheaterDao();

        bean = tdao.getOneboard(qno);

        String repw = bean.getQpw(); // 폼에 쓸때 입력한 비밀번호

        if (pass.equals(repw)) {
            HttpSession session = request.getSession();
            String id = (String) session.getAttribute("id");

            int check = tdao.updateqna(id, qno);

            if (check != 1) {
                request.setAttribute("msg", "1");
                RequestDispatcher dis = request.getRequestDispatcher("QnA.do");
                dis.forward(request, response);
            } else {// 맞다면
                request.setAttribute("bean", bean);
                request.setAttribute("qno", qno);
                RequestDispatcher dis = request.getRequestDispatcher("src/view/qnainfo2.jsp");
                dis.forward(request, response);
            }
        } else {
            request.setAttribute("msg", "2");
            RequestDispatcher dis = request.getRequestDispatcher("QnAInfo.do");
            dis.forward(request, response);
        }

    }
}
