package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.function.Consumer;

import javax.swing.JFrame;

public class KeyboardInputHandler implements KeyListener {

	private static WindowControler controler;
	private boolean writing = false;
	private String message = "";
	private Consumer<String> endMessage;
	
	public static void setControler (WindowControler c) {
		controler = c;
	}
	
	public KeyboardInputHandler(){}
	
	public static void wrapWindow(JFrame w)
	{
		w.addKeyListener(new KeyboardInputHandler());
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
		if (controler.isInEditorMode())
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
						controler.getMainMap().save(a);
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
					WindowControler.getPlayer().jump();
					break;
				case KeyEvent.VK_D:
					WindowControler.getPlayer().setVelocity(2, WindowControler.getPlayer().getVelocity().getY());
					break;
				case KeyEvent.VK_A:
					WindowControler.getPlayer().setVelocity(-2, WindowControler.getPlayer().getVelocity().getY());
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
			WindowControler.getPlayer().endJump();
			break;
		case KeyEvent.VK_D:
			if (WindowControler.getPlayer().getVelocity().getX() > 0)
				WindowControler.getPlayer().setVelocity(0, WindowControler.getPlayer().getVelocity().getY());
			break;
		case KeyEvent.VK_A:
			if (WindowControler.getPlayer().getVelocity().getX() < 0)
				WindowControler.getPlayer().setVelocity(0, WindowControler.getPlayer().getVelocity().getY());
			break;
		}
		
	}


}
