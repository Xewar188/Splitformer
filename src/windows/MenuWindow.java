package windows;

import InputHandlers.MenuKeyboardInputHandler;
import InputHandlers.MenuMouseInputHandler;
import buttons.LevelLabel;
import cells.CellBase;
import menuGui.InitialPanel;
import menuGui.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    private static final long serialVersionUID = 1L;
    private InitialPanel main;
    private MenuPanel gameMenu;
    private boolean isInInitialMenu = true;
    private WindowControler controller;
    public MenuWindow(Dimension size, Dimension pos, WindowControler controller) throws Exception
    {
        this.controller = controller;
        this.setUndecorated(true);
        this.setResizable(false);
        this.setSize(size.width, size.height);
        this.setLocation(pos.width, pos.height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        MenuKeyboardInputHandler.wrapWindow(this);
        main = new InitialPanel(this.getBounds(), this);

        gameMenu = new MenuPanel(this.getBounds(), this, controller);
        this.add(main);
        this.setVisible(true);
    }

    public boolean isInInitialMenu() {
        return isInInitialMenu;
    }
    public void enterGame() {
        if (isInInitialMenu) {
            this.remove(main);
            this.add(gameMenu);
            isInInitialMenu = false;
            this.repaint();
        }
    }

    public void exitGame() {
     if (!isInInitialMenu) {
         isInInitialMenu = true;
         this.remove(gameMenu);
         this.add(main);
         this.repaint();
     }
     else {
         controller.dispose();
     }
    }

    public void scroll(int wheelRotation) {
        if (!isInInitialMenu) {
            gameMenu.scroll(wheelRotation * 20);
        }
        this.repaint();
    }
}
