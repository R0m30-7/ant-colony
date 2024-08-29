package Formiche;

import java.awt.Dimension;

import javax.swing.JFrame;

public class GameWindow {
    private static JFrame jFrame;
    protected Menu menu;

    public GameWindow(GamePanel gamePanel) {
        jFrame = new JFrame("Ant Colony");

        //? Aggiunto il menu al JFrame
        menu = new Menu();
        //? Aggiungo il gamePanel al JFrame
        jFrame.add(gamePanel);
        
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quando chiudo la finestra interrompe il programma
        jFrame.setResizable(true);

        jFrame.pack();
        jFrame.setVisible(true);
    }

    public static Dimension getjFrameSize(){
        return jFrame.getSize();
    }
}
