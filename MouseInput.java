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
    private static int multiplier = 1;
    protected int xSpawn = 0;
    protected int ySpawn = 0;

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

        // ? Considero il tasto sinitro
        if (e.getButton() == 1) {

            xSpawn = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc() - GamePanel.antRadius / 2;
            ySpawn = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc() - GamePanel.antRadius / 2;

            SpawnCircles(multiplier);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

        /* int xMouse = (int) MouseInfo.getPointerInfo().getLocation().getX();
        int yMouse = (int) MouseInfo.getPointerInfo().getLocation().getY();

        GamePanel.food.add(new Punto(x - Game.getxLoc() - GamePanel.antRadius / 2, y - Game.getyLoc() - GamePanel.antRadius / 2, false));   //! Questo è provvisorio */

        // System.out.println("x mouse: " + x + ", y mouse: " + y + "\nfixed x: " + (x - Game.getxLoc()) + ", fixed y: " + (y - Game.getyLoc()) + "\nx loc: " + Game.getxLoc() + "\ny loc: " + Game.getyLoc() + "\n");
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // TODO Auto-generated method stub/*  */
        int rot = e.getWheelRotation();

        //? Il multiplier deve essere compreso tra 1 e 7
        if(multiplier == 1 && rot == 1 || multiplier == 7 && rot == -1){
            return;
        }
        multiplier -= e.getWheelRotation();
        mouseCircleRadius = GamePanel.antRadius * multiplier;
    }

    private int CirclesToSpawn(int mult){
        return (int) Math.pow((mult - 1) * 2 + 1, 2);
    }

    private void SpawnCircles(int mult){
        int x = 0, y = 0;
        //System.out.println("Mult: " + mult);

        if(mult == 1){
            GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
        } else{
            //? Il for spawna solo i punti di cibo più esterni del cerchio, per questo serve la ricorrenza di SpawnCircles(mult - 1)
            for(int i = 0; i < CirclesToSpawn(mult) - CirclesToSpawn(mult - 1); i++){
                x = (int) (xSpawn + Math.cos(Math.toRadians(i * 360 / (CirclesToSpawn(mult) - CirclesToSpawn(mult - 1)))) * (mouseCircleRadius / 2));
                y = (int) (ySpawn + Math.sin(Math.toRadians(i * 360 / (CirclesToSpawn(mult) - CirclesToSpawn(mult - 1)))) * (mouseCircleRadius / 2));
                GamePanel.food.add(new Punto(x, y, false));
                System.out.println("Entro con mult: " + mult + " (" + (i + 1) + ")");
            }
            SpawnCircles(--mult);
        }
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
