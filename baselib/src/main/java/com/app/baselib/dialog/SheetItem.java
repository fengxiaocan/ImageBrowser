package com.app.baselib.dialog;

public class SheetItem {
    String name;
    OnSheetItemClickListener itemClickListener;
    SheetItemColor color;

    public SheetItem(String name, SheetItemColor color,
                     OnSheetItemClickListener itemClickListener) {
        this.name = name;
        this.color = color;
        this.itemClickListener = itemClickListener;
    }
}
