package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.function.Consumer;

public class KeyboardInputHandler implements KeyListener {

	Window target;
	static WindowControler controler;
	boolean writing =false;
	String message="";
	Consumer<String> endMessage;
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
		if(writing)
			return;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(writing)
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
				writing=false;
				message="";
				return;	
			}
			else if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				
				endMessage.accept(message);
				message="";
				writing = false;
				
				return;
			}
			else
			{
				message=message+e.getKeyChar();
				return;
			}
		if(controler.inEditorMode)
		{
			if(e.getKeyCode()==KeyEvent.VK_P||e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
			
			
				controler.exitEditorMode();
			
			}
			if(e.getKeyCode()==KeyEvent.VK_S)
			{	
			writing = true;
			endMessage=a->{
				try {
					controler.mainMap.save(a);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			};
			}
		}
		else
		{
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
			controler.dispose();
			}
		if(e.getKeyCode()==KeyEvent.VK_W)
			{
			WindowControler.player.jump();
			}
		if(e.getKeyCode()==KeyEvent.VK_D)
			{
			WindowControler.player.isMovingRight=true;
			}
		if(e.getKeyCode()==KeyEvent.VK_A)
			{
			WindowControler.player.isMovingLeft=true;
			}
		if(e.getKeyCode()==KeyEvent.VK_M)
			{
			controler.merge();
			}
		if(e.getKeyCode()==KeyEvent.VK_P)
			{
			
			
				controler.enterEditorMode();
			
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(writing)
			return;
		if(e.getKeyCode()==KeyEvent.VK_D)
		{
			WindowControler.player.isMovingRight=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_A)
		{
			WindowControler.player.isMovingLeft=false;
		}
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			WindowControler.player.endJump();
		}
	}

}
