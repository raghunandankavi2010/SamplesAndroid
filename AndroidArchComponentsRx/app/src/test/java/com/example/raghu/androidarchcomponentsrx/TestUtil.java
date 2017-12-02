package com.example.raghu.androidarchcomponentsrx;

import com.example.raghu.androidarchcomponentsrx.models.Example;
import com.example.raghu.androidarchcomponentsrx.models.User;

/**
 * Created by raghu on 2/12/17.
 */

public class TestUtil {

    public static Example createSampleExample(String name,String age){
        Example example = new Example();
        User user = new User();
        user.setName(name);
        user.setAge(age);
        example.setUser(user);
        return example;
    }
}
