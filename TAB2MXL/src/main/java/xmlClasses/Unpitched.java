package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class Unpitched{
	private String step;
	private int octave;
	
	public Unpitched() {

	}
	
	public Unpitched(String step, int octave) {
		this.step = step;
		this.octave = octave;
	}
	
	@XmlElement(name="display-step")
	public String getStep() {  
	    return step;  
	}  
	public void setStep(String step) {  
	    this.step = step;  
	}
	
	@XmlElement(name="display-octave")
	public int getOctave() {  
	    return octave;  
	}  
	public void setOctave(int octave) {  
	    this.octave = octave;  
	}
}
