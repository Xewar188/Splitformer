package window;

import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	Window()
	{
		this.setUndecorated(true);
		this.setResizable(false);
		
		this.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2);
		
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-this.getSize().width/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-this.getSize().height/2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		KeyboardInputHandler.wrapWindow(this);
		MouseInputHandler.wrapWindow(this);
		this.setVisible(true);
	}
	

}
