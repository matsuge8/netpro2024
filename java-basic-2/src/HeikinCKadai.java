import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class HeikinCKadai {
    public static void main(String[] args){
        ArrayList<Integer> score = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < 100; i++){
            int mathScore = random.nextInt(101);
            score.add(mathScore);
        }
        int sum = 0;
        for (int sumScore : score){
            sum += sumScore;
        }
        double average = (double) sum / score.size();
        System.out.println("平均点は：" + average);
        System.out.println("\n");
        ArrayList<Integer> passScore = new ArrayList<>();
        for(int pscore : score){
            if (pscore >= 80){
                passScore.add(pscore);
            }
        }
        Collections.sort(passScore);
        System.out.println("以下合格者の点数です。");
        for(int i = 0; i < passScore.size(); i++){
            int pscore = passScore.get(i);
            System.out.println(pscore);
        }
            }

}
