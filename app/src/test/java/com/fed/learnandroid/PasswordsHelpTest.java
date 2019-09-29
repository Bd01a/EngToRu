package com.fed.learnandroid;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordsHelpTest {

    public static final String[] RUS = {"й","ц","у","к","е"};
    public static final String[] ENG = {"q","w","e","r","t"};

    public static final String[] SOURCES = {
            "",
            "екуцй",
            "ЙЦУК",
            "зщшг"
    };
    public static final String[] RESULTS = {
            "",
            "trewq",
            "QWER",
            "зщшг"

    };

    private PasswordsHelp helper;

    @Before
    public void setUp() throws Exception {
        helper = new PasswordsHelp(RUS, ENG);
    }

    @Test
    public void convert() {
        assertTrue("Error in this case", SOURCES.length==RESULTS.length);
        for(int i=0; i<SOURCES.length; i++){
            assertEquals(RESULTS[i], helper.convert(SOURCES[i]));
        }

    }
    @Test(expected = IllegalArgumentException.class)
    public void convertIsThrows() {
        new PasswordsHelp(RUS, new String[]{});
    }

}