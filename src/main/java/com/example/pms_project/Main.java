package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.DTO.LoginDTO;
import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.DataBaseClasses.PlayerDB;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Server.ReadThreadClient;
import com.example.pms_project.Server.SocketWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private Stage anotherStage;
    private PlayerList playerDatabase = new PlayerList();
    private SocketWrapper socketWrapper;

    public Stage getStage(){
        return stage;
    }

    public void setPlayerDatabase(PlayerList playerDatabase) {
        this.playerDatabase = playerDatabase;
        PlayerDB.addPlayerToDatabase(playerDatabase);
    }

//    public SocketWrapper getSocketWrapper(){
//        return socketWrapper;
//    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1048, 700);

        Controller controller = fxmlLoader.getController();
        controller.setMain(this);

        System.out.println("Fetching Database from Server...");
        connectToServer("Fetch Database");

        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
//        playerDatabase.showAllPlayers();
    }

    public void goToPlayersPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("players-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

        PlayersPageController controller = fxmlLoader.getController();
        controller.setMain(this);
        controller.setPlayerList(playerDatabase);
        controller.load();

        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void goToLoginPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

//        PlayersPageController controller = fxmlLoader.getController();
//        controller.setPlayerList(playerDatabase);
//        controller.load();
        LoginScreen loginController = fxmlLoader.getController();
        loginController.setMain(this);

        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    public void goToRegisterPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("RegisterScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

//        PlayersPageController controller = fxmlLoader.getController();
//        controller.setPlayerList(playerDatabase);
//        controller.load();

        stage.setTitle("Register Club");
        stage.setScene(scene);
        stage.show();
    }

    public void showDashboard(String clubName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

        Dashboard controller = fxmlLoader.getController();
        controller.setMain(this);
        controller.setClub(ClubDB.getClub(clubName));
        controller.load();

        stage.setTitle("Register Club");
        stage.setScene(scene);
        stage.show();
    }

    public void showAlert() throws IOException {

    }

    private void connectToServer(String serverCommand) throws IOException {
        int serverPort = 4000;
        socketWrapper = new SocketWrapper("localhost", serverPort);
        new ReadThreadClient(serverCommand,this, this.getSocketWrapper());
    }

//    public void connectToServer(String serverCommand, LoginDTO loginDTO) throws IOException {
//        int serverPort = 4000;
//        socketWrapper = new SocketWrapper("localhost", serverPort);
//        new ReadThreadClient(serverCommand, this, this.getSocketWrapper(), loginDTO);
//    }

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    public static void main(String[] args) {
        launch();
    }
}