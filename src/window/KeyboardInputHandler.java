package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.function.Consumer;

public class KeyboardInputHandler implements KeyListener {

	Window target;
	static WindowControler controler;
	boolean writing = false;
	String message = "";
	Consumer<String> endMessage;
	
	KeyboardInputHandler(Window t)
	{
		target = t;
	}
	
	static void wrapWindow(Window w)
	{
		w.addKeyListener(new KeyboardInputHandler(w));
	}
	
	public void keyTyped(KeyEvent e) {
		if (writing)
			return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (writing)
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				writing = false;
				message = "";
				return;	
			}
			else if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				
				endMessage.accept(message);
				message = "";
				writing = false;
				return;
			}
			else
			{
				message = message + e.getKeyChar();
				return;
			}
		if (controler.inEditorMode)
		{
			if (e.getKeyCode() == KeyEvent.VK_P || 
					e.getKeyCode() == KeyEvent.VK_ESCAPE)
			{
				controler.exitEditorMode();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_S)
			{	
			
				writing = true;
				endMessage=a->{
					try {
						controler.mainMap.save(a);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				};
			}
		}
		else
		{
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					controler.dispose();
					break;
				case KeyEvent.VK_W:
					WindowControler.player.jump();
					break;
				case KeyEvent.VK_D:
					WindowControler.player.velocity.setLocation(2, WindowControler.player.velocity.getY());
					break;
				case KeyEvent.VK_A:
					WindowControler.player.velocity.setLocation(-2, WindowControler.player.velocity.getY());
					break;
				case KeyEvent.VK_M:
					controler.merge();
					break;
				case KeyEvent.VK_P:
					controler.enterEditorMode();
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(writing)
			return;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			WindowControler.player.endJump();
			break;
		case KeyEvent.VK_D:
			if (WindowControler.player.velocity.getX() > 0)
				WindowControler.player.velocity.setLocation(0, WindowControler.player.velocity.getY());
			break;
		case KeyEvent.VK_A:
			if (WindowControler.player.velocity.getX() < 0)
				WindowControler.player.velocity.setLocation(0, WindowControler.player.velocity.getY());
			break;
		}
		
	}

}
