package xmlClasses;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class Direction {
	private String placement;
	private DirectionType dt;
	
	public Direction() {
		
	}
	
	public Direction(String placement, String words) {
		this.placement = placement;
		this.dt = new DirectionType(words);
	}
	
	@XmlAttribute
	public String getPlacement() {  
	    return placement;  
	}  
	public void setPlacement(String placement) {  
	    this.placement = placement;  
	}
	@XmlElement(name="direction-type")
	public DirectionType getDirectionType() {
		return dt;
	}
	public void setDirectionType(DirectionType dt) {  
	    this.dt = dt;  
	}
	
}
