package view.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Move;
import model.players.Player;
import view.Tron;
import view.components.buttons.Popup;

public class LaunchGame implements ActionListener {

    /**
     * The tron game frame
     */
    private final Tron tron;


    /**
     * Constructor of the LaunchGame class
     * @param tron The tron game frame
     */
    public LaunchGame(Tron tron) {
        this.tron = tron;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // We check if the game is running or not over
        if(!tron.isRunning() && !tron.getGameState().isOver()){
            
            // We change the status of the game
            tron.setRunning(true);
            
            // We change the icon of the launch game button
            tron.getStartButton().setIcon(new ImageIcon("ressources/images/stop.png"));

            // We launch the game in a new thread
            new Thread(){
                @Override
                public void run() {
                    
                    // We continue to play while the game is not over or the user has not stopped the game
                    while(!tron.getGameState().isOver() && tron.isRunning()){

                        tron.getTimer().start();
                        
                        // Each player chooses a move
                        Map<Player, Move> moves = tron.getGameState().getStillPlayingPlayers().parallelStream().collect(
                            Collectors.toMap(
                                player -> player,
                                player -> player.chooseMove(tron.getGameState())
                            )
                        );
                        
                        // We print in the console the state of the game before the moves
                        System.out.println("Board state before moves:");
                        System.out.println(tron.getGameState().getBoard());
                        
                        // We execute the moves
                        tron.getGameState().play(moves);

                    }

                    // We stop the timer
                    tron.getTimer().stop();

                    // We change the status of the game
                    tron.setRunning(false);

                    // We change the icon of the launch game button
                    tron.getStartButton().setIcon(new ImageIcon("ressources/images/play.png"));

                }
            }.start();
        } else{
            // If We press the button while the game is running or not over, we stop the game
            tron.setRunning(false);

            // We change the icon of the launch game button to mark that the game is in the process of stopping
            tron.getStartButton().setIcon(new ImageIcon("ressources/images/loading.gif"));
        }
    }
    
}
