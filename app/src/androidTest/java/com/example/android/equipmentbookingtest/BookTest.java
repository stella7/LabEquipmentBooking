package com.example.android.equipmentbookingtest;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.robotium.solo.Solo;

/**
 * Created by Stella on 2015-11-29.
 */
public class BookTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    public BookTest(){
        super(Showdevice.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
      //  Intent intent = new Intent(this, BookDevice.class);
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    protected void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void test2GetBooked(){


        solo.unlockScreen();
        solo.clickOnButton("Book Now");
       // solo.clickOnView(getActivity().findViewById(R.id.));
        solo.assertCurrentActivity("did not find BookDeviceActivity", BookDevice.class);

        if(solo.waitForActivity(BookDevice.class)){
            assertTrue(solo.waitForText("Wet Processing Station"));
            assertTrue(solo.searchText("r13jason@uwaterloo.com"));
           // assertEquals("r13jason@uwaterloo.com",solo.getText(2));
        }
        solo.getCurrentActivity();

       // solo.isCheckBoxChecked(2);
    }

    public void test1Book(){

        solo.clickOnButton("Book Now");
        // solo.clickOnView(getActivity().findViewById(R.id.));
        solo.assertCurrentActivity("did not find BookDeviceActivity", BookDevice.class);

        if(solo.waitForActivity(BookDevice.class)){

            solo.clickOnCheckBox(0);
            solo.clickOnButton("Done");
            assertTrue(solo.isCheckBoxChecked(0));
            assertTrue(solo.waitForText("s12he@uwaterloo.com"));
            assertTrue(solo.waitForText("Equipment booking successful"));
        }

    }

    @Override
    public Activity getActivity() {
        Intent intent = new Intent();
        intent.putExtra("deviceName", "Wet Processing Station");
        intent.putExtra("json_device_one", "Wet processing stations (wet benches) by FineLine " +
                "Fabrications, Inc., are equipped with magnetically stirred hot plates, ultrasound " +
                "bath, DI water resistivity controllers, and Laurell spinners.");

        setActivityIntent(intent);
        return super.getActivity();
    }

}
