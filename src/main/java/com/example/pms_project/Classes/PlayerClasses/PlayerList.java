package com.example.pms_project.Classes.PlayerClasses;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlayerList implements Serializable {
    public List<Player> list = new ArrayList<>();
    int playerCount = 0;

    public void addPlayer(Player player) {
        list.add(player);
        playerCount++;
    }

    public void showPlayers() {
        for (int i = 0; i < playerCount; i++) {
            System.out.println((i + 1) + ") " + list.get(i));
        }
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public Player getPlayer(int i) {
        return list.get(i);
    }

    private static void MergeSort(List<Player> list, int left, int right) {
        if(left >= right)
            return;

        int mid = (left + right) / 2;

        MergeSort(list, left, mid);
        MergeSort(list, mid + 1, right);

        MERGE(list, left, mid, right);
    }

    private static void MERGE(List<Player> list, int left, int mid, int right) {
        List<Player> Left = new ArrayList<>();
        List<Player> Right = new ArrayList<>();

        int n1 = left - mid + 1;
        int n2 = right - mid;

        for(int i = left; i <= mid; i++) {
            Left.add(list.get(i));
        }

        for(int i = mid + 1; i <= right; i++) {
            Right.add(list.get(i));
        }

        int x = 0, y = 0;

//        System.out.println("YP" + list.get(3));

        for(int m = left; m <= right; m++){

            if((x < Left.size() && y < Right.size() && Left.get(x).getSalary() < Right.get(y).getSalary()) || y >= Right.size()){
                list.set(m, Left.get(x));
                x++;
            }
            else{
                list.set(m, Right.get(y));
                y++;
            }
        }
    }

//    public static PlayerList sortAllPlayerBySalary() {
//        int size = PlayerDatabase.playerCount;
//        List<Player> List = new ArrayList<>();
//
//        for (int i = 0; i < size; i++) {
//            List.add(PlayerDatabase.list.get(i));
//        }
//        MergeSort(List, 0, size - 1);
//
//        PlayerList playerList = new PlayerList();
//        for(int i = 0; i < size; i++) {
//            playerList.addPlayer(List.get(i));
//        }
//
//        return playerList;
//    }

    boolean isEmpty(){
        return playerCount == 0;
    }

    int size(){
        return playerCount;
    }


    PlayerList searchPlayerListBySalaryRange(int lower, int upper) {
        PlayerList playerList = new PlayerList();
        for (int i = 0; i < playerCount; i++) {
            if (list.get(i).getSalary() >= lower && list.get(i).getSalary() <= upper) {
                playerList.addPlayer(list.get(i));
            }
        }
        if (playerList.isEmpty()) {
            return null;
        }
        return playerList;
    }

    public void showAllPlayers(){
        for(int i = 0; i < playerCount; i++) {
            System.out.println(list.get(i));
        }
    }

    //Works Properly
    public void RemovePlayer(Player player){
        list.remove(player);
        playerCount--;
        if(list.contains(player)){
            System.out.println("Not Removed");
        }
        else{
            System.out.println("Removed");
        }
    }
}

