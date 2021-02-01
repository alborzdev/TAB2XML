package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Measure {
	private int number;
	private Attributes att;
	private ArrayList<Note> note;
	
	public Measure() {
		
	}  
	
	public Measure(int number, Attributes att, ArrayList<Note> note) {  
	    super();  
	    this.number = number;
	    this.att = att;
	    this.note = note;  
	} 
	public Measure(int number, ArrayList<Note> note) {  
	    super();  
	    this.number = number;
	    this.note = note;  
	} 
	public Measure(int number) {  
	    super();  
	    this.number = number;
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
	public ArrayList<Note> getNote() {  
	    return note;  
	}  
	public void setNote(ArrayList<Note> note) {  
	    this.note = note;  
	}
	
	//PartWritter
	public void addNote(Note n) {
		note.add(n);
	}
	
}
