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
import dto.QnABean;

/**
 * Servlet implementation class QnA
 */
@WebServlet("/QnA.do")
public class QnA extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String pageNum = request.getParameter("pageNum");  //  [1][2][3],,,,���° �������� �ľ�
		if(pageNum== null){
			pageNum = "1"; //�ʱⰪ���� 1�� �ο�. ��, �ֽű��� �������� ����
		}
		int pageSize=10;
		int count =0;
		int number=0;
		int currentPage = Integer.parseInt(pageNum);
		
		TheaterDao tdao = new TheaterDao();
		count = tdao.getAllcount();
		
		int start = (currentPage-1)*pageSize+1;
		int end = currentPage*pageSize;
		
		Vector<QnABean> v = tdao.getAllBoard(start,end);
		number = count-(currentPage-1)*pageSize;
		
		HttpSession session = request.getSession();
		String qid = (String)session.getAttribute("id");
		
		QnABean qnabean = new QnABean();
		qnabean.setQid(qid);
		
		request.setAttribute("v", v);
		request.setAttribute("number", number);
		request.setAttribute("check", request.getParameter("check"));
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("count", count);
	
		RequestDispatcher dis = request.getRequestDispatcher("src/view/qna.jsp");
		dis.forward(request, response);
	}


}
