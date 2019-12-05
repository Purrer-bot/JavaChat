package client.network;

import client.constants.Config;
import client.gui.MainWindowListeners;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    Socket socket;
    boolean isRunning = true;
    MainWindowListeners mwl;
    JTextArea messageArea;
    JTextField messageField;

    public Connection() {
        try {
            socket = new Socket(Config.IP, Config.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startListener(JTextArea textArea){
        this.messageArea = textArea;
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (isRunning) {
                    try {
                        BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String message = inputStream.readLine();
                        textArea.append(message + "\n");
                        System.out.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                    if (!isRunning) {
                        break;
                    }
                }
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    public void sendMessage(String message){
        try {
            PrintWriter outputStreamWriter = new PrintWriter(socket.getOutputStream());
            message += "\n";
            outputStreamWriter.write(message);
            outputStreamWriter.flush();
            if (message.equals(Config.STOPWORD)) {
                releaseConnection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void releaseConnection() {
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
