package movie;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PsuedoMatrix {
	private static Logger logger = LogManager.getLogger(PsuedoMatrix.class);
	private Integer numRows;
	private Integer numCols;
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
	
	PsuedoMatrix(Integer numRows, Integer numCols){
		this.numRows = numRows;
		this.numCols = numCols;
		// Initialize rows
		rows = new ArrayList<LinkedList<FreeRange>>();
		for(Integer i = 0; i < numRows; i++) {
			LinkedList<FreeRange> row = new LinkedList<FreeRange>();
			row.add(new FreeRange(0, numCols));
			rows.set(i, row);
		}
	}
	
	FreeRange occupy(Integer row, Integer num1, Integer num2) {
		if(this.checkRowExists(row) && this.checkColumnExists(num1) && this.checkColumnExists(num2)) {
			logger.fatal("trying to occupy seats outside range");
			return null;
		}
		LinkedList<FreeRange> list = this.rows.get(row);
		for (FreeRange range: list) {
			if(range.validOccupyInput(num1, num2)) {
				FreeRange[] resultRanges = range.occupy(num1, num2);
				addRemoveList(list, range, resultRanges);
				return resultRanges[0];
			}
		}
		return null;
	}
	
	private static boolean addRemoveList(LinkedList<FreeRange> list, FreeRange oldRange, FreeRange[] newRange) {
		Integer index = list.indexOf(oldRange);
		ArrayList<FreeRange> newRangeList = new ArrayList<FreeRange>(2);
		newRangeList.add(newRange[0]);
		newRangeList.add(newRange[1]);
		if(index >= 0) {
			list.addAll(index, newRangeList);
		}
		list.remove(oldRange);
		return false;
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