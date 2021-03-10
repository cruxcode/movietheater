package movie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
        File outputFile = new File(args[0]+".output.txt");
        String[] splits = null;
        try {
			Scanner reader = new Scanner(inputFile);
			FileWriter writer = null;
			try {
				writer = new FileWriter(outputFile);
			} catch (IOException e1) {
				logger.fatal("output file could not be created", e1);
				reader.close();
				return;
			}
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				splits = line.split(" ");
				if(splits.length < 2) {
					logger.fatal("bad input file");
					reader.close();
					writer.close();
					return;
				}
		        String reqName = splits[0];
		        Integer numSeats = null;
		        try {
		        	numSeats = Integer.parseInt(splits[1]);
		        	AllocatorResult result = allocator.allocate(numSeats);
		        	if(result != null) {
		            	System.out.println(reqName + " " + result);
		            	writer.write(reqName + " " + result + "\n");
		            }
		        } catch (Exception e) {
		        	reader.close();
		        	writer.close();
		        	logger.fatal("second split should be an integer");
		        }
			}
			reader.close();
			writer.close();
		} catch (FileNotFoundException e) {
			logger.fatal("input file does not exist");
			return;
		} catch (IOException e) {
			logger.fatal("IOException", e);
		}
        logger.info("stopping simulation...");
    }
    
}
