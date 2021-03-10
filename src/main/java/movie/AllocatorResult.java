package movie;

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
		return this.row.toString() + " " + this.start.toString() + " " + this.end.toString();
	}
}