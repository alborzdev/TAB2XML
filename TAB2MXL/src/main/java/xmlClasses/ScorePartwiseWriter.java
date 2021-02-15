package xmlClasses;

import java.io.IOException;

import java.util.ArrayList;

import xmlClasses.Attributes;
import xmlClasses.Clef;
import xmlClasses.Key;
import xmlClasses.Measure;
import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.Pitch;
import xmlClasses.Time;

/**
 * This class is to create a new score partwise object
 * that contains relevent xmlElements
 */

public class ScorePartwiseWriter {
	
	private Score_Partwise spw;
	private Work work;
	private Identification id;
	private Part_List pl;
	ArrayList <Creator> creators = new ArrayList<Creator>();
	private Score_Part sp;
    
	
	ScorePartwiseWriter(String name, String title, String lyricist, String composer, Part part){
		//Creating a new work with the title
		work = new Work(title);
		
		//Adding new creators
		creators.add(new Creator("composer", composer)); 
		creators.add(new Creator("lyricist", lyricist)); 
		
		//Creating a new id with the creators in it
		id = new Identification (creators);
		
		//For now score part is hard coded
		sp = new Score_Part("P1", "Music"); //HARD CODED
		
		//Putting score part in a new part list
		pl = new Part_List(sp);
		
		//The final score partwise object
		spw = new Score_Partwise(3.1, pl, part, id, work);
	}
	
	//Returning the score partwise object after it is constructed
	public Score_Partwise getScore_Partwise() {
		return spw;
	}
	
	
}