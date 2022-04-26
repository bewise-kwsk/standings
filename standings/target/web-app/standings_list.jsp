<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="standings.Standing" %>
<jsp:useBean id="standings" scope="session" type="List<Standing>" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Standings</title>
</head>
<body>
  <h1>順位表</h1>
  <table border="1">
    <tbody>
      <tr style="text-align: center;">
        <th>チーム</th>
        <th>試合数</th>
        <th>勝</th>
        <th>分</th>
        <th>敗</th>
        <th>得</th>
        <th>失</th>
        <th>差</th>
        <th>点</th>
      </tr>
      <%
      for (Standing standing: standings) {
      %>
        <tr>
          <td style="text-align; end;"><%= standing.getTeam() %></td>
          <td style="text-align: end;"><%= standing.getPlayed() %></td>
          <td style="text-align: end;"><%= standing.getWon() %></td>
          <td style="text-align: end;"><%= standing.getDrawn() %></td>
          <td style="text-align: end;"><%= standing.getLost() %></td>
          <td style="text-align: end;"><%= standing.getGf() %></td>
          <td style="text-align: end;"><%= standing.getGa() %></td>
          <td style="text-align: end;"><%= standing.getGd() %></td>
          <td style="text-align: end;"><%= standing.getPoints() %></td>
        </tr>
      <%
      }
      %>
    </tbody>
  </table>
</body>
</html>