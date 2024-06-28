package saisyukadai;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class quizClient extends JFrame {
    private JTextField answerField;
    private JTextArea displayArea;
    private PrintWriter out;
    private BufferedReader in;

    public quizClient() {
        // GUI Setup
        setTitle("Chat Client");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        answerField = new JTextField();
        add(answerField, BorderLayout.SOUTH);

        answerField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAnswer();
            }
        });

        setVisible(true);

        // Connect to server
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Receive question from server
            String question = in.readLine();
            displayArea.append("Question: " + question + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAnswer() {
        String answer = answerField.getText();
        out.println(answer);
        try {
            String response = in.readLine();
            displayArea.append(answer + "\n");
            displayArea.append("Response: " + response + "\n");
            if (response.equals("Correct!")) {
                answerField.setEditable(false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        answerField.setText("");
    }

    public static void main(String[] args) {
        new quizClient();
    }
}
            /* new Thread(new ServerListener(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = userInputField.getText();
        out.println(message);
        chatArea.append("Me: " + message + "\n");
        userInputField.setText("");
    }

    private class ServerListener implements Runnable {
        private BufferedReader in;

        public ServerListener(Socket socket) {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String messageFromServer;
            try {
                while ((messageFromServer = in.readLine()) != null) {
                    chatArea.append("Server: " + messageFromServer + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new quizClient();
    }
}
 */