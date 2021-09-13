package windows;

import InputHandlers.MenuKeyboardInputHandler;
import menuGui.InitialPanel;
import menuGui.MenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class MenuWindow extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private final InitialPanel main;
    private final MenuPanel gameMenu;
    private boolean isInInitialMenu = true;
    private final WindowController controller;
    public MenuWindow(Dimension size, Dimension pos, WindowController controller) throws Exception
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
