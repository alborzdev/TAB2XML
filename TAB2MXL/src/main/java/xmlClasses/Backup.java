package xmlClasses;

import javax.xml.bind.annotation.XmlElement;

public class Backup extends Note{
	private int duration;
	
	public Backup() {
		
	}
	
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
