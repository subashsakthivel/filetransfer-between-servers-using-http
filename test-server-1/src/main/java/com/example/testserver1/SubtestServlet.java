package com.example.testserver1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


//no use
@WebServlet(name = "SubtestServlet", value = "/SubtestServlet")
public class SubtestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8080/main_war_exploded/TestServlet");
            System.out.println(url.getPath() + " " + url.getPort() + " " +url.getRef() + " " +url.getProtocol() + " " +url.getHost()+" "+request.getServerPort());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setChunkedStreamingMode(1024);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "text");
            connection.setRequestProperty("PORT", "8081");
            connection.setRequestProperty("servername", "servername 01");
            connection.setRequestProperty("Request-url","server1");
            connection.setRequestProperty("Origin",request.getRequestURI());
            connection.setDoOutput(true);
            InputStream in = connection.getInputStream();
            connection.setDefaultUseCaches(false);
            connection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert connection != null;
        System.out.println("Response code : "+connection.getResponseCode());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println(request.getServerPort() + " "+ request.getRequestURI());
    }
}
