package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.Instrument;

import xmlClasses.ScoreInstrument;

import java.lang.Math;

/* This class parses Drum tablature into a list of notes, each with
 * their own specific information
 */
public class DrumReader {
	// each string represent a line of one measure
	private String[] measure;
	// this array represent one column of notes in a measure
	private char[] column;
	// represent the column that was most recently read
	private int curr_col;
	// represent the duration of each note in the beam currently being parsed
	private int beamDur;
	//number of notes in the beam currently being parsed
	private int beamLen;

	// OLD, used for row parsing method
	private char[] rows;
	private int row;

	// each string in the list represents a part of the drumKit
	private ArrayList<String> drumKit = new ArrayList<String>();

	// map correlate instrument acronym and Music-XML id
	private Map<String, String> instrumentIds = new HashMap<String, String>();

	// map to correlate instrument id and the max duration of that instrument
	private Map<String, Integer> instrumentMax = new HashMap<String, Integer>();

	// map to correlate an instrument id and its respective display step and octave
	private Map<String, String> instrumentStep = new HashMap<String, String>();

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
	 * Parses one column of notes of the current measure
	 * 
	 * returns a list of String arrays, each containing information for one drum
	 * note
	 */
	public List<String[]> readNotes() {
		readColumn();
		// List of String arrays, where each array holds the information for one drum
		// note
		ArrayList<String[]> notes = new ArrayList<String[]>();
		String instrument = "";
		String type = "";
		String step = "";
		int octave = -1;
		int duration = 1;
		int voice = 1;
		int col2 = 0;
		String beam = "";
		String notehead = "o";
		String stem = "";
		boolean beamFlag;
		boolean beamStarted = false;
		int max = 16;

		// goes through each index row in the current column
		for (int row = 0; row < this.column.length; row++) {

			// checks if there is a note at index row, in the column
			if (this.column[row] == 'o' || this.column[row] == 'x') {

				// gets max duration from hash-map
				try {
					// collects instrument from hash-map
					instrument = this.drumKit.get(row);
					max = instrumentMax.get(instrument);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				// block where the duration is found
				try {
					col2 = this.curr_col; // current column index + 1

					// finds the duration of this note by going through each column after note,
					// until either another note is found on the same row or the max duration is hit
					while (col2 < this.measure[row].length()) {
						if ((this.measure[row].charAt(col2) == '-') && (duration < max)) {
							duration++;
						} else {
							col2 = this.measure[row].length();
						}

						col2++;
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("DURATION BLOCK ERROR");
				}

				//checks the current beam could continue or end, otherwise a beam could begin or doesn't exist
				if (beamDur == duration) {
					//checks if there is room for beam to continue, current instrument isn't a bass drum, and there isn't a beam already started in the current column
					if ((this.curr_col + duration) < this.measure[row].length() && !instrument.equals("P1-I36") && !beamStarted) {
						
						//represents weather a beam should continue or end
						beamFlag = false;
						
						//goes through each row looking for potential beam
						for (int i = 0; i < this.measure.length; i++) {
							
							//stores note at row i, after the duration of the current note
							char note = this.measure[i].charAt((this.curr_col - 1) + duration);
							
							//checks if this note is suitable to beam with the current note being parsed
							//the note's max duration must equal the beam duration and not be a Bass Drum note
							if ((note == 'o' || note == 'x') && this.drumKit.get(i) != "BD" && instrumentMax.get(this.drumKit.get(i)) == beamDur) {
								beamFlag = true;
							}
						}

						//checks if the beam should continue or end
						if (beamFlag && beamLen < 3) {
							this.beamLen++;
							beam = "continue";
						} else {
							beam = "end";
							this.beamDur = 0;
						}

					} else {
						beam = "end";
						this.beamDur = 0;
					}

				} else {

					//checks if there is room for beam to begin, current instrument isn't a bass drum, and there isn't a beam already started in the current column
					if (((this.curr_col + duration) < this.measure[row].length()) && !instrument.equals("P1-I36") && !beamStarted) {
						beamFlag = false;
						
						//goes through each row looking for potential beam
						for (int i = 0; i < this.measure.length; i++) {
							//stores note at row i, after the duration of the current note
							char note = this.measure[i].charAt((this.curr_col - 1) + duration);
							
							//checks if this note is suitable to beam with the current note being parsed
							//the note's max duration must equal the current duration and not be a Bass Drum note
							if ((note == 'o' || note == 'x') && this.drumKit.get(i) != "BD" && instrumentMax.get(this.drumKit.get(i)) == duration) {
								beamFlag = true;
							}
						}

						//checks if a beam should start
						if (beamFlag) {
							
							beam = "begin";
							beamDur = duration;
							beamLen = 1;
							beamStarted = true;
						}
					}

				}

				//sets the type of note
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

				//changes the note-head accordingly
				if (this.column[row] == 'x') {
					notehead = "x";
				}

				//adjustment for Hi Hat
				if (instrument.equals("P1-I43") && !notehead.equals("x")) {
					// Open Hi Hat
					instrument = "P1-I47";
				}

				//changes un-pitched position of note using the instrument-step map
				step = instrumentStep.get(instrument).substring(0, 1);
				octave = Integer.parseInt(instrumentStep.get(instrument).substring(1));

				//makes stem down for bass notes
				if (instrument.equals("P1-I36")) {
					stem = "down";
					// used to make bass notes not included in other beams
					voice = 2;
				} else {
					stem = "up";
				}

				//stores all information for the current note
				String[] note = { step, 
						octave + "", 
						duration + "", 
						instrument, 
						voice + "", 
						type, 
						notehead, 
						beam, 
						stem};

				notes.add(note);

			}

			//note data that must be reset
			beam = "";
			duration = 1;
			notehead = "o";
		}
		return notes;
	}

	/*
	 * Adds all instruments IDs to the global drum kit list
	 */
	private void initializeDrumKit() {
		readColumn();
		String id = "x";

		// IDs for supported instruments
		instrumentIds.put("BD", "P1-I36"); // base drum
		instrumentIds.put("KD", "P1-I36"); // kick drum
		instrumentIds.put("SD", "P1-I39"); // snare drum
		instrumentIds.put("HH", "P1-I43"); // closed high hat, only when note-head is an X
		instrumentIds.put("HO", "P1-I47"); // open high hat
		instrumentIds.put("RC", "P1-I52"); // ride cymbal
		instrumentIds.put("CC", "P1-I50"); // crash cymbal
		instrumentIds.put("HT", "P1-I48");// Low-Mid Tom
		instrumentIds.put("MT", "P1-I46"); // low tom
		instrumentIds.put("FT", "P1-I42"); // Low Floor Tom

		// Max duration for supported instruments
		instrumentMax.put("P1-I36", 8); // base drum 1
		instrumentMax.put("P1-I39", 1); // snare drum;
		instrumentMax.put("P1-I43", 2); // closed high-hat
		instrumentMax.put("P1-I47", 2); // open high-hat
		instrumentMax.put("P1-152", 2); // ride cymbal
		instrumentMax.put("P1-I50", 2); // crash cymbal
		instrumentMax.put("P1-I48", 1); // Low-Mid Tom
		instrumentMax.put("P1-I46", 1); // low tom
		instrumentMax.put("P1-I42", 1); // low floor tom

		// Instrument display octave and step
		instrumentStep.put("P1-I36", "F4"); // base drum 1
		instrumentStep.put("P1-I39", "C5"); // snare drum;
		instrumentStep.put("P1-I43", "G5"); // closed high-hat
		instrumentStep.put("P1-I47", "G5"); // open high-hat
		instrumentStep.put("P1-I52", "F1"); // ride cymbal
		instrumentStep.put("P1-I50", "A5"); // crash cymbal
		instrumentStep.put("P1-I48", "E5"); // Low-Mid Tom
		instrumentStep.put("P1-I46", "D5"); // low tom
		instrumentStep.put("P1-I42", "A4"); // low floor tom

		String instrument = "";
		int col = this.curr_col - 1;// current column index in measure

		// go through initial columns of instrument acronym and collect instruments
		for (int line = 0; line < this.column.length; line++) {
			instrument = "";
			instrument = this.measure[line].charAt(col) + "" + this.measure[line].charAt(col + 1); // concatinates
																									// instrument
			System.out.println("instrument: " + instrument);
			col = this.curr_col - 1;
			// adds instrument into drum-kit
			System.out.println("DRUMKIT: " + instrumentIds.get(instrument));
			id = instrumentIds.get(instrument);
			this.drumKit.add(id);
		}

	}

	/* returns the list of drumKit parts */
	public ArrayList<String> getDrumKit() {
		return this.drumKit;
	}

	/*
	 * Takes one column of note-heads from the current measure into global column
	 * array
	 */
	protected void readColumn() {
		this.column = new char[this.measure.length];
		for (int i = 0; i < this.measure.length; i++) {
			column[i] = measure[i].charAt(curr_col);
		}
		this.curr_col++;

	}

	// Checks if there are anymore columns to be read in the current measure
	public boolean hasNext() {
		return this.curr_col < this.measure[0].length();

	}

	/*
	 * OLD, currently not used Reads one row of notes of the current measure
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

		// gets max duration from hash-map
		try {
			// collects instrument from hash-map
			instrument = this.drumKit.get(this.row - 1);
			max = instrumentMax.get(instrument);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		// finds the duration of each note
		for (int col = 0; col < this.rows.length; col++) {

			// checks if there is a note at index col in the row
			if (this.rows[col] == 'o' || this.rows[col] == 'x') {

				if (forward > 0) {
					// adds the forward before this note to fix the spacing
					String[] skip = { "forward", forward + "" };
					notes.add(skip);
					// resets the forward
					forward = 0;
				}

				// risky block where the duration is found
				try {
					col2 = col + 1;

					// finds the duration of this note by going through row until a note is found,
					// the end of rows is found or there is another note
					while (col2 < this.rows.length) {
						if ((this.rows[col2] == '-') && (duration < max)) {
							duration++;
						} else {
							col2 = this.rows.length;
						}
						col2++;

					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("DURATION ERROR");
				}

				// checks if a beam is beginning, continuing, ending, or doesn't exist
				if (beamDur == duration) {
					// there is room for beam to continue
					if ((col + duration) < this.rows.length) {

						beamFlag = false;
						// goes throgh measure looking for a potential beam
						for (int i = 0; i < this.measure.length; i++) {
							char note = this.measure[i].charAt(col + duration);
							if ((note == 'o' || note == 'x') && this.drumKit.get(i) != "BD") {
								beamFlag = true;
							}
						}

						// checks for existing continues
						// checks if the beam should continue or end
						if (beamFlag) {
							beam = "continue";
						} else {
							beam = "end";
							this.beamDur = 0;
						}

					} else {
						beam = "end";
						this.beamDur = 0;
					}

				} else {

					// checks if a new beam should be created
					if (((col + duration) < this.rows.length) && this.drumKit.get(this.row - 1) != "BD") {
						beamFlag = false;
						// goes through measure looking for potential beam
						for (int i = 0; i < this.measure.length; i++) {
							char note = this.measure[i].charAt(col + duration);
							if ((note == 'o' || note == 'x') && this.drumKit.get(i) != "BD") {
								beamFlag = true;
							}
						}

						if (beamFlag) {
							beam = "begin";
							beamDur = duration;
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

				// Store all information for this note
				String[] note = { step, octave + "", duration + "", instrument, voice + "", type, notehead, beam };

				notes.add(note);

			} else {
				forward++;
				System.out.println("Adding to forward, forward " + forward);

			}

			// skips index where this instrument is played
			beam = "";
			col += duration - 1;
			duration = 1;
			notehead = "o";
		}
		return notes;
	}

	/*
	 * OLD Takes one row of notes from the current measure into the global row array
	 * Currently Unused
	 */
	protected void readRow() {
		this.rows = new char[this.measure[0].length()];
		System.out.println("Creating Row");
		for (int i = 0; i < this.measure[0].length(); i++) {
			this.rows[i] = this.measure[this.row].charAt(i);
			System.out.println("ROW[" + i + "] :" + this.measure[this.row].charAt(i));
		}
		this.row++;

	}

	/*
	 * OLD Checks if the drum reader has anymore rows to read
	 */
	public boolean hasNextRow() {
		return this.row < this.measure.length;

	}

}
