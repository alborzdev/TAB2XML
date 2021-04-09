package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;

import com.sun.xml.txw2.annotation.XmlElement;

public class Slur {
	private int number;
	private String type;
	private String placement;
	
	
	public Slur() {
	}
	
	public Slur(int number, String type, String placement) {
		this.number = number;
		this.type = type;
		this.placement = placement;
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
	
	@XmlAttribute
	public String getPlacement() {  
	    return placement;  
	}  
	public void setPlacement(String placement) {  
	    this.placement = placement;  
	}
}
