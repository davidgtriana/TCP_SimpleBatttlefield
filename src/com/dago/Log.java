package com.dago;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Log {
    public String history = "";
    public JLabel lbl;

    public Log(){
        this.history="";
        instantiateLabel();
    }

    public void instantiateLabel(){
        lbl = new JLabel();
        lbl.setVerticalAlignment(JLabel.BOTTOM);
        lbl.setBounds(700,300,400,200);
        lbl.setBorder(new EmptyBorder(10,10,10,10));
        lbl.setOpaque(true);
        lbl.setFont(new Font("Calibri", Font.PLAIN, 14));
        lbl.setForeground(Color.WHITE);
        lbl.setBackground(Color.darkGray);
    }

    public void print(String text){
        if(text == "") return;
        history += " - " + text + "<br>";
        lbl.setText(String.format(
                "<html><div style=\"width:%dpx;\">%s</div></html>",
                250,
                history));
    }
}
