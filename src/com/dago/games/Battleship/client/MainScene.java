package com.dago.games.Battleship.client;

import com.dago.games.Battleship.Values;
import com.dago.Log;
import com.dago.Scene;
import com.dago.games.Battleship.Cell;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MainScene extends Scene {
    public final static int ROWS = Values.ROWS;
    public final static int COLUMNS = Values.COLUMNS;
    public Log log;
    public TCPClient tcp;
    private LinkedHashMap<String, JButton> btns;
    private ArrayList<Cell> cells;

    private Cell selectedCell;

    public MainScene(){
        this.instantiateVisualComponents();
        for(Cell cell : cells) this.add(cell.lbl);
        for(String key : btns.keySet()) this.add(btns.get(key));
        this.add(log.lbl);
    }
    @Override
    public void init() {
        log.print("Welcome to Simple BattleShip.");
        log.print("Press Connect to establish connection with the host.");
    }
    @Override
    public void update(){
        for(Cell cell: cells) cell.update();
    }
    @Override
    public void dispose() {
        disconnectToServer();
        removeAll();
    }

    void instantiateVisualComponents(){

        int currentRow = 0;
        int currentColumn = 0;
        int x = 10, y = 10, w=100, h=100, gap = 10;

        this.cells = new ArrayList<>();
        for (currentRow = 0; currentRow < ROWS; currentRow++) {
            for (currentColumn = 0; currentColumn < COLUMNS; currentColumn++) {
                Cell cell = new Cell(currentRow,currentColumn);
                JLabel lbl = cell.lbl;
                lbl.setBounds(x+((gap+w)*currentColumn),y+((gap+h)*currentRow),w,h);
                lbl.setHorizontalAlignment(JLabel.CENTER);
                lbl.setText("X");
                lbl.setOpaque(true);
                lbl.setBackground(Color.MAGENTA);
                lbl.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent e) {selectCell(cell);}});
                this.cells.add(cell);
            }
        }
        this.log = new Log();
        this.btns = new LinkedHashMap<>();
        JButton btn;

        btn = new JButton();
        btn.setText("Connect");
        btn.setBounds(700,100,300,100);
        btn.addActionListener(e -> connectToServer());
        this.btns.put("CONNECT", btn);

        btn = new JButton();
        btn.setText("Disconnect");
        btn.setBounds(700,650,150,40);
        btn.addActionListener(e -> disconnectToServer());
        this.btns.put("DISCONNECT", btn);

        btn = new JButton();
        btn.setText("Attack");
        btn.setBounds(700,40,150,40);
        btn.addActionListener(e -> attack());
        this.btns.put("ATTACK", btn);

    }

    private void selectCell(Cell cell) {
        if(!this.isConnected()) return;
        if(cell.state != Cell.HIDDEN) return;

        if(this.selectedCell != null && this.selectedCell.state == Cell.SELECTED)
            this.selectedCell.setState(Cell.HIDDEN);

        this.selectedCell = cell;
        this.selectedCell.setState(Cell.SELECTED);
        log.print("Cell selected: " + this.selectedCell.getPosition());
    }

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

    public boolean isConnected(){
        if(tcp == null) {
            log.print("There's not a connection established");
            log.print("Try to connect to the server");
            return false;
        }
        return true;
    }

    public void resetTable(){
        for(Cell cell : cells)cell.setState(Cell.HIDDEN);
    }

}
