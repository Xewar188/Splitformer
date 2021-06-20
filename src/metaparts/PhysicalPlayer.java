package metaparts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import window.KeyboardInputHandler;

public class PhysicalPlayer extends JFrame{

	private static final long serialVersionUID = 1L;

	public PhysicalPlayer(int x, int y) {
		this.setUndecorated(true);
		this.setResizable(false);
		this.setSize(Player.width, Player.height);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(false);

		KeyboardInputHandler.wrapWindow(this);
	
		this.add(new Skin());
	}
}
	class Skin extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setColor(Color.black);
			g2d.fillRect(0, Player.height/4, Player.width, Player.height * 3 / 4 + 1);
			g2d.setColor(Color.PINK.brighter());
			g2d.fillRect(0, 0, Player.width, Player.height / 4);
		}
	}