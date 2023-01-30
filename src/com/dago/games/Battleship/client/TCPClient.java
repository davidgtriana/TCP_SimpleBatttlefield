package com.dago.games.Battleship.client;

import com.dago.games.Battleship.Values;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {
    public static int PORT = Values.PORT;
    InetAddress host;
    Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;

    public String msg = "";
    public int response;

    TCPClient(){}
    TCPClient(int port) { PORT = port;}

    public boolean connectSocket() {
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostAddress(), PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            msg = "Connection Refused";
            return false;
        }
        return true;
    }

    public boolean disconnect(){
        try {
            oos.writeObject("EXIT");
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("ERROR: " + e);
            msg = "Disconnection refused";
            return false;
        }
        return true;
    }

    public Boolean sendMessage(Object obj){
        try {
            oos.writeObject(obj);
            response = (Integer)ois.readObject();
        } catch (IOException | ClassNotFoundException  e) {
            System.out.println("ERROR:" + e);
            msg = "Message refused.";
            return false;
        }
        return true;
    }
}

