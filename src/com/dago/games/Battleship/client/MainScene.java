package com.dago.games.Battleship.client;

import com.dago.games.Battleship.Values;
import com.dago.Log;
import com.dago.Scene;
import com.dago.games.Battleship.Cell;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * MainScene is a class that extends the Scene class and serves as the main scene in the game Simple BattleShip.
 * MainScene contains the logic for instantiating visual components, such as cells, buttons and log, and
 * updates the appearance of these components.
 * The class has the following properties:
 *  - ROWS and COLUMNS are the number of rows and columns for the cells in the game
 *  - log is an instance of the Log class which displays information to the user
 *  - tcp is an instance of the TCPClient class which handles communication with the server
 *  - btns is a hashmap of JButtons which are used in the game
 *  - cells is an arraylist of Cell objects which represent the game grid
 *  - selectedCell is a reference to the currently selected cell
 *  - lblBackground is a JLabel used as background image
 *
 *  The class has the following methods:
 *  - MainScene() is a constructor which instantiates visual components and adds them to the scene
 *  - init() initializes the log and displays a welcome message to the user
 *  - update() updates the appearance of cells, buttons and log
 *  - dispose() disconnects from the server and removes all components from the scene
 *  - instantiateVisualComponents() instantiates cells, buttons, background, log
 *  - instantiateBackground() instantiates the background label
 *  - instantiateButtons() instantiates the hashmap of buttons and adds action listeners to them
 *  - instantiateCells() instantiates the arraylist of cells and adds mouse listeners to them
 *  - selectCell(Cell cell) selects the specified cell and updates its appearance
 *  - attack() sends an attack command to the server if a cell is selected and the game is connected
 * @author Dago
 * @version 1.0
 * @date 5-Feb-2023
 */

/**
 * MainScene extends Scene
 */
public class MainScene extends Scene {
    //Constants for Rows and Columns
    public final static int ROWS = Values.ROWS;
    public final static int COLUMNS = Values.COLUMNS;

    //Instance of Log
    public Log log;

    //Instance of TCPClient
    public TCPClient tcp;

    //LinkedHashMap of JButtons
    private LinkedHashMap<String, JButton> btns;

    //ArrayList of cells
    private ArrayList<Cell> cells;

    //Selected cell
    private Cell selectedCell;

    //Background label
    private JLabel lblBackground;

    /**
     * Constructor for MainScene
     */
    public MainScene(){
        //Instantiate the visual components
        this.instantiateVisualComponents();

        //Add cells' labels to the scene
        for(Cell cell : cells) this.add(cell.lbl);

        //Add buttons to the scene
        for(String key : btns.keySet()) this.add(btns.get(key));

        //Add log label to the scene
        this.add(log.lbl);

        //Add background label to the scene
        this.add(lblBackground);
    }

    /**
     * Override method for init
     */
    @Override
    public void init() {
        //Print welcome message to log
        log.print("Welcome to Simple BattleShip.");
        log.print("Press Connect to establish connection with the host.");
    }

    /**
     * Override method for update
     */
    @Override
    public void update(){
        //Update all cells
        for(Cell cell: cells) cell.update();

        //Revalidate and repaint the scene
        this.revalidate();
        this.repaint();
    }

    /**
     * Override method for dispose
     */
    @Override
    public void dispose() {
        //Disconnect from the server
        disconnectToServer();

        //Remove all components
        removeAll();
    }

    /**
     * Method to instantiate visual components
     */
    void instantiateVisualComponents(){

        //Instantiate cells
        instantiateCells();

        //Instantiate Log
        this.log = new Log();

        //Instantiate buttons
        instantiateButtons();

        //Instantiate background
        instantiateBackground();

    }

    /**
     * Method to instantiate background
     */
    private void instantiateBackground() {
        //Create new label for background
        lblBackground = new JLabel();

        //Set bounds for the label
        lblBackground.setBounds(0,0,Values.WIDTH,Values.HEIGHT);

        //Set image icon for the label
        lblBackground.setIcon(new ImageIcon(Main.class.getResource("res/bg_battleship.jpg")));
    }

    /**
     * Method to instantiate Buttons
     */
    private void instantiateButtons() {
        int x=765, y=555, w = 228 , h = 80, gap = 17;
        this.btns = new LinkedHashMap<>();
        JButton btn;

        btn = new JButton();
        btn.setBounds(x,y,w,h);
        btn.addActionListener(e -> connectToServer());
        this.btns.put("CONNECT", btn);

        btn = new JButton();
        btn.setBounds(x+w+gap,y,w,h);
        btn.addActionListener(e -> disconnectToServer());
        this.btns.put("DISCONNECT", btn);

        btn = new JButton();
        btn.setBounds(845,67,315,91);
        btn.addActionListener(e -> attack());
        this.btns.put("ATTACK", btn);

        for(String key : btns.keySet()){
            this.btns.get(key).setContentAreaFilled(false);
            this.btns.get(key).setBorderPainted(false);
            this.btns.get(key).setOpaque(false);
            this.btns.get(key).setBorder(null);
        }
    }

    /**
     * Method to instantiate Cells
     */
    private void instantiateCells() {
        int currentRow = 0;
        int currentColumn = 0;
        int x = 117, y = 198, w=110, h=110, gap = 52;

        this.cells = new ArrayList<>();
        for (currentRow = 0; currentRow < ROWS; currentRow++) {
            for (currentColumn = 0; currentColumn < COLUMNS; currentColumn++) {
                Cell cell = new Cell(currentRow,currentColumn);
                JLabel lbl = cell.lbl;
                lbl.setBounds(x+((gap+w)*currentColumn),y+((gap+h)*currentRow),w,h);
                lbl.setOpaque(false);
                lbl.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {selectCell(cell);}});
                this.cells.add(cell);
            }
        }
    }

    /**
     * The method takes a Cell object as an input and updates its state to SELECTED.
     * If the cell's state is not HIDDEN, the method returns without doing anything.
     * If another cell was previously selected (indicated by the selectedCell member
     * being non-null and in the SELECTED state), it resets its state to HIDDEN before
     * selecting the new cell. The position of the selected cell is logged with the
     * message "Cell selected: (cell position)".
     */
    private void selectCell(Cell cell) {
        if(!this.isConnected()) return;
        if(cell.state != Cell.HIDDEN) return;

        if(this.selectedCell != null && this.selectedCell.state == Cell.SELECTED)
            this.selectedCell.setState(Cell.HIDDEN);

        this.selectedCell = cell;
        this.selectedCell.setState(Cell.SELECTED);
        log.print("Cell selected: " + this.selectedCell.getPosition());
    }

    /**
     * The attack() method simulates the player's attack on the opponent's board.
     * If the player is not connected to the game, the method returns. If the
     * player has not selected a cell to attack, the method prints a message and
     * returns. The selected cell's position is sent to the server and the response
     * (miss, hit, or game over) is received. The state of the selected cell is
     * updated based on the response and the result is logged. The method also
     * disconnects from the server if the response is "game over".
     */
    private void attack(){
        if(!this.isConnected()) return;

        if(selectedCell == null) {
            log.print("Select an objective first");
            return;
        }
        String msg = selectedCell.getPosition();
        String result = "";

        if(!this.tcp.sendMessage(msg)){
            log.print("Failed sent: " + tcp.msg);
            log.print("Try again...");
            return;
        }

        log.print("Position attacked: " + msg);

        int response = this.tcp.response;
        if(response == 0){
            result = "MISS - You missed haha. Try again?";
            selectedCell.setState(Cell.MISSED);
        }
        if(response == 1){
            result = "HIT - You got me!!!";
            selectedCell.setState(Cell.ATTACKED);
        }
        if(response == 2){
            result = "MISS - You missed enough";
            selectedCell.setState(Cell.MISSED);
        }
        log.print("BATTLESHIP: " + result);
        if(response == 2 || response == 1){
            log.print("BATTLESHIP: GAME OVER");
            disconnectToServer();
        }
    }

    /**
     * This code implements the connectToServer() method which attempts to connect
     * to a server via a TCPClient object. The function first checks if the system
     * is already connected, and if so, logs a message indicating that the system
     * is already connected. If not, a new instance of the TCPClient is created,
     * and an attempt to connect to the server is made. If the connection is successful,
     * the function logs a message indicating that the connection was successful
     * and to which host the system connected. If the connection fails, the function
     * logs a failure message and the reason for the failure.
     * The method then calls resetTable() to reset the game table.
     */
    private void connectToServer() {
        if(isConnected()){
            log.print("The system is already connected");
            return;
        }

        this.tcp = new TCPClient();
        log.print(".");
        log.print("Attempting to connect...");
        if(!this.tcp.connectSocket()){
            log.print("Failed connection: " + this.tcp.msg);
            log.print("Try again...");
            this.tcp = null;
            return;
        }
        log.print("Connected to: " + this.tcp.host.toString());
        log.print("BATTLESHIP: A new game has started");
        this.resetTable();
    }

    /**
     * The code implements the disconnectToServer method which
     * attempts to disconnect from a server. If the system is
     * not connected, the method immediately returns. It then
     * prints "Disconnecting..." and calls the disconnect method
     * of the tcp object. If the disconnect method returns false,
     * it prints an error message with the tcp.msg. If the disconnect
     * method returns true, the tcp object is set to null and a message
     * "Disconnection successful" is printed.
     */
    private void disconnectToServer(){
         if(!this.isConnected()) return;
         log.print(".");
         log.print("Disconnecting...");
         if(!this.tcp.disconnect()) {
             log.print("Error: " + this.tcp.msg);
             return;
         }
         this.tcp = null;
         log.print("Disconnection successful");
     }

    /**
     * This method checks if the system is connected to a server or not.
     * @return `true` if the system is connected to a server, `false` otherwise.
     */
    public boolean isConnected(){
        if(tcp == null) {
            log.print("There's not a connection established");
            log.print("Try to connect to the server");
            return false;
        }
        return true;
    }

    /**
     * This method resets the state of all the cells in the table.
     */
    public void resetTable(){
        for(Cell cell : cells)cell.setState(Cell.HIDDEN);
    }

}
