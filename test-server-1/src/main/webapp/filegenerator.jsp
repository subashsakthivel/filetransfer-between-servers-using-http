<%@ page import="java.sql.Connection" %>
<%@ page import="com.example.testserver1.FileGenerator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%
      Connection conn = (Connection) session.getAttribute("conn");
      FileGenerator fileGenerator = new FileGenerator("subtest1","sub-test", "postgres", "password","test",conn);
      Thread t1 = new Thread(fileGenerator);
      t1.start();
    %>
</head>
<body>

</body>
</html>
