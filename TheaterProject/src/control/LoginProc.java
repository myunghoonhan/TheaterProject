package control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TheaterDao;

/**
 * Servlet implementation class LoginProc
 */
@WebServlet("/LoginProc.do") //완료
public class LoginProc extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		reqpro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		reqpro(request, response);
	}

	protected void reqpro(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String url = request.getParameter("url");
	
		TheaterDao tdao = TheaterDao.getInstance();
		int check = tdao.userCheck(id, pw); //디비접속, userCheck메소드 실행

		PrintWriter out = response.getWriter(); //JS쓰기 위해 선언
		String str = "";

		if (check == 1) { // ID가 맞으면

			HttpSession session = request.getSession(); //session객체 만들기
			session.setAttribute("id", id);
			response.sendRedirect(url); //받아온 url 위치로 다시 넘기기

		} else if (check == 0) { //비번오류 check=0

			str = "<script language='javascript'>";
			str += "alert('비밀번호가 일치하지 않습니다.');";
			str += "history.go(-1);"; 
			str += "</script>";
			out.print(str);

		} else { //아이디 오류 check=-1

			str = "<script language='javascript'>";
			str += "alert('해당하는 ID가 없습니다.');";
			str += "history.go(-1);";
			str += "</script>";
			out.print(str);

		}
	}

}
