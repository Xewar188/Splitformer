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
	public Rectangle frame = null;
	public Point mapLocation;
	Point mapCell;
	Rectangle maxSize;
	public final int width = 14;
	public final int height = 25;
	public Window main;
	WindowControler controler;
	public Point2D velocity = new Point2D.Double(0,0);
	float velBuffer = 0;
	
	public Player(Map m,Window w,WindowControler wc,Rectangle r)
	{
		maxSize = r;
		controler = wc;
		setCell(m.startLocation.x, m.startLocation.y);
		mapLocation=new Point((int)(((float)mapCell.x+0.5f) * Playground.getCellWidth()),
								(int) (((float)mapCell.y+0.5f))* Playground.getCellHeight());
		
		setMainWindow(w);
		
	}
	public void draw(Graphics2D g, Window f) {
		if ( main != null && ( f == main || !f.getBounds().intersects(main.getBounds())))
		{
			g.setColor(Color.black);
			g.fillRect(mapLocation.x - width/2 + frame.x - f.getBounds().x, 
						mapLocation.y - height/4 + frame.y - f.getBounds().y, width, height * 3 / 4);
			g.setColor(Color.PINK.brighter());
			g.fillRect(mapLocation.x-width/2 + frame.x - f.getBounds().x,
						mapLocation.y-height/2 + frame.y - f.getBounds().y, width, height / 4);
		}
		
	}
	public void setCell(int x, int y)
	{
		mapCell = new Point(x,y);
		if (controler.mainMap.bluePrint[Math.max(Math.min(x, Map.COLUMNS - 1),0)]
										[Math.max(Math.min(y, Map.ROWS - 1),0)] instanceof Goal)
		{
			controler.endLevel();
		}
	}
	public void setMapLocation(Point pos)
	{
		mapLocation.setLocation(pos);
	}
	public void setMainWindow(Window w)
	{
		if(frame != null)
		{
			mapLocation.x += frame.x - w.getBounds().x;
			mapLocation.y += frame.y - w.getBounds().y;
			int i, j;
			i = (mapLocation.x + w.main.frame.x) / Playground.getCellWidth();
			j = (mapLocation.y + w.main.frame.y) / Playground.getCellHeight();
			
			if(mapLocation.x < 0)
				i--;
			if(mapLocation.y < 0)
				j--;
			
			setCell(i,j);
		}
		
		main = w;
		frame = new Rectangle(main.getBounds());
	}
	
	public boolean isTouchingCellY(int x, int y) {
		return isTouchingCellOfWindowY(x, y, mapCell, main);
	}
	
	public boolean isTouchingCellX(int x, int y) {
		return isTouchingCellOfWindowX(x, y, mapCell, main);
	}
	
	public boolean isTouchingCellOfWindowX(int x, int y, Point pos, Window toCheck) {
		if (Math.abs(y) <= 1 && Math.abs(x) == 1) {
			boolean toReturn = toCheck.main.isInFrame(pos.x + x, pos.y + y) && 
			controler.mainMap.bluePrint[pos.x + x][pos.y + y].tangible &&
			(getSidePositionX(x) + frame.x - toCheck.getBounds().x) * x >= ((pos.x + (1 + x) / 2) * Playground.getCellWidth() - toCheck.main.frame.x) * x;
			
			if (y == -1)
				toReturn = toReturn && getSidePositionY(-1) + toCheck.main.frame.y + frame.y - toCheck.getBounds().y < pos.y * Playground.getCellHeight();
			
			if (y == 1)
				toReturn = toReturn && getSidePositionY(1) + toCheck.main.frame.y + frame.y - toCheck.getBounds().y > (pos.y+1) * Playground.getCellHeight();
			return toReturn;
		}
		return false;
	}
	
	public boolean isTouchingCellOfWindowY(int x, int y, Point pos, Window toCheck) {
		if (Math.abs(x) <= 1 && Math.abs(y) == 1) {
			boolean toReturn = toCheck.main.isInFrame(pos.x + x, pos.y + y) && 
			controler.mainMap.bluePrint[pos.x + x][pos.y + y].tangible &&
			(getSidePositionY(y) + frame.y - toCheck.getBounds().y) * y >= ((pos.y + (1 + y) / 2) * Playground.getCellHeight() - toCheck.main.frame.y) * y;
			
			if (x == -1)
				toReturn = toReturn && getSidePositionX(-1) + toCheck.main.frame.x + frame.x - toCheck.getBounds().x < pos.x * Playground.getCellWidth();
			
			if (x == 1)
				toReturn = toReturn && getSidePositionX(1) + toCheck.main.frame.x + frame.x - toCheck.getBounds().x > (pos.x+1) * Playground.getCellWidth();
			return toReturn;
		}
		return false;
	}
	private boolean isItTouchingSide(Point p)
	{
		int x = (int) Math.signum(p.getX());
		int y = (int) Math.signum(p.getY());
		if(main == null)
			return false;
		
		boolean test = false;
		if(p.y != 0)
		{
			test = isTouchingCellY(0,y);
					
			test=test || isTouchingCellY(-1,y);
					
			test=test || isTouchingCellY(1,y);
					
		}
		else if(p.x != 0)
		{
	
				test = isTouchingCellX(x,0);
				
				test = test || isTouchingCellX(x,-1);
				
				test = test || isTouchingCellX(x,1);
						
			}
		return test;
	}
	private boolean isItTouchingSide(Point p,Window w)
	{
		int x = (int) Math.signum(p.getX());
		int y = (int) Math.signum(p.getY());
		boolean test = false;
		Point tempCell = new Point();
		tempCell.setLocation((mapLocation.x + frame.x - w.getBounds().x + w.main.frame.x) / Playground.getCellWidth(),
								(mapLocation.y + frame.y - w.getBounds().y + w.main.frame.y) / Playground.getCellHeight());
		
		if (mapLocation.x + frame.x - w.getBounds().x < 0)
			tempCell.x--;
		if (mapLocation.y + frame.y - w.getBounds().y < 0)
			tempCell.y--;

		if (mapLocation.x + frame.x - w.getBounds().x > w.getBounds().width)
			tempCell.x++;
		if (mapLocation.y + frame.y - w.getBounds().y > w.getBounds().height)
			tempCell.y++;
		
		if (p.y != 0)
		{
			test = isTouchingCellOfWindowY(0, y, tempCell, w);
			
			test = test || isTouchingCellOfWindowY(1, y, tempCell, w);
			
			test = test || isTouchingCellOfWindowY(-1, y, tempCell, w);
				
			if(!test && isOnEdgeOfWindow(w).y == 0 && isOnEdgeOfWindow(w).x != 0)
			{
				
				tempCell.x-=isOnEdgeOfWindow(w).x;
				test = isTouchingCellOfWindowY(0, y, tempCell, w);
				
				test = test || isTouchingCellOfWindowY(1, y, tempCell, w);
				
				test = test || isTouchingCellOfWindowY(-1, y, tempCell, w);
			}
		}
		else if (p.x != 0)
		{
				test = isTouchingCellOfWindowX(x, 0, tempCell, w);
				
				test = test || isTouchingCellOfWindowX(x, 1, tempCell, w);
				
				test = test || isTouchingCellOfWindowX(x, -1, tempCell, w);
				
				if (!test && isOnEdgeOfWindow(w).x == 0 && isOnEdgeOfWindow(w).y != 0)
				{
					
					tempCell.y-=isOnEdgeOfWindow(w).y;
					test = isTouchingCellOfWindowX(x, 0, tempCell, w);
					
					test = test || isTouchingCellOfWindowX(x, 1, tempCell, w);
					
					test = test || isTouchingCellOfWindowX(x, -1, tempCell, w);
				}		
			}
		return test;
	}
	public void jump()
	{
		
		boolean test = isItTouchingSide(new Point(0,1));
		if(!test)
		{
			for(Window w : controler.windows)
			{
				if(isItTouchingSide(new Point(0,1), w))
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
		if(isItTouchingSide(new Point(x,0)))
		{
			mapLocation.x = Math.min(Math.max((mapCell.x + (1 + x) / 2) * Playground.getCellWidth()- main.main.frame.x ,0),frame.width)- x * width/2;
		}
		else if(main == null && mapLocation.x + frame.x < 0)
		{
			mapLocation.x = Toolkit.getDefaultToolkit().getScreenSize().width - frame.x;
		}
		else if(main == null && mapLocation.x + frame.x > Toolkit.getDefaultToolkit().getScreenSize().width)
		{
			mapLocation.x = -frame.x;
		}
		if(main != null && (isOnEdgeOfWindow(main).x != 0 || isOnEdgeOfWindow(main).y != 0))
			for(Window w : controler.windows)
			{
				if((w != main && 
						isOnEdgeOfWindow(w).x != 0 || isOnEdgeOfWindow(w).y != 0) && 
						main.getBounds().createIntersection(w.getBounds()).isEmpty())
					if(isItTouchingSide(new Point(x,0),w))
					{
						
						if (mapLocation.x + frame.x - w.getBounds().x < 0) {
							mapLocation.x = w.getBounds().x - frame.x - width/2;
						}
						else if (mapLocation.x + frame.x - w.getBounds().x > w.getBounds().width)  {
							mapLocation.x = (int) w.getBounds().getMaxX() - frame.x + width/2;
						}
						else {
							int cellXInSecondWindow = (mapLocation.x + frame.x - w.getBounds().x + w.main.frame.x) / Playground.getCellWidth();
							mapLocation.x =  Playground.getCellWidth() * (cellXInSecondWindow +  (1 + x) / 2) 
												- w.main.frame.x + w.getBounds().x - frame.x - x * width/2;
						}
						
					}
			}
	}

	private void correctSideY(int xToCorrect) {
		int y = (int) Math.signum(xToCorrect);
		
		
		if(isItTouchingSide(new Point(0,y)))
		{
			mapLocation.y = Math.min(Math.max((mapCell.y + (1 + y) / 2) * Playground.getCellHeight()- main.main.frame.y, 0),frame.height) - y * height/2;
			if(y * velocity.getY() > 0)
			{
				velocity.setLocation(velocity.getX(), 0);
			}
		}
		else if(main == null && mapLocation.y + frame.y < 0)
		{
			mapLocation.y = Toolkit.getDefaultToolkit().getScreenSize().height - frame.y;
		}
		else if(main == null && mapLocation.y + frame.y > Toolkit.getDefaultToolkit().getScreenSize().height)
		{
			mapLocation.y = -frame.y;
		}
		if(main != null && (isOnEdgeOfWindow(main).x != 0 || isOnEdgeOfWindow(main).y != 0))
			for(Window w : controler.windows)
			{
				if((w != main && 
						isOnEdgeOfWindow(w).x != 0 || isOnEdgeOfWindow(w).y != 0) && 
						main.getBounds().createIntersection(w.getBounds()).isEmpty())
					if(isItTouchingSide(new Point(0,y),w))
					{
						
						if(y * velocity.getY() > 0)
						{
							velocity.setLocation(velocity.getX(), 0);
						}

						if (mapLocation.y + frame.y - w.getBounds().y < 0) {
							mapLocation.y = w.getBounds().y - frame.y - height/2;
						}
						else if (mapLocation.y + frame.y - w.getBounds().y > w.getBounds().height)  {
							mapLocation.y = (int) w.getBounds().getMaxY() - frame.y + height/2;
							
						}
						else {
							int cellXInSecondWindow = (mapLocation.y + frame.y - w.getBounds().y + w.main.frame.y) / Playground.getCellWidth();
							mapLocation.y =  Playground.getCellHeight() * (cellXInSecondWindow +  (1 + y) / 2) 
												- w.main.frame.y + w.getBounds().y - frame.y - y * height/2;
						}
						
					}
			}
	}
	
	public void correctLocation(Point p)
	{
		if(p.x < 0)
		{	
			correctSideX(-1);
		}
		if(p.x > 0)
		{	
			correctSideX(1);
		}
		if(p.y > 0)
		{	
			correctSideY(1);
		}
		if(p.y < 0)
		{	
			correctSideY(-1);
		}
	}
	
	public void tryRegister() {
		if(main != null)
		{
			int i, j;
			i = (mapLocation.x + main.main.frame.x) / Playground.getCellWidth();
			j = (mapLocation.y + main.main.frame.y) / Playground.getCellHeight();
			if (mapLocation.x + main.main.frame.x < 0)
				i--;
			if (mapLocation.y + main.main.frame.y < 0)
				j--;
			setCell(i, j);
			if (isOnEdgeOfWindow(main).x != 0 || isOnEdgeOfWindow(main).y != 0)
			{
				int area = width * height / 2;
				for(Window w:controler.windows)
				{
					
					if(w == main || 
							isOnEdgeOfWindow(w).x == 0 && isOnEdgeOfWindow(w).y == 0 ||
							!main.getBounds().createIntersection(w.getBounds()).isEmpty())
						continue;
					if(isOnEdgeOfWindow(w).x == -isOnEdgeOfWindow(main).x || 
							isOnEdgeOfWindow(w).y == -isOnEdgeOfWindow(main).y)
					{
						Rectangle intersection = w.getBounds().intersection(new Rectangle(getAbsoluteSidePositionX(-1),
																				getAbsoluteSidePositionY(-1), width, height));
						int temp = intersection.width * intersection.height;
						
						if(temp > area)
						{
							setMainWindow(w);
						}
					}
				}
				
			}
			
			if (!main.getBounds().intersects(new Rectangle(getAbsoluteSidePositionX(-1),
															getAbsoluteSidePositionY(-1), width, height)))
			{
				main=null;
			}
		}
		else
		{
			for (Window w : controler.windows)
			{
				if (isOnEdgeOfWindow(w).x != 0 || isOnEdgeOfWindow(w).y != 0)
				{
					if (isOnEdgeOfWindow(w).x * velocity.getX() <= 0 && isOnEdgeOfWindow(w).y * velocity.getY() <= 0)
					{
						setMainWindow(w);
						break;
					}
				}
			}
	
		}
	}
	
	public void correctVel() {
		if(velocity.getY() < 0)
		{
			if(velBuffer > 0)
			{
				velocity.setLocation(velocity.getX(), velocity.getY() + 0.1);
				velBuffer -= 0.1;
			}
			else
			{
				velocity.setLocation(velocity.getX(), velocity.getY() + 0.3);
			}
		}
		else if(velocity.getY() < 15) {
			velocity.setLocation(velocity.getX(), velocity.getY() + 0.3);
		}
	}
	public void move()
	{
		
		tryRegister();
		
		for (int i = 0; i < Math.abs(velocity.getX()); i++) 
		{
			mapLocation.setLocation(mapLocation.getX() + Math.signum(velocity.getX()), mapLocation.getY());
			tryRegister();
			correctLocation(new Point(-1,0));
			correctLocation(new Point(1,0));
		}
		
		for (int i = 0; i < Math.abs(velocity.getY()); i++) 
		{
			mapLocation.setLocation(mapLocation.getX(), mapLocation.getY() + Math.signum(velocity.getY()));
			tryRegister();
			correctLocation(new Point(0,-1));
			correctLocation(new Point(0,1));
		}
		correctVel();
		
	}
	
	public int getSidePositionX(int dir) {
		return mapLocation.x + (int) Math.signum(dir) * width/2;
	}
	
	public int getSidePositionY(int dir) {

		return mapLocation.y + (int) Math.signum(dir) * height/2;
	}
	
	public int getAbsoluteSidePositionX(int dir) {
		return mapLocation.x + (int) Math.signum(dir) * width/2 + frame.x;
	}
	
	public int getAbsoluteSidePositionY(int dir) {
		
		return mapLocation.y + (int) Math.signum(dir) * height/2 + frame.y;
	}
	public Point isOnEdgeOfWindow(Window w) {
		
		Point p = new Point();
		if (w.getBounds().x >= getAbsoluteSidePositionX(-1) && w.getBounds().x <= getAbsoluteSidePositionX(1) &&
				w.getBounds().y <= getAbsoluteSidePositionY(1)&&w.getBounds().getMaxY() >= getAbsoluteSidePositionY(-1))
		{
			p.setLocation(-1, 0);
		}
		else if ( w.getBounds().getMaxX() >= getAbsoluteSidePositionX(-1) && w.getBounds().getMaxX() <= getAbsoluteSidePositionX(1) &&
			w.getBounds().y <= getAbsoluteSidePositionY(1) && w.getBounds().y + w.getBounds().height >= getAbsoluteSidePositionY(-1))
			{
				p.setLocation(1, 0);
			}
		if ( w.getBounds().y >= getAbsoluteSidePositionY(-1) && w.getBounds().y <= getAbsoluteSidePositionY(1) &&
				w.getBounds().x <= getAbsoluteSidePositionX(1) && w.getBounds().getMaxX() >= getAbsoluteSidePositionX(-1))
		{
			p.setLocation(p.getX(), -1);
		}
		else if ( w.getBounds().getMaxY() >= getAbsoluteSidePositionY(-1) && w.getBounds().getMaxY() <= getAbsoluteSidePositionY(1) &&
			w.getBounds().x <= getAbsoluteSidePositionX(1) && w.getBounds().getMaxX() >= getAbsoluteSidePositionX(-1))
			{
				p.setLocation(p.getX(), 1);
			}
		return p;
	}
}