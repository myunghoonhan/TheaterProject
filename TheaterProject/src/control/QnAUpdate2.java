package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TheaterDao;
import dto.QnABean;

/**
 * Servlet implementation class QnAUpdate2
 */
@WebServlet("/QnAUpdate2.do")
public class QnAUpdate2 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		reqPro(request, response);
	}

	private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String title = request.getParameter("title"); 
		String qno = request.getParameter("qno");
		String contents = request.getParameter("contents");
		System.out.println(title+qno+contents);
		QnABean bean = new QnABean();
		TheaterDao tdao = new TheaterDao();
		
		bean = tdao.getOneboard(qno);
		
		tdao.updateqna2(bean, title, contents);
		
		RequestDispatcher dis = request.getRequestDispatcher("QnA.do");
		dis.forward(request, response);
	}
}
