package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Instrument {
	private String id;
	
	
	public Instrument() {
	}
	
	public Instrument(String id) {
		this.id = id;
	}
	
	@XmlAttribute
	public String getId() {  
	    return id;  
	}  
	public void setId(String id) {  
	    this.id = id;  
	}
	
}
