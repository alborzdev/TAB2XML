package xmlClasses;

import com.sun.xml.txw2.annotation.XmlAttribute;

public class Direction {
	private String placement;
	private DirectionType dt;
	
	public Direction() {
		
	}
	
	public Direction(String placement) {
		this.placement = placement;
	}
	
	@XmlAttribute
	public String getPlacement() {  
	    return placement;  
	}  
	public void setPlacement(String placement) {  
	    this.placement = placement;  
	}
	
}
