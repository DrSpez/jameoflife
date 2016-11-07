import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class MouseTracker extends MouseMotionAdapter {
    Game game;

    public MouseTracker(Game game) {
        this.game = game;
    }

    public void mouseDragged(MouseEvent e) {
        saySomething("Mouse dragged", e);
        int mx = e.getX() / game.SCALE;
        int my = e.getY() / game.SCALE;
        game.mouseDragged(mx, my);
    }

    public void mouseMoved(MouseEvent e) {
        saySomething("Mouse moved", e);
        int mx = e.getX() / game.SCALE;
        int my = e.getY() / game.SCALE;
        game.mouseMoved(mx, my);
    }

    void saySomething(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription
                + " (" + e.getX() + "," + e.getY() + ")"
                + " detected on "
                + e.getComponent().getClass().getName());
    }
}
