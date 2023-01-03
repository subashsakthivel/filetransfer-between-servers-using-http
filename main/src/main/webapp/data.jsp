<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="com.example.main.DBFunctions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<body>
<%
    Connection conn = (Connection) session.getAttribute("conn");
    String table_name = session.getAttribute("table_name").toString();
    if (conn != null) {
        out.print("<h4> Database Connection Established</h4>");
        DBFunctions db = new DBFunctions();
        ResultSet rs = db.fetch_data(conn, table_name);

        out.print("<table border=1><tr>" + "<th>id</th>" + "<th>server name</th>" + "<th>file name</th>" +  "<th>file location</th>" + "<th>time</th>"+"</tr>");
        while (true) {
            try {
                if (!rs.next()) break;
                out.print("<tr><td>" + rs.getString("id") + "</td><td>"+rs.getString("server_name")+"</td><td>"+rs.getString("file_name")+"</td><td>"+rs.getString("file_location")+"</td><td>"+rs.getString("time")+"</td></tr>");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        out.print("</table>");
    } else out.print("<h4> Connection Failed</h4>");
%>
</body>
</html>
