package com.example.pms_project.Server;
import com.example.pms_project.Classes.DTO.LoginDTO;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Main;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;

public class ReadThreadClient implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    private static final String INPUT_FILE_NAME = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\java\\com\\example\\pms_project\\Server\\players.txt";
    private Main main;
    private PlayerList playerList;
    private String serverRequest;
    private LoginDTO loginDTO;

    private HashMap <String, String> LoginData;


    public ReadThreadClient(String Req, Main main, SocketWrapper socketWrapper) {
        this.main = main;
        this.socketWrapper = socketWrapper;
        serverRequest = Req;
        this.thr = new Thread(this);
        thr.start();
    }

    public ReadThreadClient(String Req, Main main, SocketWrapper socketWrapper, LoginDTO loginDTO) {
        this.main = main;
        this.socketWrapper = socketWrapper;
        serverRequest = Req;
        this.thr = new Thread(this);
        thr.start();
        this.loginDTO = loginDTO;
    }

    public void run() {
        try {
            if(serverRequest.equals("Fetch Database")){
                socketWrapper.write("Fetch Database");
                Object o = socketWrapper.read();

                System.out.println("Reading Fetched Data...");

                if(o instanceof PlayerList){
                    System.out.println("Detected Player Database");
                    playerList = (PlayerList) o;
                    main.setPlayerDatabase(playerList);
                    System.out.println("Setting Up Database...");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error While Sending Request To The Server");
        }
//        finally {
//            try {
//                socketWrapper.closeConnection();
//                System.out.println("Client Served. Closing Connection...");
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("Error While Closing Connection");
//            }
//        }
    }
}



