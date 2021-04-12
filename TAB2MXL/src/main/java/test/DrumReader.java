package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Instrument;

import xmlClasses.ScoreInstrument;

import java.lang.Math;

public class DrumReader {
	private String[] measure;
	private char[] column;
	private char[] rows;
	private int row;
	private int curr_col;
	private int curr_line;
	private int beamDur;
	private int beamLen;

	// Each line in drum tab represents a part of the drumKit
	private ArrayList<String> drumKit = new ArrayList<String>();
	
	//map to hold an instrument acronym as the key and the xml id as the value
	private Map<String, String> instrumentIds = new HashMap<String, String>();
	
	//map to hold an instrument id as the key and max duration as the value
	private Map<String, Integer> instrumentMax = new HashMap<String, Integer>();
	
	//map to hold an instrument id as a key and instrument display octave/step as its value
	private Map<String, String> instrumentStep = new HashMap<String, String>();

	// related to duration of note
	private int type;

	public DrumReader(String[] measure) {
		this.measure = measure;
		this.curr_col = 0;
		this.row = 0;
		initializeDrumKit();
	}

	public void setMeasure(String[] measure) {
		this.measure = measure;
		this.curr_col = 0;
		this.row = 0;
		this.beamDur = 0;
		this.beamLen = 0;
	}

	/*
	 * Reads one line of notes of a measure
	 * 
	 * returns a list of String arrays containing information for one drum note
	 */
	public List<String[]> readNoteRow() {
		readRow();
		
		ArrayList<String[]> notes = new ArrayList<String[]>();
		String instrument = "";
		String type = "";
		String step = ""; 
		int octave = -1;
		int duration = 1;
		int voice = 1;
		int col2 = 0;
		int forward = 0;
		String beam = "";
		String notehead = "o";
		boolean beamFlag;
		
		int max = 16;
		
		//gets max duration from hashmap
		try {
			//collects instrument from hashmap
			instrument = this.drumKit.get(this.row-1);
			System.out.println("instrument: " + instrument + ",   this.getDrumKit: " + this.drumKit.get(this.row-1) + ", this.row-1 : " + (this.row-1));
			max = instrumentMax.get(instrument);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}		

		System.out.println("FINISHED STEP OCTAVE, INSTRUMENT, and MAX");
		
		//finds the duration of each note
		for (int col = 0;  col < this.rows.length; col++) {
			System.out.println("Parsing col " + col);
			
			//checks if there is a note at index col in the row
			if (this.rows[col] == 'o' || this.rows[col] == 'x') {
				System.out.println("Parsing a note");
				
				if(forward > 0) {
					//adds the forward before this note to fix the spacing
					String [] skip = {"forward", forward + ""};
					notes.add(skip);
					//resets the forward
					forward = 0;
				}

				
				//risky block where the duration is found
				try {
					col2 = col + 1;
					
					//finds the duration of this note by going through row until a note is found, the end of rows is found or there is another note
					while (col2 < this.rows.length) {
						if((this.rows[col2] == '-') && (duration < max)){
							duration++;
						}else {
							col2 = this.rows.length;
						}
						col2++;
						
					}
					System.out.println("Duration :" + duration);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("DURATION ERROR");
				}
				
				//checks if a beam is begining, continuing, ending, or doesn't exist
				if(beamDur == duration) {
					//there is room for beam to continue
					if((col + duration) < this.rows.length) {
						
						beamFlag = false;
						//goes throguh measure looking for potential beam
						for(int i = 0; i < this.measure.length; i ++) {
							char note = this.measure[i].charAt(col + duration);
							if((note == 'o' || note =='x') && this.drumKit.get(i) != "BD") {
								beamFlag = true;
							}
						}
						
						//checks for existing continues
						//checks if the beam should continue or end
						if(beamFlag) {
							beam = "continue";
						}else {
							beam = "end";
							this.beamDur = 0;
						}
						
					}else{
						beam = "end";
						this.beamDur = 0;
					}
						
				}else {
					
					//checks if a new beam should be created
					if(((col + duration) < this.rows.length) && this.drumKit.get(this.row-1) != "BD") {
						beamFlag = false;
						//goes through measure looking for potential beam
						for(int i = 0; i < this.measure.length; i ++) {
							char note = this.measure[i].charAt(col + duration);
							if((note == 'o' || note =='x') && this.drumKit.get(i) != "BD") {
								beamFlag = true;
							}
						}
						
						if(beamFlag) {
							beam = "begin";
							beamDur = duration;
							System.out.println("BEAM STARTED AT COL " + col);
						}
					}
					
				}
				
				// sets the type of note
				if (duration == 1) {
					type = "16th";
				} else if (duration == 2) {
					type = "eighth";
				} else if (duration == 4) {
					type = "quarter";
				} else if (duration == 8) {
					type = "half";
				} else if (duration == 16) {
					type = "whole";
				}
								
				// changes the notehead accordingly
				if (this.rows[col] == 'x') {
					notehead = "x";
				}
				
				// changes un-pitched position of note using the instrument-step map
				step = instrumentStep.get(instrument).substring(0, 1);
				octave = Integer.parseInt(instrumentStep.get(instrument).substring(1));
				
				//Store all information for this note
				String[] note = { step, // step of note
						octave + "", // octave of note
						duration + "", // duration
						instrument, // instrument
						voice + "", // voice
						type, 
						notehead,
						beam	};

				notes.add(note);
				
			}else {
				forward++;
				System.out.println("Adding to forward, forward " + forward);
				
			}
			
			//skips index where this instrument is played
			beam = "";
			col += duration -1;
			duration = 1;
			notehead = "o";
		}
		return notes;
	}
	
	/*
	 * Reads one line of notes of a measure
	 * 
	 * returns a list of String arrays containing information for one drum note
	 */
	public List<String[]> readNotes() {
		readColumn();
		ArrayList<String[]> notes = new ArrayList<String[]>();
		String instrument = "";
		String type = "";
		String step = ""; 
		int octave = -1;
		int duration = 1;
		int voice = 1;
		int col2 = 0;
		int forward = 0;
		String beam = "";
		String notehead = "o";
		boolean beamFlag;
		boolean beamStarted = false;
		
		int max = 16;	

		System.out.println("FINISHED STEP OCTAVE, INSTRUMENT, and MAX");
		
		//finds the duration of each note
		for (int row = 0;  row < this.column.length; row++) {
			System.out.println("Parsing row " + row);
			
			//checks if there is a note at index col in the row
			if (this.column[row] == 'o' || this.column[row] == 'x') {
				System.out.println("Parsing a note");

				//gets max duration from hashmap
				try {
					//collects instrument from hashmap
					instrument = this.drumKit.get(row);
					System.out.println("instrument: " + instrument + ",   this.getDrumKit: " + this.drumKit.get(row) + ", row: " + row);
					max = instrumentMax.get(instrument);
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				
				
				//risky block where the duration is found
				try {
					col2 = this.curr_col; //current col index + 1
					
					//finds the duration of this note by going through row until a note is found, the end of rows is found or there is another note
					while (col2 < this.measure[row].length()) {
						if((this.measure[row].charAt(col2) == '-') && (duration < max)){
							duration++;
						}else {
							col2 = this.measure[row].length();
						}
						col2++;
						
					}
					System.out.println("Duration :" + duration);
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("DURATION ERROR");
				}	
				
				//checks if a beam is begining, continuing, ending, or doesn't exist
				if(beamDur == duration) {
					//there is room for beam to continue
					if((this.curr_col + duration) < this.measure[row].length() && !this.drumKit.get(row).equals("BD") && !beamStarted) {
						
						beamFlag = false;
						//goes through row looking for potential beam
						for(int i = 0; i < this.measure.length; i ++) {
							char note = this.measure[i].charAt((this.curr_col-1) + duration);
							if((note == 'o' || note =='x') && this.drumKit.get(i) != "BD") {
								beamFlag = true;
							}
						}
						
						//checks for existing continues
						//checks if the beam should continue or end
						if(beamFlag) {
							this.beamLen ++;
							beam = "continue";
						}else {
							beam = "end";
							this.beamDur = 0;
						}
						
					}else{
						beam = "end";
						this.beamDur = 0;
					}
						
				}else {
					
					//checks if a new beam should be created
					if(((this.curr_col + duration) < this.measure[row].length()) && !this.drumKit.get(row).equals("BD") && !beamStarted) {
						beamFlag = false;
						//goes through measure looking for potential beam
						for(int i = 0; i < this.measure.length; i ++) {
							char note = this.measure[i].charAt((this.curr_col-1) + duration);
							if((note == 'o' || note =='x') && this.drumKit.get(i) != "BD") {
								beamFlag = true;
							}
						}
						
						if(beamFlag) {
							beam = "begin";
							beamDur = duration;
							beamStarted = true;
							System.out.println("BEAM STARTED AT COL " + (this.curr_col - 1));
						}
					}
					
				}
				
				// sets the type of note
				if (duration == 1) {
					type = "16th";
				} else if (duration == 2) {
					type = "eighth";
				} else if (duration == 4) {
					type = "quarter";
				} else if (duration == 8) {
					type = "half";
				} else if (duration == 16) {
					type = "whole";
				}
								
				// changes the notehead accordingly
				if (this.column[row] == 'x') {
					notehead = "x";
				}
				
				// changes un-pitched position of note using the instrument-step map
				step = instrumentStep.get(instrument).substring(0, 1);
				octave = Integer.parseInt(instrumentStep.get(instrument).substring(1));
				
				//Store all information for this note
				String[] note = { step, // step of note
						octave + "", // octave of note
						duration + "", // duration
						instrument, // instrument
						voice + "", // voice
						type, 
						notehead,
						beam	};

				notes.add(note);
				
			}//else {
//				forward++;
//				System.out.println("Adding to forward, forward " + forward);
//				
//			}
			
			//skips index where this instrument is played
			beam = "";
			duration = 1;
			duration = 1;
			notehead = "o";
		}
		return notes;
	}

	/*
	 * Add all instruments to drum kit
	 */
	private void initializeDrumKit() {
		readColumn();
		String id = "x";
		
		//IDs for supported instruments
		instrumentIds.put("BD", "P1-I36"); //base drum
		instrumentIds.put("KD", "P1-I36"); //kick drum
		instrumentIds.put("SD", "P1-I39"); //snare drum
		instrumentIds.put("HH", "P1-I43"); //closed high hat, only when note-head is an X
		instrumentIds.put("HHO", "P1-I47"); //open high hat 
		instrumentIds.put("RC", "P1-I52"); //ride cymbal
		instrumentIds.put("CC", "P1-I50"); //crash cymbal
		instrumentIds.put("HT", "P1-I48");// Low-Mid Tom
		instrumentIds.put("MT", "P1-I46"); //low tom
		instrumentIds.put("FT", "P1-I42"); //Low Floor Tom

		//Max duration for supported instruments
		instrumentMax.put("P1-I36", 8); //base drum 1
		instrumentMax.put("P1-I39", 2); //snare drum;
		instrumentMax.put("P1-I43", 2); // closed high-hat
		instrumentMax.put("P1-I47", 2); //open high-hat
		instrumentMax.put("P1-152", 2); //ride cymbal
		instrumentMax.put("P1-I50", 2); //crash cymbal
		instrumentMax.put("P1-I48",2); //Low-Mid Tom
		instrumentMax.put("P1-I46", 2); //low tom
		instrumentMax.put("P1-I42", 2); //low floor tom
		
		//Instrument display octave and step
		instrumentStep.put("P1-I36", "F4"); //base drum 1
		instrumentStep.put("P1-I39", "C5"); //snare drum;
		instrumentStep.put("P1-I43", "G5"); // closed high-hat
		instrumentStep.put("P1-I47", "G5"); //open high-hat
		instrumentStep.put("P1-I52", "F1"); //ride cymbal
		instrumentStep.put("P1-I50", "A5"); //crash cymbal
		instrumentStep.put("P1-I48", "E5"); //Low-Mid Tom
		instrumentStep.put("P1-I46", "D5"); //low tom
		instrumentStep.put("P1-I42", "A4"); //low floor tom

		System.out.println("ADEEDES ALL MAPS");
		String instrument = "";
		int col = this.curr_col - 1;// prev col

		// go through initial line of instruments
		for (int line = 0; line < this.column.length; line++) {
			instrument = "";
			instrument = this.measure[line].charAt(col) + "" + this.measure[line].charAt(col + 1); // concatinates
																									// instrument
			System.out.println("instrument: " + instrument);
			col = this.curr_col - 1;
			// adds instrument into drumkit
			System.out.println("DRUMKIT: " + instrumentIds.get(instrument));
			id = instrumentIds.get(instrument);
			this.drumKit.add(id);
		}

	}

	public ArrayList<String> getDrumKit() {
		return this.drumKit;
	}

	/*
	 * Takes one vertical line of notes from this.measure into this.column Changed
	 * Derrui's guitar reader to work for drum tab
	 */
	protected void readColumn() {
		this.column = new char[this.measure.length];
		for (int i = 0; i < this.measure.length; i++) {
			column[i] = measure[i].charAt(curr_col);
//			System.out.println("col" + column[i]);
		}
		this.curr_col++;

	}
	
	/*
	 * Takes one horizontal line of notes from this.measure into this.row
	 */
	protected void readRow() {
		this.rows = new char[this.measure[0].length()];
		System.out.println("Creating Row");
		for (int i = 0; i < this.measure[0].length() ; i++) {
			this.rows[i] = this.measure[this.row].charAt(i);
			System.out.println("ROW[" +i+"] :" + this.measure[this.row].charAt(i));
		}
		this.row++;

	}

	/* Checks if the drum reader has anymore rows to read */
	public boolean hasNextRow() {
		return this.row < this.measure.length;
		
	}
	
	public boolean hasNext() {
		return this.curr_col < this.measure[0].length();
		
	}
}
