<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% request.setCharacterEncoding("UTF-8"); %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>TheaterProject</title>

  <link rel="stylesheet" href="src/css/style.css">
  <!-- Bootstrap -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
  <!-- HTML5 Shim and Respond.js IE8 support 
  of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.2/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body id="index_body">

  <jsp:include page="./component/header.jsp">
  	<jsp:param value="navbar-fixed-top" name="navbar_style"/>
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
            <div class="carousel-caption">
            </div>
          </div>

          <div class="item">
            <img src="src/image/main2.jpg" class="carousel_img" alt="..." style="margin: 0 auto;">
            <div class="carousel-caption">
            </div>
          </div>

          <div class="item">
            <img src="src/image/main3.jpg" class="carousel_img" alt="..." style="margin: 0 auto;">
            <div class="carousel-caption">
            </div>
          </div>

        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
          <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
          <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
          <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
          <span class="sr-only">Next</span>
        </a>
      </div>
    </figure>

    <div class="container">
    
      <ul id="index_nav_tabs" class="nav nav-tabs nav-justified" style="margin: 45px 0px 55px 0px;">
      	  <li role="presentation" class="active"><a href="Main.do">전체</a></li>
		  <li role="presentation" ><a href="SearchTab.do?gubun=1">콘서트</a></li>
		  <li role="presentation"><a href="SearchTab.do?gubun=2">연극</a></li>
		  <li role="presentation"><a href="SearchTab.do?gubun=3">전시회</a></li>
		  <li role="presentation"><a href="SearchTab.do?gubun=4">The-Ticket</a></li>
		  <li role="presentation" class="dropdown">
		    <a class="dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-expanded="false">지역별 <span class="caret"></span></a>
	    	<ul id="nav_tabs_dropdown_menu" class="dropdown-menu" role="menu" style="width: 100%;">
		    	<li><a href="SearchTabLoc.do?gubunLocation=0">대학로</a></li>
			    <li><a href="SearchTabLoc.do?gubunLocation=1">홍대</a></li>
			    <li><a href="SearchTabLoc.do?gubunLocation=2">강남</a></li>
			    <li class="divider"></li>
			    <li><a href="SearchTabLoc.do?gubunLocation=3">기타</a></li>
		    </ul>
  		 </li>
	  </ul>
	<!-- 메인 탑4  -->
      <div id="index_thumbnail">
        <h3>TOP4<img src="src/image/hot2.gif"></h3>

        <div class="row"><!-- table  -->
          
			<c:forEach var="bean" items="${top4}">
	          <div class="col-xs-6 col-sm-3">
	              <div class="thumbnail" style="padding: 0px">
	              <a href="TicketInfo.do?sno=${bean.sno}"><img src="src/image/poster/${bean.smainimg }" alt="..."></a>
	                <div class="caption">
	                  <h4>[${bean.slocation}] ${bean.sname}</h4>
					  <span class="label label-danger" style="font-size: 15px; vertical-align:middle">♥ ${bean.slike}</span>
	            	  <span style="font-size: 25px; font-weight: 900; color: #d9534f; vertical-align:middle;">
	            	  <c:set var="price" value="${bean.sprice }" />
					  <fmt:formatNumber type="currency" currencySymbol="" value="${price}" /></span>
					  <span style="font-size: 20px; font-weight: 900; color: #d9534f; vertical-align:middle;">
					  원
					  </span>
	                </div>
	              </div>
	          </div>
			</c:forEach>
          
        </div><!-- end div row table  -->
      </div> <!-- div id="index_thumbnail" -->
      
	<!-- 메인 최신 더티켓  -->
      <div id="index_thumbnail">
        <h3>최신 The-Ticket</h3>

        <div class="row">
			<c:set var="count" value="${0 }" />
			<c:forEach var="bean" items="${newTicket}">
				<c:if test="${count%4==0 }">
					<div class="clearfix"></div>
				</c:if>
	
				<div class="col-xs-6 col-sm-3">
	              <div class="thumbnail" style="padding: 0px">
	              <a href="TicketInfo.do?sno=${bean.sno}"><img src="src/image/poster/${bean.smainimg }" alt="..."></a>
	                <div class="caption">
	                  <h4>[${bean.slocation}] ${bean.sname}</h4>
	            	  <span class="label label-danger" style="font-size: 15px; vertical-align:middle">♥ ${bean.slike}</span>
	            	  <span style="font-size: 25px; font-weight: 900; color: #d9534f; vertical-align:middle;">
	            	  <c:set var="price" value="${bean.sprice }" />
					  <fmt:formatNumber type="currency" currencySymbol="" value="${price}" /></span>
					  <span style="font-size: 20px; font-weight: 900; color: #d9534f; vertical-align:middle;">
					  원
					  </span>
	                </div>
	              </div>
	            </div>
				
				<c:set var="count" value="${count+1 }" />
			</c:forEach>
          
        </div>  <!-- 최신더티켓 "row" -->
      </div> <!-- 최신더티켓 index_thumbnail -->

    </div> <!-- end class="container" -->
  </main>

  <jsp:include page="./component/footer.jsp"></jsp:include>

  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
 
</body>
</html>
