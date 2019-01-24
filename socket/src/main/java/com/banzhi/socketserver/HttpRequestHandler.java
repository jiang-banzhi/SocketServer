package com.banzhi.socketserver;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestHandler {
    Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    public void handler() throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        char[] buf = new char[1024];
        int index;
        while ((index = inputStreamReader.read(buf)) != -1) {
            sb.append(buf, 0, index);
            if (index < buf.length) {
                break;
            }
        }
        System.out.println(sb.toString());
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        String[] splits = sb.toString().split("\r\n");
        if (splits != null && splits.length > 1) {
            int mark = 1;//从第一行开始解析 第0行为 POST / HTTP/1.1
            while (splits.length > mark && splits[mark].length() > 0) {
                String[] keyVal = splits[mark].split(":");
                headers.put(keyVal[0], keyVal[1]);
                mark++;
            }
            if (splits.length - 1 == mark + 1) {
                String split = splits[mark + 1];//请求头与body间隔一行
                String[] bodys = split.split("&");
                for (String body : bodys) {
                    String[] kv = body.split("=");
                    params.put(kv[0], kv[1]);
                }
            }
        }
        socket.getOutputStream().
                write(("HTTP/1.1 200 OK\r\n" +  //响应头第一行
                        "Content-Type: text/html; charset=utf-8\r\n" +  //简单放一个头部信息
                        "\r\n" +  //这个空行是来分隔请求头与请求体的
                        "<h1>这是响应报文</h1>\r\n").getBytes());
    }
}
