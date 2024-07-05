package final_exam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class quiz {
    static String ans;
    static String[] q;
    static String[] c;
    static List<Integer> targetList = new ArrayList<>(Arrays.asList(0, 1, 2));
    static String[] quiz = { "Aは何でしょう？", "Bはどこでしょう？", "Cは何語でしょう？" };
    static String[] correct = { "A", "B", "C" };
    static int quizIdx = 0;
    public static void setQandCor() {
        Collections.shuffle(targetList);
        for (int i = 0; i < quiz.length; i++) {
            q[i] = quiz[targetList.get(i)];
            c[i] = correct[targetList.get(i)];
        }
    }
    public static void moveToNextQuiz() {
        quizIdx++;
    }
    public static String getAns() {
        return ans;
    }
    public static String getCor() {
        return c[quizIdx];
    }
    public static String getQuiz() {
        return q[quizIdx];
    }
}