import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class OrderClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ポートを入力してください(5000など) → ");
            int port = scanner.nextInt();
            scanner.nextLine(); // consume newline

            Socket socket = new Socket("localhost", port);
            System.out.println("localhostの" + port + "番ポートに接続を要求します");

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            while (true) {
                System.out.println("商品を入力してください(例: ノートパソコン) ↓");
                String product = scanner.nextLine();
                if (product.equalsIgnoreCase("quit") || product.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.println("数量を入力してください(例: 10) ↓");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // consume newline

                OrderRequest orderRequest = new OrderRequest(product, quantity);
                oos.writeObject(orderRequest);
                oos.flush();

                OrderResponse response = (OrderResponse) ois.readObject();
                System.out.println("サーバからの確認メッセージ: " + response.getConfirmation());
                System.out.println("商品: " + response.getProduct() + ", 数量: " + response.getQuantity());

                System.out.println();
            }

            ois.close();
            oos.close();
            socket.close();
            scanner.close();
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        }
    }

}
