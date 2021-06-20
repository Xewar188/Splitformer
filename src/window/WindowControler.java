package window;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Timer;

import cells.CellBase;
import metaparts.Player;
import playground.Map;
import playground.Playground;

public class WindowControler {

	public Vector<Window> windows = new Vector<Window>();
	private Map mainMap;
	private Rectangle startSize;
	private static Player player;
	private boolean inEditorMode = false;
	private int currentType = 0;
	
	private Timer repainter = new Timer(50/3, new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	 for(Window w : windows)
	    	 {
	    		 w.repaint(); 
	    	 }
	    	 if(!inEditorMode)
	    		 player.move();
	      }
	      });
	
	public static Player getPlayer() {
		return player;
	}
	
	public boolean isInEditorMode() {
		return inEditorMode;
	}
	
	public int getCurrentType() {
		return currentType;
	}
	
	public void startPainting() {
		repainter.start();
	}
	
	protected Map getMainMap() {
		return mainMap;
	}
	
	public CellBase getCell(int x, int y) {
		return mainMap.getCell(x, y);
	}
	
	public WindowControler()
	{	
		mainMap = new Map();
		startSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width/4, 
									Toolkit.getDefaultToolkit().getScreenSize().height/4,
									Toolkit.getDefaultToolkit().getScreenSize().width/2, 
									Toolkit.getDefaultToolkit().getScreenSize().height/2);
		KeyboardInputHandler.setControler(this);
		MouseInputHandler.setControler(this);
		windows.add(new Window(new Dimension(startSize.width, startSize.height),
				new Dimension(startSize.x, startSize.y), mainMap, startSize, 0, 0));
		
		player = new Player(mainMap, windows.firstElement(), this);
		
	}
	
	public void endLevel()
	{
		System.out.println("end");
	}
	
	public void dispose()
	{
		for(Window w : windows)
		{
			w.dispose();
		}
		windows.clear();
		repainter.stop();
		player.dispose();
	}
	
	public void splitVerticaly(Window w, int x)
	{
		for(int i = 0; i <= w.getBounds().height / Playground.getCellWidth();i++)
		{
			
			if(!mainMap.getCell(Math.max(Math.min((x+w.main.getPlaygroundOffset().x) / Playground.getCellWidth(), Map.COLUMNS - 1),0),
									Math.max(Math.min(i+(w.main.getPlaygroundOffset().y) / Playground.getCellHeight(), Map.ROWS - 1),0)).canBeSplit())
				return;
		}
		windows.remove(w);
		windows.add(new Window(new Dimension(x, w.getSize().height),
				new Dimension(w.getLocation().x, w.getLocation().y), mainMap, startSize, w.x, w.y));
		windows.add(new Window(new Dimension(w.getSize().width - x, w.getSize().height),
				new Dimension(w.getLocation().x + x, w.getLocation().y), mainMap, startSize, w.x + x, w.y));
		if(player.getCurrentWindow() == w)
		{
			if(windows.get(windows.size()-2).getBounds().contains(player.getCurrentOffset().x + player.getLocation().x,
																	player.getCurrentOffset().y + player.getLocation().y))
			{
				player.setMainWindow(windows.get(windows.size() - 2));
			}
			else
			{
				player.setMainWindow(windows.get(windows.size() - 1));
			}
		}
		w.dispose();
	}
	
	public void splitHorizontaly(Window w, int y)
	{
		for(int i = 0; i <= w.getBounds().width / Playground.getCellHeight();i++)
		{
			if(!mainMap.getCell(Math.max(Math.min(i+(w.main.getPlaygroundOffset().x) / Playground.getCellWidth(), Map.COLUMNS - 1),0),
					Math.max(Math.min((y+w.main.getPlaygroundOffset().y) / Playground.getCellHeight(), Map.ROWS - 1),0)).canBeSplit())
				return;
			
		}
		windows.remove(w);
		windows.add(new Window(new Dimension(w.getSize().width, y),
				new Dimension(w.getLocation().x, w.getLocation().y), mainMap, startSize, w.x, w.y));
		windows.add(new Window(new Dimension(w.getSize().width,w.getSize().height - y),
				new Dimension(w.getLocation().x, w.getLocation().y + y), mainMap, startSize, w.x, w.y + y));
		if(player.getCurrentWindow() == w)
		{
			if(windows.get(windows.size() - 2).getBounds().contains(player.getCurrentOffset().x + player.getLocation().x,
																	  player.getCurrentOffset().y + player.getLocation().y))
			{
				player.setMainWindow(windows.get(windows.size() - 2));
			}
			else
			{
				player.setMainWindow(windows.get(windows.size() - 1));
			}
		}
		w.dispose();
	}
	
	public void merge()
	{
		Window temp = new Window(new Dimension(startSize.width, startSize.height), 
				new Dimension(startSize.x,startSize.y), mainMap, startSize, 0, 0);
		if(WindowControler.player.getCurrentWindow() != null && 
				!mainMap.getCell(WindowControler.player.getCellX(), WindowControler.player.getCellY()).isTangible())
		{
			var temppos = new Point(WindowControler.player.getLocation().x + player.getCurrentWindow().main.getPlaygroundOffset().x,
									WindowControler.player.getLocation().y + player.getCurrentWindow().main.getPlaygroundOffset().y);
			player.setMainWindow(temp);
			player.setMapLocation(temppos);
		}
		else
		{
			var temppos = new Point((int)(((float)mainMap.getStartLocation().x+0.5f) * Playground.getCellWidth()),
					(int) (((float)mainMap.getStartLocation().y+0.5f))* Playground.getCellHeight());
			player.setMainWindow(temp);
			player.setMapLocation(temppos);
		}
		
		for(Window w : windows)
		{
			w.dispose();
		}
		windows.clear();
		windows.add(temp);
	}
	
	public void enterEditorMode() {
		inEditorMode = true;
		for(Window w : windows)
		{
			w.setVisible(false);
		}
		windows.add(new Window(new Dimension(startSize.width, startSize.height), 
				new Dimension(startSize.x,startSize.y), mainMap, startSize, 0, 0));
		
	}
	
	public void exitEditorMode() {
		inEditorMode = false;	
		windows.lastElement().dispose();
		windows.remove(windows.lastElement());
		for(Window w : windows)
		{
			w.setVisible(true);
		}
	}
	
	public Point getCellFromPointEditor(int x, int y)
	{
		if(windows.lastElement().contains(x,y))
			return new Point(x / Playground.getCellWidth(), 
								y / Playground.getCellHeight());
		else
			return new Point(0,0);
	}

	public void setCurrentType(int i) {
		currentType = i;
		
	}
}
