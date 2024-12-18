package com.example.pms_project;
import javafx.event.ActionEvent;
import java.io.IOException;

public class Controller {

    Main main;

    public Main getMain() {
        return main;
    }

    public void onClubsButtonClick(ActionEvent actionEvent) throws IOException {
        main.showClubView();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void onPlayerButtonClick(ActionEvent actionEvent) throws IOException {
        main.goToPlayersPage();
    }

    public void onAdminButtonClick(ActionEvent actionEvent) throws IOException {
        main.goToLoginPage();
    }
}