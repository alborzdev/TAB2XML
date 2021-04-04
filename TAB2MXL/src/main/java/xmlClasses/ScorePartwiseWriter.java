package xmlClasses;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xmlClasses.Attributes;
import xmlClasses.Clef;
import xmlClasses.Key;
import xmlClasses.Measure;
import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.Pitch;
import xmlClasses.Time;

public class ScorePartwiseWriter {
	
	private Score_Partwise spw;
	private Work work;
	private Identification id;
	private Part_List pl;
	ArrayList <Creator> creators = new ArrayList<Creator>();
	private Score_Part sp;
	private ScorePartDrum spd;
	private ArrayList<ScoreInstrument> si = new ArrayList<ScoreInstrument>();

    
	
	public ScorePartwiseWriter(String title, String lyricist, String composer, Part part){
		work = new Work(title);
		creators.add(new Creator("composer", composer)); 
		creators.add(new Creator("lyricist", lyricist)); 
		id = new Identification (creators);
		sp = new Score_Part("P1", "Music"); //HARD CODED
		pl = new Part_List(sp);
		spw = new Score_Partwise(3.1, pl, part, id, work);
	}
	public ScorePartwiseWriter(String title, String lyricist, String composer, Part part, ArrayList<String> drumkit){         
		work = new Work(title);         
		creators.add(new Creator("composer", composer));         
		creators.add(new Creator("lyricist", lyricist));          
		id = new Identification (creators);         
		spd = new ScorePartDrum("P1", "Music");         	      
		pl = new Part_List(spd);         
		spw = new Score_Partwise(3.1, pl, part, id, work);     
	}
	
	//done
	public Score_Partwise getScore_Partwise() {
		return spw;
	}
	
}