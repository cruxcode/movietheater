package movie;

import java.util.LinkedList;

public class BaselineAllocator implements AllocatorStrategy {
	private PsuedoMatrix matrix;
	@Override
	public void setSize(Integer numRows, Integer numCols) {
		this.matrix = new PsuedoMatrix(numRows, numCols);
	}

	@Override
	public AllocatorResult allocate(Integer numSeats) {
		AllocatorResult result = null;
		for (Integer i = 0; i < this.matrix.getNumRows(); i++) {
			LinkedList<FreeRange> row = this.matrix.getRow(i);
			
		}
		return result;
	}

}
