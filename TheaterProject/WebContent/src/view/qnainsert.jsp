<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <script src="https://cloud.tinymce.com/stable/tinymce.min.js?apiKey=qiq1n2mn7n2znyaybwojmzwk4omk2q7on8cim9exffn3pfrx"></script>
    <script>
        tinymce.init({
        selector: '#contents'
        });
    </script>
</head>
<body>

    <jsp:include page="./component/header.jsp">
        <jsp:param value="navbar-static-top" name="navbar_style" />
    </jsp:include>

    <main>
    
        <script type="text/javascript">
            function send_finish() {
                document.writeform.submit();
            }
        </script>
        
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
    
                    <h1>Q&A-게시글 작성</h1>
                    <form action="QnAInsert.do" method="post" name="writeform">
                        <table class="table table-bordered">
                            <thead>
                            </thead>
                            <tbody>
                                <tr>
                                    <td width="80">제목</td>
                                    <td><input type="text" name="subject" class="form-control" placeholder="제목을 입력하세요."></td>
                                </tr>
                                <tr height="40">
                                    <td width="80">글 비밀번호</td>
                                    <td><input type="password" name="pass" class="form-control" placeholder="비밀번호를 입력하세요." style="width: 13em;"></td>
                                </tr>
                                <tr height="40">
                                    <td width="120" style="vertical-align: middle;">게시글</td>
                                    <td><textarea id="contents" class="form-control" name="contents" rows="7" style="resize: none"></textarea></td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
    
                </div>
            </div>
            <!-- end row  -->
    
            <div class="row">
                <center>
                    <button type="button" class="btn btn-danger btn-lg" onclick="send_finish()">작성완료</button>
                </center>
            </div>
    
        </div>
        <!-- end container  -->
    </main>

    <jsp:include page="./component/footer.jsp"></jsp:include>
</body>
</html>
