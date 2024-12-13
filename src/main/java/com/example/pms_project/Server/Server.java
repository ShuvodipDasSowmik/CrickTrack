package com.example.pms_project.Server;

import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    Server(){
        try{
            serverSocket = new ServerSocket(4000);
            while(true){
                try{
                    System.out.println("Server listening on port 4000");
                    Socket clientSocket = serverSocket.accept();
                    serve(clientSocket);
                }
                catch (Exception e){
                    System.out.println("Server exception");
                }
            }
        }
        catch(IOException e){
//            e.printStackTrace();
            System.out.println("Exception While Accepting Client Request");
        }
    }

    public void serve (Socket clientSocket){
        try{
            SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
            new ReadThreadServer(socketWrapper);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("IOException While Serving A Client");
        } catch (Exception e) {
            System.out.println("Unknown Exception While Serving A Client");
        }
    }


    public static void main(String args[]){
        new Server();
    }
}
