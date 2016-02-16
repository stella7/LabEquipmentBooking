package com.example.android.equipmentbookingtest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by Stella on 2015-12-04.
 */
public class MenuOptionTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    public MenuOptionTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void test1ViewHistory(){
        solo.clickOnMenuItem("My Book History");
        solo.assertCurrentActivity("did not find View History Activity", Useraccount.class);

        if(solo.waitForActivity(Useraccount.class)){

           // assertTrue(solo.waitForText("Raman spectroscopy"));
            assertTrue(solo.waitForText("Wet Processing Station"));
           // assertTrue(solo.waitForText("Atomic Force Microscope"));
        }
    }

    public void test2Setting(){
        solo.clickOnMenuItem("Setting");
        solo.assertCurrentActivity("did not find View History Activity", AccountSetting.class);
        if(solo.waitForActivity(AccountSetting.class)){

            solo.clickOnText("Change My Password");
            solo.assertCurrentActivity("Error", ChangePassword.class);
            solo.enterText(0, "1234abc");
            solo.enterText(1, "1234abc");
            solo.clickOnButton("Change");
            assertTrue(solo.waitForText("Password has been changed"));

        }

    }


    public void test4SendEmail(){

        solo.clickOnMenuItem("Contact Administrator");
        solo.assertCurrentActivity("did not find ContactUs Activity", ContactUs.class);
        if(solo.waitForActivity(ContactUs.class)){

            solo.enterText(0, "Hello World");
            solo.clickOnButton(0);
            solo.sleep(1000);
            //solo.goBack();
            //solo.goBackToActivity("MainActivity");
           // solo.goBack();

        }
    }

    public void test3Logout(){
        solo.clickOnMenuItem("Log Out");
        solo.assertCurrentActivity("Error", Login.class);
        if(solo.waitForActivity(Login.class)){

            solo.enterText(0, "s12he@uwaterloo.com");
            solo.enterText(1, "1234");
            solo.clickOnButton("Log in");
            assertTrue(solo.waitForText("Login Failed. User email or password is incorrect"));
            solo.sleep(1000);
            solo.clearEditText(1);
            solo.enterText(1, "1234abc");
            solo.clickOnButton("Log in");
            assertTrue(solo.waitForText("Login Successfully"));
        }

    }

}
