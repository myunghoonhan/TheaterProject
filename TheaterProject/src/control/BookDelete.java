package control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TheaterDao;

/**
 * Servlet implementation class BookDelete
 */
@WebServlet("/BookDelete.do")
public class BookDelete extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		reqPro(request,response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		reqPro(request,response);
	}	
	private void reqPro(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String sno = request.getParameter("sno");
		String bookDate = request.getParameter("bookDate");
		int people = Integer.parseInt(request.getParameter("people"));
		int bno = Integer.parseInt(request.getParameter("bno"));
		
		TheaterDao tdao = new TheaterDao();
		
		//������Ҹ� �ϴ� �޼ҵ� ȣ��
		String bssdate = bookDate.substring(0, 10);
		int ssseat = tdao.deleteBook(sno, bno, bssdate, people);
		tdao.updateBook(ssseat, sno, bssdate);
		
		RequestDispatcher dis = request.getRequestDispatcher("MyPage.do");
		dis.forward(request, response);
	}
}
