package buttons;

import playground.Map;
import windows.WindowControler;

import java.awt.*;

public class EditButton extends Button{
    private Rectangle body;
    private WindowControler target;
    private Map level;

    public EditButton(int x, int y, int sideLength, WindowControler target, Map level) {
        body = new Rectangle(x, y, sideLength, sideLength);
        this.setBounds(body);
        this.target = target;
        this.level = level;
    }
    public void draw(Graphics2D g) {

        g.setColor(Color.gray);
        g.fill(body);
        g.setColor(Color.white);
        g.fillPolygon(new int[]{(int) (this.getX() + this.getWidth()/2 + this.getWidth()/2 * Math.cos(Math.toRadians(38))),
                        (int) (this.getX() + this.getWidth()/2 + this.getWidth()/2 * Math.cos(Math.toRadians(52))),
                        (int) (this.getX() + this.getWidth()/2 + this.getWidth()/2 * Math.cos(Math.toRadians(218))),
                        (int) (this.getX() + this.getWidth()/2 + this.getWidth()/3 * 2 * Math.cos(Math.toRadians(225))),
                        (int) (this.getX() + this.getWidth()/2 + this.getWidth()/2 * Math.cos(Math.toRadians(232)))},
                new int[]{(int) (this.getY() + this.getHeight()/2 - this.getWidth()/2 * Math.sin(Math.toRadians(38))),
                        (int) (this.getY() + this.getHeight()/2 - this.getWidth()/2 * Math.sin(Math.toRadians(52))),
                        (int) (this.getY() + this.getHeight()/2 - this.getWidth()/2 * Math.sin(Math.toRadians(218))),
                        (int) (this.getY() + this.getHeight()/2 - this.getWidth()/3 * 2 * Math.sin(Math.toRadians(225))),
                        (int) (this.getY() + this.getHeight()/2 - this.getWidth()/2 * Math.sin(Math.toRadians(232)))},
                5);
    }
    @Override
    public boolean tryPress(int x, int y) {
        if (body.contains(x,y)) {
            if (level != null)
                target.startEditing(level);
            return true;
        }
        return false;
    }

    public void scroll(int y) {
        body.setLocation(body.x, body. y + y);
        this.setBounds(body);
    }
}