package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
@XmlType(propOrder={"step", "octave"})
@XmlSeeAlso({AlteredPitch.class})
public class Pitch {
	private String step;
	private int octave;
	private int alter;
	
	public Pitch() {}  
	
	public Pitch(String step, int octave, int alter) {  
	    this.step = step;  
	    this.octave = octave; 
	    this.alter = alter;
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
	
	@XmlElement
	public int getAlter() {  
	    return alter;  
	}  
	public void setAlter(int alter) {  
	    this.alter = alter;  
	}
}
