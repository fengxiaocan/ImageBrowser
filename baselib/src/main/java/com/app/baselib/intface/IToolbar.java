package com.app.baselib.intface;

import android.support.v7.widget.Toolbar;

public interface IToolbar extends IActivityInit{
    int toolbarId();
    
    void initToolbar(Toolbar toolbar);
    
    void afterInitView();
 
    boolean homeButtonEnabled();
    
    boolean haveTitle();
    
    int backButtonRes();
}
