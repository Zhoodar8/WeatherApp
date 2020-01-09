package com.example.weatherapp;

import android.content.Context;

import androidx.test.espresso.intent.Intents;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.weatherapp.data.PreferenceHelper;
import com.example.weatherapp.ui.splash.SplashActivity;

import junit.extensions.ActiveTestSuite;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
  public   ActivityTestRule<SplashActivity> activityActivityTestRule = new ActivityTestRule<>
            (SplashActivity.class);


    @Before
    public void before(){
        Intents.init();
    }

    @Test
    public void splashActivityTest2() {
        PreferenceHelper.clear();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1,Intents.getIntents().size());

    }


    @Test
    public void splashActivityTest() {

        PreferenceHelper.setIsFirstLaunch();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1,Intents.getIntents().size());

    }


    @After
    public void after() {
        PreferenceHelper.clear();
        Intents.release();
    }
}
