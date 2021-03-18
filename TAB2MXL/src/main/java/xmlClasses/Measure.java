package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;

public class Measure {
	private int number;
	private Attributes att;
	private ArrayList<Object> note;
	//private ArrayList<DrumNote> drumNote;
	
	public Measure() {
		
	}  
	
	public Measure(int number, Attributes att, ArrayList<Object> note) {  
	    this.number = number;
	    this.att = att;
	    this.note = note;  
	} 
	public Measure(int number, ArrayList<Object> note) {
	    this.number = number;
	    this.note = note;  
	}
	public Measure(int number, Attributes att) {
	    this.att = att;
	    this.number = number;
	    this.note = new ArrayList<Object>();
	} 
	public Measure(int number) {
	    this.number = number;
	    this.note = new ArrayList<Object>();
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
	
	@XmlElement(name="note", type=Note.class)
	public ArrayList<Object> getNote() {  
	    return note;  
	}  
	public void setNote(ArrayList<Object> note) {  
	    this.note = note;  
	}
	
	//PartWriter
	public void addNote(Object n) {
		note.add(n);
	}
	//DrumPartWriter
//		public void addDrumNote(DrumNote n) {
//			drumNote.add(n);
//		}
	
}
