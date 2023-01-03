package com.example.testserver1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;

public class FileGenerator implements Runnable{
    public String prefix = "example";
    public String table_name, dbname,user,password;
    Connection conn = null;

    public File createFile(final String prefix) throws IOException {
        String folder = "D:\\sub-test1\\";
        File file = new File(folder + prefix + ".zip");
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        //raf.setLength(ThreadLocalRandom.current().nextLong(1048576 * 500, 1048576L * 4500));
        raf.setLength(ThreadLocalRandom.current().nextLong(1048576 , 1048576L * 5));
        raf.close();
        return file;
    }
    public FileGenerator(String table_name, String dbname, String user, String password, String prefix, Connection conn) {
        this.table_name = table_name;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        this.prefix = prefix;
        this.conn = conn;
    }
    @Override
    public void run() {
        DBFunctions db = new DBFunctions();
        if (conn != null) {
           // db.createTable(conn, table_name);
            String message;
            int n = 0;
            while (true) {
                if(Thread.activeCount() < 20) {
                    File f = null;
                    System.out.println(System.currentTimeMillis());
                    BasicFileAttributes bc = null;
                    try {
                        f = createFile(prefix + n);
                        bc = Files.readAttributes(Paths.get(f.getAbsolutePath()), BasicFileAttributes.class);
                        message = bc.creationTime() + " " + bc.size();
                        db.insert_row(conn, table_name, f.getName(), f.getAbsolutePath(), bc.creationTime().toString(), bc.size() + "");
                       // new FileSender(f.getAbsolutePath());
                        new FileTransfer(f.getAbsolutePath());
                        Thread.sleep(5000);
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    n++;
                }
            }
        } else out.print("Connection Failed");
    }
}