package view.components.buttons;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class RaisedButton extends JButton {

    public RaisedButton(String text) {
        super(text);
        build();
    }

    public RaisedButton(String text, Color color) {
        super(text);
        setBackground(color);
        build();
    }

    public RaisedButton(Icon icon) {
        super(icon);
        build();
    }

    public void build() {
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }



    
    
}
