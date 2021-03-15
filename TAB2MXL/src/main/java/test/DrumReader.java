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

	// Each line in drum tab represents a part of the drumKit
	private ArrayList<String> drumKit = new ArrayList<String>();
	private Map<String, String> instrumentIds = new HashMap<String, String>();
	private Map<String, Integer> instrumentMax = new HashMap<String, Integer>();

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
		int beamDur = 0;
		int gap = 0;
		String beam = "";
		String notehead = "o";
		
		int max = 16;
		
		//gets max duration from hashmap
		try {
			//collects instrument from hashmap
			instrument = this.drumKit.get(this.row-1);
			max = instrumentMax.get(instrument);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		// changes unpitched position of note
		switch (this.row - 1) {
		case 0:
			step = "A";
			octave = 5;
			break;

		case 1:
			step = "F";
			octave = 5;
			break;

		case 2:
			step = "D";
			octave = 5;
			break;

		case 3:
			step = "B";
			octave = 4;
			break;

		case 4:
			step = "G";
			octave = 4;
			break;

		default:
			step = "E";
			octave = 4;
			break;
		}
		System.out.println("FINISHED STEP OCTAVE, INSTRUMENT, and MAX");
		
		//finds the duration of each note
		for (int col = 0;  col < this.rows.length; col++) {
			
			//checks if there is a note at index col in the row
			if (this.rows[col] == 'o' || this.rows[col] == 'x') {
				try {
					col2 = col + 1;
					
					//finds the duration of this note
					while (col2 < this.rows.length) {
						if((this.rows[col2] == '-') && (duration < max)){
							duration++;
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
					//checks if a new beam should be created
					if((col + duration) < this.rows.length) {
						if(this.rows[col + duration] == 'o' || this.rows[col + duration] == 'x') {
							beam = "continue";
						}
						
					}else{
						beam = "end";
					}
						
				}else {
					
					//checks if a new beam should be created
					if((col + duration) < this.rows.length) {
					if(this.rows[col + duration] == 'o' || this.rows[col + duration] == 'x') {
						
						beam = "begin";
						beamDur = duration;
					}
					}
					
				}
				
				// sets the duration of note
				if (duration == 1) {
					type = "sixteenth";
				} else if (duration == 2) {
					type = "eigth";
				} else if (duration == 4) {
					type = "quarter";
				} else if (duration == 8) {
					type = "half";
				} else if (duration == 16) {
					type = "whole";
				}
								
				// changes the notehead of HH and CC notes to xs
				if (instrument.equals("P1-I50") || instrument.equals("P1-I47")) {
					notehead = "x";
				}

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
				String[] note = { "gap"};
				notes.add(note);			
			}
			
			//skips index where this instrument is played
			col += duration;
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
		instrumentIds.put("BD", "P1-I36");
		instrumentIds.put("P1-I37", "Bass Drum 2");
		instrumentIds.put("P1-I38", "Side Stick");
		instrumentIds.put("SD", "P1-I39");
		instrumentIds.put("P1-I42", "Low Floor Tom");
		instrumentIds.put("P1-I43", "Closed Hi-Hat");
		instrumentIds.put("P1-I44", "High Floor Tom");
		instrumentIds.put("P1-I45", "Pedal Hi-Hat");
		instrumentIds.put("P1-I46", "Low Tom");
		instrumentIds.put("HH", "P1-I47");
		instrumentIds.put("P1-I48", "Low-Mid Tom");
		instrumentIds.put("MT", "P1-I49");
		instrumentIds.put("CC", "P1-I50");
		instrumentIds.put("HT", "P1-I51");
		instrumentIds.put("P1-I52", "Ride Cymbal 1");
		instrumentIds.put("P1-I53", "Chinese Cymbal");
		instrumentIds.put("P1-I54", "Ride Bell");
		instrumentIds.put("P1-I55", "Tambourine");
		instrumentIds.put("P1-I56", "Splash Cymbal");
		instrumentIds.put("P1-I57", "Cowbell");
		instrumentIds.put("P1-I58", "Crash Cymbal 2");
		instrumentIds.put("P1-I60", "Ride Cymbal 2");
		instrumentIds.put("P1-I64", "Open Hi Conga");
		instrumentIds.put("P1-I65", "Low Conga");

		// maxes
		instrumentMax.put("P1-I36", 8);
		instrumentMax.put("P1-I39", 2);
		instrumentMax.put("P1-I47", 2);
		instrumentMax.put("P1-I49",2);
		instrumentMax.put("P1-I50", 8);
		instrumentMax.put("P1-I51", 2);
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
}
