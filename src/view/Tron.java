package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import application.Constante;
import model.board.Point;
import model.players.Player;
import model.players.SmartPlayer;

import java.awt.*;

import model.Move;
import model.State;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import observer.Listenable;
import observer.Listener;
import view.actions.LaunchGame;
import view.actions.NextStep;
import view.actions.Quitter;
import view.actions.ResetGame;
import view.components.boardGame.BoardGame;
import view.components.boardGame.CellComponent;
import view.components.buttons.*;
import view.components.buttons.Popup;
import view.ui.ProgressBar;

/**
 * Tron is the main class of the game. 
 * It contains the main method and the main class of the game.
 * 
 * @author <a href="mailto:sdoumbouya633@gmail.com"> Sekou DOUMBOUYA </a>
 * @version 1.0
 */

public class Tron extends JFrame implements Listener{

            private JButton bStart, bExit, bReset, bNextStep;

            private int nbTours;

            private long time;

            private Timer timer;

            private int sizeOfboard;

            private int numberOfPlayers;

            private int chooserDepth;

            private JLabel labelNbTours;

            private JLabel labelTimer;

            private State state;

            private BoardGame boardGame;

            private Thread thread;



            private boolean isRunning;

    public Tron() {

            UIManager.put("Button.background", new Color(114, 134, 211));
            UIManager.put("Button.border", BorderFactory.createRaisedBevelBorder());

            init();
            
    }

    public void init(){

            this.nbTours = 0;

            this.time = 0;
            
            try{
                this.state = new State(this.buildPopUpSizeOfGrid(), this.buildPopUpNumberOfPlayers());
            }catch(Exception e){
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }

            this.state.addListener(this);

            JPanel panel1 = new JPanel();

            panel1.setLayout(new GridLayout(3, 1, 10, 10));

            this.buildButtons();

            this.labelNbTours = new JLabel("Turns N° : " + this.nbTours);

            this.labelNbTours.setHorizontalAlignment(JLabel.CENTER);

            // Timer of the game in minutes second and milliseconds
            this.labelTimer = new JLabel("Time : 00:00:00");

            this.labelTimer.setHorizontalAlignment(JLabel.CENTER);

            this.timer = new Timer(10, e -> {

                this.time += 10;

                int minutes = (int) (this.time / 1000 / 60);

                int seconds = (int) (this.time / 1000) - minutes * 60;

                int milliseconds = (int) (this.time - seconds * 1000 - minutes * 60 * 1000);

                this.labelTimer.setText("Time : " + String.format("%02d", minutes) + ":" + String.format("%02d", seconds) + ":" + String.format("%03d", milliseconds));

            });


            JPanel panelTours = new JPanel();

            panelTours.setLayout(new GridLayout(2, 1, 10, 10));

            panelTours.add(this.labelTimer);

            panelTours.add(this.labelNbTours);

            panelTours.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createRaisedBevelBorder()
            ));

            panel1.add(panelTours);

            JPanel panel2 = new JPanel();

            panel2.setLayout(new GridLayout(2, 2, 10, 10));

            

            panel2.add(bStart);

            panel2.add(bNextStep);

            panel2.add(bReset);

            panel2.add(bExit);

            panel2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
            ));
            panel1.add(panel2);

            // Slider for the limit time of reflexion of the smart player. We precise that the time is in milliseconds.
            JPanel panel3 = new JPanel();

            panel3.setLayout(new GridLayout(2, 1, 10, 10));

            JLabel label = new JLabel("Limit time reflexion(s) : " + (Constante.getTimeLimit() == Long.MAX_VALUE ? "∞" : Constante.getTimeLimit()));

            label.setHorizontalAlignment(JLabel.CENTER);

            panel3.add(label);


            panel3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createCompoundBorder(
                    BorderFactory.createRaisedBevelBorder(),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                )
            ));

            JSlider slider = new JSlider(JSlider.HORIZONTAL, 0,  100, 50);

            slider.setMajorTickSpacing(100);

            slider.setMinorTickSpacing(10);

            slider.setPaintTicks(true);

            slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));

            Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
            labelTable.put( 0, new JLabel("0") );
            labelTable.put( 50, new JLabel("50") );
            labelTable.put( 100, new JLabel("∞") );
            slider.setLabelTable( labelTable );
            slider.setPaintLabels(true);


            slider.addChangeListener(e -> {

                JSlider source = (JSlider)e.getSource();

                if (!source.getValueIsAdjusting()) {

                    long time = source.getValue();

                    // If the time is maximum, the smart player will not have a limit time of reflexion
                    if(time == 100) time = Long.MAX_VALUE;

                    Constante.setTimeLimit(time);

                    label.setText("Limit time reflexion(s) : " + (time == Long.MAX_VALUE ? "∞" : time));

                }

            });

            panel3.add(slider);

            panel1.add(panel3);
            
            build();
        
            boardGame = new BoardGame(state.getBoard());

            boardGame.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));

            

            JPanel scores = new JPanel();

            JLabel title = new JLabel("Scores :");

            title.setBackground(Color.BLACK);

            title.setOpaque(true);

            title.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            title.setHorizontalAlignment(JLabel.CENTER);

            title.setVerticalAlignment(JLabel.CENTER);

            title.setFont(new Font("Arial", Font.BOLD, 15));

            title.setForeground(Color.WHITE);

            ProgressBar progessbar = new ProgressBar(state);


            scores.setLayout(new BorderLayout());
            scores.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createLineBorder(Color.BLACK)
            ));

        
            scores.add(title, BorderLayout.PAGE_START);
            scores.add(progessbar);
            this.add(panel1, BorderLayout.LINE_START);
            this.add(boardGame, BorderLayout.CENTER);
            this.add(scores, BorderLayout.PAGE_END);            
        
            this.pack();

    }


    /**
     * Returns the game state
     * 
     * @return the game state
     */
    public State getGameState() {
        return this.state;
    }


    /**
     * Returns the thread of the game
     * 
     * @return the thread of the game
     */
    public Thread getThread() {
        return this.thread;
    }

    /**
     * Check if the game is running
     * 
     * @return true if the game is running, false otherwise
     */
    public boolean isRunning() {
        return this.isRunning;
    }

    /**
     * Set the game running
     * 
     * @param isRunning true if the game is running, false otherwise
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * Returns the start button
     * 
     * @return the start button
     */
    public JButton getStartButton() {
        return this.bStart;
    }

    /**
     * Returns the reset button
     * 
     * @return the reset button
     */
    public JButton getResetButton() {
        return this.bReset;
    }

    /**
     * Returns the exit button
     * 
     * @return the exit button
     */
    public JButton getExitButton() {
        return this.bExit;
    }

    /**
     * Returns the next step button
     * 
     * @return the next step button
     */
    public JButton getNextStepButton() {
        return this.bNextStep;
    }

    /**
     * Ask the user to enter the size of the grid
     */
    public int buildPopUpSizeOfGrid() { 
        
            // Choose the size of the grid with a slider in a pop-up
            this.sizeOfboard = 5; // The default size of the grid(minimum size)
            JLabel label = new JLabel("Size of the grid : " + sizeOfboard);
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 3,  21, 5);
            slider.setMajorTickSpacing(2);
            slider.setMinorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
            Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
            for(int i = 3; i <= 21; i += 2) {
                labelTable.put( i, new JLabel(String.valueOf(i)) );
            }

            slider.setLabelTable( labelTable );
            slider.setPaintLabels(true);
            
            slider.addChangeListener(e -> {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    sizeOfboard = source.getValue();
                    label.setText("Size of the grid : " + sizeOfboard);
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(label);
            panel.add(slider);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JOptionPane.showOptionDialog(this, panel, "Size of the grid", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("ressources/images/aera.png"), new String[]{"OK"}, "OK");
            return sizeOfboard;
    }

    /**
     * Ask the user to enter the information of the players
     */
    public List<Player> buildPopUpNumberOfPlayers()
    {
            // Choose the number of players with a slider in a pop-up
            this.numberOfPlayers = 2; // The default number of players
            JLabel label = new JLabel("Number of players : " + numberOfPlayers);
            JSlider slider = new JSlider(JSlider.HORIZONTAL, 2,  8, 2);
            slider.setMajorTickSpacing(1);
            slider.setMinorTickSpacing(1);
            slider.setPaintTicks(true);
            slider.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
            Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
            for(int i = 2; i <= 8; i+=2) {
                labelTable.put( i, new JLabel(String.valueOf(i)) );
            }

            slider.setLabelTable( labelTable );
            slider.setPaintLabels(true);

            slider.addChangeListener(e -> {
                JSlider source = (JSlider)e.getSource();
                if (!source.getValueIsAdjusting()) {
                    numberOfPlayers = source.getValue();
                    label.setText("Number of players : " + numberOfPlayers);
                }
            });

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(label);
            panel.add(slider);
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            JOptionPane.showOptionDialog(this, panel, "Number of players", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("ressources/images/players.png"), new String[]{"OK"}, "OK");


            


            List<Player> players = new ArrayList<Player>();
            String[] header = {"Player", "Color", "Depth of thought", "Strategy", "heuristic", "Pruning"};
            Object[][] data = new Object[numberOfPlayers][header.length];

            JSlider sliderDepth = new JSlider(JSlider.HORIZONTAL, 1,  10, 1);
                sliderDepth.setMajorTickSpacing(1);
                sliderDepth.setMinorTickSpacing(1);
                sliderDepth.setPaintTicks(true);
                sliderDepth.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
                Hashtable<Integer, JLabel> labelTableDepth = new Hashtable<Integer, JLabel>();
                for(int j = 1; j <= 10; j++) {
                    labelTableDepth.put( j, new JLabel(String.valueOf(j)) );
                }
            
            sliderDepth.setLabelTable( labelTableDepth );
            sliderDepth.setPaintLabels(true);

            for (int i = 0; i < numberOfPlayers; i++) {
                String[] strategies = {"Paranoid", "Maxn"};
                String strategy = (String) JOptionPane.showInputDialog(null, "Choose a strategy for player " + (i + 1), "Strategy", JOptionPane.QUESTION_MESSAGE, new ImageIcon("ressources/images/strategy.png"), strategies, strategies[0]);
                if (strategy == null) {
                    System.exit(0);
                }
                boolean shallowPruning = JOptionPane.showConfirmDialog(null, "Do you want to use shallow pruning for player " + (i + 1) + " ?", "Shallow pruning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

                this.chooserDepth = 1;
                sliderDepth.setValue(1);
                String message = "Depth of thought for player " + (i + 1) + " : " ;
                JLabel labelDepth = new JLabel(message + chooserDepth);
                sliderDepth.addChangeListener(e -> {
                    JSlider source = (JSlider)e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        chooserDepth = source.getValue();
                        labelDepth.setText(message + chooserDepth);
                    }
                });

                JPanel panelDepth = new JPanel();
                panelDepth.setLayout(new BoxLayout(panelDepth, BoxLayout.Y_AXIS));
                panelDepth.add(labelDepth);
                panelDepth.add(sliderDepth);

                JOptionPane.showOptionDialog(this, panelDepth, "Depth of thought", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"OK"}, "OK");

                String heuristique = JOptionPane.showInputDialog(null, "Choose a heuristique for player " + (i + 1), "Heuristique", JOptionPane.QUESTION_MESSAGE, null, new String[]{"Voronoi", "Checker", "OpenSpace", "GSALAP" }, "Voronoi").toString();
                
                SmartPlayer player = new SmartPlayer("Player " + (i + 1), strategy, heuristique, chooserDepth, shallowPruning);
                players.add(player);
                data[i][0] = "Player " + (i + 1);
                data[i][1] = "";
                data[i][2] = chooserDepth;
                data[i][3] = strategy;
                data[i][4] = heuristique;
                data[i][5] = shallowPruning;

            }

            // Set color of the player in the table
            JTable table = new JTable(data, header) {
                @Override
                public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                    Component c = super.prepareRenderer(renderer, row, column);
                    if (column == 1) {
                        c.setBackground(CellComponent.getColor(row));
                    }else{
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            };

            // Set the size of the table in function of the number of players
            table.setPreferredScrollableViewportSize(new Dimension(500, 50 + 30 * numberOfPlayers));


            JScrollPane scrollPane = new JScrollPane(table);

            JOptionPane.showMessageDialog(null, scrollPane, "Players", JOptionPane.INFORMATION_MESSAGE);


            return players;
    }


    /**
     * Initialize all buttons of the game
     */
    public void buildButtons() {
        this.bStart = new RaisedButton(new ImageIcon("ressources/images/play.png"));

        this.bExit = new RaisedButton("Exit");

        this.bReset = new RaisedButton("Reset");

        this.bNextStep = new RaisedButton(new ImageIcon("ressources/images/turn-right.png"));

        bNextStep.addActionListener(new NextStep(this));

        bStart.addActionListener(new LaunchGame(this));

        bExit.addActionListener(new Quitter(this));

        bReset.addActionListener(new ResetGame(this));

    }
    public void build() {
        
        setTitle("Jeu de Tron");
        addWindowListener(new Quitter(this));
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override

    public void notify(Listenable listenable, String s, Object... objects) {

        if(s.equals("playMoves")){

            this.incrementNbTours();
            this.updateNbTours();
            
            // If the game is over
            if(this.getGameState().isOver()){

                this.timer.stop();

                // We notify the user
                System.out.println("The game is over");
                
                // We mention the winner
                Player winner = this.getGameState().getWinner();
                String message = "Nb of turns : " + this.getNbTours() + "\n";
                // Durée total en min sec milli
                message += "Total duration : " + time / 60000 + " min " + (time % 60000) / 1000 + " sec " + (time % 1000) + " milli" + "\n";
                if(winner != null){
                    message += "The winner is " + winner.getName();
                } else {
                    message += "The game is a draw";
                }
                System.out.println(message);
                Popup.show(this, message, "Game over", JOptionPane.INFORMATION_MESSAGE);

            }

        }
        

        if(s.equals("movePlayer")){

            Player player = (Player) objects[0];

            State state = (State) listenable;

            Move move = state.getLastMove(player);

            Point previous = state.getPreviousPosition(player);

            if(!move.getHead().equals(previous)){

                this.boardGame.getCellComponent(previous).setSelected(false);

                this.boardGame.getCellComponent(move.getHead()).setSelected(true);

                this.boardGame.getCellComponent(previous).repaint();

                this.boardGame.getCellComponent(move.getHead()).repaint();

            }
        }

    }


    /**
     * Return the number of turns
     */
    public int getNbTours() {
        return nbTours;
    }

    /**
     * Increment the number of turns
     */
    public void incrementNbTours() {
        this.nbTours++;
    }

    /**
     * Update the value of the label which displays the number of turns
     */
    public void updateNbTours() {
        this.labelNbTours.setText("Turns N° : " + this.nbTours);
    }

    /**
     * Return the time during which the game is launched
     */
    public long getTime() {
        return time;
    }

    /**
     * Set the time during which the game is launched
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Update the value of the label which displays the time during which the game is launched
     */
    public void updateTime() {
        int seconds = (int) (this.time / 1000) % 60 ;
        int minutes = (int) ((this.time / (1000*60)) % 60);
        this.labelTimer.setText("Time : " + minutes + ":" + seconds);
    }

    /**
     * Return the timer
     */
    public Timer getTimer() {
        return timer;
    }







}
