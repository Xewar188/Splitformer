package window;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Timer;

import metaparts.Player;
import playground.Map;

public class WindowControler {

	Vector<Window> windows= new Vector<Window>();
	public Map mainMap;
	Rectangle startSize;
	Player player;
	Timer repainter= new Timer(50/3,new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	 for(Window w : windows)
	    	 {
	    		 w.repaint();
	    		 player.move();
	    	 }
	      }
	      });
	public WindowControler()
	{	
		mainMap=new Map();
		startSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/4,
				Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
		KeyboardInputHandler.controler=this;
		MouseInputHandler.controler=this;
		windows.add(new Window(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2)
				,new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/4)
				,mainMap,startSize,0,0));
		player=new Player(mainMap,windows.firstElement(),this,windows.firstElement().getBounds());
		
	}
	public void dispose()
	{
		for(Window w:windows)
		{
			w.dispose();
		}
		windows.clear();
		repainter.stop();
	}
	public void splitVerticaly(Window w,int x)
	{
		
		windows.remove(w);
		windows.add(new Window(new Dimension(x,w.getSize().height),new Dimension(w.getLocation().x,w.getLocation().y),mainMap,startSize,w.x,w.y));
		windows.add(new Window(new Dimension(w.getSize().width-x,w.getSize().height),new Dimension(w.getLocation().x+x,w.getLocation().y),mainMap,startSize,w.x+x,w.y));
		w.dispose();
	}
	public void splitHorizontaly(Window w,int y)
	{
		windows.remove(w);
		windows.add(new Window(new Dimension(w.getSize().width,y),new Dimension(w.getLocation().x,w.getLocation().y),mainMap,startSize,w.x,w.y));
		windows.add(new Window(new Dimension(w.getSize().width,w.getSize().height-y),new Dimension(w.getLocation().x,w.getLocation().y+y),mainMap,startSize,w.x,w.y+y));
		w.dispose();
	}
}
