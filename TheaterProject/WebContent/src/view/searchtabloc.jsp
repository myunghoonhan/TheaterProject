<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body id="index_body">

    <jsp:include page="./component/header.jsp">
        <jsp:param value="navbar-fixed-top" name="navbar_style" />
    </jsp:include>

    <main>
        <figure>
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="background-color: #e5e5e5">
                <!-- Indicators -->
                <ol class="carousel-indicators">
                    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                </ol>
        
                <!-- Wrapper for slides -->
                <div class="carousel-inner" role="listbox">
        
                    <div class="item active">
                        <img src="src/image/main1.jpg" class="carousel_img" alt="..." style="margin: 0 auto;">
                        <div class="carousel-caption"></div>
                    </div>
        
                    <div class="item">
                        <img src="src/image/main2.jpg" class="carousel_img" alt="..." style="margin: 0 auto;">
                        <div class="carousel-caption"></div>
                    </div>
        
                    <div class="item">
                        <img src="src/image/main3.jpg" class="carousel_img" alt="..." style="margin: 0 auto;">
                        <div class="carousel-caption"></div>
                    </div>
        
                </div>
        
                <!-- Controls -->
                <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev"> <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span></a>
                <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next"> <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span></a>
            </div>
        </figure>

    <div class="container">

        <ul id="index_nav_tabs" class="nav nav-tabs nav-justified" style="margin: 45px 0px 55px 0px;">
            <li role="presentation"><a href="Main.do">전체</a></li>
            <li role="presentation" class="${active_1}"><a href="SearchTab.do?gubun=1">콘서트</a></li>
            <li role="presentation" class="${active_2}"><a href="SearchTab.do?gubun=2">연극</a></li>
            <li role="presentation" class="${active_3}"><a href="SearchTab.do?gubun=3">전시회</a></li>
            <li role="presentation" class="${active_4}"><a href="SearchTab.do?gubun=4">The-Ticket</a></li>
            <li role="presentation" class="dropdown ${active_5}">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-expanded="false"> 지역별 <span class="caret"></span></a>
                <ul id="nav_tabs_dropdown_menu" class="dropdown-menu" role="menu" style="width: 100%;">
                    <li><a href="SearchTabLoc.do?gubunLocation=0">대학로</a></li>
                    <li><a href="SearchTabLoc.do?gubunLocation=1">홍대</a></li>
                    <li><a href="SearchTabLoc.do?gubunLocation=2">강남</a></li>
                    <li class="divider"></li>
                    <li><a href="SearchTabLoc.do?gubunLocation=3">기타</a></li>
                </ul>
            </li>
        </ul>

        <div id="Top4_thumblist">
            <h3>${gubunLocation} 인기 TOP4<img src="src/image/hot2.gif"></h3>

            <div class="row">

                <c:forEach var="bean" items="${searchTab_top4_loc}">
                    <div class="col-xs-6 col-sm-3">
                        <div class="thumbnail" style="padding: 0px">
                            <a href="TicketInfo.do?sno=${bean.sno}"><img src="src/image/poster/${bean.smainimg }" alt="..."></a>
                            <div class="caption">
                                <h4>[${bean.slocation}] ${bean.sname}</h4>
                                <span class="label label-danger" style="font-size: 15px; vertical-align: middle">♥ ${bean.slike}</span>
                                <span style="font-size: 25px; font-weight: 900; color: #d9534f; vertical-align: middle;">
                                    <c:set var="price" value="${bean.sprice }" />
                                    <fmt:formatNumber type="currency" currencySymbol="" value="${price}" />
                                </span>
                                <span style="font-size: 20px; font-weight: 900; color: #d9534f; vertical-align: middle;"> 원 </span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                
            </div>
        </div>
        
        <div id="New_thumblist">
            <h3>${gubunLocation} 전체 The-Ticket <span class="badge">${searchTab_count_loc}</span></h3>

            <div class="row">

                <c:set var="count" value="${0}" />
                <c:forEach var="bean" items="${searchTab_new_loc}">
                    <c:if test="${count%6==0}">
                        <div class="clearfix"></div>
                    </c:if>

                    <div class="col-xs-12 col-sm-2">
                        <div class="thumbnail" style="padding: 0px">
                            <a href="TicketInfo.do?sno=${bean.sno}"><img src="src/image/poster/${bean.smainimg }" alt="..."></a>
                            <div class="caption">
                                <h4>[${bean.slocation}] ${bean.sname}</h4>
                                <span class="label label-danger" style="font-size: 12px; vertical-align: middle">♥ ${bean.slike}</span>
                                <span style="font-size: 20px; font-weight: 900; color: #d9534f; vertical-align: middle;">
                                    <c:set var="price" value="${bean.sprice }" />
                                    <fmt:formatNumber type="currency" currencySymbol="" value="${price}" />
                                </span>
                                <span style="font-size: 15px; font-weight: 900; color: #d9534f; vertical-align: middle;"> 원 </span>
                            </div>
                        </div>
                    </div>
                    
                    <c:set var="count" value="${count+1 }" />
                </c:forEach>
                
            </div>
            <!-- end div id="row" -->
        </div>
        <!-- end index_thumbnail -->
        
        <nav style="text-align: center; margin-top: 10px;">
            <ul class="pagination pagination-lg">
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
    <!-- end container --> </main>
    
    <jsp:include page="./component/footer.jsp"></jsp:include>
</body>
</html>
