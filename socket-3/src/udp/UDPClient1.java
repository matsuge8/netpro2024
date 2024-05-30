package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient1 {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");

            byte[] sendData;
            byte[] receiveData = new byte[1024];

            String message = "abc";
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9877);
            clientSocket.send(sendPacket);

            System.out.println("送信したメッセージ: " + message);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("受信したメッセージ: " + modifiedMessage);

            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
