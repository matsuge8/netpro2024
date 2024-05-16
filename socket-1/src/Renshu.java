public class Renshu {
    // xを2倍にして返す関数
    public int doubleValue(int x) {
        return x * 2;
    }
    // 1からnまでの整数の合計値を返す関数
    public int sumUpToN(int n) {
        int sum = 0;
        for(int i = 1; i <= n; i++){
            sum += i;
        }
        return sum;
    }
    // pからqまでの整数の合計値を返す関数
    public int sumFromPtoQ(int p, int q){
        int sum1 = 0;
        if(p > q){
            return -1;
        }
        else{
            for(int i = p; i <= q; i++){
                sum1 += i;
            }
            return sum1;
        }
    }
    //配列の指定されたindexから以降の要素の合計値を返す関数
    public int sumFromArrayIndex(int[] a, int index){
        int sum2 = 0;
        if(a.length <= index){
            return -1;
        }
        else{
            for(int i = index; i < a.length; i++){
                sum2 += a[i];
            }
            return sum2;
        }
    }
    //配列aの中で最大の値を返す関数。
    public int selectMaxValue(int[] a){
        int maxValue = a[0];
        for(int i = 0; i < a.length; i++){
            if( maxValue < a[i]){
                maxValue = a[i];
            }
        }
        return maxValue;
    }
    //配列aの中で最小の値を返す関数。
    public int selectMinValue(int[] a){
        int minValue = a[0];
        for(int i = 0; i < a.length; i++){
            if( minValue > a[i]){
                minValue = a[i];
            }
        }
        return minValue;
    }
    //配列aの中で最大の値が入っている要素の位置を返す関数。
    public int selectMaxIndex(int[] a){
        int maxValue = a[0];
        int maxIndex = 0;
        for(int i = 0; i < a.length; i++){
            if(maxValue < a[i]){
                maxValue = a[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    //配列aの中で最小の値が入っている要素の位置を返す関数。
    public int selectMinIndex(int[] a){
        int minValue = a[0];
        int minIndex = 0;
        for(int i = 0; i < a.length; i++){
            if(minValue > a[i]){
                minValue = a[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    //配列のpのi番目とj番目の要素を入れ替える。
    public void swapArrayElements(int[] p, int i, int j){
        int t = 0;
        t = p[i];
        p[i] = p[j];
        p[j] = t;
    }
    //同じ長さの配列aとbの内容を入れ替える
    public boolean swapTwoArrays(int[] a, int[] b){
        int[] h = new int[a.length];
        if(a.length == b.length){
            for(int i= 0; i < a.length; i++){
                h[i] = a[i];
                a[i] = b[i];
                b[i] = h[i];
            }
            return true;
        }
        else{
            return false;
        }
    }

    //ここに続きを実装していく。

}
