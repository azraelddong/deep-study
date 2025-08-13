package org.example.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReadWriteNioMain {
    public static void main(String[] args) throws Exception {

        FileInputStream is = new FileInputStream("/Users/apple/Downloads/a.txt");

        FileOutputStream os = new FileOutputStream("/Users/apple/Downloads/b.txt");

        FileChannel inChannel = is.getChannel();
        FileChannel outChannel = os.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(30);

        while (true) {

            // 🌈特别重要！！！！一定要复位，不然会一直输出0
//            buffer.clear();

            // 读取
            int read = inChannel.read(buffer);
            System.out.println("read = " + read);
            if (read == -1) {
                break;
            }

            // 缓冲区反转
            buffer.flip();

            // 写入
            outChannel.write(buffer);
        }

        is.close();
        os.close();
    }
}
