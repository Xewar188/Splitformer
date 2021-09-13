package playground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serial;

import javax.swing.JPanel;

import windows.GameWindow;
import windows.WindowController;

public class Playground extends JPanel {

	@Serial
	private static final long serialVersionUID = 1L;
	private final Map content;
	private final Rectangle frame;
	private static Rectangle completeSize;
	private final GameWindow windowFrame;

	
	public Playground(Rectangle r, Map main, Rectangle complete, GameWindow f)
	{

		windowFrame = f;
		frame = r;
		content = main;
		completeSize = complete;
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
				g2d.fill(new Rectangle(i*getCellWidth() - frame.x, j*getCellHeight() - frame.y,
						getCellWidth(), getCellHeight()));
			}
		
		if (WindowController.getPlayer() != null)
			WindowController.getPlayer().draw(g2d, windowFrame);
	}
	
	public boolean isInFrame(int i, int j) {
		return i >= 0 && i < Map.COLUMNS && j >= 0 && j < Map.ROWS &&
				i >= (frame.x / getCellWidth()) && i < (int) (Math.ceil(frame.getMaxX() / getCellWidth())) &&
				j >= (frame.y / getCellHeight()) && j < (int) (Math.ceil(frame.getMaxY() / getCellHeight()));
	}
	
	
	public static int getCellWidth() {
		return completeSize.width / Map.COLUMNS;
	}
	
	public static int getCellHeight() {
		return completeSize.height / Map.ROWS;
	}
}
