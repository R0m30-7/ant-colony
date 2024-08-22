package Formiche;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

import java.awt.MouseInfo;

/* 
 * Codici dei tasti del mouse:
 * 0 - Nessun tasto premuto
 * 1 - Tasto sinistro
 * 2 - Tasto rotella
 * 3 - Tasto destro
 */

public class MouseInput implements MouseInputListener, MouseWheelListener {

    private static int mouseCircleRadius = GamePanel.antRadius;
    private int multiplier = 1;

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        // ? Considero il tasto sinitro
        if (e.getButton() == 1) {
            int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
            int y = (int) MouseInfo.getPointerInfo().getLocation().getY();

            GamePanel.food.add(new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2, y - Game.getyLoc() - GamePanel.antRadius / 2, false));   //! Questo è provvisorio
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

        int x = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int y = (int) MouseInfo.getPointerInfo().getLocation().getY();

        GamePanel.food.add(new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2, y - Game.getyLoc() - GamePanel.antRadius / 2, false));   //! Questo è provvisorio

        // System.out.println("x mouse: " + x + ", y mouse: " + y + "\nfixed x: " + (x - Game.getxLoc()) + ", fixed y: " + (y - Game.getyLoc()) + "\nx loc: " + Game.getxLoc() + "\ny loc: " + Game.getyLoc() + "\n");
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub
        int rot = e.getWheelRotation();

        //? Il multiplier deve essere >= 1 e <= 7
        if(multiplier == 1 && rot == 1 || multiplier == 7 && rot == -1){
            return;
        }
        multiplier -= e.getWheelRotation();
        mouseCircleRadius = GamePanel.antRadius * multiplier;
    }

    public static int getMouseCircleRadius() {
        return mouseCircleRadius;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
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
}
