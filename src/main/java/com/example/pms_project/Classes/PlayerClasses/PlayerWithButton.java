package com.example.pms_project.Classes.PlayerClasses;


import com.example.pms_project.Main;
//import javafx.application.Platform;
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

        sellButton.setStyle("-fx-background-color: #6A42C2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setStyle("-fx-background-color: #6A42C2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        buyButton.setStyle("-fx-background-color: #6A42C2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        button.setOnAction(e -> showCustomAlert());
        sellButton.setOnAction(e -> showSellAlert());
        buyButton.setOnAction(e -> showBuyAlert());
    }

    private void showBuyAlert() {

        TextInputDialog priceDialog = new TextInputDialog();
        priceDialog.setTitle("Buy Player");
        priceDialog.setHeaderText("Buying Player: " + getName());
        priceDialog.setContentText("Enter the buying price:");

        priceDialog.showAndWait().ifPresent(input -> {
            try {
                int buyingPrice = Integer.parseInt(input);

                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Purchase");
                confirmationAlert.setHeaderText("Confirm Purchase of " + getName());
                confirmationAlert.setContentText("Buying Price: $" + buyingPrice + "\nAre you sure you want to proceed?");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        try {
                            Player sendPlayer = new Player(name, country, age, height, position, currentClub, number, buyingPrice);
                            main.getSocketWrapper().write(sendPlayer);
                        } catch (IOException e) {
                            System.out.println("Exception While Sending Buy Request to Server");
                        }

                        // Show success alert
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Purchase Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(getName() + " has been purchased successfully for $" + buyingPrice + ".");
                        successAlert.show();
                    }
                });
            } catch (NumberFormatException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid numerical buying price.");
                errorAlert.show();
            }
        });
    }


    private void showSellAlert() {
        TextInputDialog priceDialog = new TextInputDialog();
        priceDialog.setTitle("Sell Player");
        priceDialog.setHeaderText("Selling Player: " + getName());
        priceDialog.setContentText("Enter the selling price:");

        priceDialog.showAndWait().ifPresent(input -> {
            try {
                int sellingPrice = Integer.parseInt(input);

                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Sale");
                confirmationAlert.setHeaderText("Confirm Sale of " + getName());
                confirmationAlert.setContentText("Selling Price: $" + sellingPrice + "\nAre you sure you want to proceed?");

                confirmationAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {

                        try {
                            main.getSocketWrapper().write("Sell Player," + name + "," + club);
                        } catch (IOException e) {
                            System.out.println("Exception While Sending Sell Request to Server");
                        } catch (Exception e) {
                            System.out.println("Exception While Reading From Server");
                        }


                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Sale Successful");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText(getName() + " has been transferred to Selling List with Selling Price $" + sellingPrice + ".");
                        successAlert.show();
                    }
                });
            } catch (NumberFormatException ex) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Invalid Input");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Please enter a valid numerical selling price.");
                errorAlert.show();
            }
        });
    }


    private void showCustomAlert() {
        Dialog<Void> customDialog = new Dialog<>();
        customDialog.setTitle(getName() + " Details");
        customDialog.setHeaderText(null); // No header
        customDialog.setGraphic(null);   // Remove the default graphic

        customDialog.getDialogPane().setPrefWidth(800);
        customDialog.getDialogPane().setPrefHeight(600);

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pms_project/Assets/Batsman.jpeg")).toExternalForm())); // Replace with your image path
        imageView.setFitWidth(300);

        imageView.setPreserveRatio(true);

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

        VBox content = new VBox(20, imageView, details);
        content.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        customDialog.getDialogPane().setContent(content);

        customDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

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