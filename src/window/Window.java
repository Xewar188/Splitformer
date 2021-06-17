package window;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;

import playground.Map;
import playground.Playground;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	int x, y;
	public Playground main;
	Window(Dimension size, Dimension pos, Map map, Rectangle maxSize, int mainx, int mainy)
	{
		this.setUndecorated(true);
		this.setResizable(false);
		x = mainx;
		y = mainy;
		this.setSize(size.width, size.height);
		this.setLocation(pos.width, pos.height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		main = new Playground(new Rectangle(x, y, this.getWidth(), this.getHeight()),
												map, maxSize, this);
		this.add(main);
		KeyboardInputHandler.wrapWindow(this);
		MouseInputHandler.wrapWindow(this);
		this.setVisible(true);
	}
	

}
