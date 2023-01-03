<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.testserver1.FileGenerator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
        System.out.println(request.getServerPort() + " "+request.getRequestURI());
        System.out.println("file generator started");
          Connection conn = (Connection) session.getAttribute("conn");
          String table_name = session.getAttribute("table_name").toString();

      if(conn!=null && table_name!=null) {
          FileGenerator fileGenerator = new FileGenerator(table_name, "sub-test", "postgres", "password", "test",conn, request.getServerPort());
          Thread t1 = new Thread(fileGenerator);
          t1.start();
      }else {
          System.out.println("request success");
      }
    %>
</head>
<body>

</body>
</html>
