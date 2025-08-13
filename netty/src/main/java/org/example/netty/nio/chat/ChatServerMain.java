package org.example.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 聊天服务端
 */
public class ChatServerMain {
    public static void main(String[] args) {
        ChatServerMain chatServer = new ChatServerMain();
        chatServer.listen();
    }


    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int port = 8899;

    public ChatServerMain() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port));

            selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 连接事件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听事件
     */
    public void listen() {
        try {
            while (true) {
                // 没有事件阻塞住
                int count = selector.select();
                if (count > 0) {
                    // 说明有事件发生
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        if (next.isAcceptable()) {  // OP_ACCEPT
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);

                            System.out.println("客户端上线，address:" + socketChannel.getRemoteAddress());
                        }

                        if (next.isReadable()) {    // OP_READ
                            read(next);
                        }

                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取消息
     */
    public void read(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int read = channel.read(buffer);

            if (read > 0) {
                String msg = new String(buffer.array());
                System.out.println("发送的消息：" + msg);

                // 转发消息
                sendOther(msg, channel);
            }

        } catch (IOException e) {
            if (channel != null) {
                try {
                    System.out.println(channel.getRemoteAddress() + " 离线了....");
                    channel.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            key.cancel();
        }
    }

    /**
     * 转发消息，排除自身
     */
    public void sendOther(String msg, SocketChannel self) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self) {
                SocketChannel socketChannel = (SocketChannel) channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

                socketChannel.write(buffer);
            }
        }
    }
}
