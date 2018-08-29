package seng202.group8.Parser_Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seng202.group8.Parser.Parser;

public class ParserTest {
    private Parser parserTest;

    @Before
    public void setUp() throws Exception {
        parserTest =  new Parser("seng202_2018_example_data.csv");
    }

    @After
    public void tearDown() {
        parserTest = null;
    }
}
