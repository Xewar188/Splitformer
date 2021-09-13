package buttons;

import javax.swing.*;
import java.awt.*;

public abstract class Button extends JComponent {

    public abstract boolean tryPress(int x, int y);
    public abstract void draw(Graphics2D g);
}
