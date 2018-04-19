
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

/**
 * This class represents the view.
 * Essentially here we create the JFrame (top-level container),
 * and all the classes which make up the complete GUI.
 */


public class View {
    public static void main(String[] args) throws Exception {
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());

        //Here we create 2 Views with the same
        //model passed as an argument, to visualise
        //the effect of the Observer pattern.
        // That is, when a change happens to one view, the other
        //is being updated accordingly.

        DictionaryModel model = new DictionaryModel();


        KeypadPane keypad = new KeypadPane(model);
        MessagePane view = new MessagePane(model);


        JFrame gui = new JFrame();
        gui.setSize(500, 500);
        gui.setLayout(new BorderLayout());
        gui.add(keypad, BorderLayout.SOUTH);
        gui.add(view, BorderLayout.CENTER);
        gui.setTitle("Predictive Text");
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getID() == KeyEvent.KEY_PRESSED) {
                            int keyCode = e.getKeyCode();
                            switch (keyCode) {
                                case KeyEvent.VK_LEFT:
                                    model.lastMatch();
                                    break;
                                case KeyEvent.VK_RIGHT:
                                    model.nextMatch();
                                    break;
                                case KeyEvent.VK_ENTER:
                                    model.acceptWord();
                                    break;
                                case KeyEvent.VK_BACK_SPACE:
                                    model.removeLastCharacter();
                                    break;
                                case KeyEvent.VK_0:
                                    model.acceptWord();
                                    break;
                                case KeyEvent.VK_2:
                                    model.addCharacter('2');
                                    break;
                                case KeyEvent.VK_3:
                                    model.addCharacter('3');
                                    break;
                                case KeyEvent.VK_4:
                                    model.addCharacter('4');
                                    break;
                                case KeyEvent.VK_5:
                                    model.addCharacter('5');
                                    break;
                                case KeyEvent.VK_6:
                                    model.addCharacter('6');
                                    break;
                                case KeyEvent.VK_7:
                                    model.addCharacter('7');
                                    break;
                                case KeyEvent.VK_8:
                                    model.addCharacter('8');
                                    break;
                                case KeyEvent.VK_9:
                                    model.addCharacter('9');
                                    break;
                            }
                        }
                        return true;
                    }
                });
    }
}
