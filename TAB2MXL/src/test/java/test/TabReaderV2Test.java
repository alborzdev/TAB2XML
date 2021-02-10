package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TabReaderV2Test {

	@Test
	void measureGetterTest() {
		TabReaderV2 tabReader = new TabReaderV2();
		tabReader.readMeasure();
		String [] measure = tabReader.getMeasure();
		assertEquals("------0-1---1-3-", measure[0]);		
	}
	
}
