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
	
	boolean occupy(Integer row, Integer num1, Integer num2) {
		if(this.checkRowExists(row) && this.checkColumnExists(num1) && this.checkColumnExists(num2)) {
			logger.fatal("trying to occupy seats outside range");
			return false;
		}
		LinkedList<FreeRange> list = this.rows.get(row);
		for (FreeRange range: list) {
			if(range.validOccupyInput(num1, num2)) {
				range.occupy(num1, num2);
				return true;
			}
		}
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