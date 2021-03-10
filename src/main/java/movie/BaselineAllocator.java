package movie;

import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdk.internal.org.jline.utils.Log;

public class BaselineAllocator implements AllocatorStrategy {
	private static Logger logger = LogManager.getLogger(BaselineAllocator.class);
	private PsuedoMatrix matrix;
	@Override
	public void setSize(Integer numRows, Integer numCols) {
		this.matrix = new PsuedoMatrix(numRows, numCols);
	}

	@Override
	public AllocatorResult allocate(Integer numSeats) {
		for (Integer i = 0; i < this.matrix.getNumRows(); i++) {
			LinkedList<FreeRange> row = this.matrix.getRow(i);
			for(FreeRange range: row) {
				FreeRange resultRange = this.matrix.occupy(i, range.getStart(), range.getStart() + numSeats - 1);
				if(resultRange != null) {
					return new AllocatorResult(i, resultRange.getStart(), resultRange.getEnd());
				}
			}
		}
		return null;
	}

}
