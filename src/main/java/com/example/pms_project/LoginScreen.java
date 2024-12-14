package com.example.pms_project;

import com.example.pms_project.Classes.DTO.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginScreen {

    Main main;

    @FXML
    private TextField ClubName;
    @FXML
    private TextField AccessCode;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void onBackClick() throws IOException {
        main.showHomePage();
    }

    public void onLoginClick() throws IOException {
        String clubName = this.ClubName.getText();
        String accessCode = this.AccessCode.getText();

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setClubName(clubName);
        loginDTO.setAccessCode(accessCode);

        main.getSocketWrapper().write("Check Login");
        main.getSocketWrapper().write(loginDTO);


        try{
//            main.getSocketWrapper().write("Check Login");
//            main.getSocketWrapper().write(loginDTO);
            Object o = main.getSocketWrapper().read();

            if (o instanceof LoginDTO) {
                LoginDTO LDTO = (LoginDTO) o;
                if (LDTO.isStatus()) {
                    System.out.println("Verified");
                    main.showDashboard(loginDTO.getClubName());
                }
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void onRegisterClick() throws IOException {
        main.goToRegisterPage();
    }
}
