package org.example.netty.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 聊天客户端
 */
public class ChatClientMain {
    public static void main(String[] args) {
        ChatClientMain chatClientMain = new ChatClientMain();

        // 每隔3s读取一次通道信息
        new Thread(() -> {
            while (true) {
                chatClientMain.receive();

                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            chatClientMain.send(msg);
        }
    }

    private static final int port = 8899;
    private static final String host = "127.0.0.1";
    private SocketChannel socketChannel;
    private Selector selector;
    private String username;

    public ChatClientMain() {
        try {
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            socketChannel.configureBlocking(false);
            selector = Selector.open();

            socketChannel.register(selector, SelectionKey.OP_READ);

            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(username + "is ok....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        msg = username + "说：" + msg;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        try {
            socketChannel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receive() {
        try {
            // 没有事件阻塞住
            int count = selector.select();
            if (count > 0) {
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    if (next.isReadable()) {    // OP_READ
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println("接收到消息：" + new String(buffer.array()));
                    }
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
