package view.components.buttons;

import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Popup {

        private static final String DEFAULT_BACKGROUND = UIManager.get("OptionPane.background").toString();
        private static final String DEFAULT_FOREGROUND = UIManager.get("OptionPane.foreground").toString();
        private static final String DEFAULT_PANEL_BACKGROUND = UIManager.get("Panel.background").toString();
        


        private static final Color BACKGROUND_COLOR = new Color(238, 238, 238);

        

        public static void show(Component parent, String message) {
            try {
                before();
                JOptionPane.showMessageDialog(parent, message);
            } finally {
                after();
            }
        }
        

        public static void show(Component parent, String message, String title) {
            try {
                before();
                JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
            } finally {
                after();
            }
        }

        public static void show(String message, String title) {
            show(null, message, title);
        }


        public static void show(Component parent, String message, String title, Icon icon) {
            try {
                before();
                JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE, icon);
            } finally {
                after();
            }
        }

        public static void show(String message, String title, Icon icon) {
            show(null, message, title, icon);
        }


        public static void show(Component parent, String message, String title, int messageType) {
            try {
                before();
                JOptionPane.showMessageDialog(parent, message, title, messageType);
            } finally {
                after();
            }
        }

        public static void show(String message, String title, int messageType) {
            show(null, message, title, messageType);
        }

        public static void show(Component parent, String message, String title, int messageType, Icon icon) {
            try {
                before();
                JOptionPane.showMessageDialog(parent, message, title, messageType, icon);
            } finally {
                after();
            }
        }

        public static void show(String message, String title, int messageType, Icon icon) {
            show(null, message, title, messageType, icon);
        }


        private static void before() {
            // Warning icon
            UIManager.put("OptionPane.warningIcon", new ImageIcon("ressources/images/warning.png")
            );
            // Error icon
            UIManager.put("OptionPane.errorIcon", new ImageIcon("ressources/images/cross.png")
            );
            UIManager.put("OptionPane.background", BACKGROUND_COLOR);
            UIManager.put("Panel.background", BACKGROUND_COLOR);
        }

        private static void after() {
            UIManager.put("OptionPane.background", DEFAULT_BACKGROUND);
            UIManager.put("OptionPane.foreground", DEFAULT_FOREGROUND);
            UIManager.put("Panel.background", DEFAULT_PANEL_BACKGROUND);
        }



}
