package buttons;

import playground.Map;
import windows.WindowController;

import java.awt.*;

public class PlayButton extends Button{
    private final Rectangle body;
    private final WindowController target;
    private final Map level;

    public PlayButton(int x, int y, int sideLength, WindowController target, Map level) {
        body = new Rectangle(x, y, sideLength, sideLength);
        this.setBounds(body);
        this.target = target;
        this.level = level;

    }

    @Override
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
        if (body.contains(x,y)) {
            if (level != null)
                target.startLevel(level);
            return true;
        }
        return false;
    }

    public void scroll(int y) {
        body.setLocation(body.x, body. y + y);
        this.setBounds(body);
    }
}