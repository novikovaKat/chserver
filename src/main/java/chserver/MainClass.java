/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chserver;

/**
 *
 * @author 0802n
 */
public class MainClass {
    public static void main(String[] args){
        ChatServer chatServer = new ChatServer();
        Thread tChatServer = new Thread(chatServer);
        tChatServer.start();
    }
}
