package com.example.testserver1;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTransfer extends Thread{

    private final String file_location ;
    private final HttpURLConnection connection;

    public FileTransfer(String file_location) {
        this.file_location = file_location;
        this.connection = connect();
        try {
            sleep(2000);
        }catch (Exception e){
            e.printStackTrace();
        }
        start();
    }
    public HttpURLConnection connect() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("http://localhost:8080/main_war_exploded/FileUploaderServlet?name=server1");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setChunkedStreamingMode(1024);
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setDoOutput(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void run() {
        Path path = Paths.get(file_location);
        FileChannel inChannel = null;
        OutputStream os = null;
        System.out.println("file transfer started "+ path);
        try {
            inChannel = FileChannel.open(path);
            os = connection.getOutputStream();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (inChannel.read(buffer) != -1) {
                buffer.flip();
                assert os != null;
               // System.out.print(buffer);
                os.write(buffer.array());
                buffer.clear();
            }
            os.flush();
            System.out.println("response code " + connection.getResponseCode());
        }catch (Exception e){
            e.printStackTrace();
        } finally {

            System.out.println("file transfer completed "+ path);
        }
    }
}
