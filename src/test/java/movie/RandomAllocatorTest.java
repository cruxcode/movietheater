package movie;

import static org.junit.Assert.*;

import org.junit.Test;

public class RandomAllocatorTest {

	@Test
	public void test() {
		MockRandomAllocator alloc = new MockRandomAllocator();
		AllocatorResult result;
		alloc.setSize(10, 20);
		alloc.setRandomRow(3);
		result = alloc.allocate(2);
		assertEquals(result.toString(), "D0 D1");
		alloc.setRandomRow(5);
		result = alloc.allocate(8);
		assertEquals(result.toString(), "F0 F1 F2 F3 F4 F5 F6 F7");
		
		RandomAllocator ralloc = new RandomAllocator();
		Integer row = ralloc.getRandomRow(15);
		assertTrue(row < 15);
	}

}
