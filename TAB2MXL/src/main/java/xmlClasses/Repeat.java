package xmlClasses;

import com.sun.xml.txw2.annotation.XmlAttribute;

public class Repeat {
	private String direction;
	
	public Repeat() {
		
	}
	
	public Repeat(String direction) {
		this.direction = direction;
	}
	
	@XmlAttribute
	public String getDirection() {  
	    return direction;  
	}  
	public void setDirection(String direction) {  
	    this.direction = direction;  
	}
}
