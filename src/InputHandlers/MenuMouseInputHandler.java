package InputHandlers;

import windows.MenuWindow;

import java.awt.*;
import java.awt.event.*;

public class MenuMouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final MenuWindow target;
    private Point pPos = null;

    public MenuMouseInputHandler(MenuWindow t)
    {
        target = t;

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        target.scroll(e.getWheelRotation());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
            target.setLocation(target.getLocation().x + e.getLocationOnScreen().x - pPos.x,
                    target.getLocation().y + e.getLocationOnScreen().y - pPos.y);

            pPos = e.getLocationOnScreen();


    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        pPos = e.getLocationOnScreen();
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}
