package com.tliam.config;

import java.io.IOException;
import java.net.Socket;

public class Config {
    private int port;
    private String webroot;


    public void setPort(int port) {
        this.port = port;
    }

    public void setWebroot(String webroot) {
        this.webroot = webroot;
    }

    public int getPort() {
        return port;
    }

    public String getWebroot() {
        return webroot;
    }

    public void OpenPortConnection(){
        try {
            Socket socket = new Socket("localhost", 8080); // Use the same port as the server
            System.out.println("Connected to server");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
