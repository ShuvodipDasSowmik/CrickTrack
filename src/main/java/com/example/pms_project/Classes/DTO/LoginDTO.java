package com.example.pms_project.Classes.DTO;

import java.io.Serializable;

public class LoginDTO implements Serializable {
    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    private String clubName;
    private String accessCode;
    private boolean status;
}
