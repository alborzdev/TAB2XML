package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"string","fret"})
public class Technical {
	private int string;
	private int fret;
	private HammerOn hammerOn;
	private PullOff pullOff;
	
	public Technical() {}  
	
	public Technical(int string, int fret) { 
	    this.string = string;  
	    this.fret = fret;
	} 
	public Technical(int string, int fret, HammerOn hammerOn) { 
	    this.string = string;  
	    this.fret = fret;
	    this.hammerOn = hammerOn;
	} 
	public Technical(int string, int fret, PullOff pullOff) { 
	    this.string = string;  
	    this.fret = fret;
	    this.pullOff = pullOff;
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
