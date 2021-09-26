package com.ltnet.netty.bio;

import java.io.IOException;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9090);
        socket.getOutputStream().write("Hello Server".getBytes());
        socket.getOutputStream().flush();

        byte[] bytes = new byte[1024];
        socket.getInputStream().read(bytes);
        System.out.println("Read from server : " + new String(bytes));

        socket.close();
    }
}
