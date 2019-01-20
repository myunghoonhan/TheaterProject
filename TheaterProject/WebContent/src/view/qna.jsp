<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
</head>
<body>

    <jsp:include page="./component/header.jsp">
        <jsp:param value="navbar-static-top" name="navbar_style" />
    </jsp:include>

    <main>
        <div class="container">
    
            <div class="row">
                <div class="col-xs-12" style="margin-bottom: 20px">
                    <img src="src/image/qna.jpg" class="img-responsive img-rounded" alt="Responsive image">
                </div>
            </div>
    
            <div class="row">
                <div class="col-xs-12" style="margin-bottom: 30px">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th width="100" style="text-align: center; vertical-align: middle;">글번호</th>
                                <th style="text-align: center; vertical-align: middle;">글제목</th>
                                <!-- <th></th> -->
                                <th width="100" style="text-align: center; vertical-align: middle;">작성자</th>
                                <th width="100" style="text-align: center; vertical-align: middle;">작성일</th>
                                <th width="100" style="text-align: center; vertical-align: middle;">조회수</th>
                            </tr>
                        </thead>
    
                        <tbody>
    
                            <c:set var="number" value="${number}" />
                            <c:forEach var="bean" items="${v}">
    
                                <tr>
                                    <td style="text-align: center; vertical-align: middle;">${number}</td>
                                    <td><c:if test="${bean.qstep>1 }">
                                            <c:forEach begin="1" end="${(bean.qstep-1)*5 }">&nbsp;
                                            </c:forEach>
                                        </c:if> <a href="QnAInfo.do?qno=${bean.qno}" style="text-decoration: none">${bean.qsubject}</a></td>
                                    <td style="text-align: center; vertical-align: middle;">${bean.qid}</td>
                                    <td style="text-align: center; vertical-align: middle;">${bean.qdate}</td>
                                    <td style="text-align: center; vertical-align: middle;">${bean.qcount}</td>
                                </tr>
    
                                <c:set var="number" value="${number-1 }" />
                            </c:forEach>
    
                        </tbody>
                    </table>
    
                </div>
                <!-- end Q&A리스트 col-xs-12  -->
            </div>
            <!-- end Q&A리스트 row  -->
            <hr>
    
            <c:if test="${sessionScope.id!=null}">
                <div class="row">
                    <div class="col-xs-12 col-sm-2" style="margin-bottom: 20px">
                        <a class="btn btn-danger" href="Question.do" role="button"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 질문하기</a>
                    </div>
                </div>
            </c:if>
    
            <center>
                <c:if test="${count>0 }">
                    <c:set var="pageCount" value="${count/pageSize+(count%pageSize==0 ? 0 : 1) }" />
                    <!-- 시작 페이지 숫자를 지정 -->
                    <c:set var="startPage" value="${1 }" />
                    <c:if test="${currentPage%10 != 0 }">
                        <fmt:parseNumber var="result" value="${currentPage/10 }" integerOnly="true" />
                        <c:set var="startPage" value="${result*10+1 }" />
                    </c:if>
                    <c:if test="${currentPage%10 == 0 }">
                        <c:set var="startPage" value="${(result-1)*10+1 }" />
                    </c:if>
    
                    <!-- 화면에 표시할 카운터링을 설정 [1][2]...[10] -->
                    <c:set var="pageBlock" value="${10 }" />
                    <c:set var="endPage" value="${startPage+pageBlock-1 }" />
    
                    <c:if test="${endPage>pageCount }">
                        <c:set var="endPage" value="${pageCount}" />
                    </c:if>
    
                    <!-- 이전 링크 파악 -->
                    <c:if test="${startPage>10 }">
                        <a href="qnalist.do?pageNum=${startPage-10 }" style="text-decoration: none">[이전]</a>
                    </c:if>
    
                    <!-- 현재 페이징 처리 -->
                    <c:forEach var="i" begin="${startPage }" end="${endPage }">
                        <a href="qnalist.do?pageNum=${i}" style="text-decoration: none">[${i}]</a>
                    </c:forEach>
    
                    <!-- 다음 링크 파악 -->
                    <c:if test="${endPage<pageCount }">
                        <a href="qnalist.do?pageNum=${startPage+10 }" style="text-decoration: none">[다음]</a>
                    </c:if>
                </c:if>
            </center>
            <!-- <nav style="text-align: center;">
    			  <ul class="pagination">
    			    <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
    	    		
    	    		<li class="active"><a href="#">1<span class="sr-only">(current)</span></a></li>
    			    <li><a href="#">2</a></li>
    			    <li><a href="#">3</a></li>
    			    <li><a href="#">4</a></li>
    			    <li><a href="#">5</a></li>
    			    <li><a href="#">6</a></li>
    			    <li><a href="#">7</a></li>
    			    <li><a href="#">8</a></li>
    			    <li><a href="#">9</a></li>
    			    <li><a href="#">10</a></li>
    			    
    			    <li><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
    			  </ul>
    	  	  </nav> -->
    
        </div>
        <!-- end container  -->
    </main>

    <jsp:include page="./component/footer.jsp"></jsp:include>
</body>
</html>
