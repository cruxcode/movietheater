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
