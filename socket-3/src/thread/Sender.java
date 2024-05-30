package thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Sender {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        String message = "Hello, multicast receivers!";
        InetAddress group = null;
        int port = 8888;

        try {
            socket = new DatagramSocket();
            group = InetAddress.getByName("224.0.0.1");  // マルチキャストアドレス
            byte[] buf = message.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, group, port);

            socket.send(packet);
            System.out.println("Message sent to multicast group.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

}
