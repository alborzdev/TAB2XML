package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import com.sun.xml.txw2.annotation.XmlElement;

public class HammerOn {
	private int number;
	private String type;
	private String character;
	
	
	public HammerOn() {
	}
	
	public HammerOn(int number, String type, String character) {
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
	
	@XmlElement
	public String getChar() {  
	    return type;  
	}  
	public void setChar(String character) {  
	    this.character = character;  
	}
}
