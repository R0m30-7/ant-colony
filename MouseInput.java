package Formiche;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

import java.awt.MouseInfo;

public class MouseInput implements MouseInputListener {

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
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY();

        GamePanel.food.add(
                new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2, y - Game.getyLoc() - GamePanel.antRadius / 2,
                        false));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY();

        GamePanel.food.add(
                new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2, y - Game.getyLoc() - GamePanel.antRadius / 2,
                        false));

        /*
         * System.out.println("x mouse: " + x + ", y mouse: " + y + "\nfixed x: " + (x -
         * Game.getxLoc()) + ", fixed y: "
         * + (y - Game.getyLoc()) + "\nx loc: " + Game.getxLoc() + "\ny loc: " +
         * Game.getyLoc() + "\n");
         */
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
    }

}
