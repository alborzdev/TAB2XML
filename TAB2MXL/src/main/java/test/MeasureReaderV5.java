package test;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import config.ConfigReader;

public class MeasureReaderV5 {
	
	//measure specific variables
	ConfigReader cfg = ConfigReader.getConfig();
	private String[] measure;
	private int character_count, string_count, curr_col, ts_beats, ts_beatlength, trueMeasureLength, wNoteLength;
	//note specific variables
	private String[] strColumn, slurStatus, pSlur, hSlur; //assume that only hammeron/pulloff can slur => don't think u can slur any other way on guitar lmao
	private char[] column;
	private int noteLength;
	// A# = Bb, also
	private String[] tuning = {"E","B","G","D","A","E"};
	private String[] scale = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
	private int[] octaves = {4,3,3,3,2,2};
	private String[] lengths = {"whole","half","quarter","eighth","16th", "32nd", "64th", "128th"};
	private String numbersOnlyRegex="([0-9])*";
	private Pattern nOPat = Pattern.compile(numbersOnlyRegex);
	boolean hasNextColumn; //has next column?
	
	//To do:
	//change type of column to string, read multiple columns for double digit frets
	//better way to decode notes

	
	protected MeasureReaderV5(String[] measure) { //basic instructor for testing do not use
		this.measure = measure;
		this.character_count = measure[0].length();
		this.trueMeasureLength = this.floor2pow2(this.character_count);
		this.string_count = Integer.parseInt(cfg.getAttr("string_count"));
		this.ts_beats = 4;
		this.ts_beatlength = 4;
		this.hasNextColumn = true;
		curr_col = 0;
	}
	
	protected MeasureReaderV5(String[] measure, int beats, int beatlength) { //deprecated do not use
		this.measure = measure;
		this.character_count = measure[0].length();
		this.trueMeasureLength = this.floor2pow2(this.character_count);
		this.string_count = measure.length;
		this.ts_beats = beats;
		this.ts_beatlength = beatlength;
		this.hasNextColumn = true;
		curr_col = 0;
	}
	
	public MeasureReaderV5(String[] measure, String[] tuning, int beats, int beatlength) { 
		this.measure = measure;
		this.character_count = measure[0].length();
		this.string_count = measure.length;
		this.tuning = tuning;
		this.ts_beats = beats;
		this.ts_beatlength = beatlength;
		this.hasNextColumn = true;
		curr_col = 0;
		this.inferOctaves(tuning);
		this.trueMeasureLength = this.getTrueMeasureLength();
		this.wNoteLength = this.ts_beatlength * (this.trueMeasureLength/this.ts_beats);
		
		this.slurStatus = new String[this.string_count];
		this.pSlur = new String[this.string_count];
		this.hSlur = new String[this.string_count];
		for(int i = 0; i<this.string_count; i++) {
			this.slurStatus[i] = null;
			this.pSlur[i] = null;
			this.hSlur[i] = null;
		}
	}
	
	public List<String[]> getNotes() {
		List<String[]> out = new ArrayList<String[]>();
		int[] shifts = getFretsExpanded(this.strColumn);
		for(int i = 0; i<shifts.length; i++) {
			if(shifts[i] >= 0) {
				String[] stepAndOctave = calculateNoteandOctave(i,shifts[i]);
				String alter = "0";
				String accidental = "";
				String grace = null;
				String slur = "0";
				String hslur = "0";
				String pslur = "0";
				
				
				
				if(stepAndOctave[0].length() > 1) {
					alter = "1";
					accidental = "sharp";
				}
				
				if(this.strColumn[i].charAt(0) == 'g') {
					grace = "";
				}
				
				if(this.slurStatus[i] != null) {
					slur = "1";
				}
				if(this.hSlur[i] != null) {
					hslur = "1";
				}
				if(this.pSlur[i] != null) {
					pslur = "1";
				}

				//System.out.println((int)Math.ceil(this.log2((double)(this.wNoteLength)/(this.noteLength))));
				
				String[] noteProperties = {
						""+this.noteLength,																	//raw duration
						this.getType(),				//type
						""+stepAndOctave[0].charAt(0),																	//step
						stepAndOctave[1],																				//octave
						alter,																				//alter
						accidental,																			//accidental
						""+(i+1),																					//string
						""+shifts[i],																			//fret
						hslur,
						hSlur[i],
						slur,
						slurStatus[i], //slur
						pslur,
						pSlur[i],
						grace, //grace note
						""  
				};
				out.add(noteProperties);
				
				
				this.stringArrayDump("noteProperties, values are (duration, type, step, octave, alter, accidental,string,fret,slur,TBD,TBD)", noteProperties);
				
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
			//------------------------------------------------------------------------------------
//			this.readColumn(this.curr_col);
//			this.curr_col++;
//			if(!this.isEmpty(this.column)) {
//				for(int i=0; i<this.string_count; i++) {
//					//append only non-dash characters
//					if(this.column[i] != '-') {
//						this.strColumn[i] = this.strColumn[i] + this.column[i];
//					}
//				}
//				this.noteLength = 1;
//			}else {
//				this.noteLength = 2;
//			}
			//read until there are no more empty columns - to get 2 or 3 length "notes"
			int counter = 0;
			this.readColumn(this.curr_col);
			while(!this.isEmpty(this.column) && this.hasNextColumn) {
				counter ++;
				this.curr_col++;
				for(int i=0; i<this.string_count; i++) {
					//append only non-dash characters
					if(this.column[i] != '-') {
						this.strColumn[i] = this.strColumn[i] + this.column[i];
					}
				}
				this.readColumn(this.curr_col);
				if(!this.isStrictEmpty(column)) {
					break;
				}
			}
			this.noteLength = counter + 1;
			//------------------------------------------------------------------------------------
			//continue reading empty columns to determine note length
			this.readColumn(this.curr_col);
			this.curr_col++;
			while(this.isStrictEmpty(this.column) && this.hasNextColumn) { //note to self: strictempty => not a number || empty => is a dash || empty is more "strict" logically, but less strict musically
				if(!this.isEmpty(this.column)) {
					
					if(this.columnHas(this.column, 'g')) {
						break;
					}
					
					for(int i=0; i<this.string_count; i++) {
						if(this.column[i] != '-') {
							this.strColumn[i] = this.strColumn[i] + this.column[i];
						}
					}
				}
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
	
	public int[] getTuning() {
		return this.octaves;
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
			int octave = this.octaves[string];
			for(int i=1; i<=fret; i++) {
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
	
	private String getType() {
		double rawlength = (double)(this.wNoteLength)/(this.noteLength);
		double index = Math.ceil(this.log2((double)(this.wNoteLength)/(this.noteLength)));
		double roundedlength = this.trueMeasureLength/Math.pow(2, index);
		System.out.println("DEBUG: roundedlength: " + roundedlength);
		
		//original rounded down
		String out = lengths[(int)index];
		if(this.noteLength == roundedlength * 4 / 3 && index > 0) {
			//triplet flag
			out = lengths[(int)index -1];
		}
		
		return out;
	}
	
	private int[] getFrets(String[] column) {
		int[] out = new int[string_count];
		for(int i=0; i<string_count; i++) {
			try {
				out[i] = Integer.parseInt(column[i]);
			}catch(NumberFormatException e) {
				out[i] = -1;
			}
		}
		return out;
		
	}
	
	private int[] getFretsExpanded(String[] column) {
		int[] out = new int[string_count];
		for(int i=0; i<string_count; i++) {
			Matcher nOMat = nOPat.matcher(column[i]);
			
			try {
				Boolean slur = false;
				Boolean pslur = false;
				Boolean hslur = false;
				if(nOMat.matches()) { //purely numbers
					out[i] = Integer.parseInt(column[i]);
				}else if(column[i].charAt(0) == 'g') { //replace with regex if possible
					//grace notes
					String temp = column[i].substring(1, column[i].length()-1);
					out[i] = Integer.parseInt(temp);
					slur = true;	
					
					
				}else if(column[i].charAt(column[i].length()-1) == 'p') { //replace with regex if possible
					//hammer on pull off
					String temp = column[i].substring(0, column[i].length()-1);
					out[i] = Integer.parseInt(temp);
					slur = true;
					pslur = true;

				}else if (column[i].charAt(column[i].length()-1) == 'h'){
					String temp = column[i].substring(0, column[i].length()-1);
					out[i] = Integer.parseInt(temp);
					slur = true;
					hslur = true;
					
				}else {
					out[i] = -1;
				}
				this.slurStatus = this.advanceConnectorStatus(this.slurStatus, i, slur);
				this.pSlur = this.advanceConnectorStatus(this.pSlur, i, pslur);
				this.hSlur = this.advanceConnectorStatus(this.hSlur, i, hslur);
				
			}catch(Exception e) {
				out[i] = -1;
			}
			
		}
		
		//this.stringArrayDump("slurStatus", slurStatus);
		return out;
		
	}
	
	private String[] advanceConnectorStatus1(String[] connectorArray, int string, boolean cont) {
		if(connectorArray[string].equals(null)) {
			if(cont) {
				connectorArray[string] = "start";
			}else {
				//remain as none
			}
		}else if(connectorArray[string].equals("start")) {
			if(cont) {
				connectorArray[string] = "continue";
			}else {
				connectorArray[string] = "stop";
			}
		}else if(connectorArray[string].equals("stop")) {
			if(cont) {
				connectorArray[string] = "start";
			}else {
				connectorArray[string] = "none";
			}
		}else { //slur is continue
			if(cont) {
				//remain as continue
			}else {
				connectorArray[string] = "stop";
			}
		}
		return connectorArray;
	}
	
	private String[] advanceConnectorStatus(String[] connectorArray, int string, boolean cont) {
		if(connectorArray[string] == null) {
			if(cont) {
				connectorArray[string] = "start";
			}else {
				//remain as none
			}
		}else if(connectorArray[string].equals("start")) {
			if(cont) {
				connectorArray[string] = "continue";
			}else {
				connectorArray[string] = "stop";
			}
		}else if(connectorArray[string].equals("stop")) {
			if(cont) {
				connectorArray[string] = "start";
			}else {
				connectorArray[string] = null;
			}
		}else { //slur is continue
			if(cont) {
				//remain as continue
			}else {
				connectorArray[string] = "stop";
			}
		}
		return connectorArray;
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
	
	private boolean isStrictEmpty(char[] column) {
		boolean out = true;
		for (char ch: column) {
			String character = "" + ch;
			Matcher nOMat = nOPat.matcher(character);
			if(nOMat.matches()) {
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
				this.column[i] = this.measure[i].charAt(in);
			}
		}
	}
	
	private char[] getColumn(int in) {
		char[] lcolumn = new char[string_count];
		for(int i=0; i<string_count; i++) {
			lcolumn[i] = this.measure[i].charAt(in);
		}
		return lcolumn;
	}
	
	private void inferOctaves(String[] tuning) { //calculate base octaves from tuning information		
		System.out.println("DEBUG: attempting to infer octaves from tuning information: ");
		this.stringArrayDump("tuning info", tuning);		
		int lowestOctave;
		if(tuning.length == 6) {
			lowestOctave = 2; //lowest note possible is C2?
		}else {
			lowestOctave = 1;
		}
		
		int[] baseOctaves = new int[this.string_count];
		int counter = 0;
		for(int i=this.string_count-1; i>=0; i--) {
			if(i < this.string_count-1) {
				baseOctaves[i] = baseOctaves[i+1];
			}else {
				baseOctaves[i] = lowestOctave;
			}
			
			while(!tuning[i].equalsIgnoreCase(scale[counter])) {
				counter ++;
				if(counter > 11) {
					baseOctaves[i] ++;
					counter = 0;
				}else if(counter > scale.length) {
					break;
				}
			}
			System.out.println("DEBUG: calculated base octave: " + baseOctaves[i] + " for string " + i + " " + tuning[i]);
		}		
		this.octaves = baseOctaves;
	}
	
	private int getTrueMeasureLength1() {
		int out = 1;
		int counter = 0;
		System.out.println(measure[3]);
		while(counter < this.character_count && isStrictEmpty(getColumn(counter))) {		
			System.out.print("0");
			counter ++;
		}
		counter++;
		while(counter < this.character_count) {	
			if(!isStrictEmpty(getColumn(counter)) && !isStrictEmpty(getColumn(counter-1))) {
				System.out.print("x");
			}else {
				System.out.print("-");
				out ++;
			}
			counter ++;
		}
		System.out.println("DEBUG: true measure length: " + out);
		return out;
	}
	
	private int getTrueMeasureLength() {
		int out = 1;
		int counter = 0;
		System.out.println(measure[3]);
		while(counter < this.character_count && isStrictEmpty(getColumn(counter))) {		
			System.out.print("0");
			counter ++;
		}
		counter++;
		while(counter < this.character_count) {	
			if(!isStrictEmpty(getColumn(counter)) && !isStrictEmpty(getColumn(counter-1))) {
				System.out.print("x");
			}else if(columnHas(getColumn(counter-1), 'g')){
				System.out.print("-");
				while(counter < this.character_count && !isEmpty(getColumn(counter))) {
					System.out.print("x");
					counter++;
				}
				out ++;
			}else {
				System.out.print("-");
				out ++;
			}
			counter ++;
		}
		System.out.println("DEBUG: true measure length: " + out);
		return out;
	}
	
	private boolean columnHas(char[] scrutinee, char compare) {
		boolean out = false;
		for(char ch : scrutinee) {
			if(ch == compare) {
				out = true;
				break;
			}
		}
		return out;
	}
	
	private int floor2pow2(int in) { //round down to power of 2, useful to determine "true measure length" that would be inflated by double digit frets
		return (int) Math.floor(Math.log(in)/Math.log(2));
	}
	
	private double log2 (double in) {
		double out = (Math.log(in) / Math.log(2));
		return out;
	}
	
	private void stringArrayDump(String arrayName, String[] in) {
		System.out.println("DEBUG: dumping contents of: " +arrayName);
		for(int i=0; i<in.length; i++) {
			System.out.println(in[i]);
		}
	}
}
