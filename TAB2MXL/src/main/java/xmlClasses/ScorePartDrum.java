package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ScorePartDrum extends Score_Part{
	
	private ArrayList<ScoreInstrument> si;

	public ScorePartDrum() {}  
	
	public ScorePartDrum(String id, String partName) { 
	    super(id, partName);
	    this.si = new ArrayList<ScoreInstrument>();
	    si.add(new ScoreInstrument("P1-I36", "Bass Drum 1"));
	    si.add(new ScoreInstrument("P1-I37", "Bass Drum 2"));
	    si.add(new ScoreInstrument("P1-I38", "Side Stick"));
	    si.add(new ScoreInstrument("P1-I39", "Snare"));
	    si.add(new ScoreInstrument("P1-I42", "Low Floor Tom"));
	    si.add(new ScoreInstrument("P1-I43", "Closed Hi-Hat"));
	    si.add(new ScoreInstrument("P1-I44", "High Floor Tom"));
	    si.add(new ScoreInstrument("P1-I45", "Pedal Hi-Hat"));
	    si.add(new ScoreInstrument("P1-I46", "Low Tom"));
	    si.add(new ScoreInstrument("P1-I47", "Open Hi-Hat"));
	    si.add(new ScoreInstrument("P1-I48", "Low-Mid Tom"));
	    si.add(new ScoreInstrument("P1-I49", "Hi-Mid Tom"));
	    si.add(new ScoreInstrument("P1-I50", "Crash Cymbal 1"));
	    si.add(new ScoreInstrument("P1-I51", "High Tom"));
	    si.add(new ScoreInstrument("P1-I52", "Ride Cymbal 1"));
	    si.add(new ScoreInstrument("P1-I53", "Chinese Cymbal"));
	    si.add(new ScoreInstrument("P1-I54", "Ride Bell"));
	    si.add(new ScoreInstrument("P1-I55", "Tambourine"));
	    si.add(new ScoreInstrument("P1-I56", "Splash Cymbal"));
	    si.add(new ScoreInstrument("P1-I57", "Cowbell"));
	    si.add(new ScoreInstrument("P1-I58", "Crash Cymbal 2"));
	    si.add(new ScoreInstrument("P1-I60", "Ride Cymbal 2"));
	    si.add(new ScoreInstrument("P1-I64", "Open Hi Conga"));
	    si.add(new ScoreInstrument("P1-I65", "Low Conga"));
	    //HARDCODED
	    
	} 
	
	@XmlElement(name="score-instrument")
	public ArrayList<ScoreInstrument> getScoreInstrument() {  
	    return si;  
	}  
	public void setInstrument(ArrayList<ScoreInstrument> si) {  
	    this.si = si;  
	}  
}

