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
		
	}
	
	/*
	 * NOT READY
	 * Testing different approaches to reading notes
	 */
	public String [] readNote() {
		String [] notes = new String[6];
		int col = this.curr_col;
	    int note = 16;
		for(int line = 0; line < column.length; line++) {
			if(this.column[line] == 'o' || this.column[line] == 'x') {
				//checks if this is a 16th 8th quarter or while note
				while(this.measure[line].charAt(col) == '-') {
					note = note / 2;
				
					col++;
					col++;
					col++;
					col++;
					col++;
					col++;
					if(this.measure[line].length() <= col) {
						break;
					}
					
				}
				
				notes[line] = "1/" + note  + " note on " + this.drumKit.get(line);
				
			}
			col = curr_col;
			note = 16;
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
			
			//go through all characters before measure line '|'
			while(this.measure[line].charAt(col) != '|') {
				instrument += this.measure[line].charAt(col);
				col++;
			}
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
