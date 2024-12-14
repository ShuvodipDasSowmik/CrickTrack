package com.example.pms_project.Classes.DataBaseClasses;

import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;

import java.util.ArrayList;
import java.util.List;

public class PlayerDB {
    private static PlayerList PlayerDatabase = new PlayerList();

    static void addPlayerToDatabase(Player player) {
        PlayerDatabase.addPlayer(player);
    }

    public static PlayerList getPlayerDatabase() {
        return PlayerDatabase;
    }

    public static void addPlayerToDatabase(PlayerList playerList) {
        PlayerDatabase = playerList;
        addPlayerToClubDatabase();
    }

    static int getPlayerCount() {
        return PlayerDatabase.getPlayerCount();
    }

    static void addPlayerToClubDatabase(){
        List<String> Club = new ArrayList<String>();

        for(int i = 0; i < PlayerDatabase.getPlayerCount(); i++){
            boolean alreadyExists = false;
            String clubName = PlayerDatabase.getPlayer(i).getClub();

            for(int k = 0; k < Club.size(); k++){
                if(clubName.equals(Club.get(k))){
                    alreadyExists = true;
                    break;
                }
            }
            if(!alreadyExists){
                for(int j = 0; j < PlayerDatabase.getPlayerCount(); j++){
                    if(PlayerDatabase.getPlayer(j).getClub().equals(clubName)){
                        Club.add(PlayerDatabase.getPlayer(j).getClub());
                    }
                }
            }
        }

        for(int i = 0; i < Club.size(); i++){
            PlayerList temp = new PlayerList();

            for(int j = 0; j < PlayerDatabase.getPlayerCount(); j++){
                if(PlayerDatabase.getPlayer(j).getClub().equals(Club.get(i))){
                    temp.addPlayer(PlayerDatabase.getPlayer(j));
                }
            }

            ClubDB.addClub(Club.get(i), temp);
        }
    }

    public static Player searchPlayerByName(String name){
        for(int i = 0; i < PlayerDatabase.getPlayerCount(); i++){
            if(PlayerDatabase.getPlayer(i).getName().equals(name)){
                return PlayerDatabase.getPlayer(i);
            }
        }
        return null;
    }

    public static void soldPlayer(String playerName) {
        Player p = searchPlayerByName(playerName);
//        System.out.println(p);
        PlayerDatabase.RemovePlayer(p);

//        if(PlayerDatabase.list.contains(p)){
//            System.out.println("Not Removed");
//        }
//        else{
//            System.out.println("Removed");
//        }

        Player newPlayer = new Player(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), "None", p.getNumber(), 0);
        PlayerDatabase.addPlayer(newPlayer);
//        System.out.println(newPlayer);
    }

    public static void buyPlayer(Player newPlayer) {
        Player p = searchPlayerByName(newPlayer.getName());
//        System.out.println(p);
        PlayerDatabase.RemovePlayer(p);

//        if(PlayerDatabase.list.contains(p)){
//            System.out.println("Not Removed");
//        }
//        else{
//            System.out.println("Removed");
//        }

        PlayerDatabase.addPlayer(newPlayer);
//        System.out.println(newPlayer);
    }
}
