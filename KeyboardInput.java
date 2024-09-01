package Formiche;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private GameWindow gameWindow;

    public KeyboardInput(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //? Se viene premuto lo spazio
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(!Game.getisPaused()){
                Game.setPaused(true);
                gameWindow.showMenu();
            } else {
                Game.setPaused(false);
                gameWindow.hideMenu();
            }
        }

        //? Se viene premuto K
        if(e.getKeyCode() == KeyEvent.VK_K){
            GamePanel.spawnRandomFood = !GamePanel.spawnRandomFood;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
