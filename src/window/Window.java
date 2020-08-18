package window;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	Window(Dimension size, Dimension pos)
	{
		this.setUndecorated(true);
		this.setResizable(false);
		
		this.setSize(size.width, size.height);
		
		this.setLocation(pos.width, pos.height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		KeyboardInputHandler.wrapWindow(this);
		MouseInputHandler.wrapWindow(this);
		this.setVisible(true);
	}
	

}
