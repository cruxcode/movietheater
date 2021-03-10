package movie;

public interface AllocatorStrategy {
	void setSize(Integer numRows, Integer numCols);
	AllocatorResult allocate();
}