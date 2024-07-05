package final_exam;

public class quiza {
    String ans;
    static String cor;
    static String q;
    static final String[] quiz = { "Aは何でしょう？", "What is 1 + 1?" ,"2 + 2 = ?", "日本一高い山は？"};
    static final String[] correct = { "A", "2" ,"4", "富士山"};
    private static int quizIndex = 0;

    // public void qanda() {
    // int n = (int) Math.random();
    // cor = correct[n];
    // }
    public static void setQuestion() {
        if (quizIndex >= quiz.length){
            quizIndex = -1; //質問が尽きたことを示す
        }
    }

    /* public void setAns(String ans) {
        this.ans = ans;
    }

    public void setCor() {
        this.cor = correct[0];
    }

    public void setQuiz() {
        this.q = quiz[0];
    } */

    public void matchingAns() {
        if (ans == cor) {
            System.out.println("Correct!");
        } else {
            System.out.println("Again...");
        }
    }

    public String getAns() {
        return ans;
    }

    public static String getCor() {
        if(quizIndex != -1){
            return correct[quizIndex];
        }else{
            return "全問題クリア";
        }
    }

    public static String getQuiz() {
        if(quizIndex != -1){
            return quiz[quizIndex];
        }else{
            return "全問題クリア";
        }
    }
    public static void moveToNextQuiz(){
        quizIndex++;
        setQuestion();
    }
    public static void resetQuiz(){
        quizIndex = 0;
    }

}
