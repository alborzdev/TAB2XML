package test;

public class DrumReader {
	private String [] measure;
	private char [] column;
	private int curr_col;
	
	//Each line in drum tab represents a part of the drumKit
	private String [] drumKit = {"BaseDrum", "FloorTum", "SnareDrum", "MiddleTom", "HighTom", "HighHat", "Cymbol"};
	//related to duration of note 
	private int type;
	
	public DrumReader(String [] measure) {
		this.measure = measure;
		curr_col = 0;
	}
	
	/*
	 * NOT READY
	 * Testing different approaches to reading notes
	 */
	public void readNote() {
		for(int line = 0; line < column.length; line++) {
			if(this.column[line] == '0' || this.column[line] == 'X') {
				this.type = 1/16;
				
				//checks if this is a 16th 8th quarter or while note
				while(this.measure[line].charAt(this.curr_col) != '-') {
					type *= 2;
				}
				
				
				
			}
		}
	}
	
	/* Takes one vertical line of notes from this.measure into this.column
	 * Changed Derrui's guitar reader to work for drum tab
	 */
	private void readColumn() {
		this.column = new char[this.drumKit.length];
		for(int i=0; i<drumKit.length; i++) {
			column[i] = measure[i].charAt(curr_col);
		}
		curr_col ++;
	}
}
