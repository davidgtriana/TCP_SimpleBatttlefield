package com.dago.games.Battleship;

import com.dago.AppAdapter;

import javax.swing.*;
import java.awt.*;

public class Cell implements AppAdapter {
    public static final byte HIDDEN = 1;
    public static final byte SELECTED = 3;
    public static final byte MISSED = 4;
    public static final byte ATTACKED = 5;
    String row;
    String column;
    public JLabel lbl;
    public byte state = HIDDEN;
    public Cell(int row, int column){
        this.row = Integer.toString(row);
        this.column = Integer.toString(column);
        this.lbl = new JLabel();
    }
    public String getPosition(){
        return row+column;
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {
        switch (state) {
            case HIDDEN -> this.lbl.setBackground(Color.MAGENTA);
            case SELECTED -> this.lbl.setBackground(Color.YELLOW);
            case MISSED -> this.lbl.setBackground(Color.GRAY);
            case ATTACKED -> this.lbl.setBackground(Color.RED);
        }
    }

    @Override
    public void dispose() {

    }

    public void setState(byte state){
        this.state = state;
        this.update();
    }
}
