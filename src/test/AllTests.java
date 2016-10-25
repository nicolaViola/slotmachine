package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CroupierTest.class, 
				MachineTest.class, 
				MachineControllerTester.class })
public class AllTests {

}
