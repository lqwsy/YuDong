package com.hw.txtreaderlib.interfaces;

import com.hw.txtreaderlib.bean.TxtMsg;

/*
* create by bifan-wei
* 2017-11-13
*/
public interface LoadListener {
    void onSuccess();
    void onFail(TxtMsg txtMsg);
}
