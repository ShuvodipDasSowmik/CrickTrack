package com.example.pms_project.Server;

import com.example.pms_project.Classes.DTO.LoginDTO;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.SellList;
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

    //    private HashMap <String, String> LoginData;
//    private HashMap<Player, String> sellStatePlayers;
    private SellList sellStatePlayers;

    public ReadThreadClient(String Req, Main main, SocketWrapper socketWrapper) {
        this.main = main;
        this.socketWrapper = socketWrapper;
        serverRequest = Req;
        this.thr = new Thread(this);
        thr.start();
    }

    public ReadThreadClient(Main main, SocketWrapper socketWrapper) {
        this.main = main;
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

//    public ReadThreadClient(String Req, Main main, SocketWrapper socketWrapper, LoginDTO loginDTO) {
//        this.main = main;
//        this.socketWrapper = socketWrapper;
//        serverRequest = Req;
//        this.thr = new Thread(this);
//        thr.start();
//        this.loginDTO = loginDTO;
//    }

    public synchronized void run() {


        while (true) {
            try {
                Object o = socketWrapper.read();

                if (o instanceof SellList) {
                    SellList p = (SellList) o;
                    main.setSellStatePlayers(p);
                    System.out.println("Setting Up Sell Database...");
                    p.showPlayers();
                } else if (o instanceof PlayerList) {
//                    socketWrapper.write("Fetch Database");

                    System.out.println("Reading Fetched Data...");
//                    System.out.println("Detected Player Database");
                    playerList = (PlayerList) o;
                    main.setPlayerDatabase(playerList);
                    System.out.println("Setting Up Database...");

                } else if (o instanceof LoginDTO) {
                    LoginDTO loginDTO = (LoginDTO) o;
                    if (loginDTO.isStatus()) {
//                        main.setCurrentClub(loginDTO.getClubName());
                        Platform.runLater(() -> {
                            try {
                                System.out.println("Dashboard in login entered");
                                main.showDashboard(loginDTO.getClubName());
                            } catch (IOException e) {
//                                    throw new RuntimeException(e);
                                e.printStackTrace();
                                System.out.println("Exception While Showing Dashboard in ReadThreadClient");
                            } finally {
                                System.out.println("Dashboard in login exited");
                            }
                        });
                    }
                } else {

                    if (o instanceof String) {
                        String msg = (String) o;

                        if (msg.equals("Refresh")) {
                            main.getSocketWrapper().write("Sell Player List");
                            Thread.sleep(1000);
                            System.out.println("Found Refresh Msg");
//                                PlayerList newDatabase = (PlayerList) socketWrapper.read();
//                                main.setPlayerDatabase(newDatabase);
//                            main.getSocketWrapper().write("Fetch Database");
//                                main.setSellStatePlayers((PlayerList) socketWrapper.read());
                            Platform.runLater(() -> {
                                try {
                                    System.out.println("Dashboard in refresh entered");
                                    main.showDashboard(main.getCurrentClub().getClubName());
                                } catch (IOException e) {
                                    System.out.println("Exception While Showing Dashboard in Refresh");
                                    e.printStackTrace();
                                } finally {
                                    System.out.println("Dashboard in refresh exited");
                                }
                            });
                        } else if (msg.equals("Sell Player List")) {
                            Object m = socketWrapper.read();
                            SellList newSellStatePlayers = (SellList) m;
                            main.setSellStatePlayers(newSellStatePlayers);
                        }
                    }

                }

            } catch (Exception e) {
//                    System.out.println(e);
                e.printStackTrace();
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
}



