package xmlClasses;

public class PartWriter {
	
	private Part part;
	private Measure currentMeasure;
	
	PartWriter(){
		part = new Part();
		
	}
	
	public void nextMeasure(){
		currentMeasure=new Measure();
		part.addMeasure(currentMeasure);
	}
	
	public void nextNote(int duration, String type, String step, int octave){
		currentMeasure.addNote( new Note( duration, type, new Pitch( step, octave ) ) );
		
	}
	
}
