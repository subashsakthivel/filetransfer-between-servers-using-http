<%@ page import="java.io.InputStream" %>
<%@ page import="com.example.main.FileTransfer" %>
<%@ page import="java.sql.Connection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    Connection conn = (Connection) session.getAttribute("conn");
    String table_name = session.getAttribute("table_name").toString();

    if(conn!=null) {
        String server = request.getParameter("name") + " ";
        System.out.println("HI " + server);
//        InputStream in = request.getInputStream();
//        new FileTransfer(in , server, conn, table_name);
    }
    String name = request.getParameter("name");
    System.out.println(name);
%>
</body>
</html>
