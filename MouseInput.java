import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;


public class MouseInput extends MouseInputAdapter {

    Game game;

    public MouseInput(Game game) {
        this.game = game;
    }

    public void mouseClicked(MouseEvent e) {
        int mx = e.getX() / game.SCALE;
        int my = e.getY() / game.SCALE;
        System.out.println("Clicked into: (" + mx + ", " + my + ")");
        game.mouseClicked(mx, my);
    }

}
