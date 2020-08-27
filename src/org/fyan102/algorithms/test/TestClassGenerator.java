package org.fyan102.algorithms.test;
import org.fyan102.algorithms.interfaces.ITest;
public class TestClassGenerator implements ITest {
	private int value;

	public TestClassGenerator() {
		value = 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int newValue) {
		value = newValue;
	}
}
