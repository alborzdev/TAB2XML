package test;
import java.util.ArrayList;
import java.util.List;

import config.ConfigReader;

public class MeasureReaderV3 {
	
	//measure specific variables
	ConfigReader cfg = ConfigReader.getConfig();
	private String[] measure;
	private int character_count, string_count, curr_col, ts_beats, ts_beatlength;
	//note specific variables
	private String[] strColumn;
	private char[] column;
	private int noteLength;
	// A# = Bb, also
	private String[] tuning = {"E","B","G","D","A","E"};
	private String[] scale = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	private int[] octaves = {3,3,4,4,4,5};
	private int[] baseShifts = {2,5,1,4,7,2};
	private String[] lengths = {"whole","half","quarter","eighth","sixteenth"};
	boolean hasNextColumn; //has next column?
	
	//To do:
	//change type of column to string, read multiple columns for double digit frets
	//better way to decode notes

	
	public MeasureReaderV3(String[] measure) { //maybe need a better constuctor?
		this.measure = measure;
		this.character_count = measure[0].length();
		this.string_count = Integer.parseInt(cfg.getAttr("string_count"));
		this.ts_beats = 4;
		this.ts_beatlength = 4;
		this.hasNextColumn = true;
		curr_col = 0;
	}
	
	public MeasureReaderV3(String[] measure, int string_count, int beats, int beatlength) { //maybe need a better constuctor?
		this.measure = measure;
		this.character_count = measure[0].length();
		this.string_count = string_count;
		this.ts_beats = beats;
		this.ts_beatlength = beatlength;
		this.hasNextColumn = true;
		curr_col = 0;
	}
	
	public List<String[]> getNotes() {
		List<String[]> out = new ArrayList<String[]>();
		int[] shifts = getFrets(this.strColumn);
		for(int i = 0; i<shifts.length; i++) {
			if(shifts[i] >= 0) {
				String[] stepAndOctave = calculateNoteandOctave(i,shifts[i]);
				String alter = "";
				String accidental = "";
				
				if(stepAndOctave[0].length() > 1) {
					alter = "1";
					accidental = "sharp";
				}
				
				String[] noteProperties = {
						""+this.noteLength,																	//raw duration
						lengths[this.log2((this.ts_beats*this.ts_beatlength)/this.noteLength)],				//type
						""+stepAndOctave[0].charAt(0),																	//step
						stepAndOctave[1],																				//octave
						alter,																				//alter
						accidental,																			//accidental
						""+i,																					//string
						""+shifts[i]																			//fret
				};
				out.add(noteProperties);
				
				
				this.stringArrayDump("DEBUG: dumping noteProperties, values are (duration, type, step, octave, alter, accidental)", noteProperties);
			}
		}
		return out;
	}
	
	public void readNotes() {
		if(this.hasNextColumn) {
			//read column
			this.readColumn(curr_col);
			this.curr_col++;
			//if the newly read column is empty - usually at the start of a measure where formatting/rests/empty measure cause this then continue reading until a note is found
			while(this.isEmpty(this.column) && this.hasNextColumn) {
				this.readColumn(this.curr_col);
				this.curr_col++;
			}
			//save read notes
			this.strColumn = new String[this.string_count];
			for(int i=0; i<this.string_count; i++) {
				this.strColumn[i] = "" + this.column[i];
			}
			//check if the next column has notes - allows for double-digit frets
			this.readColumn(this.curr_col);
			this.curr_col++;
			if(!this.isEmpty(this.column)) {
				for(int i=0; i<this.string_count; i++) {
					//append only non-dash characters
					if(this.column[i] != '-') {
						this.strColumn[i] = this.strColumn[i] + this.column[i];
					}
				}
				this.noteLength = 1;
			}else {
				this.noteLength = 2;
			}
			//continue reading empty columns to determine note length
			this.readColumn(this.curr_col);
			this.curr_col++;
			while(this.isEmpty(this.column) && this.hasNextColumn) {
				this.readColumn(this.curr_col);
				this.curr_col++;
				this.noteLength ++;
			}
			//if it's not the end of the measure, backtrack one column to allow reading next time
			if(this.hasNextColumn) {
				this.curr_col--;
			}
			this.stringArrayDump("read column", this.strColumn);
			System.out.println("DEBUG: read note length: "+this.noteLength);		
		}
		
	}
	
	public boolean hasNext() {
		return this.hasNextColumn;
	}
	
	private String calculateNote(int string, int fret){
		String baseNote = this.tuning[string];
		int counter = -1;
		for (int i = 0; i<scale.length; i++) {
			if(baseNote.equalsIgnoreCase(scale[i])) {
				counter = i;
				break;
			}
		}
		if(counter < 0) {
			//fail
			return "!"; //need to handle bad numbers and stuff better? maybe not necessary at all?
		}else {
			int note = (counter+fret) % 12;
			return scale[note];
		}
		
	}
	
	private String[] calculateNoteandOctave(int string, int fret){
		System.out.println("DEBUG: calculating note and octave for string: " + string + " fret: " + fret);
		String baseNote = this.tuning[string];
		int counter = -1;
		for (int i = 0; i<scale.length; i++) {
			if(baseNote.equalsIgnoreCase(scale[i])) {
				counter = i;
				break;
			}
		}
		if(counter < 0) {
			//fail
			return null; //need to handle bad numbers and stuff better? maybe not necessary at all - all formatting filtering is done by tabreader
		}else {
			String[] out = new String[2];
			int octave = this.octaves[this.string_count-string-1];
			System.out.println("DEBUG: base octave: " + octave);
			for(int i=1; i<=fret; i++) {
				System.out.println("DEBUG: iteration: " + i + " octave: " + octave);
				System.out.println("DEBUG: calculation: " + (counter + i) + " mod: " + ((counter + i) % 12));
				System.out.println("DEBUG: note: " + this.scale[((counter + i) % 12)]);
				if(((counter + i) % 12) == 0) {
					octave ++;
				}
			}
			int note = (counter+fret) % 12;
			out[0] = scale[note];
			out[1] = "" + octave;
			return out;
		}
		
	}
	
	private int[] getFrets(String[] Column) {
		int[] out = new int[string_count];
		for(int i=0; i<string_count; i++) {
			try {
				out[i] = Integer.parseInt(Column[i]);
			}catch(NumberFormatException e) {
				out[i] = -1;
			}
		}
		return out;
		
	}
	
	private int log2 (int in) {
		int out = (int) (Math.log(in) / Math.log(2));
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
	
	private void readColumn(int in) {
		if(curr_col > character_count-1) {
			this.hasNextColumn = false;
		}else {
			this.column = new char[string_count];
			for(int i=0; i<string_count; i++) {
				column[i] = measure[i].charAt(in);
			}
		}
	}
	
	public void stringArrayDump(String arrayName, String[] in) {
		System.out.println("DEBUG: dumping contents of: " +arrayName);
		for(int i=0; i<in.length; i++) {
			System.out.println(in[i]);
		}
	}
}
