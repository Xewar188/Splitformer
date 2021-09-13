package InputHandlers;

import menuGui.GameMenuPanel;
import windows.MenuWindow;

import java.awt.event.*;

public class ButtonMouseInputHandler extends MenuMouseInputHandler{
    private GameMenuPanel target;
    private MouseListener[] windowListeners;
    public ButtonMouseInputHandler(GameMenuPanel t, MouseListener[] mouseListeners, MenuWindow w) {
        super(w);
        target = t;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            target.press(e.getX(), e.getY());
        }
        super.mouseClicked(e);
    }
}
