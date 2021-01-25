package xmlClasses;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Part {
	
	private String id;
	private ArrayList<Measure> measure;
	public Part() {}  
	
	public Part(String id, ArrayList<Measure> measure) {  
	    super();  
	    this.id = id;
	    this.measure = measure;
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
}
