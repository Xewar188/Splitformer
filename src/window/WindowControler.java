package window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;

public class WindowControler {

	Vector<Window> windows= new Vector<Window>();
	public WindowControler()
	{	
		KeyboardInputHandler.controler=this;
		MouseInputHandler.controler=this;
		windows.add(new Window(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2)
				,new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width/4, Toolkit.getDefaultToolkit().getScreenSize().height/4)));
		
	}
	public void dispose()
	{
		for(Window w:windows)
		{
			w.dispose();
		}
		windows.clear();
	}
	public void splitVerticaly(Window w)
	{
		
		windows.remove(w);
		windows.add(new Window(new Dimension(w.getSize().width/2,w.getSize().height),new Dimension(w.getLocation().x,w.getLocation().y)));
		windows.add(new Window(new Dimension(w.getSize().width/2,w.getSize().height),new Dimension(w.getLocation().x+w.getSize().width/2,w.getLocation().y)));
		w.dispose();
	}
	public void splitHorizontaly(Window w)
	{
		windows.remove(w);
		windows.add(new Window(new Dimension(w.getSize().width,w.getSize().height/2),new Dimension(w.getLocation().x,w.getLocation().y)));
		windows.add(new Window(new Dimension(w.getSize().width,w.getSize().height/2),new Dimension(w.getLocation().x,w.getLocation().y+w.getSize().height/2)));
		w.dispose();
	}
}
