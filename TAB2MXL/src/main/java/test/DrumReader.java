package test;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Instrument;

import java.lang.Math;

public class DrumReader {
	public String [] measure;
	private char [] column;
	private int curr_col;
	
	//Each line in drum tab represents a part of the drumKit
	private ArrayList<String> drumKit = new ArrayList<String>() ;
	
	//related to duration of note 
	private int type;
	
	public DrumReader(String [] measure) {
		this.measure = measure;
		curr_col = 0;
		initializeDrumKit();
	}
	
	public void setMeasure(String [] measure) {
		this.measure = measure;
		this.curr_col = 0;
	}
	
	/*
	 * Reads one line of notes of a measure
	 * 
	 * returns a list of String arrays containing information for one drum note
	 */
	public List<String []> readNote() {
		readColumn();
	    ArrayList<String []> notes = new ArrayList<String[]>();
		int col = this.curr_col;
	    String type = "";
	    String step = "";
	    int octave = -1;
	    double duration = 0.5;
	    String instrument = "";
	    int voice = 1;
	    String notehead = "o";
	    System.out.println("before loop");
	    System.out.println("coulumn length: " + column.length);
		for(int line = 0; line < column.length; line++) {
			System.out.println(this.column[line]);
			if(this.column[line] == 'o' || this.column[line] == 'x') {
				//checks if this is a 16th 8th quarter or while note
				System.out.println("Note Found at Line: " + line);
				try {

				while(col < this.measure[line].length() && this.measure[line].charAt(col) == '-') {
					duration += 0.5;
//					System.out.println("note: " + this.measure[line].charAt(col));
					col++;
				}
				duration *= 2;
				}catch(Exception e) {
					System.out.println(e.getMessage());
					System.out.println("DURATION ERROR");
				}
				System.out.println("Duration: " + duration);
				//sets the duration of note
				if(duration == 0.5) {
					type = "half sixteenth";
				}else if(duration == 1) {
					type = "sixteenth";
				}else if(duration == 2) {
					type = "eigth";
				}else if(duration == 4) {
					type = "quarter";
				}else if (duration == 8) {
					type = "half";
				}else if (duration == 16) {
					type = "whole";
				}
				
				//sets the instrument of this note
				instrument = this.drumKit.get(line);
				
				//changes unpitched position of note
				switch(line) {
				case 0: step = "A"; octave = 5;
				break;
				
				case 1: step = "F"; octave = 5;
				break;
				
				case 2: step = "D"; octave = 5;
				break;
				
				case 3: step = "B"; octave = 4;
				break;
				
				case 4: step = "G"; octave = 4;
				break;
				
				default: step = "E"; octave = 4;
				break;
				}
				
				//changes the notehead of HH and CC notes to xs
				if(instrument.toLowerCase().equals("cc") || instrument.toLowerCase().equals("hh") || instrument.toLowerCase().equals("sn") ) {
					notehead = "x";
				}
				
				
				String [] note = {step,	//step of note
								  octave + "",	//octave of note
								  (int)Math.floor(duration) + "",	//duration
								  instrument,	//instrument
								  voice + "",	//voice
								  type,
								  notehead};
								  
				notes.add(note);
				
			}
			col = curr_col;
			type = "";
			duration = 0.5;
			notehead = "o";
		}
		return notes;
	}
	
	/*
	 * Add all instruments to drum kit
	 */
	private void initializeDrumKit() {
		readColumn();
		String instrument = "";
		int col = this.curr_col -1;//prev col
		
		//go through initial line of instruments
		for(int line = 0; line < this.column.length; line++) {
			instrument = "";
			instrument = this.measure[line].charAt(col) + "" + this.measure[line].charAt(col+1); //concatinates instrument
//			System.out.println("instrument: " + instrument);
			col = this.curr_col-1;
			//adds instrument into drumkit
			this.drumKit.add(instrument);
		}
		
	}
	
	public ArrayList<String> getDrumKit() {
		return this.drumKit;
	}
	
	/* Takes one vertical line of notes from this.measure into this.column
	 * Changed Derrui's guitar reader to work for drum tab
	 */
	protected void readColumn() {
		this.column = new char[this.measure.length];
		for(int i=0; i<this.measure.length; i++) {
			column[i] = measure[i].charAt(curr_col);
//			System.out.println("col" + column[i]);
		}
		this.curr_col ++;
		System.out.println("CURRENT COL: " + this.curr_col);
		
	}
	
	public boolean hasNext() {
		System.out.println("Testing Next: cur col: " + this.curr_col + ",  measure length: " + this.measure[0].length() );
		if(this.curr_col >= this.measure[0].length()) {
			return false;
		}else {
			return true;
		}
	}
}
