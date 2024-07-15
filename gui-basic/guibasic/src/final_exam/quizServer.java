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

    private quiz q = new quiz();

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
        q.resetQuiz(); // クイズクラスの問題をシャッフルします
        q.setQandCor(); // シャッフルしたクイズを配列にセットする
        if (q.getQuiz() == null) { // クイズが読み込めなかったとき、サーバーを立て直すように表示する。
            appendToDisplayArea("問題を読み込むことに失敗しました。もう一度、サーバーを立て直してください。");
            sendToClient("問題を読み込むことに失敗しました。もう一度、サーバーを立て直してください。");
        } else {
            String question = q.getQuiz(); // 問題を取得
            appendToDisplayArea("問題: " + question);
            sendToClient("問題: " + question);
        }
    }

    private void sendAnswer() {
        String answer = answerField.getText();
        appendToDisplayArea("あなた: " + answer);
        sendToClient("対戦相手: " + answer);

        if (answer.equalsIgnoreCase(q.getCor())) {
            appendToDisplayArea("正解です!");
            sendToClient("対戦相手が正解しました!");
            serverScore++;
            if (q.isLastQuestion()) {
                displayScores();
            } else {
                q.moveToNextQuiz(); // 次の問題に移動
                String question = q.getQuiz(); // 問題を取得
                appendToDisplayArea("問題: " + question);
                sendToClient("問題: " + question);
            }

        } else {
            appendToDisplayArea("不正解! もう一度考え直して.");
            sendToClient("対戦相手の回答は不正解です!");
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
        appendToDisplayArea("最終スコア:");
        appendToDisplayArea("サーバー: " + serverScore);
        appendToDisplayArea("クライアント: " + clientScore);
        sendToClient("最終スコア:");
        sendToClient("サーバー: " + serverScore);
        sendToClient("クライアント: " + clientScore);
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
                    appendToDisplayArea("クライアント: " + response);

                    if (response.equalsIgnoreCase(q.getCor())) {
                        appendToDisplayArea("クライアントが正解しました!");
                        sendToClient("正解です!");
                        clientScore++;
                        if (q.isLastQuestion()) {
                            displayScores();
                        } else {
                            q.moveToNextQuiz();
                            String question = q.getQuiz(); // 問題を取得
                            appendToDisplayArea("問題: " + question);
                            sendToClient("問題: " + question);
                        }
                    } else {
                        appendToDisplayArea("クライアントの回答は不正解です!");
                        sendToClient("不正解です! もう一度お試しください.");
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
