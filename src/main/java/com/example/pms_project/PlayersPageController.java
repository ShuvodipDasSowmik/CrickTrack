package com.example.pms_project;

import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.PlayerWithButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.stream.Collectors;


public class PlayersPageController {
    PlayerList playerList;
    Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void onBackClick() throws IOException {
        main.showHomePage();
    }

    public void onSearchByNameClick(){
        searchByNameField.setVisible(true);
//        searchButton1.setVisible(true);

        upperRange.setVisible(false);
        lowerRange.setVisible(false);
//        searchButton2.setVisible(false);

        searchByPosition.setVisible(false);
//        searchButton3.setVisible(false);
    }

    public void onSearchBySalaryClick(){
        upperRange.setVisible(true);
        lowerRange.setVisible(true);
//        searchButton2.setVisible(true);

        searchByPosition.setVisible(false);
//        searchButton3.setVisible(false);

        searchByNameField.setVisible(false);
//        searchButton1.setVisible(false);
    }

    public void searchByPositionClick(){
        searchByPosition.setVisible(true);
//        searchButton3.setVisible(true);

        searchByNameField.setVisible(false);
//        searchButton1.setVisible(false);

        upperRange.setVisible(false);
        lowerRange.setVisible(false);
//        searchButton2.setVisible(false);
    }

    public void onSearchByClubClick() {
        searchByClubField.setVisible(true);
//        searchButton4.setVisible(true);

        // Hide other search fields/buttons
        searchByNameField.setVisible(false);
//        searchButton1.setVisible(false);
        upperRange.setVisible(false);
        lowerRange.setVisible(false);
//        searchButton2.setVisible(false);
        searchByPosition.setVisible(false);
//        searchButton3.setVisible(false);
    }


    @FXML
    private TextField searchByNameField;
    @FXML
    private TextField upperRange;
    @FXML
    private TextField lowerRange;
    @FXML
    private TextField searchByPosition;
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
    @FXML
    private TextField searchByClubField;

    ObservableList <PlayerWithButton> data;


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
            data.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, 0, ""));
        }

//        tableView.setEditable(true);
        tableView.setItems(data);

//        if (init) {
//            initializeColumns();
//            init = false;
//        }

        initializeColumns();
    }

    @FXML
    private void handleSearchByName() {
        String searchText = searchByNameField.getText().toLowerCase();
        ObservableList<PlayerWithButton> filteredData = data.stream()
                .filter(player -> player.getName().toLowerCase().contains(searchText))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tableView.setItems(filteredData);
    }

    // Search by salary range
    @FXML
    private void handleSearchBySalary() {
        try {
            double lower = Double.parseDouble(lowerRange.getText());
            double upper = Double.parseDouble(upperRange.getText());

            ObservableList<PlayerWithButton> filteredData = data.stream()
                    .filter(player -> player.getSalary() >= lower && player.getSalary() <= upper)
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            tableView.setItems(filteredData);
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., non-numeric)
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid salary range. Please enter valid numbers.");
            alert.showAndWait();
        }
    }

    // Search by position
    @FXML
    private void handleSearchByPosition() {
        String positionText = searchByPosition.getText().toLowerCase();
        ObservableList<PlayerWithButton> filteredData = data.stream()
                .filter(player -> player.getPosition().toLowerCase().contains(positionText))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tableView.setItems(filteredData);
    }

    @FXML
    private void handleSearchByClub() {
        String clubText = searchByClubField.getText().toLowerCase();
        ObservableList<PlayerWithButton> filteredData = data.stream()
                .filter(player -> player.getClub().toLowerCase().contains(clubText))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tableView.setItems(filteredData);
    }


    // Add event listeners for search fields
    @FXML
    private void initializeSearchFields() {
        // Add listeners to the search fields to trigger the search as the user types
        searchByNameField.setOnKeyReleased(event -> handleSearchByName());
        searchByPosition.setOnKeyReleased(event -> handleSearchByPosition());
        // Search button for salary range
        upperRange.setOnKeyReleased(event -> handleSearchBySalary());
        lowerRange.setOnKeyReleased(event -> handleSearchBySalary());
        searchByClubField.setOnKeyReleased(event -> handleSearchByClub());
    }

    @FXML
    private void initialize() {
        initializeSearchFields();
    }
}
