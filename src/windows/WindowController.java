package windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Timer;

import InputHandlers.*;
import cells.CellBase;
import player.Player;
import playground.Map;
import playground.Playground;

public class WindowController {

	private MenuWindow menuWindow;
	public Vector<GameWindow> gameWindows = new Vector<>();
	private EditWindow editWindow;
	private Map mainMap;
	private final Rectangle startSize;
	private static Player player;
	private boolean inEditorMode = false;
	private static int currentType = 0;
	private boolean canBeEdited = false;

	public WindowController()
	{
		startSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize().width/4, 
									Toolkit.getDefaultToolkit().getScreenSize().height/4,
									Toolkit.getDefaultToolkit().getScreenSize().width/2, 
									Toolkit.getDefaultToolkit().getScreenSize().height/2);
		GameKeyboardInputHandler.setController(this);
		GameMouseInputHandler.setController(this);
		EditMouseInputHandler.setController(this);
		EditKeyboradInputHandler.setControler(this);

		try {
			menuWindow = new MenuWindow(new Dimension(startSize.width, startSize.height),
					new Dimension(startSize.x, startSize.y), this);
		} catch (Exception e) {
			System.exit(1);
		}
		CellBase.fillCompedium();
	}
	
	private final Timer repainter = new Timer(50/3, new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	    	 for (GameWindow w : gameWindows)
	    	 {
	    		 w.repaint(); 
	    	 }
	    	 if (!inEditorMode && !gameWindows.lastElement().isFinished())
	    		 player.move();
	    	 else if (editWindow != null)
	    	 	editWindow.repaint();
	      }
	      });

	public void startEditing(Map base) {
		canBeEdited = true;
		mainMap = base;
		menuWindow.setVisible(false);
		enterEditorMode();
		startPainting();
	}

	public void endEditing() {
		canBeEdited = false;
		menuWindow.dispose();
		try {
			menuWindow = new MenuWindow(new Dimension(startSize.width, startSize.height),
					new Dimension(startSize.x, startSize.y), this);
			menuWindow.enterGame();
		} catch (Exception e) {
			System.exit(1);
		}

		if (player != null)
			player.dispose();
		player = null;

		for(GameWindow w : gameWindows)
		{
			w.dispose();
		}
		gameWindows.clear();
		if (editWindow != null)
			editWindow.dispose();
		repainter.stop();
	}

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
	
	public Map getMainMap() {
		return mainMap;
	}
	
	public CellBase getCell(int x, int y) {
		return mainMap.getCell(x, y);
	}

	public void startLevel(Map level) {
		mainMap = level;

		gameWindows.add(new GameWindow(new Dimension(startSize.width, startSize.height),
				new Dimension(startSize.x, startSize.y), this, startSize, 0, 0));

		player = new Player(mainMap, gameWindows.firstElement(), this);

		menuWindow.setVisible(false);
		repainter.start();
	}

	public void returnToMenu() {

		for(GameWindow w : gameWindows)
		{
			w.dispose();
		}
		gameWindows.clear();
		repainter.stop();
		if (player != null)
			player.dispose();
		menuWindow.setVisible(true);
	}

	public void endLevel()
	{
		if (canBeEdited)
			return;
		if (!gameWindows.lastElement().isFinished()) {
			merge();
			gameWindows.lastElement().finish();
		}
	}


	public void completeLevel() {
		returnToMenu();
	}

	public void dispose()
	{
		for(GameWindow w : gameWindows)
		{
			w.dispose();
		}
		menuWindow.dispose();
		gameWindows.clear();
		if (editWindow != null)
			editWindow.dispose();
		repainter.stop();
		if (player != null)
			player.dispose();
	}
	
	public void splitVertically(GameWindow w, int x)
	{
		for(int i = 0; i <= w.getBounds().height / Playground.getCellWidth();i++)
		{
			
			if(!mainMap.getCell(Math.max(Math.min((x+w.main.getPlaygroundOffset().x) / Playground.getCellWidth(), Map.COLUMNS - 1),0),
									Math.max(Math.min(i+(w.main.getPlaygroundOffset().y) / Playground.getCellHeight(), Map.ROWS - 1),0)).canBeSplit())
				return;
		}
		gameWindows.remove(w);
		gameWindows.add(new GameWindow(new Dimension(x, w.getSize().height),
				new Dimension(w.getLocation().x, w.getLocation().y), this, startSize, w.x, w.y));
		gameWindows.add(new GameWindow(new Dimension(w.getSize().width - x, w.getSize().height),
				new Dimension(w.getLocation().x + x, w.getLocation().y), this, startSize, w.x + x, w.y));
		choosePlayerWindow(w);
		w.dispose();
	}
	
	public void splitHorizontally(GameWindow w, int y)
	{
		for (int i = 0; i <= w.getBounds().width / Playground.getCellWidth();i++)
		{
			if (!mainMap.getCell(Math.max(Math.min(i + (w.main.getPlaygroundOffset().x) / Playground.getCellWidth(), Map.COLUMNS - 1),0),
					Math.max(Math.min((y+w.main.getPlaygroundOffset().y) / Playground.getCellHeight(), Map.ROWS - 1),0)).canBeSplit())
				return;

		}
		gameWindows.remove(w);
		gameWindows.add(new GameWindow(new Dimension(w.getSize().width, y),
				new Dimension(w.getLocation().x, w.getLocation().y), this, startSize, w.x, w.y));
		gameWindows.add(new GameWindow(new Dimension(w.getSize().width,w.getSize().height - y),
				new Dimension(w.getLocation().x, w.getLocation().y + y), this, startSize, w.x, w.y + y));
		choosePlayerWindow(w);
		w.dispose();
	}

	private void choosePlayerWindow(GameWindow w) {
		if (player.getCurrentWindow() == w)
		{
			if(gameWindows.get(gameWindows.size() - 2).getBounds().contains(player.getCurrentOffset().x + player.getLocation().x,
					player.getCurrentOffset().y + player.getLocation().y))
			{
				player.setMainWindow(gameWindows.get(gameWindows.size() - 2));
			}
			else
			{
				player.setMainWindow(gameWindows.get(gameWindows.size() - 1));
			}
		}
	}

	public void merge()
	{
		GameWindow temp = new GameWindow(new Dimension(startSize.width, startSize.height), 
				new Dimension(startSize.x,startSize.y), this, startSize, 0, 0);
		if (WindowController.player.getCurrentWindow() != null &&
				!mainMap.getCell(WindowController.player.getCellX(), WindowController.player.getCellY()).isTangible())
		{
			var temppos = new Point(WindowController.player.getLocation().x + player.getCurrentWindow().main.getPlaygroundOffset().x,
									WindowController.player.getLocation().y + player.getCurrentWindow().main.getPlaygroundOffset().y);
			player.setMainWindow(temp);
			player.setMapLocation(temppos);
		}
		else
		{
			var temppos = new Point((int)(((float)mainMap.getStartLocation().x + 0.5f) * Playground.getCellWidth()),
					(int) (((float)mainMap.getStartLocation().y + 0.5f))* Playground.getCellHeight());
			player.setMainWindow(temp);
			player.setMapLocation(temppos);
		}
		
		for(GameWindow w : gameWindows)
		{
			w.dispose();
		}
		gameWindows.clear();
		gameWindows.add(temp);
	}
	
	public void enterEditorMode() {
		if (!canBeEdited)
			return;
		inEditorMode = true;
		for(GameWindow w : gameWindows)
		{
			w.dispose();
		}
		gameWindows.clear();
		editWindow = new EditWindow(new Dimension(startSize.width, startSize.height),
				new Dimension(startSize.x,startSize.y), this, startSize, 0, 0);
		if (player != null)
			player.dispose();
		player = null;
	}
	
	public void exitEditorMode() {
		inEditorMode = false;
		editWindow.dispose();
		gameWindows.add(new GameWindow(new Dimension(startSize.width, startSize.height),
				new Dimension(startSize.x, startSize.y), this, startSize, 0, 0));

		player = new Player(mainMap, gameWindows.firstElement(), this);
	}
	
	public Point getCellFromPointEditor(int x, int y)
	{
		if(editWindow.contains(x,y))
			return new Point(x / Playground.getCellWidth(), 
								y / Playground.getCellHeight());
		else
			return new Point(0,0);
	}

	public void setCurrentType(int i) {
		currentType = i;
	}

	public static Color getCurrentColor() {
		return CellBase.getByID(CellBase.getIdFromCompedium(currentType)).getColor();
	}

}
