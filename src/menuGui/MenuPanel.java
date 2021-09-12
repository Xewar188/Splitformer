package menuGui;

import InputHandlers.ButtonMouseInputHandler;
import buttons.CreateButton;
import buttons.LevelLabel;
import buttons.ReturnButton;
import windows.MenuWindow;
import windows.WindowControler;

import java.awt.*;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Vector;

public class MenuPanel extends  GameMenu{

    Font textFont;
    ReturnButton returnButton;
    CreateButton createButton;
    Vector<LevelLabel> levels = new Vector<>();

    public MenuPanel(Rectangle size, MenuWindow window, WindowControler controller) throws Exception {
        this.setSize(size.width, size.height);
        textFont = new Font("Dialog", Font.BOLD, 20);

        int buttonSize = Math.min(this.getWidth()/12, this.getHeight()/6);
        returnButton = new ReturnButton(buttonSize/10, this.getHeight() - buttonSize - buttonSize/10, buttonSize, window);
        createButton = new CreateButton(this.getWidth() - buttonSize  - buttonSize/10, this.getHeight() - buttonSize - buttonSize/10, buttonSize, controller);

        ButtonMouseInputHandler toAdd= new ButtonMouseInputHandler(this, window.getMouseListeners(), window);
        this.addMouseListener(toAdd);
        this.addMouseMotionListener(toAdd);
        this.addMouseWheelListener(toAdd);
        createLabels(controller);
    }

    private void createLabels(WindowControler controller) {
        File[] files = new File("maps/").listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        for (int i = 0 ; i < files.length; i++) {
            levels.add(new LevelLabel(new Rectangle(this.getWidth()/2 - this.getWidth()/10*7/2,
                    4 + i * 4 + i * this.getHeight()/10,this.getWidth()/10*7,
                    this.getHeight()/10),files[i].getName().substring(0, files[i].getName().length() - 4),controller));
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.fillRect( 0, 0, this.getWidth(), this.getHeight());
        returnButton.draw(g2);
        createButton.draw(g2);
        for (LevelLabel l : levels) {
            l.draw(g2);
        }
        super.paintComponent(g);
    }

    @Override
    public void press(int x, int y) {
        createButton.tryPress(x, y);
        returnButton.tryPress(x, y);
        for (LevelLabel l : levels) {
            l.tryPress(x, y);
        }
    }

    public void scroll(int rotation) {
        for (int i = 0; i < Math.abs(rotation); i++) {
            if ((levels.firstElement().getMinY() >= 4 && Math.signum(rotation) > 0)
                    ||( levels.lastElement().getMaxY() <= this.getHeight() - 4 && Math.signum(rotation) < 0)) {
                return;
            }
            for (LevelLabel l : levels) {
                l.scroll((int) Math.signum(rotation));
            }

        }
    }
}
