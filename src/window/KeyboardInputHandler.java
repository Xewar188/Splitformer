package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputHandler implements KeyListener {

	Window target;
	static WindowControler controler;
	KeyboardInputHandler(Window t)
	{
		target=t;
	}
	static void wrapWindow(Window w)
	{
		KeyboardInputHandler h= new KeyboardInputHandler(w);
		w.addKeyListener(h);
	}
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			controler.dispose();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
