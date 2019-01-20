<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>

<c:if test="${TmemberBean.profile == null }">
	<c:set var="profileImg" value="profile_default.png" />
</c:if>
<c:if test="${TmemberBean.profile != null }">
	<c:set var="profileImg" value="${TmemberBean.profile}" />
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <script type="text/javascript">
        $(function() {
            $("#imgInp").on('change', function() { //input file이 실행되면 readURL 실행
                readURL(this);
            });
    
            $(function() {
                $('[data-toggle="tooltip"]').tooltip()
            });
    
            $('#tooltip').tooltip(options);
        });
    
        function readURL(input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    $('#blah').attr('src', e.target.result); //회원수정 img의 src를 타겟으로 바꿈
                }
                var fname = document.getElementById('imgInp').value; //input file의 value 가져옴
                var fext = fname.substr(fname.length - 3).toLowerCase(); //value의 끝자리 3자리가
                if (fext == 'jpg' || fext == 'png') { //jpg,png이면 input file의 값을 선택한걸로 바꿈
                    reader.readAsDataURL(input.files[0]);
                } else { //jpg 나 png 파일이 아니면 input file의 value를 null로
                    alert(".jpg 혹은 .png 파일이 아닙니다."); //알림창
                    document.getElementById('imgInp').value = ""; //input file의 value를 null로
                    $('#blah').attr('src', "src/image/profile_img/${profileImg}"); //회원수정 사진을 다시 getMem_image의 값으로
                }
            }
        }
    
        function checkModalNull(form) {
            if (form.newpass.value == "" || form.newpasscheck.value == "") {
                alert("새 비밀번호를 입력하십시오.");
            } else if (form.newpass.value != form.newpasscheck.value) {
                alert("비밀번호가 일치하지 않습니다.");
                form.newpasscheck.focus();
            } else if (form.name.value == "") {
                alert("수정 할 이름을 입력하십시오.");
                form.name.focus();
            } else if (form.phone.value == "") {
                alert("수정 할 핸드폰번호를 입력하십시오.");
                form.name.focus();
            } else {
                form.submit();
            }
    
        }
    </script>
</head>

<body>

    <jsp:include page="./component/header.jsp">
        <jsp:param value="navbar-static-top" name="navbar_style" />
    </jsp:include>

    <main id="main_mypage">

    <div class="container">

        <div class="jumbotron" style="margin-bottom: 50px">
            <div style="float: right; margin: 0 10px 0 0;">
                <a href=# id="tooltip" data-toggle="tooltip" data-placement="top" title="회원정보수정"><img id="settingBtn" src="src/image/setting.png" data-toggle="modal" data-target="#mypageModal" style="width: 23px; height: 23px;"></a>
            </div>
            <div class="row">
                <div class="col-xs-12 col-sm-4" style="margin-bottom: 10px">
                    <img src="src/image/profile_img/${profileImg}" class="img-responsive img-circle" alt="Responsive image" style="z-index: 1; margin: 0 auto; width: 300px; height: 300px; max-width: none;">
                </div>

                <div class="col-xs-12 col-sm-8">
                    <table style="margin: 0 auto;">
                        <tr>
                            <td class="list_title">이름</td>
                            <td style="font-size: 30px"><b>${TmemberBean.name}</b></td>
                        </tr>
                        <tr>
                            <td class="list_title">핸드폰번호</td>
                            <td style="font-size: 20px">${TmemberBean.phone}</td>
                        </tr>
                        <tr>
                            <td class="list_title">이메일</td>
                            <td style="font-size: 20px">${TmemberBean.email}</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <!-- end 나의 상세정보 점포트론  -->

        <div id="New_thumblist" style="margin-bottom: 50px">
            <h3>관심 The-Ticket</h3>
            <div class="row">
                <c:if test="${interestBean.isEmpty()}">
                    <div class="container">
                        <div class="alert alert-danger" role="alert" style="text-align: center">관심 The-Ticket이 없습니다.</div>
                    </div>
                </c:if>
                <c:forEach var="interestBean" items="${interestBean }">
                    <div class="col-xs-6 col-md-2">
                        <div class="thumbnail" style="padding: 0px">
                            <a href="TicketInfo.do?sno=${interestBean.sno}"><img src="src/image/poster/${interestBean.smainimg }" alt="..."></a>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- end 관심 더티켓 row  -->
        </div>
        <!-- end index_thumbnail -->

        <div class="row">
            <h3>예매내역</h3>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th width="80" style="text-align: center; vertical-align: middle;">예매번호</th>
                        <th width="100" style="text-align: center; vertical-align: middle;">예매정보</th>
                        <th></th>
                        <th width="50" style="text-align: center; vertical-align: middle;">수량</th>
                        <th width="100" style="text-align: center; vertical-align: middle;">총금액</th>
                        <th width="110"></th>
                    </tr>
                </thead>

                <tbody>

                    <c:forEach var="bookbean" items="${bookbean }">
                        <tr>
                            <td style="text-align: center; vertical-align: middle;">${bookbean.bno}</td>
                            <td><a href="TicketInfo.do?sno=${bookbean.bsno}"><img src="src/image/poster/${bookbean.smainimg}" class="img-responsive" alt="Responsive image"></a></td>
                            <td style="vertical-align: middle;">
                                <a href="TicketInfo.do?sno=${bookbean.bsno}" style="text-decoration: none">
                                    <font size="4">[${bookbean.slocation}] ${bookbean.sname}</font>
                                    <br>
                                    <c:set var="abc" value="${bookbean.bssdate}" /> 예매날짜:${fn:substring(abc,0,10)}&nbsp;${bookbean.stime}
                                </a>
                            </td>
                            <td style="text-align: center; vertical-align: middle;">${bookbean.bpeople}</td>
                            <td style="text-align: center; vertical-align: middle;"><c:set var="price" value="${bookbean.btotalprice }" /> <fmt:formatNumber type="currency" currencySymbol="" value="${price}" />원</td>
                            <td style="text-align: center; vertical-align: middle;">
                                <button type="button" class="btn btn-danger" style="margin-bottom: 5px;" onclick="location.href='BookDelete.do?sno=${bookbean.bsno}&bno=${bookbean.bno}&bookDate=${bookbean.bssdate}&people=${bookbean.bpeople}'">예매취소</button>
                                <button type="button" class="btn btn-success" onclick="location.href='TicketInfo.do?sno=${bookbean.bsno}&movedown=1'">관람후기</button>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>

            </table>
        </div>
        <!-- end 예매내역 row  -->

        <nav style="text-align: center;">
        <ul class="pagination">
            <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
            <li class="active"><a href="#">1<span class="sr-only">(current)</span></a></li>
            <!-- <li><a href="#">2</a></li>
    				    <li><a href="#">3</a></li>
    				    <li><a href="#">4</a></li>
    				    <li><a href="#">5</a></li>
    				    <li><a href="#">6</a></li>
    				    <li><a href="#">7</a></li>
    				    <li><a href="#">8</a></li>
    				    <li><a href="#">9</a></li>
    				    <li><a href="#">10</a></li> -->
            <li class="disabled"><a href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>
        </ul>
        </nav>

    </div>
    <!-- end container  --> </main>

    <!--Modal mypage  -->
    <div class="modal fade" id="mypageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">

            <form name="update_profile" method="POST" enctype="multipart/form-data" action="UpdateProfile.do">
                <div class="form-group">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="myModalLabel">프로필 수정</h4>
                        </div>
                        <div class="modal-body">

                            <div class="row">
                                <!-- file -->
                                <label for="imgInp" style="cursor: pointer;">
                                    <!--label클릭시 for속성에 의해 id=imgInp인 input file이 실행된다-->
                                    <img for="imgInp" src="src/image/camera.png" class="img-responsive" alt="Responsive image" style="z-index: 1; position: absolute; left: 50%; top: 50%; margin: -185px 0 0 -35px; width: 70px; height: 70px"></img>
                                    <input type="file" name="mem_image" id="imgInp" style="position: absolute; left: 50%; top: 50%; margin: -65px 0 0 -35px; width: 0px;"></iput>
                                </label>
                            </div>

                            <div class="row">
                                <div class="col-xs-12" style="margin-bottom: 10px;">
                                    <!-- 회원수정 프로필 사진 -->
                                    <img id="blah" src="src/image/profile_img/${profileImg}" class="img-responsive img-circle" alt="Responsive image" style="margin: 0 auto; width: 300px; height: 300px;"></img>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12" style="margin-bottom: 10px">
                                    <small>새 비밀번호</small> <input type="password" name="newpass" class="form-control" placeholder="새 비밀번호 입력" value="" required></input>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12" style="margin-bottom: 10px">
                                    <small>새 비밀번호 확인</small> <input type="password" name="newpasscheck" class="form-control" placeholder="새 비밀번호 입력" value="" required></input>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12" style="margin-bottom: 10px">
                                    <small>이름</small> <input type="text" name="name" class="form-control" placeholder="이름 입력" value="${TmemberBean.name}" required></input>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12" style="margin-bottom: 10px">
                                    <small>핸드폰번호</small> <input type="text" name="phone" class="form-control" placeholder="핸드폰번호 입력" value=${TmemberBean.phone } required></input>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-xs-12" style="margin-bottom: 10px">
                                    <small>이메일</small> <input type="text" name="email" class="form-control" placeholder="이메일 입력" value=${TmemberBean.email } disabled="disabled"></input>
                                </div>
                            </div>

                        </div>

                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-danger" onclick="checkModalNull(this.form)">수정</button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <!-- end Modal mypage  -->

    <jsp:include page="./component/footer.jsp"></jsp:include>
</body>
</html>