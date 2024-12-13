package com.example.pms_project.Server;

import com.example.pms_project.Classes.DTO.LoginDTO;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public PlayerList playerList;

    private HashMap <String, String> loginData;
    private static final String INPUT_FILE_NAME = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\java\\com\\example\\pms_project\\Server\\players.txt";

    private static final String INPUT_FILE_LOGIN = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\java\\com\\example\\pms_project\\Server\\loginData.txt";

    public HashMap <Player, String> sellStatePlayers = new HashMap<>();



    private PlayerList addPlayerToDatabase() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PlayerList tempList = new PlayerList();

        while (true) {
            String line = "";
            try{
                line = br.readLine();
            }
            catch (EOFException e){
                System.out.println("End Of File Exception");
            }

            if (line == null)
                break;
            // System.out.println(line);

            String[] tokens = line.split(",");

            String name = tokens[0];
            String country = tokens[1];
            int age = Integer.parseInt(tokens[2]);
            double height = Double.parseDouble(tokens[3]);
            String club = tokens[4];
            String position = tokens[5];

            int number;
            if (!tokens[6].isEmpty()) {
                number = Integer.parseInt(tokens[6]);
            } else {
                number = -1;
            }
            int salary = Integer.parseInt(tokens[7]);

            tempList.addPlayer(new Player(name, country, age, height, position, club, number, salary));
        }

        br.close();
        return tempList;
    }

    private HashMap<String, String> addLoginData() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_LOGIN));
        HashMap <String, String> ld = new HashMap<>();

        while (true) {
            String line = "";
            try{
                line = br.readLine();
            }
            catch (EOFException e){
                System.out.println("End Of File Exception");
            }

            if (line == null)
                break;

            String[] tokens = line.split(",");

            String clubName = tokens[0];
            String accessCode = tokens[1];

            ld.put(clubName, accessCode);
        }
        return ld;
    }


    public ReadThreadServer(SocketWrapper socketWrapper) throws Exception {
//        this.playerList = playerList;
        this.loginData = addLoginData();
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {
                    if (o instanceof String) {
                        String s = (String) o;
                        if (s.equals("Fetch Database")) {
                            try {
                                playerList = addPlayerToDatabase();
                                socketWrapper.write(playerList);
                                System.out.println("Sent Database To The Client");
                            } catch (Exception e) {
                                System.out.println("Error While Sending Requested Database");
                            }
                        }

                        else if(s.equals("Check Login")){
                            try{
                                LoginDTO ldto = (LoginDTO) socketWrapper.read();
                                String accessCode = loginData.get(ldto.getClubName());

                                ldto.setStatus(ldto.getAccessCode().equals(accessCode));

                                socketWrapper.write(ldto);
                                System.out.println("Sent Login Data To The Client");
                            }
                            catch (Exception e){
                                System.out.println("Error While Adding Login Data");
                            }
                        }

                        else if(s.equals("Sell Player")){
                            try {
                                Player P = (Player) socketWrapper.read();
                                String price = (String) socketWrapper.read();
                                sellStatePlayers.put(P, price);
                                System.out.println("Player Successfully Put in Selling List");
                            }
                            catch (Exception e){
                                System.out.println("Error While Selling Player");
                            }
                        }
                    }
                }
            }
        }
        catch (EOFException e){
//            e.printStackTrace();
            System.out.println("Client Closed Connection");
        }
        catch (Exception e) {
//            e.printStackTrace();
            System.out.println("Error While Reading Request From Client");
        } finally {
            try {
                socketWrapper.closeConnection();
            } catch (IOException e) {
//                e.printStackTrace();
                System.out.println("Error While Closing Connection");
            }
        }
    }
}



