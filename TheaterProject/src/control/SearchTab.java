package control;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TheaterDao;
import dto.ShowBean;

/**
 * Servlet implementation class SearchTab
 */
@WebServlet("/SearchTab.do")
public class SearchTab extends HttpServlet {
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
		
		//선택한 Tab 읽어오기
		String gubun = request.getParameter("gubun");
		
		if(gubun.equals("0")){
			request.setAttribute("center", "main.do");			
			RequestDispatcher dis = request.getRequestDispatcher("Main.jsp");
			dis.forward(request, response);
		}else if(gubun.equals("1")){
			gubun = "콘서트";
			request.setAttribute("active_1", "active");
			request.setAttribute("image", "concert.jpg");
		}else if(gubun.equals("2")){
			gubun = "연극";
			request.setAttribute("active_2", "active");
			request.setAttribute("image", "play.jpg");
		}else if(gubun.equals("3")){
			gubun = "전시회";
			request.setAttribute("active_3", "active");
			request.setAttribute("image", "exhibition.jpg");
		}else if(gubun.equals("4")){
			gubun = "The-Ticket";
			request.setAttribute("active_4", "active");
			request.setAttribute("image", "theticket.jpg");
		}
		
		//데이터 베이스 객체 생성후
		TheaterDao tdao = TheaterDao.getInstance();
		
		//해당 Tab에 대한 TOP4 얻어오는 메소드 호출 getSearchTab_top4(gubun)		
		Vector<ShowBean> searchTab_top4 = tdao.getSearchTab_top4(gubun);
		
		//해당 Tab에 대한 최신 The-Ticket을 얻어오는 메소드 호출
		Vector<ShowBean> searchTab_new = tdao.getSearchTab_new(gubun);
		
		//해당 Tab에 대한 전체공연 수를 얻어오는 메소드 호출
		int searchTab_count = tdao.getSearchTab_count(gubun);
				
		//request객체에 부착
		request.setAttribute("gubun", gubun);
		request.setAttribute("searchTab_top4", searchTab_top4);
		request.setAttribute("searchTab_new", searchTab_new);
		request.setAttribute("searchTab_count", searchTab_count);
		
		RequestDispatcher dis = request.getRequestDispatcher("src/view/searchtab.jsp");
		dis.forward(request, response);
	}
}
