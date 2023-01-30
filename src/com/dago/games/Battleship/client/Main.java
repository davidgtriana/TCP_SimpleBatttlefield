package com.dago.games.Battleship.client;

import com.dago.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends JFrame{																							// Create the general class that inherits methods and atrributes of the class JFrame
    public static final long serialVersionUID = 1L;
    public static JPanel mainPanel = new JPanel();
    public static GameFrame app = new GameFrame();

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