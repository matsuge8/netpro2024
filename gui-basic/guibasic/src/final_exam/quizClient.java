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
import javax.swing.SwingUtilities;

public class quizClient extends JFrame {
    private JTextField answerField;
    private JTextArea displayArea;
    private PrintWriter out;
    private BufferedReader in;
    private JScrollPane scrollPane;

    public quizClient() {
        // GUI settings
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

        // Connect to server
        connectToServer();
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Receive questions from the server
            new Thread(new Runnable() {
                @Override
                public void run() {
                    receiveQuestion();
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveQuestion() {
        try {
            String question;
            while ((question = in.readLine()) != null) {
                appendToDisplayArea(question);
                if (question.equals("No more questions available.")) {
                    answerField.setEditable(false);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAnswer() {
        String answer = answerField.getText();
        out.println(answer);
        appendToDisplayArea("あなた: " + answer);
        answerField.setText("");
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

    public static void main(String[] args) {
        new quizClient();
    }
}
