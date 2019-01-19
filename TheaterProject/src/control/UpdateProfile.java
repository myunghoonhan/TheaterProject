package control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.TheaterDao;
import dto.TmemberBean;

/**
 * Servlet implementation class UpdateProfile
 */
@WebServlet("/UpdateProfile.do")
public class UpdateProfile extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		reqPro(request, response);
	}

	private void reqPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//String savePath =  request.getRealPath("src/image/profile_img").replaceAll("\\\\", "/");
		String savePath2 =  "C:/MHHan/JSP_workspace/git/TheaterProject/WebContent/src/image/profile_img";
		int maxSize = 10 * 1024 * 1024;
		MultipartRequest multi = new MultipartRequest(request, savePath2, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		TmemberBean bean = new TmemberBean();
		bean.setId(id);
		
		
		Enumeration params = multi.getParameterNames(); //파라미터 id,phone,email받아오기
		
		while (params.hasMoreElements()) {

			String name = (String) params.nextElement();
			String value = multi.getParameter(name);
			
			if (name.equals("newpass")) {
				bean.setPw(value);
			}
			if (name.equals("name")) {
				bean.setName(value);
			}
			if (name.equals("phone")) {
				bean.setPhone(value);
			}
		}

		Enumeration files = multi.getFileNames(); //파일 정보 받기
		
		if (files.hasMoreElements()) { // 이미지 파일이 있으면
			String name = (String) files.nextElement();
			String filename = multi.getFilesystemName(name);
			
			if(filename == null){
				bean.setProfile(null);
			}else{
				bean.setProfile(filename);
			}
			
			TheaterDao tdao = TheaterDao.getInstance();

			if (tdao.updateProfile(bean)) {
				response.sendRedirect("MyPage.do");
			} else {
				PrintWriter out = response.getWriter(); //JS쓰기 위해 선언
				String str = "";
				str = "<script language='javascript'>";
				str += "alert('프로필 수정 중 오류가 발생했습니다. 다시 시도해주세요.');";
				str += "history.back();"; 
				str += "</script>";
				out.print(str);
			}
		}
	}
}
