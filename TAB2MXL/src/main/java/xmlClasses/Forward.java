package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class Forward extends Note{
	private int duration;
	
	public Forward() {
		
	}
	public Forward(int duration) {
		this.duration = duration;
	}
	
	@XmlElement
	public int getDuration() {  
	    return duration;  
	}  
	public void setDuration(int duration) {  
	    this.duration = duration;  
	}
}
