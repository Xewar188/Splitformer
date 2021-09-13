package InputHandlers;

import windows.WindowController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class GameKeyboardInputHandler implements KeyListener {

	private static WindowController controller;
	
	public static void setController(WindowController c) {
		controller = c;
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
			if (controller.gameWindows.lastElement().isFinished()) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					controller.completeLevel();
				return;
			}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE -> controller.returnToMenu();
			case KeyEvent.VK_W -> WindowController.getPlayer().jump();
			case KeyEvent.VK_D -> WindowController.getPlayer().setVelocity(2, WindowController.getPlayer().getVelocity().getY());
			case KeyEvent.VK_A -> WindowController.getPlayer().setVelocity(-2, WindowController.getPlayer().getVelocity().getY());
			case KeyEvent.VK_M -> controller.merge();
			case KeyEvent.VK_T -> controller.enterEditorMode();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (controller.gameWindows.lastElement().isFinished())
			return;

		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			WindowController.getPlayer().endJump();
			break;
		case KeyEvent.VK_D:
			if (WindowController.getPlayer().getVelocity().getX() > 0)
				WindowController.getPlayer().setVelocity(0, WindowController.getPlayer().getVelocity().getY());
			break;
		case KeyEvent.VK_A:
			if (WindowController.getPlayer().getVelocity().getX() < 0)
				WindowController.getPlayer().setVelocity(0, WindowController.getPlayer().getVelocity().getY());
			break;
		}
		
	}


}
