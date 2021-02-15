package xmlClasses;

import java.util.ArrayList;

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
	
	//Lab change
	//Constructor
	PartWriter(){
		part = new Part("P"+(parts_created+1));
	}
	
	//Empty measure
	public void nextMeasure(){
		currentMeasure=new Measure(part.getMeasure().size()+1);
		part.addMeasure(currentMeasure);
	}
	
	//Empty measure with attributes
	public void nextMeasure(int divisions, int fifths, int beats, int beat_type, String sign, int line){
		currentMeasure=new Measure(
				part.getMeasure().size()+1,
				new Attributes(divisions,
						new Key(fifths),
						new Time(beats, beat_type),
						new Clef(sign, line)
				)
		);
		part.addMeasure(currentMeasure);
	}
	
	//Empty measure with attributes + Staff Details
		public void nextMeasure(int divisions, int fifths, int beats, int beat_type, String sign, int line, int stafflines, ArrayList<StaffTuning> stafftunings){
			currentMeasure=new Measure(
					part.getMeasure().size()+1,
					new Attributes(divisions,
							new Key(fifths),
							new Time(beats, beat_type),
							new Clef(sign, line),
							new StaffDetails(stafflines, stafftunings)
					)
			);
			part.addMeasure(currentMeasure);
		}
	
	//Create Note with its Pitch object and adding it to the current measure.
	public void nextNote(int duration, String type, String step, int octave){
		currentMeasure.addNote( new Note( duration, type, new Pitch( step, octave ) ) );
		
	}
	
	//Create Note with its Pitch object and adding it to the current measure.
		public void nextChordNote(int duration, String type, String step, int octave){
			currentMeasure.addNote( new ChordNote( duration, type, new Pitch( step, octave ) ) );
			
		}
	
	//Getter
	public Part getPart() {
		return part;
	}
	
}