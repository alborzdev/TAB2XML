package test;
import config.ConfigReader;

public class MeasureReader {
	ConfigReader cfg = ConfigReader.getConfig();
	private String[] measure;
	private int character_count, string_count, curr_col, ts_beats, ts_beatlength;
	private char[] column;
	private int[] time_signature; //maybe it'll be a tuple of some sort? i'll stuck with 2 separate ints for now (see above)
	//more hardcoded stuff assuming guitar with standard tuning EADGBe - to be changed - but how?
	//assuming no key signature
	// A# = Bb, also
	private char[] tuning = {'E','B','G','D','A','E'};
	private char[] scale = {'A','#','B','C','#','D','#','E','F','#','G','#'};
	boolean hasNextColumn; //has next column?
	
	//To do:
	//change type of column to string, read multiple columns for double digit frets
	//better way to decode notes

	
	public MeasureReader(String[] measure, int beats, int beatlength) { //maybe need a better constuctor?
		this.measure = measure;
		this.character_count = measure[0].length();
		this.string_count = Integer.parseInt(cfg.getAttr("string_count"));
		this.ts_beats = beats;
		this.ts_beatlength = beatlength;
		this.hasNextColumn = true;
		curr_col = 0;
		readColumn();
	}
	
	
	public void readNote() {
		if(isEmpty(this.column)) {
			this.readColumnV2();
		}else {
			int[] shifts = getFrets(this.column);
			for(int i = 0; i<shifts.length; i++) {
				if(shifts[i] >= 0) {
					System.out.println("DEBUG: string played: "+i+" fret played: "+shifts[i]);
					System.out.println(calculateNote(i,shifts[i]));
				}
			}
			int length = nextNote(0) +1; //+1 to include current column
			System.out.println("DEBUG: length of note/chord: "+length);
			System.out.println("DEBUG: ------------------------------------------------");
		}
	}
	
	public boolean hasNext() {
		return this.hasNextColumn;
	}
	
	private int nextNote(int count) {
		if (this.readColumnV2()) {
			if(isEmpty(this.column)) {
				count = nextNote(count + 1);
			}else {
				return count;
			}
		}else {
			return count;
		}
		return count;
	}
	
	private char calculateNote(int string, int fret){
		char baseNote = this.tuning[string];
		int counter = -1;
		for (int i = 0; i<scale.length; i++) {
			if(baseNote == scale[i]) {
				counter = i;
				break;
			}
		}
		if(counter < 0) {
			//fail
			return '!'; //need to handle bad numbers and stuff better? maybe not necessary at all?
		}else {
			int note = (counter+fret) % 12;
			return scale[note];
		}
		
	}
	
	private int[] getFrets(char[] column) {
		int[] out = new int[string_count];
		for(int i=0; i<string_count; i++) {
			try {
				out[i] = Integer.parseInt(""+column[i]);
			}catch(NumberFormatException e) {
				out[i] = -1;
			}
		}
		return out;
		
	}
	
	private boolean isEmpty(char[] column) {
		boolean out = true;
		for (char ch: column) {
			if(ch != '-') {
				out = false;
				break;
			}
		}
		return out;
	}
	
	private void readColumn() {
		this.column = new char[string_count];
		for(int i=0; i<string_count; i++) {
			column[i] = measure[i].charAt(curr_col);
		}
		curr_col ++;
	}
	
	private boolean readColumnV2() {
		boolean out = true;
		try {
			this.column = new char[string_count];
			for(int i=0; i<string_count; i++) {
				column[i] = measure[i].charAt(curr_col);
			}
			curr_col ++;
		}catch (Exception e) {
			out = false;
		}
		hasNextColumn = out;
		return out;
	}

}
