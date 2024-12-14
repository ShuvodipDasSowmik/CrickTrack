package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.PlayerWithButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.HashMap;

public class Dashboard {
    Club club;
    PlayerList playerList;
    Main main;
    HashMap <Player, String> sellStatePlayer;
    PlayerList x;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setSellStatePlayer(PlayerList x) {
        this.x = x;
    }

    public void onRefresh() throws IOException {
        main.showDashboard(club.getClubName());
    }

    @FXML
    private TableView<PlayerWithButton> tableView;
    @FXML
    TableColumn<PlayerWithButton, String> nameColumn;
    @FXML
    TableColumn<PlayerWithButton, String> countryColumn;
    @FXML
    TableColumn <PlayerWithButton, String> viewColumn;
    @FXML
    TableColumn <PlayerWithButton, String> sellColumn;
    @FXML
    TextField tPlayers;
    @FXML
    TextField ySalary;

    @FXML
    private TableView <PlayerWithButton> sellTableView;
    @FXML
    TableColumn<PlayerWithButton, String> sellPlayer;
    @FXML
    TableColumn<PlayerWithButton, String> sellCountry;
    @FXML
    TableColumn<PlayerWithButton, String> sellPrice;
    @FXML
    TableColumn <PlayerWithButton, String> sellView;
    @FXML
    TableColumn <PlayerWithButton, String> sellBuy;

    ObservableList <PlayerWithButton> sellData;
    ObservableList<PlayerWithButton> data;

//    private boolean init = true;

    @FXML
    private void BackClick() throws IOException {
        main.goToLoginPage();
    }

    private void initializeColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
        sellColumn.setCellValueFactory(new PropertyValueFactory<>("sellButton"));
        ySalary.setText(String.format("$%.0f K",club.getYearlySalary()));
        tPlayers.setText(String.valueOf(club.getPlayerCount()));

        sellPlayer.setCellValueFactory(new PropertyValueFactory<>("name"));
        sellCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
        sellPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        sellView.setCellValueFactory(new PropertyValueFactory<>("button"));
        sellBuy.setCellValueFactory(new PropertyValueFactory<>("buyButton"));

    }

    public void setClub(Club club) {
        this.club = club;
        this.playerList = club.getPlayerList();
    }

    public void load() {
        data = FXCollections.observableArrayList();
        sellData = FXCollections.observableArrayList();

//        for (HashMap.Entry<Player, String> entry : sellStatePlayer.entrySet()) {
////            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
//            Player p = entry.getKey();
//            int price = Integer.parseInt(entry.getValue());
////            System.out.println(entry.getValue() + "    " + price);
//            sellData.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, price, club.getClubName()));
//        }

        for(Player p : playerList.list){
            data.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, 0, club.getClubName()));
//            System.out.println(p);
        }

        for(Player p : x.list){
            sellData.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, 0, club.getClubName()));
//            System.out.println(p);
        }

        tableView.setItems(data);
        sellTableView.setItems(sellData);

//        if (init) {
//            initializeColumns();
//            init = false;
//        }
        initializeColumns();
    }
}
