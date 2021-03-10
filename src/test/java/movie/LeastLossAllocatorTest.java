package movie;

import static org.junit.Assert.*;

import org.junit.Test;

public class LeastLossAllocatorTest {

	@Test
	public void test() {
		LeastLossAllocator alloc = new LeastLossAllocator();
		AllocatorResult result;
		alloc.setSize(10, 20);
		result = alloc.allocate(5);
		assertEquals(result.toString(), "A0 A1 A2 A3 A4");
		result = alloc.allocate(2);
		assertEquals(result.toString(), "C0 C1");
		result = alloc.allocate(8);
		assertEquals(result.toString(), "C5 C6 C7 C8 C9 C10 C11 C12");
		result = alloc.allocate(4);
		assertEquals(result.toString(), "E0 E1 E2 E3");
	}

}
