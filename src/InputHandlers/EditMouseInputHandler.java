package InputHandlers;

import cells.CellBase;
import player.Player;
import windows.EditWindow;
import windows.WindowController;

import java.awt.*;
import java.awt.event.*;

public class EditMouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final EditWindow target;
    private Point pPos = null;
    private static WindowController controller;

    public EditMouseInputHandler(EditWindow t)
    {
        target = t;

    }

    public static void setController(WindowController c) {
        controller = c;
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
        if(controller.isInEditorMode())
        {
            controller.setCurrentType(((int) (controller.getCurrentType() +
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
        controller.getMainMap().setCell(controller.getCellFromPointEditor(e.getX(), e.getY()),
                CellBase.getIdFromCompedium(controller.getCurrentType()));

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(target.isWriting())
            return;
        if(!controller.isInEditorMode())
        {
            if(WindowController.getPlayer().getCurrentWindow() == null ||
                    !WindowController.getPlayer().isOnEdgeOfWindow(WindowController.getPlayer().getCurrentWindow()).equals(new Point(0,0)))
                return;
            if(e.getButton() == MouseEvent.BUTTON1)
                controller.splitVertically(WindowController.getPlayer().getCurrentWindow(),
                        WindowController.getPlayer().getLocation().x);
            else if(e.getButton() == MouseEvent.BUTTON3)
                controller.splitHorizontally(WindowController.getPlayer().getCurrentWindow(),
                        WindowController.getPlayer().getLocation().y - Player.height/2);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(target.isWriting())
            return;
        if(controller.isInEditorMode())
        {
            controller.getMainMap().setCell(controller.getCellFromPointEditor(e.getX(), e.getY()),
                    CellBase.getIdFromCompedium(controller.getCurrentType()));
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
