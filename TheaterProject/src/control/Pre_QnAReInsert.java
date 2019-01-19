package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Pre_QnAReInsert
 */
@WebServlet("/Pre_QnAReInsert.do")
public class Pre_QnAReInsert extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		request.setAttribute("qgroup", request.getParameter("qgroup"));
		request.setAttribute("qstep", request.getParameter("qstep"));
		request.setAttribute("qlevel", request.getParameter("qlevel"));
		
		RequestDispatcher dis = request.getRequestDispatcher("src/view/qnareinsert.jsp");
		dis.forward(request, response);
	}

}
