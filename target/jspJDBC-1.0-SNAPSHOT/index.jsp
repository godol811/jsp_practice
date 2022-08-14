<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="javax.xml.transform.Result" %>

<html>
<head>
    <title>회원 목록</title>
</head>
<body>

MEMBERS 테이블의 내용

<table width="100%" border="1">
    <tr>
        <td>이름</td><td>아이디</td><td>이메일</td>

    </tr>
    <%
        // 1. JDBC 드라이버 로딩
        Class.forName("com.mysql.jdbc.Driver");
        //만약 지정한 클래스가 존재하지 않는경우 에러가 발생한다.
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            //server time 존 설정을 해야한다 mysql 8.0이상은
//            String jdbcDriver =  "jdbc:mysql://localhost/Main?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";
//            String dbUser = "root";
//            String dbPass = "qwer1234";

            String dbcp =  "jdbc:apache:commons:dbcp:Main";
            String query = "select * from MEMBERS order by MEMBERID";

            conn = DriverManager.getConnection(dbcp);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){

    %>

    <tr>
        <td><%= rs.getString("NAME")%></td>
        <td><%= rs.getString("MEMBERID")%></td>
        <td><%= rs.getString("EMAIL")%></td>
    </tr>
    <%
            }


        }catch (SQLException e){
            System.out.println(e.getStackTrace());
        }finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (stmt != null) try {
                stmt.close();
            } catch (SQLException e) {
            }

            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    %>


</table>
</body>
</html>
