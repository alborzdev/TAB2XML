package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Notations {
	private Technical tech;
	private Slur slur;

	public Notations() {}  
	
	public Notations(Technical tech) { 
	    this.tech = tech;  
	} 
	public Notations(Technical tech, Slur slur) { 
	    this.tech = tech;
	    this.slur = slur;
	} 
	
	@XmlElement (name="technical")
	public Technical getTech() {  
	    return tech;  
	}  
	public void setTech(Technical tech) {  
	    this.tech = tech;  
	} 
	@XmlElement 
	public Slur getSlur() {  
	    return slur;  
	}  
	public void setSlur(Slur slur) {  
	    this.slur = slur;  
	} 
	
}
