<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%!
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Match Results</title>
</head>
<body>
  <h1>試合一覧</h1>
  <table border="1">
    <tbody>
      <tr style="text-align: center;">
        <th>節</th>
        <th>日付</th>
        <th>ホーム</th>
        <th>得点</th>
        <th>アウェイ</th>
      </tr>
      <%
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
          Class.forName("com.mysql.cj.jdbc.Driver");
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/standings", "sampleUser", "SU_pass");
          pstmt = conn.prepareStatement("SELECT * FROM matches ORDER BY match_no");
          ResultSet rset = pstmt.executeQuery();
          while (rset.next()) {
      %>
        <tr>
          <td style="text-align; end;"><%=rset.getInt("section") %></td>
          <td><%=rset.getDate("date").toLocalDate().format(formatter) %></td>
          <td style="text-align: end;"><%=rset.getString("home") %></td>
          <!-- <td><%=rset.getInt("goals_for") %>&nbsp;-&nbsp;<%=rset.getInt("goals_against") %></td> -->
          <td><%=rset.getInt("goals_for") + "&nbsp;-&nbsp;" + rset.getInt("goals_against") %>
          <td><%=rset.getString("away") %></td>
        </tr>
      <%
          }
        } catch (Exception ex) {
        } finally {
          try {
            if (pstmt != null)
              pstmt.close();
            if (conn != null)
              conn.close();
          } catch (SQLException se) {
          }
        }
      %>
    </tbody>
  </table>
</body>
</html>