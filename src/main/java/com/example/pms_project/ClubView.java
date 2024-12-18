package com.example.pms_project;

import com.example.pms_project.Classes.ClubClasses.Club;
import com.example.pms_project.Classes.DataBaseClasses.ClubDB;

import java.io.IOException;

public class ClubView {
    Main main;

    public void setMain(Main main){
        this.main = main;
    }

    public void onChennaiClick() throws IOException {
        main.showClubStat("Chennai Super Kings");
    }
    public void onMumbaiClick() throws IOException {
        main.showClubStat("Mumbai Indians");
    }
    public void onDelhiClick() throws IOException {
        main.showClubStat("Delhi Capitals");
    }
    public void onKolkataClick() throws IOException {
        main.showClubStat("Kolkata Knight Riders");
    }
    public void onPunjabClick() throws IOException {
        main.showClubStat("Punjab Kings");
    }
    public void onGujaratClick() throws IOException {
        main.showClubStat("Gujarat Titans");
    }
    public void onRajasthanClick() throws IOException {
        main.showClubStat("Rajasthan Royals");
    }
    public void onBangaloreClick() throws IOException {
        main.showClubStat("Royal Challengers Bangalore");
    }
}
