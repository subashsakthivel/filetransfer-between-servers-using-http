package com.example.main;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.util.Calendar;
import java.util.EnumSet;

public class FileTransfer extends Thread{

    private final InputStream in ;
    private final String servername;
    private final Connection conn ;
    private final Path path ;
    private final String table_name ;
    private final String file_name ;


    public FileTransfer(InputStream in, String servername, Connection conn, String table_name) {
        this.in = in;
        this.servername = servername;
        this.conn = conn;
        this.table_name = table_name;
        this.file_name = "file"+Thread.currentThread().getName().substring(5)+".zip";
        this.path = Paths.get("C:\\serverpage\\"+file_name);
        start();
    }

    @Override
    public void run() {
        //write files here
        System.out.println("file receiving started.."+path);
        FileChannel fileChannel = null;
        try {
            fileChannel = FileChannel.open(path,
                    EnumSet.of(StandardOpenOption.CREATE,
                            StandardOpenOption.TRUNCATE_EXISTING,
                            StandardOpenOption.WRITE)
            );
           // ByteBuffer buffer = ByteBuffer.allocate(1024);
            int i = 0;
            while ((i = in.read())!= -1 ) {
               // buffer.flip();
                System.out.print((char)i);
               // fileChannel.write(ByteBuffer.allocateDirect(i));
              //  buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("file received "+ path);
        }

    }
}
