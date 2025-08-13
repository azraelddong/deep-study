package org.example.netty.nio;

import java.nio.IntBuffer;

/**
 * 通道读写
 */
public class BasicNioMain {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(5);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i * 2);
        }

        // 🌈读写转换必要的！！！
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
    }
}
