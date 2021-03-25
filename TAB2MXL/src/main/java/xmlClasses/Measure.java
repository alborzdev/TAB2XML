package xmlClasses;

import java.util.ArrayList;

import javax.xml.bind.annotation.*;

public class Measure {
	private int number;
	private Attributes att;
	
	private ArrayList<Entry> entries;
	private Barline barline;
	private Backup backup;
	//private ArrayList<DrumNote> drumNote;
	
	public Measure() {
		
	}  
	
	public Measure(int number, Attributes att, ArrayList<Entry> entries) {  
	    this.number = number;
	    this.att = att;
	    this.entries = entries;  
	} 
	public Measure(int number, ArrayList<Entry> entries) {
	    this.number = number;
	    this.entries = entries;  
	}
	public Measure(int number, Attributes att) {
	    this.att = att;
	    this.number = number;
	    this.entries = new ArrayList<Entry>();
	} 
	public Measure(int number) {
	    this.number = number;
	    this.entries = new ArrayList<Entry>();
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
	
	@XmlAnyElement
	public ArrayList<Entry> getNote() {  
	    return entries;  
	}  
	public void setNote(ArrayList<Entry> entries) {  
	    this.entries = entries;  
	}
	
	//PartWriter
	public void addNote(Note n) {
		Entry e = new Entry();
		e.setName("note");
		e.setValue(n);
		this.entries.add(e);
	}

	@XmlElement
	public Barline getBarline() {
		return barline;
	}
	public void setBarline(Barline barline) {
		this.barline = barline;
	}
	
	public void addBackup(Backup b) {
		Entry e = new Entry();
		e.setName("backup");
		e.setValue(b);
		this.entries.add(e);
	}
	
	public void addForward(Forward f) {
		Entry e = new Entry();
		e.setName("forward");
		e.setValue(f);
		this.entries.add(e);
	}

}
