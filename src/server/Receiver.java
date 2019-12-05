package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class Receiver {
    public static String Receive(Client client){
        String message = "";
        try {
            Socket socket = client.getSocket();
            BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            message = inputStream.readLine();

            //Build server.Message
            if(client.getMessageCounter() > 0){
                Message cMessage = new Message(client.getClientName(), message, client.getID());
                Server.messageList.addFirst(cMessage);
            }


        } catch (SocketException e){
            client.stopListen();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}
