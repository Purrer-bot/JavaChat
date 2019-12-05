package server;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread{
    private String clientName;
    private boolean isRunning = true;
    private Socket socket;
    private int messageCounter;
    private int ID;

    public Socket getSocket() {
        return socket;
    }

    public String getClientName() {
        return clientName;
    }

    public int getMessageCounter() {
        return messageCounter;
    }

    public int getID() {
        return ID;
    }

    Client(Socket socket, int id){
        Thread t = new Thread(this);
        this.socket = socket;
        this.messageCounter = 0;
        this.ID = id;
        t.start();
    }
    public void run(){
        while(isRunning && socket.isConnected()){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String message = Receiver.Receive(this);
            if(message.equals("***")){
                stopListen();
            }
            if(messageCounter == 0){
                this.clientName = message;
            }
            else{
                System.out.println(clientName + " " + message);
            }
            messageCounter++;
        }
    }
    public void stopListen(){
        isRunning = false;
        System.out.println("Thread is stop " + clientName);
        try {
            socket.close();
            Server.clientList.remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
