package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Technical {
	private String string;
	private int fret;
	
	public Technical() {}  
	
	public Technical(String string, int fret) { 
	    this.string = string;  
	    this.fret = fret;
	} 
	
	@XmlElement  
	public String getString() {  
	    return string;  
	}  
	public void setString(String string) {  
	    this.string = string;  
	} 
	
	@XmlElement
	public int getFret() {  
	    return fret;  
	}  
	public void setFret(int fret) {  
	    this.fret = fret;  
	}  
}
