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
    
                    <h1>Q&A- 답글 작성하기</h1>
                    <form action="QnAReInsert.do" method="post" name="writeform">
                        <table class="table table-bordered">
                            <thead>
                            </thead>
                            <tbody>
                                <tr>
                                    <td width="80">제목</td>
                                    <td><input type="text" name="subject" class="form-control" placeholder="[답글]제목을 입력하세요." value="[답글] "></td>
                                </tr>
                                <tr height="40">
                                    <td width="80">글 비밀번호</td>
                                    <td><input type="password" name="pass" class="form-control" placeholder="비밀번호를 입력하세요." style="width: 13em;"></td>
                                </tr>
                                <tr height="40">
                                    <td width="120" style="vertical-align: middle;">답글내용</td>
                                    <td><textarea id="contents" class="form-control" rows="7" style="resize: none" name="contents"></textarea> <input type="hidden" name="qgroup" value="${qgroup}"> <input type="hidden" name="qstep" value="${qstep}"> <input type="hidden" name="qlevel"
                                        value="${qlevel}"></td>
                                </tr>
                            </tbody>
                        </table>
                    </form>
    
                </div>
            </div>
            <!-- end row  -->
    
            <div class="row">
                <center>
                    <button type="button" class="btn btn-danger btn-lg" onclick="send_finish()">작성하기</button>
                </center>
            </div>
    
        </div>
        <!-- end container  -->
    </main>

    <jsp:include page="./component/footer.jsp"></jsp:include>
</body>
</html>
