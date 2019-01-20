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
import dto.ShowBean;
import dto.ShowImgBean;

@WebServlet("/Like.do")
public class Like extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 한글처리
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 공연번호 읽어오기
        String sno = request.getParameter("sno");

        // 좋아요 버튼 클릭 구분
        String selectLike = request.getParameter("selectLike");

        // 세션에 아이디 저장 여부 확인
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");

        // 데이터 베이스 객체 생성후
        TheaterDao tdao = new TheaterDao();

        // 해당 공연에 대한 정보를 얻어오는 메소드 호출 getTicketInfo(sno)
        ShowBean bean = tdao.getTicketInfo(sno);

        // The-Ticket 정보를 불러오는 메소드 호출 getTicketDetail(sno)
        Vector<ShowImgBean> imgbean = tdao.getTicketDetail(sno);

        String like_no = null;
        String like_yes = null;

        if (selectLike.equals("1")) {
            tdao.insertLike(id, sno);
            like_no = "none";
            like_yes = "";

        } else if (selectLike.equals("2")) {
            tdao.deleteLike(id, sno);
            like_no = "";
            like_yes = "none";
        }

        // request객체에 부착
        request.setAttribute("bean", bean);
        request.setAttribute("like_no", like_no);
        request.setAttribute("like_yes", like_yes);
        request.setAttribute("id", id);
        request.setAttribute("imgbean", imgbean);

        RequestDispatcher dis = request.getRequestDispatcher("src/view/ticketinfo.jsp");
        dis.forward(request, response);
    }

}
