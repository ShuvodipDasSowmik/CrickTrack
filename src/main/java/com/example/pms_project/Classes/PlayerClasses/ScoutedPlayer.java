package com.example.pms_project.Classes.PlayerClasses;

import java.io.Serializable;

public class ScoutedPlayer extends Player implements Serializable {
    public ScoutedPlayer(String name, String country, int age, double height, String position, String club, int number, int salary) {
        super(name, country, age, height, position, club, number, salary);
    }
}
