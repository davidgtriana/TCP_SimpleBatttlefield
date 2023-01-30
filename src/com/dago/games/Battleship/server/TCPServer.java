package com.dago.games.Battleship.server;

import com.dago.games.Battleship.Values;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static final int PORT = Values.PORT;
    public ServerSocket server;
    public Socket socket;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public String clientMsg = "";

    public String msg;

    public TCPServer(){}

    public boolean start(){
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            msg = "ERROR: The server couldn't be started";
            System.out.println(msg + e);
            return false;
        }
        return true;
    }
    public boolean closeSocket(){
        try {
            ois.close();
            oos.close();
            socket.close();
        } catch (IOException e) {
            msg = "ERROR: There was an error while closing the socket";
            System.out.println(msg + e);
            return false;
        }
        return true;
    }

    public boolean shutDown(){
        try {
            server.close();
        } catch (IOException e) {
            System.out.println("TPC ERROR: " + e);
            msg = "The server didn't shut down";
            return false;
        }
        return true;
    }

    public boolean acceptSocket(){
        try {
            socket = server.accept();
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("TPC ERROR: " + e);
            msg = "The socket couldn't be connected";
            return false;
        }
        return true;
    }

    public boolean receiveRequest(){
        try {
            clientMsg = (String) ois.readObject();
            if(clientMsg.equalsIgnoreCase("EXIT")){
                msg = "EXIT";
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            msg = "ERROR: There was an error while reading the request ";
            System.out.println(msg + e);
            return false;
        }
        return true;
    }

    public boolean sendRequestResponse(Object obj){
        try {
            oos.writeObject(obj);
        } catch (IOException e) {
            msg = "ERROR: The response couldn't be sent ";
            System.out.println(msg + e);
            return false;
        }
        return true;
    }


}
