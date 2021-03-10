package movie;

import java.io.File;

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
        File inputFile = new File(args[0]);
        if(!inputFile.exists()) {
        	logger.fatal("input file does not exist");
        	return;
        }
        
        Integer numRows = 10;
        Integer numCols = 20;

        
        allocator = new BaselineAllocator();
        allocator.setSize(numRows, numCols);
        AllocatorResult result = allocator.allocate(5);
        if(result != null) {
        	System.out.println(result.toString());
        }
        result = allocator.allocate(3);
        if(result != null) {
        	System.out.println(result.toString());
        }
        result = allocator.allocate(3);
        if(result != null) {
        	System.out.println(result.toString());
        }
        result = allocator.allocate(1);
        if(result != null) {
        	System.out.println(result.toString());
        }
        result = allocator.allocate(10);
        if(result != null) {
        	System.out.println(result.toString());
        }
        result = allocator.allocate(15);
        if(result != null) {
        	System.out.println(result.toString());
        }
        logger.info("stopping simulation...");
    }
}
