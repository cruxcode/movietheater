package movie;

public class MockRandomAllocator extends RandomAllocator {
	private Integer randomRow;
	@Override
	public Integer getRandomRow(Integer max) {
		return randomRow;
	}
	public void setRandomRow(Integer row) {
		this.randomRow = row;
	}
}
