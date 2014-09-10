package ru.andreychapliuk;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	CoinsMashine cm = new CoinsMashine();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
        
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	
    	
//    	assertTrue(condition);
        System.out.println("!");
        testStrategy();
        testCheck(0);
        
    }

    public void testCheck(double d){
    	assertTrue(cm.checkSum(d));
    }
    
    public void testStrategy()
    {
    	assertTrue(cm.selectStrategy("EUR"));
    	
    	assertFalse(cm.selectStrategy("EU"));
    	assertTrue(cm.selectStrategy("UAH"));
    	assertTrue(cm.selectStrategy("$"));

    	
    }
    
}
