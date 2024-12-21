package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.PlayerWithButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.stream.Collectors;


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
    @FXML
    private TextField searchByNameField;
    @FXML
    private TextField upperRange;
    @FXML
    private TextField lowerRange;
    @FXML
    private TextField searchByPosition;

    ObservableList<PlayerWithButton> data;


    public void onSearchByNameClick(){
        searchByNameField.setVisible(true);

        upperRange.setVisible(false);
        lowerRange.setVisible(false);

        searchByPosition.setVisible(false);
    }

    public void onSearchBySalaryClick(){
        upperRange.setVisible(true);
        lowerRange.setVisible(true);

        searchByPosition.setVisible(false);

        searchByNameField.setVisible(false);
    }

    public void searchByPositionClick(){
        searchByPosition.setVisible(true);

        searchByNameField.setVisible(false);

        upperRange.setVisible(false);
        lowerRange.setVisible(false);
    }


    private void initializeColumns() {

        playerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        viewColumn.setCellValueFactory(new PropertyValueFactory<>("button"));

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
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.ERROR, "Invalid salary range. Please enter valid numbers.");
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
    private void initializeSearchFields() {
        // Add listeners to the search fields to trigger the search as the user types
        searchByNameField.setOnKeyReleased(event -> handleSearchByName());
        searchByPosition.setOnKeyReleased(event -> handleSearchByPosition());
        // Search button for salary range
        upperRange.setOnKeyReleased(event -> handleSearchBySalary());
        lowerRange.setOnKeyReleased(event -> handleSearchBySalary());
    }

    @FXML
    private void initialize() {
        initializeSearchFields();
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
