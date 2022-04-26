<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="standings.Match" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<jsp:useBean id="message" scope="session" type="java.lang.String" />
<jsp:useBean id="match" scope="session" type="standings.Match" />
<jsp:useBean id="ref_match" scope="session" type="standings.Match" />
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
    <h1>入力された試合情報が正しくありません</h1>
    <p><%= message %></p>
    <p><a href="matches_edit.jsp">こちら</a>から再度編集を行ってください</p>
    <hr />
    <h2>入力された試合情報</h2>
    <table border = 1>
      <tr>
        <th>節</th>
        <th>日付</th>
        <th>ホーム</th>
        <th>結果</th>
        <th>アウェイ</th>
      </tr>
      <tr>
        <td><%= match.getSection() %></td>
        <td><%= match.getDate().format(formatter) %></td>
        <td><%= match.getHome() %>
        <td><%= match.getGoalsFor() %>&nbsp;-&nbsp;<%= match.getGoalsAgainst() %></td>
        <td><%= match.getAway() %></td>
      </tr>
    </table>
    <hr />
    <h2>登録済みの試合情報</h2>
    <table border = 1>
      <tr>
        <th>節</th>
        <th>日付</th>
        <th>ホーム</th>
        <th>結果</th>
        <th>アウェイ</th>
      </tr>
      <tr>
        <td><%= ref_match.getSection() %></td>
        <td><%= ref_match.getDate().format(formatter) %></td>
        <td><%= ref_match.getHome() %>
        <td><%= ref_match.getGoalsFor() %>&nbsp;-&nbsp;<%= match.getGoalsAgainst() %></td>
        <td><%= ref_match.getAway() %></td>
      </tr>
    </table>
  </body>
</html>