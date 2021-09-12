package InputHandlers;

import windows.WindowControler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

import javax.swing.JFrame;

public class GameKeyboardInputHandler implements KeyListener {

	private static WindowControler controler;
	
	public static void setControler (WindowControler c) {
		controler = c;
	}
	
	public GameKeyboardInputHandler(){}
	
	public static void wrapWindow(JFrame w)
	{
		w.addKeyListener(new GameKeyboardInputHandler());
	}
	
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
			if (controler.gameWindows.lastElement().isFinished()) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					controler.completeLevel();
				return;
			}
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					controler.returnToMenu();
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
				case KeyEvent.VK_T:
					controler.enterEditorMode();
					break;
			}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (controler.gameWindows.lastElement().isFinished())
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
