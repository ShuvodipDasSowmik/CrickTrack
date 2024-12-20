package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.PlayerWithButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;


public class ClubStat {

    Club c;
    Image img;

    public void onBackClick() throws IOException {
        main.showClubView();
    }

    public void setClub(String currentClub) {
        c = ClubDB.getClub(currentClub);
        playerList = c.getPlayerList();
        img = new Image(getClass().getResource("/com/example/pms_project/Assets/" + currentClub + ".png").toExternalForm());
    }

    PlayerList playerList;
    Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private TableView<PlayerWithButton> tableView;
    @FXML
    private TextField ySalary;
    @FXML
    private TextField tPlayers;
    @FXML
    private TextField oPlayer;
    @FXML
    private TextField hpPlayer;
    @FXML
    private ImageView clubLogo;
    @FXML
    TableColumn<PlayerWithButton, String> playerColumn;
    @FXML
    TableColumn<PlayerWithButton, String> countryColumn;
    @FXML
    TableColumn <PlayerWithButton, String> salaryColumn;
    @FXML
    TableColumn <PlayerWithButton, String> viewColumn;

    ObservableList<PlayerWithButton> data;

    private void initializeColumns() {

        playerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

    }

    public void load() {

        data = FXCollections.observableArrayList();

        for(Player p : playerList.list){
            data.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, 0, ""));
        }

        ySalary.setText(String.format("$%.0f K",c.getYearlySalary()));
        tPlayers.setText(String.valueOf(c.getPlayerCount()));
        hpPlayer.setText(c.higestPaidPlayer().getName());
        oPlayer.setText(c.oldestPlayer().getName());

        clubLogo.setImage(img);
        tableView.setItems(data);

        initializeColumns();
    }
}
