package InputHandlers;

import windows.MenuWindow;
import windows.WindowControler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.function.Consumer;

public class MenuKeyboardInputHandler implements KeyListener {
    private static WindowControler controler;
    private MenuWindow target;
	public MenuKeyboardInputHandler(MenuWindow w){
        target = w;
    }

    public static void setControler (WindowControler c) {
        controler = c;

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
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    target.exitGame();
                    break;
            }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

