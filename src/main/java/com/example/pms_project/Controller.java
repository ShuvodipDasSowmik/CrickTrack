package com.example.pms_project;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;

public class Controller {

    Main main;
    MediaPlayer mediaPlayer;

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

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

    public void onExitClick(){
        System.exit(0);
    }

    public void onSoundToggle(ActionEvent actionEvent) {
        if (main == null) return; // Ensure `main` is initialized

        MediaPlayer mediaPlayer = main.getMediaPlayer();
        boolean isSoundPlaying = main.isSoundPlaying();

        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource(); // Get the button that triggered the event

        if (mediaPlayer != null) {
            if (isSoundPlaying) {
                mediaPlayer.pause(); // Pause sound
                toggleButton.setText("Sound Off"); // Update button text
            } else {
                mediaPlayer.play(); // Play sound
                toggleButton.setText("Sound On"); // Update button text
            }
            main.setSoundPlaying(!isSoundPlaying); // Toggle the sound state
        }
    }
}