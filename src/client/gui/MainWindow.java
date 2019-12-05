package client.gui;

import client.network.Connection;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame{

    private JTextArea messageArea;
    private JButton sendButton;
    private JTextField messageField;
    private MainWindowListeners mwl;
    private JLabel textLabel;

    public JTextField getMessageField() {
        return messageField;
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public MainWindowListeners getMwl() {
        return mwl;
    }

    public MainWindow(Connection conn){
        super("Чятек");

        messageArea = new JTextArea(10,20);
        messageArea.setEditable(false);

        JScrollPane chatScroll = new JScrollPane(messageArea);

        sendButton = new JButton("Отправить");
        messageField = new JTextField(10);
        textLabel = new JLabel("Введите ник");

        JPanel topPanel = new JPanel();
        topPanel.add(chatScroll);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(textLabel);
        bottomPanel.add(messageField);
        bottomPanel.add(sendButton);


        mwl = new MainWindowListeners(sendButton, messageField, messageArea, conn, textLabel);
        sendButton.addActionListener(mwl);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        Connection conn = new Connection();
        MainWindow mw = new MainWindow(conn);
        conn.startListener(mw.getMessageArea());
        mw.setVisible(true);
    }
}
