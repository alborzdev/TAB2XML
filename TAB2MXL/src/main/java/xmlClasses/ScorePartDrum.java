package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ScorePartDrum extends Score_Part{
	private ArrayList<ScoreInstrument> si;

	public ScorePartDrum() {}  
	
	public ScorePartDrum(String id, String partName, ArrayList<ScoreInstrument> sii) { 
	    super(id, partName);
	    sii.add(new ScoreInstrument("P1-I36", "Bass Drum 1"));
	    sii.add(new ScoreInstrument("P1-I37", "Bass Drum 2"));
	    sii.add(new ScoreInstrument("P1-I38", "Side Stick"));
	    sii.add(new ScoreInstrument("P1-I39", "Snare"));
	    sii.add(new ScoreInstrument("P1-I42", "Low Floor Tom"));
	    sii.add(new ScoreInstrument("P1-I43", "Closed Hi-Hat"));
	    sii.add(new ScoreInstrument("P1-I44", "High Floor Tom"));
	    sii.add(new ScoreInstrument("P1-I45", "Pedal Hi-Hat"));
	    sii.add(new ScoreInstrument("P1-I46", "Low Tom"));
	    sii.add(new ScoreInstrument("P1-I47", "Open Hi-Hat"));
	    sii.add(new ScoreInstrument("P1-I48", "Low-Mid Tom"));
	    sii.add(new ScoreInstrument("P1-I49", "Hi-Mid Tom"));
	    sii.add(new ScoreInstrument("P1-I50", "Crash Cymbal 1"));
	    sii.add(new ScoreInstrument("P1-I51", "High Tom"));
	    sii.add(new ScoreInstrument("P1-I52", "Ride Cymbal 1"));
	    sii.add(new ScoreInstrument("P1-I53", "Chinese Cymbal"));
	    sii.add(new ScoreInstrument("P1-I54", "Ride Bell"));
	    sii.add(new ScoreInstrument("P1-I55", "Tambourine"));
	    sii.add(new ScoreInstrument("P1-I56", "Splash Cymbal"));
	    sii.add(new ScoreInstrument("P1-I57", "Cowbell"));
	    sii.add(new ScoreInstrument("P1-I58", "Crash Cymbal 2"));
	    sii.add(new ScoreInstrument("P1-I60", "Ride Cymbal 2"));
	    sii.add(new ScoreInstrument("P1-I64", "Open Hi Conga"));
	    sii.add(new ScoreInstrument("P1-I65", "Low Conga"));
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

