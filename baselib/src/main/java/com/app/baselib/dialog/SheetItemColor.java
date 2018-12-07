package com.app.baselib.dialog;

public enum SheetItemColor {
    Blue("#037BFF"), Red("#FD4A2E");

    private String name;

    private SheetItemColor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
