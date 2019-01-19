package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TheaterDao;
import dto.BookBean;
import dto.ShowBean;
import dto.TmemberBean;

/**
 * Servlet implementation class MyPage
 */
@WebServlet("/MyPage.do")
public class MyPage extends HttpServlet {
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
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		TheaterDao tdao = new TheaterDao();
		
		TmemberBean bean = tdao.getTmember(id);
		
		Vector<ShowBean> interestBean = tdao.getInterest(id);
		
		Vector<BookBean> bookbean = tdao.getBook(id);
		request.setAttribute("TmemberBean", bean);
		request.setAttribute("interestBean", interestBean);
		request.setAttribute("bookbean", bookbean);
		
		RequestDispatcher dis = request.getRequestDispatcher("src/view/mypage.jsp");
		dis.forward(request, response);
	}
}
