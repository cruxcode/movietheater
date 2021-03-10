package movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


public class App 
{
	private static Logger logger = LogManager.getLogger(App.class);
	private static AllocatorStrategy allocator;
    public static void main( String[] args )
    {
    	if(!debugSet()) {
    		Configurator.setLevel("movie", Level.OFF);
    	}
        if(args.length != 1) {
        	logger.fatal("exactly one argument is required");
        	return;
        }
        String filename = args[0];
        Integer numRows = 10;
        Integer numCols = 20;
        if(getAlgoType() != null) {
        	String algoType = getAlgoType();
        	switch(algoType) 
        	{
        		case "random":
        			logger.info("strategy used is " + RandomAllocator.class.getName());
        			allocator = new RandomAllocator();
        			run(allocator, numRows, numCols, filename);
        			break;
        		case "leastloss":
        			logger.info("strategy used is " + LeastLossAllocator.class.getName());
        			allocator = new LeastLossAllocator();
        			run(allocator, numRows, numCols, filename);
        			break;
        		default:
                	logger.info("strategy used is " + BaselineAllocator.class.getName());
                    allocator = new BaselineAllocator();
                    run(allocator, numRows, numCols, filename);
        	}
        }
    }
    
    private static void run(AllocatorStrategy allocator, Integer numRows, Integer numCols, String filename) {
        allocator.setSize(numRows, numCols);
        File inputFile = new File(filename);
        File outputFile = new File(filename+".output.txt");
        String[] splits = null;
        Scanner reader = null;
        FileWriter writer = null;
        try {
			reader = new Scanner(inputFile);
			writer = null;
			try {
				writer = new FileWriter(outputFile);
			} catch (IOException e1) {
				logger.fatal("output file could not be created", e1);
				reader.close();
				return;
			}
		} catch (FileNotFoundException e) {
			logger.fatal("input file does not exist", e);
			return;
		}

        while(reader.hasNextLine()) {
			String line = reader.nextLine();
			splits = line.split(" ");
			try {
				if(splits.length < 2) {
					logger.fatal("bad input file");
					closeFilesSafely(reader, writer);
					return;
				}
				String reqName = splits[0];
				Integer numSeats = null;
				numSeats = Integer.parseInt(splits[1]);
				AllocatorResult result = allocator.allocate(numSeats);
				if(result != null) {
					logger.debug(reqName + " " + result);
					writer.write(reqName + " " + result + "\n");
				}
	        } catch (Exception e) {
	        	closeFilesSafely(reader, writer);
	        	logger.fatal("second split should be an integer", e);
	        }
		}
        
		closeFilesSafely(reader, writer);
		System.out.print(outputFile.getAbsolutePath());
    }
    
    private static void closeFilesSafely(Scanner reader, FileWriter writer) {
    	reader.close();
    	try {
			writer.close();
		} catch (IOException e) {
			logger.error("unable to close final", e);
		}
    }
    
    private static boolean debugSet() {
    	Map<String, String> env = System.getenv();
    	String envName = "APPDEBUG";
    	if(env.get(envName) != null) {
    		if(env.get(envName).equals("true"))
    			return true;
    	}
        return false;
    }
    
    private static String getAlgoType() {
    	Map<String, String> env = System.getenv();
    	String envName = "MOVIEALLOC";
    	if(env.get(envName) != null) {
    		return env.get(envName);
    	}

        return null;
    }
}
