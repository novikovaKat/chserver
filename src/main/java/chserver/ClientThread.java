/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


/**
 *
 * @author 0802n
 */
public class ClientThread implements Runnable{
    
    private Socket clientSocket;
    private ChatServer chatServer;
    private int numberClient;

    public ClientThread(Socket clientSocket, ChatServer chatServer, int numberClient) {
        this.clientSocket = clientSocket;
        this.chatServer = chatServer;
        this.numberClient = numberClient;  
    }
    
    @Override
    public void run() {
        
        BufferedReader in = null;     
            
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        System.out.println("Client " + numberClient + " has been connected");
        String clientMessage;

        try {
            new PrintWriter(clientSocket.getOutputStream(), true).println("Client " + numberClient);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        while(true){

            try {
                clientMessage = in.readLine(); 
                System.out.println();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }  

            if(!clientMessage.equals("exit")){
                System.out.println("Client " + numberClient + ": " + clientMessage);            
                chatServer.sendMessageForAllClients(numberClient, clientMessage);                        
            }
            else{
                try {
                    in.close();
                    clientSocket.close();
                    break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }           
    }    
}
