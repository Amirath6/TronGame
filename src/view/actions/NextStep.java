package view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;

import model.Move;
import model.players.Player;
import view.Tron;

public class NextStep implements ActionListener {

    /**
     * The tron game frame
     */
    private final Tron tron;

    /**
     * Constructor of the NextStep class
     * @param tron The tron game frame
     */
    public NextStep(Tron tron) {
        this.tron = tron;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // We check if the game is running or not over
        if(! tron.isRunning() && !tron.getGameState().isOver()){
            
            // We desactivate the next step button
            tron.getNextStepButton().setEnabled(false);

            // We change the image of the next step button
            tron.getNextStepButton().setIcon(new ImageIcon("ressources/images/loading.gif"));

            // We change the status of the game
            tron.setRunning(true);

            // We launch the create a new thread to execute the next step
            new Thread(){
                @Override
                public void run() {
                    
                    tron.getTimer().start();

                    // Each player chooses a move
                    Map<Player, Move> moves = tron.getGameState().getStillPlayingPlayers().parallelStream().collect(
                        Collectors.toMap(
                            player -> player,
                            player -> player.chooseMove(tron.getGameState())
                        )
                    );

                    tron.getTimer().stop();

                    // We print in the console the state of the game before the moves
                    System.out.println(tron.getGameState().getBoard());

                    // We execute the moves
                    tron.getGameState().play(moves);

                    // We print in the console the state of the game after the moves
                    System.out.println(tron.getGameState().getBoard());
                    
                    // We update the game state
                    tron.setRunning(false);

                    // The icon of the next step button is changed
                    tron.getNextStepButton().setIcon(new ImageIcon("ressources/images/turn-right.png"));
                    
                    // We activate the next step button
                    tron.getNextStepButton().setEnabled(true);
                }
            }.start();
        }

    }
    
}
