package Formiche;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.MouseInfo;

public class MouseInput implements MouseListener, MouseWheelListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        // Prendere le coordinate del mouse, aggiungere quelle coord come cibo

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

        int mouseX = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc() - GamePanel.antRadius / 2;
        int mouseY = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc() - GamePanel.antRadius / 2;

        GamePanel.food.add(new Punto(mouseX, mouseY, false));

        /*
         * System.out.println("x mouse: " + x + ", y mouse: " + y + "\nfixed x: " + (x -
         * Game.getxLoc()) + ", fixed y: "
         * + (y - Game.getyLoc()) + "\nx loc: " + Game.getxLoc() + "\ny loc: " +
         * Game.getyLoc() + "\n");
         */
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub
        // ! Non ho la minima idea di come funzioni sto coso quindi mi devo guardare un
        // ! tutorial
    }
}
