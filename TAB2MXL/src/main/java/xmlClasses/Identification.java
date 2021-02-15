package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Identification {
	private ArrayList <Creator> creators;
	
	public Identification() {}  
	
	public Identification(ArrayList <Creator> creators) { 
	    this.creators = creators;  
	} 
	
	@XmlElement(name="creator")
	public ArrayList <Creator> getCreators() {  
	    return creators;  
	}  
	
	public void setCreator(ArrayList <Creator> creators) {  
	    this.creators = creators;  
	}

}
