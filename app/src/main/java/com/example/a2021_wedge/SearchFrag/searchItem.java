package com.example.a2021_wedge.SearchFrag;

import android.graphics.drawable.Drawable;

public class searchItem {
    private String storename;
    private Drawable enter;

    public searchItem(String storename, Drawable enter) {
        this.storename = storename;
        this.enter = enter;
    }

    public String getStorename() { return storename; }
    public void setStorename(String storename) { this.storename = storename; }

    public Drawable getEnter() { return enter; }
    public void setEnter(Drawable enter) { this.enter = enter; }
}
