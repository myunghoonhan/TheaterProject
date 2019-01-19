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
 * Servlet implementation class Book
 */
@WebServlet("/Book.do")
public class Book extends HttpServlet {
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
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		TheaterDao tdao = new TheaterDao();
		
		int sprice = tdao.getSprice(sno);
		
		tdao.insertBook(id, sno, bookDate, people, sprice);
		
		String bssdate = bookDate.substring(0, 10);
		tdao.deleteSeat(sno, bssdate, people);		
		
		RequestDispatcher dis = request.getRequestDispatcher("MyPage.do");
		dis.forward(request, response);
	}
}
