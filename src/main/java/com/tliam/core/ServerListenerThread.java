package com.tliam.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ServerListenerThread extends Thread{
    private final static Logger SERVERLISTENERLOGGER = LoggerFactory.getLogger(ServerListenerThread.class);
    private int port;
    private String webroot;
    private ServerSocket serverSocket;
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        //server
        this.serverSocket = new ServerSocket(this.port);
    }

    @Override
    public void run() {
        try {
        /*  The isBound () method of Java Socket class returns a Boolean
            value representing the binding state of the socket.
            This method would continue to return 'true' for a
            closed socket, if it was bound before being closed.
            Binds SOCKET with PORT*/
            /*
            * The isClosed () method would return a Boolean value
            * 'true' if the socket has been closed successfully.
            */
            //currently processes each incoming request like a queue (one at a time)
            while(serverSocket.isBound() && !serverSocket.isClosed()) {//client
                // config.OpenPortConnection(); simulates client connection
                Socket socket = serverSocket.accept(); // if cannot connect. stops here
                //net address of the client
                SERVERLISTENERLOGGER.info("Connection Accepted: " + socket.getInetAddress());

                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();
            }
            // serverSocket.close(); @TODO: handle closing later
        } catch (IOException e) {
            SERVERLISTENERLOGGER.info("Problem establishing socket: ", e);
        } finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    // disregard
                }
            }
        }
        // super.run();
    }
}
