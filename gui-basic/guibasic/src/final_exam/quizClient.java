package final_exam;
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
    private JScrollPane scrollPane;

    public quizClient() {
        // GUI Setup
        setTitle("Quiz Client");
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

       // サーバーに接続
        connectToServer();
    }

    @SuppressWarnings("resource")
    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // サーバーからの質問を受け取る
            receiveQuestion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveQuestion() {
        try {
            String question = in.readLine();
            if (question.equals("No more questions available.")) {
                displayArea.append(question + "\n");
                answerField.setEditable(false);
            } else {
                displayArea.append("Question: " + question + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAnswer() {
        String answer = answerField.getText();
        out.println(answer);
        try {
            String response = in.readLine();
            displayArea.append("You: " + answer + "\n");
            displayArea.append("Response: " + response + "\n");
            if (response.equals("Correct!")) {
                receiveQuestion();
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