package buttons;

import javax.swing.*;

public abstract class Button extends JComponent {

    public abstract boolean tryPress(int x, int y);
}
