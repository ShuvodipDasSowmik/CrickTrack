package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.PlayerClasses.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.util.HashMap;

public class Dashboard {
//    public Button onScoutClick;
    Club club;
    PlayerList playerList;
    Main main;
//    HashMap <Player, String> sellStatePlayer;
    SellList x;


    public void onScoutClick() {
        // Open a custom form dialog
//        System.out.println("SClicked");
        // Create a dialog
        Dialog<ScoutedPlayer> dialog = new Dialog<>();
        dialog.setTitle("Scout New Player");
        dialog.setHeaderText("Enter Player Details");

        // Set the button types
        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create input fields
        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        TextField countryField = new TextField();
        countryField.setPromptText("Country");

        TextField ageField = new TextField();
        ageField.setPromptText("Age");

        TextField heightField = new TextField();
        heightField.setPromptText("Height");

        TextField positionField = new TextField();
        positionField.setPromptText("Position");

        TextField clubField = new TextField();
        clubField.setPromptText("Club");

        TextField numberField = new TextField();
        numberField.setPromptText("Jersey Number");

        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");

        // Layout the fields in a grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Country:"), 0, 1);
        grid.add(countryField, 1, 1);
        grid.add(new Label("Age:"), 0, 2);
        grid.add(ageField, 1, 2);
        grid.add(new Label("Height:"), 0, 3);
        grid.add(heightField, 1, 3);
        grid.add(new Label("Position:"), 0, 4);
        grid.add(positionField, 1, 4);
//        grid.add(new Label("Club:"), 0, 5);
//        grid.add(clubField, 1, 5);
        grid.add(new Label("Jersey Number:"), 0, 5);
        grid.add(numberField, 1, 5);
        grid.add(new Label("Salary:"), 0, 6);
        grid.add(salaryField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable Create button depending on whether fields are filled
        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());
        });

        // Convert result to a Player object when the Create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    return new ScoutedPlayer(
                            nameField.getText(),
                            countryField.getText(),
                            Integer.parseInt(ageField.getText()),
                            Double.parseDouble(heightField.getText()),
                            positionField.getText(),
                            main.currentClub.getClubName(),
                            Integer.parseInt(numberField.getText()),
                            Integer.parseInt(salaryField.getText())
                    );
                } catch (NumberFormatException e) {
                    // Handle invalid input gracefully
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Please ensure age, height, jersey number, and salary are valid numbers.");
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        // Show the dialog and handle the result
        dialog.showAndWait().ifPresent(player -> {
            // Handle the new player object here
            System.out.println("New Player: " + player);
            try {
                main.getSocketWrapper().write(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Add logic to save or update the player in your application
        });
    }

//    private void openCreatePlayerForm() {
//
//    }


    public void setMain(Main main) {
        this.main = main;
    }

    public void setSellStatePlayer(SellList x) {
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
        main.setCurrentClub(club);
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
