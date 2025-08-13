package org.example.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReadNioMain {
    public static void main(String[] args) throws Exception {

        File file = new File("/Users/apple/Downloads/a.txt");
        FileInputStream is = new FileInputStream(file);

        FileChannel fileChannel = is.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());

        fileChannel.read(buffer);

        buffer.flip();

        System.out.println(new String(buffer.array()));

        is.close();
    }
}
