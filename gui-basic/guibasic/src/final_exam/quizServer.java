package final_exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class quizServer {

    public static void main(String[] args) {
        new quizServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server started. Waiting for connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ) {
                quiz.setQandCor();
                if (quiz.getQuiz() == null) {
                    out.println("No more questions available.");
                    return;
                }
                out.println(quiz.getQuiz());
                String response;
                while ((response = in.readLine()) != null) {
                    if (response.equalsIgnoreCase(quiz.getCor())) {
                        out.println("Correct!");
                        quiz.moveToNextQuiz();
                        if (quiz.getQuiz() == null) {
                            out.println("No more questions available.");
                            break;
                        }
                        out.println(quiz.getQuiz());
                    } else {
                        out.println("Incorrect! Try again.");
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
}
