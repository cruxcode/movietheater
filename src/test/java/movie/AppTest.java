package movie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
    	Map<String, String> env = System.getenv();
    	String envName = "TESTFILE";
    	String[] args = new String[0];
    	if(env.get(envName) != null) {
    		args = new String[1];
    		args[0] = env.get(envName);
    	} else {
    		return;
    	}
    	java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
    	System.setOut(new java.io.PrintStream(out));
        App.main(args);
//        System.err.println("Out was: " + out.toString());
        assertEquals(out.toString(), env.get(envName) + App.ouputSuffix);
    }
}
