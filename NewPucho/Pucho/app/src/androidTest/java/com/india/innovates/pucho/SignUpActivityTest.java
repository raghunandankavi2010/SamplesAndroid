package com.india.innovates.pucho;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

/*import com.india.innovates.pucho.Utility.ActivityRule;*/

import android.support.test.rule.ActivityTestRule;

import com.india.innovates.pucho.presenters.SignUpPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.action.ViewActions.typeText;

/**
 * Created by Raghunandan on 14-01-2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpActivityTest {

    @Rule
    public final ActivityTestRule<SignUpActivity> rule = new ActivityTestRule<>(SignUpActivity.class);

    @Inject
    SignUpPresenter signUpPresenter;


    String mStringToBetypedName,mStringToBeTypedEmailAddress,mStringToBeTypedPassword;

    @Before
    public void init() {

            mStringToBetypedName ="abcdefghijk";
            mStringToBeTypedEmailAddress = "abcdefghijk@gmail.com";
            mStringToBeTypedPassword = "Abcd@abcd123";

    }

    @Test
    public void testShouldShowSignUpButton() {

        onView(withId(R.id.fullName)).check(matches(isDisplayed()));

        onView(withId(R.id.emailAddress)).check(matches(isDisplayed()));

        onView(withId(R.id.passwordSignUp)).check(matches(isDisplayed()));

        onView(withId(R.id.signUp2)).check(matches(isDisplayed()));

        onView(withId(R.id.fullName))
                .perform(typeText(mStringToBetypedName), closeSoftKeyboard());

        onView(withId(R.id.emailAddress))
                .perform(typeText(mStringToBeTypedEmailAddress), closeSoftKeyboard());

        onView(withId(R.id.passwordSignUp))
                .perform(typeText(mStringToBeTypedPassword), closeSoftKeyboard());

        onView(withId(R.id.signUp2)).perform(click());

        //EventBus.getDefault().post("Success");
        signUpPresenter.setName(mStringToBetypedName);
        signUpPresenter.setEmail(mStringToBeTypedEmailAddress);
        signUpPresenter.setPassword(mStringToBeTypedPassword);
        signUpPresenter.valiDate_SignUp();

        //onView(withId(R.id.progressBar)).check(matches(isDisplayed()));


    }


}
