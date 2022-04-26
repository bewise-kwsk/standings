<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="standings.Match" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<jsp:useBean id="stored_match" scope="session" type="standings.Match" />
<%!
  static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Match Result</title>
</head>
<body>
  <h1>新規試合情報登録</h1>
  <p>以下の試合情報は登録済みです</p>
  <p><a href="matches_edit.jsp">こちら</a>から編集画面に、<a href="matches.jsp">こちら</a>から一覧画面に移動できます</p>
    <hr />
    <table border = 1>
      <tr>
        <th>節</th>
        <th>日付</th>
        <th>ホーム</th>
        <th>結果</th>
        <th>アウェイ</th>
      </tr>
      <tr>
        <td><%= stored_match.getSection() %></td>
        <td><%= stored_match.getDate().format(formatter) %></td>
        <td><%= stored_match.getHome() %>
        <td><%= stored_match.getGoalsFor() %>&nbsp;-&nbsp;<%= stored_match.getGoalsAgainst() %></td>
        <td><%= stored_match.getAway() %></td>
      </tr>
    </table>
  </body>
</html>