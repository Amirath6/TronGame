package view;

import java.awt.*;
import javax.swing.*;

/**
 * ScorePanel is a panel that displays the scores of the players.
 * 
 * @author <a href="mailto:sdoumbouya633@gmail.com"> Sekou DOUMBOUYA </a>
 * @version 1.0
 * 
 */

public class ScorePanel extends JPanel {

      private JProgressBar[] bars;
      private int[] scores;
      private Color[] colors;

  public ScorePanel(int numPlayers) 
  {

        setLayout(new GridLayout(numPlayers, 1));

        bars = new JProgressBar[numPlayers];

        scores = new int[numPlayers];

        colors = new Color[numPlayers];

      for (int i = 0; i < numPlayers; i++) 
    {
      scores[i] = 0;

      colors[i] = Color.getHSBColor((float) i / numPlayers, 1, 1);

      bars[i] = new JProgressBar();

      bars[i].setForeground(colors[i]);

      add(bars[i]);
    }
  }

  public void updateScores(int[] newScores) 
  {
      for (int i = 0; i < scores.length; i++) 
    {
        scores[i] = newScores[i];
        
        bars[i].setValue(scores[i]);
    }
  }
}