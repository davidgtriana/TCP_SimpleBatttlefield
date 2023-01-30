package com.dago.games.Battleship.server;

public class Main {
    private static final TCPServer tcp = new TCPServer();
    private static SimpleBattleship game;
    public static void main(String[] args) {

        startServer();
        waitForConnection();
        while(game.isRunning()){
            if(!getPlayerMovement()){
                if (tcp.msg.equals("EXIT")) {
                    System.out.println("BATTLESHIP: Player has left");
                    System.out.println("BATTLESHIP: Match Canceled");
                    game.running = false;
                }
                continue;
            }

            game.validateMovement(tcp.clientMsg);

            if(!sendResultToPlayer()) continue;

            if(game.hit){
                System.out.println("BATTLESHIP: Game has finished | Player Won");
                game.running = false;
            }

            if(game.attempt == 0){
                System.out.println("BATTLESHIP: Game has finished | Player Lost");
                game.running = false;
            }

        }
        tcp.closeSocket();
        shutDownServer();
    }

    public static void startServer(){
        System.out.println("SERVER: Starting server for Battleship...");
        System.out.println("SERVER: Using port: " + TCPServer.PORT);
        if(!tcp.start()){
            System.out.println("ERROR: " + tcp.msg);
            System.exit(0);
        }
        game = new SimpleBattleship().start();
        System.out.println("BATTLESHIP: Online");

    }

    public static void shutDownServer(){
        System.out.println("SERVER: Shutting down.");
        if(!tcp.shutDown()){
            System.out.println("ERROR: " + tcp.msg);
            System.exit(0);
        }
        System.out.println("SERVER DOWN");
    }

    public static boolean waitForConnection(){
        System.out.println("BATTLESHIP: waiting for client connection...");
        if(!tcp.acceptSocket()){
            System.out.println("ERROR: " + tcp.msg);
            return false;
        }
        System.out.println("BATTLESHIP: Player connected.");
        System.out.println("BATTLESHIP: Starting new match.");
        return true;
    }

    public static boolean getPlayerMovement(){
        System.out.println("BATTLESHIP: Waiting for player movement...");
        if(!tcp.receiveRequest()) return false;
        System.out.println("BATTLESHIP: Player movement: " + tcp.clientMsg);
        return true;
    }


    public static boolean sendResultToPlayer(){
        int response = 0;
        if(game.hit){ response = 1;}
        else{
            if(game.attempt == 1){
                response = 2;
            }
        }
        if(!tcp.sendRequestResponse(response)){
            System.out.println("ERROR: " + tcp.msg);
            return false;
        }
        if(response == 1){
            System.out.println("BATTLESHIP: HIT - GAME OVER");
            return true;
        }
        game.attempt -= 1;
        if(game.attempt != 0 ){
            System.out.println("BATTLESHIP: MISS - GUESS");
            return true;
        }
        return true;
    }
}
