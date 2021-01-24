package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
@XmlType(propOrder={"step", "octave"})
public class Pitch {
	private String step;
	private int octave;
	
	public Pitch() {}  
	
	public Pitch(String step, int octave) {  
	    super();  
	    this.step = step;  
	    this.octave = octave; 
	} 
	
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
	}
}
