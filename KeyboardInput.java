package Formiche;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //? Se viene premuto lo spazio
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(!Game.getisPaused()){
                Game.setPaused(true);
            } else {
                Game.setPaused(false);
            }
        }

        //? Se viene premuto K
        if(e.getKeyCode() == KeyEvent.VK_K){
            if(!GamePanel.spawnRandomFood){
                GamePanel.spawnRandomFood = true;
            } else {
                GamePanel.spawnRandomFood = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
