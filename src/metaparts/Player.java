package metaparts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.Vector;

import playground.Map;
import window.Window;
import window.WindowControler;

public class Player {
public Rectangle frame= null;
public Point mapLocation;
Point mapCell;
Rectangle maxSize;
int width=14,height=25;
public Window main;
Vector<Window> additional=new Vector<Window>();
WindowControler controler;
public boolean isMovingLeft=false,isMovingRight=false;
Point2D velocity=new Point2D.Double(0,0),realMapLocation;
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
				mapCell=new Point(i,j);
				break;
			}
		}
	
	realMapLocation=new Point2D.Double(mapLocation.getX(),mapLocation.getY());
	setMainWindow(w);
	
}
public void draw(Graphics2D g) {
	g.setColor(Color.black);
	g.fillRect(mapLocation.x-width/2, mapLocation.y-height/4, width, height*3/4);
	g.setColor(Color.PINK.brighter());
	g.fillRect(mapLocation.x-width/2, mapLocation.y-height/2, width, height/4);
	
}
public void setMainWindow(Window w)
{
	if(frame!=null)
	{
	mapLocation.x+=frame.x-w.getBounds().x;
	mapLocation.y+=frame.y-w.getBounds().y;
	realMapLocation.setLocation(mapLocation.getX(),mapLocation.getY());
	}
	
	main=w;
	frame=main.getBounds();
	w.main.addPlayer(this);
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
	if(p.y!=0)
	{
		test=w.main.isInFrame(mapCell.x, (int) (mapCell.y+1*Math.signum(p.getY())))&&controler.mainMap.bluePrint[mapCell.x][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
		
		test=test||w.main.isInFrame(mapCell.x-1, (int) (mapCell.y+1*Math.signum(p.getY())))&&mapLocation.x-width/2<mapCell.x*maxSize.width/Map.COLUMNS&&
				controler.mainMap.bluePrint[mapCell.x-1][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
		
		test=test||w.main.isInFrame(mapCell.x+1, (int) (mapCell.y+1*Math.signum(p.getY())))&&mapLocation.x+width/2>(mapCell.x+1)*maxSize.width/Map.COLUMNS&&
				controler.mainMap.bluePrint[mapCell.x+1][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-w.main.frame.y)*Math.signum(p.getY());
	}
	else if(p.x!=0)
	{

			test=w.main.isInFrame((int) (mapCell.x+1*Math.signum(p.getX())),mapCell.y)&&controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
			
			test=test||w.main.isInFrame((int) (mapCell.x+1*Math.signum(p.getX())),mapCell.y-1)&&mapLocation.y-height/2<mapCell.y*maxSize.height/Map.ROWS&&
					controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y-1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
			
			test=test||w.main.isInFrame((int) (mapCell.x+1*Math.signum(p.getX())),mapCell.y+1)&&mapLocation.y+height/2>(mapCell.y+1)*maxSize.height/Map.ROWS&&
					controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y+1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-w.main.frame.x)*Math.signum(p.getX());
					
		}
	return test;
}
public void jump()
{
	
	if(isItTouchingSide(new Point(0,1)))
	velocity.setLocation(velocity.getX(), velocity.getY()-5);
}
public void correctLocation(Point p)
{
	if(p.x<0&&p.y==0)
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
	}
	if(p.x>0&&p.y==0)
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
	}
	if(p.x==0&&p.y>0)
	{	
		if(isItTouchingSide(new Point(0,1)))
		{
		mapLocation.y=(mapCell.y+1)*maxSize.height/Map.ROWS-main.main.frame.y-height/2;
		realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		}
		else if(main==null&&mapLocation.y+frame.y>Toolkit.getDefaultToolkit().getScreenSize().height)
		{
			mapLocation.y=-frame.y;
			realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		}
	}
	if(p.x==0&&p.y<0)
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
	}
	
	
	
}

public void move()
{
	
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
	
	if(isItTouchingSide(new Point(0,1)))
	{
		if(velocity.getY()>0)
		velocity.setLocation(velocity.getX(),0);
	}
	else
	{
		if(velocity.getY()<15)
		velocity.setLocation(velocity.getX(),velocity.getY()+0.1);
		
	}
	correctLocation(new Point(0,1));
	correctLocation(new Point(0,-1));
	
	
	
	if(main!=null)
	{
	mapCell.setLocation((mapLocation.x+main.main.frame.x)*Map.COLUMNS/maxSize.width,(mapLocation.y+main.main.frame.y)*Map.ROWS/maxSize.height);
	if(mapLocation.x<0)
		mapCell.x-=1;
	if(mapLocation.y<0)
		mapCell.y-=1;
	if(!main.getBounds().intersects(new Rectangle(mapLocation.x-width/2+frame.x,mapLocation.y-height/2+frame.y,width,height)))
	{
		main.main.removePlayer();
		main=null;
	}
	}
	else
	{
		for(Window w:controler.windows)
		{
			if(w.getBounds().x>mapLocation.x-width/2+frame.x&&w.getBounds().x<mapLocation.x+width/2+frame.x||
			   w.getBounds().y>mapLocation.y-height/2+frame.y&&w.getBounds().y<mapLocation.y+height/2+frame.y||
			   w.getBounds().x+w.getBounds().width>mapLocation.x-width/2+frame.x&&w.getBounds().x+w.getBounds().width<mapLocation.x+width/2+frame.x||
		       w.getBounds().y+w.getBounds().height>mapLocation.y-height/2+frame.y&&w.getBounds().y+w.getBounds().height<mapLocation.y+height/2+frame.y)
			{
				
				setMainWindow(w);
				
				break;
				
			}
		}
	}
	
	
}
}