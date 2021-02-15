package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Notations {
	private Technical tech;

	public Notations() {}  
	
	public Notations(Technical tech) { 
	    this.tech = tech;  
	} 
	
	@XmlElement 
	public Technical getTech() {  
	    return tech;  
	}  
	public void setTech(Technical tech) {  
	    this.tech = tech;  
	} 
	
}
