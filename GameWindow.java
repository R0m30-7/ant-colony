package Formiche;

import java.awt.Dimension;
import javax.swing.JFrame;

public class GameWindow {
    private static JFrame jFrame;
    protected static Menu menu = new Menu();

    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame("Ant Colony");

        // Aggiungo il gamePanel al JFrame
        jFrame.add(gamePanel);

        // Aggiungo il menu al JFrame, ma lo rendo invisibile
        jFrame.add(menu);
        menu.setVisible(false);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quando chiudo la finestra interrompe il programma
        jFrame.setResizable(true);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static void showMenu() {
        menu.setVisible(true);
        jFrame.revalidate();
        jFrame.repaint();
    }

    public static void hideMenu() {
        menu.setVisible(false);
        jFrame.revalidate();
        jFrame.repaint();
    }

    public static Dimension getjFrameSize() {
        return jFrame.getSize();
    }
}
