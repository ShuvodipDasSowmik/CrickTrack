package com.example.pms_project.Classes.ClubClasses;

import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;

public class Club {
    private String clubName;
    PlayerList playerList;

    public Club(String clubName, PlayerList playerList) {
        this.clubName = clubName;
        this.playerList = playerList;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    public void addPlayer(Player P){
        playerList.addPlayer(P);
    }

    public int getPlayerCount(){
        return playerList.getPlayerCount();
    }

    public double getYearlySalary(){
        double yearlySalary = 0;
        for(Player p: playerList.list){
            yearlySalary += p.getSalary();
        }

        return (yearlySalary*52)/1000;
    }

    public void sellPlayer(Player P){
        playerList.RemovePlayer(P);
    }

    public Player oldestPlayer(){
        Player oldestPlayer = playerList.list.get(0);
        for(int i = 1; i < playerList.list.size(); i++){
            if(playerList.list.get(i).getAge() > oldestPlayer.getAge()){
                oldestPlayer = playerList.list.get(i);
            }
        }
        return oldestPlayer;
    }

    public Player higestPaidPlayer(){
        Player hpPLayer = playerList.list.get(0);
        for(int i = 1; i < playerList.list.size(); i++){
            if(playerList.list.get(i).getSalary() > hpPLayer.getSalary()){
                hpPLayer = playerList.list.get(i);
            }
        }
        return hpPLayer;
    }
}
