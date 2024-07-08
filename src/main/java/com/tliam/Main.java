package com.tliam;

import com.tliam.config.Config;
import com.tliam.config.ConfigManager;
import com.tliam.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * Driver class for server
 */
public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // get instance of the singleton
        ConfigManager.getInstance().LoadConfigFile("src/main/resources/http.json");
        Config config = ConfigManager.getInstance().getCurrentConfig();
        LOGGER.info("Server Starting...");
        LOGGER.info("Using Port " + config.getPort());
        LOGGER.info("Using Webroot " + config.getWebroot());
        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(config.getPort(), config.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
            // TODO : handle exceptions later
        }
    }

}