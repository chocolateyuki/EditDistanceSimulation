import java.util.ArrayList;
import java.util.Arrays;

public class WuManber {

    private ArrayList<Integer> A;
    private ArrayList<Integer> B;
    private int M;
    private int N;
    int editDistance;
    int comparisonCount = 0;

    public void runWuManberAlgorithm(ArrayList<Integer> a, ArrayList<Integer> b){
        this.A = a;
        this.B = b;
        this.M = a.size();
        this.N = b.size();
        this.editDistance = 0;
        if (this.M > this.N) {
            ArrayList<Integer> tmp = this.A;
            this.A  = this.B;
            this.B  = tmp;
        }

        compose();
    }

    public void compose () {
        int p      = -1;
        int size   = this.M + this.N + 3;
        int delta  = this.N - this.M;
        int offset = M + 1;
        int fp[]   = new int[size];
        Arrays.fill(fp, -1);
        do {
            this.comparisonCount++;
            ++p;
            for (int k=-p;k<=delta-1;++k) {
                fp[k+offset] = this.snake(k, fp[k-1+offset]+1, fp[k+1+offset]);
                this.comparisonCount++;
            }
            for (int k=delta+p;k>=delta+1;--k) {
                fp[k+offset] = this.snake(k, fp[k-1+offset]+1, fp[k+1+offset]);
                this.comparisonCount++;
            }
            fp[delta+offset] = this.snake(delta, fp[delta-1+offset]+1, fp[delta+1+offset]);
        } while(fp[delta+offset] < this.N);
        this.editDistance = delta + 2 * p;
    }

    private int snake (int k, int p, int pp) {
        int y = Math.max(p, pp);
        int x = y - k;
        while (x < M && y < N && this.A.get(x) == this.B.get(y)) {
            ++x;
            ++y;
            this.comparisonCount++;
        }
        return y;
    }

}