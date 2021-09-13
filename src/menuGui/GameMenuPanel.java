package menuGui;

import javax.swing.*;

public abstract class GameMenuPanel extends JComponent {
    public abstract void press(int x, int y);
    public abstract void scroll(int y);
}
