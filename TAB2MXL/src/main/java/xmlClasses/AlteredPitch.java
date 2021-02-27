package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public class AlteredPitch extends Pitch {
		private int alter;
		
		public AlteredPitch() {}  
		
		public AlteredPitch(String step, int octave, int alter) {
		    super(step, octave);
		    this.alter = alter;
		} 
		
		@XmlElement
		public int getAlter() {  
		    return alter;  
		}  
		public void setAlter(int alter) {  
		    this.alter = alter;  
		}
	}
