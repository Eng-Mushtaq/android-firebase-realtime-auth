package com.example.vamsi.login;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry; // Updated import
import androidx.test.ext.junit.runners.AndroidJUnit4; // Updated import

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext(); // Updated method

        assertEquals("com.example.vamsi.login", appContext.getPackageName());
    }
}
