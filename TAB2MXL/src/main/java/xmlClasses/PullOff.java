package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

public class PullOff {
	private int number;
	private String type;
	@XmlValue
	private String character;
	
	
	public PullOff() {
	}
	
	public PullOff(int number, String type, String character) {
		this.number = number;
		this.type = type;
		this.character = character;
	}
	
	@XmlAttribute
	public int getNumber() {  
	    return number;  
	}  
	public void setNumber(int number) {  
	    this.number = number;  
	}
	
	@XmlAttribute
	public String getType() {  
	    return type;  
	}  
	public void setType(String type) {  
	    this.type = type;  
	}
	
	public String getChar() {  
	    return character;  
	}  
	public void setChar(String character) {  
	    this.character = character;  
	}
}
