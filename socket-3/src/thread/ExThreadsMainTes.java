package thread;

// Runnable インターフェースを実装することで、このクラスのインスタンスはスレッドとして実行可能になります。
public class ExThreadsMainTes implements Runnable {
    String myAlfabetStr="noalfabet";


    // main メソッドはプログラムのエントリーポイントです。
    public static void main(String[] args){
        ExThreadsMainTes[] cts = new ExThreadsMainTes[7];
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

        for(int i = 0; i < 7; i++){
            cts[i] = new ExThreadsMainTes();
            String name = daysOfWeek[i] +"_chan thread";
            cts[i].setAlfabet(name);
        }

        for(ExThreadsMainTes ct:cts){
            new Thread(ct).start();
        }

        // ct を実行する新しいスレッドを作成します。
        /* Thread th1 = new Thread(ct1);
        Thread th2 = new Thread(ct2);
        Thread th3 = new Thread(ct3); */
        // スレッドを開始します。これにより、CountTenRunnable の run メソッドが呼び出されます。
 
        /* System.out.println("th1.getName()"+th1.getName());
        th1.start();
 
        System.out.println("th2.getName()"+th2.getName());
        th2.start();
        
        System.out.println("th3.getName()"+th3.getName());
        th3.start(); */

        // この try-catch ブロックは、0 から 9 までの値を 500 ミリ秒間隔で出力するループを実行します。
        try {
            for(int i = 0; i < 10; i++) {
                System.out.println("main:i=" + i);

                // メインスレッドを 500 ミリ秒間一時停止します。
                Thread.sleep(500);  // ミリ秒単位のスリープ時間
            }
        }
        catch(InterruptedException e) {
            // スレッドが中断された場合は、例外を出力します。
            System.err.println(e);
        }
    }

    public void setAlfabet(String alfabetstr) {
        myAlfabetStr = alfabetstr;
    }

    // run メソッドは、新しいスレッドが実行するコードを含みます。
    public void run() {
        // この try-catch ブロックは、0 から 9 までの値を 1000 ミリ秒間隔で出力するループを実行します。
        try {
            for(int i = 0; i < 10; i++) {
                System.out.println(myAlfabetStr+"runnable thread:i=" + i);

                // スレッドを 1000 ミリ秒間一時停止します。
                Thread.sleep(1000);  // ミリ秒単位のスリープ時間
            }
        }
        catch(InterruptedException e) {
            // スレッドが中断された場合は、例外を出力します。
            System.err.println(e);
        }
    }
}
