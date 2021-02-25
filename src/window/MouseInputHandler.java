package window;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import cells.CellBase;

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
		if(controler.inEditorMode)
		{
			controler.currentType=((int) (controler.currentType+CellBase.idCompedium.length+Math.signum(e.getWheelRotation())))%CellBase.idCompedium.length;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(controler.inEditorMode)
		{
			controler.mainMap.setCell(controler.getCellPoint(e.getX(), e.getY()), CellBase.idCompedium[controler.currentType]);
		}
		else
		{
		target.setLocation(target.getLocation().x+e.getLocationOnScreen().x-pPos.x, target.getLocation().y+e.getLocationOnScreen().y-pPos.y);
		if(WindowControler.player.main==null||WindowControler.player.isOnEdgeOfWindow(WindowControler.player.main).x != 0||WindowControler.player.isOnEdgeOfWindow(WindowControler.player.main).y != 0)
		{
		WindowControler.player.correctLocation(new Point((int)-Math.signum(e.getLocationOnScreen().x-pPos.x),(int) -Math.signum(e.getLocationOnScreen().y-pPos.y)));
		}
		pPos=e.getLocationOnScreen();
		if(WindowControler.player.main==target)
		{
			WindowControler.player.frame=new Rectangle(target.getBounds());
		}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(controler.inEditorMode)
		{
			
		}
		else
		{
		if(WindowControler.player.main==null)
			return;
		if(e.getButton()==MouseEvent.BUTTON1)
			controler.splitVerticaly(WindowControler.player.main,WindowControler.player.mapLocation.x);
		else if(e.getButton()==MouseEvent.BUTTON3)
			controler.splitHorizontaly(WindowControler.player.main,WindowControler.player.mapLocation.y-WindowControler.player.height/2);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(controler.inEditorMode)
		{
			controler.mainMap.setCell(controler.getCellPoint(e.getX(), e.getY()), CellBase.idCompedium[controler.currentType]);
		}
		else
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
