package com.example.android.equipmentbookingtest;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stella on 2015-11-29.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());

    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }
/*

*/
    public void testSearchWrongDevice(){
        solo.enterText(0, "Micro Scope");
        solo.clickOnButton("Search");
        assertTrue(solo.waitForText("No such device in system"));

        solo.sleep(2000);
    }

    public void testSearchDevice(){
        solo.enterText(0, "Karl Suss MA6 Mask Aligner");
        solo.clickOnButton("Search");
        if(solo.waitForActivity(Showdevice.class)){

            assertTrue(solo.waitForText("Karl Suss MA6 Mask Aligner"));
            assertTrue(solo.waitForText("The SUSS MA6 mask aligner is regarded as the benchmark " +
                    "from semiconductor submicron research to 3D micro-system production.  " +
                    "The system is available with bottom-side-alignment microscopes for accurate" +
                    " backside processes, which is an important feature, especially in microsystem " +
                    "technology. For all classic lithography and topside alignment a dual video " +
                    "microscope is available. The maximum wafer size exposed is 6\" square."));
        }

        solo.sleep(2000);
    }

    public void testViewAll() {

        solo.clickOnText("View All");
        solo.getText("Raman spectroscopy");
        solo.clickInList(2);

        if(solo.waitForActivity(Showdevice.class)){

            assertTrue(solo.waitForText("Raman spectroscopy"));
            assertTrue(solo.waitForText("Raman spectroscopy is a spectroscopic technique based on inelastic " +
                    "scattering of monochromatic light, usually from a laser source. Inelastic scattering means " +
                    "that the frequency of photons in monochromatic light changes upon interaction with a sample."));
        }

    }
/*
    @Override
    public Activity getActivity() {
        Intent intent = new Intent();
        intent.putExtra("deviceName", "some data");
        setActivityIntent(intent);
        return super.getActivity();
    }

*/
}

