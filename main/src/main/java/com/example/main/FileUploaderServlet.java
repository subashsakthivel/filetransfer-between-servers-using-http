package com.example.main;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import jakarta.websocket.Session;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;

@WebServlet(name = "FileUploaderServlet", value = "/FileUploaderServlet")
public class FileUploaderServlet extends HttpServlet {
    public static volatile int n =0;
    public static Connection conn = null;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String server_name = request.getParameter("name");
        System.out.println(server_name );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String server_name = request.getParameter("name");
        HttpSession session = request.getSession();
        String table_name = "maindb";
        DBFunctions db = new DBFunctions();
        if(conn==null) {
            synchronized (this) {
                conn = db.connect_to_db("sub-test", "postgres", "password");
                System.out.println("conn db worked");
            }
        }
        Path path = Paths.get("C:\\serverpage\\file" + n + ".zip");
        File file = path.toFile();
        //file.createNewFile();
        if(conn!=null) {
            db.insert_row(conn, table_name, server_name, file.getName(), path.toString(), String.valueOf(System.currentTimeMillis()));
        }else{
            System.out.println("connection lossed");
        }
        n++;
        System.out.println(server_name );
        InputStream in = request.getInputStream();

        System.out.println("file receiving started.."+path);
        FileChannel fileChannel = null;
        FileOutputStream fos = new FileOutputStream(file);
        try {
            fileChannel = FileChannel.open(path,
                    EnumSet.of(StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.WRITE)
            );

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int i = 0;
            while ((i = in.read())!= -1 ) {
                //buffer.flip();
                fos.write(i);
                //System.out.print((char)i);
                //fileChannel.write(ByteBuffer.allocateDirect(i));
                //buffer.clear();
            }

            fos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            assert fileChannel != null;
            fileChannel.close();
            in.close();
            fos.close();
         //   System.out.println("table_name" + table_name);

            System.out.println("file received " + path);
        }
        //new FileTransfer(request.getInputStream(), server_name, null , "maindb");
    }
}
