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

            int x = 0, y = 0;

        if(multiplier == 1){
            //? Nel caso non ci sia cibo non ho bisogno di controllare se ci sono doppioni
            if(GamePanel.food.size() == 0){
                GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
                //! System.out.println("Aggiunto il punto singolo");
            } else {
                //? Questo for serve per non aggiungere il cibo su delle coordinate in cui è presente già del cibo
                for(int i = 0; i < GamePanel.food.size(); i++){
                    if(GamePanel.food.get(i).getX() == xSpawn && GamePanel.food.get(i).getY() == ySpawn){
                        break;
                    }
                    if(i == GamePanel.food.size() - 1){
                        GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
                        //! System.out.println("Aggiunto il punto singolo");
                    }
                }
            }

        } else{
            //? Nel caso non ci sia cibo non ho bisogno di controllare se ci sono doppioni
            if(GamePanel.food.size() == 0){
                for(int i = 0; i < multiplier; i++){
                    SpawnCircles(multiplier - i, false);
                }
            } else{
                //? SpawnCircles() spawna solo i punti di cibo più esterni del cerchio
                for(int i = 0; i < multiplier; i++){
                    SpawnCircles(multiplier - i, true);
                }
            }
          }
        System.out.println("Dim lista: " + GamePanel.food.size());
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
        if(mult != 0){
            return (int) Math.pow((mult - 1) * 2 + 1, 2);
        } else{
            return 0;
        }
    }

    private void SpawnCircles(int mult, boolean toCheck){
        int x = 0, y = 0;
        System.out.println("Mult: " + mult);

        for(int i = 0; i < CirclesToSpawn(mult) - CirclesToSpawn(mult - 1); i++){
            x = (int) (xSpawn + Math.cos(Math.toRadians(i * 360 / (CirclesToSpawn(mult) - CirclesToSpawn(mult - 1)))) * ((GamePanel.antRadius * mult) / 2));
            y = (int) (ySpawn + Math.sin(Math.toRadians(i * 360 / (CirclesToSpawn(mult) - CirclesToSpawn(mult - 1)))) * ((GamePanel.antRadius * mult) / 2));
            //? Questo for serve per non aggiungere il cibo su delle coordinate in cui è presente già del cibo
            if(toCheck){
                for(int j = 0; j < GamePanel.food.size(); j++){
                    if(GamePanel.food.get(j).getX() == x && GamePanel.food.get(j).getY() == y){
                        break;
                    }
                    if(j == GamePanel.food.size() - 1){
                        GamePanel.food.add(new Punto(x, y, false));
                    }
                    //System.out.println(" (" + (i + 1) + ") " + mult + ", x: " + x + ", y: " + y);
                }
            } else{
                GamePanel.food.add(new Punto(x, y, false));
            }
            
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
