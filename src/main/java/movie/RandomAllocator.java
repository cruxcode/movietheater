package movie;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomAllocator implements AllocatorStrategy {
	private static Logger logger = LogManager.getLogger(RandomAllocator.class);
	private PsuedoMatrix matrix;
	@Override
	public void setSize(Integer numRows, Integer numCols) {
		Integer bufferSize = 3;
		this.matrix = new PsuedoMatrix(numRows, numCols, bufferSize);
	}

	@Override
	public AllocatorResult allocate(Integer numSeats) {
		HashSet<Integer> rowsChecked = new HashSet<Integer>();
		while(rowsChecked.size() < this.matrix.getNumRows()) {
			// randomly choose a row
			Integer row = getRandomRow(this.matrix.getNumRows());
			rowsChecked.add(row);
			// then do a least loss over the columns
			FreeRange range = this.findLeastLossFreeRange(this.matrix.getRow(row), numSeats);
			if(range != null) {
				FreeRange resultRange = this.matrix.occupy(row, range.getStart(), range.getStart() + numSeats - 1);
				return new AllocatorResult(row, resultRange.getStart(), resultRange.getEnd());
			}			
		}
		logger.info("Seats cannot be allocated");
		return null;
	}
	
	public FreeRange findLeastLossFreeRange(LinkedList<FreeRange> list, Integer numSeats) {
		Integer minCost = this.matrix.getNumCols();
		FreeRange minCostlyRange = null;
		for(FreeRange range: list) {
			if(range.validOccupyInput(range.getStart(), range.getStart() + numSeats - 1)) {
				Integer currCost = (range.getEnd() - range.getStart() + 1) - numSeats;
				if(currCost < minCost) {
					minCost = currCost;
					minCostlyRange = range;
				}
			}
		}
		return minCostlyRange;
	}
	
	public static Integer getRandomRow(Integer max){
		Random random = new Random();
		return random.nextInt(max);
	}
}
