package InputHandlers;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import cells.CellBase;
import player.Player;
import windows.GameWindow;
import windows.WindowControler;

public class GameMouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	private GameWindow target;
	private Point pPos = null;
	private static WindowControler controler;
	
	public GameMouseInputHandler(GameWindow t)
	{
		target = t;
		
	}
	
	public static void setControler (WindowControler c) {
		controler = c;
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (controler.gameWindows.lastElement().isFinished()) {
			controler.gameWindows.lastElement().tryPress(e.getX(), e.getY());
			return;
		}
		if(WindowControler.getPlayer().getCurrentWindow() == null ||
				!WindowControler.getPlayer().isOnEdgeOfWindow(WindowControler.getPlayer().getCurrentWindow()).equals(new Point(0,0)))
			return;
		if(e.getButton() == MouseEvent.BUTTON1)
			controler.splitVerticaly(WindowControler.getPlayer().getCurrentWindow(),
										WindowControler.getPlayer().getLocation().x);
		else if(e.getButton() == MouseEvent.BUTTON3)
			controler.splitHorizontaly(WindowControler.getPlayer().getCurrentWindow(),
										WindowControler.getPlayer().getLocation().y);

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
