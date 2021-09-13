package buttons;

import playground.Map;
import windows.WindowController;

import java.awt.*;

public class LevelLabel extends Button{

    String levelName;
    Rectangle body;
    EditButton editButton;
    PlayButton playButton;
    public LevelLabel(Rectangle position, String levelName, WindowController control) {
        this.levelName = levelName;
        body = position;
        Map targetMap;
        this.setBounds(body);
        try {
            targetMap = new Map(levelName);
        }
        catch (Exception e) {
            editButton = new EditButton(position.x + position.width - 8 - 2 * position.height + 8, position.y + 2,
                    position.height - 4, control, null);
            playButton = new PlayButton(position.x + position.width - 4 - position.height + 4, position.y + 2,
                    position.height - 4, control, null);
            levelName = "ERROR";
            return;
        }

        editButton = new EditButton(position.x + position.width - 8 - 2 * position.height + 8, position.y + 2,
                position.height - 4, control, targetMap);
        playButton = new PlayButton(position.x + position.width - 4 - position.height + 4, position.y + 2,
                position.height - 4, control, targetMap);


    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.gray.brighter());
        g.fill(body);
        g.setColor(Color.gray);
        g.setFont(new Font("Dialog", Font.BOLD, 20).deriveFont((float) (body.height - 19)));
        g.drawString(levelName,body.x + 2, body.y + body.height - 17);
        editButton.draw(g);
        playButton.draw(g);
    }

    @Override
    public boolean tryPress(int x, int y) {
        if (editButton.tryPress(x, y))
            return true;
        else
            return playButton.tryPress(x, y);
    }

    public int getMaxY() {
        return body.y + body.height;
    }

    public  int getMinY() {
        return body.y;
    }

    public void scroll(int y) {
        body.setLocation(body.x, body. y + y);
        editButton.scroll(y);
        playButton.scroll(y);
    }
}
