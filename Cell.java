/**
 * Created by maxpy on 11/3/2016.
 */
public class Cell {
    boolean alive;

    public Cell(boolean value) {
        alive = value;
    }

    public void flip() {
        this.alive = !this.alive;
    }

    public void kill() {
        this.alive = false;
    }

    public void create() {
        this.alive = true;
    }
}
