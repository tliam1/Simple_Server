package com.tliam.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class HttpConnectionWorkerThread extends Thread{
    private final static Logger CONNECTIONWORKERLOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {
//        super.run();
        /*
         * A program uses an input stream to read data from a source,
         * one item at a time: Reading information into a program.
         * A program uses an output stream to write data to a destination,
         * one item at time: Writing information from a program.
         */
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            // TODO : Read then write
            String htmlPage = LoadHomePage();
            /*
             * final keyword is used to indicate that a
             * variable, method, or class cannot be modified or extended.
             */
            final String CRLF = "\r\n"; // 13 and 10 in ascii
            String response =
                    "HTTP/1.1 200 OK" + CRLF + // status of the response : HTTP_VER RESPONSE_CODE RESPONSE_MESSAGE
                            "Content-Length: " + htmlPage.getBytes().length + CRLF + // HEADER
                            CRLF +
                            htmlPage +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());

        } catch (IOException e) {
            CONNECTIONWORKERLOGGER.info("CONNECTION PROCESSING FAILED, ", e);
            throw new RuntimeException(e);
        }finally {
            try {
                assert inputStream != null;
                inputStream.close();
                assert outputStream != null;
                outputStream.close();
                assert socket != null;
                socket.close();
            } catch (IOException e) {
                // disregard
            }
        }
    CONNECTIONWORKERLOGGER.info("CONNECTION PROCESSING FINISHED!");
    }

    public static String LoadHomePage() throws IOException {
        StringBuffer htmlContents = new StringBuffer();
        try (Stream<String> stream = Files.lines(Paths.get("src/main/java/com/tliam/html/HomePage.html"))) {
            stream.forEach(line -> htmlContents.append(line));
            // stream.forEach(System.out::println);
        }
        return htmlContents.toString();
    }
}
