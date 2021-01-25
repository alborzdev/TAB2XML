package xmlClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Part {
	
	private String id;
	private Measure measure;
	public Part() {}  
	
	public Part(String id, Measure measure) {  
	    super();  
	    this.id = id;
	    this.measure = measure;
	} 
	
	@XmlAttribute
	public String getID() {  
	    return id;  
	}  
	public void setId(String id) {  
	    this.id = id;  
	}
	
	@XmlElement
	public Measure getMeasure() {  
	    return measure;  
	}  
	public void setMeasure(Measure measure) {  
		this.measure = measure;
	}
}
