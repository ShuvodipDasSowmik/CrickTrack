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
}
