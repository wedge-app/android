package com.example.a2021_wedge.Sajang;

public class ItemWaitingList {

    private String personnel;
    private String nickname;

    public ItemWaitingList(String personnel, String nickname) {
        this.personnel = personnel;
        this.nickname = nickname;
    }

    public String getPersonnel() {
        return personnel;
    }

    public void setPersonnel(String personnel) {
        this.personnel = personnel;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
