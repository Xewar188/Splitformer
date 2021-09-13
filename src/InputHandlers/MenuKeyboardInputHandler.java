package InputHandlers;

import windows.MenuWindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MenuKeyboardInputHandler implements KeyListener {
    private final MenuWindow target;
	public MenuKeyboardInputHandler(MenuWindow w){
        target = w;
    }

    public static void wrapWindow(MenuWindow w)
    {

        w.addKeyListener(new MenuKeyboardInputHandler(w));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            target.exitGame();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

