package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ScorePartDrum extends Score_Part{
	private ScoreInstrument si;

	public ScorePartDrum() {}  
	
	public ScorePartDrum(String id, String partName, ScoreInstrument si) { 
	    super(id, partName);
	    this.si = si;
	} 
	
	@XmlElement(name="score-instrument")
		public ScoreInstrument getScoreInstrument() {  
		    return si;  
		}  
		public void setInstrument(ScoreInstrument si) {  
		    this.si = si;  
		}  
}

