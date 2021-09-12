package windows;

import InputHandlers.EditKeyboradInputHandler;
import InputHandlers.EditMouseInputHandler;
import InputHandlers.GameKeyboardInputHandler;
import InputHandlers.GameMouseInputHandler;
import cells.CellBase;
import playground.Map;
import playground.Playground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EditWindow extends GameWindow {
    private boolean isInWritingMode = false;
    private final Overlay overlay;
    public EditWindow(Dimension size, Dimension pos, WindowControler con, Rectangle maxSize, int mainx, int mainy)
    {
        super(size, pos, con, maxSize, mainx, mainy);
        for (MouseListener m : this.getMouseListeners())
            this.removeMouseListener(m);
        for (MouseMotionListener m : this.getMouseMotionListeners())
            this.removeMouseMotionListener(m);
        for (MouseWheelListener m : this.getMouseWheelListeners())
            this.removeMouseWheelListener(m);
        for (KeyListener m : this.getKeyListeners())
            this.removeKeyListener(m);
        EditKeyboradInputHandler.wrapWindow(this);
        EditMouseInputHandler.wrapWindow(this);
        overlay = new Overlay(this.getWidth(), this.getHeight(),"");
        this.add(overlay,null,0);
    }

    public void setSaveName(String s) {
        overlay.setMessage(s);
    }

    public void startWriting() {
        isInWritingMode = true;
        overlay.startWriting();
    }

    public void endWriting() {
        isInWritingMode = false;
        overlay.endWriting();
    }

    public boolean isWriting() {
        return isInWritingMode;
    }
}

class Overlay extends JComponent {

    private boolean isInWritingMode = false;
    private String message = "";
    public Overlay(int width, int height, String message) {
        this.setSize(width, height);
        this.message = message;
    }
    @Override
    public void paintComponent(Graphics g) {
        if (isInWritingMode) {
            g.setColor(new Color(172, 172, 172, 122));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            g.setColor(Color.gray);
            g.fillRect(this.getWidth() / 2 - 200, this.getHeight() / 2 - 20, 400, 40);

            g.setColor(Color.gray.brighter());

            g.fillRect(this.getWidth() / 2 - 180, this.getHeight() / 2 - 15, 360, 30);

            g.setColor(Color.black);
            g.setFont(new Font("Dialog", Font.BOLD, 20).deriveFont(26f));

            g.drawString(this.message, this.getWidth() / 2 - 180 + 4, this.getHeight() / 2 + 15 - 2);
        }
        else {
            g.setColor(WindowControler.getCurrentColor());
            g.fillRect(4,4,8,8);
        }
    }

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    public void startWriting() {
        isInWritingMode = true;
    }

    public void endWriting() {
        isInWritingMode = false;
    }
}
