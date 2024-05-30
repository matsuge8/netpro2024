package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer1 {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9877);

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            System.out.println("サーバーが起動しました。");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

                System.out.println("受信したメッセージ: " + message);

                String capitalizedMessage = message.toUpperCase();
                sendData = capitalizedMessage.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
                    receivePacket.getAddress(), receivePacket.getPort());

                serverSocket.send(sendPacket);

                System.out.println("送信したメッセージ: " + capitalizedMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
