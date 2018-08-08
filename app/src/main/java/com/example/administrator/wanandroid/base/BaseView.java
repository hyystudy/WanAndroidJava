package com.example.administrator.wanandroid.base;

import android.view.View;

/**
 * Created by Administrator on 2018/8/8/008.
 */

public interface BaseView<T> {

    void initView(View view);

    void setPresenter(T presenter);
}
