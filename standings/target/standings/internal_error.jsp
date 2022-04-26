<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="message" scope="session" type="java.lang.String" />

<!DOCTYPE html>
<html>
 <head>
  <meta charset="UTF-8">
  <title>Internal Error</title>
 </head>
 <body>
  <h1>内部サーバーエラーが発生しました</h1>
  <p><%= message %></p>
  <p><a href="matches_edit.jsp">こちら</a>から編集画面に戻ってください</p>
 </body>
</html>
