package com.wookingwoo.shoppingmalldemo;

import android.graphics.drawable.Drawable;

public class listItem {

    private String itemTitle;
    private Drawable d;


    public listItem() {
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }


    public void setD(Drawable d) {
        this.d = d;
    }


    public String getItemTitle() {
        return itemTitle;
    }

    public Drawable getD() {
        return d;
    }
}