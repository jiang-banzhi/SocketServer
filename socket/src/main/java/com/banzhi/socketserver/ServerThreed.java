package com.banzhi.socketserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThreed extends Thread {
    int port = 8888;
    ServerSocket serverSocket;

    public ServerThreed(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        super.run();
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                HttpRequestHandler handler = new HttpRequestHandler(socket);
                handler.handler();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
