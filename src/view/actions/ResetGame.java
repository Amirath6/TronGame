package view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Tron;

public class ResetGame implements ActionListener {

    /**
     * The tron game frame
     */
    private final Tron tron;

    /**
     * Constructor of the ResetGame class
     * @param tron The tron game frame
     */
    public ResetGame(Tron tron) {
        this.tron = tron;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        // We change the status of the game
        tron.setRunning(false);

        // We remove all components from the game panel
        tron.getContentPane().removeAll();

        tron.getTimer().stop();

        // We initialize the game panel
        tron.init();

        // We repaint the game panel
        tron.repaint();

        // We revalidate the game panel
        tron.revalidate();
        
    }
    
}
