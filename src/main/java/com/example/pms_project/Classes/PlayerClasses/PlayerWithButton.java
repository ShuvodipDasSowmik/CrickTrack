package com.example.pms_project.Classes.PlayerClasses;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.DataBaseClasses.PlayerDB;
import com.example.pms_project.Main;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;


public class PlayerWithButton {
    private String name;
    private String country;
    private int age;
    private double height;
    private String position;
    private String club;
    private int number;
    private int salary;
    public final Button button;
    public final Button sellButton;
    public final Button buyButton;
    private String currentClub;
    public int price;
    Main main;

    public PlayerWithButton(String name, String country, int age, double height, String position, String club, int number, int salary, Main main, int price, String currentClub) {
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.position = position;
        this.club = club;
        this.number = number;
        this.salary = salary;
        this.main = main;
        this.price = price;

        this.buyButton = new Button("Buy");
        this.sellButton = new Button("Sell");
        this.button = new Button("View");
        this.currentClub = currentClub;

        sellButton.setStyle("-fx-background-color: #0A2463; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setStyle("-fx-background-color: #0A2463; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        buyButton.setStyle("-fx-background-color: #0A2463; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        button.setOnAction(e -> showCustomAlert());
        sellButton.setOnAction(e -> showSellAlert());
        buyButton.setOnAction(e -> showBuyAlert());
    }

    private void showBuyAlert() {
        // Show an input dialog for buying price
        TextInputDialog priceDialog = new TextInputDialog();
        priceDialog.setTitle("Buy Player");
        priceDialog.setHeaderText("Buying Player: " + getName());
        priceDialog.setContentText("Enter the buying price:");

        // Wait for the user's input
        priceDialog.showAndWait().ifPresent(input -> {
            try {
                // Parse the buying price
                double buyingPrice = Double.parseDouble(input);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Purchase");
                confirmationAlert.setHeaderText("Confirm Purchase of " + getName());
                confirmationAlert.setContentText("Buying Price: $" + buyingPrice + "\nAre you sure you want to proceed?");

                // Wait for the user's confirmation
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Perform the purchase (e.g., update player status, notify, etc.)
                        Player P = PlayerDB.searchPlayerByName(name); // Find the player object
//                        ClubDB.getClub(currentClub).getPlayerList().RemovePlayer(P);

//                        P.setClub(currentClub);
//                        P.setSalary(price);
//                        PlayerDB.boughtPlayer(name); // Mark player as bought
//                        PlayerDB.buyPlayer(P);
                        // Add player to the buyer's club
//                        ClubDB.getClub(currentClub).getPlayerList().addPlayer(P);

                        try {
                            main.getSocketWrapper().write("Buy Player");
//                            main.getSocketWrapper().write(currentClub);
                            main.getSocketWrapper().write(P); // Send player details to server
                            main.getSocketWrapper().write(currentClub);
//                            main.getSocketWrapper().write(String.valueOf(buyingPrice)); // Send buying price to server

//                            HashMap <Player, String> sellStatePlayers = (HashMap<Player, String>) main.getSocketWrapper().read();
//                            main.setSellStatePlayers(sellStatePlayers);
//                            main.showDashboard(currentClub);

                        } catch (IOException e) {
                            System.out.println("Exception While Sending Buy Request to Server");
                        }

                        // Show success alert
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Purchase Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(getName() + " has been purchased successfully for $" + buyingPrice + ".");
                        successAlert.show();


                        // Refresh the dashboard to reflect changes
//                        main.setPlayerDatabase(PlayerDB.getPlayerDatabase());
//                        Platform.runLater(() -> {
//                            try {
//                                main.showDashboard(currentClub);
//                            } catch (Exception e) {
//                                System.out.println("Exception in DashBoard from Buy");
//                            }
//                        });

                    }
                });
            } catch (NumberFormatException ex) {
                // Handle invalid input
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid numerical buying price.");
                errorAlert.show();
            }
        });
    }


    private void showSellAlert() {
        // Show an input dialog for selling price
        TextInputDialog priceDialog = new TextInputDialog();
        priceDialog.setTitle("Sell Player");
        priceDialog.setHeaderText("Selling Player: " + getName());
        priceDialog.setContentText("Enter the selling price:");

        // Wait for the user's input
        priceDialog.showAndWait().ifPresent(input -> {
            try {
                // Parse the selling price
                int sellingPrice = Integer.parseInt(input);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Sale");
                confirmationAlert.setHeaderText("Confirm Sale of " + getName());
                confirmationAlert.setContentText("Selling Price: $" + sellingPrice + "\nAre you sure you want to proceed?");

                // Wait for the user's confirmation
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {

                        Player P = PlayerDB.searchPlayerByName(name);
//                        PlayerDB.soldPlayer(name);
//                        ClubDB.getClub(club).getPlayerList().RemovePlayer(P);

                        try {
                            main.getSocketWrapper().write("Sell Player," + name);
                        } catch (IOException e) {
                            System.out.println("Exception While Sending Sell Request to Server");
                        } catch (Exception e) {
                            System.out.println("Exception While Reading From Server");
                        }

//                        System.out.println("OK!");
                        // Perform the sale (e.g., update player status, notify, etc.)

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Sale Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(getName() + " has been transferred to Selling List with Selling Price $" + sellingPrice + ".");
                        successAlert.show();
                    }
                });
            } catch (NumberFormatException ex) {
                // Handle invalid input
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid numerical selling price.");
                errorAlert.show();
            }
        });
    }


    private void showCustomAlert() {
        // Create a custom dialog
        Dialog<Void> customDialog = new Dialog<>();
        customDialog.setTitle(getName() + " Details");
        customDialog.setHeaderText(null); // No header
        customDialog.setGraphic(null);   // Remove the default graphic

        customDialog.getDialogPane().setPrefWidth(800);
        customDialog.getDialogPane().setPrefHeight(600);

        // Add an image to the dialog (optional)
        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pms_project/Assets/Batsman.jpeg")).toExternalForm())); // Replace with your image path
        imageView.setFitWidth(300);
//        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
//        imageView.setStyle("-fx-alignment: right");

        // Add player details
        Text details = new Text(
                "Player Name: " + getName() + "\n" +
                        "Club Name: " + getClub() + "\n" +
                        "Country: " + getCountry() + "\n" +
                        "Position: " + getPosition() + "\n" +
                        "Jersey: " + getNumber() + "\n" +
                        "Age: " + getAge() + "\n" +
                        "Height: " + getHeight()
        );
        details.setStyle("-fx-font-size: 20px; -fx-font-family: 'Berlin Sans FB'");

        // Arrange components in a VBox
        VBox content = new VBox(20, imageView, details);
        content.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        // Set the custom content
        customDialog.getDialogPane().setContent(content);

        // Add OK button to close the dialog
        customDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        // Show the dialog
        customDialog.showAndWait();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Button getButton() {
        return button;
    }

    public Button getSellButton() {
        return sellButton;
    }

    public int getPrice() {
        return price;
    }

    public Button getBuyButton() {
        return buyButton;
    }

    @Override
    public String toString() {
        return "[" + '\n' +
                "name='" + name + '\'' +
                ", " + '\n' + "country = '" + country + '\'' +
                ", " + '\n' + "age = " + age +
                ", " + '\n' + "height = " + height +
                ", " + '\n' + "position = '" + position + '\'' +
                ", " + '\n' + "club = '" + club + '\'' +
                ", " + '\n' + "number = " + number +
                ", " + '\n' + "salary = " + salary + '\n' +
                ']';
    }
}