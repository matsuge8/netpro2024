package saisyukadai;

public class quiz {
    String ans;
    static String cor;
    static String q;
    String[] correct = { "A" };
    String[] quiz = { "Aは何でしょう？" };

    // public void qanda() {
    // int n = (int) Math.random();
    // cor = correct[n];
    // }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public void setCor() {
        this.cor = correct[0];
    }

    public void setQuiz() {
        this.q = quiz[0];
    }

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
        return cor;
    }

    public static String geQuiz() {
        return q;
    }

}
