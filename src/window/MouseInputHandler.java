package window;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseInputHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

	Window target;
	Point pPos=null;
	static WindowControler controler;
	MouseInputHandler(Window t)
	{
		target=t;
		
	}
	static void wrapWindow(Window w)
	{
		MouseInputHandler h= new MouseInputHandler(w);
		w.addMouseListener(h);
		w.addMouseMotionListener(h);
		w.addMouseWheelListener(h);
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		target.setLocation(target.getLocation().x+e.getLocationOnScreen().x-pPos.x, target.getLocation().y+e.getLocationOnScreen().y-pPos.y);
		pPos=e.getLocationOnScreen();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1)
			controler.splitVerticaly(target);
		else if(e.getButton()==MouseEvent.BUTTON3)
			controler.splitHorizontaly(target);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pPos=e.getLocationOnScreen();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
