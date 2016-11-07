import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{
    protected static final int MX = 400;
    protected static final int MY = 250;
    protected static final int SCALE = 3;
    private static final String TITLE = "Game of Life";
    private Board board = new Board(MX, MY, -1);


    private boolean running = false;
    private boolean paused = true;
    private Thread thread;
    private Player p;

    private BufferedImage image = new BufferedImage(MX, MY, BufferedImage.TYPE_INT_RGB);

    private void init() {
        addKeyListener(new KeyInput(this));
        addMouseListener(new MouseInput(this));
        addMouseMotionListener(new MouseTracker(this));
        p = new Player(10, 10, this);
        render();
    }

    private synchronized void start() {
        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running)
            return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    protected void keyPressed(KeyEvent e) {
        int step = 2;

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
            p.setX(p.getX() + step);
        }
        else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
            p.setX(p.getX() - step);
        }
        else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {
            p.setY(p.getY() + step);
        }
        else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {
            p.setY(p.getY() - step);
        }
        else if (key == KeyEvent.VK_SPACE) {
            paused = !paused;
        }
        else if (key == KeyEvent.VK_R) {
            board.initialize(-1);
        }

    }

    protected void keyReleased(KeyEvent e) {

    }

    protected void mouseClicked(int mx, int my) {
        board.space[mx][my].alive = true;
    }

    protected void mouseDragged(int mx, int my) {
        if (mx >= 0 && mx < MX && my >= 0 && my < MY) {
            board.space[mx][my].alive = true;
            p.move(mx, my);
        }
    }

    protected void mouseMoved(int mx, int my) {
            p.move(mx, my);
        }

    public void run() {
        init();

        long lastTime = System.nanoTime();
        final double amountOfTicks = 10.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            if (!paused) {
                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;
                if (delta >= 1) {
                    tick();
                    updates++;
                    delta--;
                }
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);
                System.out.println("Epochs passed: " + this.board.nEpochs);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        // Color the image
        for (int ix = 0; ix < MX; ix++){
            for (int iy = 0; iy < MY; iy++){
                int cAlive = board.space[ix][iy].alive ? 125 : 0;
                int cNeighbours = board.countNeighbours(ix, iy) * 125 / 8;
                int color = new Color(cNeighbours*2, cAlive + cNeighbours, 0).getRGB();
                image.setRGB(ix, iy, color);
            }
        }

        int pColor = new Color(255, 0, 0).getRGB();
        image.setRGB(p.getX(), p.getY(), pColor);

        // Evolve the board
        board.evolve();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);  // Creates 3 buffers
            return;
        }

        Graphics g = bs.getDrawGraphics();
        ///////////
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        ///////////
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setPreferredSize(new Dimension(MX * SCALE, MY * SCALE));
        game.setMaximumSize(new Dimension(MX * SCALE, MY * SCALE));
        game.setMinimumSize(new Dimension(MX * SCALE, MY * SCALE));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

}
