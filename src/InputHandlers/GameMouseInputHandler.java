package InputHandlers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import windows.GameWindow;
import windows.WindowController;

public class GameMouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	private final GameWindow target;
	private Point pPos = null;
	private static WindowController controller;
	
	public GameMouseInputHandler(GameWindow t)
	{
		target = t;
		
	}
	
	public static void setController(WindowController c) {
		controller = c;
	}
	
	public static void wrapWindow(GameWindow w)
	{
		GameMouseInputHandler h = new GameMouseInputHandler(w);
		w.addMouseListener(h);
		w.addMouseMotionListener(h);
		w.addMouseWheelListener(h);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (pPos == null)
			pPos = e.getLocationOnScreen();

		target.setLocation(target.getLocation().x + e.getLocationOnScreen().x - pPos.x,
							target.getLocation().y + e.getLocationOnScreen().y - pPos.y);

		if(WindowController.getPlayer().getCurrentWindow() == null ||
				WindowController.getPlayer().isOnEdgeOfWindow(WindowController.getPlayer().getCurrentWindow()).x != 0 ||
				WindowController.getPlayer().isOnEdgeOfWindow(WindowController.getPlayer().getCurrentWindow()).y != 0)
		{
		WindowController.getPlayer().correctLocation(new Point((int) -Math.signum(e.getLocationOnScreen().x - pPos.x),
															(int) -Math.signum(e.getLocationOnScreen().y - pPos.y)));
		}

		pPos = e.getLocationOnScreen();

		if(WindowController.getPlayer().getCurrentWindow() == target)
		{
			WindowController.getPlayer().setCurrentOffset(new Rectangle(target.getBounds()));
		}


	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (controller.gameWindows.lastElement().isFinished()) {
			controller.gameWindows.lastElement().tryPress(e.getX(), e.getY());
			return;
		}
		if(WindowController.getPlayer().getCurrentWindow() == null ||
				!WindowController.getPlayer().isOnEdgeOfWindow(WindowController.getPlayer().getCurrentWindow()).equals(new Point(0,0)))
			return;
		if(e.getButton() == MouseEvent.BUTTON1)
			controller.splitVertically(WindowController.getPlayer().getCurrentWindow(),
										WindowController.getPlayer().getLocation().x);
		else if(e.getButton() == MouseEvent.BUTTON3)
			controller.splitHorizontally(WindowController.getPlayer().getCurrentWindow(),
										WindowController.getPlayer().getLocation().y);

	}

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
