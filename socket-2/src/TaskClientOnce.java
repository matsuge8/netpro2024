import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TaskClientOnce {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(hostname, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            TaskObject task = new TaskObject();
            task.setExecNumber(100); // 例として100以下の最大素数を求める

            oos.writeObject(task);
            System.out.println("Task sent to server");

            ITask resultTask = (ITask) ois.readObject();
            int result = resultTask.getResult();
            System.out.println("Received result from server: " + result);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}