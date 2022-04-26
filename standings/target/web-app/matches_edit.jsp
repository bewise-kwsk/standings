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
<title>Edit Match Results</title>
</head>
<body>
  <h1>試合情報編集</h1>
  <table border="1">
    <tbody>
      <tr style="text-align: center;">
        <th>節</th>
        <th>日付</th>
        <th>ホーム</th>
        <th>得点</th>
        <th>アウェイ</th>
        <th>操作</th>
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
          <td>
            <form action="edit_match" method="post">
              <input type="hidden" name="match_no" value="<%=rset.getInt("match_no") %>" />
              <input type="submit" value="変更" />
            </form>
          </td>
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
      <tr>
        <form action="add_match" method="post" >
          <td><input type="number" name="section" /></td>
          <td><input type="date" name="date" /></td>
          <td>
            <select name="home">
              <option value="札幌">札幌</option>
              <option value="鹿島">鹿島</option>
              <option value="浦和">浦和</option>
              <option value="柏">柏</option>
              <option value="FC東京">FC東京</option>
              <option value="川崎F">川崎F</option>
              <option value="横浜FM">横浜FM</option>
              <option value="湘南">湘南</option>
              <option value="清水">清水</option>
              <option value="磐田">磐田</option>
              <option value="名古屋">名古屋</option>
              <option value="京都">京都</option>
              <option value="G大阪">G大阪</option>
              <option value="C大阪">C大阪</option>
              <option value="神戸">神戸</option>
              <option value="広島">広島</option>
              <option value="福岡">福岡</option>
              <option value="鳥栖">鳥栖</option>
            </select>
          </td>
          <td>
            <input type="number" name="goals_for" /> - <input type="number" name="goals_against" />
          </td>
          <td>
            <select name="away">
              <option value="札幌">札幌</option>
              <option value="鹿島">鹿島</option>
              <option value="浦和">浦和</option>
              <option value="柏">柏</option>
              <option value="FC東京">FC東京</option>
              <option value="川崎F">川崎F</option>
              <option value="横浜FM">横浜FM</option>
              <option value="湘南">湘南</option>
              <option value="清水">清水</option>
              <option value="磐田">磐田</option>
              <option value="名古屋">名古屋</option>
              <option value="京都">京都</option>
              <option value="G大阪">G大阪</option>
              <option value="C大阪">C大阪</option>
              <option value="神戸">神戸</option>
              <option value="広島">広島</option>
              <option value="福岡">福岡</option>
              <option value="鳥栖">鳥栖</option>
            </select>
          </td>
          <td>
            <input type="submit" value="追加" />
          </td>
        </form>
      </tr>
    </tbody>
  </table>
</body>
</html>