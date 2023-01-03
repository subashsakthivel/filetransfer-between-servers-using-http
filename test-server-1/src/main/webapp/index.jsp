<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.testserver1.DBFunctions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="js/ajax.js"></script>
<!DOCTYPE html>
<html>
<head>
    <title>Server Page 1</title>
</head>

<body>
<%
    DBFunctions db = new DBFunctions();
    Connection conn = db.connect_to_db("sub-test", "postgres", "password");
    session.setAttribute("conn",conn);
    session.setAttribute("table_name", "subtest1");
%>
<div style="align-content: center">
    <input type="button" onclick="generator()" value="start">
    <div id="result"></div>
</div>
</body>
</html>