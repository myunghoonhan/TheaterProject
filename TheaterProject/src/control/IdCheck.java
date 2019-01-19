package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TheaterDao;

@WebServlet("/IdCheck.do") //완료
public class IdCheck extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqpro(request, response);
	}

	protected void reqpro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");

		// 데이터베이스에 접근
		TheaterDao tdao = TheaterDao.getInstance();
		// 아이디 체크 메소드
		int count = tdao.getCheckId(id);

		request.setAttribute("count", count);
		request.setAttribute("id", id);
		
		RequestDispatcher dis = request.getRequestDispatcher("src/view/component/IdCheck.jsp");
		dis.forward(request, response);

	}
}
