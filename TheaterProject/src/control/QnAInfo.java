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
 * Servlet implementation class QnAInfo
 */
@WebServlet("/QnAInfo.do")
public class QnAInfo extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String qno = request.getParameter("qno");
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		TheaterDao tdao = new TheaterDao();
		
		QnABean bean = tdao.getOneboard(qno);
		
		request.setAttribute("bean", bean);
		request.setAttribute("id", id);
		RequestDispatcher dis = request.getRequestDispatcher("src/view/qnainfo.jsp");
		dis.forward(request, response);
	}
}
