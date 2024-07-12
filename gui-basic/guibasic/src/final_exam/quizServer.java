package final_exam;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class quizServer extends JFrame {
    private JTextField answerField;
    private JTextArea displayArea;
    private PrintWriter out;
    private BufferedReader in;
    private JScrollPane scrollPane;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private int serverScore = 0;
    private int clientScore = 0;

    public quizServer() {
        // GUI画面の設定
        setTitle("Quiz Server");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        answerField = new JTextField();
        add(answerField, BorderLayout.SOUTH);

        answerField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAnswer();
            }
        });

        setVisible(true);

        // Start server
        new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        }).start();
    }

    private void startServer() {
        try {
            serverSocket = new ServerSocket(5000);
            appendToDisplayArea("サーバーを開始。接続を待っています・・・");
            clientSocket = serverSocket.accept();
            appendToDisplayArea("クライアントが接続しました。: " + clientSocket.getInetAddress());
            new ClientHandler(clientSocket).start();

            // サーバー側からクイズを開始する
            handleServerSideQuiz();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleServerSideQuiz() {
        quiz.resetQuiz();//クイズクラスの問題をシャッフルします
            if (quiz.getQuiz() == null) {
                appendToDisplayArea("これ以上問題がありません");
                sendToClient("これ以上問題がありません");
                displayScores();
                
            }
            String question = quiz.getQuiz();//問題を取得
            appendToDisplayArea("Question: " + question);
            sendToClient("Question: " + question);

            /* synchronized (this) {
                try {
                    wait(); // Wait for the server's answer
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

                // Client's turn
                synchronized (this) {
                    try {
                        wait(); // Wait for the client's answer
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } */
            
        
    }

    private void sendAnswer() {
        String answer = answerField.getText();
        appendToDisplayArea("You: " + answer);
        sendToClient("Server:" + answer);

        if (answer.equalsIgnoreCase(quiz.getCor())) {
                appendToDisplayArea("Correct!");
                sendToClient("Server answered correctly!");
                serverScore++;
                quiz.moveToNextQuiz();
                String question = quiz.getQuiz();//問題を取得
            appendToDisplayArea("Question: " + question);
            sendToClient("Question: " + question);

            } else {
                appendToDisplayArea("Incorrect! Try again.");
                sendToClient("Server answered incorrectly!");
            }

        
        answerField.setText("");
        synchronized (this) {
            notify(); // Notify the server that the answer is sent
        }
    }

    private void sendToClient(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void appendToDisplayArea(String message) {
        displayArea.append(message + "\n");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
            }
        });
    }

    private void displayScores() {
        appendToDisplayArea("Final Scores:");
        appendToDisplayArea("Server: " + serverScore);
        appendToDisplayArea("Client: " + clientScore);
        sendToClient("Final Scores:");
        sendToClient("Server: " + serverScore);
        sendToClient("Client: " + clientScore);
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String response;
                while ((response = in.readLine()) != null) {
                    appendToDisplayArea("Client: " + response);

                        if (response.equalsIgnoreCase(quiz.getCor())) {
                            appendToDisplayArea("Client answered correctly!");
                            sendToClient("Correct!");
                            clientScore++;
                            quiz.moveToNextQuiz();
                            String question = quiz.getQuiz();//問題を取得
            appendToDisplayArea("Question: " + question);
            sendToClient("Question: " + question);
                        } else {
                            appendToDisplayArea("Client answered incorrectly!");
                            sendToClient("Incorrect! Try again.");
                        }

                    synchronized (quizServer.this) {
                        quizServer.this.notify(); // Notify the server that the client's answer is received
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new quizServer();
    }
}
