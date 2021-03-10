package movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App 
{
	private static Logger logger = LogManager.getLogger(App.class);
	private static AllocatorStrategy allocator;
    public static void main( String[] args )
    {
        logger.info("starting simulation...");
        if(args.length != 1) {
        	logger.fatal("exactly one argument is required");
        	return;
        }
        Integer numRows = 10;
        Integer numCols = 20;

        
        allocator = new BaselineAllocator();
        allocator.setSize(numRows, numCols);
        File inputFile = new File(args[0]);
        String[] splits = null;
        try {
			Scanner reader = new Scanner(inputFile);
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				splits = line.split(" ");
				if(splits.length < 2) {
					logger.fatal("bad input file");
					return;
				}
		        String reqName = splits[0];
		        Integer numSeats = null;
		        try {
		        	numSeats = Integer.parseInt(splits[1]);
		        	AllocatorResult result = allocator.allocate(numSeats);
		        	if(result != null) {
		            	System.out.println(result.toString());
		            }
		        } catch (Exception e) {
		        	logger.fatal("second split should be an integer");
		        }
			}
		} catch (FileNotFoundException e) {
			logger.fatal("input file does not exist");
			return;
		}
        logger.info("stopping simulation...");
    }
}
