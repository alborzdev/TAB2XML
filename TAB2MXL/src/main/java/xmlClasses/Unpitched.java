package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class Unpitched extends Pitch {
	
	public Unpitched() {
		
	}
	
	public Unpitched(String step, int octave) {
		super(step, octave);	
	}
	/*
	@XmlElement
	public String getStep() {  
	    return step;  
	}  
	public void setStep(String step) {  
	    this.step = step;  
	}
	
	@XmlElement
	public int getOctave() {  
	    return octave;  
	}  
	public void setOctave(int octave) {  
	    this.octave = octave;  
	*/ //DONT NEED THIS BECAUSE OF EXTENDS?
	
}
