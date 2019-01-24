package com.banzhi.socketserver;

public class SocketServer {

    public static void main(String[] arg) {
        new ServerThreed(8888).start();
    }
}
