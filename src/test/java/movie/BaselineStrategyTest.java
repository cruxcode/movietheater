package movie;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaselineStrategyTest {

	@Test
	public void test() {
		BaselineAllocator alloc = new BaselineAllocator();
		AllocatorResult result;
		alloc.setSize(10, 20);
		result = alloc.allocate(2);
		assertEquals(resultEqual(result, new AllocatorResult(0, 0, 1)), true);
		result = alloc.allocate(4);
		assertEquals(resultEqual(result, new AllocatorResult(0, 5, 8)), true);
		result = alloc.allocate(4);
		assertEquals(resultEqual(result, new AllocatorResult(0, 12, 15)), true);
		result = alloc.allocate(10);
		assertEquals(resultEqual(result, new AllocatorResult(2, 0, 9)), true);
		assertEquals(result.toString(), "C0 C1 C2 C3 C4 C5 C6 C7 C8 C9");
		result = alloc.allocate(3);
		assertEquals(resultEqual(result, new AllocatorResult(2, 13, 15)), true);
		result = alloc.allocate(18);
		assertEquals(resultEqual(result, new AllocatorResult(4, 0, 17)), true);
		// check null condition
		alloc = new BaselineAllocator();
		result = alloc.allocate(10);
		assertEquals(result, null);
	}

	private boolean resultEqual(AllocatorResult r1, AllocatorResult r2) {
		if(r1.getRow() == r2.getRow() && r1.getStart() == r2.getStart() && r1.getEnd() == r2.getEnd()) {
			return true;
		}
		return false;
	}
}
