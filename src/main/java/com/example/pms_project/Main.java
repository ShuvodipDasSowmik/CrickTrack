package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.DataBaseClasses.ClubDB;
import com.example.pms_project.Classes.DataBaseClasses.PlayerDB;
import com.example.pms_project.Classes.PlayerClasses.Player;
import com.example.pms_project.Classes.PlayerClasses.PlayerList;
import com.example.pms_project.Classes.PlayerClasses.SellList;
import com.example.pms_project.Server.ReadThreadClient;
import com.example.pms_project.Server.SocketWrapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.spec.ECField;
import java.util.HashMap;


public class Main extends Application {

    private Stage stage;
    private PlayerList playerDatabase = new PlayerList();
    private SocketWrapper socketWrapper;
    private boolean isDatabaseFetched = false;
//    private HashMap<Player, String> sellStatePlayers = new HashMap<>();
    private SellList sellStatePlayers = new SellList();
    Club currentClub;
    boolean firstRun = false;


    public Stage getStage() {
        return stage;
    }

    public void setPlayerDatabase(PlayerList playerDatabase) {
        this.playerDatabase = playerDatabase;
        PlayerDB.addPlayerToDatabase(playerDatabase);
    }

    public void setSellStatePlayers(SellList sellStatePlayers) {
        this.sellStatePlayers = sellStatePlayers;
    }

    public SellList getSellStatePlayers() {
        return sellStatePlayers;
    }

//    public SocketWrapper getSocketWrapper(){
//        return socketWrapper;
//    }

    public void setCurrentClub(Club currentClub) {
        this.currentClub = currentClub;
    }

    public Club getCurrentClub() {
        return currentClub;
    }
    @Override
    public void start(Stage stage) throws IOException {
        connectToServer();
        this.stage = stage;

//        String audioFilePath = "E:\\JavaFX\\Player Management System\\PMS_Project\\src\\main\\resources\\com\\example\\pms_project\\Assets\\De Ghuma Ke.mp3"; // Update path as needed
//
//        Media media = new Media(Paths.get(audioFilePath).toUri().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(media);

//        mediaPlayer.play();
        showHomePage();
    }

    public void showHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1048, 700);

        Controller controller = fxmlLoader.getController();
        controller.setMain(this);

        if (!isDatabaseFetched) {
            System.out.println("Fetching Database from Server...");
//            connectToServer("Fetch Database");
            socketWrapper.write("Fetch Database");
            socketWrapper.write("Sell Player List");
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

        Dashboard controller = fxmlLoader.getController();
        controller.setMain(this);

        System.out.println(clubName);
        controller.setSellStatePlayer(sellStatePlayers);
        controller.setClub(ClubDB.getClub(clubName));
        ClubDB.getClub(clubName).getPlayerList().showPlayers();
        controller.load();

        stage.setTitle(clubName + " Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public void showClubView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ClubView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

        ClubView controller = fxmlLoader.getController();
        controller.setMain(this);
//        controller.setPlayerList(playerDatabase);
//        controller.load();

        stage.setTitle("Clubs");
        stage.setScene(scene);
        stage.show();
    }

    public void showClubStat(String clubName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ClubStat.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 675);

        ClubStat controller = fxmlLoader.getController();
        controller.setMain(this);
//        controller.setPlayerList(playerDatabase);
        controller.setClub(clubName);
        controller.load();

        stage.setTitle("Clubs");
        stage.setScene(scene);
        stage.show();
    }


    public void connectToServer() throws IOException {
        int serverPort = 4000;
        socketWrapper = new SocketWrapper("localhost", serverPort);
        new ReadThreadClient(this, this.getSocketWrapper());
    }

    public SocketWrapper getSocketWrapper() {
        return socketWrapper;
    }

    public static void main(String[] args) {
        launch();
    }
}