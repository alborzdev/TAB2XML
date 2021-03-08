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
		initializeDrumKit();
	}

	public void setMeasure(String[] measure) {
		this.measure = measure;
		this.curr_col = 0;
	}

	/*
	 * Reads one line of notes of a measure
	 * 
	 * returns a list of String arrays containing information for one drum note
	 */
	public List<String[]> readNote() {
		readColumn();
		ArrayList<String[]> notes = new ArrayList<String[]>();
		int col = this.curr_col;
		String type = "";
		String step = "";
		int octave = -1;
		int duration = 1;
		String instrument = "";
		int voice = 1;
		String notehead = "o";
		boolean stop = false;
		int max = 16;
		for (int line = 0; line < column.length; line++) {
			if (this.column[line] == 'o' || this.column[line] == 'x') {
				// checks if this is a 16th 8th quarter or while note
				try {
					// sets the instrument of this note
					instrument = this.drumKit.get(line);
					max = instrumentMax.get(instrument);
					while (col < this.measure[line].length() && this.measure[line].charAt(col) == '-' && duration < max && !stop) {
						for(int i = 0; i  < column.length; i++) {
							if (this.measure[i].charAt(col) == 'o' || this.measure[i].charAt(col) == 'x') {
								stop = true;
							}
						}
						
						if(!stop) {
						duration++;
						col++;
						}
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("DURATION ERROR");
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

				// changes unpitched position of note
				switch (line) {
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

				// changes the notehead of HH and CC notes to xs
				if (instrument.equals("P1-I50") || instrument.equals("P1-I47")
						|| instrument.toLowerCase().equals("sn")) {
					notehead = "x";
				}

				String[] note = { step, // step of note
						octave + "", // octave of note
						duration + "", // duration
						instrument, // instrument
						voice + "", // voice
						type, notehead };

				notes.add(note);
				
			}
			col = curr_col;
			type = "";
			duration = 1;
			notehead = "o";
			max = 16;
			stop = false;
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
		instrumentMax.put("P1-I36", 16);
		instrumentMax.put("P1-I39", 2);
		instrumentMax.put("P1-I47", 2);
		instrumentMax.put("P1-I49",2);
		instrumentMax.put("P1-I50", 16);
		instrumentMax.put("P1-I51", 2);
		String instrument = "";
		int col = this.curr_col - 1;// prev col

		// go through initial line of instruments
		for (int line = 0; line < this.column.length; line++) {
			instrument = "";
			instrument = this.measure[line].charAt(col) + "" + this.measure[line].charAt(col + 1); // concatinates
																									// instrument
//			System.out.println("instrument: " + instrument);
			col = this.curr_col - 1;
			// adds instrument into drumkit
			System.out.println(instrumentIds.get(instrument));
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

	public boolean hasNext() {
		if (this.curr_col >= this.measure[0].length()) {
			return false;
		} else {
			return true;
		}
	}
}
