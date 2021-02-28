package xmlClasses;

import java.util.ArrayList;

public class DrumPartWriter {
	private Part part;
	private Measure currentMeasure;
	private static int parts_created=0;
	
	DrumPartWriter(){
		part = new Part("P"+(parts_created+1));
	}
	
	//Empty measure
	public void nextMeasure(){
		currentMeasure=new Measure(part.getMeasure().size()+1);
		part.addMeasure(currentMeasure);
	}
	//First measure/ Attribute measure
		public void nextMeasure(Attributes att){
			currentMeasure=new Measure(part.getMeasure().size()+1, att);
			part.addMeasure(currentMeasure);
		}
	
	//Empty measure with attributes
	public void nextMeasure(int divisions, int fifths, int beats, int beat_type, String sign, int line){
		currentMeasure=new Measure(part.getMeasure().size()+1, new Attributes(divisions, new Key(fifths), new Time(beats, beat_type), new Clef(sign, line) ) );
		part.addMeasure(currentMeasure);
	}

	//Create Note with its Pitch object and adding it to the current measure.
	public void nextDrumNote(int duration, String type, String step, int octave, int voice, String instID, String stem ){
		currentMeasure.addDrumNote( new DrumNote( duration, type, new Unpitched( step, octave ), new Instrument(instID), voice, stem));
		
	}
	
	public void nextDrumNoteB(int duration, String type, String step, int octave, int voice, String instID, String stem, ArrayList<Beam> beam ){
		currentMeasure.addDrumNote( new DrumNoteB( duration, type, new Unpitched( step, octave ), new Instrument(instID), voice, stem, beam));
		
	}
	public void nextDrumNoteBNH(int duration, String type, String step, int octave, int voice, String instID, String stem, ArrayList<Beam> beam, String notehead){
		currentMeasure.addDrumNote( new DrumNoteBNH( duration, type, new Unpitched( step, octave ), new Instrument(instID), voice, stem, beam, notehead));
		
	}
	public void nextDrumNoteNH(int duration, String type, String step, int octave, int voice, String instID, String stem, String notehead){
		currentMeasure.addDrumNote( new DrumNoteNH( duration, type, new Unpitched( step, octave ), new Instrument(instID), voice, stem, notehead));
		
	}
	public void nextDrumNoteChord(int duration, String type, String step, int octave, int voice, String instID, String stem, String notehead){
		currentMeasure.addDrumNote( new DrumNoteChord( duration, type, new Unpitched( step, octave ), new Instrument(instID), voice, stem, notehead));
		
	}
	
	
	//Getter
	public Part getDrumPart() {
		return part;
	}
}
