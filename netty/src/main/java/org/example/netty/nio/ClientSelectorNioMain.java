package org.example.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSelectorNioMain {
    public static void main(String[] args) throws Exception{

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7777);
        if (!socketChannel.connect(inetSocketAddress)) {
           while (!socketChannel.finishConnect()) {
               System.out.println("等待连接......");
           }
        }


        // 连接成功，正常发送消息
        String msg = "hello";

        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        socketChannel.write(buffer);

        System.in.read();
    }
}
