package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

/* Used to represent going forward in the time-line of notes */
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
