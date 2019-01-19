<%@page import="com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="src/css/style.css">
<script language="javascript" src="src/js/script.js"></script>
<script type="text/javascript"	src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
<script type="text/javascript">
$(function() {
	$(".modal").on("hidden.bs.modal", function(){
	    $(this).find('form')[0].reset();
	});
});

function idCheck(id){
	if(id == ""){
		alert("아이디를 입력하세요.");
	}else{
		url="IdCheck.do?id="+id;
		window.open(url, "post", "width=300, height=150, left=600 top=200");
	}
}
</script>

<%
	String port = String.valueOf(request.getServerPort());
	String url= request.getRequestURL().toString();
	
	if(url.equals("http://localhost:"+port+"/TheaterProject/src/view/index.jsp")){ //메인
		
		url = "http://localhost:"+port+"/TheaterProject/Main.do";
		
	}else if(url.equals("http://localhost:"+port+"/TheaterProject/src/view/searchtab.jsp")){ //탭 클릭시
		
		String gubun = request.getParameter("gubun");
		url ="http://localhost:"+port+"/TheaterProject/SearchTab.do?gubun="+gubun;
		
	}else if(url.equals("http://localhost:"+port+"/TheaterProject/src/view/searchtabloc.jsp")){ //지역별 클릭시
		
		String gubunLocation = request.getParameter("gubunLocation");
		url = "http://localhost:"+port+"/TheaterProject/SearchTabLoc.do?gubunLocation="+gubunLocation;
		
	}else if(url.equals("http://localhost:"+port+"/TheaterProject/src/view/ticketinfo.jsp")){ //상세페이지 클릭시
		
		String sno = request.getParameter("sno");
		url = "http://localhost:"+port+"/TheaterProject/TicketInfo.do?sno="+sno;
		
	}else{ //기본
		url = "http://localhost:"+port+"/TheaterProject/Main.do";
	}
%>

</head>
<body>

   <header>
   
    <nav id="header_navbar_default" class="navbar navbar-default ${param.navbar_style}" role="navigation">

      <div class="container-fluid">

        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="Main.do">
            <img class="img_index" alt="Brand" src="src/image/theaterlogo.png">
          </a>
        </div>
		
        <!-- Collect the nav links, forms, and other content for toggling -->
        
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav navbar-left">
            <li>
              <form class="navbar-form navbar-right" role="search" method="post" action="SearchTitle.do">
                <div class="form-group">
                  <input type="text" name="search" class="form-control" placeholder="The-Ticket을 입력하세요." size="40" required>
                </div>
                <button type="submit" class="btn btn-default">찾기</button>
              </form>
            </li>
          </ul>
			
          <ul class="nav navbar-nav navbar-right">
         
          <c:if test="${sessionScope.id != null }">
         	<li class="dropdown">
	                <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="button" aria-expanded="false">
	                  ${id} 님
	                  <span class="caret"></span>
	                </a>
	                <ul class="dropdown-menu" role="menu" aria-labelledby="drop3">
	                  <li role="presentation"><a role="menuitem" tabindex="-1" href="MyPage.do">마이페이지</a></li>
	                </ul>
            </li>
            <li role="presentation"><a role="menuitem" tabindex="-1" href="LoginOut.do?url=<%=url%>">로그아웃</a></li>
          </c:if>
         
          <c:if test="${sessionScope.id==null }">
         	 <li><a href="#" class="head_link_default" data-toggle="modal" data-target="#loginModal">로그인</a></li>
          	 <li><a href="#" class="head_link_default" data-toggle="modal" data-target="#registerModal">회원가입</a></li>
          </c:if>
              <li><a href="QnA.do" class="head_link_default">Q&A</a></li>
          </ul>
          
        </div><!-- /.navbar-collapse -->

      </div><!-- /.container-fluid -->
    </nav>
  </header>

   <!-- Modal Login-->
  <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">

      <form name="login" method="POST" action="LoginProc.do">
      	<input type="hidden" name="url" value="<%=url%>">
        <div class="form-group">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="myModalLabel">로그인</h4>
            </div>
            <div class="modal-body">
              <div class="row">
                <div class="col-sm-12" style="margin-bottom: 10px">
                  <input type="text" name="id" class="form-control" placeholder="아이디" style="height: 50px" required>
                </div>
                <div class="col-sm-12" style="margin-bottom: 10px">
                  <input type="password" name="pw" class="form-control" placeholder="비밀번호" style="height: 50px" required>
                </div>
                <div class="col-sm-12">
                  <button type="submit" class="btn btn-danger" style="height: 50px; width: 100%">로그인</button>
                </div>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-success" data-dismiss="modal" data-toggle="modal" data-target="#registerModal">회원가입</button>
            </div>
          </div>
        </div>
      </form>

    </div>
  </div>

  <!-- Modal Register-->
  <div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">

      <!-- <form name="regForm" method="POST" action="../controller/MemberInsert.jsp"> -->
      <form name="regForm" method="POST" action="MemberInsert.do">
        <div class="form-group">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="myModalLabel">회원가입</h4>
            </div>

            <div class="modal-body">

              <div class="row">
                <div class="col-xs-9">
                  <h5 style="margin: 5px 0px;">아이디</h5>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9" style="margin-bottom: 10px; padding-right: 3px;">
                  <input type="text" name="id" class="form-control" placeholder="" required>
                </div>
                <div class="col-xs-3" style="margin-bottom: 10px; padding-left: 0px;">
                  <button type="button" class="btn btn-success" onclick="idCheck(this.form.id.value)">중복확인</button>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9">
                  <h5 style="margin: 5px 0px;">비밀번호</h5>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-12" style="margin-bottom: 10px">
                  <input type="password" name="pw" class="form-control" placeholder="" required>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9">
                  <h5 style="margin: 5px 0px;">비밀번호 확인</h5>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-12" style="margin-bottom: 25px">
                  <input type="password" name="pwcheck" class="form-control" placeholder="" required>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9">
                  <h5 style="margin: 5px 0px;">이름</h5>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-12" style="margin-bottom: 10px">
                  <input type="text" name="name" class="form-control" placeholder="" required>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9">
                  <h5 style="margin: 5px 0px;">핸드폰번호</h5>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-12" style="margin-bottom: 10px">
                  <input type="text" name="phone" class="form-control" placeholder="'-'를 제외하고 입력하세요." required>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9">
                  <h5 style="margin: 5px 0px;">이메일</h5>
                </div>
              </div>

              <div class="row">
                <div class="col-xs-9" style="margin-bottom: 35px; padding-right: 3px;">
                  <input type="email" name="email" class="form-control" placeholder="example@example.com" required>
                </div>
                <div class="col-xs-3" style="margin-bottom: 10px; padding-left: 0px;">
                  <button type="button" class="btn btn-success" onclick="">인증</button>
                </div>
              </div>

              <div class="row">
                <div class="col-sm-12">
                  <button type="submit" class="btn btn-danger" onclick="" style="height: 50px; width: 100%">회원가입</button>
                </div>
              </div>

            </div>

            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </form>

    </div>
  </div>

</body>
</html>