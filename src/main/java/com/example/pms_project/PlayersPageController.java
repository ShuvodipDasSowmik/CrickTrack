package com.example.pms_project;

import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.PlayerWithButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class PlayersPageController {
    PlayerList playerList;
    Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private TableView <PlayerWithButton> tableView;
    @FXML
    TableColumn<PlayerWithButton, String> nameColumn;
    @FXML
    TableColumn<PlayerWithButton, String> countryColumn;
    @FXML
    TableColumn <PlayerWithButton, String> positionColumn;
    @FXML
    TableColumn<PlayerWithButton, String> clubColumn;
    @FXML
    TableColumn <PlayerWithButton, String> viewColumn;

    ObservableList <PlayerWithButton> data;

//    private boolean init = true;

    public void setPlayerList(PlayerList playerList) {
        this.playerList = playerList;
    }

    private void initializeColumns() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

    }

    public void load() {

        data = FXCollections.observableArrayList();

        for(Player p : playerList.list){
            data.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main));
        }

//        tableView.setEditable(true);
        tableView.setItems(data);

//        if (init) {
//            initializeColumns();
//            init = false;
//        }

        initializeColumns();
    }
}
