package movie;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AppTest.class, BaselineStrategyTest.class, LeastLossAllocatorTest.class, RandomAllocatorTest.class,
		RowTrimTest.class })
public class AllTests {

}
