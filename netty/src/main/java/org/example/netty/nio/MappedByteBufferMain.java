package org.example.netty.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferMain {

    public static void main(String[] args) throws Exception {

        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/apple/Downloads/a.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());

        map.put(0, (byte) 'H');


        randomAccessFile.close();
    }
}
