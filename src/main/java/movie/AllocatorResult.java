package movie;

import java.util.StringJoiner;

public class AllocatorResult {
	private Integer row;
	private Integer start;
	private Integer end;
	public Integer getRow() {
		return row;
	}
	public Integer getStart() {
		return start;
	}
	public Integer getEnd() {
		return end;
	}
	AllocatorResult(Integer row, Integer start, Integer end){
		this.row = row;
		this.start = start;
		this.end = end;
	}
	public String toString() {
		String rowName = convertToChar(this.getRow());
		StringJoiner joiner = new StringJoiner(" ");
		for(Integer i = this.getStart(); i <= this.getEnd(); i++) {
			joiner.add(rowName + i);
		}
		return joiner.toString();
	}
	public static String convertToChar(Integer row) {
		return Character.toString((char)(row + 65));
	}
}