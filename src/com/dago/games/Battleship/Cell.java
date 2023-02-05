package com.dago.games.Battleship;

import com.dago.AppAdapter;
import com.dago.games.Battleship.client.Main;
import javax.swing.*;

/**
 * Cell is a class that represents each cell of a grid in a Battleship game.
 * It has several states that indicate the current status of the cell: HIDDEN, SELECTED, MISSED, ATTACKED.
 * The class implements the AppAdapter interface to have methods for init, update and dispose.
 *
 * @author Dago
 * @version 1.0
 * @date 5-Feb-23
 *
 */
public class Cell implements AppAdapter {
    /**
     * A constant that represents the state of the cell being hidden.
     */
    public static final byte HIDDEN = 1;
    /**
     * A constant that represents the state of the cell being selected.
     */
    public static final byte SELECTED = 3;
    /**
     * A constant that represents the state of the cell being missed.
     */
    public static final byte MISSED = 4;
    /**
     * A constant that represents the state of the cell being attacked.
     */
    public static final byte ATTACKED = 5;
    /**
     * A string that represents the row of the cell.
     */
    String row;
    /**
     * A string that represents the column of the cell.
     */
    String column;
    /**
     * A JLabel object that stores the label of the cell.
     */
    public JLabel lbl;
    /**
     * A byte that represents the state of the cell.
     */
    public byte state = HIDDEN;

    /**
     * Constructor method that creates a new cell and sets its position according to the row and column given.
     * @param row The row of the cell.
     * @param column The column of the cell.
     */
    public Cell(int row, int column){
        this.row = Integer.toString(row);
        this.column = Integer.toString(column);
        this.lbl = new JLabel();
    }

    /**
     * Method that returns the position of the cell as a string in the format "row + column".
     * @return A string that represents the position of the cell.
     */
    public String getPosition(){
        return row+column;
    }

    /**
     * Method that initializes the cell.
     */
    @Override
    public void init() {

    }

    /**
     * Method that updates the state of the cell and sets the corresponding image based on the state.
     */
    @Override
    public void update() {
        switch (state) {
            case HIDDEN -> this.lbl.setIcon(new ImageIcon(Main.class.getResource("res/empty.png")));
            case SELECTED -> this.lbl.setIcon(new ImageIcon(Main.class.getResource("res/aiming.png")));
            case MISSED -> this.lbl.setIcon(new ImageIcon(Main.class.getResource("res/wrong.png")));
            case ATTACKED -> this.lbl.setIcon(new ImageIcon(Main.class.getResource("res/right.png")));
        }
    }

    /**
     * Cleans up any resources used by the Cell class.
     */
    @Override
    public void dispose() {

    }

    /**
     * Updates the state of the cell.
     * @param state The new state of the cell.
     */
    public void setState(byte state){
        this.state = state;
        this.update();
    }
}
