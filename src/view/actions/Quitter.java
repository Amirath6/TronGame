package view.actions;

import view.Tron;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Class representing an action to quit the game
 *
 * @author <a href="mailto:sekou.doumbouya@etu.unicaen.fr"> Sekou DOUMBOUYA </a>
 * @version 1.0
 */
public class Quitter implements ActionListener, WindowListener {

    /* Class attributes */

    private final Tron frame;

    /**
     * class constructor
     *
     * @param frame upper level window
     */

    public Quitter(Tron frame) {
        this.frame = frame;
    }

    /**
     * stop the game and close the window if the user confirms the action
     */
    private void arreterTout() {

        int result = JOptionPane.showConfirmDialog(frame.getContentPane(), "VOULEZ-VOUS QUITTER ?", "ATTENTION FERMETURE!", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.out.println("Fin de l'excecution, on ferme tout. Bonne journée.");
            // this.frame.getTimer().stop();
            this.frame.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        arreterTout();

    }

    @Override
    public void windowActivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowClosed(WindowEvent e) {
        // TODO document why this method is empty
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // TODO Auto-generated method stub
        arreterTout();
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowIconified(WindowEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void windowOpened(WindowEvent e) {
        // TODO Auto-generated method stub

    }

}
