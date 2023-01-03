package com.example.main;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;


//no use
@WebServlet(name = "TestServlet", value = "/TestServlet")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        assert out != null;
        out.print("<h1>heelo</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<h1>hello</h1>");
        System.out.println("remote address " + request.getRemoteAddr() +
                "\nremote User " +request.getRemoteUser() +
                "\nremote host " +request.getRemoteHost() +
                "\nremote port " +request.getRemotePort() +
                "\nserver port " + request.getServerPort() +
                "\nlocal port "+request.getLocalPort() +
                "\nquerystring "+request.getQueryString() +
                "\ncontent type "+request.getContentType() +
                "\nurl " + request.getRequestURI() +
                "\ncontext path "+ request.getContextPath()+
                "\npath trans "+request.getPathTranslated() +
                "\norigin " + request.getHeader("Origin") +
                "\nPORT "+ request.getHeader("PORT"));
        Collection<String> h = response.getHeaderNames();
        for(String s : h){
            System.out.println(s + " : "+response.getHeader(s));
        }
    }
}
