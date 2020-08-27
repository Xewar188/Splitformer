package playground;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

import metaparts.Player;

public class Playground extends JPanel {

	private static final long serialVersionUID = 1L;
	Map content;
	public Rectangle frame,completeSize;
	Player player=null;
	public Playground(Rectangle r,Map main,Rectangle complete)
	{
		frame=r;
		content=main;
		completeSize=complete;
		
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for(int i=frame.x*Map.COLUMNS/completeSize.width;i<frame.getMaxX()*Map.COLUMNS/completeSize.width;i++)
			for(int j=frame.y*Map.ROWS/completeSize.height;j<frame.getMaxY()*Map.ROWS/completeSize.height;j++)
			{
			
				g2d.setColor(content.bluePrint[i][j].main);
				g2d.fill(new Rectangle(i*completeSize.width/Map.COLUMNS-frame.x,j*completeSize.height/Map.ROWS-frame.y,completeSize.width/Map.COLUMNS,completeSize.height/Map.ROWS));
			}
		
		if(player!=null)
			player.draw(g2d);
	}
	public void addPlayer(Player p)
	{
		player=p;
	}
	public void removePlayer()
	{
		player=null;
	}
	public boolean isInFrame(int i, int j) {
		return i>=0&&i<Map.COLUMNS&&j>=0&&j<Map.ROWS&&
				i>=frame.x*Map.COLUMNS/completeSize.width-1&&i<(frame.x+frame.width)*Map.COLUMNS/completeSize.width+1
				&&j>=frame.y*Map.ROWS/completeSize.height-1&&j<(frame.y+frame.height)*Map.ROWS/completeSize.height+1;
	}
}
