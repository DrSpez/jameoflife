/**
 * Created by maxpy on 11/3/2016.
 */
public class Game {
    public static void main(String[] args) {
        int M = 50;
        begin(M, M);
    }

    public static void begin(int nx, int ny) {
        System.out.println("Game on!");
        Board board = new Board(nx, ny, -1);
        board.show();
        int N = 10;
        for (int n=0; n < N; n++) {
            System.out.println("Epoch " + (n + 1));
            board.epoch();
            board.show();
        }
    }
}
