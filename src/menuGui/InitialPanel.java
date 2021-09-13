package menuGui;

import InputHandlers.ButtonMouseInputHandler;
import buttons.Button;
import buttons.ReturnButton;
import buttons.StartButton;
import windows.MenuWindow;

import java.awt.*;
import java.util.Vector;

public class InitialPanel extends GameMenuPanel {

    private final Font textFont;

    private final Vector<Button> buttons = new Vector<>();

    public InitialPanel(Rectangle size, MenuWindow window) throws Exception {
        this.setSize(size.width, size.height);
        textFont = new Font("Dialog", Font.BOLD, 20);

        int buttonSize = Math.min(this.getWidth()/10, this.getHeight()/5);
        buttons.add(new StartButton(this.getWidth()/2 - buttonSize/2, this.getHeight()/2 - buttonSize/2, buttonSize, window));
        buttons.add(new ReturnButton(buttonSize/10, this.getHeight() - buttonSize - buttonSize/10, buttonSize, window));

        ButtonMouseInputHandler toAdd= new ButtonMouseInputHandler(this, window.getMouseListeners(), window);
        this.addMouseListener(toAdd);
        this.addMouseMotionListener(toAdd);
        this.addMouseWheelListener(toAdd);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.fillRect( 0, 0, this.getWidth(), this.getHeight());
        enterGui(g2);
        super.paintComponent(g);
    }

    private void enterGui(Graphics2D g) {

        g.setColor(Color.gray);
        g.setFont(textFont.deriveFont(80f));
        g.drawString("SPLITFORMER",this.getWidth()/2 - g.getFontMetrics(g.getFont()).stringWidth("SPLITFORMER")/2,this.getHeight()/3);
       for (Button b : buttons) {
           b.draw(g);
       }
    }

    @Override
    public void press(int x, int y) {
        for (Button b : buttons) {
            b.tryPress(x, y);
        }
    }

    @Override
    public void scroll(int y) {}
}
