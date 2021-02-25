package metaparts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import cells.CellBase;
import cells.Goal;
import playground.Map;
import window.Window;
import window.WindowControler;

public class Player {
public Rectangle frame= null;
public Point mapLocation;
Point mapCell;
Rectangle maxSize;
public final int width=14;
public final int height=25;
public Window main;
WindowControler controler;
public boolean isMovingLeft=false,isMovingRight=false;
Point2D velocity=new Point2D.Double(0,0),realMapLocation;
float velBuffer=0;

public Player(Map m,Window w,WindowControler wc,Rectangle r)
{
	
	maxSize=r;
	controler=wc;
	for(int j=0; j<Map.ROWS;j++)
		for(int i=0;i<Map.COLUMNS;i++)
		{
			if(m.bluePrint[i][j].id=='3')
			{
				mapLocation=new Point((int)(r.width/Map.COLUMNS*((float)i+0.5f)),(int) (r.height/Map.ROWS*((float)j+0.5f)));
				setCell(i,j);
				break;
			}
		}
	
	realMapLocation=new Point2D.Double(mapLocation.getX(),mapLocation.getY());
	setMainWindow(w);
	
}
public void draw(Graphics2D g,Window f) {
	if(main!=null&&(f==main||!f.getBounds().intersects(main.getBounds())))
	{
	g.setColor(Color.black);
	g.fillRect(mapLocation.x-width/2+frame.x-f.getBounds().x, mapLocation.y-height/4+frame.y-f.getBounds().y, width, height*3/4);
	g.setColor(Color.PINK.brighter());
	g.fillRect(mapLocation.x-width/2+frame.x-f.getBounds().x, mapLocation.y-height/2+frame.y-f.getBounds().y, width, height/4);
	}
	
}
public void setCell(int x, int y)
{
	mapCell=new Point(x,y);
	if(controler.mainMap.bluePrint[Math.max(Math.min(x,Map.COLUMNS-1),0)][Math.max(Math.min(y,Map.ROWS-1),0)] instanceof Goal)
	{
		controler.endLevel();
	}
}
public void setMapLocation(Point pos)
{
	mapLocation.setLocation(pos);
	realMapLocation.setLocation(mapLocation);
}
public void setMainWindow(Window w)
{
	if(frame!=null)
	{
	mapLocation.x+=frame.x-w.getBounds().x;
	mapLocation.y+=frame.y-w.getBounds().y;
	realMapLocation.setLocation(mapLocation.getX(),mapLocation.getY());
	int i, j;
	i=(mapLocation.x+w.main.frame.x)*Map.COLUMNS/maxSize.width;
	j=(mapLocation.y+w.main.frame.y)*Map.ROWS/maxSize.height;
	if(mapLocation.x<0)
		i-=1;
	if(mapLocation.y<0)
		j-=1;
	setCell(i,j);
	}
	
	main=w;
	frame=new Rectangle(main.getBounds());
}
public boolean isItTouchingSide(Point p)
{
	if(main==null)
		return false;
	boolean test=false;
	if(p.y!=0)
	{
		test=main.main.isInFrame(mapCell.x, (int) (mapCell.y+1*Math.signum(p.getY())))&&controler.mainMap.bluePrint[mapCell.x][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-main.main.frame.y)*Math.signum(p.getY());
		test=test||main.main.isInFrame(mapCell.x-1, (int) (mapCell.y+1*Math.signum(p.getY())))&&mapLocation.x-width/2+main.main.frame.x<mapCell.x*maxSize.width/Map.COLUMNS&&
				controler.mainMap.bluePrint[mapCell.x-1][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-main.main.frame.y)*Math.signum(p.getY());
				
		test=test||main.main.isInFrame(mapCell.x+1, (int) (mapCell.y+1*Math.signum(p.getY())))&&mapLocation.x+width/2+main.main.frame.x>(mapCell.x+1)*maxSize.width/Map.COLUMNS&&
				controler.mainMap.bluePrint[mapCell.x+1][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-main.main.frame.y)*Math.signum(p.getY());
				
	}
	else if(p.x!=0)
	{

			test=main.main.isInFrame((int) (mapCell.x+1*Math.signum(p.getX())),mapCell.y)&&controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-main.main.frame.x)*Math.signum(p.getX());
			
			test=test||main.main.isInFrame((int) (mapCell.x+1*Math.signum(p.getX())),mapCell.y-1)&&mapLocation.y-height/2+main.main.frame.y<mapCell.y*maxSize.height/Map.ROWS&&
					controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y-1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-main.main.frame.x)*Math.signum(p.getX());
			
			test=test||main.main.isInFrame((int) (mapCell.x+1*Math.signum(p.getX())),mapCell.y+1)&&mapLocation.y+height/2+main.main.frame.y>(mapCell.y+1)*maxSize.height/Map.ROWS&&
					controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y+1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-main.main.frame.x)*Math.signum(p.getX());
					
		}
	return test;
}
public boolean isItTouchingSide(Point p,Window w)
{
	boolean test=false;
	Point tempCell=new Point();
	tempCell.setLocation((mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x)*Map.COLUMNS/maxSize.width,(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y)*Map.ROWS/maxSize.height);
	
	if(mapLocation.x+frame.x-w.getBounds().x<0)
		tempCell.x-=1;
	if(mapLocation.y+frame.y-w.getBounds().y<0)
		tempCell.y-=1;
	if(p.y!=0)
	{
		test=w.main.isInFrame(tempCell.x, (int) (tempCell.y+1*Math.signum(p.getY())))&&controler.mainMap.bluePrint[tempCell.x][(int) (tempCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY())+frame.y-w.getBounds().y)*Math.signum(p.getY())>=((tempCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
		
		test=test||w.main.isInFrame(tempCell.x-1, (int) (tempCell.y+1*Math.signum(p.getY())))&&mapLocation.x-width/2+frame.x-w.getBounds().x<tempCell.x*maxSize.width/Map.COLUMNS-w.main.frame.x&&
				controler.mainMap.bluePrint[tempCell.x-1][(int) (tempCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY())+frame.y-w.getBounds().y)*Math.signum(p.getY())>=((tempCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
		
		test=test||w.main.isInFrame(tempCell.x+1, (int) (tempCell.y+1*Math.signum(p.getY())))&&mapLocation.x+width/2+frame.x-w.getBounds().x>(tempCell.x+1)*maxSize.width/Map.COLUMNS-w.main.frame.x&&
				controler.mainMap.bluePrint[tempCell.x+1][(int) (tempCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY())+frame.y-w.getBounds().y)*Math.signum(p.getY())>=((tempCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
		if(!test&&isOnEdgeOfWindow(w).y!=0)
		{
			
			tempCell.y-=isOnEdgeOfWindow(w).y;
			tempCell.x+=isOnEdgeOfWindow(w).x;
			test=w.main.isInFrame(tempCell.x, (int) (tempCell.y+1*Math.signum(p.getY())))&&controler.mainMap.bluePrint[tempCell.x][(int) (tempCell.y+1*Math.signum(p.getY()))].tangible&&
					(mapLocation.y+height/2*Math.signum(p.getY())+frame.y-w.getBounds().y)*Math.signum(p.getY())>=((tempCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
			
			test=test||w.main.isInFrame(tempCell.x-1, (int) (tempCell.y+1*Math.signum(p.getY())))&&mapLocation.x-width/2+frame.x-w.getBounds().x<tempCell.x*maxSize.width/Map.COLUMNS-w.main.frame.x&&
					controler.mainMap.bluePrint[tempCell.x-1][(int) (tempCell.y+1*Math.signum(p.getY()))].tangible&&
					(mapLocation.y+height/2*Math.signum(p.getY())+frame.y-w.getBounds().y)*Math.signum(p.getY())>=((tempCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
			
			test=test||w.main.isInFrame(tempCell.x+1, (int) (tempCell.y+1*Math.signum(p.getY())))&&mapLocation.x+width/2+frame.x-w.getBounds().x>(tempCell.x+1)*maxSize.width/Map.COLUMNS-w.main.frame.x&&
					controler.mainMap.bluePrint[tempCell.x+1][(int) (tempCell.y+1*Math.signum(p.getY()))].tangible&&
					(mapLocation.y+height/2*Math.signum(p.getY())+frame.y-w.getBounds().y)*Math.signum(p.getY())>=((tempCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
		}
	}
	else if(p.x!=0)
	{

			test=w.main.isInFrame((int) (tempCell.x+1*Math.signum(p.getX())),tempCell.y)&&controler.mainMap.bluePrint[(int) (tempCell.x+1*Math.signum(p.getX()))][tempCell.y].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX())+frame.x-w.getBounds().x)*Math.signum(p.getX())>=((tempCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
			
			test=test||w.main.isInFrame((int) (tempCell.x+1*Math.signum(p.getX())),tempCell.y-1)&&mapLocation.y-height/2+frame.y-w.getBounds().y<tempCell.y*maxSize.height/Map.ROWS-w.main.frame.y&&
					controler.mainMap.bluePrint[(int) (tempCell.x+1*Math.signum(p.getX()))][tempCell.y-1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX())+frame.x-w.getBounds().x)*Math.signum(p.getX())>=((tempCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
			
			test=test||w.main.isInFrame((int) (tempCell.x+1*Math.signum(p.getX())),tempCell.y+1)&&mapLocation.y+height/2+frame.y-w.getBounds().y>(tempCell.y+1)*maxSize.height/Map.ROWS-w.main.frame.y&&
					controler.mainMap.bluePrint[(int) (tempCell.x+1*Math.signum(p.getX()))][tempCell.y+1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX())+frame.x-w.getBounds().x)*Math.signum(p.getX())>=((tempCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
					if(!test&&isOnEdgeOfWindow(w).x!=0)
					{
						
						tempCell.x+=isOnEdgeOfWindow(w).x;
						tempCell.y-=isOnEdgeOfWindow(w).y;
						test=w.main.isInFrame((int) (tempCell.x+1*Math.signum(p.getX())),tempCell.y)&&controler.mainMap.bluePrint[(int) (tempCell.x+1*Math.signum(p.getX()))][tempCell.y].tangible&&
								(mapLocation.x+width/2*Math.signum(p.getX())+frame.x-w.getBounds().x)*Math.signum(p.getX())>=((tempCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
						
						test=test||w.main.isInFrame((int) (tempCell.x+1*Math.signum(p.getX())),tempCell.y-1)&&mapLocation.y-height/2+frame.y-w.getBounds().y<tempCell.y*maxSize.height/Map.ROWS-w.main.frame.y&&
								controler.mainMap.bluePrint[(int) (tempCell.x+1*Math.signum(p.getX()))][tempCell.y-1].tangible&&
								(mapLocation.x+width/2*Math.signum(p.getX())+frame.x-w.getBounds().x)*Math.signum(p.getX())>=((tempCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
						
						test=test||w.main.isInFrame((int) (tempCell.x+1*Math.signum(p.getX())),tempCell.y+1)&&mapLocation.y+height/2+frame.y-w.getBounds().y>(tempCell.y+1)*maxSize.height/Map.ROWS-w.main.frame.y&&
								controler.mainMap.bluePrint[(int) (tempCell.x+1*Math.signum(p.getX()))][tempCell.y+1].tangible&&
								(mapLocation.x+width/2*Math.signum(p.getX())+frame.x-w.getBounds().x)*Math.signum(p.getX())>=((tempCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
					}		
		}
	return test;
}
public void jump()
{
	
	boolean test =isItTouchingSide(new Point(0,1));
	if(!test)
	{
		for(Window w : controler.windows)
		{
			test =isItTouchingSide(new Point(0,1),w);
			if(test)
				break;
		}
	}
	if(test)
	{
		velocity.setLocation(velocity.getX(), -5);
		velBuffer=5;
	}
}

public void endJump() {
	
	velBuffer=0;
}
public void correctLocation(Point p)
{
	if(p.x<0)
	{	
		if(isItTouchingSide(new Point(-1,0)))
		{
		mapLocation.x=(mapCell.x)*maxSize.width/Map.COLUMNS-main.main.frame.x+width/2;
		realMapLocation.setLocation(mapLocation.getX(),realMapLocation.getY());
		}
		else if(main==null&&mapLocation.x+frame.x<0)
		{
			mapLocation.x=Toolkit.getDefaultToolkit().getScreenSize().width-frame.x;
			realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		}
		if(main!=null&&(isOnEdgeOfWindow(main).x!=0||isOnEdgeOfWindow(main).y!=0))
		for(Window w:controler.windows)
		{
			if((w!=main&&isOnEdgeOfWindow(w).x!=0||isOnEdgeOfWindow(w).y!=0)&&main.getBounds().createIntersection(w.getBounds()).isEmpty())
			{
				if(isItTouchingSide(p,w))
				{
					
					Point tempCell=new Point();
					tempCell.setLocation((mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x)*Map.COLUMNS/maxSize.width,(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y)*Map.ROWS/maxSize.height);
					if(mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x<0)
						tempCell.x-=1;
					if(tempCell.x+1>=Map.COLUMNS||tempCell.y>=0&&tempCell.y<Map.ROWS&&controler.mainMap.bluePrint[tempCell.x+1][ tempCell.y].tangible)
					{
						tempCell.x+=1;
					}
					mapLocation.x=Math.min((tempCell.x)*maxSize.width/Map.COLUMNS-w.main.frame.x+w.getBounds().x-frame.x+width/2,w.getBounds().x+w.getBounds().width-frame.x+width/2);
					realMapLocation.setLocation(mapLocation.getX(),realMapLocation.getY());
				}
			}
		}
		
	}
	if(p.x>0)
	{	
		if(isItTouchingSide(new Point(1,0)))
		{
		mapLocation.x=(mapCell.x+1)*maxSize.width/Map.COLUMNS-main.main.frame.x-width/2;
		realMapLocation.setLocation(mapLocation.getX(),realMapLocation.getY());
		}
		else if(main==null&&mapLocation.x+frame.x>Toolkit.getDefaultToolkit().getScreenSize().width)
		{
			mapLocation.x=-frame.x;
			realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		}
		if(main!=null&&(isOnEdgeOfWindow(main).x!=0||isOnEdgeOfWindow(main).y!=0))
		for(Window w:controler.windows)
		{
			if((w!=main&&isOnEdgeOfWindow(w).x!=0||isOnEdgeOfWindow(w).y!=0)&&main.getBounds().createIntersection(w.getBounds()).isEmpty())
			{
				if(isItTouchingSide(p,w))
				{
					
					Point tempCell=new Point();
					tempCell.setLocation((mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x)*Map.COLUMNS/maxSize.width,(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y)*Map.ROWS/maxSize.height);
					if(mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x<0)
						tempCell.x-=1;
					if(tempCell.x-1<0||tempCell.y>=0&&tempCell.y<Map.ROWS&&controler.mainMap.bluePrint[tempCell.x-1][ tempCell.y].tangible)
					{
						tempCell.x-=1;
					}
					
					mapLocation.x=Math.max((tempCell.x+1)*maxSize.width/Map.COLUMNS-w.main.frame.x+w.getBounds().x-frame.x-width/2,w.getBounds().x-frame.x-width/2);
					realMapLocation.setLocation(mapLocation.getX(),realMapLocation.getY());
				}
			}
		}
	}
	if(p.y>0)
	{	
		if(velocity.getY()<0)
		{
			if(velBuffer>0)
			{
				velocity.setLocation(velocity.getX(),velocity.getY()+0.1);
				velBuffer-=0.1;
			}
			else
			{
				velocity.setLocation(velocity.getX(),velocity.getY()+0.3);
			}
		}
		else
		if(velocity.getY()<15)
			velocity.setLocation(velocity.getX(),velocity.getY()+0.3);
		if(isItTouchingSide(new Point(0,1)))
		{
			if(velocity.getY()>0)
				velocity.setLocation(velocity.getX(),0);
			mapLocation.y=(mapCell.y+1)*maxSize.height/Map.ROWS-main.main.frame.y-height/2;
			realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		}
		else if(main==null&&mapLocation.y+frame.y>Toolkit.getDefaultToolkit().getScreenSize().height)
		{
			mapLocation.y=-frame.y;
			realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
			
		}
		
		
		if(main!=null&&(isOnEdgeOfWindow(main).x!=0||isOnEdgeOfWindow(main).y!=0))
			for(Window w:controler.windows)
			{
				if((w!=main&&isOnEdgeOfWindow(w).x!=0||isOnEdgeOfWindow(w).y!=0)&&main.getBounds().createIntersection(w.getBounds()).isEmpty())
				{
					
					if(isItTouchingSide(p,w))
					{
						Point tempCell=new Point();
						tempCell.setLocation((mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x)*Map.COLUMNS/maxSize.width,(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y)*Map.ROWS/maxSize.height);
						if(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y<0)
							tempCell.y-=1;
						
						if(tempCell.y-1<0||tempCell.x>=0&&tempCell.x<Map.COLUMNS&&controler.mainMap.bluePrint[tempCell.x][ tempCell.y-1].tangible)
						{
							tempCell.y-=1;
						}
						
						mapLocation.y=Math.max((tempCell.y+1)*maxSize.height/Map.ROWS-w.main.frame.y+w.getBounds().y-frame.y-height/2,w.getBounds().y-frame.y-height/2);
						realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
						velocity.setLocation(velocity.getX(),0);
					}
				}
			}
		
		

	}
	if(p.y<0)
	{	
		if(isItTouchingSide(new Point(0,-1)))
		{
		mapLocation.y=(mapCell.y)*maxSize.height/Map.ROWS-main.main.frame.y+height/2;
		realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		if(velocity.getY()<0)
		{
			velocity.setLocation(velocity.getX(),0);
		}
		}
		else if(main==null&&mapLocation.y+frame.y<0)
		{
			mapLocation.x=Toolkit.getDefaultToolkit().getScreenSize().height-frame.y;
			realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		}
		if(main!=null&&(isOnEdgeOfWindow(main).x!=0||isOnEdgeOfWindow(main).y!=0))
			for(Window w:controler.windows)
			{
				if((w!=main&&isOnEdgeOfWindow(w).x!=0||isOnEdgeOfWindow(w).y!=0)&&main.getBounds().createIntersection(w.getBounds()).isEmpty())
				{
					if(isItTouchingSide(p,w))
					{
						if(velocity.getY()<0)
						{
							velocity.setLocation(velocity.getX(),0);
						}
						Point tempCell=new Point();
						tempCell.setLocation((mapLocation.x+frame.x-w.getBounds().x+w.main.frame.x)*Map.COLUMNS/maxSize.width,(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y)*Map.ROWS/maxSize.height);
						if(mapLocation.y+frame.y-w.getBounds().y+w.main.frame.y<0)
							tempCell.y-=1;
						if(tempCell.y+1>=Map.ROWS||tempCell.x>=0&&tempCell.x<Map.COLUMNS&&controler.mainMap.bluePrint[tempCell.x][ tempCell.y+1].tangible)
						{
							tempCell.y+=1;
						}
						mapLocation.y=Math.min((tempCell.y)*maxSize.height/Map.ROWS-w.main.frame.y+w.getBounds().y-frame.y+height/2,w.getBounds().y+w.getBounds().height-frame.y+height/2);
						realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
					}
				}
			}
		
	}
	
	
	
	
}

public void move()
{
	if(main!=null)
	{
		int i, j;
		i=(mapLocation.x+main.main.frame.x)*Map.COLUMNS/maxSize.width;
		j=(mapLocation.y+main.main.frame.y)*Map.ROWS/maxSize.height;
		if(mapLocation.x+main.main.frame.x<0)
			i-=1;
		if(mapLocation.y+main.main.frame.y<0)
			j-=1;
		setCell(i,j);
		if(isOnEdgeOfWindow(main).x!=0||isOnEdgeOfWindow(main).y!=0)
		{
			int area=width*height/2;
			for(Window w:controler.windows)
			{
				
				if(w==main||isOnEdgeOfWindow(w).x==0&&isOnEdgeOfWindow(w).y==0||!main.getBounds().createIntersection(w.getBounds()).isEmpty())
					continue;
				if(isOnEdgeOfWindow(w).x==-isOnEdgeOfWindow(main).x||isOnEdgeOfWindow(w).y==-isOnEdgeOfWindow(main).y)
				{
				int temp=w.getBounds().intersection(new Rectangle(mapLocation.x-width/2+frame.x,mapLocation.y-height/2+frame.y,width,height)).width
						*w.getBounds().intersection(new Rectangle(mapLocation.x-width/2+frame.x,mapLocation.y-height/2+frame.y,width,height)).height;
				
				if(temp>area)
				{
					setMainWindow(w);
				
					
				}
				}
			}
			
		}
		if(!main.getBounds().intersects(new Rectangle(mapLocation.x-width/2+frame.x,mapLocation.y-height/2+frame.y,width,height)))
		{
		
			main=null;
		
		}
	}
	else
	{
		
		for(Window w:controler.windows)
		{
			if(isOnEdgeOfWindow(w).x!=0||isOnEdgeOfWindow(w).y!=0)
			{
				if(isOnEdgeOfWindow(w).x*velocity.getX()<=0&&isOnEdgeOfWindow(w).y*velocity.getY()<=0)
				{setMainWindow(w);
					break;
				}
			}
		}

	}
	if(isMovingLeft)
	{
		realMapLocation.setLocation(realMapLocation.getX()-2, realMapLocation.getY());

		correctLocation(new Point(-1,0));
	}
	
	if(isMovingRight)
	{
		realMapLocation.setLocation(realMapLocation.getX()+2, realMapLocation.getY());
		correctLocation(new Point(1,0));
	}
	
	realMapLocation.setLocation(realMapLocation.getX()+velocity.getX(), realMapLocation.getY()+velocity.getY());
	mapLocation.setLocation(realMapLocation);
	
	correctLocation(new Point(0,-1));
	correctLocation(new Point(0,1));
	

	

	
	
	
	
	
	
	
}
public Point isOnEdgeOfWindow(Window w) {
	
	Point p=new Point();
	if(w.getBounds().x>=mapLocation.x-width/2+frame.x&&w.getBounds().x<=mapLocation.x+width/2+frame.x&&
			w.getBounds().y<=mapLocation.y+height/2+frame.y&&w.getBounds().y+w.getBounds().height>=mapLocation.y-height/2+frame.y)
	{
		p.setLocation(-1, 0);
	}
	else
		if( w.getBounds().x+w.getBounds().width>=mapLocation.x-width/2+frame.x&&w.getBounds().x+w.getBounds().width<=mapLocation.x+width/2+frame.x&&
		w.getBounds().y<=mapLocation.y+height/2+frame.y&&w.getBounds().y+w.getBounds().height>=mapLocation.y-height/2+frame.y)
		{
			p.setLocation(1,0);
		}
	if( w.getBounds().y>=mapLocation.y-height/2+frame.y&&w.getBounds().y<=mapLocation.y+height/2+frame.y&&
			w.getBounds().x<=mapLocation.x+width/2+frame.x&&w.getBounds().x+w.getBounds().width>=mapLocation.x-width/2+frame.x)
	{
		p.setLocation(p.getX(),-1);
	}
	else
		if( w.getBounds().y+w.getBounds().height>=mapLocation.y-height/2+frame.y&&w.getBounds().y+w.getBounds().height<=mapLocation.y+height/2+frame.y&&
		w.getBounds().x<=mapLocation.x+width/2+frame.x&&w.getBounds().x+w.getBounds().width>=mapLocation.x-width/2+frame.x)
		{
			p.setLocation(p.getX(),1);
		}
	return p;
}
}