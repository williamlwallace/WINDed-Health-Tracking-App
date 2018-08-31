package seng202.group8.Parser_Tests;

import org.junit.After;
import org.junit.Before;

import seng202.group8.parser.Parser;

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
