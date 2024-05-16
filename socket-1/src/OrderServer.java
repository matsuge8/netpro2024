import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OrderServer {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ポートを入力してください(5000など) → ");
            int port = scanner.nextInt();
            scanner.close();

            ServerSocket server = new ServerSocket(port);
            System.out.println("localhostの" + port + "番ポートで待機します");

            while (true) {
                Socket socket = server.accept();
                System.out.println("接続しました。相手の入力を待っています......");

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                OrderRequest orderRequest = (OrderRequest) ois.readObject();
                String product = orderRequest.getProduct();
                int quantity = orderRequest.getQuantity();

                System.out.println("注文を受け付けました: 商品 - " + product + ", 数量 - " + quantity);

                OrderResponse response = new OrderResponse();
                response.setConfirmation("注文が受け付けられました");
                response.setProduct(product);
                response.setQuantity(quantity);

                oos.writeObject(response);
                oos.flush();

                ois.close();
                oos.close();
                socket.close();
            }
        } catch (BindException be) {
            System.err.println("ポート番号が不正か、ポートが使用中です");
            System.err.println("別のポート番号を指定してください(6000など)");
        } catch (Exception e) {
            System.err.println("エラーが発生したのでプログラムを終了します");
            e.printStackTrace();
        }
    }

}
