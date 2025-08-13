package org.example.netty.blockio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 阻塞io服务端
 */
public class BlockIoMain {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newCachedThreadPool();

        // 服务端ServerSocket服务
        ServerSocket serverSocket = new ServerSocket(7777);

        System.out.println("服务器连接.....");

        while (true) {
            Socket socket = serverSocket.accept();

            System.out.println("客户端已连接....");

            executorService.execute(() -> {
                System.out.println("当前线程id：" + Thread.currentThread().getId() + "，线程名称：" + Thread.currentThread().getName());
                handler(socket);
            });

        }
    }

    /**
     * 处理器
     */
    public static void handler(Socket socket) {
        try (socket) {
            // 读取
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];

            while (true) {
                System.out.println("当前线程id：" + Thread.currentThread().getId() + "，线程名称：" + Thread.currentThread().getName());
                int read = is.read(bytes);
                // 🌈结束返回-1
                if (read == -1) {
                    break;
                } else {
                    System.out.println(new String(bytes, 0, read));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
