package client.gui;

import client.network.Connection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class MainWindowListeners implements ActionListener{
    private JButton sendButton;
    private JTextField messageField;
    private JTextArea textArea;
    private String message;
    private JLabel textLabel;
    Connection connection;
    public static int messageCounter = 0;

    public String getMessage() {
        return message;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JTextField getMessageField() {
        return messageField;
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public MainWindowListeners(JButton bt, JTextField mf, JTextArea ta, Connection conn, JLabel jp){
        this.messageField = mf;
        this.sendButton = bt;
        this.textArea = ta;
        this.connection = conn;
        this.textLabel = jp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sendButton){
            message = messageField.getText() + "\n";
            textArea.append(message);

            messageField.setText("");

            connection.sendMessage(message);
            messageCounter++;
            if(messageCounter > 0){
                textLabel.setText("Сообщение");
            }
        }
    }


}
