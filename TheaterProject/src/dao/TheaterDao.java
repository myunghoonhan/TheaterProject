package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import dto.BookBean;
import dto.QnABean;
import dto.ReviewBean;
import dto.ShowBean;
import dto.ShowImgBean;
import dto.ShowSeatBean;
import dto.TmemberBean;

public class TheaterDao {
    
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String url         = "jdbc:mysql://localhost:3306/theater";
    
    static String       USERNAME    = "root";
    static String       PASSWORD    = "root";
    
    Connection          conn        = null;
    PreparedStatement   pstmt       = null;
    ResultSet           rs          = null;
    
    private static TheaterDao instance = new TheaterDao();
    
    public static TheaterDao getInstance() {
        return instance;
    }
    
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

    public void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int userCheck(String id, String pw) { // 로그인시 (LoginProc.java) 아이디 및 비번 체크 (완료)
        connect();
        String dbpasswd = null;
        int x = -1;

        try {
            String sql = 
                      " SELECT                                "
                    + "      pw                               "
                    + " FROM                                  "
                    + "      TMEMBER                          "
                    + " WHERE 1=1                             "
                    + " AND id = ?                            ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dbpasswd = rs.getString("pw");
                if (dbpasswd.equals(pw)) {
                    x = 1;
                } else {
                    x = 0;
                }
            } else {
                x = -1;
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            disconnect();
        }
        return x;
    }

    public int getCheckId(String id) { // 회원가입시 (IdCheck.java) 아이디 중복체크 (완료)
        connect();
        int count = 0;
        try {
            String sql = 
                      " SELECT          "
                    + "     COUNT(*)    "
                    + " FROM            "
                    + "     TMEMBER     "
                    + " WHERE 1=1       "
                    + " AND id = ?      ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean insertMember(TmemberBean bean) { // 회원가입에서 insert (완료)
        connect();

        try {
            String sql = 
                      " INSERT INTO TMEMBER (            "
                    + "       id                         "
                    + "     , pw                         "
                    + "     , name                       "
                    + "     , phone                      "
                    + "     , email                      "
                    + " ) VALUES (                       "
                    + "       ?                          "
                    + "     , ?                          "
                    + "     , ?                          "
                    + "     , ?                          "
                    + "     , ?                          "
                    + ")                                 ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bean.getId());
            pstmt.setString(2, bean.getPw());
            pstmt.setString(3, bean.getName());
            pstmt.setString(4, bean.getPhone());
            pstmt.setString(5, bean.getEmail());

            pstmt.executeUpdate();
        } catch (Exception e) {

            System.out.println(e);
            return false;

        } finally {
            disconnect();
        }
        return true;
    }

    public TmemberBean selectMember(String id) { // 완료
        connect();
        TmemberBean bean = new TmemberBean();

        try {
            String sql = 
                      " SELECT          "
                    + "     *           "
                    + " FROM            "
                    + "     TMEMBER     "
                    + " WHERE 1=1       "
                    + " AND id = ?      ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean.setId(rs.getString("id"));
                bean.setPw(rs.getString("pw"));
                bean.setName(rs.getString("name"));
                bean.setPhone(rs.getString("phone"));
                bean.setEmail(rs.getString("email"));
                bean.setProfile(rs.getString("profile"));
            }
        } catch (Exception e) {
            System.out.println("selectMember() : " + e);
        } finally {
            disconnect();
        }
        return bean;
    }

    public boolean updateProfile(TmemberBean bean) { // 완료
        connect();

        try {
            if (bean.getProfile() == null) {
                String sql = 
                          " UPDATE TMEMBER SET  "
                        + "       pw    = ?     "
                        + "     , name  = ?     "
                        + "     , phone = ?     "
                        + " WHERE id = ?        ";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, bean.getPw());
                pstmt.setString(2, bean.getName());
                pstmt.setString(3, bean.getPhone());
                pstmt.setString(4, bean.getId());
            } else {
                String sql = 
                          " UPDATE TMEMBER SET   "
                        + "       pw      = ?    "
                        + "     , name    = ?    "
                        + "     , phone   = ?    "
                        + "     , profile = ?    "
                        + " WHERE id = ?         ";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, bean.getPw());
                pstmt.setString(2, bean.getName());
                pstmt.setString(3, bean.getPhone());
                pstmt.setString(4, bean.getProfile());
                pstmt.setString(5, bean.getId());
            }

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("DB:updateProfile(): " + e);
            return false;
        } finally {
            disconnect();
        }
        return true;
    }

    public Vector<ShowBean> getTop4() { // 완료
        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> top4 = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {
            // 3.
            String sql = 
                      " SELECT                                                  "
                    + "     *                                                   "
                    + " FROM                                                    "
                    + "     ( SELECT                                            "
                    + "             A.*                                         "
                    + "           , @r:=@r+1 AS rnum                            "
                    + "       FROM                                              "
                    + "            ( SELECT                                     "
                    + "                  *                                      "
                    + "              FROM                                       "
                    + "                  SHOWINFO                               "
                    + "              ORDER BY slike DESC                        "
                    + "            ) A                                          "
                    + "          , ( SELECT @r:=0 ) r                           "
                    + "     ) C                                                 "
                    + " WHERE 1=1                                               "
                    + " AND rnum <= 4                                           ";
            pstmt = conn.prepareStatement(sql);
            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();
            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                top4.add(bean);
            }
            // 5
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return top4;// 박스 객체를 리턴
    }

    public Vector<ShowBean> getNewTicket() { // 완료
        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> newTicket = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {
            // 3.
            String sql = 
                    
                    " SELECT                                                  "
                  + "     *                                                   "
                  + " FROM                                                    "
                  + "     ( SELECT                                            "
                  + "             A.*                                         "
                  + "           , @r:=@r+1 AS rnum                            "
                  + "       FROM                                              "
                  + "            ( SELECT                                     "
                  + "                  *                                      "
                  + "              FROM                                       "
                  + "                  SHOWINFO                               "
                  + "              ORDER BY supdate DESC                      "
                  + "            ) A                                          "
                  + "          , ( SELECT @r:=0 ) r                           "
                  + "     ) C                                                 "
                  + " WHERE 1=1                                               "
                  + " AND rnum <= 12                                          ";
            pstmt = conn.prepareStatement(sql);
            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();
            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                newTicket.add(bean);
            }
            // 5
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTicket;// 박스 객체를 리턴
    }

    public Vector<ShowBean> getSearchTab_top4(String gubun) { // 완료
        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> top4 = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {
            // 3.
            String sql = 
                    " SELECT                                                  "
                  + "     *                                                   "
                  + " FROM                                                    "
                  + "     ( SELECT                                            "
                  + "             A.*                                         "
                  + "           , @r:=@r+1 AS rnum                            "
                  + "       FROM                                              "
                  + "            ( SELECT                                     "
                  + "                  *                                      "
                  + "              FROM                                       "
                  + "                  SHOWINFO                               "
                  + "              WHERE stab = ?                               "
                  + "              ORDER BY slike DESC                        "
                  + "            ) A                                          "
                  + "          , ( SELECT @r:=0 ) r                           "
                  + "     ) C                                                 "
                  + " WHERE 1=1                                               "
                  + " AND rnum <= 4                                           ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gubun);

            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();
            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                top4.add(bean);
            }
            // 5
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return top4;// 박스 객체를 리턴
    }

    public Vector<ShowBean> getSearchTab_new(String gubun) { // 완료

        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> newTicket = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {
            // 3.
            String sql = 
                    " SELECT                                                  "
                  + "     *                                                   "
                  + " FROM                                                    "
                  + "     ( SELECT                                            "
                  + "             A.*                                         "
                  + "           , @r:=@r+1 AS rnum                            "
                  + "       FROM                                              "
                  + "            ( SELECT                                     "
                  + "                  *                                      "
                  + "              FROM                                       "
                  + "                  SHOWINFO                               "
                  + "              WHERE stab = ?                               "
                  + "              ORDER BY supdate DESC                      "
                  + "            ) A                                          "
                  + "          , ( SELECT @r:=0 ) r                           "
                  + "     ) C                                                 "
                  + " WHERE 1=1                                               "
                  + " AND rnum <= 30                                          ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gubun);

            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();
            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                newTicket.add(bean);
            }
            // 5
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTicket;// 박스 객체를 리턴
    }

    public int getSearchTab_count(String gubun) { // 완료
        connect();

        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM  SHOWINFO WHERE stab = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gubun);

            rs = pstmt.executeQuery();

            if (rs.next()) {// 검색 결과가 있다면
                count = rs.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Vector<ShowBean> getSearchTab_top4_loc(String gubunLocation) { // 완료
        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> top4 = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {
            // 3.
            String sql = 
                    " SELECT                                                  "
                  + "     *                                                   "
                  + " FROM                                                    "
                  + "     ( SELECT                                            "
                  + "             A.*                                         "
                  + "           , @r:=@r+1 AS rnum                            "
                  + "       FROM                                              "
                  + "            ( SELECT                                     "
                  + "                  *                                      "
                  + "              FROM                                       "
                  + "                  SHOWINFO                               "
                  + "              AND slocation = ?                          "
                  + "              ORDER BY slike DESC                        "
                  + "            ) A                                          "
                  + "          , ( SELECT @r:=0 ) r                           "
                  + "     ) C                                                 "
                  + " WHERE 1=1                                               "
                  + " AND rnum <= 4                                           ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gubunLocation);

            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();
            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                top4.add(bean);
            }
            // 5
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return top4;// 박스 객체를 리턴
    }

    public Vector<ShowBean> getSearchTab_new_loc(String gubunLocation) { // 완료
        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> newTicket = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {
            // 3.
            String sql = 
                    " SELECT                                                  "
                  + "     *                                                   "
                  + " FROM                                                    "
                  + "     ( SELECT                                            "
                  + "             A.*                                         "
                  + "           , @r:=@r+1 AS rnum                            "
                  + "       FROM                                              "
                  + "            ( SELECT                                     "
                  + "                  *                                      "
                  + "              FROM                                       "
                  + "                  SHOWINFO                               "
                  + "              AND slocation = ?                          "
                  + "              ORDER BY supdate DESC                      "
                  + "            ) A                                          "
                  + "          , ( SELECT @r:=0 ) r                           "
                  + "     ) C                                                 "
                  + " WHERE 1=1                                               "
                  + " AND rnum <= 30                                          ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gubunLocation);

            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();
            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                newTicket.add(bean);
            }
            // 5
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return newTicket;// 박스 객체를 리턴
    }

    public int getSearchTab_count_loc(String gubunLocation) { // 완료
        connect();

        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM SHOWINFO WHERE slocation=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, gubunLocation);

            rs = pstmt.executeQuery();

            if (rs.next()) {// 검색 결과가 있다면
                count = rs.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Vector<ShowBean> getSearchTitle(String sql) { // 완료
        connect();

        // 리턴할 객체 생성(즉 박스로 리턴)
        Vector<ShowBean> searchTitle = new Vector<>();

        // 컬럼의 데이터를 빈클래스에 맵핑해야하기에 객체를 선언(즉, 가방)
        ShowBean bean = null;
        try {

            pstmt = conn.prepareStatement(sql);

            // 4.쿼리실행후 결과를 리턴
            rs = pstmt.executeQuery();

            // 반복문을 통하여 데이터를 가방(빈에 저장)에 추출
            while (rs.next()) {
                // 빈클래스 (가방) 객체 생성
                bean = new ShowBean();
                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));

                searchTitle.add(bean);
            }
            // 5
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchTitle;// 박스 객체를 리턴
    }

    public ShowBean getTicketInfo(String sno) { // 완료
        connect();

        ShowBean bean = null;
        try {
            String sql = "SELECT * FROM SHOWINFO WHERE sno=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new ShowBean();

                bean.setSno(rs.getString(1));
                bean.setSname(rs.getString(2));
                bean.setSaddress(rs.getString(3));
                bean.setSperiod(rs.getString(4));
                bean.setSactor(rs.getString(5));
                bean.setSprice(rs.getInt(6));
                bean.setStime(rs.getString(7));
                bean.setStab(rs.getString(8));
                bean.setSlocation(rs.getString(9));
                bean.setSmainimg(rs.getString(10));
                bean.setSwido(rs.getString(11));
                bean.setSkyungdo(rs.getString(12));
                bean.setSlike(rs.getInt(13));
                bean.setSupdate(rs.getDate(14));
                bean.setStemp(rs.getString(15));
                bean.setSinttemp(rs.getInt(16));
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public int getLike(String sno, String id) {
        connect();

        int result = 0;
        try {
            String sql = "SELECT COUNT(*) FROM FAVORITE WHERE fsno=? AND fid=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            pstmt.setString(2, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Vector<ShowImgBean> getTicketDetail(String sno) { // 완료
        connect();

        Vector<ShowImgBean> v = new Vector<>();
        ShowImgBean imgbean = null;

        try {
            String sql = "SELECT * FROM SHOWIMG WHERE sno=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                imgbean = new ShowImgBean();

                imgbean.setSno(rs.getString(1));
                imgbean.setSsubimg(rs.getString(2));

                v.add(imgbean);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public Vector<ReviewBean> getReviewAll(String sno, String id) { // 완료
        connect();

        Vector<ReviewBean> reviewAll = new Vector<>();
        ReviewBean bean = null;

        try {

            String sql = 
                      " SELECT                          "
                    + "       review.*                  "
                    + "     , tmember.name              "
                    + "     , tmember.profile           "
                    + " FROM                            "
                    + "     REVIEW LEFT JOIN TMEMBER    "
                    + "     ON REVIEW.rid = TMEMBER.id  "
                    + " WHERE 1=1                       "
                    + " AND review.rsno = ?             ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);

            rs = pstmt.executeQuery();

            while (rs.next()) {

                bean = new ReviewBean();
                bean.setRid(rs.getString(1));
                bean.setRsno(rs.getString(2));
                bean.setRcontents(rs.getString(3));
                bean.setRdate(rs.getDate(4));
                bean.setName(rs.getString(5));
                bean.setProfile(rs.getString(6));

                reviewAll.add(bean);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return reviewAll;
    }

    public Vector<ShowSeatBean> getBookInfo(String sno) { // 완료,오류
        connect();

        Vector<ShowSeatBean> v = new Vector<>();
        ShowSeatBean bean = null;

        try {
            String sql = "SELECT * FROM SHOWSEAT WHERE sssno=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ShowSeatBean();
                bean.setSssno(rs.getString(1));
                bean.setSsdate(rs.getDate(2));
                bean.setSsseat(rs.getInt(3));

                v.add(bean);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public String getBookInfo_time(String sno) { // 완료
        connect();

        String time = null;
        try {
            String sql = "SELECT stime FROM SHOWINFO WHERE sno = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                time = rs.getString(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public void insertLike(String id, String sno) { // 완료(수정완료)
        connect();

        try {
            String sql = "INSERT INTO favorite VALUES(?,?,NOW())";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, sno);

            int result = pstmt.executeUpdate();

            if (result != 0) {
                String sql2 = "UPDATE  SHOWINFO  SET slike = slike + 1 WHERE sno= ?";

                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, sno);

                pstmt.executeUpdate();
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteLike(String id, String sno) { // 완료(수정완료)
        connect();

        try {
            String sql = "DELETE FROM favorite WHERE fid=? AND fsno=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, sno);

            int result = pstmt.executeUpdate();

            if (result != 0) {
                String sql2 = "UPDATE SHOWINFO SET slike = slike - 1 WHERE sno= ?";

                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, sno);

                pstmt.executeUpdate();
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insertReview(String id, String sno, String contents) {// 완료
        connect();

        try {
            String sql = "INSERT INTO review VALUES(?,?,?,NOW())";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, sno);
            pstmt.setString(3, contents);

            pstmt.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getSprice(String sno) { // 완료
        connect();

        int sprice = 0;
        try {
            String sql = "SELECT sprice FROM SHOWINFO WHERE sno=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                sprice = rs.getInt(1);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprice;
    }

    public void insertBook(String id, String sno, String bookDate, int people, int sprice) {// 완료
        connect();

        try {

            String bssdate = bookDate.substring(0, 10);
            int totalprice = sprice * people;

            String sql = 
                        " INSERT INTO BOOK (               "
                      + "       bno                        "
                      + "     , bid                        "
                      + "     , bsno                       "
                      + "     , bssdate                    "
                      + "     , bpeople                    "
                      + "     , btotalprice                "
                      + " ) VALUES (                       "
                      + "       nextval('BOOK_SEQ')        "
                      + "     , ?                          "
                      + "     , ?                          "
                      + "     , ?                          "
                      + "     , ?                          "
                      + "     , ?                          "
                      + ")                                 ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, sno);
            pstmt.setString(3, bssdate);
            pstmt.setInt(4, people);
            pstmt.setInt(5, totalprice);

            pstmt.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteSeat(String sno, String bssdate, int people) { // 완료
        connect();
        int result = 0;

        try {
            String sql = "SELECT ssseat FROM showseat WHERE sssno=? AND ssdate=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            pstmt.setString(2, bssdate);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);

                String sql_update = "UPDATE showseat SET ssseat=? WHERE sssno=? AND ssdate=?";
                pstmt = conn.prepareStatement(sql_update);

                int ssseat = result - people;

                pstmt.setInt(1, ssseat);
                pstmt.setString(2, sno);
                pstmt.setString(3, bssdate);

                pstmt.executeUpdate();

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public TmemberBean getTmember(String id) {
        connect();

        TmemberBean bean = null;

        try {
            String sql = "SELECT * FROM tmember WHERE id=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new TmemberBean();

                bean.setId(rs.getString(1));
                bean.setPw(rs.getString(2));
                bean.setName(rs.getString(3));
                bean.setPhone(rs.getString(4));
                bean.setEmail(rs.getString(5));
                bean.setProfile(rs.getString(6));
                bean.setTemp(rs.getString(7));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public Vector<ShowBean> getInterest(String id) { // 완료
        connect();

        Vector<ShowBean> v = new Vector<>();
        ShowBean bean = null;
        try {
            String sql = 
                          " SELECT                                        "
                        + "     *                                         "
                        + " FROM                                          "
                        + "     (SELECT                                   "
                        + "           A.*                                 "
                        + "         , @r:=@r+1 AS rnum                    "
                        + "      FROM                                     "
                        + "         ( SELECT                              "
                        + "                FAVORITE.*                     "
                        + "              , SHOWINFO.smainimg              "
                        + "           FROM                                "
                        + "              FAVORITE LEFT JOIN SHOWINFO      "
                        + "              ON FAVORITE.fsno = SHOWINFO.sno  "
                        + "           WHERE FAVORITE.fid = ?              "
                        + "           ORDER BY FAVORITE.clicktime DESC    "
                        + "         ) A                                   "
                        + "       , ( SELECT @r:=0 ) r                    "
                        + "     ) B                                       "
                        + " WHERE rnum <=6                                ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new ShowBean();

                bean.setSno(rs.getString(2));
                bean.setSmainimg(rs.getString(4));

                v.add(bean);
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public Vector<BookBean> getBook(String id) { // 완료
        connect();
        Vector<BookBean> v = new Vector<>();
        BookBean bean = null;

        try {
            String sql =
                    " SELECT                        "
                  + "     BOOK.*                    "
                  + "   , SHOWINFO.smainimg         "
                  + "   , SHOWINFO.slocation        "
                  + "   , SHOWINFO.sname            "
                  + "   , SHOWINFO.stime            "
                  + " FROM BOOK LEFT JOIN SHOWINFO  "
                  + " ON BOOK.bsno = SHOWINFO.sno   "
                  + " WHERE 1=1                     "
                  + " AND BOOK.bid = ?              "
                  + " ORDER BY BOOK.bno DESC        ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new BookBean();
                bean.setBno(rs.getInt(1));
                bean.setBid(rs.getString(2));
                bean.setBsno(rs.getString(3));
                bean.setBssdate(rs.getString(4));
                bean.setBpeople(rs.getInt(5));
                bean.setBtotalprice(rs.getInt(6));
                bean.setSmainimg(rs.getString(7));
                bean.setSlocation(rs.getString(8));
                bean.setSname(rs.getString(9));
                bean.setStime(rs.getString(10));

                v.add(bean);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    // 후기 중복 쓰기를 방지하기 위해 기존에 후기를 쓴 이력이 있는지 확인하는 메소드
    public int getWriteReview(String id, String sno) { // 완료

        connect();
        int a = 0;

        try {
            String sql = "SELECT COUNT(*) FROM review WHERE rid=? AND rsno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, sno);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                a = rs.getInt(1);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public int deleteBook(String sno, int bno, String bssdate, int people) { // 완료
        connect();

        int result = 0;
        int ssseat = 0;

        try {
            String sql = "SELECT ssseat FROM showseat WHERE sssno=? AND ssdate=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, sno);
            pstmt.setString(2, bssdate);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt(1);

                String sql_delete = "DELETE FROM book WHERE bno=?";
                pstmt = conn.prepareStatement(sql_delete);

                ssseat = result + people;

                pstmt.setInt(1, bno);

                pstmt.executeUpdate();

            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssseat;
    }

    public void updateBook(int ssseat, String sno, String bssdate) { // 완료
        connect();

        try {
            String sql = "UPDATE showseat SET ssseat=? WHERE sssno=? AND ssdate=?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, ssseat);
            pstmt.setString(2, sno);
            pstmt.setString(3, bssdate);

            pstmt.executeUpdate();

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAllcount() { // 완료
        connect();
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM QNA"; // �������Լ�
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Vector<QnABean> getAllBoard(int start, int end) { // 완료
        connect();
        Vector<QnABean> v = new Vector<>();
        QnABean bean = null;
        try {
            String sql = 
                      " SELECT                                      "
                    + "     *                                       "
                    + " FROM                                        "
                    + "     ( SELECT                                "
                    + "           A.*                               "
                    + "         , @r:=@r+1 AS rnum                  "
                    + "       FROM                                  "
                    + "         ( SELECT                            "
                    + "               *                             "
                    + "           FROM QNA                          "
                    + "           ORDER BY qgroup DESC, qlevel ASC  "
                    + "         ) A                                 "
                    + "       , ( SELECT @r:=0 ) r                  "
                    + "     ) B                                     "
                    + " WHERE 1=1                                   "
                    + " AND rnum >= ?                               "
                    + " AND rnum <= ?                               ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, start);
            pstmt.setInt(2, end);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean = new QnABean();
                bean.setQno(rs.getInt(1));
                bean.setQid(rs.getString(2));
                bean.setQpw(rs.getString(3));
                bean.setQsubject(rs.getString(4));
                bean.setQcontents(rs.getString(5));
                bean.setQdate(rs.getDate(6));
                bean.setQgroup(rs.getInt(7));
                bean.setQstep(rs.getInt(8));
                bean.setQlevel(rs.getInt(9));
                bean.setQcount(rs.getInt(10));
                v.add(bean);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    public QnABean getOneboard(String qno) { // 완료
        connect();
        QnABean bean = null;
        try {
            String countsql = "UPDATE QNA SET qcount= qcount+1 WHERE qno=?";
            pstmt = conn.prepareStatement(countsql);
            pstmt.setInt(1, Integer.parseInt(qno));
            pstmt.executeUpdate();

            String sql = "SELECT * FROM QNA WHERE qno=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(qno));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new QnABean();
                bean.setQno(rs.getInt(1));
                bean.setQid(rs.getString(2));
                bean.setQpw(rs.getString(3));
                bean.setQsubject(rs.getString(4));
                bean.setQcontents(rs.getString(5));
                bean.setQdate(rs.getDate(6));
                bean.setQgroup(rs.getInt(7));
                bean.setQstep(rs.getInt(8));
                bean.setQlevel(rs.getInt(9));
                bean.setQcount(rs.getInt(10));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public void qnaInsert(QnABean bean) { // 완료

        connect();
        int maxref = 0;

        try {

            if (bean.getQid() != null) {

                String sql = "SELECT MAX(qgroup) FROM QNA";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    maxref = rs.getInt(1);
                }

                bean.setQgroup(maxref + 1);
                bean.setQstep(1);
                bean.setQlevel(1);
                bean.setQcount(0);

                String insertsql = "INSERT INTO QNA VALUES(nextval('QNA_SEQ'),?,?,?,?,NOW(),?,?,?,?)";

                pstmt = conn.prepareStatement(insertsql);
                pstmt.setString(1, bean.getQid());
                pstmt.setString(2, bean.getQpw());
                pstmt.setString(3, bean.getQsubject());
                pstmt.setString(4, bean.getQcontents());
                pstmt.setInt(5, bean.getQgroup());
                pstmt.setInt(6, bean.getQstep());
                pstmt.setInt(7, bean.getQlevel());
                pstmt.setInt(8, bean.getQcount());
                pstmt.executeUpdate();
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void qnaReInsert(QnABean bean) { // 완료
        connect();
        try {
            bean.setQstep(bean.getQstep() + 1);

            String levelsql = 
                          " UPDATE QNA SET qlevel=qlevel+1 " 
                        + " WHERE qgroup=? AND qlevel>?    ";
            pstmt = conn.prepareStatement(levelsql);

            pstmt.setInt(1, bean.getQgroup());// �θ�� �׷�
            pstmt.setInt(2, bean.getQlevel());// �θ� �� ����
            pstmt.executeUpdate();

            bean.setQlevel(bean.getQlevel() + 1);
            bean.setQcount(0);// ī��Ʈ�� �ʱ�ȭ

            String insertsql = "INSERT INTO QNA VALUES(nextval('QNA_SEQ'),?,?,?,?,NOW(),?,?,?,?)";

            pstmt = conn.prepareStatement(insertsql);
            pstmt.setString(1, bean.getQid());
            pstmt.setString(2, bean.getQpw());
            pstmt.setString(3, bean.getQsubject());
            pstmt.setString(4, bean.getQcontents());
            pstmt.setInt(5, bean.getQgroup());
            pstmt.setInt(6, bean.getQstep());
            pstmt.setInt(7, bean.getQlevel());
            pstmt.setInt(8, bean.getQcount());

            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updateqna(String id, String qno) { // 완료
        connect();
        int check = 0;
        try {
            String updatesql = "SELECT * FROM QNA WHERE qno=? AND qid=?";
            pstmt = conn.prepareStatement(updatesql);
            pstmt.setInt(1, Integer.parseInt(qno));
            pstmt.setString(2, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                check = 1;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public void updateqna2(QnABean bean, String title, String content) { // 완료
        connect();

        try {
            String updatesql2 = "UPDATE QNA SET qdate=NOW(), qsubject=?,qcontents=? WHERE qno=?";
            pstmt = conn.prepareStatement(updatesql2);
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setInt(3, bean.getQno());
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int deleteqna(String id, String qno) { // 완료
        connect();
        int check = 0;
        try {
            String delsql = "DELETE FROM QNA WHERE qno=? AND qid=?";
            pstmt = conn.prepareStatement(delsql);
            pstmt.setInt(1, Integer.parseInt(qno));
            pstmt.setString(2, id);
            check = pstmt.executeUpdate();
            if (check != 0) {
                check = 1;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
}
