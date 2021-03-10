package movie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App 
{
	private static Logger logger = LogManager.getLogger(App.class);
	private static AllocatorStrategy allocator;
    public static void main( String[] args )
    {
        logger.info("starting simulation...");
        if(args.length < 2) {
        	logger.fatal("less than two arguments passed");
        	return;
        }
        Integer numRows, numCols;
        try {
            numRows = Integer.parseInt(args[0]);
            numCols = Integer.parseInt(args[1]);
        } catch (Exception e) {
        	logger.fatal("error in conversion of arguments", e);
        	return;
        }

        
        allocator = new BaselineAllocator();
        allocator.setSize(numRows, numCols);
        AllocatorResult result = allocator.allocate(5);
        System.out.print(result.toString());
        logger.info("stopping simulation...");
    }
}
