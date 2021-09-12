package windows;

import java.awt.*;

import javax.swing.*;

import InputHandlers.GameKeyboardInputHandler;
import InputHandlers.GameMouseInputHandler;
import buttons.ContinueButton;
import cells.CellBase;
import playground.Map;
import playground.Playground;

public class GameWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	int x, y;
	public Playground main;
	private boolean isFinished = false;
	private EndMessage end;
	public GameWindow(Dimension size, Dimension pos, WindowControler controler, Rectangle maxSize, int mainx, int mainy)
	{
		this.setUndecorated(true);
		this.setResizable(false);
		x = mainx;
		y = mainy;
		this.setSize(size.width, size.height);
		this.setLocation(pos.width, pos.height);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		main = new Playground(new Rectangle(x, y, this.getWidth(), this.getHeight()),
												controler.getMainMap(), maxSize, this);
		end = new EndMessage(this.getWidth(), this.getHeight(), controler);
		this.add(main);
		GameKeyboardInputHandler.wrapWindow(this);
		GameMouseInputHandler.wrapWindow(this);
		this.setVisible(true);
		this.add(end, null, 0);
		end.setVisible(false);
	}

	public boolean isFinished() {
		return isFinished;
	}
	public void finish() {
		end.setVisible(true);
		isFinished = true;
	}

	public boolean tryPress(int x, int y) {
		if (!isFinished)
			return false;
		return end.tryPress(x, y);

	}

}
class EndMessage extends JComponent {

	ContinueButton continueButton;
	public EndMessage(int width, int height, WindowControler cont) {
		this.setSize(width, height);
		continueButton = new ContinueButton(this.getWidth()/2 - this.getWidth() / 20,
				this.getHeight()/2 - this.getWidth() / 20, this.getWidth()/10, cont);
	}
	@Override
	public void paintComponent(Graphics g) {

			g.setColor(new Color(172, 172, 172, 122));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());


			g.setColor(Color.YELLOW.darker());
			g.setFont(new Font("Dialog", Font.BOLD, 20).deriveFont(80f));

			g.drawString("CONGRATULATIONS", this.getWidth() / 2 - g.getFontMetrics(g.getFont()).stringWidth("CONGRATULATIONS")/2,
					this.getHeight() / 3);
			continueButton.draw((Graphics2D) g);

	}

	public boolean tryPress(int x, int y) {
		return continueButton.tryPress(x, y);
	}
}
