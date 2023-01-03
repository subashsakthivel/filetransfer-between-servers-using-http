<%@ page import="com.example.main.DBFunctions" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.net.http.HttpResponse" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.nio.channels.FileChannel" %>
<%@ page import="java.util.EnumSet" %>
<%@ page import="java.nio.file.StandardOpenOption" %>
<%@ page import="java.nio.ByteBuffer" %>
<%@ page import="java.nio.file.Path" %>
<%@ page import="java.nio.file.Paths" %>
<%@ page import="java.nio.channels.Channels" %>
<%@ page import="java.nio.channels.ReadableByteChannel" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="js/ajax.js"></script>
<head>
    <title>Main Server Page</title>
</head>
<body>
<%
    DBFunctions db = new DBFunctions();
    Connection conn = db.connect_to_db("sub-test", "postgres", "password");
    session.setAttribute("conn",conn);
    session.setAttribute("table_name", "maindb");
%>
<div style="align-content: center">
    <div id="result"></div>
</div>
</body>
</html>