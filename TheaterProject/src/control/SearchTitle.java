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
 * Servlet implementation class SearchTitle
 */
@WebServlet("/SearchTitle.do")
public class SearchTitle extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 검색한 내용 읽어오기
        String search = request.getParameter("search");
        String url = request.getParameter("url");

        // 데이터 베이스 객체 생성후
        TheaterDao tdao = new TheaterDao();

        String sql = "select * from show where sname like '%" + search + "%'";

        // 해당 공연이 있는지 여부를 알아보는 메소드 호출 getSearchTitle(search)
        Vector<ShowBean> searchTitle = tdao.getSearchTitle(sql);

        // request객체에 부착
        request.setAttribute("searchTitle", searchTitle);
        request.setAttribute("search", search);
        request.setAttribute("url", url);

        RequestDispatcher dis = request.getRequestDispatcher("src/view/searchresult.jsp");
        dis.forward(request, response);
    }
}
