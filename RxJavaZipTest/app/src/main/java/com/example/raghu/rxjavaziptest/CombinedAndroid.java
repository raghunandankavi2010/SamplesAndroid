package com.example.raghu.rxjavaziptest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raghu on 31-01-2017.
 */

public class CombinedAndroid {

    private List<Android> finalList = new ArrayList<>();


    public CombinedAndroid addAll(List<Android> list1,List<Android> list2)
    {
        finalList.addAll(list1);
        finalList.addAll(list2);
        return this;
    }

    public List<Android> getFinalList() {
        return finalList;
    }

}