package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={"divisions", "key", "time", "clef"})
public class Attributes {
	private int divisions;
	private Key key;
	private Time time;
	private Clef clef;
	
	
	public Attributes() {}  
	
	public Attributes(int divisions, Key key, Time time, Clef clef) {  
	    super();  
	    this.divisions = divisions; 
	    this.key = key;
	    this.time = time;
	    this.clef = clef;
	} 
	
	@XmlElement
	public int getDivisions() {  
	    return divisions;  
	}  
	public void setDivisions(int divisions) {  
	    this.divisions = divisions;  
	}
	
	@XmlElement
	public Key getKey() {  
	    return key;  
	}  
	public void setKey(Key key) {  
	    this.key = key;  
	}
	
	@XmlElement
	public Time getTime() {  
	    return time;  
	}  
	public void setTime(Time time) {  
	    this.time = time;  
	}
	
	@XmlElement
	public Clef getClef() {  
	    return clef;  
	}  
	public void setClef(Clef clef) {  
	    this.clef = clef;  
	}
}
