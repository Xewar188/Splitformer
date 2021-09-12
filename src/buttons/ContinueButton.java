package buttons;

import windows.MenuWindow;
import windows.WindowControler;

import java.awt.*;

public class ContinueButton extends Button {
    private Rectangle body;
    private WindowControler target;
    public ContinueButton(int x, int y, int sideLength, WindowControler target) {
        body = new Rectangle(x, y, sideLength, sideLength);
        this.target = target;
    }
    public void draw(Graphics2D g) {

        g.setColor(Color.gray);
        g.fill(body);
        g.setColor(Color.white);
        g.fillPolygon(new int[]{(int) (body.getX() + body.getWidth() / 2 - body.getWidth() / 12 + Math.cos(Math.PI * 4 / 3) * body.getWidth() / 3),
                        (int) (body.getX() + body.getWidth() / 2 - body.getWidth() / 12 + Math.cos(Math.PI * 2 / 3) * body.getWidth() / 3),
                        (int) (body.getX() + body.getWidth() / 2 - body.getWidth() / 12 + Math.cos(0) * body.getWidth() / 3)},
                new int[]{(int) (body.getY() + body.getHeight() / 2 + Math.sin(Math.PI * 4 / 3) * body.getWidth() / 3),
                        (int) (body.getY() + body.getHeight() / 2 + Math.sin(Math.PI * 2 / 3) * body.getWidth() / 3),
                        (int) (body.getY() + body.getHeight() / 2 + Math.sin(0) * body.getWidth() / 3)}
                ,3);
    }
    @Override
    public boolean tryPress(int x, int y) {
        if (body.contains(x,y))
            target.completeLevel();
        return body.contains(x,y);
    }
}