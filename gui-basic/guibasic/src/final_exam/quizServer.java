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

    private quiz quiz = new quiz();

    public quizServer() {
        setupGUI();
        startServer();
    }

    private void setupGUI() {
        setTitle("プレイヤー1");
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
    }

    private void startServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(5000);
                    appendToDisplayArea("サーバーを開始。接続を待っています・・・");
                    clientSocket = serverSocket.accept();
                    appendToDisplayArea("クライアントが接続しました。: " + clientSocket.getInetAddress());
                    new ClientHandler(clientSocket).start();

                    handleServerSideQuiz();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void handleServerSideQuiz() {
        quiz.resetQuiz();
        quiz.setQandCor();
        if (quiz.getQuizSentence() == null) {
            appendToDisplayArea("問題を読み込むことに失敗しました。もう一度、サーバーを立て直してください。");
            sendToClient("問題を読み込むことに失敗しました。もう一度、サーバーを立て直してください。");
        } else {
            sendQuestionToClient();
        }
    }

    private void sendAnswer() {
        String answer = answerField.getText();
        appendToDisplayArea("あなた: " + answer);
        sendToClient("対戦相手: " + answer);

        if (answer.equalsIgnoreCase(quiz.getCor())) {
            appendToDisplayArea("正解です!");
            sendToClient("対戦相手が正解しました!");
            serverScore++;
            handleNextQuestion();
        } else {
            appendToDisplayArea("不正解! もう一度考え直して.");
            sendToClient("対戦相手の回答は不正解です!");
        }

        answerField.setText("");
    }

    private void handleNextQuestion() {
        if (quiz.isLastQuestion()) {
            displayScores();
        } else {
            quiz.moveToNextQuiz();
            sendQuestionToClient();
        }
    }

    private void sendQuestionToClient() {
        String question = quiz.getQuizSentence();
        appendToDisplayArea("\n");
        appendToDisplayArea("第" + quiz.getQuizIndex() + "問");
        appendToDisplayArea("問題: " + question);
        appendToDisplayArea("（解答形式：" + quiz.getAnswerFormat() + "）");
        sendToClient("\n");
        sendToClient("第" + quiz.getQuizIndex() + "問");
        sendToClient("問題: " + question);
        sendToClient("（解答形式：" + quiz.getAnswerFormat() + "）");
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
        appendToDisplayArea("あなた: " + serverScore);
        appendToDisplayArea("相手: " + clientScore);
        sendToClient("最終スコア:");
        sendToClient("相手: " + serverScore);
        sendToClient("あなた: " + clientScore);
        if( serverScore > clientScore){
            appendToDisplayArea("あなたの勝利!!!");
            sendToClient("相手の勝利!!");
        }else if(serverScore < clientScore){
            appendToDisplayArea("相手の勝利!!");
            sendToClient("あなたの勝利!!");
        }else{
            appendToDisplayArea("同点!!");
            sendToClient("同点!!");
        }
        appendToDisplayArea("プレイしてくれてありがとう!!また遊んでね");
        sendToClient("プレイしてくれてありがとう!!また遊んでね");
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

                    if (response.equalsIgnoreCase(quiz.getCor())) {
                        appendToDisplayArea("クライアントが正解しました!");
                        sendToClient("正解です!");
                        clientScore++;
                        handleNextQuestion();
                    } else {
                        appendToDisplayArea("クライアントの回答は不正解です!");
                        sendToClient("不正解です! もう一度お試しください.");
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
