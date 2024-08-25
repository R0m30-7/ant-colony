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
        // ? Considero il tasto sinistro
        if (e.getButton() == 1) {
            //? Il raggio massimo con cui posso spawnare il cibo è 7, se è 8 non lo devo fare
            if(multiplier >= 8){
                return;
            }
            xSpawn = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc() - GamePanel.antRadius / 2;
            ySpawn = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc() - GamePanel.antRadius / 2;

            if(multiplier == 1){
                //? Nel caso non ci sia cibo non ho bisogno di controllare se ci sono doppioni
                if(GamePanel.food.size() == 0){
                    GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
                } else {
                    //? Questo for serve per non aggiungere il cibo su delle coordinate in cui è presente già del cibo
                    for(int i = 0; i < GamePanel.food.size(); i++){
                        if(GamePanel.food.get(i).getX() == xSpawn && GamePanel.food.get(i).getY() == ySpawn){
                            break;
                        }
                        //? Non voglio più di maxFood cibo
                        if(i == GamePanel.food.size() - 1 && GamePanel.food.size() < GamePanel.maxFood){
                            GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
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
        } else if(e.getButton() == 3 && multiplier <= 8){
            // ? Rimuovo il cibo che si trova all'interno del cerchio bianco
            int xMouse = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc();
            int yMouse = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc();

            for(int i = 0; i < GamePanel.food.size(); i++){
                if(GamePanel.DistanzaFra(new Punto(GamePanel.food.get(i).getX() + (GamePanel.antRadius / 2), GamePanel.food.get(i).getY() + (GamePanel.antRadius / 2), false), new Punto(xMouse, yMouse, false)) < (getMouseCircleRadius() / 2)){
                    GamePanel.food.remove(i);
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        //? Il raggio massimo con cui posso spawnare il cibo è 7, se è 8 non lo devo fare
        if(multiplier >= 8){
            return;
        }
        xSpawn = (int) MouseInfo.getPointerInfo().getLocation().getX() - Game.getxLoc() - GamePanel.antRadius / 2;
        ySpawn = (int) MouseInfo.getPointerInfo().getLocation().getY() - Game.getyLoc() - GamePanel.antRadius / 2;

        if(multiplier == 1){
            //? Nel caso non ci sia cibo non ho bisogno di controllare se ci sono doppioni
            if(GamePanel.food.size() == 0){
                GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
            } else {
                //? Questo for serve per non aggiungere il cibo su delle coordinate in cui è presente già del cibo
                for(int i = 0; i < GamePanel.food.size(); i++){
                    if(GamePanel.food.get(i).getX() == xSpawn && GamePanel.food.get(i).getY() == ySpawn){
                        break;
                    }
                    //? Non voglio più di maxFood cibo
                    if(i == GamePanel.food.size() - 1 && GamePanel.food.size() < GamePanel.maxFood){
                        GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
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
    }
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rot = e.getWheelRotation();

        //? Il multiplier deve essere compreso tra 1 e 8
        if(multiplier == 1 && rot == 1 || multiplier == 8 && rot == -1){
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

        for(int i = 0; i < CirclesToSpawn(mult) - CirclesToSpawn(mult - 1); i++){
            if(mult == 1 && GamePanel.food.size() < GamePanel.maxFood){
                GamePanel.food.add(new Punto(xSpawn, ySpawn, false));
            } else{
                x = (int) (xSpawn + Math.cos(Math.toRadians(i * 360 / (CirclesToSpawn(mult) - CirclesToSpawn(mult - 1)))) * ((GamePanel.antRadius * mult) / 2));
                y = (int) (ySpawn + Math.sin(Math.toRadians(i * 360 / (CirclesToSpawn(mult) - CirclesToSpawn(mult - 1)))) * ((GamePanel.antRadius * mult) / 2));
                //? Questo for serve per non aggiungere il cibo su delle coordinate in cui è presente già del cibo
                if(toCheck){
                    for(int j = 0; j < GamePanel.food.size(); j++){
                        if(GamePanel.food.get(j).getX() == x && GamePanel.food.get(j).getY() == y){
                            break;
                        }
                        //? Non voglio più di maxFood cibo
                        if(j == GamePanel.food.size() - 1 && GamePanel.food.size() < GamePanel.maxFood){
                            if(x < GamePanel.panelWidth && x > 0 && y < GamePanel.panelHeight && y > 0){
                                GamePanel.food.add(new Punto(x, y, false));
                            }
                        }
                    }
                } else{
                    if(x < GamePanel.panelWidth && x > 0 && y < GamePanel.panelHeight && y > 0){
                        GamePanel.food.add(new Punto(x, y, false));
                    }
                }
            }
        }
    }

    public static int getMouseCircleRadius() {
        return mouseCircleRadius;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(getMouseCircleRadius() < 1 || getMouseCircleRadius() > 8 && multiplier > 8){
            mouseCircleRadius = GamePanel.antRadius;
            multiplier = 1;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
