package com.example.android.equipmentbookingtest;

import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.widget.Toast;

import com.robotium.solo.Solo;

/**
 * Created by Stella on 2015-11-29.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<Login> {

    private Solo solo;

    public LoginTest(){
        super(Login.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void test1LoginFail(){
        solo.unlockScreen();
        solo.enterText(0, "stella");
        solo.enterText(1, "123");
        solo.clickOnButton("Log in");

        assertTrue(solo.waitForText("Login Failed. User email or password is incorrect"));


    }

    public void test2Register(){
        solo.clickOnButton("Sign up");
        solo.enterText(0, "s12he@uwaterloo.com");
        solo.enterText(1, "Emily");
        solo.enterText(2, "1234");
        solo.enterText(3, "123");

        solo.clickOnButton(0);
        assertTrue(solo.waitForText("Passwords don't match"));

        solo.clearEditText(3);
        solo.enterText(3, "1234");

        solo.clickOnButton(0);
        assertTrue(solo.waitForText("Register successfully"));
        solo.waitForDialogToClose();
    }

    public void test3LoginSuccess() {
        solo.unlockScreen();
        solo.enterText(0, "s12he@uwaterloo.com");
        solo.enterText(1, "1234");
        solo.clickOnButton("Log in");
        assertTrue(solo.waitForText("Login Successfully"));
        solo.sleep(2000);
    }

}
