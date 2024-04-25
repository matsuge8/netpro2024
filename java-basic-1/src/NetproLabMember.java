import java.util.Random;

public class NetproLabMember {
    public static final int year = 15;//15年間を表す。
    public static final int a = 3;

    public static void main(String[] args){
        int[][] members = new int [year][a];
        Random random = new Random();
        double totalRatio = 1;
        for (int i = 0; i < year; i++) {
          //学生の総数
          members[i][0] = 120 + (random.nextInt(20) - 10);
          //女性の割合(%)
          members[i][1] = 20 + i;
          //岩井研の人数
          members[i][2] = 10 + (random.nextInt(6) - 3);
    
          // 男性数を求める
          int men = members[i][0] - (int) (members[i][0] * ((float) members[i][1] / 100));
          // 総数から岩井研の人数を取り出す組み合わせ
          long cpsRatio = combination(members[i][0], members[i][2]);
          // 男性の中から岩井研のメンバーを取り出す組み合わせ
          long menRatio = combination(men, members[i][2]);
          // 岩井研の人数に男性しか入らない割合
          totalRatio *= (double) menRatio / cpsRatio;
        }
        System.out.println(totalRatio);
    }

     // Combination
   public static final long combination(final int n, int r) {
    long bunbo = 1;
    long bunsi = 1;
    for(int i = 0; i < r; i++){
        bunsi *= (n-i);
    }
    for(int i = 1; i < r; i++){
        bunbo *= i;
    }
    return bunsi / bunbo;
  }

}
