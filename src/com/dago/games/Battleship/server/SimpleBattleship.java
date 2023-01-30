package com.dago.games.Battleship.server;
import com.dago.games.Battleship.Cell;
import com.dago.games.Battleship.Values;

import java.util.Random;

public class SimpleBattleship {
    public final static int ROWS = Values.ROWS;
    public final static int COLUMNS = Values.COLUMNS;
    public final static int MAX_ATTEMPTS = Values.MAX_ATTEMPTS;
    public int attempt;
    public boolean hit = false;
    public String shipPosition = "";
    public Boolean running;
    public SimpleBattleship(){}
    public SimpleBattleship start(){
        running = true;
        attempt = 5;
        setShipRandomPosition();
        return this;
    }
    public Boolean isRunning(){
        return running;
    }
    public void setShipRandomPosition(){
        Random rand = new Random();
        String row = Integer.toString(rand.nextInt(3));
        String column = Integer.toString(rand.nextInt(3));
        shipPosition = row + column;
        System.out.println("--- Ship has been placed in a random cell: " + shipPosition);
    }

    public void validateMovement(String choice){
        System.out.println("Validating");
        if(choice.equals(this.shipPosition)){
            this.hit = true;
        }
    }

}
