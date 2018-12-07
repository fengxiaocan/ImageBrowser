package com.app.baselib.recycler;

import com.evil.helper.recycler.inface.IRecycleData;

public class RecycleData implements IRecycleData {
    private int type;
    public RecycleData() {
    }

    public RecycleData(int type) {
        this.type = type;
    }

    @Override
    public int getRecycleType() {
        return type;
    }
}
