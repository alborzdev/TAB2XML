package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

/* Used to represent a backup in the time-line of notes */
public class Backup {
	private int duration;
	
	public Backup(int duration) {
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

