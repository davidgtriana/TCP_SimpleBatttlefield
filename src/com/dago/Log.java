package com.dago;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Log class handles the text that is printed on a JLabel.
 * The label helps to register everything that happens in the game.
 * @author Dago
 * @version 1.0
 * @date 5-Feb-2023
 */
public class Log {

    /** String to store the history of logs. */
    public String history = "";

    /** JLabel to display the log history. */
    public JLabel lbl;

    /**
     * Constructor to initialize the history of logs.
     */
    public Log(){
        this.history="";
        instantiateLabel();
    }
    /**
     * Method to create a JLabel and set its properties.
     */
    public void instantiateLabel(){
        lbl = new JLabel();
        lbl.setVerticalAlignment(JLabel.BOTTOM);
        lbl.setBounds(806,226,393,260);
        lbl.setBorder(new EmptyBorder(20,20,20,20));
        lbl.setOpaque(false);
        lbl.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl.setForeground(Color.WHITE);
    }
    /**
     * Method to add the text to the log history.
     * @param text The text to be added to the log history.
     */
    public void print(String text){
        if(text == "") return;
        history += " - " + text + "<br>";
        lbl.setText(String.format(
                "<html><div style=\"width:%dpx;\">%s</div></html>",
                250,
                history));
    }
}


