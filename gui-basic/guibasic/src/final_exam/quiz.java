package final_exam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class quiz {
    private String[] quiz = new String[10];
    private String[] correct = new String[10];
    private String[] format = new String[10];
    private List<Integer> targetList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
    private String[] quizSentence = {   
        "ドラえもんに登場するスネ夫の名字は？",
        "ドラえもんの好物はどらやき、では妹のドラミちゃんの好物は？",
        "２００９年８月２１日に最初の公判がおこなわれた、特定の刑事裁判において、国民から選ばれた人と裁判官とともに審理に参加する日本の司法制度を何制度と言う？",
        "将棋に使う駒の数は敵味方合わせていくつでしょう？",
        "野球で１試合に一人でヒット、２塁打、３塁打、ホームランを全て打つことを特に何と言うでしょう？",
        "宇多田ヒカルのニックネームはヒッキー、では倉木麻衣のニックネームはなんでしょう？",
        "エッセイも人気だったが、ガンで５３歳で亡くなったちびまるこちゃんの作者といえばだれ？",
        "直訳すると「ペラペラ円盤」。パソコンでデータを保存する時に使うものといえば何?",
        "硬式テニスで０点のことを２文字で何と言うでしょう？",
        "バレーボールで攻撃をすることが出来ない守備専門のプレーヤーを何と言うでしょう?" 
    };
    private String[] correctAnswer = { 
        "骨川", "メロンパン", "裁判員制度", "40", "サイクルヒット", 
        "クッキー", "さくらももこ", "フロッピーディスク", "ラブ", "リベロ" 
    };
    private String[] answerFormat = { 
        "漢字", "カタカナ", "漢字", "半角数字", "カタカナ",
        "カタカナ", "ひらがな", "カタカナ", "カタカナ", "カタカナ"
    };
    private int quizIdx = 0;

    public void setQandCor() {
        for (int i = 0; i < quizSentence.length; i++) {
            quiz[i] = quizSentence[targetList.get(i)];
            correct[i] = correctAnswer[targetList.get(i)];
            format[i] = answerFormat[targetList.get(i)];
        }
    }

    public void resetQuiz() {
        Collections.shuffle(targetList);
        quizIdx = 0;
    }

    public void moveToNextQuiz() {
        quizIdx++;
    }

    public String getCor() {
        return correct[quizIdx];
    }

    public String getQuizSentence() {
        return quiz[quizIdx];
    }

    public String getAnswerFormat(){
        return format[quizIdx];
    }

    public int getQuizLength() {
        return quizSentence.length;
    }

    public boolean isLastQuestion() {
        return quizIdx >= quizSentence.length - 1;
    }

    public int getQuizIndex(){
        return quizIdx + 1;
    }
}
