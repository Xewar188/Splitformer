package InputHandlers;

import cells.CellBase;
import menuGui.GameMenu;
import player.Player;
import windows.GameWindow;
import windows.MenuWindow;
import windows.WindowControler;

import java.awt.*;
import java.awt.event.*;

public class MenuMouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private MenuWindow target;
    private Point pPos = null;
    private static WindowControler controler;

    public MenuMouseInputHandler(MenuWindow t)
    {
        target = t;

    }

    public static void setControler(WindowControler c) {
        controler = c;
    }

    public static void wrapWindow(MenuWindow w)
    {
        MenuMouseInputHandler h = new MenuMouseInputHandler(w);
        w.addMouseListener(h);
        w.addMouseMotionListener(h);
        w.addMouseWheelListener(h);
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
