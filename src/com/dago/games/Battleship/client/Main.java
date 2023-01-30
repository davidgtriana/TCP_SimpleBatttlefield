/**
 * Main class for the Battleship client.
 * This class creates a JFrame and sets its default close operation and bounds.
 * The main panel is set with a null layout and added as the content pane.
 * The window adapter is used to dispose the game frame when the window is closing.
 *
 * @author Dago
 * @version 1.0
 */
package com.dago.games.Battleship.client;

import com.dago.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame{
    public static final long serialVersionUID = 1L;
    public static JPanel mainPanel = new JPanel();
    public static GameFrame app = new GameFrame();

    /**
     * The main method for the Battleship client.
     * It creates a new Main instance and sets it to be visible.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main frame = new Main();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * The constructor for the Main class.
     * It sets the default close operation, bounds, layout for the main panel, and adds the window adapter.
     */
    Main(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0 , 1280, 720);
        mainPanel.setLayout(null);
        setContentPane(mainPanel);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                app.dispose();
            }});
    }
}