package movie;

public class BaselineAllocator implements AllocatorStrategy {
	private PsuedoMatrix matrix;
	@Override
	public void setSize(Integer numRows, Integer numCols) {
		this.matrix = new PsuedoMatrix(numRows, numCols);
	}

	@Override
	public AllocatorResult allocate(Integer numSeats) {
		// TODO Auto-generated method stub
		return null;
	}

}
