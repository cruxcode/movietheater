package movie;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreeRange {
	private static Logger logger = LogManager.getLogger(FreeRange.class);
	private Integer start;
	private Integer end;
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	
	FreeRange(Integer num1, Integer num2){
		this.setStart(getSmaller(num1, num2));
		this.setEnd(getBigger(num1, num2));
	}
	
	public FreeRange[] occupy(Integer num1, Integer num2){
		if(!this.validOccupyInput(num1, num2)) {
			return null;
		}
		FreeRange[] resultingRanges = new FreeRange[3];
		resultingRanges[0] = new FreeRange(getSmaller(num1, num2), getBigger(num1, num2)); 
		Integer newEnd = getSmaller(num1, num2) - 1;
		Integer newStart = getBigger(num1, num2) + 1;
		if(this.checkInRange(newEnd)) {
			resultingRanges[1] = new FreeRange(this.getStart(), newEnd);
		}
		if(this.checkInRange(newStart)) {
			resultingRanges[2] = new FreeRange(newStart, this.getEnd());
		}
		return resultingRanges;
	}
	
	public boolean validOccupyInput(Integer num1, Integer num2) {
		if(this.checkInRange(num1) && this.checkInRange(num2)) {
			return true;
		}
		return false;
	}
	
	private boolean checkInRange(Integer num1) {
		if(num1 < this.getStart() || num1 > this.getEnd()) {
			return false;
		}
		return true;
	}
	
	private static Integer getSmaller(Integer num1, Integer num2) {
		if(num1 < num2) {
			return num1;
		}
		return num2;
	}
	
	private static Integer getBigger(Integer num1, Integer num2) {
		if(num1 > num2) {
			return num1;
		}
		return num2;
	}
}