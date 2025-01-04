package com.example.pms_project.Classes.PlayerClasses;


import com.example.pms_project.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.util.Duration;

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
//                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                        successAlert.setTitle("Purchase Successful");
//                        successAlert.setHeaderText(null);
//                        successAlert.setContentText(getName() + " has been purchased successfully for $" + buyingPrice + ".");
//                        successAlert.show();
                        showNotification(getName() + " has been purchased successfully for $" + buyingPrice + ".", "success");
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


//                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
//                        successAlert.setTitle("Sale Successful");
//                        successAlert.setHeaderText(null);
//                        successAlert.setContentText(getName() + " has been transferred to Selling List with Selling Price $" + sellingPrice + ".");
//                        successAlert.show();
                        showNotification(getName() + " has been transferred to Selling List with Selling Price $" + sellingPrice + ".", "success");
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
        customDialog.setHeaderText(null);
        customDialog.setGraphic(null);

        customDialog.getDialogPane().setPrefWidth(600);
        customDialog.getDialogPane().setPrefHeight(400);

        customDialog.getDialogPane().setStyle("-fx-background-color: #7E60BF;");

        ImageView imageView;

        if(getName().equals("Shuvodip Sowmik") || getName().equals("Shuvodip Das") || getName().equals("Sowmik"))
            imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pms_project/Assets/sowmik.jpg")).toExternalForm()));
        else
            imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/com/example/pms_project/Assets/" + getPosition() + ".jpg")).toExternalForm()));

        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);


        Text details = new Text(
                "Player Name: " + getName() + "\n" +
                        "Club Name: " + getClub() + "\n" +
                        "Country: " + getCountry() + "\n" +
                        "Position: " + getPosition() + "\n" +
                        "Jersey: " + getNumber() + "\n" +
                        "Salary: " + getSalary() + "\n" +
                        "Age: " + getAge() + "\n" +
                        "Height: " + getHeight()
        );
        details.setStyle("-fx-font-size: 20px; -fx-font-family: 'Berlin Sans FB'; -fx-padding-bottom: 20px;-fx-font-weight: 300");

        HBox content = new HBox(20, imageView, details);
        content.setStyle("-fx-padding: 20px; -fx-alignment: center-left; -fx-padding-right: 30px");

        customDialog.getDialogPane().setContent(content);

        customDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        customDialog.showAndWait();
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