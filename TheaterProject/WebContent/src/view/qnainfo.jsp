<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<% request.setCharacterEncoding("UTF-8"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title></title>

<link rel="stylesheet" href="src/css/style.css">
<!-- Bootstrap -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.2/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
	
	<jsp:include page="./component/header.jsp">
		<jsp:param value="navbar-static-top" name="navbar_style" />
	</jsp:include>

	<main>
		<c:if test="${msg ==2 }">
			<script type="text/javascript">
				alert("비밀번호가 틀렸습니다");
			</script>
		</c:if>
		<div class="container">
			<div class="row">
				<div class="col-xs-12" >
					<h1>Q&A-게시글</h1>
					<table class="table table-bordered">
						<thead>
						</thead>
						<tbody>
							<tr height="40">
								<td width="120">글번호</td>
								<td width="180">${bean.qno}</td>
								<td width="120">작성일</td>
								<td width="180">${bean.qdate}</td>
								<td width="120">조회수</td>
								<td width="180">${bean.qcount}</td>
							</tr>
							<tr height="40">
								<td width="120">글제목</td>
								<td width="180" colspan="3">${bean.qsubject}</td>
								<td width="120">작성자</td>
								<td width="180">${bean.qid}</td>
							</tr>
							<tr height="40">
								<td width="120" style="vertical-align: middle;">게시글</td>
								<td colspan="5"><div class="well well-lg" style="margin-bottom: 0px">${bean.qcontents}</div></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div><!-- end row  -->
			
			<div class="row">
				<center>
					<c:if test="${id!=null}">
						<button type="button" class="btn btn-danger" onclick="location.href='Pre_QnAReInsert.do?qgroup=${bean.qgroup}&qstep=${bean.qstep}&qlevel=${bean.qlevel}'">답글쓰기</button>
						<button type="button" class="btn btn-danger" data-toggle="modal" data-target=".qnaupdate-modal-sm">수정</button>
						<button type="button" class="btn btn-danger" data-toggle="modal" data-target=".qnadelete-modal-sm">삭제</button>
					</c:if>
					<button type="button" class="btn btn-danger" onclick="location.href='QnA.do'">전체글보기</button>
				</center>
			</div>
		</div><!-- end container  -->
		
		<!-- Modal 수정버튼-->
		  <div class="modal fade qnaupdate-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		    <div class="modal-dialog modal-sm">
		
		      <form name="login" method="POST" action="QnAUpdate.do">
		        <div class="form-group">
		          <div class="modal-content">
		            <div class="modal-header">
		              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		              <h4 class="modal-title" id="myModalLabel">게시글 수정하기</h4>
		            </div>
		            <div class="modal-body">
		              <div class="row">
		                <div class="col-sm-12" style="margin-bottom: 10px">
		                  <h4>비밀번호를 입력하세요.</h4>
		                </div>
		                <div class="col-sm-12" style="margin-bottom: 10px">
		                  <input type="password" name="pass" class="form-control" placeholder="비밀번호" style="height: 50px">
		                  <input type="hidden" name="qno" value="${bean.qno}">
		                </div>
		                <div class="col-sm-12">
		                  <button type="submit" class="btn btn-primary" style="height: 50px; width: 100%">입력</button>
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
		  
		  <!-- Modal 삭제버튼-->
		  <div class="modal fade qnadelete-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		    <div class="modal-dialog modal-sm">
		
		      <form name="login" method="POST" action="QnADelete.do">
		        <div class="form-group">
		          <div class="modal-content">
		            <div class="modal-header">
		              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		              <h4 class="modal-title" id="myModalLabel">게시글 삭제하기</h4>
		            </div>
		            <div class="modal-body">
		              <div class="row">
		                <div class="col-sm-12" style="margin-bottom: 10px">
		                  <h4>비밀번호를 입력하세요.</h4>
		                </div>
		                <div class="col-sm-12" style="margin-bottom: 10px">
		                  <input type="password" name="pass" class="form-control" placeholder="비밀번호" style="height: 50px">
		                  <input type="hidden" name="qno" value="${bean.qno}">
		                </div>
		                <div class="col-sm-12">
		                  <button type="submit" class="btn btn-primary" style="height: 50px; width: 100%">입력</button>
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
	
	</main>

	<jsp:include page="./component/footer.jsp"></jsp:include>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>
