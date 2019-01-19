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
 * Servlet implementation class SearchTabLoc
 */
@WebServlet("/SearchTabLoc.do") //완료
public class SearchTabLoc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqPro(request, response);
	}

	private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		// 선택한 지역 Tab 읽어오기
		String gubunLocation = request.getParameter("gubunLocation");

		if (gubunLocation.equals("0")) {
			gubunLocation = "대학로";
		} else if (gubunLocation.equals("1")) {
			gubunLocation = "홍대";
		} else if (gubunLocation.equals("2")) {
			gubunLocation = "강남";
		} else if (gubunLocation.equals("3")) {
			gubunLocation = "기타";
		}
		request.setAttribute("active_5", "active");

		// 데이터 베이스 객체 생성후
		TheaterDao tdao = new TheaterDao();

		// 해당 Tab에 대한 TOP4 얻어오는 메소드 호출 getSearchTab_top4_loc(gubunLocation);
		Vector<ShowBean> searchTab_top4_loc = tdao.getSearchTab_top4_loc(gubunLocation);

		// 해당 Tab에 대한 최신 The-Ticket을 얻어오는 메소드 호출
		Vector<ShowBean> searchTab_new_loc = tdao.getSearchTab_new_loc(gubunLocation);

		// 해당 Tab에 대한 전체공연 수를 얻어오는 메소드 호출
		int searchTab_count_loc = tdao.getSearchTab_count_loc(gubunLocation);

		// request객체에 부착
		request.setAttribute("gubunLocation", gubunLocation);
		request.setAttribute("searchTab_top4_loc", searchTab_top4_loc);
		request.setAttribute("searchTab_new_loc", searchTab_new_loc);
		request.setAttribute("searchTab_count_loc", searchTab_count_loc);

		RequestDispatcher dis = request.getRequestDispatcher("src/view/searchtabloc.jsp");
		dis.forward(request, response);
	}
}
