package test;
import config.ConfigReader;
import sun.security.util.math.ImmutableIntegerModuloP;

public class MeasureReader {
	ConfigReader cfg = ConfigReader.getConfig();
	private String[] measure;
	private int character_count, string_count, curr_col, ts_beats, ts_beatlength;
	private char[] column;
	private int[] time_signature; //maybe it'll be a tuple of some sort? i'll stuck with 2 separate ints for now (see above)
	
	public MeasureReader(String[] measure, int beats, int beatlength) { //maybe need a better constuctor?
		this.measure = measure;
		this.character_count = measure[0].length();
		this.string_count = Integer.parseInt(cfg.getAttr("string"));
		this.ts_beats = beats;
		this.ts_beatlength = beatlength;
		curr_col = 0;
		readColumn();
	}
	
	
	public void readNote() {
		
	}
	
	private void readColumn() {
		this.column = new char[string_count];
		for(int i=0; i<string_count; i++) {
			column[i] = measure[i].charAt(curr_col);
		}
		curr_col ++;
	}
	

}
