package com.example.raghu.retrofitsample.test2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raghu on 6/10/17.
 */

public class AddList {

    //static {"related_ids":[50,34,31,56,47],"upsells_ids":[15]}

    List<Integer> related_ids = new ArrayList<>();
    List<Integer> upsells_ids = new ArrayList<>();
    public AddList(){
        related_ids.add(50);
        related_ids.add(34);
        related_ids.add(31);
        related_ids.add(56);
        related_ids.add(47);
        upsells_ids.add(15);
        Example example = new Example();
        example.setRelatedIds(related_ids);
        example.setUpsellsIds(upsells_ids);



    }

}
