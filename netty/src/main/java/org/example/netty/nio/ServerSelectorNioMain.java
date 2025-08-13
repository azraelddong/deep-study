package org.example.netty.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSelectorNioMain {
    public static void main(String[] args) throws Exception {

        // 开启服务端并绑定一个端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(7777));
        // 设置非阻塞的
        serverSocketChannel.configureBlocking(false);

        // 开启一个selector选择器
        Selector selector = Selector.open();

        // 将服务端channel通道绑定到selector选择器上，并监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("无客户端连接.....");
                continue;
            }

            // 获取selector选择器上所有通道key
            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            // 遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {  // OP_ACCEPT连接事件
                    // 客户端生成socketChannel通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    // 将socketChannel注册到selector选择器,并监听读取事件和关联一个buffer缓冲区
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                    System.out.println("有一个客户端连接，id:" + socketChannel.hashCode());
                }

                if (next.isReadable()) { // OP_READ读事件

                    // 从选择器中获取通道
                    SocketChannel channel = (SocketChannel) next.channel();

                    // 从选择器中获取缓冲区数据
                    ByteBuffer buffer = (ByteBuffer)next.attachment();

                    // 将缓冲区的数据读取到通道中
                    channel.read(buffer);

                    System.out.println("数据：" + new String(buffer.array()));
                }

                iterator.remove();
            }

        }
    }
}
