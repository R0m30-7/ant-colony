package Formiche;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private static int frames = 0;
    protected static int FPSToDisplay = 0;
    private final static int FPSGoal = 60;

    private static int xLoc = 0; // ? Mi dice le coordinate del punto in
    private static int yLoc = 0; // ? alto a sinistra del gamePanel

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();
        gamePanel.addMouseMotionListener(new MouseInput()); // ? Aggiungo il mouse motion listener al gamePanel
        gamePanel.addMouseListener(new MouseInput()); // ? Aggiungo il mouse listener al gamePanel
        gamePanel.addMouseWheelListener(new MouseInput());  //? Aggiungo il wheel listener al gamePanel
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long now = System.nanoTime();
        long lastFrame = now;
        long lastCheck = System.currentTimeMillis();
        double timePerFrame = 1000000000 / FPSGoal;

        while (true) {
            // FPS counter
            now = System.nanoTime();
            if (now - lastFrame > timePerFrame) {
                gamePanel.repaint();
                frames++;
                lastFrame = now;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                // ! System.out.println("FPS: " + frames);  //!  togliere il commento se si vuole vedere il numero di FPS
                FPSToDisplay = frames;
                frames = 0;

                xLoc = (int) gamePanel.getLocationOnScreen().getX(); // ? Mi dice le coordinate del punto in alto a sinistra del gamePanel
                yLoc = (int) gamePanel.getLocationOnScreen().getY();
            }
        }
    }

    public static int getFPSGoal() {
        return FPSGoal;
    }

    public static int getxLoc() {
        return xLoc;
    }

    public static int getyLoc() {
        return yLoc;
    }
}
