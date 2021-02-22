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
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			controler.player.jump();
		}
		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			controler.player.isMovingRight=true;
		}
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			controler.player.isMovingLeft=true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			controler.player.isMovingRight=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			controler.player.isMovingLeft=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			controler.player.endJump();
		}
	}

}
