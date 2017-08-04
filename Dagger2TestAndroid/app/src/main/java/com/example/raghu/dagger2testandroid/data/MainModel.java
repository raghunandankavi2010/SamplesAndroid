package com.example.raghu.dagger2testandroid.data;

import javax.inject.Inject;

/**
 * Created by raghu on 4/8/17.
 */

public class MainModel {

    @Inject
    public MainModel() {
    }

    public String loadData() {

        return "Hello";
    }
}