import java.util.ArrayList;

public class IntegerComparator {

    int count = 0;
    private final ArrayList<Integer>  left;
    private final ArrayList<Integer>  right;
    
    /** Temporary variables. */
    private final int[] vDown;
    private final int[] vUp;
    
    public IntegerComparator(ArrayList<Integer> left, ArrayList<Integer>  right) {
        this.left = left;
        this.right = right;
        
        final int size = left.size() + right.size() + 2;
        vDown = new int[size];
        vUp   = new int[size];
    }

    /**
     * Get the {@link EditScript} object.
     * <p>
     * It is guaranteed that the objects embedded in the {@link Commands.InsertCommand
     * insert commands} come from the second sequence and that the objects
     * embedded in either the {@link Commands.DeleteCommand delete commands} or
     * {@link Commands.KeepCommand keep commands} come from the first sequence. This can
     * be important if subclassing is used for some elements in the first
     * sequence and the <code>equals</code> method is specialized.
     *
     * @return the edit script resulting from the comparison of the two
     *         sequences
     */
    public EditScript<Integer> getScript() {
        final EditScript<Integer> script = new EditScript<Integer>();
        buildScript(0, left.size(), 0, right.size(), script);
        return script;
    }

    private void buildScript(final int start1, final int end1, final int start2, final int end2,
            final EditScript<Integer> script) {
        final Snake middle = getMiddleSnake(start1, end1, start2, end2);

        if (middle == null
                || middle.getStart() == end1 && middle.getDiag() == end1 - end2
                || middle.getEnd() == start1 && middle.getDiag() == start1 - start2) {

            int i = start1;
            int j = start2;
            while (i < end1 || j < end2) {
                if (i < end1 && j < end2 && left.get(i).equals(right.get(j))) {
                    script.append(new Commands.KeepCommand<>(left.get(i)));
                    ++i;
                    ++j;
                } else {
                    if (end1 - start1 > end2 - start2) {
                        script.append(new Commands.DeleteCommand<>(left.get(i)));
                        ++i;
                    } else {
                        script.append(new Commands.InsertCommand<>(right.get(j)));
                        ++j;
                    }
                }
            }

        } else {

            buildScript(start1, middle.getStart(),
                        start2, middle.getStart() - middle.getDiag(),
                        script);
            for (int i = middle.getStart(); i < middle.getEnd(); ++i) {
                script.append(new Commands.KeepCommand<>(left.get(i)));
            }
            buildScript(middle.getEnd(), end1,
                        middle.getEnd() - middle.getDiag(), end2,
                        script);
        }

    }
    
    private Snake getMiddleSnake(int start1, int end1, int start2, int end2) {
        // Myers Algorithm
        // Initialisations
        final int m = end1 - start1;
        final int n = end2 - start2;
        if (m == 0 || n == 0) {
            return null;
        }

        final int delta  = m - n;
        final int sum    = n + m;
        final int offset = (sum % 2 == 0 ? sum : sum + 1) / 2;
        vDown[1+offset] = start1;
        vUp[1+offset]   = end1 + 1;

        for (int d = 0; d <= offset ; ++d) {
            // Down
            for (int k = -d; k <= d; k += 2) {
                // First step
                count++;
                final int i = k + offset;
                if (k == -d || k != d && vDown[i-1] < vDown[i+1]) {
                    vDown[i] = vDown[i+1];
                } else {
                    vDown[i] = vDown[i-1] + 1;
                }

                int x = vDown[i];
                int y = x - start1 + start2 - k;

                while (x < end1 && y < end2 && left.get(x).equals(right.get(y))) {
                    vDown[i] = ++x;
                    ++y;
                    count++;
                }
                // Second step
                if (delta % 2 != 0 && delta - d <= k && k <= delta + d) {
                    if (vUp[i-delta] <= vDown[i]) {
                        return buildSnake(vUp[i-delta], k + start1 - start2, end1, end2);
                    }
                }
            }

            // Up
            for (int k = delta - d; k <= delta + d; k += 2) {
                // First step
                count++;
                final int i = k + offset - delta;
                if (k == delta - d
                        || k != delta + d && vUp[i+1] <= vUp[i-1]) {
                    vUp[i] = vUp[i+1] - 1;
                } else {
                    vUp[i] = vUp[i-1];
                }

                int x = vUp[i] - 1;
                int y = x - start1 + start2 - k;
                while (x >= start1 && y >= start2
                        && left.get(x).equals(right.get(y))) {
                    vUp[i] = x--;
                    y--;
                    count++;
                }
                // Second step
                if (delta % 2 == 0 && -d <= k && k <= d ) {
                    if (vUp[i] <= vDown[i + delta]) {
                        return buildSnake(vUp[i], k + start1 - start2, end1, end2);
                    }
                }
            }
        }
        // this should not happen
        throw new RuntimeException("Internal Error");
    }

    private Snake buildSnake(final int start, final int diag, final int end1, final int end2) {
        int end = start;
        while (end - diag < end2
                && end < end1
                && left.get(end) == right.get(end - diag)) {
            ++end;
        }
        return new Snake(start, end, diag);
    }

    private static class Snake {

        private final int start;
        private final int end;
        private final int diag;

        public Snake(final int start, final int end, final int diag) {
            this.start = start;
            this.end   = end;
            this.diag  = diag;
        }

        /**
         * Get the start index of the snake.
         *
         * @return start index of the snake
         */
        public int getStart() {
            return start;
        }

        /**
         * Get the end index of the snake.
         *
         * @return end index of the snake
         */
        public int getEnd() {
            return end;
        }

        /**
         * Get the diagonal number of the snake.
         *
         * @return diagonal number of the snake
         */
        public int getDiag() {
            return diag;
        }
    }
    
}
