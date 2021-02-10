package parsing;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import config.ConfigReader;
import test.TabReaderV2;
import test.MeasureReaderV2;

public class ParsingTest {
	ConfigReader cfg = ConfigReader.getConfig();
	TabReaderV2 tb;;
	MeasureReaderV2 mr;
	@BeforeEach
	public void setup() {
		tb = new TabReaderV2(cfg.getAttr("hotcrossbuns_path")+cfg.getAttr("hotcrossbuns_file"));
		tb.readMeasure();
		mr = new MeasureReaderV2(tb.getMeasure(),4,4);
	}
	
	@Test
	public void testConfig() {
		assertTrue(cfg.getAttr("attr1").equals("test"));
		assertFalse(cfg.getAttr("attr2").equals("test"));
		assertTrue(cfg.getAttr("invalidAttribute") == null);
	}
	
	@Test
	public void testTabReader() {
		assertTrue(tb.hasNext());
		assertTrue(tb.getMeasure()[4].length() == 16);
		assertTrue(tb.getMeasure()[4].charAt(0) == '3');
		tb.readMeasure();
		assertFalse(tb.hasNext());
	}
	
	@Test
	public void testMeasureReader() {
		List<String[]> list = mr.readNote();
		assertTrue(list.size() == 1);
		assertTrue(list.get(0)[0].equals("16"));
		assertTrue(list.get(0)[2].equals("C"));
	}

}
