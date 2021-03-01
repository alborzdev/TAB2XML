package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class Barline {
	private String location;
	private String barStyle;
	
	public Barline() {
		
	}
	
	public Barline(String loc, String bs) {
		this.location = loc;
		this.barStyle = bs;
	}
	
	@XmlElement
	public String getLocation() {  
	    return location;  
	}  
	public void setLocation(String loc) {  
	    this.location = loc;  
	}
	
	@XmlElement(name="bar-style")
	public String getBarStyle() {  
	    return barStyle;  
	}  
	public void setBarStyle(String bs) {  
	    this.barStyle = bs;  
	}
			
}
