package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

public class Identification {
	private Creator creator;
	
	public Identification() {}  
	
	public Identification(Creator creator) {  
	    super();  
	    this.creator = creator;  
	} 
	
	@XmlElement(name="creator")
	public Creator getCreator() {  
	    return creator;  
	}  
	public void setCreator(Creator creator) {  
	    this.creator = creator;  
	}

}
