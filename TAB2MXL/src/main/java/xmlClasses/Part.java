package xmlClasses;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Part {
	
	private String id;
	private ArrayList<Measure> measure;
	public Part() {
		id="";
		measure = new ArrayList<Measure>();
	}  
	
	public Part(String id, ArrayList<Measure> measure) {
	    this.id = id;
	    this.measure = measure;
	}
	public Part(String id) {  
	    this.id = id;
	    measure = new ArrayList<Measure>();
	} 
	
	@XmlAttribute
	public String getID() {  
	    return id;  
	}  
	public void setId(String id) {  
	    this.id = id;  
	}
	
	@XmlElement
	public ArrayList<Measure> getMeasure() {  
	    return measure;  
	}  
	public void setMeasure(ArrayList<Measure> measure) {  
		this.measure = measure;
	}
	
	//PartWriter
	public void addMeasure(Measure m) {
		measure.add(m);
	}
	
}
