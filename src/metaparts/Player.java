package metaparts;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Vector;

import playground.Map;
import window.Window;
import window.WindowControler;

public class Player {
Point absLocation;
Point mapLocation,mapCell;
Rectangle maxSize;
int width=14,height=25;
Window main;
Vector<Window> additional=new Vector<Window>();
WindowControler controler;
public boolean isMovingLeft=false,isMovingRight=false;
Point2D velocity=new Point2D.Double(0,0),realMapLocation;
public Player(Map m,Window w,WindowControler wc,Rectangle r)
{
	main=w;
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
	absLocation=new Point(mapLocation.x+w.getLocation().x,mapLocation.y+w.getLocation().y);
	w.main.addPlayer(this);
}
public void draw(Graphics2D g) {
	g.setColor(Color.black);
	g.fillRect(mapLocation.x-width/2, mapLocation.y-height/4, width, height*3/4);
	g.setColor(Color.PINK.brighter());
	g.fillRect(mapLocation.x-width/2, mapLocation.y-height/2, width, height/4);
	
}
public boolean isItTouchingSide(Point p)
{
	boolean test=false;
	if(!main.main.isInFrame(mapCell.x+p.x,mapCell.y+p.y))
	{
		return false;
	}
	if(p.y!=0)
	{
	if(p.y>0)
	test=mapCell.y<Map.ROWS-1;
	if(p.y<0)
	test=mapCell.y>0;	
		test=test&&controler.mainMap.bluePrint[mapCell.x][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-main.main.frame.y)*Math.signum(p.getY());
		
		test=test||mapCell.x-1>0&&mapCell.x-1<Map.COLUMNS&&mapLocation.x-width/2<mapCell.x*maxSize.width/Map.COLUMNS&&
				controler.mainMap.bluePrint[mapCell.x-1][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-main.main.frame.y)*Math.signum(p.getY());
		
		test=test||mapCell.x+1>0&&mapCell.x+1<Map.COLUMNS&&mapLocation.x+width/2>(mapCell.x+1)*maxSize.width/Map.COLUMNS&&
				controler.mainMap.bluePrint[mapCell.x+1][(int) (mapCell.y+1*Math.signum(p.getY()))].tangible&&
				(mapLocation.y+height/2*Math.signum(p.getY()))*Math.signum(p.getY())>=((mapCell.y+(1+Math.signum(p.getY()))/2)*maxSize.height/Map.ROWS-main.main.frame.y)*Math.signum(p.getY());
	}
	else if(p.x!=0)
	{
		if(p.x>0)
		test=mapCell.x<Map.COLUMNS-1;
		if(p.x<0)
		test=mapCell.x>0;	
			test=test&&controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-main.main.frame.x)*Math.signum(p.getX());
			
			test=test||mapCell.y-1>0&&mapCell.y-1<Map.ROWS&&mapLocation.y-height/2<mapCell.y*maxSize.height/Map.ROWS&&
					controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y-1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-main.main.frame.x)*Math.signum(p.getX());
			
			test=test||mapCell.y+1>0&&mapCell.y+1<Map.ROWS&&mapLocation.y+height/2>(mapCell.y+1)*maxSize.height/Map.ROWS&&
					controler.mainMap.bluePrint[(int) (mapCell.x+1*Math.signum(p.getX()))][mapCell.y+1].tangible&&
					(mapLocation.x+width/2*Math.signum(p.getX()))*Math.signum(p.getX())>=((mapCell.x+(1+Math.signum(p.getX()))/2)*maxSize.width/Map.COLUMNS-main.main.frame.x)*Math.signum(p.getX());
		}
	return test;
}
public void jump()
{
	
	if(isItTouchingSide(new Point(0,1)))
	velocity.setLocation(velocity.getX(), velocity.getY()-5);
}
public void correctLocation()
{
	if(isItTouchingSide(new Point(-1,0)))
	{	
		mapLocation.x=(mapCell.x)*maxSize.width/Map.COLUMNS-main.main.frame.x+width/2;
		realMapLocation.setLocation(mapLocation.getX(),realMapLocation.getY());
	}
	if(isItTouchingSide(new Point(1,0)))
	{	
		mapLocation.x=(mapCell.x+1)*maxSize.width/Map.COLUMNS-main.main.frame.x-width/2;
		realMapLocation.setLocation(mapLocation.getX(),realMapLocation.getY());
	}
	if(isItTouchingSide(new Point(0,1)))
	{	
		mapLocation.y=(mapCell.y+1)*maxSize.height/Map.ROWS-main.main.frame.y-height/2;
		realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
	}
	if(isItTouchingSide(new Point(0,-1)))
	{	
		mapLocation.y=(mapCell.y)*maxSize.height/Map.ROWS-main.main.frame.y+height/2;
		realMapLocation.setLocation(realMapLocation.getX(),mapLocation.getY());
		if(velocity.getY()<0)
		{
			velocity.setLocation(velocity.getX(),0);
		}
	}
	
}
public void move()
{
	
	if(isMovingLeft)
	{
		realMapLocation.setLocation(realMapLocation.getX()-2, realMapLocation.getY());
	}
	if(isMovingRight)
	{
		realMapLocation.setLocation(realMapLocation.getX()+2, realMapLocation.getY());
	}
	realMapLocation.setLocation(realMapLocation.getX()+velocity.getX(), realMapLocation.getY()+velocity.getY());
	mapLocation.setLocation(realMapLocation);
	correctLocation();
	if(isItTouchingSide(new Point(0,1)))
	{
		if(velocity.getY()>0)
		velocity.setLocation(velocity.getX(),0);
	}
	else
	{
		velocity.setLocation(velocity.getX(),velocity.getY()+0.1);
		
	}
	mapCell.setLocation((mapLocation.x)*Map.COLUMNS/maxSize.width,(mapLocation.y)*Map.ROWS/maxSize.height);
	if()

	
}
}