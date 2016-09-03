package br.com.odra.devnexus.model;

import android.app.Fragment;

import static br.com.odra.devnexus.MainActivity.BackStackOperation;

public class DrawerItem {

    private int iconResId;
    private String text;

    public DrawerItem(int iconResId, String text) {
        this.iconResId = iconResId;
        this.text = text;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getText() {
        return text;
    }

}
