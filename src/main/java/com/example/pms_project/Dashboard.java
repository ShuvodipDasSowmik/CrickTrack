package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.PlayerClasses.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;


import java.io.IOException;
import java.util.HashMap;

public class Dashboard {
    Club club;
    PlayerList playerList;
    Main main;
    SellList x;

    @FXML
    private TextField hpPlayer;
    @FXML
    private TextField oPlayer;


    public void onScoutClick() {

        Dialog<ScoutedPlayer> dialog = new Dialog<>();
        dialog.setTitle("Scout New Player");
        dialog.setHeaderText("Enter Player Details");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

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

        grid.add(new Label("Jersey Number:"), 0, 5);
        grid.add(numberField, 1, 5);
        grid.add(new Label("Salary:"), 0, 6);
        grid.add(salaryField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());
        });

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

        dialog.showAndWait().ifPresent(player -> {

            System.out.println("New Player: " + player);
            try {
                main.getSocketWrapper().write(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        showNotification("New Player " + nameField.getText() + " Signed to " + club.getClubName(), "success");
    }

    private void showNotification(String message, String type) {
        Popup popup = new Popup();

        StackPane pane = new StackPane();
        Text text = new Text(message);
        text.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        pane.getChildren().add(text);
        pane.setStyle(type.equals("success")
                ? "-fx-background-color: #4CAF50; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;"
                : "-fx-background-color: #f44336; -fx-padding: 10px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        pane.setAlignment(Pos.CENTER);

        popup.getContent().add(pane);
        popup.setAutoFix(true);
        popup.setAutoHide(true);

        popup.setY(main.getStage().getHeight());

        popup.show(main.getStage());

        TranslateTransition showTransition = new TranslateTransition(Duration.seconds(0.7), pane);
        showTransition.setFromY(main.getStage().getHeight()); // Start from below the screen
        showTransition.setToY(main.getStage().getHeight()-150); // Move to the top position
        showTransition.setCycleCount(1);
        showTransition.play();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            TranslateTransition hideTransition = new TranslateTransition(Duration.seconds(0.7), pane);
            hideTransition.setFromY(main.getStage().getHeight()-100);
            hideTransition.setToY(main.getStage().getHeight());
            hideTransition.setCycleCount(1);
            hideTransition.setOnFinished(event -> popup.hide());
            hideTransition.play();
        }));
        timeline.play();
    }


    public void setMain(Main main) {
        this.main = main;
    }

    public void setSellStatePlayer(SellList x) {
        this.x = x;
        x.showAllPlayers();
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
//    @FXML
//    TableColumn<PlayerWithButton, String> sellPrice;
    @FXML
    TableColumn <PlayerWithButton, String> sellView;
    @FXML
    TableColumn <PlayerWithButton, String> sellBuy;

    ObservableList <PlayerWithButton> sellData;
    ObservableList<PlayerWithButton> data;


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
//        sellPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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

        oPlayer.setText(club.oldestPlayer().getName());
        hpPlayer.setText(club.higestPaidPlayer().getName());

        for(Player p : playerList.list){
            data.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, 0, club.getClubName()));
        }

        for(Player p : x.list) {
//            System.out.println(main.getCurrentClub());
            if (!p.getPrevClub().equals(main.getCurrentClub().getClubName())) {
                sellData.add(new PlayerWithButton(p.getName(), p.getCountry(), p.getAge(), p.getHeight(), p.getPosition(), p.getClub(), p.getNumber(), p.getSalary(), main, 0, club.getClubName()));
            }
        }


        tableView.setItems(data);
        sellTableView.setItems(sellData);

        initializeColumns();
    }
}
