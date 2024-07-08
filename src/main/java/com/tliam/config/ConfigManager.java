package com.tliam.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.tliam.utils.Json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
// import java.net.http.HttpConnectTimeoutException;

public class ConfigManager {
    // ALERT: NON-THREADSAFE SINGLETON
    private static ConfigManager myConfigManager;
    private static Config myCurrentConfig;
    private ConfigManager(){
    }

    /*
     * Classes that use getInstance() methods and the like are of the
     * singleton design pattern. Basically, there will only ever be one
     * instance of that particular class, and you get it with getInstance().
     */
    public static ConfigManager getInstance(){
        if(myConfigManager == null){
            myConfigManager = new ConfigManager();
        }
        return myConfigManager;
    }

    /*
     * Used to load config file by path 'filePath'
     */
    public void LoadConfigFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        /*
         * StringBuffer is a class in Java that represents a mutable sequence of characters.
         * It provides an alternative to the immutable String class, allowing you to modify
         * the contents of a string without creating a new object every time.
         */
        StringBuffer stringBuffer = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1){ // while we didnt read the end of the file
                stringBuffer.append((char) i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode config = null;
        try {
            config = Json.parse(stringBuffer.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error Parsing Config File", e);
        }
        try {
            myCurrentConfig = Json.fromJson(config, Config.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error Parsing Config File, fromJson", e);
        }
    }

    /*
     * Returns the current loaded configuration
     */
    public Config getCurrentConfig(){
        if (myCurrentConfig == null) {
            throw new HttpConfigurationException("CONFIG MANAGER: No Current Configuration Set.");
        }
        return myCurrentConfig;
    }
}
