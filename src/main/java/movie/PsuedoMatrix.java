package movie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdk.internal.org.jline.utils.Log;

public class PsuedoMatrix {
	private static Logger logger = LogManager.getLogger(PsuedoMatrix.class);
	private Integer numRows;
	private Integer numCols;
	private Integer bufferSeats;
	private ArrayList<LinkedList<FreeRange>> rows;
	public Integer getNumRows() {
		return numRows;
	}
	public void setNumRows(Integer numRows) {
		this.numRows = numRows;
	}
	public Integer getNumCols() {
		return numCols;
	}
	public void setNumCols(Integer numCols) {
		this.numCols = numCols;
	}
	
	PsuedoMatrix(Integer numRows, Integer numCols, Integer bufferSeats){
		this.numRows = numRows;
		this.numCols = numCols;
		this.bufferSeats = bufferSeats;
		// Initialize rows
		this.rows = new ArrayList<LinkedList<FreeRange>>(this.numRows);
		for(Integer i = 0; i < numRows; i++) {
			LinkedList<FreeRange> row = new LinkedList<FreeRange>();
			row.add(new FreeRange(0, numCols - 1));
			this.rows.add(i, row);
		}
	}
	
	FreeRange occupy(Integer row, Integer num1, Integer num2) {
		if(!(this.checkRowExists(row) && this.checkColumnExists(num1) && this.checkColumnExists(num2))) {
			return null;
		}
		FreeRange[] resultRanges = null;
		LinkedList<FreeRange> list = this.rows.get(row);
		for (FreeRange range: list) {
			if(range.validOccupyInput(num1, num2)) {
				resultRanges = range.occupy(num1, num2);
				addRemoveList(list, range, resultRanges);
				break;
			}
		}
		if(resultRanges != null) {
			Integer trimStart = resultRanges[0].getStart() - this.bufferSeats;
			if(trimStart < 0)
				trimStart = 0;
			Integer trimEnd = resultRanges[0].getEnd() + this.bufferSeats;
			if(trimEnd >= this.numCols)
				trimEnd = this.numCols - 1;
			
			trimFreeRanges(list, trimStart, trimEnd);
			if(this.checkRowExists(row - 1)) {
				trimFreeRanges(this.rows.get(row - 1), trimStart, trimEnd);
			}
			if(this.checkRowExists(row + 1)) {
				trimFreeRanges(this.rows.get(row + 1), trimStart, trimEnd);
			}
			return  resultRanges[0];
		}
		return null;
	}
	
	private static void trimFreeRanges(LinkedList<FreeRange> list, Integer trimStart, Integer trimEnd) {
		Iterator<FreeRange> iter = list.iterator();
		while(iter.hasNext()) {
			FreeRange range = iter.next();
			// full overlap and range is small or equal to
			if(range.getStart() >= trimStart && range.getEnd() <= trimEnd) {
				iter.remove();
			}
			// full overlap and range is bigger
			else if(range.getStart() < trimStart && range.getEnd() > trimEnd) {
				int index = list.indexOf(range);
				FreeRange left = new FreeRange(range.getStart(), trimStart - 1);
				FreeRange right = new FreeRange(trimEnd + 1, range.getEnd());
				list.add(index, right);
				list.add(index, left);
				list.remove(range);
				break;
			}
			// partial right overlap
			else if(range.getStart() < trimStart && range.getEnd() >= trimStart) {
				range.setEnd(trimStart - 1);
			}
			// partial left overlap
			else if(range.getStart() <= trimEnd && range.getEnd() > trimEnd) {
				range.setStart(trimEnd + 1);
			}
		}
	}
	
	private static void addRemoveList(LinkedList<FreeRange> list, FreeRange oldRange, FreeRange[] newRange) {
		int index = list.indexOf(oldRange);
		ArrayList<FreeRange> newRangeList = new ArrayList<FreeRange>(2);
		if(newRange[1] != null)
			newRangeList.add(newRange[1]);
		if(newRange[2] != null)
			newRangeList.add(newRange[2]);
		if(index >= 0) {
			list.addAll(index, newRangeList);
			list.remove(oldRange);
		}
	}
	
	private boolean checkRowExists(Integer num){
		if(num >=0 && num < this.numRows) {
			return true;
		}
		return false;
	}
	
	private boolean checkColumnExists(Integer num) {
		if(num >=0 && num < this.numCols) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public LinkedList<FreeRange> getRow(Integer rowNum) {
		if(this.checkRowExists(rowNum)) {
			return (LinkedList<FreeRange>)this.rows.get(rowNum).clone();
		}
		return null;
	}
}