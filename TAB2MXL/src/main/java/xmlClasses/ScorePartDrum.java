package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ScorePartDrum extends Score_Part{
	private ArrayList<ScoreInstrument> si;

	public ScorePartDrum() {}  
	
	public ScorePartDrum(String id, String partName, ArrayList<ScoreInstrument> si) { 
	    super(id, partName);
	    this.si = si;
	} 
	
	@XmlElement(name="score-instrument")
		public ArrayList<ScoreInstrument> getScoreInstrument() {  
		    return si;  
		}  
		public void setInstrument(ArrayList<ScoreInstrument> si) {  
		    this.si = si;  
		}  
}

