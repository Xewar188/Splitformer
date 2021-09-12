package InputHandlers;

import cells.CellBase;
import player.Player;
import windows.EditWindow;
import windows.GameWindow;
import windows.WindowControler;

import java.awt.*;
import java.awt.event.*;

public class EditMouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private EditWindow target;
    private Point pPos = null;
    private static WindowControler controler;

    public EditMouseInputHandler(EditWindow t)
    {
        target = t;

    }

    public static void setControler (WindowControler c) {
        controler = c;
    }

    public static void wrapWindow(EditWindow w)
    {
        EditMouseInputHandler h = new EditMouseInputHandler(w);
        w.addMouseListener(h);
        w.addMouseMotionListener(h);
        w.addMouseWheelListener(h);
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(target.isWriting())
            return;
        if(controler.isInEditorMode())
        {
            controler.setCurrentType(((int) (controler.getCurrentType() +
                    CellBase.getCompediumLength() +
                    Math.signum(e.getWheelRotation()))) % CellBase.getCompediumLength());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(target.isWriting())
            return;
        if (pPos == null)
            pPos = e.getLocationOnScreen();
        if(controler.isInEditorMode())
        {
            controler.getMainMap().setCell(controler.getCellFromPointEditor(e.getX(), e.getY()),
                    CellBase.getIdFromCompedium(controler.getCurrentType()));
        }
        else
        {
            target.setLocation(target.getLocation().x + e.getLocationOnScreen().x - pPos.x,
                    target.getLocation().y + e.getLocationOnScreen().y - pPos.y);

            if(WindowControler.getPlayer().getCurrentWindow() == null ||
                    WindowControler.getPlayer().isOnEdgeOfWindow(WindowControler.getPlayer().getCurrentWindow()).x != 0 ||
                    WindowControler.getPlayer().isOnEdgeOfWindow(WindowControler.getPlayer().getCurrentWindow()).y != 0)
            {
                WindowControler.getPlayer().correctLocation(new Point((int) -Math.signum(e.getLocationOnScreen().x - pPos.x),
                        (int) -Math.signum(e.getLocationOnScreen().y - pPos.y)));
            }

            pPos = e.getLocationOnScreen();

            if(WindowControler.getPlayer().getCurrentWindow() == target)
            {
                WindowControler.getPlayer().setCurrentOffset(new Rectangle(target.getBounds()));
            }

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(target.isWriting())
            return;
        if(!controler.isInEditorMode())
        {
            if(WindowControler.getPlayer().getCurrentWindow() == null ||
                    !WindowControler.getPlayer().isOnEdgeOfWindow(WindowControler.getPlayer().getCurrentWindow()).equals(new Point(0,0)))
                return;
            if(e.getButton() == MouseEvent.BUTTON1)
                controler.splitVerticaly(WindowControler.getPlayer().getCurrentWindow(),
                        WindowControler.getPlayer().getLocation().x);
            else if(e.getButton() == MouseEvent.BUTTON3)
                controler.splitHorizontaly(WindowControler.getPlayer().getCurrentWindow(),
                        WindowControler.getPlayer().getLocation().y - Player.height/2);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(target.isWriting())
            return;
        if(controler.isInEditorMode())
        {
            controler.getMainMap().setCell(controler.getCellFromPointEditor(e.getX(), e.getY()),
                    CellBase.getIdFromCompedium(controler.getCurrentType()));
        }
        else
        {
            pPos=e.getLocationOnScreen();
        }
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
