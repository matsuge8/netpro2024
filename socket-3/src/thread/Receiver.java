package thread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Receiver {
    public static void main(String[] args) {
        MulticastSocket socket = null;
        InetAddress group = null;
        int port = 8888;

        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName("224.0.0.1");  // マルチキャストアドレス
            socket.joinGroup(group);

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            System.out.println("Waiting for multicast message...");
            socket.receive(packet);

            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Message received: " + received);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.leaveGroup(group);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket.close();
            }
        }
    }

}
