package com.example.pms_project.Server;

import com.example.pms_project.Classes.DTO.LoginDTO;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.SellList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final SocketWrapper socketWrapper;
    public static PlayerList playerList;

    private static boolean refreshVar = false;

    private static ArrayList <SocketWrapper> clientSocketList = new ArrayList <>();

    private HashMap<String, String> loginData;
    private static final String INPUT_FILE_NAME = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\java\\com\\example\\pms_project\\Server\\players.txt";

    private static final String INPUT_FILE_LOGIN = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\java\\com\\example\\pms_project\\Server\\loginData.txt";
    private static final String SELL_DATA_FILE = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\java\\com\\example\\pms_project\\Server\\sellData.txt";

    public static SellList sellStatePlayers = new SellList();


    private PlayerList addPlayerToDatabase() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PlayerList tempList = new PlayerList();

        while (true) {
            String line = "";
            try {
                line = br.readLine();
            } catch (EOFException e) {
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

    private void UpdateDatabase(PlayerList PlayerDatabase) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(INPUT_FILE_NAME));

            for (int i = 0; i < PlayerDatabase.getPlayerCount(); i++) {
                Player x = PlayerDatabase.list.get(i);
                // bw.write(System.lineSeparator());
                String text = x.getName() + "," + x.getCountry() + "," + x.getAge() + ","
                        + x.getHeight() + ","
                        + x.getClub() + "," + x.getPosition() + "," + x.getNumber() + ","
                        + x.getSalary();
                if (i != PlayerDatabase.getPlayerCount() - 1)
                    text = text + '\n';
                bw.write(text);
            }

            bw.close();
            System.out.println("Database Successfully Updated");

        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    private SellList addSellData() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(SELL_DATA_FILE));
        SellList tempList = new SellList();

        while (true) {
            String line = "";
            try {
                line = br.readLine();
            } catch (EOFException e) {
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

    private void writeSellData() throws Exception {

        BufferedWriter bw = new BufferedWriter(new FileWriter(SELL_DATA_FILE));

        for (int i = 0; i < sellStatePlayers.list.size(); i++) {
            Player x = sellStatePlayers.list.get(i);
            // bw.write(System.lineSeparator());
            String text = x.getName() + "," + x.getCountry() + "," + x.getAge() + ","
                    + x.getHeight() + ","
                    + x.getClub() + "," + x.getPosition() + "," + x.getNumber() + ","
                    + x.getSalary();
            if (i != sellStatePlayers.getPlayerCount() - 1)
                text = text + '\n';
            bw.write(text);
        }


        bw.close();
        System.out.println("Database Successfully Updated");

    }

    private HashMap<String, String> addLoginData() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_LOGIN));
        HashMap<String, String> ld = new HashMap<>();

        while (true) {
            String line = "";
            try {
                line = br.readLine();
            } catch (EOFException e) {
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
        clientSocketList.add(socketWrapper);
        System.out.println(clientSocketList.size());
        this.socketWrapper = socketWrapper;
        this.thr = new Thread(this);
        thr.start();
    }

    private synchronized void Refresh() throws IOException {
        int i = 0;
        for(SocketWrapper x : clientSocketList){
            x.write("Refresh");
//            sellStatePlayers.showPlayers();
            System.out.println("Wrote Refresh on Socket " + i);
            i++;
        }
    }

    public synchronized void run() {
        try {
            while (true) {
                Object o = socketWrapper.read();
                if (o != null) {
                    if (o instanceof String) {
                        String s = (String) o;
                        String[] tokens = s.split(",");

                        String PlayerName = "";
                        if(tokens.length > 1){
                            s = tokens[0];
                            PlayerName = tokens[1];
                        }

                        if (s.equals("Fetch Database")) {
                            try {
                                playerList = addPlayerToDatabase();
                                socketWrapper.write(playerList);
                                System.out.println("Sent Database To The Client");
                            } catch (Exception e) {
                                System.out.println("Error While Sending Requested Database");
                            }
                        } else if (s.equals("Check Login")) {
                            try {
                                LoginDTO ldto = (LoginDTO) socketWrapper.read();
                                String accessCode = loginData.get(ldto.getClubName());

                                ldto.setStatus(ldto.getAccessCode().equals(accessCode));

                                socketWrapper.write(ldto);
                                System.out.println("Sent Login Data To The Client");
                            } catch (Exception e) {
                                System.out.println("Error While Adding Login Data");
                            }
                        } else if (s.equals("Sell Player")) {
                            try {
                                Player P = playerList.searchPlayerByName(PlayerName);
//                                String price = (String) socketWrapper.read();
                                P.setClub("None");
                                P.setSalary(0);
                                sellStatePlayers.addPlayer(P);
//                                System.out.println(P);
//                                sellStatePlayers.showAllPlayers();
//                                System.out.println("Player Added??");
                                writeSellData();
                                Iterator <Player> it = playerList.list.iterator();
                                while(it.hasNext()){
                                    Player x = it.next();
                                    if(x.getName().equals(P.getName())){
                                        x.setClub("None");
                                        x.setSalary(0);
                                    }
                                }

                                refreshVar = true;

                                System.out.println("Player Successfully Put in Selling List");
                                Refresh();

//                                socketWrapper.write(sellStatePlayers);
                            } catch (Exception e) {
                                System.out.println("Error While Selling Player");
                            }
                        } else if (s.equals("Sell Player List")) {
                            try {
//                                    for (HashMap.Entry<Player, String> entry : sellStatePlayers.entrySet()) {
//                                        System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                                    }
//                                    PlayerList p = new PlayerList();
//
//                                    for (HashMap.Entry<Player, String> entry : sellStatePlayers.entrySet()) {
//                                        p.addPlayer(entry.getKey());
//                                    }
                                sellStatePlayers = addSellData();
                                socketWrapper.write(sellStatePlayers);
//                                sellStatePlayers.showAllPlayers();
                                System.out.println("Sent Sell State Players");
                            } catch (Exception e) {
                                System.out.println("Error While Sending Sell Player List");
                            }
                        }
                        else if(s.equals("Buy Player")){
                            try{
                                Player P = (Player) socketWrapper.read();
                                String buyerClub = (String) socketWrapper.read();

                                Iterator<Player> iterator = sellStatePlayers.list.iterator();
                                while (iterator.hasNext()) {
                                    Player x = iterator.next();
                                    if (x.getName().equals(P.getName())) {
                                        iterator.remove(); // Safe removal
                                        // break; // Uncomment if only one match is expected
                                    }
                                }
                                Iterator <Player> it = playerList.list.iterator();
                                while(it.hasNext()){
                                    Player x = it.next();
                                    if(x.getName().equals(P.getName())){
                                        x.setClub(buyerClub);
                                        x.setSalary(0);
                                    }
                                }
//                                sellStatePlayers.showPlayers();

//                                writeSellData();
//                                sellStatePlayers = addSellData();
                                Refresh();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                System.out.println("Error While Buy Player");
                            }
                        }

//                        else if(s.equals("Buy Player")){
//                            try{
//                                String club = (String) socketWrapper.read();
//                                Player p = (Player) socketWrapper.read();
//
//                                for (HashMap.Entry<Player, String> entry : sellStatePlayers.entrySet()) {
////                                    System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//                                    if (entry.getValue().equals(p.getName())) {
//                                        sellStatePlayers.remove(entry.getKey());
//                                        socketWrapper.write(sellStatePlayers);
//                                    }
//                                }
//                            }
//                            catch (Exception e){
//                                System.out.println("Error While Buy Player");
//                            }
//                        }
                    }
                }
//                if(refreshVar) {
//                    for(SocketWrapper x : clientSocketList){
//                        x.write("Refresh");
//                    }
//                }
            }

        } catch (EOFException e) {
//            e.printStackTrace();
            System.out.println("Client Closed Connection");
        } catch (Exception e) {
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



