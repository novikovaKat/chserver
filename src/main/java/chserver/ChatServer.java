/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chserver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        System.out.println("Waiting connection...");
        int numberClient =  1;
        Socket client;

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
         
    }

    void sendMessageForAllClients(int numberClient, String clientMessage) {
        
        Set<Integer> keys = mapClient.keySet();        
        BufferedWriter out;
       
        ArrayList <Integer> exceptionElements = new ArrayList<>();
        for(Integer number: keys){             
           
            if(Integer.compare(number, numberClient)!=0){
                
                try {
                    out=new BufferedWriter(new OutputStreamWriter(mapClient.get(number).getOutputStream()));
                    out.write("Client " + numberClient + ": " + clientMessage + "\n");
                    out.flush();
                    
                } catch (IOException e) {
                    exceptionElements.add(number);
                }
            }
        }
        
        for(Integer element: exceptionElements){
            mapClient.remove(element);
        }
    }
}
