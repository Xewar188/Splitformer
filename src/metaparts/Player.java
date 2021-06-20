package metaparts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import cells.Goal;
import playground.Map;
import playground.Playground;
import window.Window;
import window.WindowControler;

public class Player {
	private Rectangle currentOffset = null;
	private Point mapLocation;
	private Point mapCell;
	public static final int width = 14;
	public static final int height = 25;
	private Window currentWindow;
	private WindowControler controler;
	private Point2D velocity = new Point2D.Double(0,0);
	private float velBuffer = 0;
	private PhysicalPlayer doll;
	
	public Point getLocation() {
		return new Point(mapLocation);
	}
	
	public Point2D getVelocity() {
		return new Point2D.Double(velocity.getX(), velocity.getY());
	}
	
	public void setVelocity(double x, double y) {
		velocity.setLocation(x, y);
	}
	
	public Window getCurrentWindow() {
		return currentWindow;
	}
	
	public Rectangle getCurrentOffset() {
		return new Rectangle(currentOffset);
	}
	
	public void setCurrentOffset(Rectangle toSet) {
		currentOffset = new Rectangle(toSet);
	}
	
	public Player(Map m, Window w, WindowControler wc) {
		controler = wc;
		setCell(m.getStartLocation().x, m.getEndLocation().y);
		mapLocation=new Point((int) (((float)mapCell.x + 0.5f) * Playground.getCellWidth()),
								(int) (((float)mapCell.y + 0.5f))* Playground.getCellHeight());
		
		doll = new PhysicalPlayer(mapLocation.x - width/2, mapLocation.y - height/2);
		setMainWindow(w);
		
	}
	public void draw(Graphics2D g, Window f) {
		if (currentWindow == null || !isOnEdgeOfWindow(currentWindow).equals(new Point(0,0))) {
			if (!doll.isVisible())
				doll.setVisible(true);
		}
		else {
			if (doll.isVisible())
				doll.setVisible(false);
		}
		if ( currentWindow != null && ( f == currentWindow || !f.getBounds().intersects(currentWindow.getBounds())))
		{
			g.setColor(Color.black);
			g.fillRect(mapLocation.x - width/2 + currentOffset.x - f.getBounds().x, 
						mapLocation.y - height/4 + currentOffset.y - f.getBounds().y, width, height * 3 / 4);
			g.setColor(Color.PINK.brighter());
			g.fillRect(mapLocation.x-width/2 + currentOffset.x - f.getBounds().x,
						mapLocation.y-height/2 + currentOffset.y - f.getBounds().y, width, height / 4);
		}
		
	}
	
	public void dispose() {
		doll.dispose();
	}
	
	public void setCell(int x, int y) {
		mapCell = new Point(x,y);
		if (controler.getCell(Math.max(Math.min(x, Map.COLUMNS - 1),0),
								Math.max(Math.min(y, Map.ROWS - 1),0)) instanceof Goal)
		{
			controler.endLevel();
		}
	}
	public int getCellX() {
		return mapCell.x;
	}
	public int getCellY() {
		return mapCell.y;
	}
	public void setMapLocation(Point pos) {
		mapLocation.setLocation(pos);
	}
	public void setMainWindow(Window w) {
		if(currentOffset != null) {
			mapLocation.x += currentOffset.x - w.getBounds().x;
			mapLocation.y += currentOffset.y - w.getBounds().y;
			int i, j;
			i = (mapLocation.x + w.main.getPlaygroundOffset().x) / Playground.getCellWidth();
			j = (mapLocation.y + w.main.getPlaygroundOffset().y) / Playground.getCellHeight();
			
			if(mapLocation.x < 0)
				i--;
			if(mapLocation.y < 0)
				j--;
			
			setCell(i,j);
		}
		
		currentWindow = w;
		currentOffset = new Rectangle(currentWindow.getBounds());
	}
	
	public boolean isTouchingCellY(int x, int y) {
		return isTouchingCellOfWindowY(x, y, mapCell, currentWindow);
	}
	
	public boolean isTouchingCellX(int x, int y) {
		return isTouchingCellOfWindowX(x, y, mapCell, currentWindow);
	}
	
	public boolean isTouchingCellOfWindowX(int x, int y, Point pos, Window toCheck) {
		if (Math.abs(y) <= 1 && Math.abs(x) == 1) {
			boolean toReturn = toCheck.main.isInFrame(pos.x + x, pos.y + y) && 
			controler.getCell(pos.x + x, pos.y + y).isTangible() &&
			(getSidePositionX(x) + currentOffset.x - toCheck.getBounds().x) * x >= ((pos.x + (1 + x) / 2) * Playground.getCellWidth() - toCheck.main.getPlaygroundOffset().x) * x;
			
			if (y == -1)
				toReturn = toReturn && getSidePositionY(-1) + toCheck.main.getPlaygroundOffset().y + currentOffset.y - toCheck.getBounds().y < pos.y * Playground.getCellHeight();
			
			if (y == 1) {
				toReturn = toReturn && getSidePositionY(1) + toCheck.main.getPlaygroundOffset().y + currentOffset.y - toCheck.getBounds().y > (pos.y+1) * Playground.getCellHeight();
			}
				return toReturn;
		}
		return false;
	}
	
	public boolean isTouchingCellOfWindowY(int x, int y, Point pos, Window toCheck) {
		if (Math.abs(x) <= 1 && Math.abs(y) == 1) {
			boolean toReturn = toCheck.main.isInFrame(pos.x + x, pos.y + y) && 
			controler.getCell(pos.x + x, pos.y + y).isTangible() &&
			(getSidePositionY(y) + currentOffset.y - toCheck.getBounds().y ) * y  >= ((pos.y + (1 + y) / 2) * Playground.getCellHeight() - toCheck.main.getPlaygroundOffset().y) * y;
			
			if (x == -1)
				toReturn = toReturn && getSidePositionX(-1) + toCheck.main.getPlaygroundOffset().x + currentOffset.x - toCheck.getBounds().x < pos.x * Playground.getCellWidth();
			
			if (x == 1)
				toReturn = toReturn && getSidePositionX(1) + toCheck.main.getPlaygroundOffset().x + currentOffset.x - toCheck.getBounds().x > (pos.x+1) * Playground.getCellWidth();
			return toReturn;
		}
		return false;
	}
	private boolean isItTouchingSide(Point p) {
		if(currentWindow == null)
			return false;
		
		
		return isItTouchingSide(p, currentWindow);
	}
	private boolean isItTouchingSide(Point p,Window w) {
		int x = (int) Math.signum(p.getX());
		int y = (int) Math.signum(p.getY());
		boolean test = false;
		Point tempCell = new Point();
		tempCell.setLocation((mapLocation.x + currentOffset.x - w.getBounds().x + w.main.getPlaygroundOffset().x) / Playground.getCellWidth(),
								(mapLocation.y + currentOffset.y - w.getBounds().y + w.main.getPlaygroundOffset().y) / Playground.getCellHeight());
		
		
		if (mapLocation.x + currentOffset.x - w.getBounds().x < 0)
			tempCell.x--;
		if (mapLocation.y + currentOffset.y - w.getBounds().y < 0)
			tempCell.y--;

		if (mapLocation.x + currentOffset.x - w.getBounds().x > w.getBounds().width)
			tempCell.x++;
		if (mapLocation.y + currentOffset.y - w.getBounds().y > w.getBounds().height)
			tempCell.y++;
		
		if (p.y != 0) {
			test = isTouchingCellOfWindowY(0, y, tempCell, w);
			
			test = test || isTouchingCellOfWindowY(1, y, tempCell, w);
			
			test = test || isTouchingCellOfWindowY(-1, y, tempCell, w);
				
			if(!test && isOnEdgeOfWindow(w).y == 0 && isOnEdgeOfWindow(w).x != 0) {
			
				//tempCell.x-=isOnEdgeOfWindow(w).x;
				test = isTouchingCellOfWindowY(0, y, tempCell, w);
				
				test = test || isTouchingCellOfWindowY(1, y, tempCell, w);
				
				test = test || isTouchingCellOfWindowY(-1, y, tempCell, w);
			}
		}
		else if (p.x != 0) {
				test = isTouchingCellOfWindowX(x, 0, tempCell, w);
				
				test = test || isTouchingCellOfWindowX(x, 1, tempCell, w);
				
				test = test || isTouchingCellOfWindowX(x, -1, tempCell, w);
				
				if (!test && isOnEdgeOfWindow(w).x == 0 && isOnEdgeOfWindow(w).y != 0) {
					
					//tempCell.y-=isOnEdgeOfWindow(w).y;
					test = isTouchingCellOfWindowX(x, 0, tempCell, w);
					
					test = test || isTouchingCellOfWindowX(x, 1, tempCell, w);
					
					test = test || isTouchingCellOfWindowX(x, -1, tempCell, w);
				}		
			}
		return test;
	}
	public void jump() {
		
		boolean test = isItTouchingSide(new Point(0,1));
		if(!test) {
			for(Window w : controler.windows) {
				if(isItTouchingSide(new Point(0,1), w)) {
					velocity.setLocation(velocity.getX(), -5);
					velBuffer = 5;
				}
					break;
			}
		}
		if(test)
		{
			velocity.setLocation(velocity.getX(), -5);
			velBuffer = 5;
		}
	}
	
	public void endJump() {
		velBuffer = 0;
	}
	
	private void correctSideX(int xToCorrect) {
		int x = (int) Math.signum(xToCorrect);
		int modifier = 0;
		
		if (currentWindow != null) {
			if (mapLocation.x + currentOffset.x - currentWindow.getBounds().x < 0)
				modifier = -1;
	
			if (mapLocation.x + currentOffset.x - currentWindow.getBounds().x > currentWindow.getBounds().width)
				modifier = 1;
		}
		
		if(isItTouchingSide(new Point(x,0))) {
			mapLocation.x = Math.min(Math.max((mapCell.x + modifier + (1 + x) / 2) * Playground.getCellWidth()- currentWindow.main.getPlaygroundOffset().x ,0),currentOffset.width)- x * width/2;
		}
		else if(currentWindow == null && mapLocation.x + currentOffset.x < 0) {
			mapLocation.x = Toolkit.getDefaultToolkit().getScreenSize().width - currentOffset.x;
		}
		else if(currentWindow == null && mapLocation.x + currentOffset.x > Toolkit.getDefaultToolkit().getScreenSize().width) {
			mapLocation.x = -currentOffset.x;
		}
		if(currentWindow != null && (isOnEdgeOfWindow(currentWindow).x != 0 || isOnEdgeOfWindow(currentWindow).y != 0))
			for(Window w : controler.windows) {
				if((w != currentWindow && 
						isOnEdgeOfWindow(w).x != 0 || isOnEdgeOfWindow(w).y != 0) && 
						currentWindow.getBounds().createIntersection(w.getBounds()).isEmpty())
					if(isItTouchingSide(new Point(x,0),w)) {
						
						if (mapLocation.x + currentOffset.x - w.getBounds().x < 0) {
							mapLocation.x = w.getBounds().x - currentOffset.x - width/2;
						}
						else if (mapLocation.x + currentOffset.x - w.getBounds().x > w.getBounds().width)  {
							mapLocation.x = (int) w.getBounds().getMaxX() - currentOffset.x + width/2;
						}
						else {
							int cellXInSecondWindow = (mapLocation.x + currentOffset.x - w.getBounds().x + w.main.getPlaygroundOffset().x) / Playground.getCellWidth();
							mapLocation.x =  Playground.getCellWidth() * (cellXInSecondWindow +  (1 + x) / 2) 
												- w.main.getPlaygroundOffset().x + w.getBounds().x - currentOffset.x - x * width/2;
						}
						
					}
			}
	}

	private void correctSideY(int xToCorrect) {
		int y = (int) Math.signum(xToCorrect);
		int modifier = 0;
		
		if (currentWindow != null) {
			if (mapLocation.y + currentOffset.y - currentWindow.getBounds().y < 0)
				modifier = -1;
	
			if (mapLocation.y + currentOffset.y - currentWindow.getBounds().y > currentWindow.getBounds().height)
				modifier = 1;
		}
		
		if(isItTouchingSide(new Point(0,y))) {
			mapLocation.y = Math.min(Math.max((mapCell.y + modifier + (1 + y) / 2) * Playground.getCellHeight()- currentWindow.main.getPlaygroundOffset().y, 0),currentOffset.height) - y * height/2;
			if(y * velocity.getY() > 0) {
				velocity.setLocation(velocity.getX(), 0);
			}
		}
		else if(currentWindow == null && mapLocation.y + currentOffset.y < 0) {
			mapLocation.y = Toolkit.getDefaultToolkit().getScreenSize().height - currentOffset.y;
		}
		else if(currentWindow == null && mapLocation.y + currentOffset.y > Toolkit.getDefaultToolkit().getScreenSize().height) {
			mapLocation.y = -currentOffset.y;
		}
		
		if(currentWindow != null && (isOnEdgeOfWindow(currentWindow).x != 0 || isOnEdgeOfWindow(currentWindow).y != 0))
			for(Window w : controler.windows) {
				if((w != currentWindow && 
						isOnEdgeOfWindow(w).x != 0 || isOnEdgeOfWindow(w).y != 0) && 
						currentWindow.getBounds().createIntersection(w.getBounds()).isEmpty())
					if(isItTouchingSide(new Point(0,y),w)) {
						
						if(y * velocity.getY() > 0) {
							velocity.setLocation(velocity.getX(), 0);
						}

						if (mapLocation.y + currentOffset.y - w.getBounds().y < 0) {
							mapLocation.y = w.getBounds().y - currentOffset.y - height/2;
						}
						else if (mapLocation.y + currentOffset.y - w.getBounds().y > w.getBounds().height)  {
							mapLocation.y = (int) w.getBounds().getMaxY() - currentOffset.y + height/2;
							
						}
						else {
							int cellYInSecondWindow = (mapLocation.y + currentOffset.y - w.getBounds().y + w.main.getPlaygroundOffset().y) / Playground.getCellHeight();
							mapLocation.y =  Playground.getCellHeight() * (cellYInSecondWindow +  (1 + y) / 2) 
												- w.main.getPlaygroundOffset().y + w.getBounds().y - currentOffset.y - y * height/2;
						}
						
					}
			}
	}
	
	public void correctLocation(Point p) {
		if(p.x < 0) {	
			correctSideX(-1);
		}
		if(p.x > 0) {	
			correctSideX(1);
		}
		if(p.y > 0) {	
			correctSideY(1);
		}
		if(p.y < 0) {	
			correctSideY(-1);
		}
	}
	
	public void tryRegister() {
		if(currentWindow != null) {
			int i, j;
			i = (mapLocation.x + currentWindow.main.getPlaygroundOffset().x) / Playground.getCellWidth();
			j = (mapLocation.y + currentWindow.main.getPlaygroundOffset().y) / Playground.getCellHeight();
			if (mapLocation.x + currentWindow.main.getPlaygroundOffset().x < 0)
				i--;
			if (mapLocation.y + currentWindow.main.getPlaygroundOffset().y < 0)
				j--;
			setCell(i, j);
			if (isOnEdgeOfWindow(currentWindow).x != 0 || isOnEdgeOfWindow(currentWindow).y != 0) {
				int area = width * height / 2;
				for(Window w: controler.windows) {
					
					if(w == currentWindow || 
							isOnEdgeOfWindow(w).x == 0 && isOnEdgeOfWindow(w).y == 0 ||
							!currentWindow.getBounds().createIntersection(w.getBounds()).isEmpty())
						continue;
					if(isOnEdgeOfWindow(w).x == -isOnEdgeOfWindow(currentWindow).x || 
							isOnEdgeOfWindow(w).y == -isOnEdgeOfWindow(currentWindow).y) {
						Rectangle intersection = w.getBounds().intersection(new Rectangle(getAbsoluteSidePositionX(-1),
																				getAbsoluteSidePositionY(-1), width, height));
						int temp = intersection.width * intersection.height;
						
						if(temp > area) {
							setMainWindow(w);
						}
					}
				}
				
			}
			
			if (!currentWindow.getBounds().intersects(new Rectangle(getAbsoluteSidePositionX(-1),
															getAbsoluteSidePositionY(-1), width, height))) {
				currentWindow=null;
			}
		}
		else {
			for (Window w : controler.windows) {
				if (isOnEdgeOfWindow(w).x != 0 || isOnEdgeOfWindow(w).y != 0) {
					if (isOnEdgeOfWindow(w).x * velocity.getX() <= 0 && isOnEdgeOfWindow(w).y * velocity.getY() <= 0) {
						setMainWindow(w);
						break;
					}
				}
			}
	
		}
	}
	
	public void correctVel() {
		if(velocity.getY() < 0) {
			if(velBuffer > 0) {
				velocity.setLocation(velocity.getX(), velocity.getY() + 0.1);
				velBuffer -= 0.1;
			}
			else {
				velocity.setLocation(velocity.getX(), velocity.getY() + 0.3);
			}
		}
		else if(velocity.getY() < 15) {
			velocity.setLocation(velocity.getX(), velocity.getY() + 0.3);
		}
	}
	public void move() {
		
		tryRegister();
		
		for (int i = 0; i < Math.abs(velocity.getY()); i++) {
			mapLocation.setLocation(mapLocation.getX(), mapLocation.getY() + Math.signum(velocity.getY()));
			tryRegister();
			correctLocation(new Point(0,-1));
			correctLocation(new Point(0,1));
		}
		
		for (int i = 0; i < Math.abs(velocity.getX()); i++) {
			mapLocation.setLocation(mapLocation.getX() + Math.signum(velocity.getX()), mapLocation.getY());
			tryRegister();
			correctLocation(new Point(-1,0));
			correctLocation(new Point(1,0));
		}
		
		
		tryRegister();

		doll.setLocation(mapLocation.x - width/2 + currentOffset.x,mapLocation.y - height/2 + currentOffset.y );
		correctVel();
		
	}
	
	public int getSidePositionX(int dir) {
		return mapLocation.x + (int) Math.signum(dir) * width/2;
	}
	
	public int getSidePositionY(int dir) {

		return mapLocation.y + (int) Math.signum(dir) * height/2;
	}
	
	public int getAbsoluteSidePositionX(int dir) {
		return mapLocation.x + (int) Math.signum(dir) * width/2 + currentOffset.x;
	}
	
	public int getAbsoluteSidePositionY(int dir) {
		
		return mapLocation.y + (int) Math.signum(dir) * height/2 + currentOffset.y;
	}
	public Point isOnEdgeOfWindow(Window w) {
		
		Point p = new Point();
		if (w.getBounds().x >= getAbsoluteSidePositionX(-1) && w.getBounds().x <= getAbsoluteSidePositionX(1) &&
				w.getBounds().y <= getAbsoluteSidePositionY(1)&&w.getBounds().getMaxY() >= getAbsoluteSidePositionY(-1)) {
			p.setLocation(-1, 0);
		}
		else if ( w.getBounds().getMaxX() >= getAbsoluteSidePositionX(-1) && w.getBounds().getMaxX() <= getAbsoluteSidePositionX(1) &&
			w.getBounds().y <= getAbsoluteSidePositionY(1) && w.getBounds().y + w.getBounds().height >= getAbsoluteSidePositionY(-1)) {
				p.setLocation(1, 0);
			}
		if ( w.getBounds().y >= getAbsoluteSidePositionY(-1) && w.getBounds().y <= getAbsoluteSidePositionY(1) &&
				w.getBounds().x <= getAbsoluteSidePositionX(1) && w.getBounds().getMaxX() >= getAbsoluteSidePositionX(-1)) {
			p.setLocation(p.getX(), -1);
		}
		else if ( w.getBounds().getMaxY() >= getAbsoluteSidePositionY(-1) && w.getBounds().getMaxY() <= getAbsoluteSidePositionY(1) &&
			w.getBounds().x <= getAbsoluteSidePositionX(1) && w.getBounds().getMaxX() >= getAbsoluteSidePositionX(-1)) {
				p.setLocation(p.getX(), 1);
			}
		return p;
	}
	
}