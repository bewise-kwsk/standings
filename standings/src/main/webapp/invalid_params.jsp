<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="message" scope="session" type="java.lang.String" />

<!DOCTYPE html>
<html>
 <head>
  <meta charset="UTF-8">
  <title>Add New Match - Invalid Match Parameters</title>
 </head>
 <body>
  <h1>試合情報の入力値が正しくありません</h1>
  <p><%= message %></p>
  <p><a href="matches_edit.jsp">こちら</a>から編集画面に戻ってください</p>
 </body>
</html>
