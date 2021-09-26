package com.ltnet.netty.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Handler;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            System.out.println("Waiting client connecting...");
            // blocking method
            Socket socket = serverSocket.accept();
            System.out.println("Client connected : " + socket.getLocalAddress());

            // 开启线程处理客户端请求，如果直接调用handler方法，则处理也为阻塞，无法响应新的客户端连接
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        handler(socket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    private static void handler(Socket socket) throws IOException {
        long threadId = Thread.currentThread().getId();
        System.out.println("[" + threadId + "] ready to read ...");
        byte[] bytes = new byte[1024];

        // 接收客户端的数据，阻塞方法，没有数据可读时阻塞
        int read = socket.getInputStream().read(bytes);
        if (read > -1) {
            System.out.println("[" + threadId + "] read from client : " + new String(bytes, 0, read));
        }
        
        // 回写
        socket.getOutputStream().write("Hello Client".getBytes());
        socket.getOutputStream().flush();
//        socket.close();
    }
}
