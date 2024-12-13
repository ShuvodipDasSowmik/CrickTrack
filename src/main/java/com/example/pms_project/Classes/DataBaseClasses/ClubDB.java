package com.example.pms_project.Classes.DataBaseClasses;

import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.ClubClasses.Club;

import java.util.ArrayList;
import java.util.List;

public class ClubDB {
    private static List<Club> ClubList = new ArrayList<>();
    static int clubCount  = 0;

    public static void addClub(Club c){
        ClubList.add(c);
    }

    public static void addClub(String clubName, PlayerList playerList){
        ClubList.add(new Club(clubName, playerList));
    }

    public List<Club> getClubList(){
        return ClubList;
    }

    public static Club getClub(String clubName){
        for(Club c : ClubList){
            if(c.getClubName().equals(clubName)){
                return c;
            }
        }
        return null;
    }
}
