package movie;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class RowTrimTest {

	@Test
	public void test() {
		LinkedList<FreeRange> list = new LinkedList<FreeRange>();
		// test full overlap and small
		list.add(new FreeRange(4, 6));
		list.add(new FreeRange(15, 18));
		PsuedoMatrix.trimFreeRanges(list, 3, 8);
		assertEquals(list.size(), 1);
		assertEquals(this.equalRange(list.get(0), new FreeRange(15, 18)), true);
		// test full overlap and big
		list = new LinkedList<FreeRange>();
		list.add(new FreeRange(0, 4));
		list.add(new FreeRange(6, 15));
		list.add(new FreeRange(18, 19));
		PsuedoMatrix.trimFreeRanges(list, 10, 12);
		assertEquals(list.size(), 4);
		assertEquals(this.equalRange(list.get(0), new FreeRange(0, 4)), true);
		assertEquals(this.equalRange(list.get(1), new FreeRange(6, 9)), true);
		assertEquals(this.equalRange(list.get(2), new FreeRange(13, 15)), true);
		assertEquals(this.equalRange(list.get(3), new FreeRange(18, 19)), true);
		// test partial right overlap and left overlap together
		list = new LinkedList<FreeRange>();
		list.add(new FreeRange(0, 4));
		list.add(new FreeRange(7, 15));
		PsuedoMatrix.trimFreeRanges(list, 3, 12);
		assertEquals(list.size(), 2);
		assertEquals(this.equalRange(list.get(0), new FreeRange(0, 2)), true);
		assertEquals(this.equalRange(list.get(1), new FreeRange(13, 15)), true);
	}
	
	boolean equalRange(FreeRange r1, FreeRange r2) {
		if(r1.getEnd() != r2.getEnd() || r1.getStart() != r2.getStart()) {
			return false;
		}
		return true;
	}

}
