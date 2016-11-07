public class Player {
    private int x;
    private int y;
    private Game game;

    public Player(int x, int y, Game game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    public int getX() {return x;}

    public int getY() {return y;}

    public void setX(int new_x) {
        if (new_x >= 0 && new_x < game.MX) {
            x = new_x;
        }
    }

    public void setY(int new_y) {
        if (new_y >= 0 && new_y < game.MY) {
            y = new_y;
        }
    }

    public void move (int new_x, int new_y) {
        if (new_x >= 0 && new_x < game.MX && new_y >= 0 && new_y < game.MY) {
            x = new_x;
            y = new_y;
        }
    }

}
