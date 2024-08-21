package Formiche;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final static int FPSGoal = 60;

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);

        gamePanel.requestFocus();
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
        int frames = 0;

        while (true) {

            // FPS counter
            now = System.nanoTime();
            if (now - lastFrame > timePerFrame) {
                gamePanel.repaint();
                frames++;
                // System.out.println("Dio cane");
                lastFrame = now;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                // ! System.out.println("FPS: " + frames); togliere il commento se si vuole
                // ! vedere il numero di FPS
                frames = 0;
            }
        }
    }

    public static int getFPSGoal() {
        return FPSGoal;
    }
}
