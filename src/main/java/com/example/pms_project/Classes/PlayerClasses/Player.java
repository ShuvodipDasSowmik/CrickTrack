package com.example.pms_project.Classes.PlayerClasses;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.Serializable;

public class Player implements Serializable{
    private String name;
    private String country;
    private int age;
    private double height;
    private String position;
    private String club;
    private int number;
    private int salary;
//    public final Button button;

    public Player(String name, String country, int age, double height, String position, String club, int number, int salary){
        this.name = name;
        this.country = country;
        this.age = age;
        this.height = height;
        this.position = position;
        this.club = club;
        this.number = number;
        this.salary = salary;
//        this.button = new Button("click");
//        button.setOnAction( e -> {
//                    System.out.println(getName() + " ");
//                    Alert a = new Alert(Alert.AlertType.INFORMATION);
//                    a.setContentText(getName() + " ");
//                    a.showAndWait();
//                }
//        );
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

//    public Button getButton(){
//        return button;
//    }

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