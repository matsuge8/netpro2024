// C言語では、#include に相当する
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class HowOldAreYou {
    public static void main(String[] args) { 

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			// BufferedReader というのは、データ読み込みのクラス(型)
			// クラスの変数を作るには、new を使う。

			// readLine() は、入出力エラーの可能性がある。エラー処理がないとコンパイルできない。
			//  Java では、 try{ XXXXXXXX }  catch(エラーの型 変数) { XXXXXXXXXXXXXXXXXX} と書く
		while(true){
			try {
				System.out.println("何歳ですか?（終了する場合はqまたはeを入力してください。)");
				String line = reader.readLine();

				if(line.equals("q")||line.equals("e")){
					System.out.println("終了します。");
					break;
				}//qもしくはeが入力されたら終了する。

				int age = Integer.parseInt(line);

				if(age < 0 || age >= 120){
					System.out.println("無効な年齢です。再入力してください。");
					continue;
				}//入力された数がマイナス若しくは120歳以上の場合は再入力をしてもらう。

				System.out.println("2030年のあなたの年齢は" + (age + 6) + "歳ですね。");
				int seireki = 2024 - age;
				if(seireki>=2019 && seireki<=2024){
					int n = seireki - 2018;
					System.out.println("あなたが誕生したのは令和" + n + "年ですね。");
				}
				else if(seireki>=1989 && seireki<=2018){
					int n = seireki - 1988;
					System.out.println("あなたが誕生したのは平成" + n + "年ですね。");
				}
				else if(seireki>=1926 && seireki<=1988){
					int n = seireki - 1925;
					System.out.println("あなたが誕生したのは昭和" + n + "年ですね。");
				}
				else if(seireki >= 1912 && seireki <= 1925){
					int n = seireki -1911;
					System.out.println("あなたが誕生したのは大正" + n + "年ですね。");
				}
				else if(seireki>=1905 &&seireki<=1911){
					int n = seireki - 1941;
					System.out.println("あなたが誕生したのは明治" + n + "年ですね。");
				}
	
			}
			catch(IOException e) {
				System.out.println(e);
			}
		}
	}
}

//  課題    キーボードから数字を打ち込む
//  その結果、 あなたは、???歳ですね、と画面に表示させる。
//  その後、あなたは10年後、????歳ですね、と画面に表示させる。
