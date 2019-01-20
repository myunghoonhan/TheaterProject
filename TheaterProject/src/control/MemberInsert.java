package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TheaterDao;
import dto.TmemberBean;

/**
 * Servlet implementation class MemberInsert
 */
@WebServlet("/MemberInsert.do") // 완료
public class MemberInsert extends HttpServlet {
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
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");

        TmemberBean bean = new TmemberBean();
        bean.setId(id);
        bean.setPw(pw);
        bean.setName(name);
        bean.setPhone(phone);
        bean.setEmail(email);

        TheaterDao tdao = TheaterDao.getInstance();

        PrintWriter out = response.getWriter(); // JS쓰기 위해 선언
        String str = "";

        if (tdao.insertMember(bean)) {

            str = "<script language='javascript'>";
            str += "alert('회원가입이 완료되었습니다. 정상 로그인이 가능합니다.');";
            str += "history.go(-1);";
            str += "</script>";
            out.print(str);

        } else {

            str = "<script language='javascript'>";
            str += "alert('회원가입 중 오류가 발생했습니다. 다시 시도해주세요.');";
            str += "history.go(-1);";
            str += "</script>";
            out.print(str);
        }
    }
}
