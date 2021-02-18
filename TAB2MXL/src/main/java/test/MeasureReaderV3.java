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
	private String[] scale = {"A","A#","B","C","C#","D","D#","E","F","F#","G","G#"};
	boolean hasNextColumn; //has next column?
	
	//To do:
	//change type of column to string, read multiple columns for double digit frets
	//better way to decode notes

	
	public MeasureReaderV3(String[] measure, int beats, int beatlength) { //maybe need a better constuctor?
		this.measure = measure;
		this.character_count = measure[0].length();
		this.string_count = Integer.parseInt(cfg.getAttr("string_count"));
		this.ts_beats = beats;
		this.ts_beatlength = beatlength;
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
