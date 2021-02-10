package xmlClasses;

import xmlClasses.Attributes;
import xmlClasses.Clef;
import xmlClasses.Key;
import xmlClasses.Measure;
import xmlClasses.Note;
import xmlClasses.Part;
import xmlClasses.Pitch;
import xmlClasses.Time;

public class ScorePartwiseWriter {
	
	private Score_Partwise sp;
	private Work work;
	private Identification id;
	private Part_List pl;

	
	//Lab change
	//Constructor
	ScorePartwiseWriter(){
		sp = new Score_Partwise();
	}
	
	//Empty measure
	//double version, Part_List part_list, Part part, Identification id, Work 
	public void makePartwise(double version, Work work, Identification id, Part_List pl, Part part){
		sp = new Score_Partwise(version, pl, part, id, work);
		
	}
	
	public void makeWork() {
		work.setWorkTitle(application.MainController.getText());
	}
	
	public void makeIdentification(String name, String lyr, String comp) {
		id.setCreator(null);
	}
	
}