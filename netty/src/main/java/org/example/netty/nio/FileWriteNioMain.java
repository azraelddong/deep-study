package org.example.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileWriteNioMain {
    public static void main(String[] args) throws Exception {

        String str = "hello world";

        // 创建输出流
        FileOutputStream os = new FileOutputStream("/Users/apple/Downloads/a1.txt");

        // 获取通道
        FileChannel fileChannel = os.getChannel();

        // 创建buffer缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 将数据放入缓冲区
        buffer.put(str.getBytes());

        // 🌈一定要反转
        buffer.flip();

        // 将buffer缓冲区数据写入到channel
        fileChannel.write(buffer);

        // 关闭输出流
        os.close();
    }
}
