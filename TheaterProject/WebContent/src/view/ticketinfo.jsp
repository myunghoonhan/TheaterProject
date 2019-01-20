<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
</head>
<body>

  <jsp:include page="./component/header.jsp">
    <jsp:param value="navbar-static-top" name="navbar_style"/>
  </jsp:include>
  
  <main>
    <script type="text/javascript">
        function idCheck1(form) {
            if(${id == null}){
                alert('로그인 후 이용가능합니다.');
            }else{
                form.submit();
            }
        }
        
        function clean(a){
            a.value="";
        }
        
        function idCheck_review(form) {
             if(${id == null}){
                alert('로그인 후 이용가능합니다.');
             }else{
                if(${writereview == 1}){
                   alert('이미 후기를 작성했습니다.');
                }else{
                   form.submit();
                }
             }
        }
        
        function idCheck_book(form){
            if(form.bookDate.value == "selectDate"){
                alert('예매일자를 선택하십시오.');
            }else if(form.people.value <= 0 || form.people.value == null){
                alert('예매인원수는 1 이상이어야 합니다.');
            }else{
                form.submit();
            }
        }
        
       function fn_MaxNum() {
              
              var f = document.frm2;
              var datas = f.bookDate.value;
              
              var d1 = datas.split(":");
              var len = d1[1].length;
              var d2 = d1[1].substring(0,len-1)
              f.people.max = d2;
              //alert(f.people.max);
              
       }
           
    </script>
    <div id="detail_main" class="container" style="margin-top: 40px">
      <div class="row">

        <div class="col-md-3 col-md-push-9"> <!--오룬쪽  -->
          <div class="panel panel-default">
            <div class="panel-body">
            
              <form name="frm2" class="form-horizontal" action="Book.do" method="POST"> 
              
                <h4><strong>예매일자</strong></h4>
                <div class="form-group" style="margin-bottom: 30px;">
                
                  <input type="hidden" name="sno" value="${bean.sno}">
                
                  <div class="col-xs-12">
                    <select class="form-control" name="bookDate"  onChange="fn_MaxNum()">
                      <option value="selectDate">예매일자를 선택하세요.</option>
                      <c:forEach var="bookinfo" items="${bookinfo}">
                        <option>${bookinfo.ssdate} (잔여석:${bookinfo.ssseat})</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                
                <h4><strong>The-Ticket 시간</strong></h4>
                <div style="margin-bottom: 30px;">
                    ${bookinfo_time}
                </div>
                
                <h4><strong>인원수</strong></h4>
                <div class="form-group" style="margin-bottom: 30px;">
                    <div class="col-xs-12">
                        <input type="number" name="people" class="form-control" placeholder="0" min="0" max="" required style="font-size: 20px;">
                    </div>
                </div>

                <div class="form-group">
                  <div class="col-xs-12">
                    <button type="button" class="btn btn-danger" style="width: 100%; height: 50px; font-size: 20px; font-weight: bold" onclick="idCheck_book(this.form)" >예매하기</button>
                  </div>
                </div>

              </form>
              
            </div>
          </div>
        </div><!--end 오룬쪽  -->

        <div class="col-md-9 col-md-pull-3"><!-- 왼쪽  -->

          <div class="row"  style="margin-bottom: 10px; border-style: solid; border-width: 1px; border-radius: 4px; border-color: #ddd; padding: 15px;">
            <div class="col-xs-12 col-md-7">
              <img src="src/image/poster/${bean.smainimg}" class="img-responsive img-rounded" alt="Responsive image" style="width: 100%;">
            </div>

            <div class="col-xs-12 col-md-5">
              <ul>
                <li style="list-style: none; margin-bottom: 30px;">
                  <h3><strong>The-Ticket</strong></h3>${bean.sname}
                </li>
                <li style="list-style: none; margin-bottom: 30px;">
                  <h3><strong>장소</strong></h3>${bean.saddress}
                </li>
                <li style="list-style: none; margin-bottom: 30px;">
                  <h3><strong>기간</strong></h3>${bean.speriod}
                </li>
                <li style="list-style: none; margin-bottom: 30px;">
                  <h3><strong>출연</strong></h3>${bean.sactor}
                </li>
                <li style="list-style: none; margin-bottom: 30px; font-size: 35px; font-weight: 900; color: #d9534f;">
                    <c:set var="price" value="${bean.sprice}" />
                    <fmt:formatNumber type="currency" currencySymbol="" value="${price}" />원
                </li>
                
                <li style="list-style: none; margin-bottom: 30px;">
                    <form name="like_no" method="post" action="TicketInfo.do?sno=${bean.sno}">
                        <input type="hidden" name="selectLike" value="1">
                        <button type="button" id="no" style="display:${like_no}" onclick="idCheck1(this.form)" class="btn btn-default btn-lg"><span class="glyphicon glyphicon-heart-empty" aria-hidden="true"></span>&nbsp;LIKE</button>
                    </form>
                    <form name="like_yes" method="post" action="TicketInfo.do?sno=${bean.sno}">
                        <input type="hidden" name="selectLike" value="2">
                        <button type="button" id="yes" style="display:${like_yes}" onclick="idCheck1(this.form)" class="btn btn-danger btn-lg active"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span>&nbsp;LIKE</button>
                    </form>
                </li>
                
              </ul>

            </div>
          </div>

          <div class="row">
            <div class="col-xs-12"> <!--style="margin-bottom: 20px;"-->
              <h3><strong>The-Ticket 정보</strong></h3>
              <c:forEach var="imgbean" items="${imgbean}">
                <img src="src/image/poster/${imgbean.ssubimg}" class="img-responsive" alt="Responsive image" width="100%">
              </c:forEach>
            </div>
          </div>
          
          <div class="row">
            <div class="col-xs-12" style="margin-bottom: 20px;">
              <jsp:include page="./component/map.jsp"/>
            </div>
          </div>
          
          <c:if test="${movedown == 1}">
            <script type="text/javascript">
                $(function() {
                    $('html,body').animate({scrollTop:'10000'},1100);
                    document.reviewForm.contents.focus();
                });
            </script>
          </c:if> 
          
          <div class="row"> <!-- 후기 작성폼  -->
            <div class="col-xs-12">
                <h3><strong>후기</strong></h3>
            </div>
          </div>
          
          <div class="row">
            <form name="reviewForm" method="post" action="TicketInfo.do?sno=${bean.sno}">
                <div class="form-group">
                    <div class="col-xs-10" style="padding-right: 0px">
                      <textarea class="form-control" name="contents" rows="3" style="resize: none" onfocus="clean(this)">후기를 남겨주세요.</textarea>
                    </div>
                    <div class="col-xs-2">
                      <button type="button" class="btn btn-lg btn-success " style="width: 100%; height: 70px;" onclick="idCheck_review(this.form)">작성하기</button>
                    </div>
                </div>
            </form>
          </div>
          
        <div class="row"><!-- 후기 리스트  -->
        <div class="col-xs-12">
            <table class="table table-hover">
                <thead>
                    <tr>
                      <th width="100"></th>
                      <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reviewAll" items="${reviewAll }">
                        <tr>
                            <td>
                                <c:if test="${reviewAll.profile == null}">
                                    <img src="src/image/profile_img/profile_default.png" class="img-responsive img-circle" alt="Responsive image" style="margin: 0 auto; width: 100px; height: 100px; max-width: none;">
                                </c:if>
                                <c:if test="${reviewAll.profile != null}">
                                    <img src="src/image/profile_img/${reviewAll.profile}" class="img-responsive img-circle" alt="Responsive image" style="margin: 0 auto; width: 100px; height: 100px; max-width: none;">
                                </c:if>
                            </td>
                            <td style="vertical-align:middle;">
                                <div style="padding-left: 20px;">
                                    ${reviewAll.name}님(ID: ${reviewAll.rid })&nbsp;&nbsp;작성일: ${reviewAll.rdate}<br>${reviewAll.rcontents}
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                 </tbody>
            </table>
            </div>
        </div>
          
          <nav style="text-align: center;">
              <ul class="pagination">
                <li class="disabled"><a href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                
                <li class="active"><a href="#">1<span class="sr-only">(current)</span></a></li>
              <!--   <li><a href="#">2</a></li>
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
          
        </div> <!-- end div(class="col-md-9 col-md-pull-3") 왼쪽창  -->
      </div> <!-- end div(class="row") 전체행  -->
    </div> <!-- end container  -->
  </main>

  <jsp:include page="./component/footer.jsp"></jsp:include>
</body>
</html>
