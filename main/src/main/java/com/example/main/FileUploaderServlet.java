package com.example.main;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(name = "FileUploaderServlet", value = "/FileUploaderServlet")
public class FileUploaderServlet extends HttpServlet {
    public static AtomicInteger n  = new AtomicInteger(0);
    public static Connection conn = null;
    public static Hashtable<Integer, Integer> servername = new Hashtable<>();
    public static AtomicInteger server_no = new AtomicInteger(0);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String server_name = request.getParameter("name");
        System.out.println(server_name );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int PORT = Integer.parseInt(request.getHeader("PORT")) ;

        if(!servername.containsKey(PORT)){
            servername.put(PORT, server_no.incrementAndGet());
        }

        System.out.println("port : " + PORT);

        String table_name = "maindb";
        DBFunctions db = new DBFunctions();
        while(conn==null) {
            synchronized (this) {
                conn = db.connect_to_db("sub-test", "postgres", "password");
                System.out.println("conn db worked");
            }
        }

        //file.createNewFile();



        //FileChannel fileChannel = null;
        InputStream in = request.getInputStream();
        Path path = Paths.get("C:\\serverpage\\file" + n.incrementAndGet() + ".zip");
        File file = path.toFile();
        FileOutputStream fos = new FileOutputStream(file);
        db.insert_row(conn, table_name, "server" + servername.get(PORT), file.getName(), path.toString(), String.valueOf(System.currentTimeMillis()));
        System.out.println("file receiving started.."+path + " " + PORT);
        try {
//            fileChannel = FileChannel.open(path,
//                    EnumSet.of(StandardOpenOption.CREATE,
//                            StandardOpenOption.TRUNCATE_EXISTING,
//                            StandardOpenOption.WRITE)
//            );

            //ByteBuffer buffer = ByteBuffer.allocate(1024);
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

            in.close();
            fos.close();
         //   System.out.println("table_name" + table_name);
            System.out.println("file received " + path + "servername :" + "server"+servername.get(PORT));
        }
        //new FileTransfer(request.getInputStream(), server_name, null , "maindb");
    }
}
