package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"tuningStep", "tuningOctave"})
public class StaffTuning {
	private int line;
	private String tuningStep;
	private int tuningOctave;

		public StaffTuning() {}  
		
		public StaffTuning(int line, String tuningStep, int tuningOctave) { 
		    this.line = line;  
		    this.tuningStep = tuningStep;
		    this.tuningOctave = tuningOctave;
		} 
		
		@XmlAttribute  
		public int getLine() {  
		    return line;  
		}  
		public void setLine(int line) {  
		    this.line = line;  
		} 
		
		@XmlElement(name="tuning-step")
		public String getTuningStep() {  
		    return tuningStep;  
		}  
		public void setTuningStep(String tuningStep) {  
		    this.tuningStep = tuningStep;  
		}  
		
		@XmlElement(name="tuning-octave")
		public int getTuningOctave() {  
		    return tuningOctave;  
		}  
		public void setTuningOctave(int to) {  
		    this.tuningOctave = to;  
		} 
}
