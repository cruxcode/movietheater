package movie;

import java.util.Iterator;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LeastLossAllocator implements AllocatorStrategy {
	private static Logger logger = LogManager.getLogger(LeastLossAllocator.class);
	private PsuedoMatrix matrix;
	private Integer bufferSize;
	@Override
	public void setSize(Integer numRows, Integer numCols) {
		this.bufferSize = 3;
		this.matrix = new PsuedoMatrix(numRows, numCols, this.bufferSize);
	}

	@Override
	public AllocatorResult allocate(Integer numSeats) {
		if(this.matrix == null) {
			logger.fatal("setSize must be called first before calling allocate");
			return null;
		}
		Integer minLoss = -1;
		Integer minRow = null;
		FreeRange minRange = null;
		for (Integer i = 0; i < this.matrix.getNumRows(); i++) {
			LinkedList<FreeRange> row = this.matrix.getRow(i);
			Iterator<FreeRange> iter = row.iterator();
			while(iter.hasNext()) {
				FreeRange range = iter.next();
				if(range.validOccupyInput(range.getStart(), range.getStart() + numSeats - 1)) {
					Integer trimStart = range.getStart() - this.bufferSize;
					Integer trimEnd = range.getEnd() + this.bufferSize;
					Integer currLoss = this.calculateLoss(i, trimStart, trimEnd);
					if(currLoss < minLoss || minLoss == -1) {
						minLoss = currLoss;
						minRow = i;
						minRange = range;
					}
				}
			}
		}
		if(minRange != null) {
			FreeRange resultRange = this.matrix.occupy(minRow, minRange.getStart(), minRange.getStart() + numSeats - 1);
			if(resultRange != null) {
				return new AllocatorResult(minRow, resultRange.getStart(), resultRange.getEnd());
			}
		}
		return null;
	}

	private Integer calculateLoss(Integer row, Integer trimStart, Integer trimEnd) {
		if(trimStart < 0) {
			trimStart = 0;
		}
		if(trimEnd > this.matrix.getNumCols()) {
			trimEnd = this.matrix.getNumCols() - 1;
		}
		Integer loss = this.calculateLossHelper(row, trimStart, trimEnd);
		if(this.matrix.checkRowExists(row - 1)) {
			loss = loss + this.calculateLossHelper(row - 1, trimStart, trimEnd);
		}
		if(this.matrix.checkRowExists(row + 1)){
			loss = loss + this.calculateLossHelper(row + 1, trimStart, trimEnd);
		}
		return loss;
	}
	private Integer calculateLossHelper(Integer row, Integer trimStart, Integer trimEnd) {
		LinkedList<FreeRange> list = this.matrix.getRow(row);
		Integer loss = 0;
		for(FreeRange range: list) {
			// full overlap and range is small or equal to
			if(range.getStart() >= trimStart && range.getEnd() <= trimEnd) {
				loss = loss + trimStart - range.getStart() + trimEnd - range.getEnd();
			}
			// full overlap and range is bigger
			else if(range.getStart() < trimStart && range.getEnd() > trimEnd) {
				loss = loss + trimEnd - trimStart + 1;
				break;
			}
			// partial right overlap
			else if(range.getStart() < trimStart && range.getEnd() >= trimStart) {
				loss = loss + range.getEnd() - trimStart + 1;
			}
			// partial left overlap
			else if(range.getStart() <= trimEnd && range.getEnd() > trimEnd) {
				loss = loss + range.getStart() - trimEnd + 1;
			}
			
		}
		return loss;
	}
}
