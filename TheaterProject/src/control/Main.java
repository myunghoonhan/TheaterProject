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
 * Servlet implementation class Main
 */
@WebServlet("/Main.do")
public class Main extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 데이터 베이스 객체 생성
        TheaterDao tdao = TheaterDao.getInstance();

        // The-Ticket TOP4를 얻어오는 메소드 호출
        Vector<ShowBean> top4 = tdao.getTop4();

        // 최신 The-Ticket을 얻어오는 메소드 호출
        Vector<ShowBean> newTicket = tdao.getNewTicket();

        // request객체에 부착
        request.setAttribute("top4", top4);
        request.setAttribute("newTicket", newTicket);

        RequestDispatcher dis = request.getRequestDispatcher("src/view/index.jsp");
        dis.forward(request, response);
    }

}
