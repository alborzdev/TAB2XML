package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Technical {
	private int string;
	private int fret;
	
	public Technical() {}  
	
	public Technical(int string, int fret) { 
	    this.string = string;  
	    this.fret = fret;
	} 
	
	@XmlElement  
	public int getString() {  
	    return string;  
	}  
	public void setString(int string) {  
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
