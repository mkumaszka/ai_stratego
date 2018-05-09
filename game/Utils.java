package game;

public class Utils {

    public static int[][] cloneArray(final int[][] in ) {

        final int ROWS = in.length;
        final int COLS = in[0].length;
        int out[][] = new int[ROWS][COLS];

        for (int row = 0; row < ROWS; row++) {
            System.arraycopy(in[row], 0, out[row], 0, COLS);
        }
        return out;
    }

}
