package Formiche;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import java.awt.MouseInfo;

/* 
 * Codici dei tasti del mouse:
 * 0 - Nessun tasto premuto
 * 1 - Tasto sinistro
 * 2 - Tasto rotella
 * 3 - Tasto destro
 */

public class MouseInput implements MouseInputListener {

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        // ? Considero il tasto sinitro
        if (e.getButton() == 1) {
            int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
            int y = (int) MouseInfo.getPointerInfo().getLocation().getY();

            GamePanel.food.add(new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2,
                    y - Game.getyLoc() - GamePanel.antRadius / 2, false));
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY();

        GamePanel.food.add(
                new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2,
                        y - Game.getyLoc() - GamePanel.antRadius / 2,
                        false));

        /*
         * System.out.println("x mouse: " + x + ", y mouse: " + y + "\nfixed x: " + (x -
         * Game.getxLoc()) + ", fixed y: "
         * + (y - Game.getyLoc()) + "\nx loc: " + Game.getxLoc() + "\ny loc: " +
         * Game.getyLoc() + "\n");
         */
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
