package com.example.pms_project.Classes.PlayerClasses;

import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.DataBaseClasses.PlayerDB;
import com.example.pms_project.Main;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Objects;


public class PlayerWithButton{
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
    Main main;

    public PlayerWithButton(String name, String country, int age, double height, String position, String club, int number, int salary, Main main){
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.position = position;
        this.club = club;
        this.number = number;
        this.salary = salary;
        this.main = main;

        this.sellButton = new Button("Sell");
        this.button = new Button("View");

        sellButton.setStyle("-fx-background-color: #0A2463; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setStyle("-fx-background-color: #0A2463; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        button.setOnAction(e -> showCustomAlert());
        sellButton.setOnAction(e -> showSellAlert());
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
                double sellingPrice = Double.parseDouble(input);

                // Show a confirmation dialog
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Sale");
                confirmationAlert.setHeaderText("Confirm Sale of " + getName());
                confirmationAlert.setContentText("Selling Price: $" + sellingPrice + "\nAre you sure you want to proceed?");

                // Wait for the user's confirmation
                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {

                        Player P = PlayerDB.searchPlayerByName(name);
                        PlayerDB.soldPlayer(name);
                        ClubDB.getClub(club).getPlayerList().RemovePlayer(P);

                        try {
                            main.getSocketWrapper().write("Sell Player");
                            main.getSocketWrapper().write(PlayerDB.searchPlayerByName(name));
                            main.getSocketWrapper().write(String.valueOf(sellingPrice));
                        } catch (IOException e) {
                            System.out.println("Exception While Sending Sell Request to Server");
                        }
                        // Perform the sale (e.g., update player status, notify, etc.)

                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Sale Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(getName() + " has been transferred to Selling List with Selling Price $" + sellingPrice + ".");
                        successAlert.show();

                        try {
                            main.setPlayerDatabase(PlayerDB.getPlayerDatabase());
                            main.showDashboard(club);
                        } catch (IOException e) {
                            System.out.println("Exception While Showing Dashboard");
                        }
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

    public Button getButton(){
        return button;
    }

    public Button getSellButton(){
        return sellButton;
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