package com.indiainnovates.pucho;

/**
 * Created by Raghunandan on 14-01-2016.
 */
import com.indiainnovates.pucho.utils.Utility;

import org.junit.Test;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidator {

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(Utility.validateEmailRegex("name@email.com"), is(true));
    }


    /* Name must have fist name followed by space followed by last name*/
    @Test
    public void name_CorrectName_ReturnsTrue() {
        assertThat(Utility.validateNameRegex("abcd efg"), is(true));
    }

    /* password must atleast contain 1 capital, 1 special character and 1 number*/
    @Test
    public void password_Correctpassword_ReturnsTrue() {
        assertThat(Utility.validatePasswordRegex("Raghu@raghu123"), is(true));
    }
}
