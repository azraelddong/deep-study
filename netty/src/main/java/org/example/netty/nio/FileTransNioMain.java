package org.example.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileTransNioMain {

    public static void main(String[] args) throws Exception{
        FileOutputStream os = new FileOutputStream("/Users/apple/Downloads/a2.txt");

        FileInputStream is = new FileInputStream("/Users/apple/Downloads/a.txt");

        FileChannel inChannel = is.getChannel();
        FileChannel outChannel = os.getChannel();

        outChannel.transferFrom(inChannel, 0, inChannel.size());
    }
}
