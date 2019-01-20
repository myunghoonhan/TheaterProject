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
import dto.ReviewBean;
import dto.ShowBean;
import dto.ShowImgBean;
import dto.ShowSeatBean;

/**
 * Servlet implementation class TicketInfo
 */
@WebServlet("/TicketInfo.do")
public class TicketInfo extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        reqPro(request, response);
    }

    private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // 공연번호 읽어오기
        String sno = request.getParameter("sno");

        // 좋아요 on/off 여부 불러오기
        String selectLike = request.getParameter("selectLike");

        // 세션에 아이디 저장 여부 확인
        HttpSession session = request.getSession();
        String id = (String) session.getAttribute("id");

        // 데이터 베이스 객체 생성후
        TheaterDao tdao = new TheaterDao();

        // 해당 공연에 대한 정보를 얻어오는 메소드 호출 getTicketInfo(sno)
        ShowBean bean = tdao.getTicketInfo(sno);

        String like_no = null;
        String like_yes = null;

        if (selectLike == null) {
            // 회원정보 내 좋아요 클릭 여부를 확인하기 위한 메소드 호출 getLike(sno, id);
            int like_first = tdao.getLike(sno, id);

            if (like_first == 0) {
                like_no = "";
                like_yes = "none";
            } else if (like_first == 1) {
                like_no = "none";
                like_yes = "";
            }
        } else {
            if (selectLike.equals("1")) {
                tdao.insertLike(id, sno);
                like_no = "none";
                like_yes = "";

            } else if (selectLike.equals("2")) {
                tdao.deleteLike(id, sno);
                like_no = "";
                like_yes = "none";
            }
        }

        // The-Ticket 정보를 불러오는 메소드 호출 getTicketDetail(sno)
        Vector<ShowImgBean> imgbean = tdao.getTicketDetail(sno);

        String contents = request.getParameter("contents");

        if (contents == null) {

        } else {
            tdao.insertReview(id, sno, contents);
        }

        // 해당 공연의 후기를 모두 불러오는 메소드 호출 getRieviewAll(sno, id)
        Vector<ReviewBean> reviewAll = tdao.getReviewAll(sno, id);

        // 해당 공연의 후기를 작성했는지 여부를 파악하는 메소드 호출
        int writereview = tdao.getWriteReview(id, sno);

        // 해당 공연의 예매정보를 불러오는 메소드 호출 getBookInfo(sno)
        Vector<ShowSeatBean> bookinfo = tdao.getBookInfo(sno);
        String bookinfo_time = tdao.getBookInfo_time(sno);

        if (request.getParameter("movedown") != null) {
            int movedown = Integer.parseInt(request.getParameter("movedown"));
            request.setAttribute("movedown", movedown);
        }

        // request객체에 부착
        request.setAttribute("bean", bean);
        request.setAttribute("like_no", like_no);
        request.setAttribute("like_yes", like_yes);
        request.setAttribute("id", id);
        request.setAttribute("imgbean", imgbean);
        request.setAttribute("reviewAll", reviewAll);
        request.setAttribute("writereview", writereview);
        request.setAttribute("bookinfo", bookinfo);
        request.setAttribute("bookinfo_time", bookinfo_time);

        RequestDispatcher dis = request.getRequestDispatcher("src/view/ticketinfo.jsp");
        dis.forward(request, response);
    }
}
