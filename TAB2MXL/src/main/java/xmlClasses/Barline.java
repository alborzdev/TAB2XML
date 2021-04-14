package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

import com.sun.xml.txw2.annotation.XmlAttribute;

public class Barline {
	private String location;
	private String barStyle;
	private String direction;
	private Repeat repeat;
	
	public Barline() {
		
	}
	
	public Barline(String loc, String bs, String direction) {
		this.location = loc;
		this.barStyle = bs;
		this.repeat = new Repeat(direction);
	}
	
	@XmlAttribute
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
	
	@XmlElement(name="repeat")
	public Repeat getRepeat() {  
	    return repeat;  
	}  
	public void setRepeat(Repeat repeat) {  
	    this.repeat = repeat;  
	}
			
}
