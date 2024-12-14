package com.example.pms_project;

import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.DataBaseClasses.PlayerDB;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Server.ReadThreadClient;
import com.example.pms_project.Server.SocketWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.spec.ECField;
import java.util.HashMap;


public class Main extends Application {

    private Stage stage;
    private PlayerList playerDatabase = new PlayerList();
    private SocketWrapper socketWrapper;
    private boolean isDatabaseFetched = false;
    private HashMap <Player, String> sellStatePlayers = new HashMap<>();

    public Stage getStage(){
        return stage;
    }

    public void setPlayerDatabase(PlayerList playerDatabase) {
        this.playerDatabase = playerDatabase;
        PlayerDB.addPlayerToDatabase(playerDatabase);
    }

    public void setSellStatePlayers(HashMap <Player, String> sellStatePlayers) {
        this.sellStatePlayers = sellStatePlayers;
    }

    public HashMap <Player, String> getSellStatePlayers() {
        return sellStatePlayers;
    }

//    public SocketWrapper getSocketWrapper(){
//        return socketWrapper;
//    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        showHomePage();
    }

    public void showHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1048, 700);

        Controller controller = fxmlLoader.getController();
        controller.setMain(this);

        if(!isDatabaseFetched){
            System.out.println("Fetching Database from Server...");
            connectToServer("Fetch Database");
            isDatabaseFetched = true;
        }

        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
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

        PlayerList x = new PlayerList();

        System.out.println("Fetching Sell List");
        socketWrapper.write("Sell Player List");
//        sellStatePlayers = new HashMap<>();

        try{
//            System.out.println("HJBADS");
            Object o = socketWrapper.read();
//            System.out.println("HJBADS");
            if (o instanceof PlayerList) {
                x = (PlayerList) o;
                x.showPlayers();
            } else {
                System.out.println("Class Mismatch");
            }
        }
        catch (Exception O){
            System.out.println("Reading Error");
            O.printStackTrace();
        }

        for (HashMap.Entry<Player, String> entry : sellStatePlayers.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
//        System.out.println("HEllo");

        Dashboard controller = fxmlLoader.getController();
        controller.setMain(this);
        controller.setSellStatePlayer(x);
        controller.setClub(ClubDB.getClub(clubName));
        controller.load();

        stage.setTitle(clubName + " Dashboard");
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