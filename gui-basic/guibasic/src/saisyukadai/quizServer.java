package saisyukadai;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class quizServer extends JFrame implements ActionListener {
    private static final String SERVER_HOSTNAME = "localhost";
    private static final int SERVER_PORT = 5000;

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        new quizServer().setVisible(true);
    }

    public quizServer() {
        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);

        inputField = new JTextField(25);
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);

        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(sendButton);

        add(chatScrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        connectToServer();
    }

    private void connectToServer() {
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(SERVER_HOSTNAME, SERVER_PORT);
            Socket socket = new Socket();
            socket.connect(socketAddress, 10000);

            InetAddress inadr;
            if ((inadr = socket.getInetAddress()) != null) {
                chatArea.append("Connected to " + inadr + "\n");
            } else {
                chatArea.append("Connection failed.\n");
                return;
            }

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Start a new thread to listen for messages from the server
            new Thread(new ServerListener()).start();
        } catch (IOException e) {
            chatArea.append("Error connecting to server: " + e.getMessage() + "\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = inputField.getText();
        if (message != null && !message.trim().isEmpty()) {
            writer.println(message);
            inputField.setText("");
        }
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    chatArea.append("Server: " + message + "\n");
                }
            } catch (IOException e) {
                chatArea.append("Error reading from server: " + e.getMessage() + "\n");
            }
        }
    }
}
