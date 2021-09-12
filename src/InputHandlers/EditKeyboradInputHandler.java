package InputHandlers;

import windows.EditWindow;
import windows.WindowControler;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.function.Consumer;

public class EditKeyboradInputHandler implements KeyListener {

    private static WindowControler controler;
    private boolean writing = false;
    private StringBuilder message = new StringBuilder();
    private Consumer<String> endMessage;
    private EditWindow target;

    public static void setControler (WindowControler c) {
        controler = c;
    }

    public EditKeyboradInputHandler(EditWindow w){
        target = w;
    }

    public static void wrapWindow(EditWindow w)
    {
        w.addKeyListener(new EditKeyboradInputHandler(w));
    }

    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (writing)
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            {
                writing = false;
                target.endWriting();
                message = new StringBuilder();
                return;
            }
            else if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {

                endMessage.accept(message.toString());
                message = new StringBuilder();
                target.endWriting();
                controler.endEditing();
                writing = false;
                return;
            }
            else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
            {
                message.deleteCharAt(message.length() - 1);
                target.setSaveName(message.toString());
                return;
            }
            else
            {
                if (message.length() == 14)
                    return;
                message.append(e.getKeyChar());
                target.setSaveName(message.toString());
                return;
            }
        if (e.getKeyCode() == KeyEvent.VK_T)
            {
                controler.exitEditorMode();
            }

            if(e.getKeyCode() == KeyEvent.VK_S && controler.isInEditorMode())
            {

                writing = true;
                target.startWriting();
                target.setSaveName("");
                endMessage = a -> {
                    try {
                        controler.getMainMap().save(a);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                };
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                controler.endEditing();
            }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}
