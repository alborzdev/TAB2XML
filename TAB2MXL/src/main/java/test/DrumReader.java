package test;

import java.util.ArrayList;

public class DrumReader {
	private String [] measure;
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
		readColumn(); //reads first line of notes
	}
	
	public void setMeasure(String [] measure) {
		this.measure = measure;
		this.curr_col = 0;
	}
	
	/*
	 * Reads one line of notes of a measure
	 */
	public String [] readNote() {
		readColumn();
		String [] notes = new String[6];
		int col = this.curr_col;
	    String note ="";
	    double duration = 0.5;
	    
		for(int line = 0; line < column.length; line++) {
			if(this.column[line] == 'o' || this.column[line] == 'x') {
				//checks if this is a 16th 8th quarter or while note
				try {

				while(col < this.measure[line].length() && this.measure[line].charAt(col) == '-') {
					duration += 0.5;
					System.out.println("note: " + this.measure[line].charAt(col));
					col++;
				}
				duration *= 2;
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				if(duration == 1) {
					note = "sixteenth";
				}else if(duration == 2) {
					note = "eigth";
				}else if(duration == 4) {
					note = "quarter";
				}else if (duration == 8) {
					note = "half";
				}
				
				notes[line] = "duration: " + duration + "\nnote: " + note + "\ndrumkit: " + this.drumKit.get(line);
				
			}
			col = curr_col;
			note = "";
			duration = 0.5;
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
			System.out.println("instrument: " + instrument);
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
			System.out.println("col" + column[i]);
		}
		curr_col ++;
		
	}
}
