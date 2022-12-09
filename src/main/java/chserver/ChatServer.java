/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 0802n
 */
public class ChatServer implements Runnable{
    private Map <Integer, Socket> mapClient = new TreeMap <>();

    @Override
    public void run() {     
        ServerSocket server;   
        try {                     
            server = new ServerSocket(8887);            
            System.out.println("Waiting connection...");
            int numberClient =  1;
            Socket client = null;
            
            while(true){
                try {
                    client = server.accept();
                    Thread clientThread = new Thread(new ClientThread(client, this, numberClient));
                    clientThread.setDaemon(true);
                    clientThread.start();
                    mapClient.put(numberClient, client);
                    numberClient++;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }            
            
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
       
    }

    void sendMessageForAllClients(int numberClient, String clientMessage) {
        
        Set<Integer> keys = mapClient.keySet();        

        BufferedWriter out = null;      
        
        for(int number: keys){        
                   
            if(number!=numberClient){
                try {
                    out = new BufferedWriter(new OutputStreamWriter(mapClient.get(number).getOutputStream()));
                    
                    out.write("Client " + numberClient + ": " + clientMessage);
                    out.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
