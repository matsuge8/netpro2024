import java.util.Arrays;

public class TaskObject implements ITask {
    private int number;
    private int result;

    @Override
    public void setExecNumber(int x) {
        this.number = x;
    }

    @Override
    public void exec() {
        result = findMaxPrime(number);
    }

    @Override
    public int getResult() {
        return result;
    }

    private int findMaxPrime(int n) {
        if (n < 2) return -1;
        boolean[] isPrime = new boolean[n + 1];
        Arrays.fill(isPrime, true);
        isPrime[0] = isPrime[1] = false;

        for (int p = 2; p * p <= n; p++) {
            if (isPrime[p]) {
                for (int i = p * p; i <= n; i += p) {
                    isPrime[i] = false;
                }
            }
        }

        for (int i = n; i >= 2; i--) {
            if (isPrime[i]) {
                return i;
            }
        }

        return -1;
    }
}
