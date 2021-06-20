package playground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import window.Window;
import window.WindowControler;

public class Playground extends JPanel {

	private static final long serialVersionUID = 1L;
	private Map content;
	private Rectangle frame;
	private static Rectangle completeSize;
	private Window windowFrame;
	
	public Playground(Rectangle r, Map main, Rectangle complete, Window f)
	{
		windowFrame = f;
		frame = r;
		content = main;
		completeSize = complete;
	}
	
	public void updateWindow(Window f)
	{
		windowFrame = f;
	}
	
	public Point getPlaygroundOffset() {
		return new Point(frame.x, frame.y);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		for (int i = frame.x / getCellWidth(); i < frame.getMaxX() / getCellWidth(); i++)
			for (int j = frame.y /completeSize.height; j < frame.getMaxY() / getCellHeight(); j++)
			{
			
				g2d.setColor(content.getCell(i, j).getColor());
				g2d.fill(new Rectangle(i*getCellWidth()-frame.x, j*getCellHeight()-frame.y,
						getCellWidth(), getCellHeight()));
			}
		
		
			WindowControler.getPlayer().draw(g2d, windowFrame);
	}
	
	public boolean isInFrame(int i, int j) {
		return i >= 0 && i < Map.COLUMNS && j >= 0 && j < Map.ROWS &&
				i >= (int) (frame.x / getCellWidth()) && i < (int) (Math.ceil(frame.getMaxX() / getCellWidth())) &&
				j >= (int) (frame.y / getCellHeight()) && j < (int) (Math.ceil(frame.getMaxY() / getCellHeight()));
	}
	
	
	public static int getCellWidth() {
		return completeSize.width / Map.COLUMNS;
	}
	
	public static int getCellHeight() {
		return completeSize.height / Map.ROWS;
	}
}
