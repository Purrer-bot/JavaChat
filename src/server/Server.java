package server;

import server.config.Config;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class Server{
    private boolean isRunning;
    private ServerSocket server;
    public static ArrayDeque<Message> messageList = new ArrayDeque<>();
    public static ArrayList<Client> clientList = new ArrayList<>();
    public static int clientId = 0;

    public Server(){
        try {
            server  = new ServerSocket(Config.PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isRunning = true;
    }
    public void startServer(){
        broadcast();
        while(isRunning){
            try {
                int id = clientId;
                clientId++;
                Client client = new Client(server.accept(), id);
                clientList.add(client);
                System.out.println("CLIENT CONNECTED");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void broadcast(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                while(isRunning) {
                    if (messageList.size() > 0) {
                        Message message = messageList.pollFirst();
                        for (Client client : clientList) {
                            if (message != null) {
                                if(client.getID() != message.getId()) {
                                    try {
                                        String result = message.getClientName() + ": " + message.getMessage();
                                        PrintWriter outputStream = new PrintWriter(client.getSocket().getOutputStream());
                                        outputStream.write(result);
                                        outputStream.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        }

                    }
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }
    public void stopServer(){
        isRunning = false;
    }
}
