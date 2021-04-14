package xmlClasses;

import xmlClasses.Attributes;
import xmlClasses.Clef;
import xmlClasses.Key;
import xmlClasses.Measure;
import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.Pitch;
import xmlClasses.Time;

public class PartWriter {
	
	private Part part;
	private Measure currentMeasure;
	private static int parts_created=0;
	private Technical tech;
	private Slur slur;
	private Notations notations;
	
	//Lab change
	//Constructor
	public PartWriter(){
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
	public void nextBarline(String location, String style, String direction ){
		currentMeasure.addBarline( new Barline(location, style, direction));
	}
	
	//Create a forward to go forward a certain duration in the time-line of notes
	public void nextDirection(Direction d){
		currentMeasure.addDirection(d);
	}
	
//	//Create Note with its Pitch object and adding it to the current measure.
//	public void nextNote(int duration, String type, String step, int octave, int string, int fret, int voice){
//		currentMeasure.addNote( new Note( duration, type, new Pitch( step, octave ), new Notations( new Technical(string, fret) ), voice) );
//		
//	}
//	
//	//Create Note with its Pitch object and adding it to the current measure.
//	public void nextChordNote(int duration, String type, String step, int octave, int string, int fret, int voice){
//		currentMeasure.addNote( new ChordNote( duration, type, new Pitch( step, octave ), new Notations( new Technical(string, fret) ), voice ) );
//	}
//	
//	//Create Note with its Pitch object and adding it to the current measure.
//	public void nextAlteredNote(int duration, String type, String step, int octave, int alter, int string, int fret, int voice){
//		currentMeasure.addNote( new Note( duration, type, new Pitch( step, octave, alter), new Notations( new Technical(string, fret) ), voice) );
//		
//	}
//	
//	//Create Note with its Pitch object and adding it to the current measure.
//	public void nextAlteredChordNote(int duration, String type, String step, int octave, int alter, int string, int fret, int voice){
//		currentMeasure.addNote( new ChordNote( duration, type, new Pitch( step, octave, alter), new Notations( new Technical(string, fret) ), voice ) );
//	}
	
	public void nextAllNote( int duration, String type, String step, int octave, int alter, int string, int fret, int hNum, String hType, 
							String hCharacter, int sNum, String sPlacement, String sType, int pNum, String pType, String pCharacter,
							int voice, String chord, String grace) {
		
		if(pType == null && hType != null && sType != null) {
			tech = new Technical(string, fret, new HammerOn(hNum, hType, hCharacter));
			notations = new Notations(tech, new Slur(sNum, sType, sPlacement));
		}
		else if(hType == null && pType != null && sType != null) {
			tech = new Technical(string, fret, new PullOff(pNum, pType, pCharacter));
			notations = new Notations(tech, new Slur(sNum, sType, sPlacement));
		}
		else {
			tech = new Technical(string, fret);
			notations = new Notations(tech);
		}
		
		currentMeasure.addNote( new Note( duration, type, new Pitch( step, octave, alter ), notations, voice, chord, grace) );
	}
	
	//Getter
	public Part getPart() {
		return part;
	}
	
}