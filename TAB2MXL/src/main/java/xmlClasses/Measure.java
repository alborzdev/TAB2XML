package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Measure {
	private int number;
	private Attributes att;
	private Note note;
	
	public Measure() {}  
	
	public Measure(int number, Attributes att, Note note) {  
	    super();  
	    this.number = number;
	    this.att = att;
	    this.note = note;  
	} 
	public Measure(int number, Note note) {  
	    super();  
	    this.number = number;
	    this.note = note;  
	} 
	
	@XmlAttribute
	public int getNumber() {  
	    return number;  
	}  
	public void setNumber(int number) {  
	    this.number = number;  
	}
	
	@XmlElement
	public Attributes getAttributes() {  
	    return att;  
	}  
	public void setAttributes(Attributes att) {  
	    this.att = att;  
	}
	
	@XmlElement
	public Note getNote() {  
	    return note;  
	}  
	public void setNote(Note note) {  
	    this.note = note;  
	}
}
